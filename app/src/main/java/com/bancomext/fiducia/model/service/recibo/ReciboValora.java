package com.bancomext.fiducia.model.service.recibo;

public class ReciboValora {

    private String valorarID;
    private int puntuacion;
    private String comentarios;
    private long  atencionFechaReg;

    public ReciboValora(String valorarID, int puntuacion, String comentarios, long atencionFechaReg) {
        this.valorarID = valorarID;
        this.puntuacion = puntuacion;
        this.comentarios = comentarios;
        this.atencionFechaReg = atencionFechaReg;
    }

    public String getValorarID() {
        return valorarID;
    }

    public void setValorarID(String valorarID) {
        this.valorarID = valorarID;
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
}
