package com.itsoeh.jortiz.proyectoaaisic.models;

public class MAsignatura {
    private int idAsignatura;
    private String asignatura;
    private String claveAsignatura;
    private String claveGrupo;
    private String codigoAsignatura;
    private int idDocente;

    public MAsignatura() {
    }

    public MAsignatura(int idAsignatura, String asignatura, String claveAsignatura, String claveGrupo, String codigoAsignatura, int idDocente) {
        this.idAsignatura = idAsignatura;
        this.asignatura = asignatura;
        this.claveAsignatura = claveAsignatura;
        this.claveGrupo = claveGrupo;
        this.codigoAsignatura = codigoAsignatura;
        this.idDocente = idDocente;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
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

    public String getClaveGrupo() {
        return claveGrupo;
    }

    public void setClaveGrupo(String claveGrupo) {
        this.claveGrupo = claveGrupo;
    }

    public String getCodigoAsignatura() {
        return codigoAsignatura;
    }

    public void setCodigoAsignatura(String codigoAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }


    @Override
    public String toString() {
        return "MAsignatura{" +
                "idAsignatura=" + idAsignatura +
                ", asignatura='" + asignatura + '\'' +
                ", claveAsignatura='" + claveAsignatura + '\'' +
                ", claveGrupo='" + claveGrupo + '\'' +
                ", codigoAsignatura='" + codigoAsignatura + '\'' +
                ", idDocente=" + idDocente +
                '}';
    }
}
