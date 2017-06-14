package com.example.rcksuporte05.rcksistemas.classes;

public class Operacao {

    private String ativo;
    private String id_operacao;
    private String nome_operacao;

    public String getAtivo() {
        if (ativo.equals("anyType{}")) {
            ativo = "";
        }
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getId_operacao() {
        if (id_operacao.equals("anyType{}")) {
            id_operacao = "";
        }
        return id_operacao;
    }

    public void setId_operacao(String id_operacao) {
        this.id_operacao = id_operacao;
    }

    public String getNome_operacao() {
        if (nome_operacao.equals("anyType{}")) {
            nome_operacao = "";
        }
        return nome_operacao;
    }

    public void setNome_operacao(String nome_operacao) {
        this.nome_operacao = nome_operacao;
    }

    @Override
    public String toString() {
        return nome_operacao;
    }
}