package com.example.juanaj.albedroid.json;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.juanaj.albedroid.AcercadeActivity;
import com.example.juanaj.albedroid.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MonumentoJson extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayList<Monumentos> monumentosArrayList;
    private MonumentoAdapter adapter;
    private static final String URL = "http://www.zaragoza.es/georref/json/hilo/ver_Monumento";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);*/

        return inflater.inflate(R.layout.content_aparca_json, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        listView = (ListView)getView().findViewById(R.id.lviewjson);
        listView.setOnItemClickListener(this);
        monumentosArrayList = new ArrayList<Monumentos>();
        adapter = new MonumentoAdapter(getActivity(), R.layout.filajson, monumentosArrayList);
        listView.setAdapter(adapter);
        cargarAparcamientos();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private class TareaDescargaDatos extends AsyncTask<String, Void, Void> {

        private boolean error = false;

        @Override
        protected Void doInBackground(String... urls) {

            InputStream is = null;
            String resultado = null;
            JSONObject json = null;
            JSONArray jsonArray = null;

            try {
                HttpClient clienteHttp = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urls[0]);
                HttpResponse respuesta = clienteHttp.execute(httpPost);
                HttpEntity entity = respuesta.getEntity();
                is = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String linea = null;

                while ((linea = br.readLine()) != null)
                    sb.append(linea + "\n");

                is.close();
                resultado = sb.toString();

                json = new JSONObject(resultado);
                jsonArray = json.getJSONArray("features");

                String titulo = null;
                String descripcion = null;
                String link = null;
                String coordenadas = null;
                String icono;
                Monumentos monumentos = null;

                for (int i = 0; i < jsonArray.length(); i++) {
                    titulo = jsonArray.getJSONObject(i).getJSONObject("properties").getString("title");
                    descripcion = jsonArray.getJSONObject(i).getJSONObject("properties").getString("description");
                    link = jsonArray.getJSONObject(i).getJSONObject("properties").getString("link");
                    coordenadas = jsonArray.getJSONObject(i).getJSONObject("geometry").getString("coordinates");
                    icono = jsonArray.getJSONObject(i).getJSONObject("properties").getString("icon");

                    coordenadas = coordenadas.substring(1, coordenadas.length() - 1);
                    String latlong[] = coordenadas.split(",");

                    System.out.println(icono);

                    monumentos = new Monumentos();
                    monumentos.setTitulo(titulo);
                    monumentos.setDescripcion(descripcion);
                    monumentos.setLink(link);

                    monumentos.setLatitud(Float.parseFloat(latlong[0]));
                    monumentos.setLongitud(Float.parseFloat(latlong[1]));

                    monumentosArrayList.add(monumentos);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
                error = true;
            } catch (JSONException jse) {
                jse.printStackTrace();
                error = true;
            }
            return null;
        }

        @Override
        protected void onCancelled(){
            super.onCancelled();
            adapter.clear();
            monumentosArrayList = new ArrayList<Monumentos>();
        }

        @Override
        protected void onProgressUpdate(Void... progreso) {
            super.onProgressUpdate(progreso);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void resultado) {
            super.onPostExecute(resultado);
            if (error) {
                Toast.makeText(getActivity().getApplicationContext(), (R.string.json_toast_exito), Toast.LENGTH_SHORT).show();
                return;
            }
            adapter.notifyDataSetChanged();
            Toast.makeText(getActivity().getApplicationContext(), (R.string.json_toast_exito), Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarAparcamientos(){
        TareaDescargaDatos tarea = new TareaDescargaDatos();
        tarea.execute(URL);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == ListView.INVALID_POSITION)
            return;
        Monumentos aparcamiento = monumentosArrayList.get(position);
        Intent intent = new Intent(getActivity(), Mapajson.class);
        intent.putExtra("latitud", aparcamiento.getLatitud());
        intent.putExtra("longitud", aparcamiento.getLongitud());
        intent.putExtra("titulo", aparcamiento.getTitulo());
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_jsonlista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.acercadeMJ:
                Intent intent = new Intent(getActivity(), AcercadeActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
