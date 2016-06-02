package com.example.juanaj.albedroid.bbdd;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.juanaj.albedroid.R;

import java.sql.SQLException;

import static android.provider.BaseColumns._ID;
import static com.example.juanaj.albedroid.bbdd.constantes.CALLE;
import static com.example.juanaj.albedroid.bbdd.constantes.CIUDAD;
import static com.example.juanaj.albedroid.bbdd.constantes.DESCRIPCION;
import static com.example.juanaj.albedroid.bbdd.constantes.NOMBRE_PUNTO;

public class ListaPuntos extends Fragment {

    int posicionItem;
    long idFinal;

    private static String[] FROM_SHOW = {_ID, NOMBRE_PUNTO, CALLE, DESCRIPCION, CIUDAD};
    private static int[] TO = {
            R.id.tvidbbdd, R.id.tvnombrebbdd, R.id.tvcallebbdd, R.id.tvdescripcionbbdd, R.id.tvciudadbbdd};

    BBDD datos;
    Cursor cursor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.content_lista_puntos, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        datos = new BBDD(getActivity());
        try {
            cursor = datos.getCursor();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final ListView lista = (ListView) getActivity().findViewById(R.id.lviewbbdd);


        final SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getActivity(), R.layout.filabbdd, cursor, FROM_SHOW, TO, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lista.setAdapter(adaptador);
        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idFinal = id;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_contextualbbdd, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(getActivity(), "" + posicionItem, Toast.LENGTH_SHORT).show();
        datos = new BBDD(getActivity());
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.ctxEliminar:
                datos.borrarRuta(idFinal);
                Toast.makeText(getActivity(), R.string.bbdd_lista_eliminado, Toast.LENGTH_LONG).show();
                /*Intent intent = new Intent(getActivity(), NuevoPunto.class);
                startActivity(intent);*/
                Fragment newFragment = new NuevoPunto();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(android.R.id.tabcontent, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                break;

            case R.id.ctxModificar:
                Toast.makeText(getActivity(), R.string.bbdd_lista_modificado, Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(getActivity(), NuevoPunto.class);
                intent1.putExtra("long", idFinal);
                startActivity(intent1);
                break;
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_bbddlista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.eliminarMBB:
                datos.borrarRuta(idFinal);
                Toast.makeText(getActivity(), R.string.bbdd_lista_eliminado, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), NuevoPunto.class);
                startActivity(intent);

            case R.id.modificarMBB:
                Toast.makeText(getActivity(), R.string.bbdd_lista_modificado, Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(getActivity(), NuevoPunto.class);
                intent1.putExtra("long", idFinal);
                startActivity(intent1);
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
