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
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterHistoricoFinanceiroQuitado;

public class HistoricoFinanceiro3 extends Fragment {

    private ListView lstHistoricoFinanceiroQuitado;
    private HistoricoFinanceiroHelper financeiroHelper;
    private EditText edtTotalTitulos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historico_financeiro_quitado, container, false);

        edtTotalTitulos = (EditText) view.findViewById(R.id.edtTotalTitulos);
        lstHistoricoFinanceiroQuitado = (ListView) view.findViewById(R.id.lstHistoricoFinanceiroQuitado);
        financeiroHelper = new HistoricoFinanceiroHelper();


        if (financeiroHelper.getListaQuitado() != null) {
            ListaAdapterHistoricoFinanceiroQuitado adapterQuitado = new ListaAdapterHistoricoFinanceiroQuitado(getContext(), financeiroHelper.getListaQuitado());
            lstHistoricoFinanceiroQuitado.setAdapter(adapterQuitado);
            Float total = 0.0f;
            for (int i = 0; financeiroHelper.getListaQuitado().size() > i; i++) {
                total += Float.parseFloat(financeiroHelper.getListaQuitado().get(i).getValor_total());
            }
            edtTotalTitulos.setText(String.format("R$%.2f", total));
        }
        System.gc();
        return (view);
    }
}
