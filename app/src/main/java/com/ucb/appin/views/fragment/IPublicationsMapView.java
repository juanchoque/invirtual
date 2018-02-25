package com.ucb.appin.views.fragment;

import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

import java.util.List;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IPublicationsMapView {

    void loadData(List<Aviso> data);

    void loadDataTipoAviso(List<TipoAviso> listTipoAvisos);

    void loadDataTransaccionAviso(List<TransaccionAviso> listTransaccionAvisos);

    void searchAvisos(String searchText);
}
