package com.itsoeh.jortiz.proyectoaaisic;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.Appi;
import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.VolleySingleton;
import com.itsoeh.jortiz.proyectoaaisic.models.MListaAsignaturas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class buscarAsignaturas extends AppCompatActivity {

    List<MListaAsignaturas> elements;

    public interface OnAsignaturaSelectedListener {
        void onAsignaturaSelected(MListaAsignaturas asignatura);
    }

    private OnAsignaturaSelectedListener listener;

    public void setOnAsignaturaSelectedListener(OnAsignaturaSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_asignaturas);

        setOnAsignaturaSelectedListener(new OnAsignaturaSelectedListener() {
            @Override
            public void onAsignaturaSelected(MListaAsignaturas asignatura) {
                // Aquí puedes manejar la asignatura seleccionada
            }
        });

        init();
    }

    public void init() {
        elements = new ArrayList<>(); // Inicializa tu lista aquí

        RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(this).getRequestQueue();
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.GET, Appi.LISTAR, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray contenido = response.getJSONArray("contenido");
                    for (int i = 0; i < contenido.length(); i++) {
                        JSONObject asignatura = contenido.getJSONObject(i);
                        String nombreAsignatura = asignatura.getString("asignatura");
                        String nombreDocente = asignatura.getString("nombreDoc");
                        String claveAsignatura = asignatura.getString("claveAsignatura");

                        elements.add(new MListaAsignaturas(nombreAsignatura, nombreDocente, claveAsignatura));
                    }

                    ListaAsignatura listaAsignatura = new ListaAsignatura(elements, buscarAsignaturas.this, listener);
                    RecyclerView recyclerView = findViewById(R.id.rec_listasinaturas);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(buscarAsignaturas.this,1));
                    recyclerView.setAdapter(listaAsignatura);

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

                } catch (JSONException e) {
                    Toast.makeText(buscarAsignaturas.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(buscarAsignaturas.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        colaDeSolicitudes.add(solicitud);
    }
}
