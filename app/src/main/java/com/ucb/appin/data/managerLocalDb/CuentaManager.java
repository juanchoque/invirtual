package com.ucb.appin.data.managerLocalDb;

import android.content.Context;
import android.util.Log;

import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.util.UtilInVirtual;

import io.reactivex.Observable;
import io.realm.Realm;

/**
 * Created by Juan choque on 2/24/2018.
 */

public class CuentaManager implements ICuentaManager{

    @Override
    public Cuenta getCuenta(Context context) {
        UtilInVirtual utilInmo = new UtilInVirtual(context);
        String imei = utilInmo.getNumberPhone();

        Realm realm = Realm.getDefaultInstance();
        Cuenta result = null;
        try {
            Cuenta cuentasBean = realm.where(Cuenta.class)
                    .equalTo("celular", imei)
                    .findFirst();

            if(cuentasBean != null){
                result = new Cuenta();
                result.setCelular(cuentasBean.getCelular());
                result.setNombres(cuentasBean.getNombres());
                result.setAparteno(cuentasBean.getAparteno());
                result.setAmaterno(cuentasBean.getAmaterno());
                result.setId(cuentasBean.getId());
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
    public boolean saveOrUpadateJarcode(Context context) {
        boolean result = false;
        Realm realm = Realm.getDefaultInstance();
        try {
            UtilInVirtual utilInmo = new UtilInVirtual(context);
            String imei = utilInmo.getNumberPhone();

            Cuenta cuenta = new Cuenta();
            cuenta.setId(17);
            cuenta.setNombres("Juan");
            cuenta.setAparteno("Choque");
            cuenta.setAmaterno("Baldiviezo");
            cuenta.setCelular(imei);
            cuenta.setsTipoAvisos("1,2");
            cuenta.setsTransaccionAvisos("1,3");

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(cuenta);
            realm.commitTransaction();

            result = true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
        return result;
    }

    @Override
    public Observable observaerSaveOrUpadate(Cuenta cuenta) {
        return Observable.defer(() -> {
            try {
                this.saveOrUpdateCuentas(cuenta);

                return Observable.just(cuenta);
            } catch(Throwable e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public void saveOrUpdateCuentas(Cuenta cuentasBean) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if(cuentasBean != null){
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(cuentasBean);
                realm.commitTransaction();

                Log.i("------>",">CREA CUENT CORRECTAMENTE>");
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
    public boolean getCuenta(Context context, String ids) {
        UtilInVirtual utilInmo = new UtilInVirtual(context);
        String imei = utilInmo.getNumberPhone();

        Realm realm = Realm.getDefaultInstance();
        boolean show = false;
        try {
            Cuenta cuentasBean = realm.where(Cuenta.class)
                    .equalTo("celular", imei)
                    .findFirst();

            if(cuentasBean != null){
                String idsTipo = cuentasBean.getsTipoAvisos();
                String idsTrans = cuentasBean.getsTransaccionAvisos();

                if(idsTipo == null || idsTrans == null){
                    show = true;
                }
                else if(idsTipo.isEmpty() || idsTrans.isEmpty()){
                    show = true;
                }
                else{
                    if(idsTipo.contains(ids) || idsTrans.contains(ids)){
                        show = true;
                    }
                }
            }
            else{
                show = true;
            }
        }catch (Exception err){
            show = false;
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
        return show;
    }

    public Observable<Object> observerGetAccount(Context context) {
        return Observable.defer(() -> {
            Realm realm = Realm.getDefaultInstance();
            Cuenta result = null;
            try {
                UtilInVirtual utilInmo = new UtilInVirtual(context);
                String imei = utilInmo.getNumberPhone();

                Cuenta cuentasBean = realm.where(Cuenta.class)
                        .equalTo("celular", imei)
                        .findFirst();

                if(cuentasBean != null){
                    result = new Cuenta();
                    result.setCelular(cuentasBean.getCelular());
                    result.setNombres(cuentasBean.getNombres());
                    result.setAparteno(cuentasBean.getAparteno());
                    result.setAmaterno(cuentasBean.getAmaterno());
                    result.setId(cuentasBean.getId());
                    result.setsTipoAvisos(cuentasBean.getsTipoAvisos());
                    result.setsTransaccionAvisos(cuentasBean.getsTransaccionAvisos());
                }

                return Observable.just(result);
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
