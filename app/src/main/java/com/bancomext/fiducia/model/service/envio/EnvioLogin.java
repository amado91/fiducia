package com.bancomext.fiducia.model.service.envio;

public class EnvioLogin {

    String token;
    int dispositivo;

    public EnvioLogin(String token, int dispositivo) {
        this.token = token;
        this.dispositivo = dispositivo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(int dispositivo) {
        this.dispositivo = dispositivo;
    }
}
