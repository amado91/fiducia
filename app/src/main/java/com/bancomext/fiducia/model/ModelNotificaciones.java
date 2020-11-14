package com.bancomext.fiducia.model;

import java.io.Serializable;

public class ModelNotificaciones implements Serializable {

    private String titulo;
    private String descripcion;
    private String fecha;
    private String leido;
    private String contrato;
    private String idalerta;

    public ModelNotificaciones(String titulo, String descripcion, String fecha, String leido, String contrato, String idalerta) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.leido = leido;
        this.contrato = contrato;
        this.idalerta = idalerta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLeido() {
        return leido;
    }

    public void setLeido(String leido) {
        this.leido = leido;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getIdalerta() {
        return idalerta;
    }

    public void setIdalerta(String idalerta) {
        this.idalerta = idalerta;
    }
}
