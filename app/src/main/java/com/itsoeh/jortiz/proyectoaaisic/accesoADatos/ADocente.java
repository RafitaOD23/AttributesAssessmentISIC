package com.itsoeh.jortiz.proyectoaaisic.accesoADatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.itsoeh.jortiz.proyectoaaisic.accesoADatos.AccesoADatos;
import com.itsoeh.jortiz.proyectoaaisic.models.MDocente;


import java.util.ArrayList;

public class ADocente extends AccesoADatos {
    public ADocente(@Nullable Context context) {
        super(context);
    }

    public void guardar(MDocente x){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("INSERT INTO docente VALUES (NULL ,"+
                "'"+x.getNombre()+"',"+
                "'"+x.getApellidos()+"',"+
                "'"+x.getCorreo()+"',"+
                "'"+x.getMatricula()+"',"+
                "'"+x.getPassword()+"')"
        );
    }
    public ArrayList<MDocente> listar(){
        ArrayList<MDocente> lista=new ArrayList<MDocente>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor reg=db.rawQuery("SELECT * FROM docente ",null);
        while(reg.moveToNext()){
            MDocente x=new MDocente();
            x.setIdDocente(reg.getInt(0));
            x.setNombre(reg.getString(1));
            x.setApellidos(reg.getString(2));
            x.setCorreo(reg.getString(3));
            x.setMatricula(reg.getString(4));
            x.setPassword(reg.getString(5));
            lista.add(x);
        }
        return lista;
    }

    public String[] buscarContrasenia(String correo, String contraseña){
        String datos[] = new String[2];
        SQLiteDatabase db = getReadableDatabase();
        Cursor reg = db.rawQuery("SELECT nombre, password FROM docente WHERE correo = '"+correo+"' AND password = '"+contraseña+"'",null);
        if (reg.getCount()!=0){
            reg.moveToNext();
            datos[0] = reg.getString(0);
            datos[1] = reg.getString(1);
            return datos;
        }else
            return null;
    }
}