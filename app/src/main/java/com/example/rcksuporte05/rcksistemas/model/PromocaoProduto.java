package com.example.rcksuporte05.rcksistemas.model;

public class PromocaoProduto {
    private int idPromocao;
    private int idProduto;
    private int idEmpresa;
    private String ativo;
    private String tipoDesconto;
    private Float descontoPerc;
    private Float descontoValor;
    private Float percComInterno;
    private Float percComExterno;
    private Float percComExportacao;
    private int usuarioId;
    private String usuarioNome;
    private String usuarioData;

    public int getIdPromocao() {
        return idPromocao;
    }

    public void setIdPromocao(int idPromocao) {
        this.idPromocao = idPromocao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getTipoDesconto() {
        return tipoDesconto;
    }

    public void setTipoDesconto(String tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }

    public Float getDescontoPerc() {
        return descontoPerc;
    }

    public void setDescontoPerc(Float descontoPerc) {
        this.descontoPerc = descontoPerc;
    }

    public Float getDescontoValor() {
        return descontoValor;
    }

    public void setDescontoValor(Float descontoValor) {
        this.descontoValor = descontoValor;
    }

    public Float getPercComInterno() {
        return percComInterno;
    }

    public void setPercComInterno(Float percComInterno) {
        this.percComInterno = percComInterno;
    }

    public Float getPercComExterno() {
        return percComExterno;
    }

    public void setPercComExterno(Float percComExterno) {
        this.percComExterno = percComExterno;
    }

    public Float getPercComExportacao() {
        return percComExportacao;
    }

    public void setPercComExportacao(Float percComExportacao) {
        this.percComExportacao = percComExportacao;
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
}
