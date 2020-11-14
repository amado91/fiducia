package com.bancomext.fiducia.model.service.envio;

public class EnvioReporte {

    private int tipoReporte;
    private int numeroContrato;
    private String usuario;

    public EnvioReporte(int tipoReporte, int numeroContrato, String usuario) {
        this.tipoReporte = tipoReporte;
        this.numeroContrato = numeroContrato;
        this.usuario = usuario;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
