package com.ucb.appin.data.managerLocalDb;

import android.content.Context;
import android.util.Log;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.Bitacora;
import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.util.ConstInVirtual;
import com.ucb.appin.util.UtilInVirtual;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Juan choque on 10/12/2017.
 */

public class AvisoManager implements IAvisoManager {
    private Context context;

    public AvisoManager(Context context) {
        this.context = context;
    }

    public AvisoManager() {

    }

    @Override
    public List<Aviso> getAllAvisos() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<Aviso>rListAvisos = null;
        try {
            rListAvisos = new ArrayList<Aviso>();

            RealmResults<Aviso> avisos = realm.where(Aviso.class)
                    .equalTo("estado", ConstInVirtual.ACTIVE)
                    .findAllSorted("id", Sort.DESCENDING);

            if(avisos != null){
                for(int i = 0;i < avisos.size();i++){
                    Aviso rAvisosBean = avisos.get(i);
                    Aviso avisosBean = new Aviso();
                    avisosBean.setId(rAvisosBean.getId());
                    avisosBean.setIdc(rAvisosBean.getIdc());
                    avisosBean.setTitulo(rAvisosBean.getTitulo());
                    avisosBean.setDescripcion(rAvisosBean.getDescripcion());
                    avisosBean.setPrecio(rAvisosBean.getPrecio());
                    avisosBean.setTelefono(rAvisosBean.getTelefono());
                    avisosBean.setDireccion(rAvisosBean.getDireccion());
                    avisosBean.setLatitud(rAvisosBean.getLatitud());
                    avisosBean.setLongitud(rAvisosBean.getLongitud());
                    avisosBean.setTipoaviso(rAvisosBean.getTipoaviso());
                    avisosBean.setTransaccionAviso(rAvisosBean.getTransaccionaviso());
                    avisosBean.setImagen(rAvisosBean.getImagen());

                    rListAvisos.add(avisosBean);
                }
            }

            realm.commitTransaction();
            Log.v("=====>",">>CANT LIST AVISOS>>" + rListAvisos.size());
        }catch (Exception er){
            Log.v("=====>",">ERROR LIST AVISOS>" + er.getMessage());
            rListAvisos = new ArrayList<>();
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }

        return rListAvisos;
    }

    @Override
    public List<Aviso> getAllAvisos(Aviso aviso) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<Aviso>rListAvisos = null;
        try {
            rListAvisos = new ArrayList<Aviso>();

            RealmResults<Aviso> avisos = realm.where(Aviso.class)
                    .equalTo("estado", ConstInVirtual.ACTIVE)
                    .findAllSorted("id", Sort.DESCENDING);

            if(avisos != null){
                for(int i = 0;i < avisos.size();i++){
                    Aviso rAvisosBean = avisos.get(i);
                    Aviso avisosBean = new Aviso();
                    avisosBean.setId(rAvisosBean.getId());
                    avisosBean.setIdc(rAvisosBean.getIdc());
                    avisosBean.setTitulo(rAvisosBean.getTitulo());
                    avisosBean.setDescripcion(rAvisosBean.getDescripcion());
                    avisosBean.setPrecio(rAvisosBean.getPrecio());
                    avisosBean.setTelefono(rAvisosBean.getTelefono());
                    avisosBean.setDireccion(rAvisosBean.getDireccion());
                    avisosBean.setLatitud(rAvisosBean.getLatitud());
                    avisosBean.setLongitud(rAvisosBean.getLongitud());
                    avisosBean.setTipoaviso(rAvisosBean.getTipoaviso());
                    avisosBean.setTransaccionAviso(rAvisosBean.getTransaccionaviso());
                    avisosBean.setImagen(rAvisosBean.getImagen());

                    rListAvisos.add(avisosBean);
                }
            }

            realm.commitTransaction();
            Log.v("=====>",">>CANT LIST AVISOS>>" + rListAvisos.size());
        }catch (Exception er){
            Log.v("=====>",">ERROR LIST AVISOS>" + er.getMessage());
            rListAvisos = new ArrayList<>();
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }

        return rListAvisos;
    }

    @Override
    public List<Aviso> getAllAvisos(Aviso aviso, Integer tipoAviso, Integer transaccionAviso) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<Aviso>rListAvisos = null;
        try {
            rListAvisos = new ArrayList<Aviso>();

            RealmResults<Aviso> avisos = realm.where(Aviso.class)
                    .equalTo("estado", ConstInVirtual.ACTIVE)
                    .and()
                    .equalTo("tipoaviso", tipoAviso)
                    .and()
                    .equalTo("transaccionaviso", transaccionAviso)
                    .findAllSorted("id", Sort.DESCENDING);

            if(avisos != null){
                for(int i = 0;i < avisos.size();i++){
                    Aviso rAvisosBean = avisos.get(i);
                    Aviso avisosBean = new Aviso();
                    avisosBean.setId(rAvisosBean.getId());
                    avisosBean.setIdc(rAvisosBean.getIdc());
                    avisosBean.setTitulo(rAvisosBean.getTitulo());
                    avisosBean.setDescripcion(rAvisosBean.getDescripcion());
                    avisosBean.setPrecio(rAvisosBean.getPrecio());
                    avisosBean.setTelefono(rAvisosBean.getTelefono());
                    avisosBean.setDireccion(rAvisosBean.getDireccion());
                    avisosBean.setLatitud(rAvisosBean.getLatitud());
                    avisosBean.setLongitud(rAvisosBean.getLongitud());
                    avisosBean.setTipoaviso(rAvisosBean.getTipoaviso());
                    avisosBean.setTransaccionAviso(rAvisosBean.getTransaccionaviso());
                    avisosBean.setImagen(rAvisosBean.getImagen());

                    rListAvisos.add(avisosBean);
                }
            }

            realm.commitTransaction();
            Log.v("=====>",">>CANT LIST AVISOS>>" + rListAvisos.size());
        }catch (Exception er){
            Log.v("=====>",">ERROR LIST AVISOS>" + er.getMessage());
            rListAvisos = new ArrayList<>();
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }

        return rListAvisos;
    }

    @Override
    public Aviso getAviso(int id) {
        Realm realm = Realm.getDefaultInstance();
        Aviso result = null;
        try {
            Aviso aviso = realm.where(Aviso.class)
                    .equalTo("id", id)
                    .findFirst();

            if(aviso != null){
                result = new Aviso();
                result.setId(aviso.getId());
                result.setIdc(aviso.getIdc());
                result.setTitulo(aviso.getTitulo());
                result.setDescripcion(aviso.getDescripcion());
                result.setPrecio(aviso.getPrecio());
                result.setTelefono(aviso.getTelefono());
                result.setDireccion(aviso.getDireccion());
                result.setLatitud(aviso.getLatitud());
                result.setLongitud(aviso.getLongitud());
                result.setTipoaviso(aviso.getTipoaviso());
                result.setTransaccionAviso(aviso.getTransaccionaviso());
                result.setImagen(aviso.getImagen());
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
    public Observable observerSaveUpateAviso(Aviso aviso, final int tipoOperacion) {
        return Observable.defer(() -> {
            Realm realm = Realm.getDefaultInstance();
            try {
                int rtipoOperacion = tipoOperacion;

                BitacoraManager bitacoraManager = new BitacoraManager();
                CuentaManager cuentaManager = new CuentaManager();

                if(rtipoOperacion == ConstInVirtual.ADD_OPERACION){
                    int id = getMaxId();
                    int idAviso = id + 1;
                    aviso.setId(idAviso);
                }
                else if(rtipoOperacion == ConstInVirtual.EDIT_OPERATION){
                    Bitacora bitacoraBean = bitacoraManager.getBitacoraByIdBean(aviso.getId());
                    if(bitacoraBean != null){
                        if(bitacoraBean.getEstado().equals("A")){
                            bitacoraManager.removeBitacoraById(bitacoraBean.getId());
                            rtipoOperacion = ConstInVirtual.ADD_OPERACION;
                        }
                    }
                }

                //get account
                Cuenta cuentasBean = cuentaManager.getCuenta(context);

                if(cuentasBean != null){
                    aviso.setFecPublicacion(new Date());
                    aviso.setEstado(ConstInVirtual.ACTIVE);
                    aviso.setCuenta(cuentasBean.getId());

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(aviso);
                    realm.commitTransaction();

                    realm.refresh();
                    try {
                        realm.close();
                    }catch (Exception ex){};

                    //get id max id bitacora
                    int id = bitacoraManager.getMaxId();

                    //test only
                    Bitacora bitacoraBean = new Bitacora();
                    int idBitacora = (id + 1);
                    bitacoraBean.setId(idBitacora);
                    bitacoraBean.setOperacion(rtipoOperacion);// 1 = add, 2 = mod, 3 = del
                    bitacoraBean.setIdBean(aviso.getId());
                    bitacoraBean.setEstado("A");

                    bitacoraManager.saveOrUpdate(bitacoraBean);

                    //end test
                    Log.i("---ADD CORRECTO--->",">>");
                }

                return Observable.just(aviso);
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
    public boolean saveUpateAviso(Aviso aviso, int tipoOperacion) {

        Realm realm = Realm.getDefaultInstance();
        try {
            BitacoraManager bitacoraManager = new BitacoraManager();
            CuentaManager cuentaManager = new CuentaManager();

            if(tipoOperacion == ConstInVirtual.ADD_OPERACION){
                int id = getMaxId();
                int idAviso = id + 1;
                aviso.setId(idAviso);
            }
            else if(tipoOperacion == ConstInVirtual.EDIT_OPERATION){
                Bitacora bitacoraBean = bitacoraManager.getBitacoraByIdBean(aviso.getId());
                if(bitacoraBean != null){
                    if(bitacoraBean.getEstado().equals("A")){
                        bitacoraManager.removeBitacoraById(bitacoraBean.getId());
                        tipoOperacion = ConstInVirtual.ADD_OPERACION;
                    }
                }
            }

            //get account
            Cuenta cuentasBean = cuentaManager.getCuenta(context);

            if(cuentasBean != null){
                aviso.setFecPublicacion(new Date());
                aviso.setEstado(ConstInVirtual.ACTIVE);
                aviso.setCuenta(cuentasBean.getId());

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(aviso);
                realm.commitTransaction();

                realm.refresh();
                try {
                    realm.close();
                }catch (Exception ex){};

                //get id max id bitacora
                int id = bitacoraManager.getMaxId();

                //test only
                Bitacora bitacoraBean = new Bitacora();
                int idBitacora = (id + 1);
                bitacoraBean.setId(idBitacora);
                bitacoraBean.setOperacion(tipoOperacion);// 1 = add, 2 = mod, 3 = del
                bitacoraBean.setIdBean(aviso.getId());
                bitacoraBean.setEstado("A");

                bitacoraManager.saveOrUpdate(bitacoraBean);

            }
            else{
                return false;
            }
        }catch (Exception er){
            Log.i("---ERROR--->",">>" + er.getMessage());
            aviso = null;
            er.printStackTrace();
            return false;
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }

        return true;
    }

    @Override
    public int getMaxId() {
        int result = 0;
        Realm realm = Realm.getDefaultInstance();
        try {
            Number aux = realm.where(Aviso.class)
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
    public Observable observerSaveDeleteAviso(final Aviso aviso) {
        return Observable.defer(() -> {
            Realm realm = Realm.getDefaultInstance();
            try {
                Aviso finalAviso = aviso;
                boolean deleted = false;
                Aviso rAvisosBean = getAviso(finalAviso.getId());

                if(rAvisosBean != null){
                    rAvisosBean.setEstado(ConstInVirtual.INACTIVE);//for delete

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(rAvisosBean);
                    realm.commitTransaction();

                    BitacoraManager bitacoraManager = new BitacoraManager();

                    Bitacora rbitacoraBean = bitacoraManager.getBitacoraByIdBean(rAvisosBean.getId());
                    if(rbitacoraBean != null){
                        if(rbitacoraBean.getEstado().equals("A")){
                            bitacoraManager.removeBitacoraById(rbitacoraBean.getId());
                            deleted = true;
                        }
                    }

                    if(!deleted){
                        //get id max id bitacora
                        int id = bitacoraManager.getMaxId();

                        //test only
                        Bitacora bitacoraBean = new Bitacora();
                        int idBitacora = (id + 1);
                        bitacoraBean.setId(idBitacora);
                        bitacoraBean.setOperacion(3);// 1 = add, 2 = mod, 3 = del
                        bitacoraBean.setIdBean(rAvisosBean.getId());
                        bitacoraManager.insertBitacora(bitacoraBean);
                        //end test
                    }

                    deleted = true;
                }

                if(!deleted){
                    finalAviso = null;
                }

                return Observable.just(finalAviso);
            } catch(Throwable e) {
                e.printStackTrace();
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
    public boolean deleteAviso(Aviso aviso) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            boolean deleted = false;
            Aviso rAvisosBean = realm.where(Aviso.class)
                    .equalTo("id",aviso.getId())
                    .findFirst();

            if(rAvisosBean != null){

                rAvisosBean.setEstado(ConstInVirtual.INACTIVE);//for delete
                realm.copyToRealmOrUpdate(rAvisosBean);
                realm.commitTransaction();

                BitacoraManager bitacoraManager = new BitacoraManager();

                Bitacora rbitacoraBean = bitacoraManager.getBitacoraByIdBean(rAvisosBean.getId());
                if(rbitacoraBean != null){
                    if(rbitacoraBean.getEstado().equals("A")){
                        bitacoraManager.removeBitacoraById(rbitacoraBean.getId());
                        deleted = true;
                    }
                }

                if(!deleted){
                    //get id max id bitacora
                    int id = bitacoraManager.getMaxId();

                    //test only
                    Bitacora bitacoraBean = new Bitacora();
                    int idBitacora = (id + 1);
                    bitacoraBean.setId(idBitacora);
                    bitacoraBean.setOperacion(3);// 1 = add, 2 = mod, 3 = del
                    bitacoraBean.setIdBean(rAvisosBean.getId());

                    bitacoraManager.insertBitacora(bitacoraBean);
                    //end test
                }
                Log.v("=====>",">DELETE CORRECT ELIMINACION>");
            }
        }catch (Exception er){
            Log.v("=====>",">ERROR DELETE>" + er.getMessage());
            return false;
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }

        return true;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Observable observerAvisosBackground() {
        return Observable.defer(() -> {
            Realm realm = Realm.getDefaultInstance();
            try {
                RealmResults<Aviso> avisos = realm.where(Aviso.class)
                        .equalTo("estado", ConstInVirtual.ACTIVE)
                        .findAll();

                List<Aviso>rListAvisos = new ArrayList<Aviso>();

                if(avisos != null){
                    for(int i = 0;i < avisos.size();i++){
                        Aviso rAvisosBean = avisos.get(i);
                        Aviso avisosBean = new Aviso();
                        avisosBean.setId(rAvisosBean.getId());
                        avisosBean.setIdc(rAvisosBean.getIdc());
                        avisosBean.setTitulo(rAvisosBean.getTitulo());
                        avisosBean.setDescripcion(rAvisosBean.getDescripcion());
                        avisosBean.setPrecio(rAvisosBean.getPrecio());
                        avisosBean.setTelefono(rAvisosBean.getTelefono());
                        avisosBean.setDireccion(rAvisosBean.getDireccion());
                        avisosBean.setLatitud(rAvisosBean.getLatitud());
                        avisosBean.setLongitud(rAvisosBean.getLongitud());
                        avisosBean.setTipoaviso(rAvisosBean.getTipoaviso());
                        avisosBean.setTransaccionAviso(rAvisosBean.getTransaccionaviso());
                        avisosBean.setImagen(rAvisosBean.getImagen());

                        rListAvisos.add(avisosBean);
                    }
                }

                return Observable.just(rListAvisos);
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
    public Observable observerAvisosBackground(TipoAviso tipoAviso, TransaccionAviso transaccionAviso) {
        return Observable.defer(() -> {
            Realm realm = Realm.getDefaultInstance();
            try {
                RealmResults<Aviso> avisos = null;

                /*Log.v("----------->",">>>>>TIPO AVIOSO>>>>>>" + tipoAviso);
                Log.v("----------->",">>>>>TRANSACCION AVIOSO>>>>>>" + transaccionAviso);
                if(tipoAviso != null){
                    Log.v("----------->",">>>>>TIPO AVIOSO val>>>>>>" + tipoAviso.getId() + "<@@@@@>" + tipoAviso.getNombre());
                }
                if(transaccionAviso != null){
                    Log.v("----------->",">>>>>TRANSACCION AVIOSO val>>>>>>" + transaccionAviso.getId() + "<@@@@@>" + transaccionAviso.getNombre());
                }*/

                if(tipoAviso == null && transaccionAviso != null){
                    if(!transaccionAviso.getNombre().equals("Todos")){
                        avisos = realm.where(Aviso.class)
                                .equalTo("estado", ConstInVirtual.ACTIVE)
                                .and()
                                .equalTo("transaccionaviso", transaccionAviso.getId())
                                .findAll();
                    }
                }
                else if(tipoAviso != null && transaccionAviso == null){
                    if(!tipoAviso.getNombre().equals("Todos")){
                        avisos = realm.where(Aviso.class)
                                .equalTo("estado", ConstInVirtual.ACTIVE)
                                .and()
                                .equalTo("tipoaviso", tipoAviso.getId())
                                .findAll();
                    }
                }
                else if(tipoAviso != null && transaccionAviso != null){
                    if((tipoAviso.getNombre().equals("Todos") && !transaccionAviso.getNombre().equals("Todos"))){
                        avisos = realm.where(Aviso.class)
                                .equalTo("estado", ConstInVirtual.ACTIVE)
                                .and()
                                .equalTo("transaccionaviso", transaccionAviso.getId())
                                .findAll();
                    }
                    else if(!tipoAviso.getNombre().equals("Todos") && !transaccionAviso.getNombre().equals("Todos")){
                        avisos = realm.where(Aviso.class)
                                .equalTo("estado", ConstInVirtual.ACTIVE)
                                .and()
                                .equalTo("tipoaviso", tipoAviso.getId())
                                .and()
                                .equalTo("transaccionaviso", transaccionAviso.getId())
                                .findAll();
                    }
                    else if((!tipoAviso.getNombre().equals("Todos") && transaccionAviso.getNombre().equals("Todos"))){
                        avisos = realm.where(Aviso.class)
                                .equalTo("estado", ConstInVirtual.ACTIVE)
                                .and()
                                .equalTo("tipoaviso", tipoAviso.getId())
                                .findAll();
                    }
                }
                else{
                    avisos = realm.where(Aviso.class)
                            .equalTo("estado", ConstInVirtual.ACTIVE)
                            .findAll();
                }

                List<Aviso>rListAvisos = new ArrayList<Aviso>();

                if(avisos != null){
                    for(int i = 0;i < avisos.size();i++){
                        Aviso rAvisosBean = avisos.get(i);
                        Aviso avisosBean = new Aviso();
                        avisosBean.setId(rAvisosBean.getId());
                        avisosBean.setIdc(rAvisosBean.getIdc());
                        avisosBean.setTitulo(rAvisosBean.getTitulo());
                        avisosBean.setDescripcion(rAvisosBean.getDescripcion());
                        avisosBean.setPrecio(rAvisosBean.getPrecio());
                        avisosBean.setTelefono(rAvisosBean.getTelefono());
                        avisosBean.setDireccion(rAvisosBean.getDireccion());
                        avisosBean.setLatitud(rAvisosBean.getLatitud());
                        avisosBean.setLongitud(rAvisosBean.getLongitud());
                        avisosBean.setTipoaviso(rAvisosBean.getTipoaviso());
                        avisosBean.setTransaccionAviso(rAvisosBean.getTransaccionaviso());
                        avisosBean.setImagen(rAvisosBean.getImagen());

                        rListAvisos.add(avisosBean);
                    }
                }

                return Observable.just(rListAvisos);
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
    public Observable<Object> observerMyAvisosBackground(TipoAviso tipoAviso, TransaccionAviso transaccionAviso) {
        return Observable.defer(() -> {
            Realm realm = Realm.getDefaultInstance();
            try {
                CuentaManager cuentaManager = new CuentaManager();
                Cuenta cuenta = cuentaManager.getCuenta(context);

                RealmResults<Aviso> avisos = null;

                if(tipoAviso == null && transaccionAviso != null){
                    if(!transaccionAviso.getNombre().equals("Todos")){
                        avisos = realm.where(Aviso.class)
                                .equalTo("estado", ConstInVirtual.ACTIVE)
                                .and()
                                .equalTo("cuenta", cuenta.getId())
                                .and()
                                .equalTo("transaccionaviso", transaccionAviso.getId())
                                .findAll();
                    }
                }
                else if(tipoAviso != null && transaccionAviso == null){
                    if(!tipoAviso.getNombre().equals("Todos")){
                        avisos = realm.where(Aviso.class)
                                .equalTo("estado", ConstInVirtual.ACTIVE)
                                .and()
                                .equalTo("cuenta", cuenta.getId())
                                .and()
                                .equalTo("tipoaviso", tipoAviso.getId())
                                .findAll();
                    }
                }
                else if(tipoAviso != null && transaccionAviso != null){
                    if((tipoAviso.getNombre().equals("Todos") && !transaccionAviso.getNombre().equals("Todos"))){
                        avisos = realm.where(Aviso.class)
                                .equalTo("estado", ConstInVirtual.ACTIVE)
                                .and()
                                .equalTo("cuenta", cuenta.getId())
                                .and()
                                .equalTo("transaccionaviso", transaccionAviso.getId())
                                .findAll();
                    }
                    else if(!tipoAviso.getNombre().equals("Todos") && !transaccionAviso.getNombre().equals("Todos")){
                        avisos = realm.where(Aviso.class)
                                .equalTo("estado", ConstInVirtual.ACTIVE)
                                .and()
                                .equalTo("cuenta", cuenta.getId())
                                .and()
                                .equalTo("tipoaviso", tipoAviso.getId())
                                .and()
                                .equalTo("transaccionaviso", transaccionAviso.getId())
                                .findAll();
                    }
                    else if((!tipoAviso.getNombre().equals("Todos") && transaccionAviso.getNombre().equals("Todos"))){
                        avisos = realm.where(Aviso.class)
                                .equalTo("estado", ConstInVirtual.ACTIVE)
                                .and()
                                .equalTo("cuenta", cuenta.getId())
                                .and()
                                .equalTo("tipoaviso", tipoAviso.getId())
                                .findAll();
                    }
                }
                else{
                    avisos = realm.where(Aviso.class)
                            .equalTo("estado", ConstInVirtual.ACTIVE)
                            .and()
                            .equalTo("cuenta", cuenta.getId())
                            .findAll();
                }

                List<Aviso>rListAvisos = new ArrayList<Aviso>();

                if(avisos != null){
                    for(int i = 0;i < avisos.size();i++){
                        Aviso rAvisosBean = avisos.get(i);
                        Aviso avisosBean = new Aviso();
                        avisosBean.setId(rAvisosBean.getId());
                        avisosBean.setIdc(rAvisosBean.getIdc());
                        avisosBean.setTitulo(rAvisosBean.getTitulo());
                        avisosBean.setDescripcion(rAvisosBean.getDescripcion());
                        avisosBean.setPrecio(rAvisosBean.getPrecio());
                        avisosBean.setTelefono(rAvisosBean.getTelefono());
                        avisosBean.setDireccion(rAvisosBean.getDireccion());
                        avisosBean.setLatitud(rAvisosBean.getLatitud());
                        avisosBean.setLongitud(rAvisosBean.getLongitud());
                        avisosBean.setTipoaviso(rAvisosBean.getTipoaviso());
                        avisosBean.setTransaccionAviso(rAvisosBean.getTransaccionaviso());
                        avisosBean.setImagen(rAvisosBean.getImagen());

                        rListAvisos.add(avisosBean);
                    }
                }

                return Observable.just(rListAvisos);
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
    public Observable observerUpdateAvisos(List<Aviso> listAvisos) {
        return Observable.defer(() -> {
            try {
                String messageNotif = "";
                for(int i = 0;i < listAvisos.size();i++){
                    Aviso cAvisosBean = listAvisos.get(i);

                    Aviso lAvisosBean = getAvisoIdc(cAvisosBean.getId() + "");

                    int idc = cAvisosBean.getId();
                    boolean operationEdit = false;
                    messageNotif = cAvisosBean.getTipoaviso() + "." + cAvisosBean.getTransaccionaviso() + "," + cAvisosBean.getTitulo();
                    if(lAvisosBean == null){

                        Cuenta cuentasBean = getCuenta();
                        if(cuentasBean != null){
                            if(cAvisosBean.getCuenta() == cuentasBean.getId()){
                                operationEdit = true;
                            }
                        }
                    }
                    else{
                        operationEdit = true;
                    }

                    if(operationEdit){
                        Log.v("...........",">por modificar>" + lAvisosBean);

                        if(lAvisosBean != null){
                            lAvisosBean.setTelefono(cAvisosBean.getTelefono());
                            lAvisosBean.setTransaccionAviso(cAvisosBean.getTransaccionaviso());
                            lAvisosBean.setTipoaviso(cAvisosBean.getTipoaviso());
                            lAvisosBean.setImagen(cAvisosBean.getImagen());
                            lAvisosBean.setLongitud(cAvisosBean.getLongitud());
                            lAvisosBean.setLatitud(cAvisosBean.getLatitud());
                            lAvisosBean.setDireccion(cAvisosBean.getDireccion());
                            lAvisosBean.setPrecio(cAvisosBean.getPrecio());
                            lAvisosBean.setDireccion(cAvisosBean.getDireccion());
                            lAvisosBean.setDescripcion(cAvisosBean.getDescripcion());
                            lAvisosBean.setTitulo(cAvisosBean.getTitulo());
                            lAvisosBean.setEstado(cAvisosBean.getEstado());
                            lAvisosBean.setIdc(idc + "");

                            try {
                                Integer idta = cAvisosBean.getTipo_aviso();
                                Integer idtr = cAvisosBean.getTransaccion_aviso();
                                if(idta != null){
                                    lAvisosBean.setTipoaviso(idta);
                                }
                                if(idtr != null){
                                    lAvisosBean.setTransaccionAviso(idtr);
                                }
                            }catch (Exception exrr){}

                            lAvisosBean.setTipoaviso(cAvisosBean.getTipoaviso());
                            lAvisosBean.setTransaccionAviso(cAvisosBean.getTransaccionaviso());

                            updateAviso(lAvisosBean);
                        }

                    }
                    else{
                        Log.v("...........",">por nuevo>" + cAvisosBean);
                        cAvisosBean.setIdc(idc + "");
                        try {
                            Integer idta = cAvisosBean.getTipo_aviso();
                            Integer idtr = cAvisosBean.getTransaccion_aviso();
                            if(idta != null){
                                cAvisosBean.setTipoaviso(idta);
                            }
                            if(idtr != null){
                                cAvisosBean.setTransaccionAviso(idtr);
                            }
                        }catch (Exception exrr){}
                        insertAviso(cAvisosBean);
                    }

                }

                return Observable.just(messageNotif);
            } catch(Throwable e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Aviso getAvisoIdc(String id) {
        Realm realm = Realm.getDefaultInstance();
        Aviso result = null;
        try {
            Aviso avisosBean = realm.where(Aviso.class)
                    .equalTo("idc", id)
                    .findFirst();

            if(avisosBean != null){
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
            result = null;
            err.printStackTrace();
            Log.v(">>>>",">ERROR EN DB getidc>" + err.getMessage());
        }
        finally {
            if(realm != null){
                realm.close();
            }
        }
        return result;
    }

    @Override
    public Cuenta getCuenta(){
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

    @Override
    public void insertAviso(Aviso avisosBean) {
        Realm realm = Realm.getDefaultInstance();
        try {
            if(avisosBean != null){
                int id = getMaxId();

                avisosBean.setId((id + 1));

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(avisosBean);
                realm.commitTransaction();

                realm.refresh();
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


}
