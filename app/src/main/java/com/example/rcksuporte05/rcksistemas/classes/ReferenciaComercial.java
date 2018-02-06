package com.example.rcksuporte05.rcksistemas.classes;

/**
 * Created by RCK 03 on 05/02/2018.
 */

public class ReferenciaComercial {
    private String id_referencia_comercial;
    private String nome_fornecedor_referencia;
    private String telefone;


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


}
