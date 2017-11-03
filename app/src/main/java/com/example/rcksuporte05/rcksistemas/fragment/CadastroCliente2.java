package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;

public class CadastroCliente2 extends Fragment {
    private EditText edtPais;
    private EditText edtUf;
    private EditText edtMunicipio;
    private EditText edtNumero;
    private EditText edtBairro;
    private EditText edtCep;
    private EditText edtEndereco;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente2, container, false);

        edtPais = (EditText) view.findViewById(R.id.edtPais);
        edtPais.setText(ClienteHelper.getCliente().getNome_pais());

        edtUf = (EditText) view.findViewById(R.id.edtUf);
        edtUf.setText(ClienteHelper.getCliente().getEndereco_uf());

        edtMunicipio = (EditText) view.findViewById(R.id.edtMunicipio);
        edtMunicipio.setText(ClienteHelper.getCliente().getEndereco_id_municipio());

        edtNumero = (EditText) view.findViewById(R.id.edtNumero);
        edtBairro = (EditText) view.findViewById(R.id.edtBairro);
        edtCep = (EditText) view.findViewById(R.id.edtCep);
        edtEndereco = (EditText) view.findViewById(R.id.edtEndereco);

        if (ClienteHelper.getCliente() != null) {

            edtNumero.setFocusable(false);
            edtBairro.setFocusable(false);
            edtCep.setFocusable(false);
            edtEndereco.setFocusable(false);
            edtPais.setEnabled(false);
            edtUf.setEnabled(false);
            edtMunicipio.setEnabled(false);

            edtEndereco.setText(ClienteHelper.getCliente().getEndereco());
            edtNumero.setText(ClienteHelper.getCliente().getEndereco_numero());
            edtBairro.setText(ClienteHelper.getCliente().getEndereco_bairro());
            if (ClienteHelper.getCliente().getEndereco_cep() != null) {
                String cep = ClienteHelper.getCliente().getEndereco_cep().trim().replaceAll("[^0-9]", "");
                if (cep.length() >= 8) {
                    edtCep.setText(cep.substring(0, 5) + "-" + cep.substring(5));
                } else {
                    edtCep.setText(cep);
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
