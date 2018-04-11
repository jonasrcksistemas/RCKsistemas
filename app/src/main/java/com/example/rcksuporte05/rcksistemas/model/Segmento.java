package com.example.rcksuporte05.rcksistemas.model;

/**
 * Created by RCK 03 on 30/01/2018.
 */

public class Segmento {
    /*
    CREATE TABLE TBL_CADASTRO_SETOR
           (ATIVO VARCHAR(1), ID_SETOR INTEGER NOT NULL, NOME_SETOR VARCHAR(60),
            USUARIO_ID INTEGER, USUARIO_NOME VARCHAR(60), USUARIO_DATA TIMESTAMP,
            PRIMARY KEY (ID_SETOR));

     */

    private String ativo;
    private String idSetor;
    private String nomeSetor;
    private String descricaoOutros;


    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(String idSetor) {
        this.idSetor = idSetor;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public String getDescricaoOutros() {
        return descricaoOutros;
    }

    public void setDescricaoOutros(String descricaoOutros) {
        this.descricaoOutros = descricaoOutros;
    }
}
