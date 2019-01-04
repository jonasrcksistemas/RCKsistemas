package com.example.rcksuporte05.rcksistemas.model;

public class CampanhaComercialItens {

    private String ativo;
    private int idEmpresa;
    private int idTipoCampanha;
    private int idBaseCampanha;
    private int idCampanha;
    private int idLinhaProduto;
    private int idProdutoVenda;
    private String nomeProdutoLinha;
    private Float quantidadeVenda;
    private int idProdutoBonus;
    private String nomeProdutoBonus;
    private Float quantidadeBonus;
    private int usuarioId;
    private String usuarioNome;
    private String usuarioData;

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdTipoCampanha() {
        return idTipoCampanha;
    }

    public void setIdTipoCampanha(int idTipoCampanha) {
        this.idTipoCampanha = idTipoCampanha;
    }

    public int getIdBaseCampanha() {
        return idBaseCampanha;
    }

    public void setIdBaseCampanha(int idBaseCampanha) {
        this.idBaseCampanha = idBaseCampanha;
    }

    public int getIdCampanha() {
        return idCampanha;
    }

    public void setIdCampanha(int idCampanha) {
        this.idCampanha = idCampanha;
    }

    public int getIdLinhaProduto() {
        return idLinhaProduto;
    }

    public void setIdLinhaProduto(int idLinhaProduto) {
        this.idLinhaProduto = idLinhaProduto;
    }

    public int getIdProdutoVenda() {
        return idProdutoVenda;
    }

    public void setIdProdutoVenda(int idProdutoVenda) {
        this.idProdutoVenda = idProdutoVenda;
    }

    public Float getQuantidadeVenda() {
        return quantidadeVenda;
    }

    public void setQuantidadeVenda(Float quantidadeVenda) {
        this.quantidadeVenda = quantidadeVenda;
    }

    public int getIdProdutoBonus() {
        return idProdutoBonus;
    }

    public void setIdProdutoBonus(int idProdutoBonus) {
        this.idProdutoBonus = idProdutoBonus;
    }

    public Float getQuantidadeBonus() {
        return quantidadeBonus;
    }

    public void setQuantidadeBonus(Float quantidadeBonus) {
        this.quantidadeBonus = quantidadeBonus;
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

    public String getNomeProdutoLinha() {
        return nomeProdutoLinha;
    }

    public void setNomeProdutoLinha(String nomeProdutoLinha) {
        this.nomeProdutoLinha = nomeProdutoLinha;
    }

    public String getNomeProdutoBonus() {
        return nomeProdutoBonus;
    }

    public void setNomeProdutoBonus(String nomeProdutoBonus) {
        this.nomeProdutoBonus = nomeProdutoBonus;
    }
}
