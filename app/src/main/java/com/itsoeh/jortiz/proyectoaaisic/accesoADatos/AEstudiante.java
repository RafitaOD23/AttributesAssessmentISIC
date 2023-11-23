package com.itsoeh.jortiz.proyectoaaisic.accesoADatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.itsoeh.jortiz.proyectoaaisic.models.MEstudiante;

import java.util.ArrayList;

public class AEstudiante extends AccesoADatos {
    public AEstudiante(@Nullable Context context) {
        super(context);
    }

    public void guardar(MEstudiante x){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("INSERT INTO estudiante VALUES (NULL ,"+
                "'"+x.getNombre()+"',"+
                "'"+x.getApellido()+"',"+
                "'"+x.getCorreo()+"',"+
                "'"+x.getMatricula()+"',"+
                "'"+x.getPassword()+"')"
        );
    }
    public ArrayList<MEstudiante> listar(){
        ArrayList<MEstudiante> lista=new ArrayList<MEstudiante>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor reg=db.rawQuery("SELECT * FROM estudiante ",null);
        while(reg.moveToNext()){
            MEstudiante x=new MEstudiante();
            x.setIdEstudiante(reg.getInt(0));
            x.setNombre(reg.getString(1));
            x.setApellido(reg.getString(2));
            x.setCorreo(reg.getString(3));
            x.setMatricula(reg.getString(4));
            x.setPassword(reg.getString(5));
            lista.add(x);
        }
        return lista;
    }

    public String[] buscarContrasenia(String correo, String contraseña) {
        String datos[] = new String[2];
        SQLiteDatabase db = getReadableDatabase();
        Cursor reg = db.rawQuery("SELECT nombre, password FROM estudiante WHERE correo = '" + correo + "' AND password = '" + contraseña + "'", null);
        if (reg.getCount() != 0) {
            reg.moveToNext();
            datos[0] = reg.getString(0);
            datos[1] = reg.getString(1);
            return datos;
        } else
            return null;
    }

    public boolean actualizarCorreo(String correoAntiguo, String correoNuevo) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor reg = db.rawQuery("SELECT * FROM estudiante WHERE correo = '" + correoAntiguo + "'", null);
        if (reg.getCount() != 0) {
            db.execSQL("UPDATE estudiante SET correo = '" + correoNuevo + "' WHERE correo = '" + correoAntiguo + "'");
            return true;
        } else {
            return false;
        }
    }

    public boolean actualizarNombre(int idEstudiante, String nombreNuevo, String apellidoNuevo) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor reg = db.rawQuery("SELECT * FROM estudiante WHERE idEstudiante = " + idEstudiante, null);
        if (reg.getCount() != 0) {
            db.execSQL("UPDATE estudiante SET nombre = '" + nombreNuevo + "', apellido = '" + apellidoNuevo + "' WHERE idEstudiante = " + idEstudiante);
            return true;
        } else {
            return false;
        }
    }

    public int obtenerIdEstudiante(String correo) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor reg = db.rawQuery("SELECT idEstudiante FROM estudiante WHERE correo = '" + correo + "'", null);
        if (reg.moveToFirst()) {
            return reg.getInt(0);
        } else {
            return -1;
        }
    }

    public boolean actualizarContrasenia(int idEstudiante, String contraseniaAntiguo, String contraseniaNuevo) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor reg = db.rawQuery("SELECT * FROM estudiante WHERE idEstudiante = " + idEstudiante + " AND matricula = '" + contraseniaAntiguo + "'", null);
        if (reg.getCount() != 0) {
            db.execSQL("UPDATE estudiante SET matricula = '" + contraseniaNuevo + "' WHERE idEstudiante = " + idEstudiante);
            return true;
        } else {
            return false;
        }
    }
}