package com.example.rcksuporte05.rcksistemas.classes;

import java.util.List;

public class HistoricoFinanceiro {
    List<HistoricoFinanceiroPendente> listaPendente;
    List<HistoricoFinanceiroQuitado> listaQuitado;

    public List<HistoricoFinanceiroPendente> getListaPendente() {
        return listaPendente;
    }

    public void setListaPendente(List<HistoricoFinanceiroPendente> listaPendente) {
        this.listaPendente = listaPendente;
    }

    public List<HistoricoFinanceiroQuitado> getListaQuitado() {
        return listaQuitado;
    }

    public void setListaQuitado(List<HistoricoFinanceiroQuitado> listaQuitado) {
        this.listaQuitado = listaQuitado;
    }



}
