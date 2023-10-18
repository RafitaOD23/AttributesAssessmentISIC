package com.itsoeh.jortiz.proyectoaaisic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Recuperarcontra extends AppCompatActivity {
private Button btnBackLog2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarcontra);
        btnBackLog2 = findViewById(R.id.btn_login_back2);
        btnBackLog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicBack2();
            }
        });
    }

    private void clicBack2() {
        Intent brinco = new Intent(this, Login.class);
        startActivity(brinco);
    }

}