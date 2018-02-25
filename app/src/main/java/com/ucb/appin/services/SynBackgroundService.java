package com.ucb.appin.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ucb.appin.R;
import com.ucb.appin.data.api.client.IInVirtualService;
import com.ucb.appin.data.api.client.InVirtualService;
import com.ucb.appin.data.managerLocalDb.AvisoManager;
import com.ucb.appin.data.managerLocalDb.BitacoraManager;
import com.ucb.appin.data.managerLocalDb.CuentaManager;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.Bitacora;
import com.ucb.appin.util.ConstInVirtual;
import com.ucb.appin.util.UtilInVirtual;
import com.ucb.appin.views.MainView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Juan choque on 2/10/2018.
 */

public class SynBackgroundService extends Service {
    private static final String TAG = SynBackgroundService.class.getSimpleName();

    public static final int NOTIFICATION_ID = 234;
    private WaitTask waitTask = null;

    private IInVirtualService inMobService;

    public SynBackgroundService() {
        this.inMobService = new InVirtualService();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        loadLocalAvisos();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean isForeground(String myPackage) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        return !componentInfo.getPackageName().equals(myPackage);
    }

    public void createNotification(String message){
        try {
            String rmessange = "";
            String []vmessange = message.split(",");
            rmessange = vmessange[1];

            CuentaManager cuentaManager = new CuentaManager();
            boolean show = cuentaManager.getCuenta(this, vmessange[0]);
            if(show){
                Intent intent = new Intent(this, MainView.class);

                TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
                taskStackBuilder.addParentStack(MainView.class);
                taskStackBuilder.addNextIntent(intent);

                PendingIntent pendingIntent = taskStackBuilder.
                        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification = new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(rmessange)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setContentIntent(pendingIntent)
                        .build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID, notification);
            }
        }catch (Exception ex){

        }

    }


    //**********************************************************************************************
    //get data wid data local (bitacora)
    public void loadLocalAvisos(){
        //Log.v("&&&&&&",">11111>loadLocalAvisos>>");
        BitacoraManager bitacoraManager = new BitacoraManager();
        bitacoraManager.observerListBitacora()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        data -> {
                            //Log.v("--->",">RETURN DATA OF BITACORA>");
                            syncronizedLocalCloudAvisos((List<Aviso>)data);
                        },
                        error -> {
                            Log.v("--->",">NO DATA IN BITACORA SEND DELAY>");
                            wdelay();
                        }
                );
    }

    //for syncronized clud
    public void syncronizedLocalCloudAvisos(List<Aviso>listAvisos){
        //Log.v("&&&&&&",">22222>syncronizedLocalCloudAvisos>>" + listAvisos);
        io.reactivex.Observable<List<Bitacora>> observable = this.inMobService.setAvisos(listAvisos);

        Disposable disposable = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        data -> {
                            //Log.v("--->",">RETURN DATA OF CLOUD>");
                            syncronizedLocalBitacora(data);
                        },
                        error -> {
                            Log.v("--->",">NO RETURN CLOUD DELAY>");
                            wdelay();
                        }
                );
    }
    public void syncronizedLocalBitacora(List<Bitacora>listBitacora){
        //Log.v("&&&&&&",">3333333>syncronizedLocalBitacora>>" + listBitacora);
        BitacoraManager bitacoraManager = new BitacoraManager();
        bitacoraManager.observerUpdateBitacora(listBitacora)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        data -> {
                            //Log.v("--->",">RETURN CORRECT UPDATE BITACORA LOCAL>");
                            syncronizedCloudLocalAvisos();
                        },
                        error -> {
                            Log.v("--->",">NO UPDATE BITACORA LOCAL>");
                            wdelay();
                        }
                );

    }


    //***************************************************************************************************
    public void syncronizedCloudLocalAvisos(){
        //Log.v("&&&&&&",">4444444>syncronizedLocalBitacora>>");
        UtilInVirtual utilInmo = new UtilInVirtual(this);
        String imei = utilInmo.getNumberPhone();

        Bitacora bitacoraBean = new Bitacora();
        bitacoraBean.setTelefono(imei);

        io.reactivex.Observable<List<Aviso>> observable = this.inMobService.listAvisos(bitacoraBean);

        Disposable disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                    data -> {
                        //Log.v("--->",">RETURN CORRECT DATA AVISOS CLOUD>");
                        syncronizedLocalAviso(data);
                    },
                    error -> {
                        Log.v("--->",">NO NO RETURN DATA CLUD AVISOS>");
                        wdelay();
                    }
            );
    }
    public void syncronizedLocalAviso(List<Aviso>listAvisos){
        //Log.v("&&&&&&",">555555>syncronizedLocalAviso>>" + listAvisos);
        AvisoManager avisoManager = new AvisoManager(this);
        avisoManager.observerUpdateAvisos(listAvisos)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        data -> {
                            //Log.v("--->",">RETURN CORRECT UPDATE AVISOS LOCAL>");
                            if(isForeground(ConstInVirtual.NAME_PACKAGE) && !data.toString().isEmpty()){
                                createNotification(data.toString());
                            }
                            wdelay();
                        },
                        error -> {
                            Log.v("--->",">NO UPDATE AVISOS LOCAL>");
                            wdelay();
                        }
                );

    }


    ///////////////////////////////////////////////////////////////////////////////
    public void wdelay(){
        //Log.v("&&&&&&",">666666666666>wdelay>>");
        waitTask = new WaitTask();
        waitTask.execute((Void) null);
    }

    public class WaitTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                Thread.sleep(ConstInVirtual.WAIT_FIVE_SECONDS);
            }catch (Exception er){
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            waitTask = null;
            loadLocalAvisos();
        }

        @Override
        protected void onCancelled() {
            waitTask = null;
        }
    }

}
