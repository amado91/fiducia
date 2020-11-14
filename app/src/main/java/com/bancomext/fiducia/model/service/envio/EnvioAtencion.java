package com.bancomext.fiducia.model.service.envio;

public class EnvioAtencion {

    String atencionClienteId;

    public EnvioAtencion(String atencionClienteId) {
        this.atencionClienteId = atencionClienteId;
    }

    public String getAtencionClienteId() {
        return atencionClienteId;
    }

    public void setAtencionClienteId(String atencionClienteId) {
        this.atencionClienteId = atencionClienteId;
    }
}
