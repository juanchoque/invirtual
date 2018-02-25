package com.ucb.appin.presenters;

import android.content.Context;
import android.util.Log;

import com.ucb.appin.data.managerLocalDb.CuentaManager;
import com.ucb.appin.data.managerLocalDb.TipoAvisoManager;
import com.ucb.appin.data.managerLocalDb.TransaccionAvisoManager;
import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.services.SynForegroundService;
import com.ucb.appin.util.ConstInVirtual;
import com.ucb.appin.views.activity.IAdminAccountView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Juan choque on 1/27/2018.
 */

public class MyAccountPresenter implements IMyAccountPresenter {
    private Context context;
    private IAdminAccountView iAdminAccountView;

    public MyAccountPresenter(Context context, IAdminAccountView iAdminAccountView) {
        this.context = context;
        this.iAdminAccountView = iAdminAccountView;
    }

    @Override
    public void addAccount(Cuenta cuentasBean) {
        SynForegroundService synForegroundService = new SynForegroundService();
        synForegroundService.syncronizedCloudLocalSetCuentas(cuentasBean, this);
        iAdminAccountView.showMessage(ConstInVirtual.MESSAGE_OK_OPERATION_WAIT);
        iAdminAccountView.finish();
    }

    @Override
    public void getAccount() {
        CuentaManager cuentaManager = new CuentaManager();
        Cuenta cuenta = cuentaManager.getCuenta(context);

        //iAdminAccountView.loadData(cuenta);

        Disposable disposable = cuentaManager.observerGetAccount(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            Log.v("----->",">RESULT OBSERVER ACCOUNT>" + data);
                            iAdminAccountView.loadData((Cuenta) data);
                        },
                        error -> {
                            Log.v("----->",">ERROR OBSERVER GET ACCOUNT>" + error.toString());
                        }
                );
    }

    @Override
    public void showProgress(boolean show) {
        iAdminAccountView.showProgress(show);
        iAdminAccountView.finish();
    }

    @Override
    public void showMessage(String messageOkOperation) {
        iAdminAccountView.showMessage(messageOkOperation);
    }

    @Override
    public void listTipoAvisos() {
        List<TipoAviso>listTipoAvisos = new ArrayList<TipoAviso>();
        TipoAvisoManager tipoAvisoManager = new TipoAvisoManager();
        listTipoAvisos = tipoAvisoManager.getAllTipoAvisosJarcode();

        this.iAdminAccountView.loadTipoAvisos(listTipoAvisos);
    }

    @Override
    public void listTransaccionAvisos() {
        List<TransaccionAviso>listTransaccionAvisos = new ArrayList<TransaccionAviso>();
        TransaccionAvisoManager  transaccionAvisoManager = new TransaccionAvisoManager();
        listTransaccionAvisos = transaccionAvisoManager.getAllTransaccionAvisosJarcode();

        this.iAdminAccountView.loadTransaccionAvisos(listTransaccionAvisos);
    }

}
