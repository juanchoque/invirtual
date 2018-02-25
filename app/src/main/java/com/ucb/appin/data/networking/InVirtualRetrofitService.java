package com.ucb.appin.data.networking;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.Bitacora;
import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.util.ConstInVirtual;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by tecnosim on 1/18/2018.
 */

public interface InVirtualRetrofitService {

    @POST(ConstInVirtual.LIST_AVISOS)
    Observable<List<Aviso>> getData(@Body Bitacora bitacoraBean);

    @POST(ConstInVirtual.SET_AVISOS)
    Observable<List<Bitacora>> setData(@Body List<Aviso> listAvisos);

    @GET(ConstInVirtual.LIST_TIPO_AVISOS)
    Observable<List<TipoAviso>> getListTipoAvisos();

    @GET(ConstInVirtual.LIST_TRANSACCION_AVISOS)
    Observable<List<TransaccionAviso>> getListTransaccionAvisos();

    @POST(ConstInVirtual.SET_CUENTAS)
    Observable<Cuenta> setCuentas(@Body Cuenta cuentasBean);

    @POST(ConstInVirtual.UPDATE_CUENTAS)
    Observable<Cuenta> updateCuentas(@Body Cuenta cuentasBean);
}
