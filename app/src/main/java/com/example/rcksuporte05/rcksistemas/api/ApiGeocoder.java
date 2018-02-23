package com.example.rcksuporte05.rcksistemas.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RCK 03 on 22/02/2018.
 */

public class ApiGeocoder {

    //      private static final String url = "http://192.168.0.104:8080/ws/";
//        private static final String url = "http://rcksistemassuporte.ddns.com.br:1020/WhalleAPI/ws/";
//    private static final String url = "http://portalmixnutri.ddns.com.br:725/WhalleAPI/ws/";
    private static final String url = "https://maps.googleapis.com";


    public static Rotas apiRotas;

    public static Rotas buildRetrofit() {
        if (apiRotas == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(url)
                    .build();
            apiRotas = retrofit.create(Rotas.class);
        }

        return apiRotas;
    }

}




