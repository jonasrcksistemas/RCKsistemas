package com.example.rcksuporte05.rcksistemas.model;

public class ProdutoLinhaColecao {
    private String ativo;
    private int idLinhaColecao;
    private String nomeDescricaoLinha;
    private int usuarioId;
    private String usuarioNome;
    private String usuarioData;

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public int getIdLinhaColecao() {
        return idLinhaColecao;
    }

    public void setIdLinhaColecao(int idLinhaColecao) {
        this.idLinhaColecao = idLinhaColecao;
    }

    public String getNomeDescricaoLinha() {
        return nomeDescricaoLinha;
    }

    public void setNomeDescricaoLinha(String nomeDescricaoLinha) {
        this.nomeDescricaoLinha = nomeDescricaoLinha;
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
