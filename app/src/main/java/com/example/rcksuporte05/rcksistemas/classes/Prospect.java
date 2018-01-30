package com.example.rcksuporte05.rcksistemas.classes;

import android.graphics.Bitmap;

/**
 * Created by RCK 03 on 30/01/2018.
 */

public class Prospect {

    private String id_prospect; //F_ID_PROSPECT  NA TBL_CADASTRO
    private String id_cadastro; // ID NA TBL_CADASTRO
    private Segmento segmento;
    private MotivoNaoCadastramento motivoNaoCadastramento;
    private String nome_cadastro;
    private String nome_fantasia;
    private String pessoa_f_j;
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
    private String situaçãoPredio;
    private String nomeResponsavel;
    private String funcaoResponsavel;
    private String celular1;
    private String celular2;
    private String telefoneFixo;
    private String email1;
    private String email2;
    private String fornecedor1;
    private String telefoneFornecedor1;
    private String fornecedor2;
    private String telefoneFornecedor2;
    private String observacoesComerciais;
    private String limiteDeCreditoSugerido;
    private String limiteDePrazoSugerido;
    private String nomeBanco;
    private String agencia;
    private String contaCorrente;
    private String fotoPrincipalBase64;
    private String fotoSecundariaBase64;
    private Bitmap fotoPrincipal;
    private Bitmap fotoSecundaria;

    public String getId_prospect() {
        return id_prospect;
    }

    public void setId_prospect(String id_prospect) {
        this.id_prospect = id_prospect;
    }

    public String getId_cadastro() {
        return id_cadastro;
    }

    public void setId_cadastro(String id_cadastro) {
        this.id_cadastro = id_cadastro;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public MotivoNaoCadastramento getMotivoNaoCadastramento() {
        return motivoNaoCadastramento;
    }

    public void setMotivoNaoCadastramento(MotivoNaoCadastramento motivoNaoCadastramento) {
        this.motivoNaoCadastramento = motivoNaoCadastramento;
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

    public String getPessoa_f_j() {
        return pessoa_f_j;
    }

    public void setPessoa_f_j(String pessoa_f_j) {
        this.pessoa_f_j = pessoa_f_j;
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

    public String getInscri_municipal() {
        return inscri_municipal;
    }

    public void setInscri_municipal(String inscri_municipal) {
        this.inscri_municipal = inscri_municipal;
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

    public String getEndereco_id_municipio() {
        return endereco_id_municipio;
    }

    public void setEndereco_id_municipio(String endereco_id_municipio) {
        this.endereco_id_municipio = endereco_id_municipio;
    }

    public String getEndereco_cep() {
        return endereco_cep;
    }

    public void setEndereco_cep(String endereco_cep) {
        this.endereco_cep = endereco_cep;
    }

    public String getSituaçãoPredio() {
        return situaçãoPredio;
    }

    public void setSituaçãoPredio(String situaçãoPredio) {
        this.situaçãoPredio = situaçãoPredio;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getFuncaoResponsavel() {
        return funcaoResponsavel;
    }

    public void setFuncaoResponsavel(String funcaoResponsavel) {
        this.funcaoResponsavel = funcaoResponsavel;
    }

    public String getCelular1() {
        return celular1;
    }

    public void setCelular1(String celular1) {
        this.celular1 = celular1;
    }

    public String getCelular2() {
        return celular2;
    }

    public void setCelular2(String celular2) {
        this.celular2 = celular2;
    }

    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getFornecedor1() {
        return fornecedor1;
    }

    public void setFornecedor1(String fornecedor1) {
        this.fornecedor1 = fornecedor1;
    }

    public String getTelefoneFornecedor1() {
        return telefoneFornecedor1;
    }

    public void setTelefoneFornecedor1(String telefoneFornecedor1) {
        this.telefoneFornecedor1 = telefoneFornecedor1;
    }

    public String getFornecedor2() {
        return fornecedor2;
    }

    public void setFornecedor2(String fornecedor2) {
        this.fornecedor2 = fornecedor2;
    }

    public String getTelefoneFornecedor2() {
        return telefoneFornecedor2;
    }

    public void setTelefoneFornecedor2(String telefoneFornecedor2) {
        this.telefoneFornecedor2 = telefoneFornecedor2;
    }

    public String getObservacoesComerciais() {
        return observacoesComerciais;
    }

    public void setObservacoesComerciais(String observacoesComerciais) {
        this.observacoesComerciais = observacoesComerciais;
    }

    public String getLimiteDeCreditoSugerido() {
        return limiteDeCreditoSugerido;
    }

    public void setLimiteDeCreditoSugerido(String limiteDeCreditoSugerido) {
        this.limiteDeCreditoSugerido = limiteDeCreditoSugerido;
    }

    public String getLimiteDePrazoSugerido() {
        return limiteDePrazoSugerido;
    }

    public void setLimiteDePrazoSugerido(String limiteDePrazoSugerido) {
        this.limiteDePrazoSugerido = limiteDePrazoSugerido;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public String getFotoPrincipalBase64() {
        return fotoPrincipalBase64;
    }

    public void setFotoPrincipalBase64(String fotoPrincipalBase64) {
        this.fotoPrincipalBase64 = fotoPrincipalBase64;
    }

    public String getFotoSecundariaBase64() {
        return fotoSecundariaBase64;
    }

    public void setFotoSecundariaBase64(String fotoSecundariaBase64) {
        this.fotoSecundariaBase64 = fotoSecundariaBase64;
    }

    public Bitmap getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(Bitmap fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }

    public Bitmap getFotoSecundaria() {
        return fotoSecundaria;
    }

    public void setFotoSecundaria(Bitmap fotoSecundaria) {
        this.fotoSecundaria = fotoSecundaria;
    }
}
