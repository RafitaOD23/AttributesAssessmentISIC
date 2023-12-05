package com.itsoeh.jortiz.proyectoaaisic;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private Button btnEntrada, btnRegistro;
    private EditText txtUsuario, txtPassword;
    private TextView  txtForgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnEntrada = findViewById(R.id.login_btn_entrar);
        btnRegistro = findViewById(R.id.login_btn_registrar);
        txtUsuario = findViewById(R.id.login_txt_correo);
        txtPassword = findViewById(R.id.login_txt_contraseña);
        txtForgot = (TextView) findViewById(R.id.login_btn_forgetPassword);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicForgot();
            }
        });
        btnEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEntrar();
            }
        });
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicRegistro();
            }
        });
    }

    private void clicForgot() {
        Intent brinco = new Intent(Login.this, Recuperarcontra.class);
        startActivity(brinco);
    }

    private void clicRegistro() {
        Intent brinco = new Intent(this, Registrar.class);
        startActivity(brinco);
    }

    private void clicEntrar() {
        String correo = txtUsuario.getText().toString();
        String contraseña = txtPassword.getText().toString();
        if(correo.equals("")||contraseña.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_layout, null);
            builder.setView(view);

            AlertDialog dialog = builder.create();

            TextView title = view.findViewById(R.id.title);
            title.setText("Correo y contraseña vacíos");
            TextView message = view.findViewById(R.id.message);
            message.setText("Ingresa los datos solicitados");
            Button button = view.findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }else{
            if (Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", correo)) {
                try {
                    if (isConnectedToInternet()) {

                        RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(this).getRequestQueue();
                        StringRequest solicitud = new StringRequest(Request.Method.POST, Appi.BUSCARCONTRASENIA, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject respuesta = new JSONObject(response);
                                    if (!respuesta.getBoolean("error")) {
                                        JSONArray datos = respuesta.getJSONArray("contenido");
                                        String nombre = datos.getString(0);
                                        String password = datos.getString(1);
                                        if (datos != null) {
                                            loggearse();
                                        }
                                    } else {
                                        Toast.makeText(Login.this, respuesta.getString("message"), Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "Respuesta del servidor: " + respuesta.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Error al procesar la respuesta JSON: " + e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Login.this, "Falló la búsqueda de la contraseña"+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams(){
                                Map<String, String> parametros = new HashMap<String, String>();
                                parametros.put("correo", correo);
                                parametros.put("contrasenia", contraseña);
                                return parametros;
                            }
                        };
                        colaDeSolicitudes.add(solicitud);
                    } else {
                        AEstudiante estudiante = new AEstudiante(this);
                        String [] datos = estudiante.buscarContrasenia(correo, contraseña);
                        if (datos != null) {
                            AEstudiante aEstudiante = new AEstudiante(getApplicationContext());
                            int idEstudiante = aEstudiante.obtenerIdEstudiante(correo);
                            SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("email", correo);
                            editor.putInt("idEstudiante", idEstudiante);
                            editor.apply();
                            Log.d(TAG, "Correo guardado: " + sharedPref.getString("email", null));
                            Log.d(TAG, "ID del estudiante guardado: " + sharedPref.getInt("idEstudiante", -1));
                            Intent brinco = new Intent(this, MainActivity.class);
                            startActivity(brinco);
                            Toast.makeText(Login.this, "Te has loggeado exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            LayoutInflater inflater = this.getLayoutInflater();
                            View view = inflater.inflate(R.layout.dialog_layout, null);
                            builder.setView(view);
                            AlertDialog dialog = builder.create();
                            TextView title = view.findViewById(R.id.title);
                            title.setText("Ocurrió un error");
                            TextView message = view.findViewById(R.id.message);
                            message.setText("El usuario o contraseña son incorrectos");
                            Button button = view.findViewById(R.id.button);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_layout, null);
                    builder.setView(view);
                    AlertDialog dialog = builder.create();
                    TextView title = view.findViewById(R.id.title);
                    title.setText("Ocurrió un error");
                    TextView message = view.findViewById(R.id.message);
                    message.setText("Se produjo un error desconocido");
                    Button button = view.findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_layout, null);
                builder.setView(view);

                AlertDialog dialog = builder.create();

                TextView title = view.findViewById(R.id.title);
                title.setText("Ocurrió un error");
                TextView message = view.findViewById(R.id.message);
                message.setText("El formato de correo es inválido");
                Button button = view.findViewById(R.id.button);
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

    private void loggearse() {
        String correo = txtUsuario.getText().toString();
        RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(this).getRequestQueue();
        StringRequest solicitud = new StringRequest(Request.Method.POST, Appi.OBTENERIDESTUDIANTE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta = new JSONObject(response);
                    if (!respuesta.getBoolean("error")) {
                        int idEstudiante = respuesta.getInt("contenido");
                        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("email", correo);
                        editor.putInt("idEstudiante", idEstudiante);
                        editor.apply();
                        Log.d(TAG, "Correo guardado: " + sharedPref.getString("email", null));
                        Log.d(TAG, "ID del estudiante guardado: " + sharedPref.getInt("idEstudiante", -1));
                        Intent brinco = new Intent(Login.this, MainActivity.class);
                        startActivity(brinco);

                        Toast.makeText(Login.this, "Te has loggeado exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, respuesta.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Falló la búsqueda del ID del estudiante"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", correo);
                return parametros;
            }
        };
        colaDeSolicitudes.add(solicitud);
    }
    public boolean isConnectedToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}