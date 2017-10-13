package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;

public class CadastroCliente5 extends Fragment {
    private EditText edtobsFat;
    private EditText edtObsFinancas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente5, container, false);

        edtobsFat = (EditText) view.findViewById(R.id.edtobsFat);
        edtObsFinancas = (EditText) view.findViewById(R.id.edtObsFinancas);

        if (ClienteHelper.getCliente() != null) {

            edtobsFat.setFocusable(false);
            edtObsFinancas.setFocusable(false);

            edtobsFat.setText(ClienteHelper.getCliente().getObservacoes_faturamento());
            edtObsFinancas.setText(ClienteHelper.getCliente().getObservacoes_financeiro());
        }
        return view;
    }

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
