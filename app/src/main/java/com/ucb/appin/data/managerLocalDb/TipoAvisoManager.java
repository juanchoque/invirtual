package com.ucb.appin.data.managerLocalDb;

import android.util.Log;

import com.ucb.appin.data.model.TipoAviso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Juan choque on 2/24/2018.
 */

public class TipoAvisoManager implements ITipoAvisoManager{

    @Override
    public List<TipoAviso> getAllTipoAvisosJarcode() {
        List<TipoAviso>listTipoAvisos = new ArrayList<TipoAviso>();

        TipoAviso tipoAvisoBean = new TipoAviso();
        tipoAvisoBean.setId(1);
        tipoAvisoBean.setNombre("Casa/Chalet");

        TipoAviso tipoAvisoBean0 = new TipoAviso();
        tipoAvisoBean0.setId(2);
        tipoAvisoBean0.setNombre("Departamento");

        TipoAviso tipoAvisoBean1 = new TipoAviso();
        tipoAvisoBean1.setId(3);
        tipoAvisoBean1.setNombre("Habitacion");

        TipoAviso tipoAvisoBean2 = new TipoAviso();
        tipoAvisoBean2.setId(4);
        tipoAvisoBean2.setNombre("Local Comercial/Tienda");

        TipoAviso tipoAvisoBean3 = new TipoAviso();
        tipoAvisoBean3.setId(5);
        tipoAvisoBean3.setNombre("Oficina");

        TipoAviso tipoAvisoBean4 = new TipoAviso();
        tipoAvisoBean4.setId(6);
        tipoAvisoBean4.setNombre("Edificio");

        TipoAviso tipoAvisoBean5 = new TipoAviso();
        tipoAvisoBean5.setId(7);
        tipoAvisoBean5.setNombre("Otros");

        listTipoAvisos.add(tipoAvisoBean);
        listTipoAvisos.add(tipoAvisoBean0);
        listTipoAvisos.add(tipoAvisoBean1);
        listTipoAvisos.add(tipoAvisoBean2);
        listTipoAvisos.add(tipoAvisoBean3);
        listTipoAvisos.add(tipoAvisoBean4);
        listTipoAvisos.add(tipoAvisoBean5);

        return listTipoAvisos;
    }

    @Override
    public Observable observerGetAllTipoAvisos() {

        return Observable.defer(() -> {
            Realm realm = Realm.getDefaultInstance();
            try {
                List<TipoAviso>listTipoAvisos = new ArrayList<TipoAviso>();

                RealmResults<TipoAviso> tipoAvisos = realm.where(TipoAviso.class)
                        .findAllSorted("id", Sort.ASCENDING);

                if(tipoAvisos != null){
                    for(int i = 0;i < tipoAvisos.size();i++){
                        TipoAviso rtipoAviso = tipoAvisos.get(i);
                        TipoAviso ntipoAviso = new TipoAviso();
                        ntipoAviso.setId(rtipoAviso.getId());
                        ntipoAviso.setIdc(rtipoAviso.getIdc());
                        ntipoAviso.setNombre(rtipoAviso.getNombre());

                        listTipoAvisos.add(ntipoAviso);
                    }
                }

                return Observable.just(listTipoAvisos);
            } catch(Throwable e) {
                return Observable.error(e);
            }
            finally {
                if(realm != null){
                    realm.close();
                }
            }
        });
    }

    @Override
    public TipoAviso getTipoAvisoByIdJarcode(Integer idTipoAviso) {
        Realm realm = Realm.getDefaultInstance();
        TipoAviso result = null;
        try {
            TipoAviso tipoAviso = realm.where(TipoAviso.class)
                    .equalTo("id", idTipoAviso)
                    .findFirst();

            if(tipoAviso != null){
                result = new TipoAviso();
                result.setId(tipoAviso.getId());
                result.setNombre(tipoAviso.getNombre());
                result.setIdc(tipoAviso.getIdc());
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
    public Observable observerSaveOrUpdateJarcode() {
        return Observable.defer(() -> {
            try {

                TipoAviso tipoAvisoBean = new TipoAviso();
                tipoAvisoBean.setId(1);
                tipoAvisoBean.setNombre("Casa/Chalet");

                this.updateTipoAviso(tipoAvisoBean);

                TipoAviso tipoAvisoBean0 = new TipoAviso();
                tipoAvisoBean0.setId(2);
                tipoAvisoBean0.setNombre("Departamento");

                this.updateTipoAviso(tipoAvisoBean0);

                TipoAviso tipoAvisoBean1 = new TipoAviso();
                tipoAvisoBean1.setId(3);
                tipoAvisoBean1.setNombre("Habitacion");

                this.updateTipoAviso(tipoAvisoBean1);

                TipoAviso tipoAvisoBean2 = new TipoAviso();
                tipoAvisoBean2.setId(4);
                tipoAvisoBean2.setNombre("Local Comercial/Tienda");

                this.updateTipoAviso(tipoAvisoBean2);

                TipoAviso tipoAvisoBean3 = new TipoAviso();
                tipoAvisoBean3.setId(5);
                tipoAvisoBean3.setNombre("Oficina");

                this.updateTipoAviso(tipoAvisoBean3);

                TipoAviso tipoAvisoBean4 = new TipoAviso();
                tipoAvisoBean4.setId(6);
                tipoAvisoBean4.setNombre("Edificio");

                this.updateTipoAviso(tipoAvisoBean4);

                TipoAviso tipoAvisoBean5 = new TipoAviso();
                tipoAvisoBean5.setId(7);
                tipoAvisoBean5.setNombre("Otros");

                this.updateTipoAviso(tipoAvisoBean5);

                TipoAviso tipoAvisoBean6 = new TipoAviso();
                tipoAvisoBean6.setId(8);
                tipoAvisoBean6.setNombre("Todos");

                this.updateTipoAviso(tipoAvisoBean6);

                return Observable.just(tipoAvisoBean5);
            } catch(Throwable e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Observable observerSaveOrUpdateAll(List<TipoAviso> tipoAvisos) {
        return Observable.defer(() -> {
            try {

                for (int i = 0;i < tipoAvisos.size();i++){
                    TipoAviso cTipoAviso = tipoAvisos.get(i);
                    TipoAviso lTipoAviso = this.getTipoAvisoByIdc(cTipoAviso.getId());
                    if(lTipoAviso == null){
                        cTipoAviso.setIdc(cTipoAviso.getId());
                        this.addFronCloudTipoAviso(cTipoAviso);
                    }
                    else {
                        lTipoAviso.setNombre(cTipoAviso.getNombre());
                        lTipoAviso.setEstado(cTipoAviso.getEstado());

                        this.updateTipoAviso(lTipoAviso);
                    }
                }

                return Observable.just(tipoAvisos);
            } catch(Throwable e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public List<TipoAviso> getAllTipoAvisos() {
        Realm realm = Realm.getDefaultInstance();
        List<TipoAviso>listTipoAvisos = new ArrayList<TipoAviso>();
        try {

            RealmResults<TipoAviso> tipoAvisos = realm.where(TipoAviso.class)
                    .findAllSorted("id", Sort.DESCENDING);

            if(tipoAvisos != null){
                for(int i = 0;i < tipoAvisos.size();i++){
                    TipoAviso rtipoAviso = tipoAvisos.get(i);
                    TipoAviso ntipoAviso = new TipoAviso();
                    ntipoAviso.setId(rtipoAviso.getId());
                    ntipoAviso.setIdc(rtipoAviso.getIdc());
                    ntipoAviso.setNombre(rtipoAviso.getNombre());

                    listTipoAvisos.add(ntipoAviso);
                }
            }

        } catch(Exception e) {

        }
        finally {
            if(realm != null){
                realm.close();
            }
        }

        return listTipoAvisos;
    }

    @Override
    public void addFronCloudTipoAviso(TipoAviso cTipoAviso) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if(cTipoAviso != null){
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(cTipoAviso);
                realm.commitTransaction();
            }
        }catch (Exception er){
            Log.i("---ERROR INSERT A--->",">>" + er.getMessage());
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
    }

    @Override
    public TipoAviso getTipoAvisoByIdc(Integer id) {
        Realm realm = Realm.getDefaultInstance();
        TipoAviso result = null;
        try {
            TipoAviso tipoAvisoBean = realm.where(TipoAviso.class)
                    .equalTo("idc", id)
                    .findFirst();

            if(tipoAvisoBean != null){
                result = new TipoAviso();
                result.setId(tipoAvisoBean.getId());
                result.setIdc(tipoAvisoBean.getIdc());
                result.setNombre(tipoAvisoBean.getNombre());
                result.setEstado(tipoAvisoBean.getEstado());
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
    public void insertTipoAviso(TipoAviso tipoAvisoBean) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if(tipoAvisoBean != null){
                int id = getMaxIdTAviso();

                tipoAvisoBean.setId((id + 1));

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(tipoAvisoBean);
                realm.commitTransaction();
            }
        }catch (Exception er){
            Log.i("---ERROR INSERT A--->",">>" + er.getMessage());
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
    }

    @Override
    public int getMaxIdTAviso() {
        Realm realm = Realm.getDefaultInstance();
        int result = 0;
        try {
            Number aux = realm.where(TipoAviso.class)
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
    public void updateTipoAviso(TipoAviso tipoAvisoBean) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if(tipoAvisoBean != null){
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(tipoAvisoBean);
                realm.commitTransaction();
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
