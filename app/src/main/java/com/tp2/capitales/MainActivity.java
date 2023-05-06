package com.tp2.capitales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNombrePais;
    private EditText editTextNombreCiudad;
    private EditText editTextPoblacion;
    private CiudadDAO ciudadDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtengo los campos de texto de la pantalla principal
        editTextNombrePais = findViewById(R.id.editTextNombrePais);
        editTextNombreCiudad = findViewById(R.id.editTextNombreCiudad);
        editTextPoblacion = findViewById(R.id.editTextPoblacion);

        ciudadDAO = new CiudadDAO(this);
    }

    public void onClickAgregarCiudad(View view) {
        //Guardo en variables el contenido de cada uno de los campos
        String nombrePais = editTextNombrePais.getText().toString();
        String nombreCiudad = editTextNombreCiudad.getText().toString();
        String poblacionStr = editTextPoblacion.getText().toString();

        // Validación para el campo de país
        if (TextUtils.isEmpty(nombrePais)) {
            editTextNombrePais.setError("Debe ingresar un país");
            editTextNombrePais.requestFocus();
            return;
        }

        // Validación para el campo de ciudad
        if (TextUtils.isEmpty(nombreCiudad)) {
            editTextNombreCiudad.setError("Debe ingresar una ciudad");
            editTextNombreCiudad.requestFocus();
            return;
        }

        // Validación para el campo de población
        if (TextUtils.isEmpty(poblacionStr)) {
            editTextPoblacion.setError("Debe ingresar una población");
            editTextPoblacion.requestFocus();
            return;
        }

        int poblacion = Integer.parseInt(editTextPoblacion.getText().toString());

        Ciudad ciudad = new Ciudad(nombrePais, nombreCiudad, poblacion);

        ciudadDAO.agregarCiudad(ciudad);

        Toast.makeText(this, "Ciudad agregada correctamente", Toast.LENGTH_SHORT).show();
    }

    public void onClickBuscarCiudad(View view) {
        String nombreCiudad = editTextNombreCiudad.getText().toString();

        Ciudad ciudad = ciudadDAO.obtenerCiudadPorNombre(nombreCiudad);

        // Validación para el campo de ciudad
        if (TextUtils.isEmpty(nombreCiudad)) {
            editTextNombreCiudad.setError("Debe ingresar una ciudad");
            editTextNombreCiudad.requestFocus();
            return;
        } else {
            if (ciudad != null) {
                editTextNombrePais.setText(ciudad.getNombrePais());
                editTextPoblacion.setText(String.valueOf(ciudad.getPoblacion()));
                Toast.makeText(this, "Ciudad encontrada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ciudad no encontrada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickBorrarCiudad(View view) {
        String nombreCiudad = editTextNombreCiudad.getText().toString();

        // Validación para el campo de ciudad
        if (TextUtils.isEmpty(nombreCiudad)) {
            editTextNombreCiudad.setError("Debe ingresar una ciudad");
            editTextNombreCiudad.requestFocus();
            return;
        }

        ciudadDAO.borrarCiudadPorNombre(nombreCiudad);
        Toast.makeText(this, "Ciudad borrada correctamente", Toast.LENGTH_SHORT).show();
    }

    public void onClickBorrarCiudadesPorPais(View view) {
        String nombrePais = editTextNombrePais.getText().toString();

        // Validación para el campo de nombre de país
        if (TextUtils.isEmpty(nombrePais)) {
            editTextNombrePais.setError("Debe ingresar un nombre de país");
            editTextNombrePais.requestFocus();
            return;
        }

        //Ejecutar accion de eliminacion si se ingresa un nombre de pais valido

        int numCiudadesEliminadas = ciudadDAO.borrarCiudadesPorNombrePais(nombrePais);

        if (numCiudadesEliminadas > 0) {
            Toast.makeText(this, numCiudadesEliminadas + " ciudades borradas correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se encontraron ciudades para el país ingresado", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickActualizarPoblacion(View view) {
        String nombreCiudad = editTextNombreCiudad.getText().toString();
        String nombrePais = editTextNombrePais.getText().toString();
        String poblacionStr = editTextPoblacion.getText().toString();

        // Validación para el campo de país
        if (TextUtils.isEmpty(nombrePais)) {
            editTextNombrePais.setError("Debe ingresar un país");
            editTextNombrePais.requestFocus();
            return;
        }

        // Validación para el campo de ciudad
        if (TextUtils.isEmpty(nombreCiudad)) {
            editTextNombreCiudad.setError("Debe ingresar una ciudad");
            editTextNombreCiudad.requestFocus();
            return;
        }

        // Validación para el campo de población
        if (TextUtils.isEmpty(poblacionStr)) {
            editTextPoblacion.setError("Debe ingresar una población");
            editTextPoblacion.requestFocus();
            return;
        }

        int poblacion = Integer.parseInt(poblacionStr);

        // Verifica que la ciudad exista en la BD
        Ciudad ciudad = ciudadDAO.obtenerCiudadPorNombre(nombreCiudad);
        if (ciudad == null) {
            Toast.makeText(this, "La ciudad no existe en la base de datos", Toast.LENGTH_SHORT).show();
            return;
        }

        ciudadDAO.actualizarPoblacionCiudad(nombreCiudad, poblacion);
        Toast.makeText(this, "Población actualizada correctamente", Toast.LENGTH_SHORT).show();
    }

   /* public void onClickMostrarTodasLasCiudades(View view) {
        List<Ciudad> listaCiudades = ciudadDAO.obtenerTodasLasCiudades();

        StringBuilder sb = new StringBuilder();

        for (Ciudad ciudad : listaCiudades) {
            sb.append("País: ").append(ciudad.getNombrePais()).append("\n");
            sb.append("Ciudad: ").append(ciudad.getNombreCiudad()).append("\n");
            sb.append("Población: ").append(ciudad.getPoblacion()).append("\n\n");
        }

        if (sb.length() > 0) {
            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No hay ciudades registradas", Toast.LENGTH_SHORT).show();
        }
    }

    */
}
