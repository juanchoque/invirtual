package com.ucb.appin.presenters;

import com.ucb.appin.data.model.Aviso;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IAddMyPublicationPresenter {
    Aviso addAviso(Aviso avisosBean, int typeOperation);

    void listTipoAvisos();

    void listTransaccionAvisos();

}
