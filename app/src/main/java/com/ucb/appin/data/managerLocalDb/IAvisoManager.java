package com.ucb.appin.data.managerLocalDb;

import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Juan choque on 10/12/2017.
 */

public interface IAvisoManager {
    List<Aviso> getAllAvisos();

    List<Aviso> getAllAvisos(Aviso aviso);

    List<Aviso> getAllAvisos(Aviso aviso, Integer tipoAviso, Integer transaccionAviso);

    Aviso getAviso(int id);

    boolean saveUpateAviso(Aviso aviso, int tipoOperacion);

    Observable observerSaveUpateAviso(Aviso aviso, final int tipoOperacion);

    Observable observerSaveDeleteAviso(final Aviso aviso);

    int getMaxId();

    boolean deleteAviso(Aviso aviso);

    Observable observerAvisosBackground();

    Observable observerAvisosBackground(TipoAviso tipoAviso, TransaccionAviso transaccionAviso);

    Observable<Object> observerMyAvisosBackground(TipoAviso tipoAviso, TransaccionAviso transaccionAviso);

    Observable observerUpdateAvisos(List<Aviso> listAvisos);

    Aviso getAvisoIdc(String id);

    Cuenta getCuenta();

    void updateAviso(Aviso avisosBean);

    void insertAviso(Aviso avisosBean);
}
