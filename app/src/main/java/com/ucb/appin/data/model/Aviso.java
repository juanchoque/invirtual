package com.ucb.appin.data.model;

import java.util.Date;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Juan choque on 1/22/2018.
 */


public class Aviso extends RealmObject {
    @PrimaryKey
    private int id;
    private String titulo;
    private String descripcion;
    private Integer orden;
    private String estado;
    private String telefono;
    private String empresap;
    private Double latitud;
    private Double longitud;
    private String web;
    private String wat;
    private String face;
    private Integer publicado;
    private Integer promocion;
    private Double precio;
    private Double comision;
    private Date fecPublicacion = new Date();
    private Date fecModificacion = new Date();
    private Date fecFinPublicacion = new Date();
    private Integer cuenta;
    private Integer subCategoria;
    private Integer tipoaviso;
    private Integer transaccionaviso;
    private String imagen;
    private String direccion;
    private String color;
    private boolean sincronizado;

    private Integer tipo_aviso;
    private Integer transaccion_aviso;

    private String idc;
    private Integer operacion;
    private Integer idBitacora;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmpresap() {
        return empresap;
    }

    public void setEmpresap(String empresap) {
        this.empresap = empresap;
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

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getWat() {
        return wat;
    }

    public void setWat(String wat) {
        this.wat = wat;
    }

    public String getFace() {
        return face;
    }

    public void setTransaccionaviso(Integer transaccionaviso) {
        this.transaccionaviso = transaccionaviso;
    }

    public Integer getTipo_aviso() {
        return tipo_aviso;
    }

    public void setTipo_aviso(Integer tipo_aviso) {
        this.tipo_aviso = tipo_aviso;
    }

    public Integer getTransaccion_aviso() {
        return transaccion_aviso;
    }

    public void setTransaccion_aviso(Integer transaccion_aviso) {
        this.transaccion_aviso = transaccion_aviso;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getPublicado() {
        return publicado;
    }

    public void setPublicado(Integer publicado) {
        this.publicado = publicado;
    }

    public Integer getPromocion() {
        return promocion;
    }

    public void setPromocion(Integer promocion) {
        this.promocion = promocion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public Date getFecPublicacion() {
        return fecPublicacion;
    }

    public void setFecPublicacion(Date fecPublicacion) {
        this.fecPublicacion = fecPublicacion;
    }

    public Date getFecModificacion() {
        return fecModificacion;
    }

    public void setFecModificacion(Date fecModificacion) {
        this.fecModificacion = fecModificacion;
    }

    public Date getFecFinPublicacion() {
        return fecFinPublicacion;
    }

    public void setFecFinPublicacion(Date fecFinPublicacion) {
        this.fecFinPublicacion = fecFinPublicacion;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Integer getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(Integer subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Integer getTipoaviso() {
        return tipoaviso;
    }

    public void setTipoaviso(Integer tipoaviso) {
        this.tipoaviso = tipoaviso;
    }

    public Integer getTransaccionaviso() {
        return transaccionaviso;
    }

    public void setTransaccionAviso(Integer transaccionaviso) {
        this.transaccionaviso = transaccionaviso;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public Integer getOperacion() {
        return operacion;
    }

    public void setOperacion(Integer operacion) {
        this.operacion = operacion;
    }

    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }


}

