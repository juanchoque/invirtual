package com.ucb.appin.data.networking;

import com.ucb.appin.util.ConstInVirtual;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tecnosim on 1/18/2018.
 */

public abstract class InVirtualRetrofitClient {
    private InVirtualRetrofitService inMobRetrofitService;

    public InVirtualRetrofitService getInMobRetrofitService() {
        return inMobRetrofitService;
    }

    public InVirtualRetrofitClient() {
        initRetrofit();
    }

    private void initRetrofit() {
        Retrofit retrofit = retrofitBuilder();
        inMobRetrofitService = retrofit.create(getInMobRetroFitService());
    }

    private Class<InVirtualRetrofitService> getInMobRetroFitService() {
        return InVirtualRetrofitService.class;
    }

    private Retrofit retrofitBuilder() {
        return new Retrofit.Builder().baseUrl(ConstInVirtual.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(ConstInVirtual.DEFAULT_WAIT, TimeUnit.SECONDS);
        client.connectTimeout(ConstInVirtual.DEFAULT_WAIT, TimeUnit.SECONDS);
        client.writeTimeout(ConstInVirtual.DEFAULT_WAIT, TimeUnit.SECONDS);
        return client.build();
    }


}
