package com.ucb.appin.data.managerLocalDb;

import android.util.Log;

import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.Bitacora;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Juan choque on 2/24/2018.
 */

public class BitacoraManager implements IBitacoraManager {

    @Override
    public Bitacora getBitacoraByIdBean(int id) {
        Realm realm = Realm.getDefaultInstance();
        Bitacora result = null;
        try {
            Bitacora bitacoraBean = realm.where(Bitacora.class)
                    .equalTo("idBean", id)
                    .findFirst();

            if(bitacoraBean != null){
                result = new Bitacora();
                result.setId(bitacoraBean.getId());
                result.setIdc(bitacoraBean.getIdc());
                result.setIdBean(bitacoraBean.getIdBean());
                result.setOperacion(bitacoraBean.getOperacion());
                result.setEstado(bitacoraBean.getEstado());
            }

        }catch (Exception err){
            result = null;
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
        return result;
    }

    @Override
    public void removeBitacoraById(int id) {
        Realm lrealm = Realm.getDefaultInstance();
        try{
            Bitacora bitacoraBean = getBitacoraById(id);
            /*BitacoraBean bitacoraBean = lrealm.where(BitacoraBean.class)
                    .equalTo("id", id)
                    .findFirst();*/

            if(bitacoraBean != null){
                lrealm.beginTransaction();
                bitacoraBean.setEstado("X");
                lrealm.copyToRealmOrUpdate(bitacoraBean);
                lrealm.commitTransaction();

                lrealm.refresh();
            }
        }catch (Exception err){
            Log.v("....>", ">ERROR EN BITA TO DELETE>" + err.getMessage());
        }
        finally {
            if(lrealm != null){
                lrealm.close();
            }
        }
    }

    @Override
    public Bitacora getBitacoraById(int id) {
        Realm realm = Realm.getDefaultInstance();
        Bitacora result = null;
        try {
            Bitacora bitacoraBean = realm.where(Bitacora.class)
                    .equalTo("id", id)
                    .findFirst();

            if(bitacoraBean != null){
                result = new Bitacora();

                result.setId(bitacoraBean.getId());
                result.setIdBean(bitacoraBean.getIdBean());
                result.setOperacion(bitacoraBean.getOperacion());
                result.setIdc(bitacoraBean.getIdc());
                result.setTelefono(bitacoraBean.getTelefono());
            }

        }catch (Exception err){
            result = null;
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
        return result;
    }

    @Override
    public int getMaxId() {
        Realm realm = Realm.getDefaultInstance();
        int result = 0;
        try {
            Number aux = realm.where(Bitacora.class)
                    .max("id");
            if(aux != null){
                result = aux.intValue();
            }
        }catch (Exception er){
            result = 0;
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
        return result;
    }

    @Override
    public void saveOrUpdate(Bitacora bitacoraBean) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(bitacoraBean);
            realm.commitTransaction();

            realm.refresh();
        }catch (Exception er){
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
    }

    @Override
    public void insertBitacora(Bitacora bitacoraBean) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            bitacoraBean.setEstado("A");
            realm.copyToRealmOrUpdate(bitacoraBean);
            realm.commitTransaction();

            realm.refresh();
        }catch (Exception err){

        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
    }

    @Override
    public List<Bitacora> listBitacora() {
        Realm realm = Realm.getDefaultInstance();
        List<Bitacora>result = new ArrayList<Bitacora>();
        try {
            RealmResults<Bitacora> bitacoras = realm.where(Bitacora.class)
                    .equalTo("estado","A")
                    .findAll();

            if(bitacoras != null){
                for (Bitacora bitacoraBean : bitacoras){
                    Bitacora nbitacoraBean = new Bitacora();
                    nbitacoraBean.setId(bitacoraBean.getId());
                    nbitacoraBean.setIdc(bitacoraBean.getIdc());
                    nbitacoraBean.setIdBean(bitacoraBean.getIdBean());
                    nbitacoraBean.setOperacion(bitacoraBean.getOperacion());
                    nbitacoraBean.setTelefono(bitacoraBean.getTelefono());
                    result.add(nbitacoraBean);
                }
            }
            Log.v(">>>>>>", ">>EN DB BI>>" + bitacoras);
        }catch (Exception err){
            result = new ArrayList<Bitacora>();
            Log.v(">>>>>>", ">>ERROR EN DB BI>>" + err.getMessage());
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }

        return result;
    }

    @Override
    public Observable observerListBitacora() {
        return Observable.defer(() -> {
            try {
                List<Aviso>listAvisos = new ArrayList<Aviso>();
                List<Bitacora> listBitacora = listBitacora();

                for (int i = 0;i < listBitacora.size();i++){
                    Bitacora bitacoraBean = listBitacora.get(i);
                    Aviso avisosBean = getAvisoById(bitacoraBean.getIdBean());
                    avisosBean.setIdBitacora(bitacoraBean.getId());
                    avisosBean.setOperacion(bitacoraBean.getOperacion());

                    listAvisos.add(avisosBean);
                }

                return Observable.just(listAvisos);
            } catch(Throwable e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Aviso getAvisoById(int id) {
        Realm realm = Realm.getDefaultInstance();
        Aviso result = null;
        try {
            Aviso avisosBean = realm.where(Aviso.class)
                    .equalTo("id", id)
                    .findFirst();

            if(avisosBean != null) {
                result = new Aviso();

                result.setId(avisosBean.getId());
                result.setEstado(avisosBean.getEstado());
                result.setTitulo(avisosBean.getTitulo());
                result.setDescripcion(avisosBean.getDescripcion());
                result.setDireccion(avisosBean.getDireccion());
                result.setPrecio(avisosBean.getPrecio());
                result.setIdc(avisosBean.getIdc());
                result.setLatitud(avisosBean.getLatitud());
                result.setLongitud(avisosBean.getLongitud());
                result.setImagen(avisosBean.getImagen());
                result.setTipoaviso(avisosBean.getTipoaviso());
                result.setTransaccionAviso(avisosBean.getTransaccionaviso());
                result.setTelefono(avisosBean.getTelefono());
                result.setCuenta(avisosBean.getCuenta());
                result.setOrden(avisosBean.getOrden());
            }

        }catch (Exception err){
            Log.v(".....>",">error al recupera datos by id>" + err.getMessage());
            result = null;
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
        return result;
    }

    @Override
    public Observable observerUpdateBitacora(List<Bitacora> listBitacora) {
        return Observable.defer(() -> {
            try {

                for(int i = 0;i < listBitacora.size();i++){
                    Bitacora bitacoraBean = listBitacora.get(i);
                    //update aviso in list
                    Aviso avisosBean = getAvisoById(bitacoraBean.getIdBean());
                    if(avisosBean != null){
                        avisosBean.setIdc(bitacoraBean.getIdc() + "");
                        updateAviso(avisosBean);
                    }
                    //remove in bitacora
                    removeBitacoraById(bitacoraBean.getId());
                }

                return Observable.just(listBitacora);
            } catch(Throwable e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public void updateAviso(Aviso avisosBean) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if(avisosBean != null){
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(avisosBean);
                realm.commitTransaction();

                realm.refresh();
            }
        }catch (Exception er){
            Log.i("---ERROR UPDATE A--->",">>" + er.getMessage());
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
    }

}
