package com.example.rcksuporte05.rcksistemas.classes;

public class Cliente {

    private String ativo;
    private String id_empresa;
    private String id_cadastro;
    private String pessoa_f_j;
    private String data_aniversario;
    private String nome_cadastro;
    private String nome_fantasia;
    private String cpf_cnpj;
    private String inscri_estadual;
    private String inscri_municipal;
    private String endereco;
    private String endereco_bairro;
    private String endereco_numero;
    private String endereco_complemento;
    private String endereco_uf;
    private String endereco_id_municipio;
    private String endereco_cep;
    private String usuario_id;
    private String usuario_nome;
    private String usuario_data;
    private String f_cliente;
    private String f_fornecedor;
    private String f_funcionario;
    private String f_vendedor;
    private String f_transportador;
    private String data_ultima_compra;
    private String id_vendedor;
    private String f_id_cliente;
    private String id_entidade;
    private String f_id_fornecedor;
    private String f_id_vendedor;
    private String f_id_transportador;
    private String telefone_principal;
    private String email_principal;
    private String id_pais;
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
    private String cob_endereco_id_municipio;
    private String cob_endereco_cep;
    private String cob_endereco_id_pais;
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

    public String getAtivo() {
        if (ativo.equals("anyType{}")) {
            ativo = "";
        }
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getId_empresa() {
        if (id_empresa.equals("anyType{}")) {
            id_empresa = "";
        }
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getId_cadastro() {
        if (id_cadastro.equals("anyType{}")) {
            id_cadastro = "";
        }
        return id_cadastro;
    }

    public void setId_cadastro(String id_cadastro) {
        this.id_cadastro = id_cadastro;
    }

    public String getPessoa_f_j() {
        if (pessoa_f_j.equals("anyType{}")) {
            pessoa_f_j = "";
        }
        return pessoa_f_j;
    }

    public void setPessoa_f_j(String pessoa_f_j) {
        this.pessoa_f_j = pessoa_f_j;
    }

    public String getData_aniversario() {
        if (data_aniversario.equals("anyType{}")) {
            data_aniversario = "";
        }
        return data_aniversario;
    }

    public void setData_aniversario(String data_aniversario) {
        this.data_aniversario = data_aniversario;
    }

    public String getNome_cadastro() {
        if (nome_cadastro.equals("anyType{}")) {
            nome_cadastro = "";
        }
        return nome_cadastro;
    }

    public void setNome_cadastro(String nome_cadastro) {
        this.nome_cadastro = nome_cadastro;
    }

    public String getNome_fantasia() {
        if (nome_fantasia.equals("anyType{}")) {
            nome_fantasia = "";
        }
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getCpf_cnpj() {
        if (cpf_cnpj.equals("anyType{}")) {
            cpf_cnpj = "";
        }
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getInscri_estadual() {
        if (inscri_estadual.equals("anyType{}")) {
            inscri_estadual = "";
        }
        return inscri_estadual;
    }

    public void setInscri_estadual(String inscri_estadual) {
        this.inscri_estadual = inscri_estadual;
    }

    public String getInscri_municipal() {
        if (inscri_municipal.equals("anyType{}")) {
            inscri_municipal = "";
        }
        return inscri_municipal;
    }

    public void setInscri_municipal(String inscri_municipal) {
        this.inscri_municipal = inscri_municipal;
    }

    public String getEndereco() {
        if (endereco.equals("anyType{}")) {
            endereco = "";
        }
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEndereco_bairro() {
        if (endereco_bairro.equals("anyType{}")) {
            endereco_bairro = "";
        }
        return endereco_bairro;
    }

    public void setEndereco_bairro(String endereco_bairro) {
        this.endereco_bairro = endereco_bairro;
    }

    public String getEndereco_numero() {
        if (endereco_numero.equals("anyType{}")) {
            endereco_numero = "";
        }
        return endereco_numero;
    }

    public void setEndereco_numero(String endereco_numero) {
        this.endereco_numero = endereco_numero;
    }

    public String getEndereco_complemento() {
        if (endereco_complemento.equals("anyType{}")) {
            endereco_complemento = "";
        }
        return endereco_complemento;
    }

    public void setEndereco_complemento(String endereco_complemento) {
        this.endereco_complemento = endereco_complemento;
    }

    public String getEndereco_uf() {
        if (endereco_uf.equals("anyType{}")) {
            endereco_uf = "";
        }
        return endereco_uf;
    }

    public void setEndereco_uf(String endereco_uf) {
        this.endereco_uf = endereco_uf;
    }

    public String getEndereco_id_municipio() {
        if (endereco_id_municipio.equals("anyType{}")) {
            endereco_id_municipio = "";
        }
        return endereco_id_municipio;
    }

    public void setEndereco_id_municipio(String endereco_id_municipio) {
        this.endereco_id_municipio = endereco_id_municipio;
    }

    public String getEndereco_cep() {
        if (endereco_cep.equals("anyType{}")) {
            endereco_cep = "";
        }
        return endereco_cep;
    }

    public void setEndereco_cep(String endereco_cep) {
        this.endereco_cep = endereco_cep;
    }

    public String getUsuario_id() {
        if (usuario_id.equals("anyType{}")) {
            usuario_id = "";
        }
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsuario_nome() {
        if (usuario_nome.equals("anyType{}")) {
            usuario_nome = "";
        }
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getUsuario_data() {
        if (usuario_data.equals("anyType{}")) {
            usuario_data = "";
        }
        return usuario_data;
    }

    public void setUsuario_data(String usuario_data) {
        this.usuario_data = usuario_data;
    }

    public String getF_cliente() {
        if (f_cliente.equals("anyType{}")) {
            f_cliente = "";
        }
        return f_cliente;
    }

    public void setF_cliente(String f_cliente) {
        this.f_cliente = f_cliente;
    }

    public String getF_fornecedor() {
        if (f_fornecedor.equals("anyType{}")) {
            f_fornecedor = "";
        }
        return f_fornecedor;
    }

    public void setF_fornecedor(String f_fornecedor) {
        this.f_fornecedor = f_fornecedor;
    }

    public String getF_funcionario() {
        if (f_funcionario.equals("anyType{}")) {
            f_funcionario = "";
        }
        return f_funcionario;
    }

    public void setF_funcionario(String f_funcionario) {
        this.f_funcionario = f_funcionario;
    }

    public String getF_vendedor() {
        if (f_vendedor.equals("anyType{}")) {
            f_vendedor = "";
        }
        return f_vendedor;
    }

    public void setF_vendedor(String f_vendedor) {
        this.f_vendedor = f_vendedor;
    }

    public String getF_transportador() {
        if (f_transportador.equals("anyType{}")) {
            f_transportador = "";
        }
        return f_transportador;
    }

    public void setF_transportador(String f_transportador) {
        this.f_transportador = f_transportador;
    }

    public String getData_ultima_compra() {
        if (data_ultima_compra.equals("anyType{}")) {
            data_ultima_compra = "";
        }
        return data_ultima_compra;
    }

    public void setData_ultima_compra(String data_ultima_compra) {
        this.data_ultima_compra = data_ultima_compra;
    }

    public String getId_vendedor() {
        if (id_vendedor.equals("anyType{}")) {
            id_vendedor = "";
        }
        return id_vendedor;
    }

    public void setId_vendedor(String id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getF_id_cliente() {
        if (f_id_cliente.equals("anyType{}")) {
            f_id_cliente = "";
        }
        return f_id_cliente;
    }

    public void setF_id_cliente(String f_id_cliente) {
        this.f_id_cliente = f_id_cliente;
    }

    public String getId_entidade() {
        if (id_entidade.equals("anyType{}")) {
            id_entidade = "";
        }
        return id_entidade;
    }

    public void setId_entidade(String id_entidade) {
        this.id_entidade = id_entidade;
    }

    public String getF_id_fornecedor() {
        if (f_id_fornecedor.equals("anyType{}")) {
            f_id_fornecedor = "";
        }
        return f_id_fornecedor;
    }

    public void setF_id_fornecedor(String f_id_fornecedor) {
        this.f_id_fornecedor = f_id_fornecedor;
    }

    public String getF_id_vendedor() {
        if (f_id_vendedor.equals("anyType{}")) {
            f_id_vendedor = "";
        }
        return f_id_vendedor;
    }

    public void setF_id_vendedor(String f_id_vendedor) {
        this.f_id_vendedor = f_id_vendedor;
    }

    public String getF_id_transportador() {
        if (f_id_transportador.equals("anyType{}")) {
            f_id_transportador = "";
        }
        return f_id_transportador;
    }

    public void setF_id_transportador(String f_id_transportador) {
        this.f_id_transportador = f_id_transportador;
    }

    public String getTelefone_principal() {
        if (telefone_principal.equals("anyType{}")) {
            telefone_principal = "";
        }
        return telefone_principal;
    }

    public void setTelefone_principal(String telefone_principal) {
        this.telefone_principal = telefone_principal;
    }

    public String getEmail_principal() {
        if (email_principal.equals("anyType{}")) {
            email_principal = "";
        }
        return email_principal;
    }

    public void setEmail_principal(String email_principal) {
        this.email_principal = email_principal;
    }

    public String getId_pais() {
        if (id_pais.equals("anyType{}")) {
            id_pais = "";
        }
        return id_pais;
    }

    public void setId_pais(String id_pais) {
        this.id_pais = id_pais;
    }

    public String getF_id_funcionario() {
        if (f_id_funcionario.equals("anyType{}")) {
            f_id_funcionario = "";
        }
        return f_id_funcionario;
    }

    public void setF_id_funcionario(String f_id_funcionario) {
        this.f_id_funcionario = f_id_funcionario;
    }

    public String getAvisar_com_dias() {
        if (avisar_com_dias.equals("anyType{}")) {
            avisar_com_dias = "";
        }
        return avisar_com_dias;
    }

    public void setAvisar_com_dias(String avisar_com_dias) {
        this.avisar_com_dias = avisar_com_dias;
    }

    public String getObservacoes() {
        if (observacoes.equals("anyType{}")) {
            observacoes = "";
        }
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getPadrao_id_c_custo() {
        if (padrao_id_c_custo.equals("anyType{}")) {
            padrao_id_c_custo = "";
        }
        return padrao_id_c_custo;
    }

    public void setPadrao_id_c_custo(String padrao_id_c_custo) {
        this.padrao_id_c_custo = padrao_id_c_custo;
    }

    public String getPadrao_id_c_gerenciadora() {
        if (padrao_id_c_gerenciadora.equals("anyType{}")) {
            padrao_id_c_gerenciadora = "";
        }
        return padrao_id_c_gerenciadora;
    }

    public void setPadrao_id_c_gerenciadora(String padrao_id_c_gerenciadora) {
        this.padrao_id_c_gerenciadora = padrao_id_c_gerenciadora;
    }

    public String getPadrao_id_c_analitica() {
        if (padrao_id_c_analitica.equals("anyType{}")) {
            padrao_id_c_analitica = "";
        }
        return padrao_id_c_analitica;
    }

    public void setPadrao_id_c_analitica(String padrao_id_c_analitica) {
        this.padrao_id_c_analitica = padrao_id_c_analitica;
    }

    public String getCob_endereco() {
        if (cob_endereco.equals("anyType{}")) {
            cob_endereco = "";
        }
        return cob_endereco;
    }

    public void setCob_endereco(String cob_endereco) {
        this.cob_endereco = cob_endereco;
    }

    public String getCob_endereco_bairro() {
        if (cob_endereco_bairro.equals("anyType{}")) {
            cob_endereco_bairro = "";
        }
        return cob_endereco_bairro;
    }

    public void setCob_endereco_bairro(String cob_endereco_bairro) {
        this.cob_endereco_bairro = cob_endereco_bairro;
    }

    public String getCob_endereco_numero() {
        if (cob_endereco_numero.equals("anyType{}")) {
            cob_endereco_numero = "";
        }
        return cob_endereco_numero;
    }

    public void setCob_endereco_numero(String cob_endereco_numero) {
        this.cob_endereco_numero = cob_endereco_numero;
    }

    public String getCob_endereco_complemento() {
        if (cob_endereco_complemento.equals("anyType{}")) {
            cob_endereco_complemento = "";
        }
        return cob_endereco_complemento;
    }

    public void setCob_endereco_complemento(String cob_endereco_complemento) {
        this.cob_endereco_complemento = cob_endereco_complemento;
    }

    public String getCob_endereco_uf() {
        if (cob_endereco_uf.equals("anyType{}")) {
            cob_endereco_uf = "";
        }
        return cob_endereco_uf;
    }

    public void setCob_endereco_uf(String cob_endereco_uf) {
        this.cob_endereco_uf = cob_endereco_uf;
    }

    public String getCob_endereco_id_municipio() {
        if (cob_endereco_id_municipio.equals("anyType{}")) {
            cob_endereco_id_municipio = "";
        }
        return cob_endereco_id_municipio;
    }

    public void setCob_endereco_id_municipio(String cob_endereco_id_municipio) {
        this.cob_endereco_id_municipio = cob_endereco_id_municipio;
    }

    public String getCob_endereco_cep() {
        if (cob_endereco_cep.equals("anyType{}")) {
            cob_endereco_cep = "";
        }
        return cob_endereco_cep;
    }

    public void setCob_endereco_cep(String cob_endereco_cep) {
        this.cob_endereco_cep = cob_endereco_cep;
    }

    public String getCob_endereco_id_pais() {
        if (cob_endereco_id_pais.equals("anyType{}")) {
            cob_endereco_id_pais = "";
        }
        return cob_endereco_id_pais;
    }

    public void setCob_endereco_id_pais(String cob_endereco_id_pais) {
        this.cob_endereco_id_pais = cob_endereco_id_pais;
    }

    public String getLimite_credito() {
        if (limite_credito.equals("anyType{}")) {
            limite_credito = "";
        }
        return limite_credito;
    }

    public void setLimite_credito(String limite_credito) {
        this.limite_credito = limite_credito;
    }

    public String getLimite_disponivel() {
        if (limite_disponivel.equals("anyType{}")) {
            limite_disponivel = "";
        }
        return limite_disponivel;
    }

    public void setLimite_disponivel(String limite_disponivel) {
        this.limite_disponivel = limite_disponivel;
    }

    public String getPessoa_contato_financeiro() {
        if (pessoa_contato_financeiro.equals("anyType{}")) {
            pessoa_contato_financeiro = "";
        }
        return pessoa_contato_financeiro;
    }

    public void setPessoa_contato_financeiro(String pessoa_contato_financeiro) {
        this.pessoa_contato_financeiro = pessoa_contato_financeiro;
    }

    public String getEmail_financeiro() {
        if (email_financeiro.equals("anyType{}")) {
            email_financeiro = "";
        }
        return email_financeiro;
    }

    public void setEmail_financeiro(String email_financeiro) {
        this.email_financeiro = email_financeiro;
    }

    public String getObservacoes_faturamento() {
        if (observacoes_faturamento.equals("anyType{}")) {
            observacoes_faturamento = "";
        }
        return observacoes_faturamento;
    }

    public void setObservacoes_faturamento(String observacoes_faturamento) {
        this.observacoes_faturamento = observacoes_faturamento;
    }

    public String getObservacoes_financeiro() {
        if (observacoes_financeiro.equals("anyType{}")) {
            observacoes_financeiro = "";
        }
        return observacoes_financeiro;
    }

    public void setObservacoes_financeiro(String observacoes_financeiro) {
        this.observacoes_financeiro = observacoes_financeiro;
    }

    public String getTelefone_dois() {
        if (telefone_dois.equals("anyType{}")) {
            telefone_dois = "";
        }
        return telefone_dois;
    }

    public void setTelefone_dois(String telefone_dois) {
        this.telefone_dois = telefone_dois;
    }

    public String getTelefone_tres() {
        if (telefone_tres.equals("anyType{}")) {
            telefone_tres = "";
        }
        return telefone_tres;
    }

    public void setTelefone_tres(String telefone_tres) {
        this.telefone_tres = telefone_tres;
    }

    public String getPessoa_contato_principal() {
        if (pessoa_contato_principal.equals("anyType{}")) {
            pessoa_contato_principal = "";
        }
        return pessoa_contato_principal;
    }

    public void setPessoa_contato_principal(String pessoa_contato_principal) {
        this.pessoa_contato_principal = pessoa_contato_principal;
    }

    public String getInd_da_ie_destinatario() {
        if (ind_da_ie_destinatario.equals("anyType{}")) {
            ind_da_ie_destinatario = "";
        }
        return ind_da_ie_destinatario;
    }

    public void setInd_da_ie_destinatario(String ind_da_ie_destinatario) {
        this.ind_da_ie_destinatario = ind_da_ie_destinatario;
    }

    public String getComissao_percentual() {
        if (comissao_percentual.equals("anyType{}")) {
            comissao_percentual = "";
        }
        return comissao_percentual;
    }

    public void setComissao_percentual(String comissao_percentual) {
        this.comissao_percentual = comissao_percentual;
    }

    public String getId_setor() {
        if (id_setor.equals("anyType{}")) {
            id_setor = "";
        }
        return id_setor;
    }

    public void setId_setor(String id_setor) {
        this.id_setor = id_setor;
    }

    public String getNfe_email_enviar() {
        if (nfe_email_enviar.equals("anyType{}")) {
            nfe_email_enviar = "";
        }
        return nfe_email_enviar;
    }

    public void setNfe_email_enviar(String nfe_email_enviar) {
        this.nfe_email_enviar = nfe_email_enviar;
    }

    public String getNfe_email_um() {
        if (nfe_email_um.equals("anyType{}")) {
            nfe_email_um = "";
        }
        return nfe_email_um;
    }

    public void setNfe_email_um(String nfe_email_um) {
        this.nfe_email_um = nfe_email_um;
    }

    public String getNfe_email_dois() {
        if (nfe_email_dois.equals("anyType{}")) {
            nfe_email_dois = "";
        }
        return nfe_email_dois;
    }

    public void setNfe_email_dois(String nfe_email_dois) {
        this.nfe_email_dois = nfe_email_dois;
    }

    public String getNfe_email_tres() {
        if (nfe_email_tres.equals("anyType{}")) {
            nfe_email_tres = "";
        }
        return nfe_email_tres;
    }

    public void setNfe_email_tres(String nfe_email_tres) {
        this.nfe_email_tres = nfe_email_tres;
    }

    public String getNfe_email_quatro() {
        if (nfe_email_quatro.equals("anyType{}")) {
            nfe_email_quatro = "";
        }
        return nfe_email_quatro;
    }

    public void setNfe_email_quatro(String nfe_email_quatro) {
        this.nfe_email_quatro = nfe_email_quatro;
    }

    public String getNfe_email_cinco() {
        if (nfe_email_cinco.equals("anyType{}")) {
            nfe_email_cinco = "";
        }
        return nfe_email_cinco;
    }

    public void setNfe_email_cinco(String nfe_email_cinco) {
        this.nfe_email_cinco = nfe_email_cinco;
    }

    public String getId_grupo_vendedor() {
        if (id_grupo_vendedor.equals("anyType{}")) {
            id_grupo_vendedor = "";
        }
        return id_grupo_vendedor;
    }

    public void setId_grupo_vendedor(String id_grupo_vendedor) {
        this.id_grupo_vendedor = id_grupo_vendedor;
    }

    public String getVendedor_usa_portal() {
        if (vendedor_usa_portal.equals("anyType{}")) {
            vendedor_usa_portal = "";
        }
        return vendedor_usa_portal;
    }

    public void setVendedor_usa_portal(String vendedor_usa_portal) {
        this.vendedor_usa_portal = vendedor_usa_portal;
    }

    public String getVendedor_id_user_portal() {
        if (vendedor_id_user_portal.equals("anyType{}")) {
            vendedor_id_user_portal = "";
        }
        return vendedor_id_user_portal;
    }

    public void setVendedor_id_user_portal(String vendedor_id_user_portal) {
        this.vendedor_id_user_portal = vendedor_id_user_portal;
    }

    public String getF_tarifa() {
        if (f_tarifa.equals("anyType{}")) {
            f_tarifa = "";
        }
        return f_tarifa;
    }

    public void setF_tarifa(String f_tarifa) {
        this.f_tarifa = f_tarifa;
    }

    public String getF_id_tarifa() {
        if (f_id_tarifa.equals("anyType{}")) {
            f_id_tarifa = "";
        }
        return f_id_tarifa;
    }

    public void setF_id_tarifa(String f_id_tarifa) {
        this.f_id_tarifa = f_id_tarifa;
    }

    public String getF_produtor() {
        if (f_produtor.equals("anyType{}")) {
            f_produtor = "";
        }
        return f_produtor;
    }

    public void setF_produtor(String f_produtor) {
        this.f_produtor = f_produtor;
    }

    public String getRg_numero() {
        if (rg_numero.equals("anyType{}")) {
            rg_numero = "";
        }
        return rg_numero;
    }

    public void setRg_numero(String rg_numero) {
        this.rg_numero = rg_numero;
    }

    public String getRg_ssp() {
        if (rg_ssp.equals("anyType{}")) {
            rg_ssp = "";
        }
        return rg_ssp;
    }

    public void setRg_ssp(String rg_ssp) {
        this.rg_ssp = rg_ssp;
    }

    public String getConta_contabil() {
        if (conta_contabil.equals("anyType{}")) {
            conta_contabil = "";
        }
        return conta_contabil;
    }

    public void setConta_contabil(String conta_contabil) {
        this.conta_contabil = conta_contabil;
    }

    public String getMotorista() {
        if (motorista.equals("anyType{}")) {
            motorista = "";
        }
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getF_id_motorista() {
        if (f_id_motorista.equals("anyType{}")) {
            f_id_motorista = "";
        }
        return f_id_motorista;
    }

    public void setF_id_motorista(String f_id_motorista) {
        this.f_id_motorista = f_id_motorista;
    }

    public String getHabilitacao_numero() {
        if (habilitacao_numero.equals("anyType{}")) {
            habilitacao_numero = "";
        }
        return habilitacao_numero;
    }

    public void setHabilitacao_numero(String habilitacao_numero) {
        this.habilitacao_numero = habilitacao_numero;
    }

    public String getHabilitacao_categoria() {
        if (habilitacao_categoria.equals("anyType{}")) {
            habilitacao_categoria = "";
        }
        return habilitacao_categoria;
    }

    public void setHabilitacao_categoria(String habilitacao_categoria) {
        this.habilitacao_categoria = habilitacao_categoria;
    }

    public String getHabilitacao_vencimento() {
        if (habilitacao_vencimento.equals("anyType{}")) {
            habilitacao_vencimento = "";
        }
        return habilitacao_vencimento;
    }

    public void setHabilitacao_vencimento(String habilitacao_vencimento) {
        this.habilitacao_vencimento = habilitacao_vencimento;
    }

    public String getMot_id_transportadora() {
        if (mot_id_transportadora.equals("anyType{}")) {
            mot_id_transportadora = "";
        }
        return mot_id_transportadora;
    }

    public void setMot_id_transportadora(String mot_id_transportadora) {
        this.mot_id_transportadora = mot_id_transportadora;
    }

    public String getLocal_cadastro() {
        if (local_cadastro.equals("anyType{}")) {
            local_cadastro = "";
        }
        return local_cadastro;
    }

    public void setLocal_cadastro(String local_cadastro) {
        this.local_cadastro = local_cadastro;
    }
}

