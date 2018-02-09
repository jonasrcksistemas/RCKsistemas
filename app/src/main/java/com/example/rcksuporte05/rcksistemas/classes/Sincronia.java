package com.example.rcksuporte05.rcksistemas.classes;

import java.util.List;

public class Sincronia {
    private boolean cliente;
    private boolean produto;
    private boolean pedidosFinalizados;
    private boolean pedidosPendentes;
    private boolean municipio;
    private int maxProgress;
    private List<Cliente> listaCliente;
    private List<CondicoesPagamento> listaCondicoesPagamento;
    private List<Operacao> listaOperacao;
    private List<Produto> listaProduto;
    private List<TabelaPreco> listaTabelaPreco;
    private List<TabelaPrecoItem> listaTabelaPrecoItem;
    private List<Usuario> listaUsuario;
    private List<VendedorBonusResumo> listaVendedorBonusResumo;
    private List<WebPedido> listaWebPedidosPendentes;
    private List<WebPedido> listaWebPedidosFinalizados;
    private List<Pais> listaPais;
    private List<Banco> bancos;
    private List<Segmento> segmentos;
    private List<MotivoNaoCadastramento> motivos;

    public Sincronia(boolean cliente, boolean produto, boolean pedidosFinalizados, boolean pedidosPendentes) {
        this.cliente = cliente;
        this.produto = produto;
        this.pedidosFinalizados = pedidosFinalizados;
        this.pedidosPendentes = pedidosPendentes;
    }

    public boolean isCliente() {
        return cliente;
    }

    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }

    public boolean isProduto() {
        return produto;
    }

    public void setProduto(boolean produto) {
        this.produto = produto;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

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

    public List<WebPedido> getListaWebPedidosPendentes() {
        return listaWebPedidosPendentes;
    }

    public void setListaWebPedidosPendentes(List<WebPedido> listaWebPedidosPendentes) {
        this.listaWebPedidosPendentes = listaWebPedidosPendentes;
    }

    public boolean isPedidosFinalizados() {
        return pedidosFinalizados;
    }

    public void setPedidosFinalizados(boolean pedidosFinalizados) {
        this.pedidosFinalizados = pedidosFinalizados;
    }

    public boolean isPedidosPendentes() {
        return pedidosPendentes;
    }

    public void setPedidosPendentes(boolean pedidosPendentes) {
        this.pedidosPendentes = pedidosPendentes;
    }

    public List<WebPedido> getListaWebPedidosFinalizados() {
        return listaWebPedidosFinalizados;
    }

    public void setListaWebPedidosFinalizados(List<WebPedido> listaWebPedidosFinalizados) {
        this.listaWebPedidosFinalizados = listaWebPedidosFinalizados;
    }

    public List<Pais> getListaPais() {
        return listaPais;
    }

    public void setListaPais(List<Pais> listaPais) {
        this.listaPais = listaPais;
    }

    public List<Banco> getBancos() {
        return bancos;
    }

    public void setBancos(List<Banco> bancos) {
        this.bancos = bancos;
    }

    public List<Segmento> getSegmentos() {
        return segmentos;
    }

    public void setSegmentos(List<Segmento> segmentos) {
        this.segmentos = segmentos;
    }

    public List<MotivoNaoCadastramento> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<MotivoNaoCadastramento> motivos) {
        this.motivos = motivos;
    }
}
