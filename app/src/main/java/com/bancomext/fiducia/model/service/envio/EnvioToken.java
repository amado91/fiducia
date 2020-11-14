package com.bancomext.fiducia.model.service.envio;

public class EnvioToken {

    String usuario;
    String token;
    String os;

    public EnvioToken(String usuario, String token, String os) {
        this.usuario = usuario;
        this.token = token;
        this.os = os;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }
}
