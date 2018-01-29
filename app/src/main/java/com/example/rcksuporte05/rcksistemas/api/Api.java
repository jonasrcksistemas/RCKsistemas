package com.example.rcksuporte05.rcksistemas.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RCK 04 on 06/10/2017.
 */

public class Api {

//    private static final String url = "http://192.168.0.105:8080/ws/";
    private static final String url = "http://rcksistemassuporte.ddns.com.br:1020/WhalleAPI/ws/";
//    private static final String url = "http://portalmixnutri.ddns.com.br:725/WhalleAPI/ws/";

    public static Rotas apiRotas;

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

    public static OkHttpClient.Builder interceptor() {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                HeaderInterceptor headerInterceptor = new HeaderInterceptor();
                return headerInterceptor.intercept(chain);
            }
        });
    }
}
