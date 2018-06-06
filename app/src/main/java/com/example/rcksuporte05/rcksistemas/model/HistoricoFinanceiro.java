package com.example.rcksuporte05.rcksistemas.model;

import java.util.ArrayList;
import java.util.List;

public class HistoricoFinanceiro {
    private List<HistoricoFinanceiroPendente> listaVencida = new ArrayList<>();
    private List<HistoricoFinanceiroPendente> listaAvencer = new ArrayList<>();
    private List<HistoricoFinanceiroQuitado> listaQuitado = new ArrayList<>();
    private Float totalVencida = 0.f;
    private Float totalAvencer = 0.f;
    private Float totalQuitado = 0.f;
    private CadastroFinanceiroResumo cadastroFinanceiroResumo;

    public List<HistoricoFinanceiroPendente> getListaVencida() {
        return listaVencida;
    }

    public void setListaVencida(List<HistoricoFinanceiroPendente> listaVencida) {
        this.listaVencida = listaVencida;
    }

    public List<HistoricoFinanceiroQuitado> getListaQuitado() {
        return listaQuitado;
    }

    public void setListaQuitado(List<HistoricoFinanceiroQuitado> listaQuitado) {
        this.listaQuitado = listaQuitado;
    }

    public List<HistoricoFinanceiroPendente> getListaAvencer() {
        return listaAvencer;
    }

    public void setListaAvencer(List<HistoricoFinanceiroPendente> listaAvencer) {
        this.listaAvencer = listaAvencer;
    }

    public Float getTotalVencida() {
        return totalVencida;
    }

    public void setTotalVencida(Float totalVencida) {
        this.totalVencida = totalVencida;
    }

    public Float getTotalAvencer() {
        return totalAvencer;
    }

    public void setTotalAvencer(Float totalAvencer) {
        this.totalAvencer = totalAvencer;
    }

    public Float getTotalQuitado() {
        return totalQuitado;
    }

    public void setTotalQuitado(Float totalQuitado) {
        this.totalQuitado = totalQuitado;
    }

    public CadastroFinanceiroResumo getCadastroFinanceiroResumo() {
        return cadastroFinanceiroResumo;
    }

    public void setCadastroFinanceiroResumo(CadastroFinanceiroResumo cadastroFinanceiroResumo) {
        this.cadastroFinanceiroResumo = cadastroFinanceiroResumo;
    }
}
