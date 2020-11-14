package com.bancomext.fiducia.model;

import android.view.Display;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class ModelMovimientos implements Comparator<ModelMovimientos>, Serializable{

    private int movtoContratoNumero;
    private int movtoFolio;
    private String movtoTipo;
    private Date movtoFecha;
    private String movtoMoneda;
    private String movtoImporte;
    private String movtoConcepto;
    private String movtoTercero;
    private Date movtoFechaReg;

    public ModelMovimientos(){}

    public ModelMovimientos(int movtoContratoNumero, int movtoFolio, String movtoTipo, Date movtoFecha, String movtoMoneda, String movtoImporte, String movtoConcepto, String movtoTercero, Date movtoFechaReg) {
        this.movtoContratoNumero = movtoContratoNumero;
        this.movtoFolio = movtoFolio;
        this.movtoTipo = movtoTipo;
        this.movtoFecha = movtoFecha;
        this.movtoMoneda = movtoMoneda;
        this.movtoImporte = movtoImporte;
        this.movtoConcepto = movtoConcepto;
        this.movtoTercero = movtoTercero;
        this.movtoFechaReg = movtoFechaReg;
    }

    public int getMovtoContratoNumero() {
        return movtoContratoNumero;
    }

    public void setMovtoContratoNumero(int movtoContratoNumero) {
        this.movtoContratoNumero = movtoContratoNumero;
    }

    public int getMovtoFolio() {
        return movtoFolio;
    }

    public void setMovtoFolio(int movtoFolio) {
        this.movtoFolio = movtoFolio;
    }

    public String getMovtoTipo() {
        return movtoTipo;
    }

    public void setMovtoTipo(String movtoTipo) {
        this.movtoTipo = movtoTipo;
    }

    public Date getMovtoFecha() {
        return movtoFecha;
    }

    public void setMovtoFecha(Date movtoFecha) {
        this.movtoFecha = movtoFecha;
    }

    public String getMovtoMoneda() {
        return movtoMoneda;
    }

    public void setMovtoMoneda(String movtoMoneda) {
        this.movtoMoneda = movtoMoneda;
    }

    public String getMovtoImporte() {
        return movtoImporte;
    }

    public void setMovtoImporte(String movtoImporte) {
        this.movtoImporte = movtoImporte;
    }

    public String getMovtoConcepto() {
        return movtoConcepto;
    }

    public void setMovtoConcepto(String movtoConcepto) {
        this.movtoConcepto = movtoConcepto;
    }

    public String getMovtoTercero() {
        return movtoTercero;
    }

    public void setMovtoTercero(String movtoTercero) {
        this.movtoTercero = movtoTercero;
    }

    public Date getMovtoFechaReg() {
        return movtoFechaReg;
    }

    public void setMovtoFechaReg(Date movtoFechaReg) {
        this.movtoFechaReg = movtoFechaReg;
    }

    @Override
    public int compare(ModelMovimientos o1, ModelMovimientos o2) {
        return o1.getMovtoFecha().compareTo(o2.getMovtoFecha());
    }
}
