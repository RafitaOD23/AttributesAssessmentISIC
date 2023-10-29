package com.itsoeh.jortiz.proyectoaaisic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.itsoeh.jortiz.proyectoaaisic.models.MListaAsignaturas;

import java.util.ArrayList;
import java.util.List;

public class buscarAsignaturas extends AppCompatActivity {

    List<MListaAsignaturas> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_asignaturas);
        init();
    }

    public void init(){
        elements = new ArrayList<>();
        elements.add(new MListaAsignaturas("Lenguajes y automatas II", "Aline Perez Martinez", "107A"));
        elements.add(new MListaAsignaturas("Conmutacion y enrutamiento de redes de datos", "Cristy Elizabeth Aguilar Ojeda", "207A"));
        elements.add(new MListaAsignaturas("Inteligencia artificial", "Jorge Armando Garcia Bautista", "307A"));
        elements.add(new MListaAsignaturas("Sistemas programables", "Erendida Alvarado Calva", "507A"));
        elements.add(new MListaAsignaturas("Introduccion al desarrollo UI/UX", "Lorena Mendoza Guzman", "78AA"));
        elements.add(new MListaAsignaturas("Aplicaciones nativas de codigo abierto", "Mario Perez Bautista", "78BA"));
        elements.add(new MListaAsignaturas("Tutoria VII", "Mario Perez Bautista", "T07A"));
        elements.add(new MListaAsignaturas("Ingles VII", "Eduardo Daniel Montufar ", "X07A"));

        ListaAsignatura listaAsignatura = new ListaAsignatura(elements, this);
        RecyclerView recyclerView = findViewById(R.id.rec_listasinaturas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        int espaciado = getResources().getDimensionPixelSize(R.dimen.space_element);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = espaciado;
                outRect.right = espaciado;
                outRect.top = espaciado;
                outRect.bottom = espaciado;
            }
        });

        recyclerView.setAdapter(listaAsignatura);
    }
}