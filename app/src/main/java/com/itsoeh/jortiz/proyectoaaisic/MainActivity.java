package com.itsoeh.jortiz.proyectoaaisic;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.itsoeh.jortiz.proyectoaaisic.models.MListaAsignaturas;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView
                = findViewById(R.id.bottom_navigation);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
    Home home = new Home();
    Configuracion configuracion = new Configuracion();
    Notificacion notificacion = new Notificacion();
    Asignaturas asignaturas = new Asignaturas();

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, home)
                        .commit();
                return true;

            case R.id.configuraciones:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, configuracion)
                        .commit();
                return true;
            case R.id.notificaciones:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, notificacion)
                        .commit();
                return true;

                case R.id.asignaturas:
                getSupportFragmentManager()
                       .beginTransaction()
                       .replace(R.id.fragmentContainerView, asignaturas)
                       .commit();
                return true;
        }
        return false;
    }


}
