package com.melvin.seminario.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Denuncia {
    @SerializedName("_id")
    private String id;
    private String calle;
    private String altura;
    private String fecha;
    private String hora;
    private Conductor asegurado;
    private Conductor tercero;
    private String imagePathPoliza;
    private String imagePathCedula;
    private List<String> imagePathsLicencia;
    private List<String> imagePathsChoque;
    private List<String> imagePathsExtras;
    private List<String> imagePathsDanos;
    private String datos;
    private Boolean esEsquina;
    private Boolean esDobleMano;

    public Denuncia() {
    }

    public String getCalle() {
        return calle;
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

    public List<String> getImagePathsLicencia() {
        return imagePathsLicencia;
    }

    public List<String> getImagePathsChoque() {
        return imagePathsChoque;
    }

    public List<String> getImagePathsExtras() {
        return imagePathsExtras;
    }

    public void setCalle(String calle) {
        this.calle = calle;
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

    public void setImagePathsLicencia(List<String> imagePathsLicencia) {
        this.imagePathsLicencia = imagePathsLicencia;
    }

    public void setImagePathsChoque(List<String> imagePathsChoque) {
        this.imagePathsChoque = imagePathsChoque;
    }

    public void setImagePathsExtras(List<String> imagePathsExtras) {
        this.imagePathsExtras = imagePathsExtras;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public boolean isEsEsquina() {
        return esEsquina;
    }

    public void setEsEsquina(boolean esEsquina) {
        this.esEsquina = esEsquina;
    }

    public boolean isEsDobleMano() {
        return esDobleMano;
    }

    public void setEsDobleMano(boolean esDobleMano) {
        this.esDobleMano = esDobleMano;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public List<String> getImagePathsDanos() {
        return imagePathsDanos;
    }

    public void setImagePathsDanos(List<String> imagePathsDanos) {
        this.imagePathsDanos = imagePathsDanos;
    }

    public boolean chechCompleto() {
        if (calle != null) {
            if (altura != null) {
                if (fecha != null) {
                    if (hora != null) {
                        if (imagePathPoliza != null) {
                            if (imagePathCedula != null) {
                                if (imagePathsChoque != null) {
                                    if (imagePathsExtras != null) {
                                        if (imagePathsLicencia != null) {
                                            if (tercero != null) {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        } else {
                                            return false;
                                        }
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }

                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
