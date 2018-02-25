package com.ucb.appin.data.managerLocalDb;

import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.Bitacora;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Juan choque on 2/24/2018.
 */

public interface IBitacoraManager {
    Bitacora getBitacoraByIdBean(int id);

    void removeBitacoraById(int id);

    public Bitacora getBitacoraById(int id);

    int getMaxId();

    void saveOrUpdate(Bitacora bitacoraBean);

    void insertBitacora(Bitacora bitacoraBean);

    List<Bitacora> listBitacora();

    Observable observerListBitacora();

    Aviso getAvisoById(int id);

    Observable observerUpdateBitacora(List<Bitacora> listBitacora);

    void updateAviso(Aviso avisosBean);
}
