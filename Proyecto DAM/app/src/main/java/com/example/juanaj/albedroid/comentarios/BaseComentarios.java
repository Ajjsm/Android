package com.example.juanaj.albedroid.comentarios;

/**
 * Created by JuanAJ on 21/01/2016.
 */
public class BaseComentarios {
    private int id;
    private String nombre;
    private String comentario;
    private int valoracion;

    public BaseComentarios() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
}
