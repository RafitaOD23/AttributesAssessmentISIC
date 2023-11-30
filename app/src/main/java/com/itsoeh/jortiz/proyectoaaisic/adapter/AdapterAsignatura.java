package com.itsoeh.jortiz.proyectoaaisic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jortiz.proyectoaaisic.Calificaciones_Atributos;
import com.itsoeh.jortiz.proyectoaaisic.R;
import com.itsoeh.jortiz.proyectoaaisic.models.MAsignatura;

import java.util.List;

public class AdapterAsignatura extends RecyclerView.Adapter<AdapterAsignatura.ViewHolder> {
    private List<MAsignatura> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    public AdapterAsignatura(List<MAsignatura> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public AdapterAsignatura.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.item_asignatura, null);
        return new AdapterAsignatura.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterAsignatura.ViewHolder holder,final int position){
        holder.bindData(mData.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Calificaciones_Atributos.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    public void setItems(List<MAsignatura> items){
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView asi_view;
        TextView asignatura, claveAsignatura, nombreDocente, apellidosDocente;

        ViewHolder(View itemView){
            super(itemView);
            asi_view = itemView.findViewById(R.id.itemasig_img_asig);
            asignatura = itemView.findViewById(R.id.itemasig_txt_asignatura);
            claveAsignatura = itemView.findViewById(R.id.itemasig_txt_clavegrupo);
            nombreDocente = itemView.findViewById(R.id.itemasig_txt_docente);
        }

        void bindData(final MAsignatura item){
            asignatura.setText(item.getAsignatura());
            claveAsignatura.setText(item.getClaveAsignatura());
            nombreDocente.setText(item.getNombreDocente());
        }
    }
}
