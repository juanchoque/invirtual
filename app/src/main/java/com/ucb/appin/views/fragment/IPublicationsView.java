package com.ucb.appin.views.fragment;

import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

import java.util.List;

/**
 * Created by tecnosim on 2/9/2018.
 */

public interface IPublicationsView {

    void showProgress(boolean b);

    void loadData(List<Aviso> listAvisos);

    void searchAvisos(String txtBuscar);

    void mostrarDetallePublication(Aviso avisosBean);

    void loadDataTipoAviso(List<TipoAviso> listTipoAvisoBeans);

    void loadDataTransaccionAviso(List<TransaccionAviso>listTransaccionAvisos);
}
