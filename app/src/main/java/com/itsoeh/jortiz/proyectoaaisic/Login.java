package com.itsoeh.jortiz.proyectoaaisic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.ADocente;

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
            title.setText("Ocurrió un error");
            TextView message = view.findViewById(R.id.message);
            message.setText("Por favor asegurese de haber llenado todos los datos requeridos");
            Button button = view.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }else{
            ADocente docente = new ADocente(this);
            String [] datos = docente.buscarContrasenia(correo, contraseña);
            try {
                if (datos != null) {
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
                    message.setText("Por favor asegurese de haber llenado todos los datos correctamente");
                    Button button = view.findViewById(R.id.button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            } catch (Exception e) {
                AlertDialog.Builder aviso = new AlertDialog.Builder(this);
                aviso.setTitle("Error al ingresar")
                        .setMessage("Ocurrió un error desconocido")
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        }
    }
}