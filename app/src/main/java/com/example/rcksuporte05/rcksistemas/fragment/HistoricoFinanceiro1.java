package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterHistoricoFinanceiroPendentes;
import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiroPendente;

import java.util.List;

public class HistoricoFinanceiro1 extends Fragment {

    private HistoricoFinanceiroHelper financeiroHelper;
    private ListView lstHistoricoFinanceiroPendente;
    private EditText edtTotalTitulos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historico_financeiro_pendente, container, false);

        edtTotalTitulos = (EditText) view.findViewById(R.id.edtTotalTitulos);
        lstHistoricoFinanceiroPendente = (ListView) view.findViewById(R.id.lstHistoricoFinanceiroPendente);
        financeiroHelper = new HistoricoFinanceiroHelper(this);

        if (financeiroHelper.getListaVencidas() != null) {
            ListaAdapterHistoricoFinanceiroPendentes adapterHistoricoFinanceiroVencidas = new ListaAdapterHistoricoFinanceiroPendentes(getContext(), financeiroHelper.getListaVencidas());
            lstHistoricoFinanceiroPendente.setAdapter(adapterHistoricoFinanceiroVencidas);
            Float total = 0.0f;
            for (int i = 0; financeiroHelper.getListaVencidas().size() > i; i++) {
                total += Float.parseFloat(financeiroHelper.getListaVencidas().get(i).getValor_total());
            }
            edtTotalTitulos.setText(String.format("R$%.2f", total));
        }
        System.gc();
        return (view);
    }

    public void carregarLista(List<HistoricoFinanceiroPendente> lista) {
        ListaAdapterHistoricoFinanceiroPendentes adapterHistoricoFinanceiroVencidas = new ListaAdapterHistoricoFinanceiroPendentes(getContext(), lista);
        if (lista.size() > 0) {
            lstHistoricoFinanceiroPendente.setAdapter(adapterHistoricoFinanceiroVencidas);
            Float total = 0.0f;
            for (int i = 0; lista.size() > i; i++) {
                total += Float.parseFloat(lista.get(i).getValor_total());
            }
            edtTotalTitulos.setText(String.format("R$%.2f", total));
        }
    }
}
