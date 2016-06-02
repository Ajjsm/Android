package com.example.juanaj.albedroid;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.example.juanaj.albedroid.bbdd.NuevoPunto;
import com.example.juanaj.albedroid.comentarios.EscribirComentarios;
import com.example.juanaj.albedroid.json.MonumentoJson;

public class MainActivity extends FragmentActivity {
        private FragmentTabHost tabHost;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
            tabHost.setup(this,
                    getSupportFragmentManager(), android.R.id.tabcontent);
            tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("", getResources().getDrawable(R.mipmap.ic_seeee)),
                    MonumentoJson.class, null);
            tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("", getResources().getDrawable(R.mipmap.ic_escribir)),
                    EscribirComentarios.class, null);
            tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("", getResources().getDrawable(R.mipmap.ic_agregar)),
                    NuevoPunto.class, null);
            tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("", getResources().getDrawable(R.mipmap.ic_preferences)),
                    NuevoPunto.class, null);


    }
}