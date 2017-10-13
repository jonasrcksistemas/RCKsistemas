package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Municipios;
import com.example.rcksuporte05.rcksistemas.classes.Paises;

public class CadastroCliente2 extends Fragment {
    private Spinner spPais;
    private ArrayAdapter<Municipios> adapterMunicipios;
    private ArrayAdapter<Paises> adapterPaises;
    private Spinner spUf;
    private String[] uf = {"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "EX", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO"};
    private Spinner spMunicipio;
    private ArrayAdapter<String> ufAdapter;
    private EditText edtNumero;
    private EditText edtBairro;
    private EditText edtCep;
    private EditText edtEndereco;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente2, container, false);

        ClienteHelper clienteHelper = new ClienteHelper(getContext());

        spPais = (Spinner) view.findViewById(R.id.spPais);
        adapterPaises = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, clienteHelper.getListaPaises());
        spPais.setAdapter(adapterPaises);

        spUf = (Spinner) view.findViewById(R.id.spUf);
        ufAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, uf);
        spUf.setAdapter(ufAdapter);

        spMunicipio = (Spinner) view.findViewById(R.id.spMunicipio);
        adapterMunicipios = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, clienteHelper.getListaMunicipios());
        spMunicipio.setAdapter(adapterMunicipios);

        edtNumero = (EditText) view.findViewById(R.id.edtNumero);
        edtBairro = (EditText) view.findViewById(R.id.edtBairro);
        edtCep = (EditText) view.findViewById(R.id.edtCep);
        edtEndereco = (EditText) view.findViewById(R.id.edtEndereco);

        if (ClienteHelper.getCliente() != null) {

            edtNumero.setFocusable(false);
            edtBairro.setFocusable(false);
            edtCep.setFocusable(false);
            edtEndereco.setFocusable(false);
            spPais.setEnabled(false);
            spUf.setEnabled(false);
            spMunicipio.setEnabled(false);

            edtEndereco.setText(ClienteHelper.getCliente().getEndereco());
            edtNumero.setText(ClienteHelper.getCliente().getEndereco_numero());
            edtBairro.setText(ClienteHelper.getCliente().getEndereco_bairro());
            String cep = ClienteHelper.getCliente().getEndereco_cep().trim().replaceAll("[^0-9]", "");
            if (cep.length() >= 8) {
                edtCep.setText(cep.substring(0, 5) + "-" + cep.substring(5));
            } else {
                edtCep.setText(cep);
            }
            for (int i = 0; clienteHelper.getListaMunicipios().size() > i; i++) {
                if (clienteHelper.getListaMunicipios().get(i).equals(ClienteHelper.getCliente().getEndereco_id_municipio())) {
                    spMunicipio.setSelection(i);
                }
            }
            for (int i = 0; clienteHelper.getListaPaises().size() > i; i++) {
                if (clienteHelper.getListaPaises().get(i).getId_pais().equals(ClienteHelper.getCliente().getId_pais())) {
                    spPais.setSelection(i);
                }
            }
            for (int i = 0; uf.length > i; i++) {
                if (uf[i].equals(ClienteHelper.getCliente().getEndereco_uf())) {
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
