package com.ucb.appin.views.fragment;

import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

import java.util.List;

/**
 * Created by Juan choque on 1/27/2018.
 */

public interface IMyPublicationsView {
    void loadData(List<Aviso> listAvisos);

    void showDialogDelete(Aviso avisosBean);

    void showEditAviso(Aviso avisosBean);

    void showProgress(boolean b);

    void loadAvisos();

    void searchAvisos(String searchText);

    void loadDataTipoAviso(List<TipoAviso> listTipoAvisoBeans);

    void loadDataTransaccionAviso(List<TransaccionAviso>listTransaccionAvisos);

    void okDelete(Aviso data);

    void errorDelete(String s);
}
