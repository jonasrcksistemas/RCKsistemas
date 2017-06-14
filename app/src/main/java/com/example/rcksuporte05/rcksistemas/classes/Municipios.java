package com.example.rcksuporte05.rcksistemas.classes;

public class Municipios {

    private String id_municipio;
    private String nome_municipio;
    private String uf;
    private String cep;

    public String getId_municipio() {
        if(id_municipio.equals("anyType{}")){
            id_municipio = "";
        }
        return id_municipio;
    }

    public void setId_municipio(String id_municipio) {
        this.id_municipio = id_municipio;
    }

    public String getNome_municipio() {
        if(nome_municipio.equals("anyType{}")){
            nome_municipio = "";
        }
        return nome_municipio;
    }

    public void setNome_municipio(String nome_municipio) {
        this.nome_municipio = nome_municipio;
    }

    public String getUf() {
        if(uf.equals("anyType{}")){
            uf = "";
        }
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        if(cep.equals("anyType{}")){
            cep = "";
        }
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
