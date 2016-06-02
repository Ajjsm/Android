package com.example.juanaj.albedroid.comentarios;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.juanaj.albedroid.MainActivity;
import com.example.juanaj.albedroid.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class EscribirComentarios extends Fragment{
    Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.content_escribir_comentarios, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ImageButton ibGuardarC = (ImageButton)getView().findViewById(R.id.ibGuardarC);
        ibGuardarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarFichero();
            }
        });


       ImageButton ibVolverC = (ImageButton)getView().findViewById(R.id.ibVolverC);
        ibVolverC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void guardarFichero(){
        EditText etNombreC = (EditText) getView().findViewById(R.id.etNombreC);
        EditText etComentarioC = (EditText) getView().findViewById(R.id.etComentarioC);
        EditText etValoracionC = (EditText) getView().findViewById(R.id.etValoracionC);

        if (etValoracionC.getText().toString().equals(""))
            etValoracionC.setText("-1");

        String nombre = etNombreC.getText().toString();
        String comentario = etComentarioC.getText().toString();
        String valoracion = etValoracionC.getText().toString();

        WebService webService = new WebService();
        webService.execute(nombre, comentario, valoracion);
    }

    private class WebService extends AsyncTask<String, Void, Void> {

        private ProgressDialog dialog;

        @Override
        protected Void doInBackground(String... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getForObject("http://192.168.1.33:8989" + "/add_comentario?nombre=" + params[0] + "&comentario=" + params[1] + "&valoracion=" + params[2], Void.class);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setTitle(R.string.comentarios_escribir_enviado);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            EditText etNombre = (EditText) getView().findViewById(R.id.etNombreC);
            etNombre.setText("");
            EditText etComentario = (EditText) getView().findViewById(R.id.etComentarioC);
            etComentario.setText("");
            EditText etValoracion = (EditText) getView().findViewById(R.id.etValoracionC);
            etValoracion.setText("");
            if (dialog != null)
                dialog.dismiss();
        }
    }

}
