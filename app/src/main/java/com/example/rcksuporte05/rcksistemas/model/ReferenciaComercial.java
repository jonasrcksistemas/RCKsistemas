package com.example.rcksuporte05.rcksistemas.model;

/**
 * Created by RCK 03 on 05/02/2018.
 */

public class ReferenciaComercial {
    private String id_referencia_comercial;
    private String id_referencia_comercial_servidor;
    private String nome_fornecedor_referencia;
    private String telefone;
    private String id_cadastro;
    private String id_cadastro_servidor;
    private int id_entidade;

    public String getId_referencia_comercial() {
        return id_referencia_comercial;
    }

    public void setId_referencia_comercial(String id_referencia_comercial) {
        this.id_referencia_comercial = id_referencia_comercial;
    }

    public String getNome_fornecedor_referencia() {
        return nome_fornecedor_referencia;
    }

    public void setNome_fornecedor_referencia(String nome_fornecedor_referencia) {
        this.nome_fornecedor_referencia = nome_fornecedor_referencia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getId_cadastro() {
        return id_cadastro;
    }

    public void setId_cadastro(String id_cadastro) {
        this.id_cadastro = id_cadastro;
    }

    public String getId_referencia_comercial_servidor() {
        return id_referencia_comercial_servidor;
    }

    public void setId_referencia_comercial_servidor(String id_referencia_comercial_servidor) {
        this.id_referencia_comercial_servidor = id_referencia_comercial_servidor;
    }

    public String getId_cadastro_servidor() {
        return id_cadastro_servidor;
    }

    public void setId_cadastro_servidor(String id_cadastro_servidor) {
        this.id_cadastro_servidor = id_cadastro_servidor;
    }

    public int getId_entidade() {
        return id_entidade;
    }

    public void setId_entidade(int id_entidade) {
        this.id_entidade = id_entidade;
    }
}
