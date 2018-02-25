package com.ucb.appin.data.managerLocalDb;

import android.util.Log;

import com.ucb.appin.data.model.TransaccionAviso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Juan choque on 2/24/2018.
 */

public class TransaccionAvisoManager implements ITransaccionAvisoManager {
    @Override
    public List<TransaccionAviso> getAllTransaccionAvisosJarcode() {
        List<TransaccionAviso>listTransaccionAvisos = new ArrayList<TransaccionAviso>();

        TransaccionAviso transaccionAviso = new TransaccionAviso();
        transaccionAviso.setId(1);
        transaccionAviso.setNombre("Venta");

        TransaccionAviso transaccionAviso0 = new TransaccionAviso();
        transaccionAviso0.setId(2);
        transaccionAviso0.setNombre("Alquiler");

        TransaccionAviso transaccionAviso1 = new TransaccionAviso();
        transaccionAviso1.setId(3);
        transaccionAviso1.setNombre("Anticretico");

        TransaccionAviso transaccionAviso2 = new TransaccionAviso();
        transaccionAviso2.setId(4);
        transaccionAviso2.setNombre("Otros");

        listTransaccionAvisos.add(transaccionAviso);
        listTransaccionAvisos.add(transaccionAviso0);
        listTransaccionAvisos.add(transaccionAviso1);
        listTransaccionAvisos.add(transaccionAviso2);

        return listTransaccionAvisos;
    }

    @Override
    public TransaccionAviso getTransaccionAvisoByIdJarcode(Integer idtran) {
        Realm realm = Realm.getDefaultInstance();
        TransaccionAviso result = null;
        try {
            TransaccionAviso transaccionAviso = realm.where(TransaccionAviso.class)
                    .equalTo("id", idtran)
                    .findFirst();

            if(transaccionAviso != null){
                result = new TransaccionAviso();
                result.setId(transaccionAviso.getId());
                result.setNombre(transaccionAviso.getNombre());
                result.setIdc(transaccionAviso.getIdc());
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

                TransaccionAviso transaccionAviso = new TransaccionAviso();
                transaccionAviso.setId(1);
                transaccionAviso.setNombre("Venta");

                saveOrUpdateTransaccionAviso(transaccionAviso);

                TransaccionAviso transaccionAviso0 = new TransaccionAviso();
                transaccionAviso0.setId(2);
                transaccionAviso0.setNombre("Alquiler");

                saveOrUpdateTransaccionAviso(transaccionAviso0);

                TransaccionAviso transaccionAviso1 = new TransaccionAviso();
                transaccionAviso1.setId(3);
                transaccionAviso1.setNombre("Anticretico");

                saveOrUpdateTransaccionAviso(transaccionAviso1);

                TransaccionAviso transaccionAviso2 = new TransaccionAviso();
                transaccionAviso2.setId(4);
                transaccionAviso2.setNombre("Otros");

                TransaccionAviso transaccionAviso3 = new TransaccionAviso();
                transaccionAviso3.setId(5);
                transaccionAviso3.setNombre("Todos");

                saveOrUpdateTransaccionAviso(transaccionAviso3);

                return Observable.just(transaccionAviso2);
            } catch(Throwable e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Observable observerSaveOrUpdateAll(List<TransaccionAviso> listTransaccionAvisos) {
        return Observable.defer(() -> {
            try {
                for (int i = 0;i < listTransaccionAvisos.size();i++){
                    TransaccionAviso cTransaccionAvisoBean = listTransaccionAvisos.get(i);
                    TransaccionAviso lTransaccionAvisoBean = this.getTransaccionAvisoByIdc(cTransaccionAvisoBean.getId());
                    if(lTransaccionAvisoBean == null){
                        cTransaccionAvisoBean.setIdc(cTransaccionAvisoBean.getId());
                        //this.saveOrUpdateTransaccionAviso(cTransaccionAvisoBean);
                        this.addFronCloudTransaccionAviso(cTransaccionAvisoBean);
                    }
                    else {
                        lTransaccionAvisoBean.setNombre(cTransaccionAvisoBean.getNombre());
                        lTransaccionAvisoBean.setEstado(cTransaccionAvisoBean.getEstado());

                        this.updateTransaccionAviso(lTransaccionAvisoBean);
                    }
                }

                return Observable.just(listTransaccionAvisos);
            } catch(Throwable e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public void addFronCloudTransaccionAviso(TransaccionAviso cTransaccionAvisoBean) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if(cTransaccionAvisoBean != null){
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(cTransaccionAvisoBean);
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
    public TransaccionAviso getTransaccionAvisoByIdc(Integer id) {
        Realm realm = Realm.getDefaultInstance();
        TransaccionAviso result = null;
        try {
            TransaccionAviso transaccionAviso = realm.where(TransaccionAviso.class)
                    .equalTo("idc", id)
                    .findFirst();

            if(transaccionAviso != null){
                result = new TransaccionAviso();
                result.setId(transaccionAviso.getId());
                result.setIdc(transaccionAviso.getIdc());
                result.setNombre(transaccionAviso.getNombre());
                result.setEstado(transaccionAviso.getEstado());
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
    public void saveOrUpdateTransaccionAviso(TransaccionAviso transaccionAviso) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if(transaccionAviso != null){
                int id = getMaxIdTrAviso();

                transaccionAviso.setId((id + 1));

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(transaccionAviso);
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
    public int getMaxIdTrAviso() {
        Realm realm = Realm.getDefaultInstance();
        int result = 0;
        try {
            Number aux = realm.where(TransaccionAviso.class)
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
    public void updateTransaccionAviso(TransaccionAviso transaccionAviso) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if(transaccionAviso != null){
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(transaccionAviso);
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

    @Override
    public Observable<Object> observerGetAllTransaccionAvisos() {
        return Observable.defer(() -> {
            Realm realm = Realm.getDefaultInstance();
            try {
                List<TransaccionAviso>listTransAvisos = new ArrayList<TransaccionAviso>();

                RealmResults<TransaccionAviso> transaccionAvisos = realm.where(TransaccionAviso.class)
                        .findAllSorted("id", Sort.ASCENDING);

                if(transaccionAvisos != null){
                    for(int i = 0;i < transaccionAvisos.size();i++){
                        TransaccionAviso rtransaccionAviso = transaccionAvisos.get(i);
                        TransaccionAviso ntransaccionAviso = new TransaccionAviso();
                        ntransaccionAviso.setId(rtransaccionAviso.getId());
                        ntransaccionAviso.setIdc(rtransaccionAviso.getIdc());
                        ntransaccionAviso.setNombre(rtransaccionAviso.getNombre());

                        listTransAvisos.add(ntransaccionAviso);
                    }
                }

                return Observable.just(listTransAvisos);
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

}
