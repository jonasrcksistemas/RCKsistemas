package com.example.rcksuporte05.rcksistemas.classes;

public class TabelaPrecoItem {
    private String id_item;
    private String id_tabela;
    private String perc_desc_inicial;
    private String perc_desc_final;
    private String perc_com_interno;
    private String perc_com_externo;
    private String perc_com_exportacao;
    private String pontos_premiacao;
    private String cor_painel;
    private String cor_fonte;
    private String verba_perc;
    private String utiliza_verba;
    private String desconto_verba_max;
    private String id_usuario;
    private String usuario;
    private String usuario_data;
    private String cor_web;

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public String getId_tabela() {
        return id_tabela;
    }

    public void setId_tabela(String id_tabela) {
        this.id_tabela = id_tabela;
    }

    public String getPerc_desc_inicial() {
        return perc_desc_inicial;
    }

    public void setPerc_desc_inicial(String perc_desc_inicial) {
        this.perc_desc_inicial = perc_desc_inicial;
    }

    public String getPerc_desc_final() {
        return perc_desc_final;
    }

    public void setPerc_desc_final(String perc_desc_final) {
        this.perc_desc_final = perc_desc_final;
    }

    public String getPerc_com_interno() {
        return perc_com_interno;
    }

    public void setPerc_com_interno(String perc_com_interno) {
        this.perc_com_interno = perc_com_interno;
    }

    public String getPerc_com_externo() {
        return perc_com_externo;
    }

    public void setPerc_com_externo(String perc_com_externo) {
        this.perc_com_externo = perc_com_externo;
    }

    public String getPerc_com_exportacao() {
        return perc_com_exportacao;
    }

    public void setPerc_com_exportacao(String perc_com_exportacao) {
        this.perc_com_exportacao = perc_com_exportacao;
    }

    public String getPontos_premiacao() {
        return pontos_premiacao;
    }

    public void setPontos_premiacao(String pontos_premiacao) {
        this.pontos_premiacao = pontos_premiacao;
    }

    public String getCor_painel() {
        return cor_painel;
    }

    public void setCor_painel(String cor_painel) {
        this.cor_painel = cor_painel;
    }

    public String getCor_fonte() {
        return cor_fonte;
    }

    public void setCor_fonte(String cor_fonte) {
        this.cor_fonte = cor_fonte;
    }

    public String getVerba_perc() {
        return verba_perc;
    }

    public void setVerba_perc(String verba_perc) {
        this.verba_perc = verba_perc;
    }

    public String getUtiliza_verba() {
        return utiliza_verba;
    }

    public void setUtiliza_verba(String utiliza_verba) {
        this.utiliza_verba = utiliza_verba;
    }

    public String getDesconto_verba_max() {
        return desconto_verba_max;
    }

    public void setDesconto_verba_max(String desconto_verba_max) {
        this.desconto_verba_max = desconto_verba_max;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario_data() {
        return usuario_data;
    }

    public void setUsuario_data(String usuario_data) {
        this.usuario_data = usuario_data;
    }

    public String getCor_web() {
        return cor_web;
    }

    public void setCor_web(String cor_web) {
        this.cor_web = cor_web;
    }

    @Override
    public String toString() {
        return String.format("%.2f", Float.parseFloat(perc_desc_inicial)) + "% = " + pontos_premiacao;
    }
}
