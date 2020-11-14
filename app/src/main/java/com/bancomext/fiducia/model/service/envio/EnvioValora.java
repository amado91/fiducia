package com.bancomext.fiducia.model.service.envio;

public class EnvioValora {

    private int puntuacion;
    private String comentarios;
    private long  atencionFechaReg;
    private int contrato;
    private String usuario;

    public EnvioValora(int puntuacion, String comentarios, long atencionFechaReg, int fideicomiso, String usuario) {
        this.puntuacion = puntuacion;
        this.comentarios = comentarios;
        this.atencionFechaReg = atencionFechaReg;
        this.contrato = fideicomiso;
        this.usuario = usuario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public long getAtencionFechaReg() {
        return atencionFechaReg;
    }

    public void setAtencionFechaReg(long atencionFechaReg) {
        this.atencionFechaReg = atencionFechaReg;
    }

    public int getContrato() {
        return contrato;
    }

    public void setContrato(int fideicomiso) {
        this.contrato = fideicomiso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
