package com.example.rcksuporte05.rcksistemas.model;

import java.util.ArrayList;
import java.util.List;

public class Sincronia {
    private boolean cliente;
    private boolean produto;
    private boolean pedidosFinalizados;
    private boolean pedidosPendentes;
    private boolean prospectPendentes;
    private boolean prospectEnviados;
    private boolean visitasPendentes;
    private int maxProgress;
    private List<Cliente> listaCliente = new ArrayList<>();
    private List<CondicoesPagamento> listaCondicoesPagamento = new ArrayList<>();
    private List<Operacao> listaOperacao = new ArrayList<>();
    private List<Produto> listaProduto = new ArrayList<>();
    private List<TabelaPreco> listaTabelaPreco = new ArrayList<>();
    private List<TabelaPrecoItem> listaTabelaPrecoItem = new ArrayList<>();
    private List<Usuario> listaUsuario = new ArrayList<>();
    private List<WebPedido> listaWebPedidosPendentes = new ArrayList<>();
    private List<WebPedido> listaWebPedidosFinalizados = new ArrayList<>();
    private List<Pais> listaPais = new ArrayList<>();
    private List<MotivoNaoCadastramento> motivos = new ArrayList<>();
    private List<Prospect> listaProspectPendentes = new ArrayList<>();
    private List<Prospect> listaProspectEnviados = new ArrayList<>();
    private List<Prospect> listaProspectPositivados = new ArrayList<>();
    private List<VisitaProspect> visitas = new ArrayList<>();
    private List<Categoria> listaCategoria = new ArrayList<>();
    private List<Promocao> listaPromocao = new ArrayList<>();
    private List<CadastroFinanceiroResumo> listaCadastroFinanceiroResumo = new ArrayList<>();
    private List<CampanhaComClientes> listaCampanhaComClientes = new ArrayList<>();
    private List<CampanhaComercialCab> listaCampanhaComercialCab = new ArrayList<>();
    private List<CampanhaComercialItens> listaCampanhaComercialItens = new ArrayList<>();
    private List<ProdutoLinhaColecao> listaProdutoLinhaColecao = new ArrayList<>();
    private List<CadastroCondicoesPag> listaCadastroCondicoesPag = new ArrayList<>();

    public Sincronia(boolean cliente,
                     boolean produto,
                     boolean pedidosFinalizados,
                     boolean pedidosPendentes,
                     boolean prospect,
                     boolean prospectEnviados,
                     boolean visitasPendentes) {
        this.cliente = cliente;
        this.produto = produto;
        this.pedidosFinalizados = pedidosFinalizados;
        this.pedidosPendentes = pedidosPendentes;
        this.prospectPendentes = prospect;
        this.prospectEnviados = prospectEnviados;
        this.visitasPendentes = visitasPendentes;
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

    public List<MotivoNaoCadastramento> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<MotivoNaoCadastramento> motivos) {
        this.motivos = motivos;
    }

    public boolean isProspectPendentes() {
        return prospectPendentes;
    }

    public void setProspectPendentes(boolean prospectPendentes) {
        this.prospectPendentes = prospectPendentes;
    }

    public List<Prospect> getListaProspectPendentes() {
        return listaProspectPendentes;
    }

    public void setListaProspectPendentes(List<Prospect> listaProspectPendentes) {
        this.listaProspectPendentes = listaProspectPendentes;
    }

    public List<Prospect> getListaProspectEnviados() {
        return listaProspectEnviados;
    }

    public void setListaProspectEnviados(List<Prospect> listaProspectEnviados) {
        this.listaProspectEnviados = listaProspectEnviados;
    }

    public boolean isProspectEnviados() {
        return prospectEnviados;
    }

    public void setProspectEnviados(boolean prospectEnviados) {
        this.prospectEnviados = prospectEnviados;
    }

    public boolean isVisitasPendentes() {
        return visitasPendentes;
    }

    public void setVisitasPendentes(boolean visitasPendentes) {
        this.visitasPendentes = visitasPendentes;
    }

    public List<VisitaProspect> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<VisitaProspect> visitas) {
        this.visitas = visitas;
    }

    public List<Categoria> getListaCategoria() {
        return listaCategoria;
    }

    public void setListaCategoria(List<Categoria> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

    public List<Promocao> getListaPromocao() {
        return listaPromocao;
    }

    public void setListaPromocao(List<Promocao> listaPromocao) {
        this.listaPromocao = listaPromocao;
    }

    public List<Prospect> getListaProspectPositivados() {
        return listaProspectPositivados;
    }

    public void setListaProspectPositivados(List<Prospect> listaProspectPositivados) {
        this.listaProspectPositivados = listaProspectPositivados;
    }

    public List<CadastroFinanceiroResumo> getListaCadastroFinanceiroResumo() {
        return listaCadastroFinanceiroResumo;
    }

    public void setListaCadastroFinanceiroResumo(List<CadastroFinanceiroResumo> listaCadastroFinanceiroResumo) {
        this.listaCadastroFinanceiroResumo = listaCadastroFinanceiroResumo;
    }

    public List<CampanhaComClientes> getListaCampanhaComClientes() {
        return listaCampanhaComClientes;
    }

    public void setListaCampanhaComClientes(List<CampanhaComClientes> listaCampanhaComClientes) {
        this.listaCampanhaComClientes = listaCampanhaComClientes;
    }

    public List<CampanhaComercialCab> getListaCampanhaComercialCab() {
        return listaCampanhaComercialCab;
    }

    public void setListaCampanhaComercialCab(List<CampanhaComercialCab> listaCampanhaComercialCab) {
        this.listaCampanhaComercialCab = listaCampanhaComercialCab;
    }

    public List<CampanhaComercialItens> getListaCampanhaComercialItens() {
        return listaCampanhaComercialItens;
    }

    public void setListaCampanhaComercialItens(List<CampanhaComercialItens> listaCampanhaComercialItens) {
        this.listaCampanhaComercialItens = listaCampanhaComercialItens;
    }

    public List<ProdutoLinhaColecao> getListaProdutoLinhaColecao() {
        return listaProdutoLinhaColecao;
    }

    public void setListaProdutoLinhaColecao(List<ProdutoLinhaColecao> listaProdutoLinhaColecao) {
        this.listaProdutoLinhaColecao = listaProdutoLinhaColecao;
    }

    public List<CadastroCondicoesPag> getListaCadastroCondicoesPag() {
        return listaCadastroCondicoesPag;
    }

    public void setListaCadastroCondicoesPag(List<CadastroCondicoesPag> listaCadastroCondicoesPag) {
        this.listaCadastroCondicoesPag = listaCadastroCondicoesPag;
    }
}
