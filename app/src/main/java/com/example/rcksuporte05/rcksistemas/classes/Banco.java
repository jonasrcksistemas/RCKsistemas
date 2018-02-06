package com.example.rcksuporte05.rcksistemas.classes;

/**
 * Created by RCK 03 on 05/02/2018.
 */

public class Banco {
    public String codigo_febraban;
    public String nome_banco;
    public String home_page;

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

    public String getHome_page() {
        return home_page;
    }

    public void setHome_page(String home_page) {
        this.home_page = home_page;
    }

    @Override
    public String toString() {
        return codigo_febraban+" "+nome_banco;
    }
}
