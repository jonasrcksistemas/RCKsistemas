package com.example.rcksuporte05.rcksistemas.model;

public class HistoricoFinanceiroPendente {

    private String documento;
    private String parcela;
    private String especie;
    private String nome_conta;
    private String data_emissao;
    private String data_vencimento;
    private String valor_total;
    private String cobranca_descricao_status;
    private String historico;
    private String dias_atrazo;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getNome_conta() {
        return nome_conta;
    }

    public void setNome_conta(String id_conta) {
        this.nome_conta = id_conta;
    }

    public String getData_emissao() {
        return data_emissao;
    }

    public void setData_emissao(String data_emissao) {
        this.data_emissao = data_emissao;
    }

    public String getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(String data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public String getValor_total() {
        return valor_total;
    }

    public void setValor_total(String valor_total) {
        this.valor_total = valor_total;
    }

    public String getCobranca_descricao_status() {
        return cobranca_descricao_status;
    }

    public void setCobranca_descricao_status(String cobranca_descricao_status) {
        this.cobranca_descricao_status = cobranca_descricao_status;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getDias_atrazo() {
        return dias_atrazo;
    }

    public void setDias_atrazo(String dias_atrazo) {
        this.dias_atrazo = dias_atrazo;
    }
}
