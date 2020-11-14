package com.bancomext.fiducia.model.service.envio;

import java.util.Date;

public class EnvioReportMovFiltrado {

    private int tipoReporte;
    private int numeroContrato;
    private String usuario;
    private String fechaInicio;
    private String fechaFinal;

    public EnvioReportMovFiltrado(int tipoReporte, int numeroContrato, String usuario, String fechaInicio, String fechaFinal) {
        this.tipoReporte = tipoReporte;
        this.numeroContrato = numeroContrato;
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
}
