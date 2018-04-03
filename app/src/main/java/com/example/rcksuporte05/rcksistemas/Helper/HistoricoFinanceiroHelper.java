package com.example.rcksuporte05.rcksistemas.Helper;

import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.HistoricoFinanceiro;

public class HistoricoFinanceiroHelper {
    private static HistoricoFinanceiro historicoFinanceiro;
    private static Cliente cliente;

    public HistoricoFinanceiroHelper() {
    }

    public static Cliente getCliente() {
        return cliente;
    }

    public static void setCliente(Cliente cliente) {
        HistoricoFinanceiroHelper.cliente = cliente;
    }

    public static HistoricoFinanceiro getHistoricoFinanceiro() {
        return historicoFinanceiro;
    }

    public static void setHistoricoFinanceiro(HistoricoFinanceiro historicoFinanceiro) {
        HistoricoFinanceiroHelper.historicoFinanceiro = historicoFinanceiro;
    }

    public static void limparDados() {
        try {
            historicoFinanceiro = null;
            cliente = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
