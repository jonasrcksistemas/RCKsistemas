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

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Municipios;
import com.example.rcksuporte05.rcksistemas.classes.Paises;
import com.example.rcksuporte05.rcksistemas.interfaces.CadastroClienteMain;
import com.example.rcksuporte05.rcksistemas.interfaces.HistoricoFinanceiroMain;

public class CadastroCliente3 extends Fragment implements View.OnClickListener {
    private Spinner spPaisCobranca;
    private ArrayAdapter<Paises> paisAdapter;
    private Spinner spUfCobranca;
    private ArrayAdapter<String> ufCobrancaAdapter;
    private String[] uf = {"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "EX", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO"};
    private Spinner spMunicipioCobranca;
    private ArrayAdapter<Municipios> municipioAdapter;
    private EditText edtLimiteCredito;
    private EditText edtContatoFinanceiro;
    private EditText edtEmailFinanceiro;
    private EditText edtEnderecoCobranca;
    private EditText edtNumero;
    private EditText edtComplemento;
    private EditText edtCepCobranca;
    private Button btnHistoricoFinanceiro;
    private TextView txtHistoricoFinanceiro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente3, container, false);

        ClienteHelper clienteHelper = new ClienteHelper(getContext());

        spPaisCobranca = (Spinner) view.findViewById(R.id.spPaisCobranca);
        paisAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, clienteHelper.getListaPaises());
        spPaisCobranca.setAdapter(paisAdapter);

        spUfCobranca = (Spinner) view.findViewById(R.id.spUfCobranca);
        ufCobrancaAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, uf);
        spUfCobranca.setAdapter(ufCobrancaAdapter);

        spMunicipioCobranca = (Spinner) view.findViewById(R.id.spMunicipioCobranca);
        municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, clienteHelper.getListaMunicipios());
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

        if (ClienteHelper.getCliente() != null) {

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

            if (ClienteHelper.getCliente().getLimite_credito().equals(" ")) {
                edtLimiteCredito.setText(ClienteHelper.getCliente().getLimite_credito());
            } else {
                Float limiteCredito = Float.parseFloat(ClienteHelper.getCliente().getLimite_credito());
                edtLimiteCredito.setText("R$" + String.format("%.2f", limiteCredito));
            }
            edtContatoFinanceiro.setText(ClienteHelper.getCliente().getPessoa_contato_financeiro());
            edtEmailFinanceiro.setText(ClienteHelper.getCliente().getEmail_financeiro());
            edtEnderecoCobranca.setText(ClienteHelper.getCliente().getCob_endereco());
            edtNumero.setText(ClienteHelper.getCliente().getCob_endereco_numero());
            edtComplemento.setText(ClienteHelper.getCliente().getCob_endereco_complemento());
            String cep = ClienteHelper.getCliente().getCob_endereco_cep().trim().replaceAll("[^0-9]", "");
            if (cep.length() >= 8) {
                edtCepCobranca.setText(cep.substring(0, 5) + "-" + cep.substring(5));
            } else {
                edtCepCobranca.setText(cep);
            }

            for (int i = 0; clienteHelper.getListaMunicipios().size() > i; i++) {
                if (clienteHelper.getListaMunicipios().get(i).getId_municipio().equals(ClienteHelper.getCliente().getCob_endereco_id_municipio())) {
                    spMunicipioCobranca.setSelection(i);
                }
            }
            for (int i = 0; clienteHelper.getListaPaises().size() > i; i++) {
                if (clienteHelper.getListaPaises().get(i).getId_pais().equals(ClienteHelper.getCliente().getCob_endereco_id_pais())) {
                    spPaisCobranca.setSelection(i);
                }
            }
            for (int i = 0; uf.length > i; i++) {
                if (uf[i].equals(ClienteHelper.getCliente().getCob_endereco_uf())) {
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
            if (ClienteHelper.getCliente() != null) {
                Intent intent = new Intent(getContext(), HistoricoFinanceiroMain.class);
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
