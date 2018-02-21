package com.example.rcksuporte05.rcksistemas.classes;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class VisitaProspect {
    private int idVisita;
    private Prospect prospect;
    private String DescricaoVisita;
    private MotivoNaoCadastramento motivoNaoCadastramento;
    private String dataVisita;
    private String dataRetorno;
    private String tipoContato;
    private String usuario_id;
    private String latitude;
    private String longitude;

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public Prospect getProspect() {
        return prospect;
    }

    public void setProspect(Prospect prospect) {
        this.prospect = prospect;
    }

    public String getDescricaoVisita() {
        return DescricaoVisita;
    }

    public void setDescricaoVisita(String descricaoVisita) {
        DescricaoVisita = descricaoVisita;
    }

    public MotivoNaoCadastramento getMotivoNaoCadastramento() {
        return motivoNaoCadastramento;
    }

    public void setMotivoNaoCadastramento(MotivoNaoCadastramento motivoNaoCadastramento) {
        this.motivoNaoCadastramento = motivoNaoCadastramento;
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
}
