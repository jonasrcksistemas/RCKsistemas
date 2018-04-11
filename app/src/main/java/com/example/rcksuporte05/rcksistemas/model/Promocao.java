package com.example.rcksuporte05.rcksistemas.model;

import java.util.List;

public class Promocao {
    private int idPromocao;
    private int idEmpresa;
    private int numeroClientes;
    private int numeroProdutos;
    private String ativo;
    private int aplicacaoCliente;
    private int aplicacaoProduto;
    private Float descontoPerc;
    private String dataInicioPromocao;
    private String dataFimPromocao;
    private String nomePromocao;
    private int usuarioId;
    private String usuarioNome;
    private String usuarioData;
    private List<PromocaoCliente> listaPromoCliente;
    private List<PromocaoProduto> listaPromoProduto;

    public int getIdPromocao() {
        return idPromocao;
    }

    public void setIdPromocao(int idPromocao) {
        this.idPromocao = idPromocao;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getNumeroClientes() {
        return numeroClientes;
    }

    public void setNumeroClientes(int numeroClientes) {
        this.numeroClientes = numeroClientes;
    }

    public int getNumeroProdutos() {
        return numeroProdutos;
    }

    public void setNumeroProdutos(int numeroProdutos) {
        this.numeroProdutos = numeroProdutos;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public int getAplicacaoCliente() {
        return aplicacaoCliente;
    }

    public void setAplicacaoCliente(int aplicacaoCliente) {
        this.aplicacaoCliente = aplicacaoCliente;
    }

    public int getAplicacaoProduto() {
        return aplicacaoProduto;
    }

    public void setAplicacaoProduto(int aplicacaoProduto) {
        this.aplicacaoProduto = aplicacaoProduto;
    }

    public Float getDescontoPerc() {
        return descontoPerc;
    }

    public void setDescontoPerc(Float descontoPerc) {
        this.descontoPerc = descontoPerc;
    }

    public String getDataInicioPromocao() {
        return dataInicioPromocao;
    }

    public void setDataInicioPromocao(String dataInicioPromocao) {
        this.dataInicioPromocao = dataInicioPromocao;
    }

    public String getDataFimPromocao() {
        return dataFimPromocao;
    }

    public void setDataFimPromocao(String dataFimPromocao) {
        this.dataFimPromocao = dataFimPromocao;
    }

    public String getNomePromocao() {
        return nomePromocao;
    }

    public void setNomePromocao(String nomePromocao) {
        this.nomePromocao = nomePromocao;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getUsuarioData() {
        return usuarioData;
    }

    public void setUsuarioData(String usuarioData) {
        this.usuarioData = usuarioData;
    }

    public List<PromocaoCliente> getListaPromoCliente() {
        return listaPromoCliente;
    }

    public void setListaPromoCliente(List<PromocaoCliente> listaPromoCliente) {
        this.listaPromoCliente = listaPromoCliente;
    }

    public List<PromocaoProduto> getListaPromoProduto() {
        return listaPromoProduto;
    }

    public void setListaPromoProduto(List<PromocaoProduto> listaPromoProduto) {
        this.listaPromoProduto = listaPromoProduto;
    }
}