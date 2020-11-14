package com.bancomext.fiducia.model.service.envio;

public class EnvioSaldos {

    int saldoContratoNumero;


    public EnvioSaldos(int saldoContratoNumero) {
        this.saldoContratoNumero = saldoContratoNumero;
    }

    public int getSaldoContratoNumero() {
        return saldoContratoNumero;
    }

    public void setSaldoContratoNumero(int saldoContratoNumero) {
        this.saldoContratoNumero = saldoContratoNumero;
    }
}
