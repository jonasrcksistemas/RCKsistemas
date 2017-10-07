package com.example.rcksuporte05.rcksistemas.api;

import com.example.rcksuporte05.rcksistemas.classes.Sincronia;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by RCK 04 on 06/10/2017.
 */

public interface Rotas {

    @GET("usuario/listar")
    Call<List<Usuario>> getUsuarios();

    @GET("usuario/login/{idandroid}/{idusuario}")
    Call<Usuario> setAndroidId(@Path("idandroid") String idandroid, @Path("idusuario") String idusuario);

    @GET("sincronia")
    Call<Sincronia> sincroniaApi();


}
