package com.ucb.appin.views.activity;

import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

import java.util.List;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IAddMyPublicationView {
    void showProgress(final boolean show);

    void loadInitDataTipoAviso(List<TipoAviso>listTipoAvisos);

    void loadInitDataTransaccionAviso(List<TransaccionAviso>listTransaccionAvisos);

    void okSave(Aviso data);

    void errorSave(String s);
}
