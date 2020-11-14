package com.bancomext.fiducia.model;

import java.io.Serializable;
import java.util.List;

public class ModelFideicomiso implements Serializable{

    private String Title;
    private String nombre;
    private List <ModelMovimientos> movimientos;
    private List <ModelSaldos> saldos;

    public ModelFideicomiso(String title, String nombre, List<ModelMovimientos> movimientos, List<ModelSaldos> saldos) {
        Title = title;
        this.nombre = nombre;
        this.movimientos = movimientos;
        this.saldos = saldos;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ModelMovimientos> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<ModelMovimientos> movimientos) {
        this.movimientos = movimientos;
    }

    public List<ModelSaldos> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<ModelSaldos> saldos) {
        this.saldos = saldos;
    }

}
