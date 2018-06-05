package com.example.rcksuporte05.rcksistemas.model;

public class CadastroFinanceiroResumo {

    private int idCadastro;
    private Float limiteCredito;
    private Float financeiroVencido;
    private Float financeiroVencer;
    private Float financeiroQuitado;
    private Float pedidosLiberados;
    private Float limiteUtilizado;
    private Float limiteDisponivel;
    private int usuarioId;
    private String usuarioNome;
    private String usuarioData;
    private String dataUltimaAtualizacao;

    public int getIdCadastro() {
        return idCadastro;
    }

    public void setIdCadastro(int idCadastro) {
        this.idCadastro = idCadastro;
    }

    public Float getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(Float limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Float getFinanceiroVencido() {
        return financeiroVencido;
    }

    public void setFinanceiroVencido(Float financeiroVencido) {
        this.financeiroVencido = financeiroVencido;
    }

    public Float getFinanceiroVencer() {
        return financeiroVencer;
    }

    public void setFinanceiroVencer(Float financeiroVencer) {
        this.financeiroVencer = financeiroVencer;
    }

    public Float getFinanceiroQuitado() {
        return financeiroQuitado;
    }

    public void setFinanceiroQuitado(Float financeiroQuitado) {
        this.financeiroQuitado = financeiroQuitado;
    }

    public Float getPedidosLiberados() {
        return pedidosLiberados;
    }

    public void setPedidosLiberados(Float pedidosLiberados) {
        this.pedidosLiberados = pedidosLiberados;
    }

    public Float getLimiteUtilizado() {
        return limiteUtilizado;
    }

    public void setLimiteUtilizado(Float limiteUtilizado) {
        this.limiteUtilizado = limiteUtilizado;
    }

    public Float getLimiteDisponivel() {
        return limiteDisponivel;
    }

    public void setLimiteDisponivel(Float limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
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

    public String getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(String dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }
}
