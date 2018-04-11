package com.example.rcksuporte05.rcksistemas.model;

public class PromocaoRetorno {
    private String nomePromocao;
    private Float valorDesconto;

    public PromocaoRetorno(Float valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public String getNomePromocao() {
        return nomePromocao;
    }

    public void setNomePromocao(String nomePromocao) {
        this.nomePromocao = nomePromocao;
    }

    public Float getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Float valorDesconto) {
        this.valorDesconto = valorDesconto;
    }
}
