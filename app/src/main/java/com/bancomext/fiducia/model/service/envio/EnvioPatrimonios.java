package com.bancomext.fiducia.model.service.envio;

public class EnvioPatrimonios {

    private int tipoReporte;
    private int numeroContrato;

    public EnvioPatrimonios(int tipoReporte, int numeroContrato) {
        this.tipoReporte = tipoReporte;
        this.numeroContrato = numeroContrato;
    }

    public int getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(int tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public int getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(int numeroContrato) {
        this.numeroContrato = numeroContrato;
    }
}
