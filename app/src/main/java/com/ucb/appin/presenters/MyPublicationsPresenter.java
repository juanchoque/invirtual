package com.ucb.appin.presenters;

import android.content.Context;
import android.util.Log;

import com.ucb.appin.data.managerLocalDb.AvisoManager;
import com.ucb.appin.data.managerLocalDb.TipoAvisoManager;
import com.ucb.appin.data.managerLocalDb.TransaccionAvisoManager;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.util.ConstInVirtual;
import com.ucb.appin.views.fragment.IMyPublicationsView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Juan choque on 1/27/2018.
 */

public class MyPublicationsPresenter implements IMyPublicationPresenter {
    private Context context;
    private IMyPublicationsView iMyPublicationsView;

    public MyPublicationsPresenter(Context context, IMyPublicationsView iMyPublicationsView) {
        this.context = context;
        this.iMyPublicationsView = iMyPublicationsView;
    }

    @Override
    public void confirmDeleteAviso(Aviso avisosBean) {
        this.iMyPublicationsView.showDialogDelete(avisosBean);
    }

    @Override
    public Aviso deleteAviso(Aviso avisosBean) {

        AvisoManager avisoManager = new AvisoManager(context);
        avisoManager.observerSaveDeleteAviso(avisosBean)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            Log.v("----->",">RESULT OBSERVER FOR DELETE>" + ((Aviso)data));
                            iMyPublicationsView.okDelete((Aviso)data);
                        },
                        error -> {
                            Log.v("----->",">ERROR OBSERVER FOR DELETE>" + error.toString());
                            iMyPublicationsView.errorDelete(error.toString());
                        }
                );

        return avisosBean;
    }

    @Override
    public void showEditAviso(Aviso avisosBean) {
        this.iMyPublicationsView.showEditAviso(avisosBean);
    }

    @Override
    public void listTipoAvisos() {
        TipoAvisoManager tipoAvisoManager = new TipoAvisoManager();

        Disposable disposable = tipoAvisoManager.observerGetAllTipoAvisos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            Log.v("----->",">RESULT OBSERVER LIST TIPO AVISOS>");
                            iMyPublicationsView.loadDataTipoAviso((List<TipoAviso>)data);
                        },
                        error -> {
                            Log.v("----->",">ERROR OBSERVER LIST TIPO AVISOS>" + error.toString());
                        }
                );
    }

    @Override
    public void listTransaccionAvisos() {
        TransaccionAvisoManager transaccionAvisoManager = new TransaccionAvisoManager();

        Disposable disposable = transaccionAvisoManager.observerGetAllTransaccionAvisos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            Log.v("----->",">RESULT OBSERVER LIST TRANS AVISOS>");
                            iMyPublicationsView.loadDataTransaccionAviso((List<TransaccionAviso>)data);
                        },
                        error -> {
                            Log.v("----->",">ERROR OBSERVER LIST TTRAS>" + error.toString());
                        }
                );
    }

    @Override
    public void listAvisosBackground(TipoAviso tipoAviso, TransaccionAviso transaccionAviso) {
        try {
            ConstInVirtual.DISPOSABLES.clear();
        }
        catch (Exception ex){}

        AvisoManager avisoManager = new AvisoManager(context);

        Disposable disposable = avisoManager.observerMyAvisosBackground(tipoAviso, transaccionAviso)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.repeat()
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.delay(5, TimeUnit.SECONDS);
                    }
                })
                .subscribe(
                        data -> {
                            //Log.v("----->",">RESULT OBSERVER LIST MIS AVISOS>" + ((List<Aviso>)data).size());
                            iMyPublicationsView.loadData((List<Aviso>)data);
                        },
                        error -> {
                            //Log.v("----->",">ERROR OBSERVER LIST MIS AVISOS>" + error.toString());
                        }
                );

        ConstInVirtual.DISPOSABLES.add(disposable);
    }

}
