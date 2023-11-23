package com.itsoeh.jortiz.proyectoaaisic.accesoADatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AccesoADatos extends SQLiteOpenHelper {


    public AccesoADatos(@Nullable Context context) {
        super(context, "atributosEgre", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS docente("+
                "idDocente INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "apellidos TEXT," +
                "correo TEXT," +
                "matricula INTEGER UNIQUE," +
                "password TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS asignatura("+
                "idAsignaturas INTEGER PRIMARY KEY AUTOINCREMENT," +
                "asignatura TEXT," +
                "claveAsignatura TEXT," +
                "claveGrupo TEXT," +
                "codigoAsignatura TEXT UNIQUE," +
                "idDocente INTEGER,"+
                "FOREIGN KEY (idDocente) REFERENCES docente(idDocente))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS atributoE("+
                "idAtributoE INTEGER PRIMARY KEY AUTOINCREMENT," +
                "logro INTEGER," +
                "meta INTEGER," +
                "idAsignaturas INTEGER,"+
                "FOREIGN KEY (idAsignaturas) REFERENCES asignatura(idAsignaturas))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS estudiante("+
                "idEstudiante INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "apellido TEXT," +
                "correo TEXT,"+
                "matricula INTEGER UNIQUE," +
                "password TEXT)");


        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS estudiante_asignatura("+
                "idEstudiante INTEGER," +
                "codigoAsignatura TEXT," +
                "FOREIGN KEY (idEstudiante) REFERENCES estudiante(idEstudiante)," +
                "FOREIGN KEY (codigoAsignatura) REFERENCES asignatura(codigoAsignatura))");


        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS criteriosEval("+
                "idCriterio INTEGER PRIMARY KEY AUTOINCREMENT," +
                "indicadorEspecifico TEXT," +
                "nivel TEXT," +
                "idAtributoE INTEGER,"+
                "puntos INTEGER,"+
                "FOREIGN KEY (idAtributoE) REFERENCES atributoE(idAtributoE))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS calificacion("+
                "idCalificacion INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idEstudiante INTEGER," +
                "idCriterio INTEGER," +
                "calificacion INTEGER,"+
                "FOREIGN KEY (idEstudiante) REFERENCES estudiante(idEstudiante)," +
                "FOREIGN KEY (idCriterio) REFERENCES criteriosEval(idCriterio))");

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
