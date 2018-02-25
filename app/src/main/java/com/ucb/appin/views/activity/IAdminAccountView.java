package com.ucb.appin.views.activity;

import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

import java.util.List;

/**
 * Created by Juan choque on 2/4/2018.
 */

public interface IAdminAccountView {
    void loadData(Cuenta cuentasBean);

    void showProgress(boolean b);

    void finish();

    void showMessage(String messageOkOperation);

    void loadTipoAvisos(List<TipoAviso> listTipoAvisos);

    void loadTransaccionAvisos(List<TransaccionAviso> listTransaccionAvisos);
}
