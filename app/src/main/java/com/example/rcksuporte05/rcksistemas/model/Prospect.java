package com.example.rcksuporte05.rcksistemas.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCK 03 on 30/01/2018.
 */

public class Prospect {
    public static int PROSPECT_PENDENTE_SALVO = 0;
    public static int PROSPECT_PENDENTE = 1;
    public static int PROSPECT_SALVO = 2;
    public static int PROSPECT_ENVIADO = 3;
    private String latitude;
    private String longitude;
    private int idVendedor;
    private int idCategoria;
    private String id_prospect;
    private String id_prospect_servidor;
    private String id_cadastro;
    private MotivoNaoCadastramento motivoNaoCadastramento;
    private List<VisitaProspect> visitas = new ArrayList<>();
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
    private String nome_municipio;
    private String endereco_cep;
    private String id_pais;
    private String usuario_id;
    private String usuario_data;
    private String situacaoPredio;
    private String limiteDeCreditoSugerido;
    private String limiteDePrazoSugerido;
    private String idEmpresa;
    private String diaVisita;
    private String dataRetorno;
    private CadastroAnexo fotoPrincipalBase64;
    private CadastroAnexo fotoSecundariaBase64;
    private String observacoesComerciais;
    private String prospectSalvo;
    private String ind_da_ie_destinatario_prospect;
    private String usuario_nome;
    private String finalizado;

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

    public CadastroAnexo getFotoPrincipalBase64() {
        return fotoPrincipalBase64;
    }

    public void setFotoPrincipalBase64(CadastroAnexo fotoPrincipalBase64) {
        this.fotoPrincipalBase64 = fotoPrincipalBase64;
    }

    public CadastroAnexo getFotoSecundariaBase64() {
        return fotoSecundariaBase64;
    }

    public void setFotoSecundariaBase64(CadastroAnexo fotoSecundariaBase64) {
        this.fotoSecundariaBase64 = fotoSecundariaBase64;
    }

    public String getObservacoesComerciais() {
        return observacoesComerciais;
    }

    public void setObservacoesComerciais(String observacoesComerciais) {
        this.observacoesComerciais = observacoesComerciais;
    }

    public String getId_pais() {
        return id_pais;
    }

    public void setId_pais(String id_pais) {
        this.id_pais = id_pais;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNome_municipio() {
        return nome_municipio;
    }

    public void setNome_municipio(String nome_municipio) {
        this.nome_municipio = nome_municipio;
    }

    public String getId_prospect_servidor() {
        return id_prospect_servidor;
    }

    public void setId_prospect_servidor(String id_prospect_servidor) {
        this.id_prospect_servidor = id_prospect_servidor;
    }

    public String getProspectSalvo() {
        return prospectSalvo;
    }

    public void setProspectSalvo(String prospectSalvo) {
        this.prospectSalvo = prospectSalvo;
    }

    public String getInd_da_ie_destinatario_prospect() {
        return ind_da_ie_destinatario_prospect;
    }

    public void setInd_da_ie_destinatario_prospect(String ind_da_ie_destinatario_prospect) {
        this.ind_da_ie_destinatario_prospect = ind_da_ie_destinatario_prospect;
    }

    public List<VisitaProspect> getVisitas() {
        return visitas;
    }

    public void setVisitas(List<VisitaProspect> visitas) {
        this.visitas = visitas;
    }

    public String getUsuario_nome() {
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUsuario_data() {
        return usuario_data;
    }

    public void setUsuario_data(String usuario_data) {
        this.usuario_data = usuario_data;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(String finalizado) {
        this.finalizado = finalizado;
    }
}
