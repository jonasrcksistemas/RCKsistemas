package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;

public class CadastroCliente2 extends Fragment {
    private Spinner spPais;
    private ArrayAdapter<String> adapter;
    private Spinner spUf;
    private String[] uf = {"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "EX", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO"};
    private ArrayAdapter<String> MunAdapter;
    private Spinner spMunicipio;
    private ArrayAdapter<String> ufAdapter;
    private Bundle bundle = new Bundle();
    private EditText edtNumero;
    private TextView txtNumero;
    private EditText edtBairro;
    private TextView txtBairo;
    private TextView txtPais;
    private TextView txtUf;
    private TextView txtMunicipio;
    private EditText edtCep;
    private EditText edtEndereco;
    private TextView txtCep;
    private TextView txtEndereco;
    private String[] cliente;
    private String[] municipios;
    private String[] idMunicipios;
    private String[] paises;
    private String[] idPaises;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente2, container, false);

        bundle = getArguments();

        municipios = bundle.getStringArray("municipios");
        idMunicipios = bundle.getStringArray("idMunicipios");
        paises = bundle.getStringArray("paises");
        idPaises = bundle.getStringArray("idPaises");

        spPais = (Spinner) view.findViewById(R.id.spPais);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, paises);
        spPais.setAdapter(adapter);

        spUf = (Spinner) view.findViewById(R.id.spUf);
        ufAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, uf);
        spUf.setAdapter(ufAdapter);

        spMunicipio = (Spinner) view.findViewById(R.id.spMunicipio);
        MunAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, municipios);
        spMunicipio.setAdapter(MunAdapter);

        edtNumero = (EditText) view.findViewById(R.id.edtNumero);
        txtNumero = (TextView) view.findViewById(R.id.txtNumero);
        edtBairro = (EditText) view.findViewById(R.id.edtBairro);
        txtBairo = (TextView) view.findViewById(R.id.txtBairo);
        txtPais = (TextView) view.findViewById(R.id.txtPais);
        txtUf = (TextView) view.findViewById(R.id.txtUf);
        txtMunicipio = (TextView) view.findViewById(R.id.txtMunicipio);
        edtCep = (EditText) view.findViewById(R.id.edtCep);
        edtEndereco = (EditText) view.findViewById(R.id.edtEndereco);
        txtCep = (TextView) view.findViewById(R.id.txtCep);
        txtEndereco = (TextView) view.findViewById(R.id.txtEndereco);

        if (bundle.getInt("cliente") > 0) {
            cliente = bundle.getStringArray("clienteListar");

            edtNumero.setFocusable(false);
            edtBairro.setFocusable(false);
            edtCep.setFocusable(false);
            edtEndereco.setFocusable(false);
            spPais.setEnabled(false);
            spUf.setEnabled(false);
            spMunicipio.setEnabled(false);

            edtEndereco.setText(cliente[10]);
            edtNumero.setText(cliente[12]);
            edtBairro.setText(cliente[11]);
            String cep = cliente[16].trim().replaceAll("[^0-9]", "");
            if (cep.length() >= 8) {
                edtCep.setText(cep.substring(0, 5) + "-" + cep.substring(5));
            } else {
                edtCep.setText(cep);
            }
            for (int i = 0; municipios.length > i; i++) {
                if (idMunicipios[i].equals(cliente[15])) {
                    spMunicipio.setSelection(i);
                }
            }
            for (int i = 0; paises.length > i; i++) {
                if (idPaises[i].equals(cliente[34])) {
                    spPais.setSelection(i);
                }
            }
            for (int i = 0; uf.length > i; i++) {
                if (uf[i].equals(cliente[14])) {
                    spUf.setSelection(i);
                }
            }

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
