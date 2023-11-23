package com.itsoeh.jortiz.proyectoaaisic.models;

public class MCriterio {
    private int idCriterio;
    private String indicadorEspecifico;
    private int Nivel;
    private int idAtributo;
    private int puntos;

    public MCriterio() {

    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }

    public String getIndicadorEspecifico() {
        return indicadorEspecifico;
    }

    public void setIndicadorEspecifico(String indicadorEspecifico) {
        this.indicadorEspecifico = indicadorEspecifico;
    }

    public int getNivel() {
        return Nivel;
    }

    public void setNivel(int nivel) {
        Nivel = nivel;
    }

    public int getIdAtributo() {
        return idAtributo;
    }

    public void setIdAtributo(int idAtributo) {
        this.idAtributo = idAtributo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "MCriterio{" +
                "idCriterio=" + idCriterio +
                ", indicadorEspecifico='" + indicadorEspecifico + '\'' +
                ", Nivel=" + Nivel +
                ", idAtributo=" + idAtributo +
                ", puntos=" + puntos +
                '}';
    }
}
