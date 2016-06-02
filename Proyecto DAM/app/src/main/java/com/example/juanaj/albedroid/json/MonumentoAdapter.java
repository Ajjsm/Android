package com.example.juanaj.albedroid.json;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juanaj.albedroid.R;

import java.util.List;

/**
 * Created by JuanAJ on 16/01/2016.
 */
public class MonumentoAdapter extends ArrayAdapter<Monumentos> {
    private Context context;
    private int layoutId;
    private List<Monumentos> datos;

    public MonumentoAdapter(Context context, int layoutId, List<Monumentos> datos) {
        super(context, layoutId, datos);
        this.context = context;
        this.layoutId = layoutId;
        this.datos = datos;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup padre){
        View fila = view;
        ItemAparcamiento item = null;

        if(fila == null){
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            fila = layoutInflater.inflate(layoutId, padre, false);

            item = new ItemAparcamiento();
            item.ivicono = (ImageView) fila.findViewById(R.id.ivicono);
            item.titulo = (TextView) fila.findViewById(R.id.tvtitulo);
            item.descripcion = (TextView) fila.findViewById(R.id.tvdescripcion);
            fila.setTag(item);
        }else {
            item = (ItemAparcamiento)fila.getTag();
        }

        Monumentos monumentos = datos.get(posicion);
        item.ivicono.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_eye));
        item.titulo.setText(monumentos.getTitulo());
        item.descripcion.setText(monumentos.getDescripcion());
        return fila;
    }

    static class ItemAparcamiento{
        ImageView ivicono;
        TextView titulo;
        TextView descripcion;
    }
}
