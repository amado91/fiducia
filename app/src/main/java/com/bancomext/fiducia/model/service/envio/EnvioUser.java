package com.bancomext.fiducia.model.service.envio;

public class EnvioUser {

    private String usuarioId;
    private String usuario;
    private String nombre;
    private String token;
    private String dispositivo;

    public EnvioUser(String usuarioId, String usuario, String nombre, String token, String dispositivo) {
        this.usuarioId = usuarioId;
        this.usuario = usuario;
        this.nombre = nombre;
        this.token = token;
        this.dispositivo = dispositivo;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }
}
