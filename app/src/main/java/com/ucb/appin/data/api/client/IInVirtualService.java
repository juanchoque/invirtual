package com.ucb.appin.data.api.client;

import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.Bitacora;
import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by tecnosim on 1/18/2018.
 */

public interface IInVirtualService {

    Observable<List<Aviso>> listAvisos(Bitacora bitacoraBean);

    Observable<List<Bitacora>> setAvisos(List<Aviso> listAvisos);

    Observable<List<TipoAviso>> listTipoAvisos();

    Observable<List<TransaccionAviso>> listTransaccionAvisos();

    Observable<Cuenta> setCuentas(Cuenta cuentasBean);

    Observable<Cuenta> updateCuentas(Cuenta cuentasBean);
}
