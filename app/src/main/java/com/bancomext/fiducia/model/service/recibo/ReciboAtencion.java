package com.bancomext.fiducia.model.service.recibo;

public class ReciboAtencion {


    String atencionClienteId;
    int atencionContratoNumero;
    String atencionContratoNombre;
    long atencionFechaReg;
    String atencionStatus;

    public String getAtencionClienteId() {
        return atencionClienteId;
    }

    public void setAtencionClienteId(String atencionClienteId) {
        this.atencionClienteId = atencionClienteId;
    }

    public int getAtencionContratoNumero() {
        return atencionContratoNumero;
    }

    public void setAtencionContratoNumero(int atencionContratoNumero) {
        this.atencionContratoNumero = atencionContratoNumero;
    }

    public String getAtencionContratoNombre() {
        return atencionContratoNombre;
    }

    public void setAtencionContratoNombre(String atencionContratoNombre) {
        this.atencionContratoNombre = atencionContratoNombre;
    }

    public long getAtencionFechaReg() {
        return atencionFechaReg;
    }

    public void setAtencionFechaReg(long atencionFechaReg) {
        this.atencionFechaReg = atencionFechaReg;
    }

    public String getAtencionStatus() {
        return atencionStatus;
    }

    public void setAtencionStatus(String atencionStatus) {
        this.atencionStatus = atencionStatus;
    }
}
