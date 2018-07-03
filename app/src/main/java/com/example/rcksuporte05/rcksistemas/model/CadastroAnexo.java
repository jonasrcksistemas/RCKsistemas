package com.example.rcksuporte05.rcksistemas.model;

import android.graphics.Bitmap;

public class CadastroAnexo {
    private int idAnexo;
    private int idAnexoServidor;
    private int idEntidade;
    private int idCadastro;
    private int idCadastroServidor;
    private String nomeAnexo;
    private String anexo;
    transient private Bitmap miniatura;
    private String excluido;

    public int getIdAnexo() {
        return idAnexo;
    }

    public void setIdAnexo(int idAnexo) {
        this.idAnexo = idAnexo;
    }

    public int getIdAnexoServidor() {
        return idAnexoServidor;
    }

    public void setIdAnexoServidor(int idAnexoServidor) {
        this.idAnexoServidor = idAnexoServidor;
    }

    public int getIdEntidade() {
        return idEntidade;
    }

    public void setIdEntidade(int idEntidade) {
        this.idEntidade = idEntidade;
    }

    public int getIdCadastro() {
        return idCadastro;
    }

    public void setIdCadastro(int idCadastro) {
        this.idCadastro = idCadastro;
    }

    public int getIdCadastroServidor() {
        return idCadastroServidor;
    }

    public void setIdCadastroServidor(int idCadastroServidor) {
        this.idCadastroServidor = idCadastroServidor;
    }

    public String getNomeAnexo() {
        return nomeAnexo;
    }

    public void setNomeAnexo(String nomeAnexo) {
        this.nomeAnexo = nomeAnexo;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public Bitmap getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(Bitmap miniatura) {
        this.miniatura = miniatura;
    }

    public String getExcluido() {
        return excluido;
    }

    public void setExcluido(String excluido) {
        this.excluido = excluido;
    }
}
