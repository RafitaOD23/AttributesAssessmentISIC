package com.itsoeh.jortiz.proyectoaaisic.accesoADatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;


import com.itsoeh.jortiz.proyectoaaisic.models.MAtributo;

import java.util.ArrayList;

public class AAtributo extends AccesoADatos {
    public AAtributo(@Nullable Context context) {
        super(context);
    }

    public void guardar(MAtributo x){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("INSERT INTO atributoE VALUES (NULL ,"+
                "'"+x.getLogro()+"',"+
                "'"+x.getMeta()+"',"+
                "'"+x.getIdAsignatura()+"')"
        );
    }

    public ArrayList<MAtributo> listar(){
        ArrayList<MAtributo> lista=new ArrayList<MAtributo>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor reg=db.rawQuery("SELECT * FROM atributoE ",null);
        while(reg.moveToNext()){
            MAtributo x=new MAtributo();
            x.setId(reg.getInt(0));
            x.setLogro(reg.getInt(1));
            x.setMeta(reg.getInt(2));
            x.setIdAsignatura(reg.getInt(3));
            lista.add(x);
        }
        return lista;
    }
}
