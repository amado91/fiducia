package com.bancomext.fiducia.model.service.recibo;

import com.bancomext.fiducia.model.service.envio.EnvioUser;

public class ReciboLogin {

    private String codigo;
    private String mensaje;
    private EnvioUser usuario;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public EnvioUser getUsuario() {
        return usuario;
    }

    public void setUsuario(EnvioUser usuario) {
        this.usuario = usuario;
    }
}
