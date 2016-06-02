package com.example.juanaj.albedroid.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

import static android.provider.BaseColumns._ID;
import static com.example.juanaj.albedroid.bbdd.constantes.*;


/**
 * Created by JuanAJ on 17/01/2016.
 */
public class BBDD extends SQLiteOpenHelper {
    ListaPuntos listaPuntos = null;

    private static final String BASEDATOS_NOMBRE = "alberdidroid.db";
    private static final int BASEDATOS_VERSION = 1;

    private static String[] FROM_CURSOR = {_ID, NOMBRE_PUNTO, CALLE, DESCRIPCION, CIUDAD};
    private static String ORDER_BY = NOMBRE_PUNTO + " DESC";

    public BBDD(Context contexto) {
        super(contexto, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLA_PUNTOS + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOMBRE_PUNTO + " TEXT NOT NULL, "
                + CALLE + " TEXT NOT NULL, "
                + DESCRIPCION + " TEXT NOT NULL, "
                + CIUDAD + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_PUNTOS);
        onCreate(db);
    }

    public void nuevaRuta(String nombre, String calle, String descripcion, String ciudad) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put(NOMBRE_PUNTO, nombre);
        valores.put(CALLE, calle);
        valores.put(DESCRIPCION, descripcion);
        valores.put(CIUDAD, ciudad);
        db.insertOrThrow(TABLA_PUNTOS, null, valores);
    }

    public Cursor getCursor() throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, TABLA_PUNTOS, FROM_CURSOR, null, null, null, null, null, null, null);
        return cursor;
    }

    public void borrarDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM puntos");
    }

    public void borrarRuta(long idFinalA) {
        System.out.println("ID FINAL: "+ idFinalA);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM puntos WHERE _ID = "+ idFinalA);
    }

}
