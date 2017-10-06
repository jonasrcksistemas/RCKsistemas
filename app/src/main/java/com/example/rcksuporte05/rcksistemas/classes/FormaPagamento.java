package com.example.rcksuporte05.rcksistemas.classes;

/**
 * Created by RCK 03 on 06/10/2017.
 */

class FormaPagamento {

    private String ativo;
    private String id_condicao;
    private String nome_condicao;
    private String numero_parcelas;
    private String intervalo_dias;
    private String tipo_condicao;
    private String nfe_tipo_financeiro;
    private String nfe_mostrar_parcelas;
    private String usuario_id;
    private String usuario_nome;
    private String usuario_data;
    private String publicar_na_web;

    public String getAtivo() {
        if (ativo == null) {
            ativo = " ";
        }
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getId_condicao() {
        if (id_condicao == null) {
            id_condicao = " ";
        }
        return id_condicao;
    }

    public void setId_condicao(String id_condicao) {
        this.id_condicao = id_condicao;
    }

    public String getNome_condicao() {
        if (nome_condicao == null) {
            nome_condicao = " ";
        }
        return nome_condicao;
    }

    public void setNome_condicao(String nome_condicao) {
        this.nome_condicao = nome_condicao;
    }

    public String getNumero_parcelas() {
        if (numero_parcelas == null) {
            numero_parcelas = " ";
        }
        return numero_parcelas;
    }

    public void setNumero_parcelas(String numero_parcelas) {
        this.numero_parcelas = numero_parcelas;
    }

    public String getIntervalo_dias() {
        if (intervalo_dias == null) {
            intervalo_dias = " ";
        }
        return intervalo_dias;
    }

    public void setIntervalo_dias(String intervalo_dias) {
        this.intervalo_dias = intervalo_dias;
    }

    public String getTipo_condicao() {
        if (tipo_condicao == null) {
            tipo_condicao = " ";
        }
        return tipo_condicao;
    }

    public void setTipo_condicao(String tipo_condicao) {
        this.tipo_condicao = tipo_condicao;
    }

    public String getNfe_tipo_financeiro() {
        if (nfe_tipo_financeiro == null) {
            nfe_tipo_financeiro = " ";
        }
        return nfe_tipo_financeiro;
    }

    public void setNfe_tipo_financeiro(String nfe_tipo_financeiro) {
        this.nfe_tipo_financeiro = nfe_tipo_financeiro;
    }

    public String getNfe_mostrar_parcelas() {
        if (nfe_mostrar_parcelas == null) {
            nfe_mostrar_parcelas = " ";
        }
        return nfe_mostrar_parcelas;
    }

    public void setNfe_mostrar_parcelas(String nfe_mostrar_parcelas) {
        this.nfe_mostrar_parcelas = nfe_mostrar_parcelas;
    }

    public String getUsuario_id() {
        if (usuario_id == null) {
            usuario_id = " ";
        }
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsuario_nome() {
        if (usuario_nome == null) {
            usuario_nome = " ";
        }
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getUsuario_data() {
        if (usuario_data == null) {
            usuario_data = " ";
        }
        return usuario_data;
    }

    public void setUsuario_data(String usuario_data) {
        this.usuario_data = usuario_data;
    }

    public String getPublicar_na_web() {
        if (publicar_na_web == null) {
            publicar_na_web = " ";
        }
        return publicar_na_web;
    }

    public void setPublicar_na_web(String publicar_na_web) {
        this.publicar_na_web = publicar_na_web;
    }

}
