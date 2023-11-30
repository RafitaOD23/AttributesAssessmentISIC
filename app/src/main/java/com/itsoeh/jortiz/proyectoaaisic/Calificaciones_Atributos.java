package com.itsoeh.jortiz.proyectoaaisic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Calificaciones_Atributos extends AppCompatActivity {

    private Button btn_back_asi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificaciones_atributos);

        btn_back_asi = findViewById(R.id.chapass_btn_back);

        btn_back_asi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}