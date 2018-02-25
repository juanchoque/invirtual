package com.ucb.appin.presenters;

import android.content.Context;
import android.util.Log;

import com.ucb.appin.data.managerLocalDb.AvisoManager;
import com.ucb.appin.data.managerLocalDb.TipoAvisoManager;
import com.ucb.appin.data.managerLocalDb.TransaccionAvisoManager;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.views.activity.IAddMyPublicationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Juan choque on 1/27/2018.
 */

public class AddMyPublicationPresenter implements IAddMyPublicationPresenter {
    private Context context;
    private IAddMyPublicationView iAddMyPublicationView;

    public AddMyPublicationPresenter(Context context, IAddMyPublicationView iAddMyPublicationView) {
        this.context = context;
        this.iAddMyPublicationView = iAddMyPublicationView;
    }

    @Override
    public Aviso addAviso(Aviso avisosBean, int typeOperation) {
        try {
            if(avisosBean != null){
                AvisoManager avisoManager = new AvisoManager(context);
                avisoManager.observerSaveUpateAviso(avisosBean, typeOperation).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                data -> {
                                    Log.v("----->",">RESULT OBSERVER FOR ADD>" + ((Aviso)data));
                                    iAddMyPublicationView.okSave((Aviso)data);
                                },
                                error -> {
                                    Log.v("----->",">ERROR OBSERVER FOR ADD>" + error.toString());
                                    iAddMyPublicationView.errorSave(error.toString());
                                }
                        );
            }
        }catch (Exception er){
            avisosBean = null;
        }
        return avisosBean;
    }

    @Override
    public void listTipoAvisos() {
        List<TipoAviso> listTipoAvisos = new ArrayList<TipoAviso>();
        TipoAvisoManager tipoAvisoManager = new TipoAvisoManager();
        listTipoAvisos = tipoAvisoManager.getAllTipoAvisosJarcode();

        this.iAddMyPublicationView.loadInitDataTipoAviso(listTipoAvisos);
    }

    @Override
    public void listTransaccionAvisos() {
        List<TransaccionAviso>listTransaccionAvisos = new ArrayList<TransaccionAviso>();
        TransaccionAvisoManager transaccionAvisoManager = new TransaccionAvisoManager();
        listTransaccionAvisos = transaccionAvisoManager.getAllTransaccionAvisosJarcode();

        this.iAddMyPublicationView.loadInitDataTransaccionAviso(listTransaccionAvisos);
    }

}
