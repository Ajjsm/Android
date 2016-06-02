package com.example.juanaj.albedroid.comentarios;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.juanaj.albedroid.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaComentarios extends Fragment {

    private ComentarioAdapter adapter;
    private List<BaseComentarios> listaComentarios;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);*/

        return inflater.inflate(R.layout.content_lista_comentarios, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        listaComentarios = new ArrayList<BaseComentarios>();
        adapter = new ComentarioAdapter(getActivity(), R.layout.filacomentario, listaComentarios);
        ListView lvComentarios = (ListView) getView().findViewById(R.id.listaComentarios);
        lvComentarios.setAdapter(adapter);

        WebService webService = new WebService();
        webService.execute();

    }
        private class WebService extends AsyncTask<String, Void, Void> {
            private ProgressDialog dialog;

            @Override
            protected Void doInBackground(String... params) {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                BaseComentarios[] comentariosArray = restTemplate.getForObject("http://192.168.1.33:8989" + "/comentarios", BaseComentarios[].class);
                listaComentarios.addAll(Arrays.asList(comentariosArray));
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                listaComentarios.clear();
                dialog = new ProgressDialog(getActivity());
                dialog.setTitle(R.string.comentarios_cargando_opiniones);
                dialog.show();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (dialog != null)
                    dialog.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                adapter.notifyDataSetChanged();
            }
        }

}
