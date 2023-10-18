package com.itsoeh.jortiz.proyectoaaisic.models;

public class MDocente {
    private int idDocente;
    private String nombre;
    private String apellidos;
    private String correo;
    private String matricula;
    private int celular;
    private String password;

    public MDocente() {
    }

    public MDocente(int idDocente, String nombre, String apellidos, String correo, String matricula, int celular, String password) {
        this.idDocente = idDocente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.matricula = matricula;
        this.celular = celular;
        this.password = password;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "MDocente{" +
                "idDocente=" + idDocente +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", matricula='" + matricula + '\'' +
                ", celular=" + celular +
                ", password='" + password + '\'' +
                '}';
    }
}
