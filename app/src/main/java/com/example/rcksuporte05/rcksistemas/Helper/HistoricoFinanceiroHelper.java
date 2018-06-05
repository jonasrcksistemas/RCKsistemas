package com.example.rcksuporte05.rcksistemas.Helper;

import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.HistoricoFinanceiro;

public class HistoricoFinanceiroHelper {
    private static HistoricoFinanceiro historicoFinanceiro;
    private static Cliente cliente;
    private static CadastroFinanceiroResumo cadastroFinanceiroResumo;

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

    public static CadastroFinanceiroResumo getCadastroFinanceiroResumo() {
        return cadastroFinanceiroResumo;
    }

    public static void setCadastroFinanceiroResumo(CadastroFinanceiroResumo cadastroFinanceiroResumo) {
        HistoricoFinanceiroHelper.cadastroFinanceiroResumo = cadastroFinanceiroResumo;
    }

    public static void limparDados() {
        try {
            historicoFinanceiro = null;
            cliente = null;
            cadastroFinanceiroResumo = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
