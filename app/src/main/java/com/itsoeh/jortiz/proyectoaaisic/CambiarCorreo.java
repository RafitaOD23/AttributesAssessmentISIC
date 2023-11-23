package com.itsoeh.jortiz.proyectoaaisic;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
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

public class CambiarCorreo extends AppCompatActivity {

    private Button btn_back, btnGuardarCambios;
    private EditText txtOldEmail, txtNewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_correo);
        btn_back = findViewById(R.id.chaemail_btn_back);
        btnGuardarCambios = findViewById(R.id.chaemail_btn_guardar);
        txtOldEmail = findViewById(R.id.chaemail_txt_oldemail);
        txtNewEmail = findViewById(R.id.chaemail_txt_newemail);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correoAntiguo = txtOldEmail.getText().toString();
                String correoNuevo = txtNewEmail.getText().toString();
                if (correoAntiguo.equals("") || correoNuevo.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CambiarCorreo.this);
                    LayoutInflater inflater = CambiarCorreo.this.getLayoutInflater();
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
                        AEstudiante aEstudiante = new AEstudiante(getApplicationContext());
                        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                        String correoAlmacenado = sharedPref.getString("email", "");
                        if (correoAlmacenado.equals(correoAntiguo)) {
                            RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(CambiarCorreo.this).getRequestQueue();
                            StringRequest solicitud = new StringRequest(Request.Method.POST, Appi.ACTUALIZAR_CORREO, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject respuesta = new JSONObject(response);
                                        if (!respuesta.getBoolean("error")) {
                                            actualizarLocal();
                                            Toast.makeText(CambiarCorreo.this, "Correo actualizado correctamente", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(CambiarCorreo.this, Login.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finishAffinity();
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(CambiarCorreo.this);
                                            LayoutInflater inflater = CambiarCorreo.this.getLayoutInflater();
                                            View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                                            builder.setView(dialogView);

                                            AlertDialog dialog = builder.create();
                                            TextView title = dialogView.findViewById(R.id.title);
                                            title.setText("Ocurrió un error");
                                            TextView message = dialogView.findViewById(R.id.message);
                                            message.setText(respuesta.getString("message"));
                                            Button button = dialogView.findViewById(R.id.button);
                                            button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                }
                                            });

                                            dialog.show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(CambiarCorreo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "Error al procesar la respuesta JSON: " + e.getMessage());
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(CambiarCorreo.this, "Falló la actualización del correo: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> parametros = new HashMap<String, String>();
                                    parametros.put("correoAntiguo", correoAntiguo);
                                    parametros.put("correoNuevo", correoNuevo);
                                    return parametros;
                                }
                            };
                            colaDeSolicitudes.add(solicitud);

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CambiarCorreo.this);
                            LayoutInflater inflater = CambiarCorreo.this.getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                            builder.setView(dialogView);
                            AlertDialog dialog = builder.create();
                            TextView title = dialogView.findViewById(R.id.title);
                            title.setText("Ocurrió un error");
                            TextView message = dialogView.findViewById(R.id.message);
                            message.setText("El correo anterior no existe");
                            Button button = dialogView.findViewById(R.id.button);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        }

                    }else{
                        AEstudiante aEstudiante = new AEstudiante(getApplicationContext());
                        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                        String correoAlmacenado = sharedPref.getString("email", "");
                        if (correoAlmacenado.equals(correoAntiguo)) {
                            boolean actualizacionExitosa = aEstudiante.actualizarCorreo(correoAntiguo, correoNuevo);
                            if (!actualizacionExitosa) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CambiarCorreo.this);
                                LayoutInflater inflater = CambiarCorreo.this.getLayoutInflater();
                                View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                                builder.setView(dialogView);

                                AlertDialog dialog = builder.create();
                                TextView title = dialogView.findViewById(R.id.title);
                                title.setText("Ocurrió un error");
                                TextView message = dialogView.findViewById(R.id.message);
                                message.setText("El correo anterior no existe");
                                Button button = dialogView.findViewById(R.id.button);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                            }else{
                                Toast.makeText(CambiarCorreo.this, "Correo actualizado exitosamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CambiarCorreo.this, Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finishAffinity();
                            }
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CambiarCorreo.this);
                            LayoutInflater inflater = CambiarCorreo.this.getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                            builder.setView(dialogView);
                            AlertDialog dialog = builder.create();
                            TextView title = dialogView.findViewById(R.id.title);
                            title.setText("Ocurrió un error");
                            TextView message = dialogView.findViewById(R.id.message);
                            message.setText("El correo anterior no existe");
                            Button button = dialogView.findViewById(R.id.button);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        }
                    }
                }
            }
        });
    }

    private void actualizarLocal() {
        String correoAntiguo = txtOldEmail.getText().toString();
        String correoNuevo = txtNewEmail.getText().toString();
        AEstudiante aEstudiante = new AEstudiante(getApplicationContext());
        aEstudiante.actualizarCorreo(correoAntiguo, correoNuevo);
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}