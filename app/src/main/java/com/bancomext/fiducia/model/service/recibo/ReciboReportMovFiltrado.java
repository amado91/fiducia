package com.bancomext.fiducia.model.service.recibo;

import java.util.Date;

public class ReciboReportMovFiltrado {

    int codigo;
    String mensaje;
    long mensajeId;

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public long getMensajeId() {
        return mensajeId;
    }
    public void setMensajeId(long mensajeId) {
        this.mensajeId = mensajeId;
    }
}
