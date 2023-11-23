package com.itsoeh.jortiz.proyectoaaisic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.AEstudiante;
import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.Appi;
import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CambiarNombre extends AppCompatActivity {

    private Button btn_login_back2, btnGuardarCambios;
    private EditText txtNombre,  txtApellidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_nombre);
        btn_login_back2 = findViewById(R.id.chaname_btn_back);
        btnGuardarCambios = findViewById(R.id.chaname_btn_guardar);
        txtNombre = findViewById(R.id.chaname_txt_nombre);
        txtApellidos = findViewById(R.id.chaname_txt_apellidos);
        btn_login_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreNuevo = txtNombre.getText().toString();
                String apellidosNuevo = txtApellidos.getText().toString();
                if (nombreNuevo.equals("") || apellidosNuevo.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CambiarNombre.this);
                    LayoutInflater inflater = CambiarNombre.this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                    builder.setView(dialogView);

                    AlertDialog dialog = builder.create();

                    TextView title = dialogView.findViewById(R.id.title);
                    title.setText("Ocurrió un error");
                    TextView message = dialogView.findViewById(R.id.message);
                    message.setText("Completa los datos requeridos");
                    Button button = dialogView.findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    if (isConnectedToInternet()) {
                        if (Pattern.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", nombreNuevo)) {
                            if (Pattern.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", apellidosNuevo)) {
                                SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                                int idEstudiante = sharedPref.getInt("idEstudiante", -1);
                                RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(CambiarNombre.this).getRequestQueue();
                                StringRequest solicitud = new StringRequest(Request.Method.POST, Appi.ACTUALIZAR_NOMBRE, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject respuesta = new JSONObject(response);
                                            if (!respuesta.getBoolean("error")) {
                                                actualizarLocal();
                                                Toast.makeText(CambiarNombre.this, "Nombre actualizado a: " + nombreNuevo + " " + apellidosNuevo, Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(CambiarNombre.this, respuesta.getString("message"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            Toast.makeText(CambiarNombre.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(CambiarNombre.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> parametros = new HashMap<String, String>();
                                        parametros.put("idEstudiante", Integer.toString(idEstudiante));
                                        parametros.put("nombreNuevo", nombreNuevo);
                                        parametros.put("apellidosNuevo", apellidosNuevo);
                                        return parametros;
                                    }
                                };
                                colaDeSolicitudes.add(solicitud);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CambiarNombre.this);
                                LayoutInflater inflater = CambiarNombre.this.getLayoutInflater();
                                View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                                builder.setView(dialogView);

                                AlertDialog dialog = builder.create();

                                TextView title = dialogView.findViewById(R.id.title);
                                title.setText("Ocurrió un error");
                                TextView message = dialogView.findViewById(R.id.message);
                                message.setText("Formato de apellidos incorrecto");
                                Button button = dialogView.findViewById(R.id.button);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CambiarNombre.this);
                            LayoutInflater inflater = CambiarNombre.this.getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                            builder.setView(dialogView);

                            AlertDialog dialog = builder.create();

                            TextView title = dialogView.findViewById(R.id.title);
                            title.setText("Ocurrió un error");
                            TextView message = dialogView.findViewById(R.id.message);
                            message.setText("Formato de nombre incorrecto");
                            Button button = dialogView.findViewById(R.id.button);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    } else {
                        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                        int idEstudiante = sharedPref.getInt("idEstudiante", -1);
                        AEstudiante aEstudiante = new AEstudiante(getApplicationContext());
                        try {
                            boolean actualizacionExitosa = aEstudiante.actualizarContrasenia(idEstudiante, nombreNuevo, apellidosNuevo);
                            if (actualizacionExitosa) {
                                Toast.makeText(CambiarNombre.this, "Nombre actualizado a: " + nombreNuevo + " " + apellidosNuevo, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (Exception e) {
                            Toast.makeText(CambiarNombre.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }

    private void actualizarLocal() {
        String nombreNuevo = txtNombre.getText().toString();
        String apellidosNuevo = txtApellidos.getText().toString();
        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        int idEstudiante = sharedPref.getInt("idEstudiante", -1);
        AEstudiante aEstudiante = new AEstudiante(getApplicationContext());
        aEstudiante.actualizarNombre(idEstudiante, nombreNuevo, apellidosNuevo);
    }


    public boolean isConnectedToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}