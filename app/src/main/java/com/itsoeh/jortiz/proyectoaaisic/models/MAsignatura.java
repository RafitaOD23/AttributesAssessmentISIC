package com.itsoeh.jortiz.proyectoaaisic.models;

public class MAsignatura {
    private String asignatura;
    private String claveAsignatura;
    private String nombreDocente;


    public MAsignatura(String asignatura, String claveAsignatura, String nombreDocente) {
        this.asignatura = asignatura;
        this.claveAsignatura = claveAsignatura;
        this.nombreDocente = nombreDocente;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getClaveAsignatura() {
        return claveAsignatura;
    }

    public void setClaveAsignatura(String claveAsignatura) {
        this.claveAsignatura = claveAsignatura;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }
}
