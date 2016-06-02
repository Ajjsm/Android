package com.example.juanaj.albedroid.bbdd;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JuanAJ on 17/01/2016.
 */
public class Puntos implements Parcelable {
    private String nombre;
    private String calle;
    private String descripcion;
    private String ciudad;


    public Puntos(){}

    public Puntos(String nombre, String calle, String descripcion, String ciudad) {
        this.nombre = nombre;
        this.calle = calle;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
    }

    public static final Creator<Puntos> CREATOR = new Creator<Puntos>() {
        @Override
        public Puntos createFromParcel(Parcel in) {
            return new Puntos(in);
        }

        @Override
        public Puntos[] newArray(int size) {
            return new Puntos[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(calle);
        dest.writeString(descripcion);
        dest.writeString(ciudad);
    }

    private Puntos(Parcel entrada) {
        nombre = entrada.readString();
        calle = entrada.readString();
        descripcion = entrada.readString();
        ciudad = entrada.readString();
    }

}
