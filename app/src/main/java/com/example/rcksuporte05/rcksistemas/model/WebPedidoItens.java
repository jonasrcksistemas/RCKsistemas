package com.example.rcksuporte05.rcksistemas.model;

public class WebPedidoItens extends Produto {

    private String id_web_item;
    private String id_pedido;
    private String quantidade;
    private Float valor_unitario;
    private String valor_bruto;
    private String valor_desconto_per;
    private String valor_desconto_real;
    private String valor_desconto_per_add;
    private String valor_desconto_real_add;
    private String valor_total;
    private String data_movimentacao;
    private String usuario_lancamento_id;
    private String usuario_lancamento_data;
    private String id_item_desconto;
    private String pontos_unitario;
    private String pontos_total;
    private String pontos_coeficiente;
    private String pontos_cor;
    private String comissao_percentual;
    private String comissao_valor;
    private String valor_bonus_credor;
    private String perc_bonus_credor;
    private String id_tabela_preco;
    private String valor_desconto_per_orig;
    private String valor_desconto_real_orig;
    private String valor_desconto_per_add_orig;
    private String valor_desconto_real_add_orig;
    private String id_tabela_preco_faixa_orig;
    private String valor_total_orig;
    private String pontos_unitario_orig;
    private String pontos_coeficiente_orig;
    private String comissao_percentual_orig;
    private String valor_bonus_credor_orig;
    private String perc_bonus_credor_orig;
    private String comissao_valor_orig;
    private String pontos_total_orig;
    private String pontos_cor_orig;
    private String valor_preco_pago;
    private String id_web_item_servidor;
    private String tipoDesconto;
    private Boolean descontoIndevido = false;
    private Boolean produtoBase = true;

    public WebPedidoItens(Produto produto) {
        this.setAtivo(produto.getAtivo());
        this.setId_produto(produto.getId_produto());
        this.setNome_produto(produto.getNome_produto());
        this.setUnidade(produto.getUnidade());
        this.setTipo_cadastro(produto.getTipo_cadastro());
        this.setId_entidade(produto.getId_entidade());
        this.setNcm(produto.getNcm());
        this.setId_grupo(produto.getId_grupo());
        this.setId_sub_grupo(produto.getId_sub_grupo());
        this.setPeso_bruto(produto.getPeso_bruto());
        this.setPeso_liquido(produto.getPeso_liquido());
        this.setCodigo_em_barras(produto.getCodigo_em_barras());
        this.setMovimenta_estoque(produto.getMovimenta_estoque());
        this.setNome_da_marca(produto.getNome_da_marca());
        this.setId_empresa(produto.getId_empresa());
        this.setId_origem(produto.getId_origem());
        this.setCusto_produto(produto.getCusto_produto());
        this.setCusto_per_ipi(produto.getCusto_per_ipi());
        this.setCusto_ipi(produto.getCusto_ipi());
        this.setCusto_per_frete(produto.getCusto_per_frete());
        this.setCusto_frete(produto.getCusto_frete());
        this.setCusto_per_icms(produto.getCusto_per_icms());
        this.setCusto_icms(produto.getCusto_icms());
        this.setCusto_per_fin(produto.getCusto_per_fin());
        this.setCusto_fin(produto.getCusto_fin());
        this.setCusto_per_subst(produto.getCusto_per_subst());
        this.setCusto_subt(produto.getCusto_subt());
        this.setCusto_per_outros(produto.getCusto_per_outros());
        this.setCusto_outros(produto.getCusto_outros());
        this.setValor_custo(produto.getValor_custo());
        this.setExcluido(produto.getExcluido());
        this.setExcluido_por(produto.getExcluido_por());
        this.setExcluido_por_data(produto.getExcluido_por_data());
        this.setExcluido_codigo_novo(produto.getExcluido_codigo_novo());
        this.setAjuste_preco_data(produto.getAjuste_preco_data());
        this.setAjuste_preco_nfe(produto.getAjuste_preco_nfe());
        this.setAjuste_preco_usuario(produto.getAjuste_preco_usuario());
        this.setTotal_custo(produto.getTotal_custo());
        this.setTotal_credito(produto.getTotal_credito());
        this.setValor_custo_estoque(produto.getValor_custo_estoque());
        this.setCusto_data_inicial(produto.getCusto_data_inicial());
        this.setCusto_valor_inicial(produto.getCusto_valor_inicial());
        this.setProduto_venda(produto.getProduto_venda());
        this.setProduto_insumo(produto.getProduto_insumo());
        this.setProduto_consumo(produto.getProduto_consumo());
        this.setProduto_producao(produto.getProduto_producao());
        this.setVenda_perc_comissao(produto.getVenda_perc_comissao());
        this.setVenda_preco(produto.getVenda_preco());
        this.setVenda_perc_comissao_dois(produto.getVenda_perc_comissao_dois());
        this.setDescricao(produto.getDescricao());
        this.setProduto_tercerizacao(produto.getProduto_tercerizacao());
        this.setProduto_materia_prima(produto.getProduto_materia_prima());
    }


    public WebPedidoItens() {
    }

    public void setProduto(Produto produto) {
        this.setAtivo(produto.getAtivo());
        this.setId_produto(produto.getId_produto());
        this.setNome_produto(produto.getNome_produto());
        this.setUnidade(produto.getUnidade());
        this.setTipo_cadastro(produto.getTipo_cadastro());
        this.setId_entidade(produto.getId_entidade());
        this.setNcm(produto.getNcm());
        this.setId_grupo(produto.getId_grupo());
        this.setId_sub_grupo(produto.getId_sub_grupo());
        this.setPeso_bruto(produto.getPeso_bruto());
        this.setPeso_liquido(produto.getPeso_liquido());
        this.setCodigo_em_barras(produto.getCodigo_em_barras());
        this.setMovimenta_estoque(produto.getMovimenta_estoque());
        this.setNome_da_marca(produto.getNome_da_marca());
        this.setId_origem(produto.getId_origem());
        this.setCusto_produto(produto.getCusto_produto());
        this.setCusto_per_ipi(produto.getCusto_per_ipi());
        this.setCusto_ipi(produto.getCusto_ipi());
        this.setCusto_per_frete(produto.getCusto_per_frete());
        this.setCusto_frete(produto.getCusto_frete());
        this.setCusto_per_icms(produto.getCusto_per_icms());
        this.setCusto_icms(produto.getCusto_icms());
        this.setCusto_per_fin(produto.getCusto_per_fin());
        this.setCusto_fin(produto.getCusto_fin());
        this.setCusto_per_subst(produto.getCusto_per_subst());
        this.setCusto_subt(produto.getCusto_subt());
        this.setCusto_per_outros(produto.getCusto_per_outros());
        this.setCusto_outros(produto.getCusto_outros());
        this.setValor_custo(produto.getValor_custo());
        this.setExcluido(produto.getExcluido());
        this.setExcluido_por(produto.getExcluido_por());
        this.setExcluido_por_data(produto.getExcluido_por_data());
        this.setExcluido_codigo_novo(produto.getExcluido_codigo_novo());
        this.setAjuste_preco_data(produto.getAjuste_preco_data());
        this.setAjuste_preco_nfe(produto.getAjuste_preco_nfe());
        this.setAjuste_preco_usuario(produto.getAjuste_preco_usuario());
        this.setTotal_custo(produto.getTotal_custo());
        this.setTotal_credito(produto.getTotal_credito());
        this.setValor_custo_estoque(produto.getValor_custo_estoque());
        this.setCusto_data_inicial(produto.getCusto_data_inicial());
        this.setCusto_valor_inicial(produto.getCusto_valor_inicial());
        this.setProduto_venda(produto.getProduto_venda());
        this.setProduto_insumo(produto.getProduto_insumo());
        this.setProduto_consumo(produto.getProduto_consumo());
        this.setProduto_producao(produto.getProduto_producao());
        this.setVenda_perc_comissao(produto.getVenda_perc_comissao());
        this.setVenda_preco(produto.getVenda_preco());
        this.setVenda_perc_comissao_dois(produto.getVenda_perc_comissao_dois());
        this.setDescricao(produto.getDescricao());
    }

    public String getId_web_item() {
        return id_web_item;
    }

    public void setId_web_item(String id_web_item) {
        this.id_web_item = id_web_item;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public Float getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(Float valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public String getValor_bruto() {
        return valor_bruto;
    }

    public void setValor_bruto(String valor_bruto) {
        this.valor_bruto = valor_bruto;
    }

    public String getValor_desconto_per() {
        return valor_desconto_per;
    }

    public void setValor_desconto_per(String valor_desconto_per) {
        this.valor_desconto_per = valor_desconto_per;
    }

    public String getValor_desconto_real() {
        return valor_desconto_real;
    }

    public void setValor_desconto_real(String valor_desconto_real) {
        this.valor_desconto_real = valor_desconto_real;
    }

    public String getValor_desconto_per_add() {
        return valor_desconto_per_add;
    }

    public void setValor_desconto_per_add(String valor_desconto_per_add) {
        this.valor_desconto_per_add = valor_desconto_per_add;
    }

    public String getValor_desconto_real_add() {
        return valor_desconto_real_add;
    }

    public void setValor_desconto_real_add(String valor_desconto_real_add) {
        this.valor_desconto_real_add = valor_desconto_real_add;
    }

    public String getValor_total() {
        return valor_total;
    }

    public void setValor_total(String valor_total) {
        this.valor_total = valor_total;
    }

    public String getData_movimentacao() {
        return data_movimentacao;
    }

    public void setData_movimentacao(String data_movimentacao) {
        this.data_movimentacao = data_movimentacao;
    }

    public String getUsuario_lancamento_id() {
        return usuario_lancamento_id;
    }

    public void setUsuario_lancamento_id(String usuario_lancamento_id) {
        this.usuario_lancamento_id = usuario_lancamento_id;
    }

    public String getUsuario_lancamento_data() {
        return usuario_lancamento_data;
    }

    public void setUsuario_lancamento_data(String usuario_lancamento_data) {
        this.usuario_lancamento_data = usuario_lancamento_data;
    }

    public String getId_item_desconto() {
        return id_item_desconto;
    }

    public void setId_item_desconto(String id_item_desconto) {
        this.id_item_desconto = id_item_desconto;
    }

    public String getPontos_unitario() {
        return pontos_unitario;
    }

    public void setPontos_unitario(String pontos_unitario) {
        this.pontos_unitario = pontos_unitario;
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

    public String getId_tabela_preco() {
        return id_tabela_preco;
    }

    public void setId_tabela_preco(String id_tabela_preco) {
        this.id_tabela_preco = id_tabela_preco;
    }

    public String getValor_desconto_per_orig() {
        return valor_desconto_per_orig;
    }

    public void setValor_desconto_per_orig(String valor_desconto_per_orig) {
        this.valor_desconto_per_orig = valor_desconto_per_orig;
    }

    public String getValor_desconto_real_orig() {
        return valor_desconto_real_orig;
    }

    public void setValor_desconto_real_orig(String valor_desconto_real_orig) {
        this.valor_desconto_real_orig = valor_desconto_real_orig;
    }

    public String getValor_desconto_per_add_orig() {
        return valor_desconto_per_add_orig;
    }

    public void setValor_desconto_per_add_orig(String valor_desconto_per_add_orig) {
        this.valor_desconto_per_add_orig = valor_desconto_per_add_orig;
    }

    public String getValor_desconto_real_add_orig() {
        return valor_desconto_real_add_orig;
    }

    public void setValor_desconto_real_add_orig(String valor_desconto_real_add_orig) {
        this.valor_desconto_real_add_orig = valor_desconto_real_add_orig;
    }

    public String getId_tabela_preco_faixa_orig() {
        return id_tabela_preco_faixa_orig;
    }

    public void setId_tabela_preco_faixa_orig(String id_tabela_preco_faixa_orig) {
        this.id_tabela_preco_faixa_orig = id_tabela_preco_faixa_orig;
    }

    public String getValor_total_orig() {
        return valor_total_orig;
    }

    public void setValor_total_orig(String valor_total_orig) {
        this.valor_total_orig = valor_total_orig;
    }

    public String getPontos_unitario_orig() {
        return pontos_unitario_orig;
    }

    public void setPontos_unitario_orig(String pontos_unitario_orig) {
        this.pontos_unitario_orig = pontos_unitario_orig;
    }

    public String getPontos_coeficiente_orig() {
        return pontos_coeficiente_orig;
    }

    public void setPontos_coeficiente_orig(String pontos_coeficiente_orig) {
        this.pontos_coeficiente_orig = pontos_coeficiente_orig;
    }

    public String getComissao_percentual_orig() {
        return comissao_percentual_orig;
    }

    public void setComissao_percentual_orig(String comissao_percentual_orig) {
        this.comissao_percentual_orig = comissao_percentual_orig;
    }

    public String getValor_bonus_credor_orig() {
        return valor_bonus_credor_orig;
    }

    public void setValor_bonus_credor_orig(String valor_bonus_credor_orig) {
        this.valor_bonus_credor_orig = valor_bonus_credor_orig;
    }

    public String getPerc_bonus_credor_orig() {
        return perc_bonus_credor_orig;
    }

    public void setPerc_bonus_credor_orig(String perc_bonus_credor_orig) {
        this.perc_bonus_credor_orig = perc_bonus_credor_orig;
    }

    public String getComissao_valor_orig() {
        return comissao_valor_orig;
    }

    public void setComissao_valor_orig(String comissao_valor_orig) {
        this.comissao_valor_orig = comissao_valor_orig;
    }

    public String getPontos_total_orig() {
        return pontos_total_orig;
    }

    public void setPontos_total_orig(String pontos_total_orig) {
        this.pontos_total_orig = pontos_total_orig;
    }

    public String getPontos_cor_orig() {
        return pontos_cor_orig;
    }

    public void setPontos_cor_orig(String pontos_cor_orig) {
        this.pontos_cor_orig = pontos_cor_orig;
    }

    public String getValor_preco_pago() {
        return valor_preco_pago;
    }

    public void setValor_preco_pago(String valor_preco_pago) {
        this.valor_preco_pago = valor_preco_pago;
    }

    public String getId_web_item_servidor() {
        return id_web_item_servidor;
    }

    public void setId_web_item_servidor(String id_web_item_servidor) {
        this.id_web_item_servidor = id_web_item_servidor;
    }

    public String getTipoDesconto() {
        return tipoDesconto;
    }

    public void setTipoDesconto(String tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }

    public Boolean getDescontoIndevido() {
        return descontoIndevido;
    }

    public void setDescontoIndevido(Boolean descontoIndevido) {
        this.descontoIndevido = descontoIndevido;
    }

    public Boolean getProdutoBase() {
        return produtoBase;
    }

    public void setProdutoBase(Boolean produtoBase) {
        this.produtoBase = produtoBase;
    }
}
