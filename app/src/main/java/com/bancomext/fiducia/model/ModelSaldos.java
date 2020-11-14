package com.bancomext.fiducia.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class ModelSaldos implements Comparator<ModelMovimientos>, Serializable{

    private int contratoNumero;
    private Date fecha;
    private int atencionContratoNumero;
    private String ctoInvNombre;
    private String ctoInvMoneda;
    private int ctoInvSaldo;
    private String saldoFechaReg;

    public ModelSaldos(int contratoNumero, Date fecha, int atencionContratoNumero, String ctoInvNombre, String ctoInvMoneda, int ctoInvSaldo, String saldoFechaReg) {
        this.contratoNumero = contratoNumero;
        this.fecha = fecha;
        this.atencionContratoNumero = atencionContratoNumero;
        this.ctoInvNombre = ctoInvNombre;
        this.ctoInvMoneda = ctoInvMoneda;
        this.ctoInvSaldo = ctoInvSaldo;
        this.saldoFechaReg = saldoFechaReg;
    }

    public int getContratoNumero() {
        return contratoNumero;
    }

    public void setContratoNumero(int contratoNumero) {
        this.contratoNumero = contratoNumero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getAtencionContratoNumero() {
        return atencionContratoNumero;
    }

    public void setAtencionContratoNumero(int atencionContratoNumero) {
        this.atencionContratoNumero = atencionContratoNumero;
    }

    public String getCtoInvNombre() {
        return ctoInvNombre;
    }

    public void setCtoInvNombre(String ctoInvNombre) {
        this.ctoInvNombre = ctoInvNombre;
    }

    public String getCtoInvMoneda() {
        return ctoInvMoneda;
    }

    public void setCtoInvMoneda(String ctoInvMoneda) {
        this.ctoInvMoneda = ctoInvMoneda;
    }

    public int getCtoInvSaldo() {
        return ctoInvSaldo;
    }

    public void setCtoInvSaldo(int ctoInvSaldo) {
        this.ctoInvSaldo = ctoInvSaldo;
    }

    public String getSaldoFechaReg() {
        return saldoFechaReg;
    }

    public void setSaldoFechaReg(String saldoFechaReg) {
        this.saldoFechaReg = saldoFechaReg;
    }


    @Override
    public int compare(ModelMovimientos o1, ModelMovimientos o2) {
        return o1.getMovtoFecha().compareTo(o2.getMovtoFecha());
    }
}
