package com.ucb.appin.data.api.client;

import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.Bitacora;
import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.data.networking.InVirtualRetrofitClient;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tecnosim on 1/18/2018.
 */

public class InVirtualService extends InVirtualRetrofitClient implements IInVirtualService {

    @Override
    public Observable<List<Aviso>> listAvisos(Bitacora bitacoraBean) {
        return getInMobRetrofitService().getData(bitacoraBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Bitacora>> setAvisos(List<Aviso> listAvisos) {
        return getInMobRetrofitService().setData(listAvisos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<TipoAviso>> listTipoAvisos() {
        return getInMobRetrofitService().getListTipoAvisos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<TransaccionAviso>> listTransaccionAvisos() {
        return getInMobRetrofitService().getListTransaccionAvisos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Cuenta> setCuentas(Cuenta cuentasBean) {
        return getInMobRetrofitService().setCuentas(cuentasBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Cuenta> updateCuentas(Cuenta cuentasBean) {
        return getInMobRetrofitService().updateCuentas(cuentasBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
