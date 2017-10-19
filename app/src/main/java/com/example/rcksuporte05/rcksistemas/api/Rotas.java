package com.example.rcksuporte05.rcksistemas.api;

import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.classes.Sincronia;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @POST("webpedido/faturar")
    Call<List<WebPedido>> enviarPedidos(@Body List<WebPedido> webPedidos);

    @GET("historicofinanceiro/listar/{id}")
    Call<HistoricoFinanceiro> getHistoricoFinanceiro(@Path("id") int idCliente);




}
