package com.example.rcksuporte05.rcksistemas.classes;

/**
 * Created by RCK 03 on 30/01/2018.
 */

public class MotivoNaoCadastramento {

    private String idItem;
    private String idCadastro;
    private String idProspect; //é o F_Prospect da tbl_cadastro
    private String motivo;
    private String descricaoOutros;

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getIdCadastro() {
        return idCadastro;
    }

    public void setIdCadastro(String idCadastro) {
        this.idCadastro = idCadastro;
    }

    public String getIdProspect() {
        return idProspect;
    }

    public void setIdProspect(String idProspect) {
        this.idProspect = idProspect;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescricaoOutros() {
        return descricaoOutros;
    }

    public void setDescricaoOutros(String descricaoOutros) {
        this.descricaoOutros = descricaoOutros;
    }
}
