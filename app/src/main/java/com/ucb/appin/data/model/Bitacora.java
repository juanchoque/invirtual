package com.ucb.appin.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Juan choque on 2/10/2018.
 */

public class Bitacora extends RealmObject {
    @PrimaryKey
    private int id;
    private int idBean;
    private int operacion;
    private String estado;
    private String telefono;
    private int idc;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBean() {
        return idBean;
    }

    public void setIdBean(int idBean) {
        this.idBean = idBean;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
