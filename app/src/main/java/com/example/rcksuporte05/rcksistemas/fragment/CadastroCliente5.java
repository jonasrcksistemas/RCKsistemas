package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;

public class CadastroCliente5 extends Fragment {
    private TextView txtobsFat;
    private EditText edtobsFat;
    private TextView txtObsFinancas;
    private EditText edtObsFinancas;
    private String[] cliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente5, container, false);

        txtobsFat = (TextView) view.findViewById(R.id.txtobsFat);
        edtobsFat = (EditText) view.findViewById(R.id.edtobsFat);
        txtObsFinancas = (TextView) view.findViewById(R.id.txtObsFinancas);
        edtObsFinancas = (EditText) view.findViewById(R.id.edtObsFinancas);

        Bundle bundle = getArguments();

        if (bundle.getInt("cliente") > 0) {
            cliente = bundle.getStringArray("clienteListar");

            edtobsFat.setFocusable(false);
            edtObsFinancas.setFocusable(false);

            edtobsFat.setText(cliente[53]);
            edtObsFinancas.setText(cliente[54]);
        }
        System.gc();
        return view;
    }

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
