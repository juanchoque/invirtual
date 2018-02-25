package com.ucb.appin.presenters;

import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IMyPublicationPresenter {
    void confirmDeleteAviso(Aviso avisosBean);

    Aviso deleteAviso(Aviso avisosBean);

    void showEditAviso(Aviso avisosBean);

    void listTipoAvisos();

    void listTransaccionAvisos();

    void listAvisosBackground(TipoAviso tipoAvisoSel, TransaccionAviso transaccionAvisoSel);
}
