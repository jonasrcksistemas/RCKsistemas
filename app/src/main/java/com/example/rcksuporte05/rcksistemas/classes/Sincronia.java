package com.example.rcksuporte05.rcksistemas.classes;

import java.util.List;

public class Sincronia {
    private List<Cliente> listaCliente;
    private List<CondicoesPagamento> listaCondicoesPagamento;
    private List<Operacao> listaOperacao;
    private List<Produto> listaProduto;
    private List<TabelaPreco> listaTabelaPreco;
    private List<TabelaPrecoItem> listaTabelaPrecoItem;
    private List<Usuario> listaUsuario;
    private List<VendedorBonusResumo> listaVendedorBonusResumo;
    private List<WebPedido> listaWebPedidos;

    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public List<CondicoesPagamento> getListaCondicoesPagamento() {
        return listaCondicoesPagamento;
    }

    public void setListaCondicoesPagamento(List<CondicoesPagamento> listaCondicoesPagamento) {
        this.listaCondicoesPagamento = listaCondicoesPagamento;
    }

    public List<Operacao> getListaOperacao() {
        return listaOperacao;
    }

    public void setListaOperacao(List<Operacao> listaOperacao) {
        this.listaOperacao = listaOperacao;
    }

    public List<Produto> getListaProduto() {
        return listaProduto;
    }

    public void setListaProduto(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
    }

    public List<TabelaPreco> getListaTabelaPreco() {
        return listaTabelaPreco;
    }

    public void setListaTabelaPreco(List<TabelaPreco> listaTabelaPreco) {
        this.listaTabelaPreco = listaTabelaPreco;
    }

    public List<TabelaPrecoItem> getListaTabelaPrecoItem() {
        return listaTabelaPrecoItem;
    }

    public void setListaTabelaPrecoItem(List<TabelaPrecoItem> listaTabelaPrecoItem) {
        this.listaTabelaPrecoItem = listaTabelaPrecoItem;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public List<VendedorBonusResumo> getListaVendedorBonusResumo() {
        return listaVendedorBonusResumo;
    }

    public void setListaVendedorBonusResumo(List<VendedorBonusResumo> listaVendedorBonusResumo) {
        this.listaVendedorBonusResumo = listaVendedorBonusResumo;
    }

    public List<WebPedido> getListaWebPedidos() {
        return listaWebPedidos;
    }

    public void setListaWebPedidos(List<WebPedido> listaWebPedidos) {
        this.listaWebPedidos = listaWebPedidos;
    }
}
