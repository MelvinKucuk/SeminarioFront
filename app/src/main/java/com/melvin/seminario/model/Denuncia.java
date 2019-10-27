package com.melvin.seminario.model;

public class Denuncia {
    private String direccion;
    private String fecha;
    private String hora;
    private Conductor asegurado;
    private Conductor tercero;
    private String imagePathPoliza;
    private String imagePathCedula;
    private String[] imagePathsLicencia;
    private String[] imagePathsChoque;
    private String[] imagePathsExtras;

    public Denuncia() {
    }

    public String getDireccion() {
        return direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public Conductor getAsegurado() {
        return asegurado;
    }

    public Conductor getTercero() {
        return tercero;
    }

    public String getImagePathPoliza() {
        return imagePathPoliza;
    }

    public String getImagePathCedula() {
        return imagePathCedula;
    }

    public String[] getImagePathsLicencia() {
        return imagePathsLicencia;
    }

    public String[] getImagePathsChoque() {
        return imagePathsChoque;
    }

    public String[] getImagePathsExtras() {
        return imagePathsExtras;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setAsegurado(Conductor asegurado) {
        this.asegurado = asegurado;
    }

    public void setTercero(Conductor tercero) {
        this.tercero = tercero;
    }

    public void setImagePathPoliza(String imagePathPoliza) {
        this.imagePathPoliza = imagePathPoliza;
    }

    public void setImagePathCedula(String imagePathCedula) {
        this.imagePathCedula = imagePathCedula;
    }

    public void setImagePathsLicencia(String[] imagePathsLicencia) {
        this.imagePathsLicencia = imagePathsLicencia;
    }

    public void setImagePathsChoque(String[] imagePathsChoque) {
        this.imagePathsChoque = imagePathsChoque;
    }

    public void setImagePathsExtras(String[] imagePathsExtras) {
        this.imagePathsExtras = imagePathsExtras;
    }
}
