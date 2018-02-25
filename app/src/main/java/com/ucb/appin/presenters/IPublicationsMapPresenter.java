package com.ucb.appin.presenters;

import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

public interface IPublicationsMapPresenter {
    void listAvisosBackground();

    void listTipoAvisos();

    void listAvisosBackground(TipoAviso tipoAviso, TransaccionAviso transaccionAviso);

    void listTransaccionAvisos();
}
