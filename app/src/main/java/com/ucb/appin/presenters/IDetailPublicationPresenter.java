package com.ucb.appin.presenters;

import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

/**
 * Created by Juan choque on 2/24/2018.
 */

public interface IDetailPublicationPresenter {
    TipoAviso getTipoAvisoById(Integer tipoaviso);

    TransaccionAviso getTransaccionAvisoById(Integer tipoaviso);
}
