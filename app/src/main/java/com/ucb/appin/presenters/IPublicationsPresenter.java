package com.ucb.appin.presenters;

import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IPublicationsPresenter {
    void listAvisos();

    void listAvisosBackground();

    void listTipoAvisos();

    void listAvisosBackground(TipoAviso tipoAviso, TransaccionAviso transaccionAviso);

    void listTransaccionAvisos();
}
