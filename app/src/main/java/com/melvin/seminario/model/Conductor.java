package com.melvin.seminario.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Conductor implements Parcelable {

    private String nombre;
    private String apellido;
    private String licencia;
    private String fechaNacimiento;
    private String pais;

    public Conductor(String nombre, String apellido, String licencia, String fechaNacimiento, String pais) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.licencia = licencia;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
    }

    public static class Builder{
        private String nombre;
        private String apellido;
        private String licencia;
        private String fechaNacimiento;
        private String pais;

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

        public Conductor build(){
            return new Conductor(nombre, apellido, licencia, fechaNacimiento, pais);
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
}
