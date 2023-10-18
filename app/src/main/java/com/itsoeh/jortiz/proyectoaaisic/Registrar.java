package com.itsoeh.jortiz.proyectoaaisic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.ADocente;
import com.itsoeh.jortiz.proyectoaaisic.models.MDocente;

public class Registrar extends AppCompatActivity {
    private Button btnBackLog, btnRegistrar;
    private EditText txtNombre,txtApellido,txtCorreo, txtMatricula, txtPassword, txtConfirmPass;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
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
        if (correo.equals("") || password.equals("") || apellido.equals("") || nombre.equals("") || matricula.equals("")) {
            AlertDialog.Builder aviso = new AlertDialog.Builder(this);
            aviso.setTitle("Campos vacíos")
                    .setMessage("Por favor llena todos los datos requeridos")
                    .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
        try {

                ADocente g = new ADocente(this);
                MDocente m = new MDocente();
                m.setNombre(txtNombre.getText().toString());
                m.setApellidos(txtApellido.getText().toString());
                m.setCorreo(txtCorreo.getText().toString());
                m.setMatricula(txtMatricula.getText().toString());
                m.setPassword(txtPassword.getText().toString());
                g.guardar(m);
                Toast.makeText(Registrar.this, "Se realizó correctamente el registro", Toast.LENGTH_SHORT).show();
                Intent brinco = new Intent(this, Login.class);
                startActivity(brinco);
            }catch(Exception e){
                Toast.makeText(Registrar.this, "Falló el registro", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clicBack() {
        Intent brinco = new Intent(this, Login.class);
        startActivity(brinco);
    }
}
