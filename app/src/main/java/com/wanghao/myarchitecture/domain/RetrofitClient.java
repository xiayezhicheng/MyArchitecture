package com.wanghao.myarchitecture.domain;

import com.wanghao.myarchitecture.vendor.Config;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wanghao on 2016/3/24.
 */
public class RetrofitClient {

    public static Retrofit getInstance(){
        OkHttpClient client = OkHttpClientUtil.getClient();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Config.BASE_URL)
                .build();
        return retrofit;
    }
}
