package com.itsoeh.jortiz.proyectoaaisic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CambiarNombre extends AppCompatActivity {

    private Button btn_login_back2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_nombre);

        btn_login_back2 = findViewById(R.id.chaname_btn_back);
        btn_login_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}