package com.example.rcksuporte05.rcksistemas.classes;

public class WebPedido implements java.io.Serializable {

    private String id_web_pedido;
    private String id_empresa;
    private Cliente cadastro;
    private String id_vendedor;
    private String id_condicao_pagamento;
    private String id_operacao;
    private String id_tabela;
    private String nome_extenso;
    private String data_emissao;
    private String valor_produtos;
    private String valor_desconto;
    private String valor_desconto_add;
    private String desconto_per;
    private String desconto_per_add;
    private String valor_total;
    private String excluido;
    private String excluido_usuario_id;
    private String excluido_usuario_nome;
    private String excluido_usuario_data;
    private String justificativa_exclusao;
    private String usuario_lancamento_id;
    private String usuario_lancamento_nome;
    private String usuario_lancamento_data;
    private String observacoes;
    private String status;
    private String id_pedido_venda;
    private String id_nota_fiscal;
    private String id_tabela_preco_faixa;
    private String pontos_total;
    private String pontos_coeficiente;
    private String pontos_cor;
    private String comissao_percentual;
    private String comissao_valor;
    private String id_faixa_final;
    private String valor_bonus_credor;
    private String perc_bonus_credor;
    private String origem;
    private String id_web_pedido_servidor;

    public WebPedido() {
    }

    public WebPedido(Cliente cadastro) {
        this.cadastro = cadastro;
    }

    public Cliente getCadastro() {
        return cadastro;
    }

    public void setCadastro(Cliente cadastro) {
        this.cadastro = cadastro;
    }

    public String getId_web_pedido() {
        return id_web_pedido;
    }

    public void setId_web_pedido(String id_web_pedido) {
        this.id_web_pedido = id_web_pedido;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(String id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getId_condicao_pagamento() {
        return id_condicao_pagamento;
    }

    public void setId_condicao_pagamento(String id_condicao_pagamento) {
        this.id_condicao_pagamento = id_condicao_pagamento;
    }

    public String getId_operacao() {
        return id_operacao;
    }

    public void setId_operacao(String id_operacao) {
        this.id_operacao = id_operacao;
    }

    public String getId_tabela() {
        return id_tabela;
    }

    public void setId_tabela(String id_tabela) {
        this.id_tabela = id_tabela;
    }

    public String getNome_extenso() {
        return nome_extenso;
    }

    public void setNome_extenso(String nome_extenso) {
        this.nome_extenso = nome_extenso;
    }

    public String getData_emissao() {
        return data_emissao;
    }

    public void setData_emissao(String data_emissao) {
        this.data_emissao = data_emissao;
    }

    public String getValor_produtos() {
        return valor_produtos;
    }

    public void setValor_produtos(String valor_produtos) {
        this.valor_produtos = valor_produtos;
    }

    public String getValor_desconto() {
        return valor_desconto;
    }

    public void setValor_desconto(String valor_desconto) {
        this.valor_desconto = valor_desconto;
    }

    public String getValor_desconto_add() {
        return valor_desconto_add;
    }

    public void setValor_desconto_add(String valor_desconto_add) {
        this.valor_desconto_add = valor_desconto_add;
    }

    public String getDesconto_per() {
        return desconto_per;
    }

    public void setDesconto_per(String desconto_per) {
        this.desconto_per = desconto_per;
    }

    public String getDesconto_per_add() {
        return desconto_per_add;
    }

    public void setDesconto_per_add(String desconto_per_add) {
        this.desconto_per_add = desconto_per_add;
    }

    public String getValor_total() {
        return valor_total;
    }

    public void setValor_total(String valor_total) {
        this.valor_total = valor_total;
    }

    public String getExcluido() {
        return excluido;
    }

    public void setExcluido(String excluido) {
        this.excluido = excluido;
    }

    public String getExcluido_usuario_id() {
        return excluido_usuario_id;
    }

    public void setExcluido_usuario_id(String excluido_usuario_id) {
        this.excluido_usuario_id = excluido_usuario_id;
    }

    public String getExcluido_usuario_nome() {
        return excluido_usuario_nome;
    }

    public void setExcluido_usuario_nome(String excluido_usuario_nome) {
        this.excluido_usuario_nome = excluido_usuario_nome;
    }

    public String getExcluido_usuario_data() {
        return excluido_usuario_data;
    }

    public void setExcluido_usuario_data(String excluido_usuario_data) {
        this.excluido_usuario_data = excluido_usuario_data;
    }

    public String getJustificativa_exclusao() {
        return justificativa_exclusao;
    }

    public void setJustificativa_exclusao(String justificativa_exclusao) {
        this.justificativa_exclusao = justificativa_exclusao;
    }

    public String getUsuario_lancamento_id() {
        return usuario_lancamento_id;
    }

    public void setUsuario_lancamento_id(String usuario_lancamento_id) {
        this.usuario_lancamento_id = usuario_lancamento_id;
    }

    public String getUsuario_lancamento_nome() {
        return usuario_lancamento_nome;
    }

    public void setUsuario_lancamento_nome(String usuario_lancamento_nome) {
        this.usuario_lancamento_nome = usuario_lancamento_nome;
    }

    public String getUsuario_lancamento_data() {
        return usuario_lancamento_data;
    }

    public void setUsuario_lancamento_data(String usuario_lancamento_data) {
        this.usuario_lancamento_data = usuario_lancamento_data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_pedido_venda() {
        return id_pedido_venda;
    }

    public void setId_pedido_venda(String id_pedido_venda) {
        this.id_pedido_venda = id_pedido_venda;
    }

    public String getId_nota_fiscal() {
        return id_nota_fiscal;
    }

    public void setId_nota_fiscal(String id_nota_fiscal) {
        this.id_nota_fiscal = id_nota_fiscal;
    }

    public String getId_tabela_preco_faixa() {
        return id_tabela_preco_faixa;
    }

    public void setId_tabela_preco_faixa(String id_tabela_preco_faixa) {
        this.id_tabela_preco_faixa = id_tabela_preco_faixa;
    }

    public String getPontos_total() {
        return pontos_total;
    }

    public void setPontos_total(String pontos_total) {
        this.pontos_total = pontos_total;
    }

    public String getPontos_coeficiente() {
        return pontos_coeficiente;
    }

    public void setPontos_coeficiente(String pontos_coeficiente) {
        this.pontos_coeficiente = pontos_coeficiente;
    }

    public String getPontos_cor() {
        return pontos_cor;
    }

    public void setPontos_cor(String pontos_cor) {
        this.pontos_cor = pontos_cor;
    }

    public String getComissao_percentual() {
        return comissao_percentual;
    }

    public void setComissao_percentual(String comissao_percentual) {
        this.comissao_percentual = comissao_percentual;
    }

    public String getComissao_valor() {
        return comissao_valor;
    }

    public void setComissao_valor(String comissao_valor) {
        this.comissao_valor = comissao_valor;
    }

    public String getId_faixa_final() {
        return id_faixa_final;
    }

    public void setId_faixa_final(String id_faixa_final) {
        this.id_faixa_final = id_faixa_final;
    }

    public String getValor_bonus_credor() {
        return valor_bonus_credor;
    }

    public void setValor_bonus_credor(String valor_bonus_credor) {
        this.valor_bonus_credor = valor_bonus_credor;
    }

    public String getPerc_bonus_credor() {
        return perc_bonus_credor;
    }

    public void setPerc_bonus_credor(String perc_bonus_credor) {
        this.perc_bonus_credor = perc_bonus_credor;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getId_web_pedido_servidor() {
        return id_web_pedido_servidor;
    }

    public void setId_web_pedido_servidor(String id_web_pedido_servidor) {
        this.id_web_pedido_servidor = id_web_pedido_servidor;
    }
}
