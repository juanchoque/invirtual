package com.ucb.appin.presenters;

import android.content.Context;

import com.ucb.appin.data.managerLocalDb.TipoAvisoManager;
import com.ucb.appin.data.managerLocalDb.TransaccionAvisoManager;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.views.activity.DetailPublicationView;
import com.ucb.appin.views.activity.IDetailPublicationView;


/**
 * Created by Juan choque on 2/24/2018.
 */

public class DetailPublicationPresenter implements IDetailPublicationPresenter {

    private Context context;
    private IDetailPublicationView iDetailPublicationView;

    public DetailPublicationPresenter(Context context, DetailPublicationView detailPublicationView) {
        this.context = context;
        this.iDetailPublicationView = detailPublicationView;
    }

    @Override
    public TipoAviso getTipoAvisoById(Integer idtipo) {
        TipoAvisoManager tipoAvisoManager = new TipoAvisoManager();

        TipoAviso tipoAviso = tipoAvisoManager.getTipoAvisoByIdJarcode(idtipo);
        return tipoAviso;
    }

    @Override
    public TransaccionAviso getTransaccionAvisoById(Integer transaccionaviso) {
        TransaccionAvisoManager transaccionAvisoManager = new TransaccionAvisoManager();
        TransaccionAviso transaccionAviso = transaccionAvisoManager.getTransaccionAvisoByIdJarcode(transaccionaviso);
        return transaccionAviso;
    }
}
