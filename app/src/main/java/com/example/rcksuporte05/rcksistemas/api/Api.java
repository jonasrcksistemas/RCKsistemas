package com.example.rcksuporte05.rcksistemas.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RCK 04 on 06/10/2017.
 */

public class Api {

    private static final String url = "http://192.168.0.105:8080/ws/";

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
