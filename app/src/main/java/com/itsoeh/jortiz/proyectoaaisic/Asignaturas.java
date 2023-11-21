package com.itsoeh.jortiz.proyectoaaisic;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.itsoeh.jortiz.proyectoaaisic.models.MListaAsignaturas;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Asignaturas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Asignaturas extends Fragment {
    private NavController nav;
    private ImageView btn_aggCur;
    private List<MListaAsignaturas> elements;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Asignaturas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment asignaturas.
     */
    // TODO: Rename and change types and number of parameters
    public static Asignaturas newInstance(String param1, String param2) {
        Asignaturas fragment = new Asignaturas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String nombreAsignatura = data.getStringExtra("asignatura");
                String nombreDocente = data.getStringExtra("idDocente");
                String claveAsignatura = data.getStringExtra("claveAsignatura");

                elements.add(new MListaAsignaturas(nombreAsignatura, nombreDocente, claveAsignatura));
                // Aquí debes notificar a tu adaptador que los datos han cambiado
            }
        }
    }
}
