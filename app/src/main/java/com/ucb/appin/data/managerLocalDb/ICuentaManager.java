package com.ucb.appin.data.managerLocalDb;

import android.content.Context;

import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.services.SynBackgroundService;

import io.reactivex.Observable;

/**
 * Created by Juan choque on 2/24/2018.
 */

public interface ICuentaManager {
    Cuenta getCuenta(Context context);
    boolean saveOrUpadateJarcode(Context context);

    Observable observaerSaveOrUpadate(Cuenta origin);

    void saveOrUpdateCuentas(Cuenta cuentasBean);

    boolean getCuenta(Context context, String ids);
}
