package com.example.rcksuporte05.rcksistemas.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCK 03 on 30/01/2018.
 */

public class Prospect {
    private String id_prospect;
    private String id_cadastro;
    private Segmento segmento;
    private MotivoNaoCadastramento motivoNaoCadastramento;
    private List<Contato> listaContato = new ArrayList<>();
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
    private String situacaoPredio;
    private String observacoesComerciais;
    private String limiteDeCreditoSugerido;
    private String limiteDePrazoSugerido;
    private String agencia;
    private String contaCorrente;
    private String idEmpresa;
    private String diaVisita;
    private String dataRetorno;
    private String fotoPrincipalBase64;
    private String fotoSecundariaBase64;


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

    public List<Contato> getListaContato() {
        return listaContato;
    }

    public void setListaContato(List<Contato> listaContato) {
        this.listaContato = listaContato;
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

    public String getSituacaoPredio() {
        return situacaoPredio;
    }

    public void setSituacaoPredio(String situacaoPredio) {
        this.situacaoPredio = situacaoPredio;
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

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getDiaVisita() {
        return diaVisita;
    }

    public void setDiaVisita(String diaVisita) {
        this.diaVisita = diaVisita;
    }

    public String getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(String dataRetorno) {
        this.dataRetorno = dataRetorno;
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
}
