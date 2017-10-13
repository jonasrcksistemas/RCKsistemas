package com.example.rcksuporte05.rcksistemas.classes;

public class Paises {

    private String id_pais;
    private String nome_pais;

    public String getId_pais() {
        return id_pais;
    }

    public void setId_pais(String id_pais) {
        this.id_pais = id_pais;
    }

    public String getNome_pais() {
        return nome_pais;
    }

    public void setNome_pais(String nome_pais) {
        this.nome_pais = nome_pais;
    }

    @Override
    public String toString() {
        return nome_pais;
    }
}
