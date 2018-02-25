package com.ucb.appin;

import android.app.Application;

import com.ucb.appin.data.model.Aviso;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Juan choque on 10/14/2017.
 */

public class DbApplication extends Application {

    public static AtomicInteger publicationID = new AtomicInteger();

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);

        setUpRealmConfig();
        Realm realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
        publicationID = getIdByTable(realm, Aviso.class);

//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
//                        .build());
//
//        realm.close();
    }

    private void setUpRealmConfig() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("invirtual.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        //Realm.deleteRealm(configuration); // Borrado si la app reinicia.
        Realm.setDefaultConfiguration(configuration);

    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();
    }
}
