package com.itsoeh.jortiz.proyectoaaisic;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jortiz.proyectoaaisic.models.MListaAsignaturas;

import java.util.ArrayList;
import java.util.List;

public class Asignaturas extends Fragment {

    private ImageView btn_aggCur;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asignaturas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_aggCur = view.findViewById(R.id.agg_btn_cur);
        btn_aggCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAgregarCurso();
            }
        });
    }

    private void clickAgregarCurso() {
        Intent intent = new Intent(getActivity(), buscarAsignaturas.class);
        startActivityForResult(intent, 1);
    }
}
