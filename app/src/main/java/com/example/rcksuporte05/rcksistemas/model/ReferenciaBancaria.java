package com.example.rcksuporte05.rcksistemas.model;

/**
 * Created by RCK 03 on 05/02/2018.
 */

public class ReferenciaBancaria {
    private String id_referencia_bancaria;
    private String id_referencia_bancaria_servidor;
    private String codigo_febraban;
    private String nome_banco;
    private String conta_corrente;
    private String agencia;
    private String id_cadastro;
    private String id_cadastro_servidor;
    private int id_entidade;


    public String getId_referencia_bancaria() {
        return id_referencia_bancaria;
    }

    public void setId_referencia_bancaria(String id_referencia_bancaria) {
        this.id_referencia_bancaria = id_referencia_bancaria;
    }

    public String getCodigo_febraban() {
        return codigo_febraban;
    }

    public void setCodigo_febraban(String codigo_febraban) {
        this.codigo_febraban = codigo_febraban;
    }

    public String getNome_banco() {
        return nome_banco;
    }

    public void setNome_banco(String nome_banco) {
        this.nome_banco = nome_banco;
    }

    public String getConta_corrente() {
        return conta_corrente;
    }

    public void setConta_corrente(String conta_corrente) {
        this.conta_corrente = conta_corrente;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getId_cadastro() {
        return id_cadastro;
    }

    public void setId_cadastro(String id_cadastro) {
        this.id_cadastro = id_cadastro;
    }

    public String getId_referencia_bancaria_servidor() {
        return id_referencia_bancaria_servidor;
    }

    public void setId_referencia_bancaria_servidor(String id_referencia_bancaria_servidor) {
        this.id_referencia_bancaria_servidor = id_referencia_bancaria_servidor;
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
