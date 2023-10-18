package com.itsoeh.jortiz.proyectoaaisic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
            AlertDialog.Builder aviso = new AlertDialog.Builder(this);
            aviso.setTitle("Correo y contraseña vacíos")
                    .setMessage("Ingresa los datos solicitados")
                    .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else{
            ADocente docente = new ADocente(this);
            String [] datos = docente.buscarContrasenia(correo, contraseña);
            try {
                if (datos != null) {
                    Intent brinco = new Intent(this, MainActivity.class);
                    startActivity(brinco);
                    Toast.makeText(Login.this, "Te has loggeado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder aviso = new AlertDialog.Builder(this);
                    aviso.setTitle("Error al ingresar")
                            .setMessage("El usuario o contraseña son incorrectos")
                            .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
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