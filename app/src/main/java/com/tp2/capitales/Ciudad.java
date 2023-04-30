package com.tp2.capitales;

public class Ciudad {
    private String nombrePais;
    private String nombreCiudad;
    private int poblacion;

    public Ciudad(String nombrePais, String nombreCiudad, int poblacion) {
        this.nombrePais = nombrePais;
        this.nombreCiudad = nombreCiudad;
        this.poblacion = poblacion;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }
}

