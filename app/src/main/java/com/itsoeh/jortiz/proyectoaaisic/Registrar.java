package com.itsoeh.jortiz.proyectoaaisic;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayoutStates;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.ADocente;
import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.AEstudiante;
import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.Appi;
import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.VolleySingleton;
import com.itsoeh.jortiz.proyectoaaisic.models.MDocente;
import com.itsoeh.jortiz.proyectoaaisic.models.MEstudiante;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Registrar extends AppCompatActivity {
    private Button btnBackLog, btnRegistrar;
    private EditText txtNombre,txtApellido,txtCorreo, txtMatricula, txtPassword, txtConfirmPass;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        txtNombre = findViewById(R.id.registro_txt_nombre);
        txtApellido = findViewById(R.id.registro_txt_apellido);
        txtCorreo = findViewById(R.id.registro_txt_correo);
        txtMatricula = findViewById(R.id.registro_txt_matri);
        txtPassword = findViewById(R.id.registro_txt_password);
        txtConfirmPass = findViewById(R.id.registro_txt_confirmpass);
        btnRegistrar = findViewById(R.id.registro_btn_registrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicRegistrar();
            }
        });
        btnBackLog = findViewById(R.id.registro_btn_back);
        btnBackLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicBack();
            }
        });
    }

    private void clicRegistrar() {
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String correo = txtCorreo.getText().toString();
        String matricula = txtMatricula.getText().toString();
        String password = txtPassword.getText().toString();
        String passwordConf = txtConfirmPass.getText().toString();
        if (correo.equals("") || password.equals("") || apellido.equals("") || nombre.equals("") || matricula.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_layout, null);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            TextView title = view.findViewById(R.id.title);
            title.setText("Ocurrió un error");
            TextView message = view.findViewById(R.id.message);
            message.setText("Faltan campos por llenar");
            Button button = view.findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        } else {
            if (Pattern.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", nombre)) {
                if (Pattern.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", apellido)) {
                    if (Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d:,.!?@°]+$", password)) {
                        if (Pattern.matches("^[a-zA-Z0-9._%+-]+@itsoeh\\.edu\\.mx\\s*$", correo)) {
                            if(password.length() >= 8) {
                                if (password.equals(passwordConf)) {
                                    try {
                                        if (isConnectedToInternet()) {
                                            AEstudiante g = new AEstudiante(this);
                                            MEstudiante m = new MEstudiante();
                                            m.setNombre(txtNombre.getText().toString());
                                            m.setApellido(txtApellido.getText().toString());
                                            m.setCorreo(txtCorreo.getText().toString());
                                            m.setMatricula(txtMatricula.getText().toString());
                                            m.setPassword(password);
                                            g.guardar(m);
                                            RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(this).getRequestQueue();
                                            StringRequest solicitud = new StringRequest(Request.Method.POST, Appi.GUARDAR, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject respuesta = new JSONObject(response);
                                                        Toast.makeText(Registrar.this, "Se realizó correctamente el registro", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(Registrar.this, Login.class);
                                                        startActivity(intent);
                                                    } catch (JSONException e) {
                                                        Toast.makeText(Registrar.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        Log.d(ConstraintLayoutStates.TAG, "" + e.getMessage());
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(Registrar.this, "Falló el registro" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                    Log.d(ConstraintLayoutStates.TAG, "" + error.getMessage());

                                                }
                                            }) {
                                                @Override
                                                protected Map<String, String> getParams() {
                                                    Map<String, String> parametros = new HashMap<String, String>();
                                                    parametros.put("nombre", txtNombre.getText().toString());
                                                    parametros.put("apellido", txtApellido.getText().toString());
                                                    parametros.put("correo", txtCorreo.getText().toString());
                                                    parametros.put("matricula", txtMatricula.getText().toString());
                                                    parametros.put("password", txtPassword.getText().toString());
                                                    return parametros;
                                                }
                                            };
                                            colaDeSolicitudes.add(solicitud);
                                        }else{
                                            try {
                                                AEstudiante g = new AEstudiante(this);
                                                MEstudiante m = new MEstudiante();
                                                m.setNombre(txtNombre.getText().toString());
                                                m.setApellido(txtApellido.getText().toString());
                                                m.setCorreo(txtCorreo.getText().toString());
                                                m.setMatricula(txtMatricula.getText().toString());
                                                m.setPassword(password);
                                                g.guardar(m);
                                                Toast.makeText(Registrar.this, "Se realizó correctamente el registro", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Registrar.this, Login.class);
                                                startActivity(intent);
                                            }catch(Exception error){
                                                Toast.makeText(Registrar.this, "Falló el registro" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                Log.d(ConstraintLayoutStates.TAG, "" + error.getMessage());
                                            }
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(Registrar.this, "Falló el registro" +e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.d(ConstraintLayoutStates.TAG, e.toString());
                                    }
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                    LayoutInflater inflater = this.getLayoutInflater();
                                    View view = inflater.inflate(R.layout.dialog_layout, null);
                                    builder.setView(view);

                                    AlertDialog dialog = builder.create();

                                    TextView title = view.findViewById(R.id.title);
                                    title.setText("Ocurrió un error");
                                    TextView message = view.findViewById(R.id.message);
                                    message.setText("Las contraseñas no coinciden");
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
                                message.setText("La contraseña debe tener más de 8 carácteres");
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
                            title.setText("El correo no pertenece al dominio itsoeh");
                            TextView message = view.findViewById(R.id.message);
                            message.setText("Por favor utiliza un correo de dominio itsoeh");
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
                        message.setText("La contraseña debe contener letras y números");
                        Button button = view.findViewById(R.id.button);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_layout, null);
                    builder.setView(view);

                    AlertDialog dialog = builder.create();

                    TextView title = view.findViewById(R.id.title);
                    title.setText("Ocurrió un error");
                    TextView message = view.findViewById(R.id.message);
                    message.setText("El formato de apellidos es incorrecto, solo debe contener letras");
                    Button button = view.findViewById(R.id.button);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_layout, null);
                builder.setView(view);

                AlertDialog dialog = builder.create();

                TextView title = view.findViewById(R.id.title);
                title.setText("Ocurrió un error");
                TextView message = view.findViewById(R.id.message);
                message.setText("El formato del nombre es incorrecto, solo debe contener letras");
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
    private void clicBack() {
        Intent brinco = new Intent(this, Login.class);
        startActivity(brinco);
    }
    public boolean isConnectedToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
