package com.example.juanaj.albedroid.json;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.juanaj.albedroid.R;
import com.example.juanaj.albedroid.json.util.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapajson extends FragmentActivity implements OnMapReadyCallback {

    private double latitud;
    private double longitud;
    private String titulo;
    private LatLng latLng;
    private GoogleMap mapa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapajson);

        Intent i = getIntent();
        latitud = i.getFloatExtra("latitud", 0);
        longitud = i.getFloatExtra("longitud", 0);
        titulo = i.getStringExtra("titulo");

        uk.me.jstott.jcoord.LatLng ubicacion = Util.DeUMTSaLatLng(latitud, longitud, 'N', 30);
        this.latitud = ubicacion.getLat();
        this.longitud = ubicacion.getLng();

        MapsInitializer.initialize(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng marca = new LatLng(latitud, longitud);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(marca));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17f));
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitud, longitud))
                .title(titulo)).showInfoWindow();
    }
}
