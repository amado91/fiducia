package com.bancomext.fiducia.model.service.recibo;

import java.util.Date;

public class ReciboMovimientos {

    int movtoContratoNumero;
    int movtoFolio;
    String movtoTipo;
    long movtoFecha;
    String movtoMoneda;
    int movtoImporte;
    String movtoConcepto;
    String movtoTercero;
    long movtoFechaReg;


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

    public long getMovtoFecha() {
        return movtoFecha;
    }

    public void setMovtoFecha(long movtoFecha) {
        this.movtoFecha = movtoFecha;
    }

    public String getMovtoMoneda() {
        return movtoMoneda;
    }

    public void setMovtoMoneda(String movtoMoneda) {
        this.movtoMoneda = movtoMoneda;
    }

    public int getMovtoImporte() {
        return movtoImporte;
    }

    public void setMovtoImporte(int movtoImporte) {
        this.movtoImporte = movtoImporte;
    }

    public String getMovtoConcepto() {
        return movtoConcepto;
    }

    public void setMovtoConcepto(String movtoConcepto) {
        movtoConcepto = movtoConcepto;
    }

    public String getMovtoTercero() {
        return movtoTercero;
    }

    public void setMovtoTercero(String movtoTercero) {
        this.movtoTercero = movtoTercero;
    }

    public long getMovtoFechaReg() {
        return movtoFechaReg;
    }

    public void setMovtoFechaReg(long movtoFechaReg) {
        this.movtoFechaReg = movtoFechaReg;
    }
}
