package com.itsoeh.jortiz.proyectoaaisic;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    BuscarCurso buscarCurso = new BuscarCurso();


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

            case R.id.buscarcursos:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, buscarCurso)
                        .commit();
                return true;

        }
        return false;
    }
}
