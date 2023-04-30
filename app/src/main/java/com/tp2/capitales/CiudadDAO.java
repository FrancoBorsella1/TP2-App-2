package com.tp2.capitales;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CiudadDAO extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DE_DATOS = "ciudades.db";
    private static final int VERSION_BASE_DE_DATOS = 1;
    private static final String TABLA_CIUDADES = "ciudades";
    private static final String CAMPO_NOMBRE_PAIS = "nombre_pais";
    private static final String CAMPO_NOMBRE_CIUDAD = "nombre_ciudad";
    private static final String CAMPO_POBLACION = "poblacion";

    public CiudadDAO(Context context) {
        super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String crearTablaCiudades = "CREATE TABLE " + TABLA_CIUDADES + " (" +
                CAMPO_NOMBRE_PAIS + " TEXT, " +
                CAMPO_NOMBRE_CIUDAD + " TEXT, " +
                CAMPO_POBLACION + " INTEGER)";
        db.execSQL(crearTablaCiudades);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hacemos nada en esta versión
    }

    public void agregarCiudad(Ciudad ciudad) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CAMPO_NOMBRE_PAIS, ciudad.getNombrePais());
        values.put(CAMPO_NOMBRE_CIUDAD, ciudad.getNombreCiudad());
        values.put(CAMPO_POBLACION, ciudad.getPoblacion());

        db.insert(TABLA_CIUDADES, null, values);
        db.close();
    }

    public Ciudad obtenerCiudadPorNombre(String nombreCiudad) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLA_CIUDADES, new String[]{CAMPO_NOMBRE_PAIS, CAMPO_NOMBRE_CIUDAD, CAMPO_POBLACION},
                CAMPO_NOMBRE_CIUDAD + "=?", new String[]{nombreCiudad}, null, null, null, null);

        Ciudad ciudad = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String nombrePais = cursor.getString(cursor.getColumnIndex(CAMPO_NOMBRE_PAIS));
            @SuppressLint("Range") int poblacion = cursor.getInt(cursor.getColumnIndex(CAMPO_POBLACION));

            ciudad = new Ciudad(nombrePais, nombreCiudad, poblacion);
        }

        cursor.close();
        db.close();

        return ciudad;
    }

    public void borrarCiudadPorNombre(String nombreCiudad) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLA_CIUDADES, CAMPO_NOMBRE_CIUDAD + "=?", new String[]{nombreCiudad});

        db.close();
    }

    public void borrarCiudadesPorNombrePais(String nombrePais) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLA_CIUDADES, CAMPO_NOMBRE_PAIS + "=?", new String[]{nombrePais});

        db.close();
    }

    public void actualizarPoblacionCiudad(String nombreCiudad, int poblacion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CAMPO_POBLACION, poblacion);

        db.update(TABLA_CIUDADES, values, CAMPO_NOMBRE_CIUDAD + "=?", new String[]{nombreCiudad});

        db.close();
    }

    public List<Ciudad> obtenerTodasLasCiudades() {
        List<Ciudad> listaCiudades = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLA_CIUDADES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombrePais = cursor.getString(cursor.getColumnIndex(CAMPO_NOMBRE_PAIS));
                @SuppressLint("Range") String nombreCiudad = cursor.getString(cursor.getColumnIndex(CAMPO_NOMBRE_CIUDAD));
                @SuppressLint("Range") int poblacion = cursor.getInt(cursor.getColumnIndex(CAMPO_POBLACION));

                Ciudad ciudad = new Ciudad(nombrePais, nombreCiudad, poblacion);

                listaCiudades.add(ciudad);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listaCiudades;
    }
}

