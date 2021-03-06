package com.example.rcksuporte05.rcksistemas.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String ativo;
    private int id_empresa;
    private int id_cadastro;
    private int id_cadastro_servidor;
    private String pessoa_f_j;
    private String data_aniversario;
    private String nome_cadastro;
    private String nome_fantasia;
    private String cpf_cnpj;
    private String inscri_estadual;
    private String endereco;
    private String endereco_bairro;
    private String endereco_numero;
    private String endereco_complemento;
    private String endereco_uf;
    private String nome_municipio;
    private String endereco_cep;
    private int usuario_id;
    private String usuario_nome;
    private String usuario_data;
    private String f_cliente;
    private String f_fornecedor;
    private String f_funcionario;
    private String f_vendedor;
    private String f_transportador;
    private String data_ultima_compra;
    private String nome_vendedor;
    private String f_id_cliente;
    private String id_entidade;
    private String f_id_fornecedor;
    private String f_id_vendedor;
    private String f_id_transportador;
    private String telefone_principal;
    private String email_principal;
    private int id_pais;
    private String f_id_funcionario;
    private String avisar_com_dias;
    private String observacoes;
    private String padrao_id_c_custo;
    private String padrao_id_c_gerenciadora;
    private String padrao_id_c_analitica;
    private String cob_endereco;
    private String cob_endereco_bairro;
    private String cob_endereco_numero;
    private String cob_endereco_complemento;
    private String cob_endereco_uf;
    private String nome_cob_municipio;
    private String cob_endereco_cep;
    private int cob_endereco_id_pais;
    private String limite_credito;
    private String limite_disponivel;
    private String pessoa_contato_financeiro;
    private String email_financeiro;
    private String observacoes_faturamento;
    private String observacoes_financeiro;
    private String telefone_dois;
    private String telefone_tres;
    private String pessoa_contato_principal;
    private String ind_da_ie_destinatario;
    private String comissao_percentual;
    private String id_setor;
    private String nfe_email_enviar;
    private String nfe_email_um;
    private String nfe_email_dois;
    private String nfe_email_tres;
    private String nfe_email_quatro;
    private String nfe_email_cinco;
    private String id_grupo_vendedor;
    private String vendedor_usa_portal;
    private String vendedor_id_user_portal;
    private String f_tarifa;
    private String f_id_tarifa;
    private String f_produtor;
    private String rg_numero;
    private String rg_ssp;
    private String conta_contabil;
    private String motorista;
    private String f_id_motorista;
    private String habilitacao_numero;
    private String habilitacao_categoria;
    private String habilitacao_vencimento;
    private String mot_id_transportadora;
    private String local_cadastro;
    private int idCategoria;
    private int id_vendedor;
    private int id_prospect;
    private String alterado;
    private List<CadastroAnexo> listaCadastroAnexo = new ArrayList<>();
    private String situacaoPredio;
    private String finalizado;

    public Cliente(Prospect prospect) {
        this.id_vendedor = prospect.getIdVendedor();
        this.idCategoria = prospect.getIdCategoria();
        try {
            this.id_prospect = Integer.parseInt(prospect.getId_prospect());
        } catch (NumberFormatException e) {
            this.id_prospect = 0;
        }
        this.nome_cadastro = prospect.getNome_cadastro();
        this.nome_fantasia = prospect.getNome_fantasia();
        this.pessoa_f_j = prospect.getPessoa_f_j();
        this.cpf_cnpj = prospect.getCpf_cnpj();
        this.inscri_estadual = prospect.getInscri_estadual();
        this.endereco = prospect.getEndereco();
        this.endereco_bairro = prospect.getEndereco_bairro();
        this.endereco_numero = prospect.getEndereco_numero();
        this.endereco_complemento = prospect.getEndereco_complemento();
        this.endereco_uf = prospect.getEndereco_uf();
        this.nome_municipio = prospect.getNome_municipio();
        this.endereco_cep = prospect.getEndereco_cep();
        try {
            this.id_pais = Integer.parseInt(prospect.getId_pais());
        } catch (NumberFormatException e) {
            this.id_pais = 0;
        }
        try {
            this.usuario_id = Integer.parseInt(prospect.getUsuario_id());
        } catch (NumberFormatException e) {
            this.usuario_id = 0;
        }
        this.usuario_data = prospect.getUsuario_data();
        this.situacaoPredio = prospect.getSituacaoPredio();
        this.limite_credito = prospect.getLimiteDeCreditoSugerido();
        try {
            this.id_empresa = Integer.parseInt(prospect.getIdEmpresa());
        } catch (NumberFormatException e) {
            this.id_empresa = 0;
        }
        this.observacoes_faturamento = prospect.getObservacoesComerciais();
        this.ind_da_ie_destinatario = prospect.getInd_da_ie_destinatario_prospect();
        this.usuario_nome = prospect.getUsuario_nome();

        final List<CadastroAnexo> listaCadastroAnexo = new ArrayList<>();
        if (prospect.getFotoPrincipalBase64() != null) {
            prospect.getFotoPrincipalBase64().setIdEntidade(1);
            prospect.getFotoPrincipalBase64().setNomeAnexo("Imagem1");
            listaCadastroAnexo.add(prospect.getFotoPrincipalBase64());
        }

        if (prospect.getFotoSecundariaBase64() != null) {
            prospect.getFotoSecundariaBase64().setIdEntidade(1);
            prospect.getFotoSecundariaBase64().setNomeAnexo("Imagem2");
            listaCadastroAnexo.add(prospect.getFotoSecundariaBase64());
        }

        if (listaCadastroAnexo.size() > 0)
            this.setListaCadastroAnexo(listaCadastroAnexo);
    }

    public Cliente() {
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_cadastro() {
        return id_cadastro;
    }

    public void setId_cadastro(int id_cadastro) {
        this.id_cadastro = id_cadastro;
    }

    public int getId_cadastro_servidor() {
        return id_cadastro_servidor;
    }

    public void setId_cadastro_servidor(int id_cadastro_servidor) {
        this.id_cadastro_servidor = id_cadastro_servidor;
    }

    public String getPessoa_f_j() {
        return pessoa_f_j;
    }

    public void setPessoa_f_j(String pessoa_f_j) {
        this.pessoa_f_j = pessoa_f_j;
    }

    public String getData_aniversario() {
        return data_aniversario;
    }

    public void setData_aniversario(String data_aniversario) {
        this.data_aniversario = data_aniversario;
    }

    public String getNome_cadastro() {
        return nome_cadastro;
    }

    public void setNome_cadastro(String nome_cadastro) {
        this.nome_cadastro = nome_cadastro;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getInscri_estadual() {
        return inscri_estadual;
    }

    public void setInscri_estadual(String inscri_estadual) {
        this.inscri_estadual = inscri_estadual;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEndereco_bairro() {
        return endereco_bairro;
    }

    public void setEndereco_bairro(String endereco_bairro) {
        this.endereco_bairro = endereco_bairro;
    }

    public String getEndereco_numero() {
        return endereco_numero;
    }

    public void setEndereco_numero(String endereco_numero) {
        this.endereco_numero = endereco_numero;
    }

    public String getEndereco_complemento() {
        return endereco_complemento;
    }

    public void setEndereco_complemento(String endereco_complemento) {
        this.endereco_complemento = endereco_complemento;
    }

    public String getEndereco_uf() {
        return endereco_uf;
    }

    public void setEndereco_uf(String endereco_uf) {
        this.endereco_uf = endereco_uf;
    }

    public String getNome_municipio() {
        return nome_municipio;
    }

    public void setNome_municipio(String nome_municipio) {
        this.nome_municipio = nome_municipio;
    }

    public String getEndereco_cep() {
        return endereco_cep;
    }

    public void setEndereco_cep(String endereco_cep) {
        this.endereco_cep = endereco_cep;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsuario_nome() {
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getUsuario_data() {
        return usuario_data;
    }

    public void setUsuario_data(String usuario_data) {
        this.usuario_data = usuario_data;
    }

    public String getF_cliente() {
        return f_cliente;
    }

    public void setF_cliente(String f_cliente) {
        this.f_cliente = f_cliente;
    }

    public String getF_fornecedor() {
        return f_fornecedor;
    }

    public void setF_fornecedor(String f_fornecedor) {
        this.f_fornecedor = f_fornecedor;
    }

    public String getF_funcionario() {
        return f_funcionario;
    }

    public void setF_funcionario(String f_funcionario) {
        this.f_funcionario = f_funcionario;
    }

    public String getF_vendedor() {
        return f_vendedor;
    }

    public void setF_vendedor(String f_vendedor) {
        this.f_vendedor = f_vendedor;
    }

    public String getF_transportador() {
        return f_transportador;
    }

    public void setF_transportador(String f_transportador) {
        this.f_transportador = f_transportador;
    }

    public String getData_ultima_compra() {
        return data_ultima_compra;
    }

    public void setData_ultima_compra(String data_ultima_compra) {
        this.data_ultima_compra = data_ultima_compra;
    }

    public String getNome_vendedor() {
        return nome_vendedor;
    }

    public void setNome_vendedor(String nome_vendedor) {
        this.nome_vendedor = nome_vendedor;
    }

    public String getF_id_cliente() {
        return f_id_cliente;
    }

    public void setF_id_cliente(String f_id_cliente) {
        this.f_id_cliente = f_id_cliente;
    }

    public String getId_entidade() {
        return id_entidade;
    }

    public void setId_entidade(String id_entidade) {
        this.id_entidade = id_entidade;
    }

    public String getF_id_fornecedor() {
        return f_id_fornecedor;
    }

    public void setF_id_fornecedor(String f_id_fornecedor) {
        this.f_id_fornecedor = f_id_fornecedor;
    }

    public String getF_id_vendedor() {
        return f_id_vendedor;
    }

    public void setF_id_vendedor(String f_id_vendedor) {
        this.f_id_vendedor = f_id_vendedor;
    }

    public String getF_id_transportador() {
        return f_id_transportador;
    }

    public void setF_id_transportador(String f_id_transportador) {
        this.f_id_transportador = f_id_transportador;
    }

    public String getTelefone_principal() {
        return telefone_principal;
    }

    public void setTelefone_principal(String telefone_principal) {
        this.telefone_principal = telefone_principal;
    }

    public String getEmail_principal() {
        return email_principal;
    }

    public void setEmail_principal(String email_principal) {
        this.email_principal = email_principal;
    }

    public int getId_pais() {
        return id_pais;
    }

    public void setId_pais(int id_pais) {
        this.id_pais = id_pais;
    }

    public String getF_id_funcionario() {
        return f_id_funcionario;
    }

    public void setF_id_funcionario(String f_id_funcionario) {
        this.f_id_funcionario = f_id_funcionario;
    }

    public String getAvisar_com_dias() {
        return avisar_com_dias;
    }

    public void setAvisar_com_dias(String avisar_com_dias) {
        this.avisar_com_dias = avisar_com_dias;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getPadrao_id_c_custo() {
        return padrao_id_c_custo;
    }

    public void setPadrao_id_c_custo(String padrao_id_c_custo) {
        this.padrao_id_c_custo = padrao_id_c_custo;
    }

    public String getPadrao_id_c_gerenciadora() {
        return padrao_id_c_gerenciadora;
    }

    public void setPadrao_id_c_gerenciadora(String padrao_id_c_gerenciadora) {
        this.padrao_id_c_gerenciadora = padrao_id_c_gerenciadora;
    }

    public String getPadrao_id_c_analitica() {
        return padrao_id_c_analitica;
    }

    public void setPadrao_id_c_analitica(String padrao_id_c_analitica) {
        this.padrao_id_c_analitica = padrao_id_c_analitica;
    }

    public String getCob_endereco() {
        return cob_endereco;
    }

    public void setCob_endereco(String cob_endereco) {
        this.cob_endereco = cob_endereco;
    }

    public String getCob_endereco_bairro() {
        return cob_endereco_bairro;
    }

    public void setCob_endereco_bairro(String cob_endereco_bairro) {
        this.cob_endereco_bairro = cob_endereco_bairro;
    }

    public String getCob_endereco_numero() {
        return cob_endereco_numero;
    }

    public void setCob_endereco_numero(String cob_endereco_numero) {
        this.cob_endereco_numero = cob_endereco_numero;
    }

    public String getCob_endereco_complemento() {
        return cob_endereco_complemento;
    }

    public void setCob_endereco_complemento(String cob_endereco_complemento) {
        this.cob_endereco_complemento = cob_endereco_complemento;
    }

    public String getCob_endereco_uf() {
        return cob_endereco_uf;
    }

    public void setCob_endereco_uf(String cob_endereco_uf) {
        this.cob_endereco_uf = cob_endereco_uf;
    }

    public String getNome_cob_municipio() {
        return nome_cob_municipio;
    }

    public void setNome_cob_municipio(String nome_cob_municipio) {
        this.nome_cob_municipio = nome_cob_municipio;
    }

    public String getCob_endereco_cep() {
        return cob_endereco_cep;
    }

    public void setCob_endereco_cep(String cob_endereco_cep) {
        this.cob_endereco_cep = cob_endereco_cep;
    }

    public int getCob_endereco_id_pais() {
        return cob_endereco_id_pais;
    }

    public void setCob_endereco_id_pais(int cob_endereco_id_pais) {
        this.cob_endereco_id_pais = cob_endereco_id_pais;
    }

    public String getLimite_credito() {
        return limite_credito;
    }

    public void setLimite_credito(String limite_credito) {
        this.limite_credito = limite_credito;
    }

    public String getLimite_disponivel() {
        return limite_disponivel;
    }

    public void setLimite_disponivel(String limite_disponivel) {
        this.limite_disponivel = limite_disponivel;
    }

    public String getPessoa_contato_financeiro() {
        return pessoa_contato_financeiro;
    }

    public void setPessoa_contato_financeiro(String pessoa_contato_financeiro) {
        this.pessoa_contato_financeiro = pessoa_contato_financeiro;
    }

    public String getEmail_financeiro() {
        return email_financeiro;
    }

    public void setEmail_financeiro(String email_financeiro) {
        this.email_financeiro = email_financeiro;
    }

    public String getObservacoes_faturamento() {
        return observacoes_faturamento;
    }

    public void setObservacoes_faturamento(String observacoes_faturamento) {
        this.observacoes_faturamento = observacoes_faturamento;
    }

    public String getObservacoes_financeiro() {
        return observacoes_financeiro;
    }

    public void setObservacoes_financeiro(String observacoes_financeiro) {
        this.observacoes_financeiro = observacoes_financeiro;
    }

    public String getTelefone_dois() {
        return telefone_dois;
    }

    public void setTelefone_dois(String telefone_dois) {
        this.telefone_dois = telefone_dois;
    }

    public String getTelefone_tres() {
        return telefone_tres;
    }

    public void setTelefone_tres(String telefone_tres) {
        this.telefone_tres = telefone_tres;
    }

    public String getPessoa_contato_principal() {
        return pessoa_contato_principal;
    }

    public void setPessoa_contato_principal(String pessoa_contato_principal) {
        this.pessoa_contato_principal = pessoa_contato_principal;
    }

    public String getInd_da_ie_destinatario() {
        return ind_da_ie_destinatario;
    }

    public void setInd_da_ie_destinatario(String ind_da_ie_destinatario) {
        this.ind_da_ie_destinatario = ind_da_ie_destinatario;
    }

    public String getComissao_percentual() {
        return comissao_percentual;
    }

    public void setComissao_percentual(String comissao_percentual) {
        this.comissao_percentual = comissao_percentual;
    }

    public String getId_setor() {
        return id_setor;
    }

    public void setId_setor(String id_setor) {
        this.id_setor = id_setor;
    }

    public String getNfe_email_enviar() {
        return nfe_email_enviar;
    }

    public void setNfe_email_enviar(String nfe_email_enviar) {
        this.nfe_email_enviar = nfe_email_enviar;
    }

    public String getNfe_email_um() {
        return nfe_email_um;
    }

    public void setNfe_email_um(String nfe_email_um) {
        this.nfe_email_um = nfe_email_um;
    }

    public String getNfe_email_dois() {
        return nfe_email_dois;
    }

    public void setNfe_email_dois(String nfe_email_dois) {
        this.nfe_email_dois = nfe_email_dois;
    }

    public String getNfe_email_tres() {
        return nfe_email_tres;
    }

    public void setNfe_email_tres(String nfe_email_tres) {
        this.nfe_email_tres = nfe_email_tres;
    }

    public String getNfe_email_quatro() {
        return nfe_email_quatro;
    }

    public void setNfe_email_quatro(String nfe_email_quatro) {
        this.nfe_email_quatro = nfe_email_quatro;
    }

    public String getNfe_email_cinco() {
        return nfe_email_cinco;
    }

    public void setNfe_email_cinco(String nfe_email_cinco) {
        this.nfe_email_cinco = nfe_email_cinco;
    }

    public String getId_grupo_vendedor() {
        return id_grupo_vendedor;
    }

    public void setId_grupo_vendedor(String id_grupo_vendedor) {
        this.id_grupo_vendedor = id_grupo_vendedor;
    }

    public String getVendedor_usa_portal() {
        return vendedor_usa_portal;
    }

    public void setVendedor_usa_portal(String vendedor_usa_portal) {
        this.vendedor_usa_portal = vendedor_usa_portal;
    }

    public String getVendedor_id_user_portal() {
        return vendedor_id_user_portal;
    }

    public void setVendedor_id_user_portal(String vendedor_id_user_portal) {
        this.vendedor_id_user_portal = vendedor_id_user_portal;
    }

    public String getF_tarifa() {
        return f_tarifa;
    }

    public void setF_tarifa(String f_tarifa) {
        this.f_tarifa = f_tarifa;
    }

    public String getF_id_tarifa() {
        return f_id_tarifa;
    }

    public void setF_id_tarifa(String f_id_tarifa) {
        this.f_id_tarifa = f_id_tarifa;
    }

    public String getF_produtor() {
        return f_produtor;
    }

    public void setF_produtor(String f_produtor) {
        this.f_produtor = f_produtor;
    }

    public String getRg_numero() {
        return rg_numero;
    }

    public void setRg_numero(String rg_numero) {
        this.rg_numero = rg_numero;
    }

    public String getRg_ssp() {
        return rg_ssp;
    }

    public void setRg_ssp(String rg_ssp) {
        this.rg_ssp = rg_ssp;
    }

    public String getConta_contabil() {
        return conta_contabil;
    }

    public void setConta_contabil(String conta_contabil) {
        this.conta_contabil = conta_contabil;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getF_id_motorista() {
        return f_id_motorista;
    }

    public void setF_id_motorista(String f_id_motorista) {
        this.f_id_motorista = f_id_motorista;
    }

    public String getHabilitacao_numero() {
        return habilitacao_numero;
    }

    public void setHabilitacao_numero(String habilitacao_numero) {
        this.habilitacao_numero = habilitacao_numero;
    }

    public String getHabilitacao_categoria() {
        return habilitacao_categoria;
    }

    public void setHabilitacao_categoria(String habilitacao_categoria) {
        this.habilitacao_categoria = habilitacao_categoria;
    }

    public String getHabilitacao_vencimento() {
        return habilitacao_vencimento;
    }

    public void setHabilitacao_vencimento(String habilitacao_vencimento) {
        this.habilitacao_vencimento = habilitacao_vencimento;
    }

    public String getMot_id_transportadora() {
        return mot_id_transportadora;
    }

    public void setMot_id_transportadora(String mot_id_transportadora) {
        this.mot_id_transportadora = mot_id_transportadora;
    }

    public String getLocal_cadastro() {
        return local_cadastro;
    }

    public void setLocal_cadastro(String local_cadastro) {
        this.local_cadastro = local_cadastro;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public int getId_prospect() {
        return id_prospect;
    }

    public void setId_prospect(int id_prospect) {
        this.id_prospect = id_prospect;
    }

    public String getAlterado() {
        return alterado;
    }

    public void setAlterado(String alterado) {
        this.alterado = alterado;
    }

    public List<CadastroAnexo> getListaCadastroAnexo() {
        return listaCadastroAnexo;
    }

    public void setListaCadastroAnexo(List<CadastroAnexo> listaCadastroAnexo) {
        this.listaCadastroAnexo = listaCadastroAnexo;
    }

    public String getSituacaoPredio() {
        return situacaoPredio;
    }

    public void setSituacaoPredio(String situacaoPredio) {
        this.situacaoPredio = situacaoPredio;
    }

    public String getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(String finalizado) {
        this.finalizado = finalizado;
    }
}

