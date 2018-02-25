package com.ucb.appin.presenters;

import android.content.Context;
import android.util.Log;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.ucb.appin.data.managerLocalDb.AvisoManager;
import com.ucb.appin.data.managerLocalDb.TipoAvisoManager;
import com.ucb.appin.data.managerLocalDb.TransaccionAvisoManager;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.util.ConstInVirtual;
import com.ucb.appin.views.fragment.IPublicationsMapView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PublicationsMapPresenter extends RxAppCompatActivity implements IPublicationsMapPresenter {
    private Context context;
    private IPublicationsMapView iPublicationsMapView;

    public PublicationsMapPresenter(Context context, IPublicationsMapView iPublicationsMapView) {
        this.context = context;
        this.iPublicationsMapView = iPublicationsMapView;
    }

    @Override
    public void listAvisosBackground(){
        try {
            ConstInVirtual.DISPOSABLES.clear();
        }
        catch (Exception ex){}

        AvisoManager avisoManager = new AvisoManager(context);

        Disposable disposable = avisoManager.observerAvisosBackground()
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
                            Log.v("----->",">RESULT OBSERVER LIST AVISOS>");
                            iPublicationsMapView.loadData((List<Aviso>)data);
                        },
                        error -> {
                            Log.v("----->",">ERROR OBSERVER LIST AVISOS>" + error.toString());
                        }
                );

        ConstInVirtual.DISPOSABLES.add(disposable);

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
                            iPublicationsMapView.loadDataTipoAviso((List<TipoAviso>)data);
                        },
                        error -> {
                            Log.v("----->",">ERROR OBSERVER LIST TIPO AVISOS>" + error.toString());
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

        Disposable disposable = avisoManager.observerAvisosBackground(tipoAviso, transaccionAviso)
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
                            Log.v("----->",">RESULT OBSERVER LIST AVISOS>");
                            iPublicationsMapView.loadData((List<Aviso>)data);
                        },
                        error -> {
                            Log.v("----->",">ERROR OBSERVER LIST AVISOS>" + error.toString());
                        }
                );

        ConstInVirtual.DISPOSABLES.add(disposable);
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
                            iPublicationsMapView.loadDataTransaccionAviso((List<TransaccionAviso>)data);
                        },
                        error -> {
                            Log.v("----->",">ERROR OBSERVER LIST TTRAS>" + error.toString());
                        }
                );
    }

}
