package com.melvin.seminario.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Conductor implements Parcelable {

    private String nombre;
    private String apellido;
    private String licencia;
    private String fechaNacimiento;
    private String pais;
    private String dni;
    private String detalle;
    private String email;
    private String domicilio;

    public Conductor(String nombre, String apellido, String licencia, String fechaNacimiento, String pais, String dni, String detalle, String email, String domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.licencia = licencia;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
        this.dni = dni;
        this.detalle = detalle;
        this.email = email;
        this.domicilio = domicilio;
    }

    public static class Builder{
        private String nombre;
        private String apellido;
        private String licencia;
        private String fechaNacimiento;
        private String pais;
        private String dni;
        private String detalle;
        private String email;
        private String domicilio;

        public Builder(){}

        public Builder setNombre(String nombre){
            this.nombre = nombre;
            return this;
        }

        public Builder setApellido(String apellido){
            this.apellido = apellido;
            return this;
        }

        public Builder setLicencia(String licencia){
            this.licencia = licencia;
            return this;
        }

        public Builder setFechaNacimiento(String fechaNacimiento){
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public Builder setPais(String pais){
            this.pais = pais;
            return this;
        }

        public Builder setDni(String dni){
            this.dni = dni;
            return this;
        }

        public Builder setDetalle(String detalle){
            this.detalle = detalle;
            return this;
        }

        public Builder setEmail(String email){
            this.email = email;
            return this;
        }

        public Builder setDomicilio(String domicilio){
            this.domicilio = domicilio;
            return this;
        }

        public Conductor build(){
            return new Conductor(nombre, apellido, licencia, fechaNacimiento, pais, dni, detalle, email, domicilio);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.apellido);
        dest.writeString(this.fechaNacimiento);
        dest.writeString(this.licencia);
        dest.writeString(this.pais);
    }

    public Conductor(Parcel in){
        this.nombre = in.readString();
        this.apellido = in.readString();
        this.fechaNacimiento = in.readString();
        this.licencia = in.readString();
        this.pais = in.readString();
    }

    public static final Creator<Conductor> CREATOR = new Creator<Conductor>() {
        @Override
        public Conductor createFromParcel(Parcel source) {
            return new Conductor(source);
        }

        @Override
        public Conductor[] newArray(int size) {
            return new Conductor[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getLicencia() {
        return licencia;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getPais() {
        return pais;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getEmail() {
        return email;
    }
}
