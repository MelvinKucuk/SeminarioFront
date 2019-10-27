package com.melvin.seminario.model;

public class Foto {
    private String filepath;
    private String nombre;

    public Foto(String filepath, String nombre) {
        this.filepath = filepath;
        this.nombre = nombre;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
