package com.drawer.juanaj.facturaciontrabajos.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drawer.juanaj.facturaciontrabajos.R;

/**
 * Created by JuanAJ on 06/07/2016.
 */
public class CompartirFragment extends Fragment{
    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedinstanceState){
        rootview = inflater.inflate(R.layout.compartir_fragment, container, false);
        return rootview;
    }
}
