package com.ucb.appin.data.managerLocalDb;

import com.ucb.appin.data.model.TransaccionAviso;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Juan choque on 2/24/2018.
 */

public interface ITransaccionAvisoManager {
    List<TransaccionAviso> getAllTransaccionAvisosJarcode();

    TransaccionAviso getTransaccionAvisoByIdJarcode(Integer transaccionaviso);

    Observable observerSaveOrUpdateJarcode();

    Observable observerSaveOrUpdateAll(List<TransaccionAviso> listTransaccionAvisos);

    TransaccionAviso getTransaccionAvisoByIdc(Integer id);

    void saveOrUpdateTransaccionAviso(TransaccionAviso transaccionAviso);

    int getMaxIdTrAviso();

    void updateTransaccionAviso(TransaccionAviso transaccionAviso);

    Observable<Object> observerGetAllTransaccionAvisos();

    void addFronCloudTransaccionAviso(TransaccionAviso cTransaccionAvisoBean);
}
