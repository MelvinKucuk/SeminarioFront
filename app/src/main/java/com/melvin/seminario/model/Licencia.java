package com.melvin.seminario.model;

public class Licencia {
    private String nombre;
    private String apellido;
    private String licencia;
    private String fechaNacimiento;
    private String pais;

    public Licencia(String nombre, String apellido, String licencia, String fechaNacimiento, String pais) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.licencia = licencia;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
