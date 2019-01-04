package com.example.rcksuporte05.rcksistemas.model;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class VisitaProspect {
    private String idVisita;
    private Prospect prospect;
    private String descricaoVisita;
    private String dataVisita;
    private String dataRetorno;
    private String tipoContato;
    private String usuario_id;
    private String latitude;
    private String longitude;
    private String idVisitaServidor;
    private String titulo;
    private CadastroAnexo fotoPrincipalBase64;
    private CadastroAnexo fotoSecundariaBase64;

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public Prospect getProspect() {
        return prospect;
    }

    public void setProspect(Prospect prospect) {
        this.prospect = prospect;
    }

    public String getDescricaoVisita() {
        return descricaoVisita;
    }

    public void setDescricaoVisita(String descricaoVisita) {
        this.descricaoVisita = descricaoVisita;
    }

    public String getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(String dataVisita) {
        this.dataVisita = dataVisita;
    }

    public String getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(String dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public String getTipoContato() {
        return tipoContato;
    }

    public void setTipoContato(String tipoContato) {
        this.tipoContato = tipoContato;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIdVisitaServidor() {
        return idVisitaServidor;
    }

    public void setIdVisitaServidor(String idVisitaServidor) {
        this.idVisitaServidor = idVisitaServidor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public CadastroAnexo getFotoPrincipalBase64() {
        return fotoPrincipalBase64;
    }

    public void setFotoPrincipalBase64(CadastroAnexo fotoPrincipalBase64) {
        this.fotoPrincipalBase64 = fotoPrincipalBase64;
    }

    public CadastroAnexo getFotoSecundariaBase64() {
        return fotoSecundariaBase64;
    }

    public void setFotoSecundariaBase64(CadastroAnexo fotoSecundariaBase64) {
        this.fotoSecundariaBase64 = fotoSecundariaBase64;
    }
}
