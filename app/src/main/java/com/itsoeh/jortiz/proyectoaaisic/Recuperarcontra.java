package com.itsoeh.jortiz.proyectoaaisic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Recuperarcontra extends AppCompatActivity {
    private EditText txtCorreo;
    private Button btnBackLog2, btnEnviar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarcontra);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        txtCorreo = findViewById(R.id.recPass_edittext_email);
        btnEnviar = findViewById(R.id.recPass_btn_register);
        btnBackLog2 = findViewById(R.id.recPass__btn_login_back2);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicEnviar();
            }
        });
        btnBackLog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicBack2();
            }
        });
    }

    private void clicEnviar() {
        String correo = "atributosegresoitsoeh5@gmail.com";
        String contraseña = "dgxs hsaj lyjn iruv";
        String caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder nuevaContraseña = new StringBuilder();
        Random rnd = new Random();
        while (nuevaContraseña.length() < 8) {
            int index = (int) (rnd.nextFloat() * caracteresPermitidos.length());
            nuevaContraseña.append(caracteresPermitidos.charAt(index));
        }
        final String contraseniaGenerada = nuevaContraseña.toString();
        if (Pattern.matches("^[a-zA-Z0-9._%+-]+@itsoeh\\.edu\\.mx\\s*$", txtCorreo.getText().toString())) {

            if (isConnectedToInternet()) {
                // Crear una solicitud a la API para verificar el correo
                RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(Recuperarcontra.this).getRequestQueue();
                StringRequest solicitudVerificarCorreo = new StringRequest(Request.Method.POST, Appi.VERIFICAR_CORREO, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respuesta = new JSONObject(response);
                            if (!respuesta.getBoolean("error")) {
                                // El correo existe en la base de datos
                                // Crear una solicitud a la API para actualizar la contraseña
                                StringRequest solicitudActualizarPassword = new StringRequest(Request.Method.POST, Appi.ACTUALIZAR_PASSWORD_SIN_VERI, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject respuesta = new JSONObject(response);
                                            if (!respuesta.getBoolean("error")) {
                                                actuPassLocal(contraseniaGenerada);
                                                new EnviarCorreoAsyncTask(Recuperarcontra.this, correo, contraseña, txtCorreo.getText().toString(),
                                                        "Recuperación de contraseña", "tu nueva contraseña es: " + contraseniaGenerada).execute();
                                            } else {
                                                alertDialogError2();
                                            }
                                        } catch (JSONException e) {
                                            // Manejar el error
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // Manejar el error
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> parametros = new HashMap<String, String>();
                                        parametros.put("correo", txtCorreo.getText().toString());
                                        parametros.put("passwordNuevo", contraseniaGenerada);
                                        return parametros;
                                    }
                                };
                                colaDeSolicitudes.add(solicitudActualizarPassword);
                            } else {
                                alertDialogError();
                            }
                        } catch (JSONException e) {
                            // Manejar el error
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        alertDialogError();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("correo", txtCorreo.getText().toString());
                        return parametros;
                    }
                };
                colaDeSolicitudes.add(solicitudVerificarCorreo);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_layout, null);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                TextView title = view.findViewById(R.id.title);
                title.setText("Ocurrió un error");
                ImageView image = view.findViewById(R.id.imageDialog);
                image.setImageResource(R.drawable.check);
                TextView message = view.findViewById(R.id.message);
                message.setText("Debes tener conexión a internet para poder realizar la recuperación de tu contraseña");
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
            message.setText("El correo electrónico no pertenece al dominio itsoeh o no esta registrado");
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

    private void actuPassLocal(String password) {
        AEstudiante aEstudiante = new AEstudiante(this);
        aEstudiante.actualizarPasswordSinVerificarEstudiante(txtCorreo.getText().toString(), password);
    }


    private class EnviarCorreoAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog progressDialog;
        private Context context;
        private String usuario;
        private String pass;
        private String destinatario;
        private String asunto;
        private String mensaje;

        public EnviarCorreoAsyncTask(Context context, String usuario, String pass, String destinatario, String asunto, String mensaje) {
            this.context = context;
            this.usuario = usuario;
            this.pass = pass;
            this.destinatario = destinatario;
            this.asunto = asunto;
            this.mensaje = mensaje;
            this.progressDialog = new ProgressDialog(context);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication(){
                            return new PasswordAuthentication(usuario, pass);
                        }
                    });
            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(usuario));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(destinatario));
                message.setSubject(asunto);
                message.setText(mensaje);
                Transport.send(message);
                return true;
            }catch(MessagingException e){
                //Toast.makeText(context, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        @Override
        protected void onPreExecute(){
            progressDialog.setTitle("Progreso");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Enviando correo...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected void onPostExecute(Boolean result){
            progressDialog.dismiss();
            if (result) {
                alertDialog();
            }else{
                Toast.makeText(context, "Existió una falla al enviar la nueva contraseña a correo dado", Toast.LENGTH_SHORT).show();
            }

        }


    }

    private void clicBack2() {
        Intent brinco = new Intent(this, Login.class);
        startActivity(brinco);
    }
    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        TextView title = view.findViewById(R.id.title);
        title.setText("Contraseña enviada");
        ImageView image = view.findViewById(R.id.imageDialog);
        image.setImageResource(R.drawable.check);
        TextView message = view.findViewById(R.id.message);
        message.setText("Se envió una contraseña nueva a tu correo electrónico, si no aparece, revisa el spam");
        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void alertDialogError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        TextView title = view.findViewById(R.id.title);
        title.setText("Ocurrió un error");
        TextView message = view.findViewById(R.id.message);
        message.setText("No existe una cuenta de Attributes Assesment registrada con el correo ingresado");
        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void alertDialogError2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        TextView title = view.findViewById(R.id.title);
        title.setText("Ocurrió un error");
        TextView message = view.findViewById(R.id.message);
        message.setText("Ocurrió un error al actualizar la contraseña");
        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public boolean isConnectedToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}