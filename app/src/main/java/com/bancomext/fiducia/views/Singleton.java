package com.bancomext.fiducia.views;

import com.bancomext.fiducia.model.ModelFideicomiso;
import com.bancomext.fiducia.model.ModelMovimientos;
import com.bancomext.fiducia.model.ModelSaldos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Singleton {

    public static final Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    private String nombreFideicomiso;
    private String nombreUser;
    private String password;
    private int fideicomisoContrato;
    private boolean flagSaldos;
    private boolean flagMovimientos;
    private List<ModelMovimientos> fechasEncontradas;
    private Date fechaIni, fechaFin;

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<ModelMovimientos> getFechasEncontradas() {
        return fechasEncontradas;
    }

    public void setFechasEncontradas(List<ModelMovimientos> fechasEncontradas) {
        this.fechasEncontradas = fechasEncontradas;
    }

    public boolean isFlagMovimientos() {
        return flagMovimientos;
    }

    public void setFlagMovimientos(boolean flagMovimientos) {
        this.flagMovimientos = flagMovimientos;
    }

    public boolean isFlagSaldos() {
        return flagSaldos;
    }

    public void setFlagSaldos(boolean flagSaldos) {
        this.flagSaldos = flagSaldos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    private List<ModelFideicomiso> fideicomisos;
    private List<ModelMovimientos> movimientos;
    private List <ModelSaldos> saldos;

    public String getNombreFideicomiso() {
        return nombreFideicomiso;
    }

    public List<ModelMovimientos> getMovimientos() {
        return movimientos;
    }

    public List<ModelSaldos> getSaldos() {
        return saldos;
    }

    public List<ModelFideicomiso> getFideicomisos() {
        return fideicomisos;
    }

    public void setFideicomisos(List<ModelFideicomiso> fideicomisos) {
        this.fideicomisos = fideicomisos;
    }

    public void setNombreFideicomiso(String nombreFideicomiso) {
        this.nombreFideicomiso = nombreFideicomiso;
    }

    public void setMovimientos(List<ModelMovimientos> movimientos) {
        this.movimientos = movimientos;
    }

    public void setSaldos(List<ModelSaldos> saldos) {
        this.saldos = saldos;
    }


    String token;

    public static Singleton getOurInstance() {
        return ourInstance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    ModelFideicomiso fideicomiso;

    public ModelFideicomiso getFideicomiso() {
        return fideicomiso;
    }

    public void setFideicomiso(ModelFideicomiso fideicomiso) {
        this.fideicomiso = fideicomiso;
    }

    public int getFideicomisoContrato() {
        return fideicomisoContrato;
    }

    public void setFideicomisoContrato(int fideicomisoContrato) {
        this.fideicomisoContrato = fideicomisoContrato;
    }
}
