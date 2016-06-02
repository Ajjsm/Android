package com.example.juanaj.albedroid.comentarios;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.juanaj.albedroid.R;

import java.util.List;

/**
 * Created by JuanAJ on 21/01/2016.
 */
public class ComentarioAdapter extends ArrayAdapter<BaseComentarios> {
    private Context contexto;
    private int layoutId;
    private List<BaseComentarios> datos;

    public ComentarioAdapter(Context contexto, int layoutId, List<BaseComentarios> datos) {
        super(contexto, layoutId, datos);
        this.contexto = contexto;
        this.layoutId = layoutId;
        this.datos = datos;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup padre) {
        View fila = view;
        ItemComentario item = null;
        if (fila == null) {
            LayoutInflater inflater = ((Activity) contexto).getLayoutInflater();
            fila = inflater.inflate(layoutId, padre, false);
            item = new ItemComentario();
            item.nombre = (TextView) fila.findViewById(R.id.tvnombrecomentario);
            item.comentario = (TextView) fila.findViewById(R.id.tvcomentariocomentairo);
            item.valoracion = (TextView) fila.findViewById(R.id.tvvaloracioncomentario);
            fila.setTag(item);
        }
        else {
            item = (ItemComentario) fila.getTag();
        }
        BaseComentarios comentario = datos.get(posicion);
        item.nombre.setText(comentario.getNombre());
        item.comentario.setText(comentario.getComentario());
        item.valoracion.setText(String.valueOf(comentario.getValoracion()));
        return fila;
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public BaseComentarios getItem(int posicion) {
        return datos.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }
    static class ItemComentario {
        TextView nombre;
        TextView comentario;
        TextView valoracion;
    }
}
