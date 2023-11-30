package com.itsoeh.jortiz.proyectoaaisic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.Appi;
import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.VolleySingleton;
import com.itsoeh.jortiz.proyectoaaisic.adapter.AdapterAsignatura;
import com.itsoeh.jortiz.proyectoaaisic.models.MAsignatura;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Asignaturas extends Fragment {

    private ImageView btn_aggCur;
    List<MAsignatura> elements;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_aggCur = view.findViewById(R.id.agg_btn_cur);
        btn_aggCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAgregarCurso();
            }
        });
        ListaAsignaturas(view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asignaturas, container, false);
    }

    private void ListaAsignaturas(View view) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        final int idEstudiante = sharedPref.getInt("idEstudiante", -1);

        TextView tvNoAsignaturas = view.findViewById(R.id.tv_empty);

        if (idEstudiante != -1) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Appi.LISTAR_ASIGNATURA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("contenido");
                                elements = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String asignatura = object.getString("asignatura");
                                    String claveAsignatura = object.getString("claveAsignatura");
                                    String nombre = object.getString("nombre");
                                    String apellidos = object.getString("apellidos");
                                    elements.add(new MAsignatura(asignatura, claveAsignatura, nombre + " " + apellidos));
                                }

                                if (elements.isEmpty()) {
                                    tvNoAsignaturas.setVisibility(View.VISIBLE);
                                } else {
                                    tvNoAsignaturas.setVisibility(View.GONE);
                                    AdapterAsignatura adapterAsignatura = new AdapterAsignatura(elements, getContext());
                                    RecyclerView recyclerView = view.findViewById(R.id.rec_list_asig);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

                                    int espaciado = getResources().getDimensionPixelSize(R.dimen.space_element);
                                    recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                                        @Override
                                        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                                            outRect.left = espaciado;
                                            outRect.right = espaciado;
                                            outRect.top = espaciado;
                                            outRect.bottom = espaciado;
                                        }
                                    });

                                    recyclerView.setAdapter(adapterAsignatura);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("idEstudiante", String.valueOf(idEstudiante));
                    return params;
                }
            };
            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(getActivity(), "No se encontró el ID del estudiante", Toast.LENGTH_SHORT).show();
        }
    }


    private void clickAgregarCurso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog, null);
        final EditText inputCodigo = view.findViewById(R.id.input);
        Button button = view.findViewById(R.id.button_unirte);
        final AlertDialog dialog = builder.setView(view).create(); // Crea el AlertDialog aquí
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String codigoAsignatura = inputCodigo.getText().toString();
                if (codigoAsignatura.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_layout, null);
                    builder.setView(view);
                    AlertDialog dialog = builder.create();
                    TextView title = view.findViewById(R.id.title);
                    title.setText("Ocurrió un error");
                    TextView message = view.findViewById(R.id.message);
                    message.setText("Los campos están vacíos");
                    Button button = view.findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else if (codigoAsignatura.length() != 5) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_layout, null);
                    builder.setView(view);
                    AlertDialog dialog = builder.create();
                    TextView title = view.findViewById(R.id.title);
                    title.setText("Ocurrió un error");
                    TextView message = view.findViewById(R.id.message);
                    message.setText("El código de la asignatura debe tener exactamente 7 dígitos");
                    Button button = view.findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    // Aquí obtienes el idEstudiante de las preferencias compartidas
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    final int idEstudiante = sharedPref.getInt("idEstudiante", -1);

                    if (idEstudiante != -1) {
                        StringRequest solicitudAgregar = new StringRequest(Request.Method.POST, Appi.AGREGAR_ESTUDIANTE,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject respuesta = new JSONObject(response);
                                            if (!respuesta.getBoolean("error")) {
                                                Toast.makeText(getActivity(), "Te has unido a la asignatura correctamente", Toast.LENGTH_SHORT).show();
                                                // Cierra el cuadro de diálogo
                                                dialog.dismiss(); // Llama a dismiss() en el AlertDialog
                                            } else {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                View view = inflater.inflate(R.layout.dialog_layout, null);
                                                builder.setView(view);
                                                AlertDialog dialog = builder.create();
                                                TextView title = view.findViewById(R.id.title);
                                                title.setText("Ocurrió un error");
                                                TextView message = view.findViewById(R.id.message);
                                                message.setText("El estudiante ya se registró en esta asignatura");
                                                Button button = view.findViewById(R.id.button);
                                                button.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                dialog.show();
                                            }
                                        } catch (JSONException e) {
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("codigoAsignatura", codigoAsignatura);
                                params.put("idEstudiante", String.valueOf(idEstudiante)); // Aquí conviertes el idEstudiante a String
                                return params;
                            }
                        };
                        VolleySingleton.getInstance(getActivity()).addToRequestQueue(solicitudAgregar);
                    } else {
                        Toast.makeText(getActivity(), "No se encontró el ID del estudiante", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        AppCompatImageButton backButton = view.findViewById(R.id.back_asi_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}