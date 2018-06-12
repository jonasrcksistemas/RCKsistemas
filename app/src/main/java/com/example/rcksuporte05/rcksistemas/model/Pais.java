package com.example.rcksuporte05.rcksistemas.model;

/**
 * Created by RCK 03 on 02/02/2018.
 */

public class Pais {
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
