package com.example.rcksuporte05.rcksistemas.classes;

public class VendedorBonusResumo {

    private String id_vendedor;
    private String id_empresa;
    private String valor_credito;
    private String valor_debito;
    private String valor_bonus_cancelados;
    private String valor_saldo;
    private String data_ultima_atualizacao;

    public String getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(String id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getValor_credito() {
        return valor_credito;
    }

    public void setValor_credito(String valor_credito) {
        this.valor_credito = valor_credito;
    }

    public String getValor_debito() {
        return valor_debito;
    }

    public void setValor_debito(String valor_debito) {
        this.valor_debito = valor_debito;
    }

    public String getValor_bonus_cancelados() {
        return valor_bonus_cancelados;
    }

    public void setValor_bonus_cancelados(String valor_bonus_cancelados) {
        this.valor_bonus_cancelados = valor_bonus_cancelados;
    }

    public String getValor_saldo() {
        return valor_saldo;
    }

    public void setValor_saldo(String valor_saldo) {
        this.valor_saldo = valor_saldo;
    }

    public String getData_ultima_atualizacao() {
        return data_ultima_atualizacao;
    }

    public void setData_ultima_atualizacao(String data_ultima_atualizacao) {
        this.data_ultima_atualizacao = data_ultima_atualizacao;
    }
}
