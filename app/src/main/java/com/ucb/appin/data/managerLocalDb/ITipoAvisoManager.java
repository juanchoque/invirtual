package com.ucb.appin.data.managerLocalDb;

import com.ucb.appin.data.model.TipoAviso;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Juan choque on 2/24/2018.
 */

public interface ITipoAvisoManager {
    List<TipoAviso>getAllTipoAvisosJarcode();

    TipoAviso getTipoAvisoByIdJarcode(Integer idTipoAviso);

    TipoAviso getTipoAvisoByIdc(Integer id);

    int getMaxIdTAviso();

    void updateTipoAviso(TipoAviso tipoAvisoBean);

    Observable observerGetAllTipoAvisos();

    void insertTipoAviso(TipoAviso tipoAvisoBean);

    Observable observerSaveOrUpdateJarcode();

    Observable observerSaveOrUpdateAll(List<TipoAviso> tipoAvisos);

    List<TipoAviso> getAllTipoAvisos();

    void addFronCloudTipoAviso(TipoAviso cTipoAviso);
}
