package com.itsoeh.jortiz.proyectoaaisic.models;

public class MListaAsignaturas {
    private String nombreA;
    private String docenteA;
    private String claveA;

    public MListaAsignaturas(String nombreA, String docenteA, String claveA) {
        this.nombreA = nombreA;
        this.docenteA = docenteA;
        this.claveA = claveA;
    }

    public String getNombreA() {
        return nombreA;
    }

    public String getDocenteA() {
        return docenteA;
    }

    public String getClaveA() {
        return claveA;
    }

    public void setNombreA(String nombreA) {
        this.nombreA = nombreA;
    }

    public void setDocenteA(String docenteA) {
        this.docenteA = docenteA;
    }

    public void setClaveA(String claveA) {
        this.claveA = claveA;
    }
}
