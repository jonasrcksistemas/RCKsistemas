package com.example.rcksuporte05.rcksistemas.api;

import com.example.rcksuporte05.rcksistemas.classes.Foto;
import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.classes.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.classes.Prospect;
import com.example.rcksuporte05.rcksistemas.classes.Segmento;
import com.example.rcksuporte05.rcksistemas.classes.Sincronia;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.classesGeocoderUtil.RespostaGeocoder;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by RCK 04 on 06/10/2017.
 */

public interface Rotas {

    @GET("usuario/listar")
    Call<List<Usuario>> getUsuarios();

    @GET("usuario/login/{idandroid}/{idusuario}/{usuarioLogin}/{senha}")
    Call<Usuario> login(@Path("idandroid") String idandroid, @Path("idusuario") String idusuario, @Path("usuarioLogin") String usuarioLogin, @Path("senha") String senha);

    @POST("sincronia/{id}")
    Call<Sincronia> sincroniaApi(@Path("id") int id, @HeaderMap Map<String, String> chaveDeAcesso, @Body Sincronia sincronia);

    @POST("webpedido/faturar")
    Call<List<WebPedido>> enviarPedidos(@Body List<WebPedido> webPedidos, @HeaderMap Map<String, String> chaveDeAcesso);

    @GET("historicofinanceiro/listar/{id}")
    Call<HistoricoFinanceiro> getHistoricoFinanceiro(@Path("id") int idCliente, @HeaderMap Map<String, String> chaveDeAcesso);

    @POST("foto/salvarimagem")
    Call<Foto> salvarImagem(@Body Foto foto);

    @GET("segmento/listar")
    Call<List<Segmento>> buscarTodosSegmentos(@HeaderMap Map<String, String> chaveDeAcesso);

    @GET("motivonaocadastramento/listar")
    Call<List<MotivoNaoCadastramento>> buscarTodosMotivos(@HeaderMap Map<String, String> chaveDeAcesso);

    @POST("prospect/salvar")
    Call<List<Prospect>> salvarProspect(@HeaderMap Map<String, String> chaveDeAcesso, @Body List<Prospect> prospect);

    @GET("maps/api/geocode/json?key=AIzaSyATLB7h2anOZofvV4KCfrhqMuZ9-1hr4HM")
    Call<RespostaGeocoder> getGeocoder(@Query("latlng") String latlng, @Query("sensor") Boolean sensor, @Query("language")String language);
}
