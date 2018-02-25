package com.ucb.appin.services;

import android.util.Log;

import com.ucb.appin.data.api.client.IInVirtualService;
import com.ucb.appin.data.api.client.InVirtualService;
import com.ucb.appin.data.managerLocalDb.CuentaManager;
import com.ucb.appin.data.managerLocalDb.TipoAvisoManager;
import com.ucb.appin.data.managerLocalDb.TransaccionAvisoManager;
import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.presenters.IMyAccountPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Juan choque on 2/12/2018.
 */

public class SynForegroundService {
    private IInVirtualService inMobService;

    public SynForegroundService() {
        this.inMobService = new InVirtualService();
    }

    //***************************************************************************************************
    //get cloud
    public void syncronizedCloudLocalTipoAvisos(){
        io.reactivex.Observable<List<TipoAviso>> observable = this.inMobService.listTipoAvisos();

        Disposable disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        data -> {
                            syncronizedLocalTipoAviso(data);
                            //Log.v("......>",">RETURN TIPO AVISO>" + data.size());
                        },
                        error -> {
                            syncronizedLocalTipoAviso(null);
                            Log.v("......>",">ERROR RETURN TIPO AVISO>" + error.getMessage());
                            error.printStackTrace();
                        }
                );
    }

    private void syncronizedLocalTipoAviso(List<TipoAviso> tipoAvisos) {
        Log.v("...............>",";;;;;;;;;;;;;;;;;;>" + tipoAvisos);
        TipoAvisoManager tipoAvisoManager = new TipoAvisoManager();
        if(tipoAvisos == null){
            tipoAvisoManager.observerSaveOrUpdateJarcode()
            .subscribe(
                    data -> {
                        Log.v("----->",">RESULT OBSERVER INSERT TIPO AVISOS JARCODE>");
                    },
                    error -> {
                        Log.v("----->",">ERROR OBSERVER INSERT TIPO AVISOS JARCODE>" + error.toString());
                    }
            );
        }
        else{
            tipoAvisoManager.observerSaveOrUpdateAll(tipoAvisos)
                    .subscribe(
                            data -> {
                                Log.v("----->",">RESULT OBSERVER INSERT CLOUD>");
                            },
                            error -> {
                                Log.v("----->",">ERROR OBSERVER INSERT CLOUD>" + error.toString());
                            }
                    );
        }
    }

    //end tipo avisos
    //*******************************************************************************************************


    //***************************************************************************************************
    public void syncronizedCloudLocalTransaccionAvisos(){
        io.reactivex.Observable<List<TransaccionAviso>> observable = this.inMobService.listTransaccionAvisos();

        Disposable disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        data -> {
                            syncronizedLocalTransaccionAviso(data);
                            Log.v("......>",">RETURN TRAN AVISO>" + data.size());
                        },
                        error -> {
                            syncronizedLocalTransaccionAviso(null);
                            Log.v("......>",">ERROR RETURN TRAN AVISO>" + error.getMessage());
                            error.printStackTrace();
                        }
                );
    }

    private void syncronizedLocalTransaccionAviso(List<TransaccionAviso> listTransaccionAvisos) {
        TransaccionAvisoManager transaccionAvisoManager = new TransaccionAvisoManager();

        if(listTransaccionAvisos == null){
            transaccionAvisoManager.observerSaveOrUpdateJarcode()
                    .subscribe(
                            data -> {
                                Log.v("----->",">RESULT OBSERVER INSERT TRANS AVISOS JARCODE>");
                            },
                            error -> {
                                Log.v("----->",">ERROR OBSERVER INSERT TRANS AVISOS JARCODE>" + error.toString());
                            }
                    );
        }
        else{
            transaccionAvisoManager.observerSaveOrUpdateAll(listTransaccionAvisos)
                    .subscribe(
                            data -> {
                                Log.v("----->",">RESULT OBSERVER INSERT CLOUD>");
                            },
                            error -> {
                                Log.v("----->",">ERROR OBSERVER INSERT CLOUD>" + error.toString());
                            }
                    );
        }

    }


    //***************************************************************************************************
    public void syncronizedCloudLocalSetCuentas(Cuenta cuentasBean, IMyAccountPresenter presenterMyAccount){

        io.reactivex.Observable<Cuenta> observable = this.inMobService.setCuentas(cuentasBean);

        Disposable disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        data -> {
                            syncronizedLocalSetCuentas(data, cuentasBean,presenterMyAccount);
                            Log.v("--->",">RETURN CORRECT ACCOUNT>");
                        },
                        error -> {
                            syncronizedLocalSetCuentas(null, cuentasBean, presenterMyAccount);
                            Log.v("--->",">RETURN ERROR ACCOUNT>");
                            error.printStackTrace();
                        }
                );
    }

    private void syncronizedLocalSetCuentas(Cuenta cuenta, Cuenta origin, IMyAccountPresenter presenterMyAccount) {
        CuentaManager cuentaManager = new CuentaManager();
        if(cuenta == null){
            cuentaManager.observaerSaveOrUpadate(origin)
                    .subscribe(
                            data -> {
                                Log.v("----->",">RESULT OBSERVER INSERT CUENTA JARCODE>");
                            },
                            error -> {
                                Log.v("----->",">ERROR OBSERVER INSERT CUENTA JARCODE>" + error.toString());
                            }
                    );
        }
        else{
            cuentaManager.observaerSaveOrUpadate(cuenta)
                    .subscribe(
                            data -> {
                                Log.v("----->",">RESULT OBSERVER INSERT CUENTA CLOUD>");
                            },
                            error -> {
                                Log.v("----->",">ERROR OBSERVER INSERT CUENTA CLOUD>" + error.toString());
                            }
                    );
        }
    }

}
