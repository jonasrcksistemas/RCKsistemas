package com.example.rcksuporte05.rcksistemas.Helper;

import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiroPendente;
import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiroQuitado;
import com.example.rcksuporte05.rcksistemas.fragment.HistoricoFinanceiro1;
import com.example.rcksuporte05.rcksistemas.fragment.HistoricoFinanceiro2;
import com.example.rcksuporte05.rcksistemas.interfaces.HistoricoFinanceiroMain;

import java.util.ArrayList;
import java.util.List;

public class HistoricoFinanceiroHelper {
    private static HistoricoFinanceiroMain historicoFinanceiroMain;
    private static HistoricoFinanceiro1 historicoFinanceiro1;
    private static HistoricoFinanceiro2 historicoFinanceiro2;
    private static List<HistoricoFinanceiroPendente> listaVencidas = new ArrayList<>();
    private static List<HistoricoFinanceiroPendente> listaVencer = new ArrayList<>();
    private static List<HistoricoFinanceiroQuitado> listaQuitado;

    public HistoricoFinanceiroHelper(List<HistoricoFinanceiroPendente> listaVencidas, List<HistoricoFinanceiroPendente> listaVencer, List<HistoricoFinanceiroQuitado> listaQuitado) {
        this.listaVencidas = listaVencidas;
        this.listaVencer = listaVencer;
        this.listaQuitado = listaQuitado;
    }

    public HistoricoFinanceiroHelper() {
    }

    public static List<HistoricoFinanceiroPendente> getListaVencidas() {
        return listaVencidas;
    }

    public static void setListaVencidas(List<HistoricoFinanceiroPendente> listaVencidas) {
        HistoricoFinanceiroHelper.listaVencidas = listaVencidas;
    }

    public static List<HistoricoFinanceiroPendente> getListaVencer() {
        return listaVencer;
    }

    public static void setListaVencer(List<HistoricoFinanceiroPendente> listaVencer) {
        HistoricoFinanceiroHelper.listaVencer = listaVencer;
    }

    public static List<HistoricoFinanceiroQuitado> getListaQuitado() {
        return listaQuitado;
    }

    public static void setListaQuitado(List<HistoricoFinanceiroQuitado> listaQuitado) {
        HistoricoFinanceiroHelper.listaQuitado = listaQuitado;
    }

    public void limparDados() {
        try {
            historicoFinanceiroMain = null;
            historicoFinanceiro1 = null;
            listaVencidas.clear();
            listaVencer.clear();
            listaQuitado.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
