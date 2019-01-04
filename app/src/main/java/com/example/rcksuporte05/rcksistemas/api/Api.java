package com.example.rcksuporte05.rcksistemas.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RCK 04 on 06/10/2017.
 */

public class Api {

    private static final String url = "http://192.168.0.105:3383/ws/";
//    private static final String url = "http://192.168.0.105:3383/WhalleAPI2.4.0/ws/";
//    private static final String url = "http://192.168.0.154:1020/WhalleAPI2.4.0/ws/";
//    private static final String url = "http://portalmixnutri.ddns.com.br:725/WhalleAPI2.4.0/ws/";

    private static Rotas apiRotas;

    public static Rotas buildRetrofit() {
        if (apiRotas == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(interceptor().build())
                    .baseUrl(url)
                    .build();
            apiRotas = retrofit.create(Rotas.class);
        }
        return apiRotas;
    }

    private static OkHttpClient.Builder interceptor() {
        return new OkHttpClient.Builder()
                .readTimeout(180, TimeUnit.MINUTES)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        HeaderInterceptor headerInterceptor = new HeaderInterceptor();
                        return headerInterceptor.intercept(chain);
                    }
                });
    }
}