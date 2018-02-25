package com.ucb.appin.views.activity;

import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

/**
 * Created by tecnosim on 2/9/2018.
 */

public interface IDetailPublicationView {
    void loadTipoAviso(TipoAviso tipoaviso);

    void loadTransaccionAviso(TransaccionAviso transaccionaviso);
}
