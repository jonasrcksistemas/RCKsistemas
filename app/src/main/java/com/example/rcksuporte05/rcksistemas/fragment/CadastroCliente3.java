package com.example.rcksuporte05.rcksistemas.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.interfaces.CadastroClienteMain;
import com.example.rcksuporte05.rcksistemas.interfaces.HistoricoFinanceiroMain;

public class CadastroCliente3 extends Fragment implements View.OnClickListener {
    private Spinner spPaisCobranca;
    private ArrayAdapter paisAdapter;
    private Spinner spUfCobranca;
    private ArrayAdapter ufCobrancaAdapter;
    private String[] uf = {"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "EX", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO"};
    private Spinner spMunicipioCobranca;
    private ArrayAdapter municipioAdapter;
    private Bundle bundle = new Bundle();
    private EditText edtLimiteCredito;
    private EditText edtContatoFinanceiro;
    private EditText edtEmailFinanceiro;
    private EditText edtEnderecoCobranca;
    private EditText edtNumero;
    private EditText edtComplemento;
    private EditText edtCepCobranca;
    private Button btnHistoricoFinanceiro;
    private TextView txtHistoricoFinanceiro;
    private String[] cliente;
    private String[] municipios;
    private String[] idMunicipios;
    private String[] paises;
    private String[] idPaises;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente3, container, false);
        bundle = getArguments();

        municipios = bundle.getStringArray("municipios");
        idMunicipios = bundle.getStringArray("idMunicipios");
        paises = bundle.getStringArray("paises");
        idPaises = bundle.getStringArray("idPaises");

        spPaisCobranca = (Spinner) view.findViewById(R.id.spPaisCobranca);
        paisAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, paises);
        spPaisCobranca.setAdapter(paisAdapter);

        spUfCobranca = (Spinner) view.findViewById(R.id.spUfCobranca);
        ufCobrancaAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, uf);
        spUfCobranca.setAdapter(ufCobrancaAdapter);

        spMunicipioCobranca = (Spinner) view.findViewById(R.id.spMunicipioCobranca);
        municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, municipios);
        spMunicipioCobranca.setAdapter(municipioAdapter);

        edtLimiteCredito = (EditText) view.findViewById(R.id.edtLimiteCredito);
        edtContatoFinanceiro = (EditText) view.findViewById(R.id.edtContatoFinanceiro);
        edtEmailFinanceiro = (EditText) view.findViewById(R.id.edtEmailFinanceiro);
        edtEnderecoCobranca = (EditText) view.findViewById(R.id.edtEnderecoCobranca);
        edtNumero = (EditText) view.findViewById(R.id.edtNumero);
        edtComplemento = (EditText) view.findViewById(R.id.edtComplemento);
        edtCepCobranca = (EditText) view.findViewById(R.id.edtCepCobranca);

        btnHistoricoFinanceiro = (Button) view.findViewById(R.id.btnHistoricoFinanceiro);
        btnHistoricoFinanceiro.setOnClickListener(this);

        txtHistoricoFinanceiro = (TextView) view.findViewById(R.id.txtHistoricoFinanceiro);
        txtHistoricoFinanceiro.setOnClickListener(this);

        if (bundle.getInt("cliente") > 0) {
            cliente = bundle.getStringArray("clienteListar");

            edtLimiteCredito.setFocusable(false);
            edtContatoFinanceiro.setFocusable(false);
            edtEmailFinanceiro.setFocusable(false);
            edtEnderecoCobranca.setFocusable(false);
            edtNumero.setFocusable(false);
            edtComplemento.setFocusable(false);
            edtCepCobranca.setFocusable(false);
            spMunicipioCobranca.setEnabled(false);
            spPaisCobranca.setEnabled(false);
            spUfCobranca.setEnabled(false);

            if (cliente[49].equals(" ")) {
                edtLimiteCredito.setText(cliente[49]);
            } else {
                Float limiteCredito = Float.parseFloat(cliente[49]);
                edtLimiteCredito.setText("R$" + String.format("%.2f", limiteCredito));
            }
            edtContatoFinanceiro.setText(cliente[51]);
            edtEmailFinanceiro.setText(cliente[52]);
            edtEnderecoCobranca.setText(cliente[41]);
            edtNumero.setText(cliente[43]);
            edtComplemento.setText(cliente[44]);
            String cep = cliente[47].trim().replaceAll("[^0-9]", "");
            if (cep.length() >= 8) {
                edtCepCobranca.setText(cep.substring(0, 5) + "-" + cep.substring(5));
            } else {
                edtCepCobranca.setText(cep);
            }

            for (int i = 0; municipios.length > i; i++) {
                if (idMunicipios[i].equals(cliente[46])) {
                    spMunicipioCobranca.setSelection(i);
                }
            }
            for (int i = 0; paises.length > i; i++) {
                if (idPaises[i].equals(cliente[48])) {
                    spPaisCobranca.setSelection(i);
                }
            }
            for (int i = 0; uf.length > i; i++) {
                if (uf[i].equals(cliente[45])) {
                    spUfCobranca.setSelection(i);
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

    @Override
    public void onClick(View v) {
        if (v == btnHistoricoFinanceiro || v == txtHistoricoFinanceiro) {
            bundle = getArguments();
            if (bundle.getInt("cliente") > 0) {
                Intent intent = new Intent(getContext(), HistoricoFinanceiroMain.class);
                intent.putExtra("idCliente", bundle.getInt("cliente"));
                System.gc();
                getContext().startActivity(intent);
                CadastroClienteMain cadastroClienteMain = new CadastroClienteMain();
                cadastroClienteMain.finish();
            } else {
                Toast.makeText(getContext(), "Você precisa selecionar o cliente para consultar seu historico financeiro", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
