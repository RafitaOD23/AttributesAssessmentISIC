package com.itsoeh.jortiz.proyectoaaisic.models;

public class MAtributo {
    private int id;
    private int logro;
    private int meta;
    private int idAsignatura;

    public MAtributo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogro() {
        return logro;
    }

    public void setLogro(int logro) {
        this.logro = logro;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    @Override
    public String toString() {
        return "MAtributo{" +
                "id=" + id +
                ", logro=" + logro +
                ", meta=" + meta +
                ", idAsignatura=" + idAsignatura +
                '}';
    }
}
