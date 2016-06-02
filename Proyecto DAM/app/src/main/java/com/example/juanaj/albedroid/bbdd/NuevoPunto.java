package com.example.juanaj.albedroid.bbdd;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.juanaj.albedroid.MainActivity;
import com.example.juanaj.albedroid.R;

import static com.example.juanaj.albedroid.bbdd.constantes.CALLE;
import static com.example.juanaj.albedroid.bbdd.constantes.CIUDAD;
import static com.example.juanaj.albedroid.bbdd.constantes.DESCRIPCION;
import static com.example.juanaj.albedroid.bbdd.constantes.NOMBRE_PUNTO;

public class NuevoPunto extends Fragment {

    private static String[] FROM_SHOW = {NOMBRE_PUNTO, CALLE, DESCRIPCION, CIUDAD};
    private static int[] TO = {R.id.tvnombrebbdd, R.id.tvcallebbdd, R.id.tvdescripcionbbdd, R.id.tvciudadbbdd};

    private SQLiteDatabase db;
    Button btAgregar;
    Button btVerlista;

    BBDD datos;
    Cursor cursor;

    NotificationCompat.Builder notification;
    private static final int uniqueID = 37451;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);*/

        return inflater.inflate(R.layout.content_nuevo_punto, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        notification = new NotificationCompat.Builder(getActivity());
        notification.setAutoCancel(true);

        datos = new BBDD(getActivity());
        db = datos.getWritableDatabase();

        ImageButton ibModificar = (ImageButton)getView().findViewById(R.id.ibModificarP);
        ibModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarRuta();
            }
        });

        ImageButton ibAlta = (ImageButton)getView().findViewById(R.id.ib_agregarP);
        ibAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevaRuta();
                notification.setSmallIcon(R.drawable.ic_icono_agregar);
                notification.setWhen(System.currentTimeMillis());
                notification.setTicker("Se ha agregado un nuevo punto de interes");
                notification.setContentTitle("Puntos de interes");
                notification.setContentText("Se acaba de agregar un nuevo punto de interes a la lista, click para verlo");

                Intent intent = new Intent(getActivity(), ListaPuntos.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(uniqueID, notification.build());
            }
        });



        ImageButton ibVolverNP = (ImageButton) getView().findViewById(R.id.ibVolverNP);
        ibVolverNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuPrincipal();
            }
        });

        ImageButton ibListaP = (ImageButton)getView().findViewById(R.id.ibVerP);
        ibListaP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verLista();
            }
        });

    }

    public void modificarRuta(){
        Bundle extras = getActivity().getIntent().getExtras();
        long idFinal = extras.getLong("long");

        EditText tfnombre = (EditText) getView().findViewById(R.id.tfnombrebbdd);
        EditText tfkilometros = (EditText) getView().findViewById(R.id.tfcallebbdd);
        EditText tflongitud = (EditText) getView().findViewById(R.id.tfdescripcionbbdd);
        EditText tflatitud = (EditText) getView().findViewById(R.id.tfciudadbbdd);

        String nombre = String.valueOf(tfnombre.getText());
        String calle = String.valueOf(tfkilometros.getText());
        String descripcion = String.valueOf(tflongitud.getText());
        String ciudad = String.valueOf(tflatitud.getText());

        db.execSQL("UPDATE puntos SET nombre = '" + nombre
                + "', calle = '" + calle
                + "', descripcion = '" + descripcion
                + "', ciudad = '" + ciudad
                + "' WHERE _ID = " + idFinal);

        Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.bbdd_nuevo_modificado, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void nuevaRuta(){
        EditText tfnombrebbdd = (EditText) getView().findViewById(R.id.tfnombrebbdd);
        EditText tfcallebbdd = (EditText) getView().findViewById(R.id.tfcallebbdd);
        EditText tfdescripcion = (EditText) getView().findViewById(R.id.tfdescripcionbbdd);
        EditText tfciudadbbdd = (EditText) getView().findViewById(R.id.tfciudadbbdd);

        datos.nuevaRuta(tfnombrebbdd.getText().toString(), tfcallebbdd.getText().toString(), tfdescripcion.getText().toString(),
                tfciudadbbdd.getText().toString());

        tfnombrebbdd.setText("");
        tfcallebbdd.setText("");
        tfdescripcion.setText("");
        tfciudadbbdd.setText("");
        Toast toast1 = Toast.makeText(getActivity().getApplicationContext(), R.string.bbdd_nuevo_nuevo, Toast.LENGTH_SHORT);
        toast1.show();
    }

    public void verLista(){
        /*Intent intent = new Intent(getActivity(), ListaPuntos.class);
        startActivity(intent);*/

        Fragment newFragment = new ListaPuntos();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(android.R.id.tabcontent, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    public void menuPrincipal(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_bbddnueva, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.agregarRBB:
                nuevaRuta();

            case R.id.verlistaRBB:
                Fragment newFragment = new ListaPuntos();
                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.tabs, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                /*Intent i = new Intent(getActivity(), ListaPuntos.class);
                startActivity(i);*/
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
