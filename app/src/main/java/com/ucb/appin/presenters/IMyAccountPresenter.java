package com.ucb.appin.presenters;

import com.ucb.appin.data.model.Cuenta;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IMyAccountPresenter {
    void addAccount(Cuenta cuentasBean);

    void getAccount();

    void showProgress(boolean b);

    void showMessage(String messageOkOperation);

    void listTipoAvisos();

    void listTransaccionAvisos();
}
