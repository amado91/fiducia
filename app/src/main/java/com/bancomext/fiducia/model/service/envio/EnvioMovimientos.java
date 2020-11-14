package com.bancomext.fiducia.model.service.envio;

public class EnvioMovimientos {

    private int movtoContratoNumero;

    public EnvioMovimientos(int movtoContratoNumero) {
        this.movtoContratoNumero = movtoContratoNumero;
    }

    public int getMovtoContratoNumero() {
        return movtoContratoNumero;
    }

    public void setMovtoContratoNumero(int movtoContratoNumero) {
        this.movtoContratoNumero = movtoContratoNumero;
    }
}
