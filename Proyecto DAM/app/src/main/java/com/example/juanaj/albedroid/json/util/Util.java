package com.example.juanaj.albedroid.json.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by JuanAJ on 16/01/2016.
 */
public class Util {
    public static uk.me.jstott.jcoord.LatLng DeUMTSaLatLng(double este, double oeste, char zonaLat, int zonaLong) {

        uk.me.jstott.jcoord.UTMRef utm = new uk.me.jstott.jcoord.UTMRef(este, oeste, 'N', 30);

        return utm.toLatLng();
    }
    public static LatLng parseCoordenadas(String localizacion) {

        // Comprueba que el elemento seleccionado sean unas coordenadas
        if ((!localizacion.startsWith("[")) || (!localizacion.endsWith("]")))
            return null;

        // Lee y extrae la coordenada del elemento seleccionado por el usuario
        localizacion = localizacion.substring(1, localizacion.length() - 1);
        String coordenadas[] = localizacion.split(",");

        // Convierte las coordenadas de UTM a Latitud y Longitud de la librería jcoord
        uk.me.jstott.jcoord.LatLng ubicacion = Util.DeUMTSaLatLng(Double.parseDouble(coordenadas[0]),
                Double.parseDouble(coordenadas[1]), 'N', 30);

        // Devuelve finalmente las coordenadas como un objeto LatLng de Google Maps
        return new LatLng(ubicacion.getLat(), ubicacion.getLng());
    }
}
