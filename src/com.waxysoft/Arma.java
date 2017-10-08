package com.waxysoft;

/**
 * Created by Pablo on 06/04/2017.
 */
public class Arma {
    private String nombre;
    private Integer carga;
    private Integer cargaTotal;
    public Arma(String nombre, Integer carga) {
        this.nombre = nombre;
        this.carga = carga;
        this.cargaTotal = carga;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCarga() {
        return carga;
    }

    public void setCarga(Integer carga) {
        this.carga = carga;
    }

    public Integer getCargaTotal() {
        return cargaTotal;
    }

    public void setCargaTotal(Integer cargaTotal) {
        this.cargaTotal = cargaTotal;
    }

    public void restarCarga(){
        carga--;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Arma) {
            return nombre.equals(((Arma)obj).getNombre());
        } else return false;
    }

}
