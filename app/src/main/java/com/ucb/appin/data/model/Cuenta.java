package com.ucb.appin.data.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Juan choque on 1/22/2018.
 */

public class Cuenta extends RealmObject {
    @PrimaryKey
    private int id;
    private String nombres;
    private String aparteno;
    private String amaterno;
    private String usuario;
    private String clave;
    private Date fecRegistro;
    private Double latitud;
    private Double longitud;
    private String observacion;
    private Date fecFin;
    private String telefono;
    private String direccion;
    private String celular;
    private String web;
    private String whatsapp;
    private String face;
    private Integer tipoCuenta;
    private Integer ciudad;

    private String sTipoAvisos;
    private String sTransaccionAvisos;

    private boolean sincronizado;

    private int idc;

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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getAparteno() {
        return aparteno;
    }

    public void setAparteno(String aparteno) {
        this.aparteno = aparteno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFecRegistro() {
        return fecRegistro;
    }

    public void setFecRegistro(Date fecRegistro) {
        this.fecRegistro = fecRegistro;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(Integer tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Integer getCiudad() {
        return ciudad;
    }

    public void setCiudad(Integer ciudad) {
        this.ciudad = ciudad;
    }

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    public String getsTipoAvisos() {
        return sTipoAvisos;
    }

    public void setsTipoAvisos(String sTipoAvisos) {
        this.sTipoAvisos = sTipoAvisos;
    }

    public String getsTransaccionAvisos() {
        return sTransaccionAvisos;
    }

    public void setsTransaccionAvisos(String sTransaccionAvisos) {
        this.sTransaccionAvisos = sTransaccionAvisos;
    }
}
