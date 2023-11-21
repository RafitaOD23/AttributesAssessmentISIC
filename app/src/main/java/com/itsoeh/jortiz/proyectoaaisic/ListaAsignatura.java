package com.itsoeh.jortiz.proyectoaaisic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.jortiz.proyectoaaisic.models.MListaAsignaturas;

import java.util.List;

public class ListaAsignatura extends RecyclerView.Adapter<ListaAsignatura.ViewHolder> {
    private List<MListaAsignaturas> mData;
    private LayoutInflater mInflater;
    private Context context;
    private buscarAsignaturas.OnAsignaturaSelectedListener listener;

    public ListaAsignatura(List<MListaAsignaturas> itemList, Context context, buscarAsignaturas.OnAsignaturaSelectedListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {return mData.size();}

    @Override
    public ListaAsignatura.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.item_asignatura, null);
        return new ListaAsignatura.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListaAsignatura.ViewHolder holder,final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<MListaAsignaturas> items){mData=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreA, docenteA, claveA;
        ImageView imageView;

        ViewHolder(View itemview) {
            super(itemview);
            nombreA = itemview.findViewById(R.id.asi_txt_nom);
            docenteA = itemview.findViewById(R.id.asi_txt_doc);
            claveA = itemview.findViewById(R.id.asi_txt_cod);
            imageView = itemview.findViewById(R.id.itemasi_agg);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        MListaAsignaturas asignatura = mData.get(getAdapterPosition());
                        listener.onAsignaturaSelected(asignatura);
                    }
                }
            });
        }

        void bindData(final MListaAsignaturas item){
            nombreA.setText(item.getNombreA());
            docenteA.setText(item.getDocenteA());
            claveA.setText(item.getClaveA());
        }
    }
}
