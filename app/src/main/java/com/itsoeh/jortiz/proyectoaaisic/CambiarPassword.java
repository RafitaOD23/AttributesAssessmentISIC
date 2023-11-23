package com.itsoeh.jortiz.proyectoaaisic;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
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
import java.util.regex.Pattern;

public class CambiarPassword extends AppCompatActivity {

    private Button btn_login_back2, btnGuardarCambios;
    private EditText txtOldPass, txtNewPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);
        btnGuardarCambios = findViewById(R.id.chapass_btn_guardar);
        txtOldPass = findViewById(R.id.chapass_txt_oldpass);
        txtNewPass = findViewById(R.id.chapass_txt_newpass);
        btn_login_back2 = findViewById(R.id.chapass_btn_back);
        btn_login_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordAntiguo = txtOldPass.getText().toString();
                String passwordNuevo = txtNewPass.getText().toString();
                if(passwordAntiguo.equals("") && passwordNuevo.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CambiarPassword.this);
                    LayoutInflater inflater = CambiarPassword.this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();
                    TextView title = dialogView.findViewById(R.id.title);
                    title.setText("Ocurrió un error");
                    TextView message = dialogView.findViewById(R.id.message);
                    message.setText("Debes completar todos los datos requeridos");
                    Button button = dialogView.findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }else {
                    if (Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d:,.!?@°]+$", passwordNuevo)) {
                        if(passwordNuevo.length() >= 8) {
                            if (isConnectedToInternet()) {
                                SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                                int idEstudiante = sharedPref.getInt("idEstudiante", -1);
                                RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(CambiarPassword.this).getRequestQueue();
                                StringRequest solicitud = new StringRequest(Request.Method.POST, Appi.ACTUALIZAR_PASSWORD, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject respuesta = new JSONObject(response);
                                            if (!respuesta.getBoolean("error")) {
                                                actualizarLocal();
                                                Toast.makeText(CambiarPassword.this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(CambiarPassword.this);
                                                LayoutInflater inflater = CambiarPassword.this.getLayoutInflater();
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
                                            Toast.makeText(CambiarPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, "Error al procesar la respuesta JSON: " + e.getMessage());
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(CambiarPassword.this, "Falló la actualización de la contraseña: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> parametros = new HashMap<String, String>();
                                        parametros.put("idEstudiante", String.valueOf(idEstudiante));
                                        parametros.put("passwordAntiguo", passwordAntiguo);
                                        parametros.put("passwordNuevo", passwordNuevo);
                                        return parametros;
                                    }
                                };
                                colaDeSolicitudes.add(solicitud);
                            }else{
                                SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                                int idEstudiante = sharedPref.getInt("idEstudiante", -1);
                                AEstudiante aEstudiante = new AEstudiante(getApplicationContext());
                                boolean actualizacionExitosa = aEstudiante.actualizarContrasenia(idEstudiante, passwordAntiguo, passwordNuevo);
                                if (actualizacionExitosa) {
                                    Toast.makeText(CambiarPassword.this, "Contraseña actualizada exitosamente", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CambiarPassword.this);
                                    LayoutInflater inflater = CambiarPassword.this.getLayoutInflater();
                                    View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                                    builder.setView(dialogView);
                                    AlertDialog dialog = builder.create();
                                    TextView title = dialogView.findViewById(R.id.title);
                                    title.setText("Ocurrió un error");
                                    TextView message = dialogView.findViewById(R.id.message);
                                    message.setText("No se pudo actualizar la contraseña");
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

                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(CambiarPassword.this);
                            LayoutInflater inflater = CambiarPassword.this.getLayoutInflater();
                            View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                            builder.setView(dialogView);
                            AlertDialog dialog = builder.create();
                            TextView title = dialogView.findViewById(R.id.title);
                            title.setText("Ocurrió un error");
                            TextView message = dialogView.findViewById(R.id.message);
                            message.setText("La nueva contraseña debe de contener más de 8 caracteres");
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(CambiarPassword.this);
                        LayoutInflater inflater = CambiarPassword.this.getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
                        builder.setView(dialogView);
                        AlertDialog dialog = builder.create();
                        TextView title = dialogView.findViewById(R.id.title);
                        title.setText("Ocurrió un error");
                        TextView message = dialogView.findViewById(R.id.message);
                        message.setText("La nueva contraseña debe contener letras y números");
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
        });


    }

    private void actualizarLocal() {
        String passwordAntiguo = txtOldPass.getText().toString();
        String passwordNuevo = txtNewPass.getText().toString();
        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        int idEstudiante = sharedPref.getInt("idEstudiante", -1);
        AEstudiante aEstudiante = new AEstudiante(getApplicationContext());
        aEstudiante.actualizarContrasenia(idEstudiante, passwordAntiguo, passwordNuevo);
    }
    public boolean isConnectedToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}