package com.example.rcksuporte05.rcksistemas.model;

public class Produto {
    private String ativo;
    private String id_produto;
    private String nome_produto;
    private String unidade;
    private String tipo_cadastro;
    private String id_entidade;
    private String ncm;
    private String id_grupo;
    private String id_sub_grupo;
    private String peso_bruto;
    private String peso_liquido;
    private String codigo_em_barras;
    private String movimenta_estoque;
    private String nome_da_marca;
    private String id_empresa;
    private String id_origem;
    private String custo_produto;
    private String custo_per_ipi;
    private String custo_ipi;
    private String custo_per_frete;
    private String custo_frete;
    private String custo_per_icms;
    private String custo_icms;
    private String custo_per_fin;
    private String custo_fin;
    private String custo_per_subst;
    private String custo_subt;
    private String custo_per_outros;
    private String custo_outros;
    private String valor_custo;
    private String excluido;
    private String excluido_por;
    private String excluido_por_data;
    private String excluido_codigo_novo;
    private String ajuste_preco_data;
    private String ajuste_preco_nfe;
    private String ajuste_preco_usuario;
    private String total_custo;
    private String total_credito;
    private String valor_custo_estoque;
    private String custo_data_inicial;
    private String custo_valor_inicial;
    private String produto_venda;
    private String produto_insumo;
    private String produto_consumo;
    private String produto_producao;
    private String venda_perc_comissao;
    private String venda_preco;
    private String venda_perc_comissao_dois;
    private String descricao;
    private String nome_sub_grupo;
    private String produto_materia_prima;
    private String produto_tercerizacao;
    private Float saldo_estoque;
    private int idLinhaColecao;

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAtivo() {
        return this.ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getId_produto() {
        return this.id_produto;
    }

    public void setId_produto(String id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return this.nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getUnidade() {
        return this.unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getTipo_cadastro() {
        return this.tipo_cadastro;
    }

    public void setTipo_cadastro(String tipo_cadastro) {
        this.tipo_cadastro = tipo_cadastro;
    }

    public String getId_entidade() {
        return this.id_entidade;
    }

    public void setId_entidade(String id_entidade) {
        this.id_entidade = id_entidade;
    }

    public String getNcm() {
        return this.ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getId_grupo() {
        return this.id_grupo;
    }

    public void setId_grupo(String id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getId_sub_grupo() {
        return this.id_sub_grupo;
    }

    public void setId_sub_grupo(String id_sub_grupo) {
        this.id_sub_grupo = id_sub_grupo;
    }

    public String getPeso_bruto() {
        return this.peso_bruto;
    }

    public void setPeso_bruto(String peso_bruto) {
        this.peso_bruto = peso_bruto;
    }

    public String getPeso_liquido() {
        return this.peso_liquido;
    }

    public void setPeso_liquido(String peso_liquido) {
        this.peso_liquido = peso_liquido;
    }

    public String getCodigo_em_barras() {
        return this.codigo_em_barras;
    }

    public void setCodigo_em_barras(String codigo_em_barras) {
        this.codigo_em_barras = codigo_em_barras;
    }

    public String getMovimenta_estoque() {
        return this.movimenta_estoque;
    }

    public void setMovimenta_estoque(String movimenta_estoque) {
        this.movimenta_estoque = movimenta_estoque;
    }

    public String getNome_da_marca() {
        return this.nome_da_marca;
    }

    public void setNome_da_marca(String nome_da_marca) {
        this.nome_da_marca = nome_da_marca;
    }

    public String getId_empresa() {
        return this.id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getId_origem() {
        return this.id_origem;
    }

    public void setId_origem(String id_origem) {
        this.id_origem = id_origem;
    }

    public String getCusto_produto() {
        return this.custo_produto;
    }

    public void setCusto_produto(String custo_produto) {
        this.custo_produto = custo_produto;
    }

    public String getCusto_per_ipi() {
        return this.custo_per_ipi;
    }

    public void setCusto_per_ipi(String custo_per_ipi) {
        this.custo_per_ipi = custo_per_ipi;
    }

    public String getCusto_ipi() {
        return this.custo_ipi;
    }

    public void setCusto_ipi(String custo_ipi) {
        this.custo_ipi = custo_ipi;
    }

    public String getCusto_per_frete() {
        return this.custo_per_frete;
    }

    public void setCusto_per_frete(String custo_per_frete) {
        this.custo_per_frete = custo_per_frete;
    }

    public String getCusto_frete() {
        return this.custo_frete;
    }

    public void setCusto_frete(String custo_frete) {
        this.custo_frete = custo_frete;
    }

    public String getCusto_per_icms() {
        return this.custo_per_icms;
    }

    public void setCusto_per_icms(String custo_per_icms) {
        this.custo_per_icms = custo_per_icms;
    }

    public String getCusto_icms() {
        return this.custo_icms;
    }

    public void setCusto_icms(String custo_icms) {
        this.custo_icms = custo_icms;
    }

    public String getCusto_per_fin() {
        return this.custo_per_fin;
    }

    public void setCusto_per_fin(String custo_per_fin) {
        this.custo_per_fin = custo_per_fin;
    }

    public String getCusto_fin() {
        return this.custo_fin;
    }

    public void setCusto_fin(String custo_fin) {
        this.custo_fin = custo_fin;
    }

    public String getCusto_per_subst() {
        return this.custo_per_subst;
    }

    public void setCusto_per_subst(String custo_per_subst) {
        this.custo_per_subst = custo_per_subst;
    }

    public String getCusto_subt() {
        return this.custo_subt;
    }

    public void setCusto_subt(String custo_subt) {
        this.custo_subt = custo_subt;
    }

    public String getCusto_per_outros() {
        return this.custo_per_outros;
    }

    public void setCusto_per_outros(String custo_per_outros) {
        this.custo_per_outros = custo_per_outros;
    }

    public String getCusto_outros() {
        return this.custo_outros;
    }

    public void setCusto_outros(String custo_outros) {
        this.custo_outros = custo_outros;
    }

    public String getValor_custo() {
        return this.valor_custo;
    }

    public void setValor_custo(String valor_custo) {
        this.valor_custo = valor_custo;
    }

    public String getExcluido() {
        return this.excluido;
    }

    public void setExcluido(String excluido) {
        this.excluido = excluido;
    }

    public String getExcluido_por() {
        return this.excluido_por;
    }

    public void setExcluido_por(String excluido_por) {
        this.excluido_por = excluido_por;
    }

    public String getExcluido_por_data() {
        return this.excluido_por_data;
    }

    public void setExcluido_por_data(String excluido_por_data) {
        this.excluido_por_data = excluido_por_data;
    }

    public String getExcluido_codigo_novo() {
        return this.excluido_codigo_novo;
    }

    public void setExcluido_codigo_novo(String excluido_codigo_novo) {
        this.excluido_codigo_novo = excluido_codigo_novo;
    }

    public String getAjuste_preco_data() {
        return this.ajuste_preco_data;
    }

    public void setAjuste_preco_data(String ajuste_preco_data) {
        this.ajuste_preco_data = ajuste_preco_data;
    }

    public String getAjuste_preco_nfe() {
        return this.ajuste_preco_nfe;
    }

    public void setAjuste_preco_nfe(String ajuste_preco_nfe) {
        this.ajuste_preco_nfe = ajuste_preco_nfe;
    }

    public String getAjuste_preco_usuario() {
        return this.ajuste_preco_usuario;
    }

    public void setAjuste_preco_usuario(String ajuste_preco_usuario) {
        this.ajuste_preco_usuario = ajuste_preco_usuario;
    }

    public String getTotal_custo() {
        return this.total_custo;
    }

    public void setTotal_custo(String total_custo) {
        this.total_custo = total_custo;
    }

    public String getTotal_credito() {
        return this.total_credito;
    }

    public void setTotal_credito(String total_credito) {
        this.total_credito = total_credito;
    }

    public String getValor_custo_estoque() {
        return this.valor_custo_estoque;
    }

    public void setValor_custo_estoque(String valor_custo_estoque) {
        this.valor_custo_estoque = valor_custo_estoque;
    }

    public String getCusto_data_inicial() {
        return this.custo_data_inicial;
    }

    public void setCusto_data_inicial(String custo_data_inicial) {
        this.custo_data_inicial = custo_data_inicial;
    }

    public String getCusto_valor_inicial() {
        return this.custo_valor_inicial;
    }

    public void setCusto_valor_inicial(String custo_valor_inicial) {
        this.custo_valor_inicial = custo_valor_inicial;
    }

    public String getProduto_venda() {
        return this.produto_venda;
    }

    public void setProduto_venda(String produto_venda) {
        this.produto_venda = produto_venda;
    }

    public String getProduto_insumo() {
        return this.produto_insumo;
    }

    public void setProduto_insumo(String produto_insumo) {
        this.produto_insumo = produto_insumo;
    }

    public String getProduto_consumo() {
        return this.produto_consumo;
    }

    public void setProduto_consumo(String produto_consumo) {
        this.produto_consumo = produto_consumo;
    }

    public String getProduto_producao() {
        return this.produto_producao;
    }

    public void setProduto_producao(String produto_producao) {
        this.produto_producao = produto_producao;
    }

    public String getVenda_perc_comissao() {
        return this.venda_perc_comissao;
    }

    public void setVenda_perc_comissao(String venda_perc_comissao) {
        this.venda_perc_comissao = venda_perc_comissao;
    }

    public String getVenda_preco() {
        return this.venda_preco;
    }

    public void setVenda_preco(String venda_preco) {
        this.venda_preco = venda_preco;
    }

    public String getVenda_perc_comissao_dois() {
        return this.venda_perc_comissao_dois;
    }

    public void setVenda_perc_comissao_dois(String venda_perc_comissao_dois) {
        this.venda_perc_comissao_dois = venda_perc_comissao_dois;
    }

    public String getNome_sub_grupo() {
        return nome_sub_grupo;
    }

    public void setNome_sub_grupo(String nome_sub_grupo) {
        this.nome_sub_grupo = nome_sub_grupo;
    }

    public String getProduto_materia_prima() {
        return produto_materia_prima;
    }

    public void setProduto_materia_prima(String produto_materia_prima) {
        this.produto_materia_prima = produto_materia_prima;
    }

    public String getProduto_tercerizacao() {
        return produto_tercerizacao;
    }

    public void setProduto_tercerizacao(String produto_tercerizacao) {
        this.produto_tercerizacao = produto_tercerizacao;
    }

    public Float getSaldo_estoque() {
        return saldo_estoque;
    }

    public void setSaldo_estoque(Float saldo_estoque) {
        this.saldo_estoque = saldo_estoque;
    }

    public int getIdLinhaColecao() {
        return idLinhaColecao;
    }

    public void setIdLinhaColecao(int idLinhaColecao) {
        this.idLinhaColecao = idLinhaColecao;
    }
}
