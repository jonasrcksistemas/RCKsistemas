package com.example.rcksuporte05.rcksistemas.model;

public class CadastroCondicoesPag {
    private int idCondicao;
    private int idCadastro;
    private int usuarioId;
    private String usuarioNome;
    private String usuarioData;

    public int getIdCondicao() {
        return idCondicao;
    }

    public void setIdCondicao(int idCondicao) {
        this.idCondicao = idCondicao;
    }

    public int getIdCadastro() {
        return idCadastro;
    }

    public void setIdCadastro(int idCadastro) {
        this.idCadastro = idCadastro;
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
