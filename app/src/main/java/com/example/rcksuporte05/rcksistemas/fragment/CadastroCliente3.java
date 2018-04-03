package com.example.rcksuporte05.rcksistemas.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.CadastroClienteMain;
import com.example.rcksuporte05.rcksistemas.activity.FinanceiroResumoActivity;

public class CadastroCliente3 extends Fragment implements View.OnClickListener {
    private EditText edtPaisCobranca;
    private EditText edtUfCobranca;
    private EditText edtMunicipioCobranca;
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

        edtPaisCobranca = (EditText) view.findViewById(R.id.edtPaisCobranca);
        edtPaisCobranca.setText(ClienteHelper.getCliente().getNome_pais_cob());

        edtUfCobranca = (EditText) view.findViewById(R.id.edtUfCobranca);
        edtUfCobranca.setText(ClienteHelper.getCliente().getCob_endereco_uf());

        edtMunicipioCobranca = (EditText) view.findViewById(R.id.edtMunicipioCobranca);
        edtMunicipioCobranca.setText(ClienteHelper.getCliente().getCob_endereco_id_municipio());

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
            edtMunicipioCobranca.setEnabled(false);
            edtPaisCobranca.setEnabled(false);
            edtUfCobranca.setEnabled(false);

            if (ClienteHelper.getCliente().getLimite_credito() != null) {
                Float limiteCredito = Float.parseFloat(ClienteHelper.getCliente().getLimite_credito());
                edtLimiteCredito.setText("R$" + String.format("%.2f", limiteCredito));
            }
            edtContatoFinanceiro.setText(ClienteHelper.getCliente().getPessoa_contato_financeiro());
            edtEmailFinanceiro.setText(ClienteHelper.getCliente().getEmail_financeiro());
            edtEnderecoCobranca.setText(ClienteHelper.getCliente().getCob_endereco());
            edtNumero.setText(ClienteHelper.getCliente().getCob_endereco_numero());
            edtComplemento.setText(ClienteHelper.getCliente().getCob_endereco_complemento());

            if (ClienteHelper.getCliente().getCob_endereco_cep() != null) {
                String cep = ClienteHelper.getCliente().getCob_endereco_cep().trim().replaceAll("[^0-9]", "");
                if (cep.length() >= 8) {
                    edtCepCobranca.setText(cep.substring(0, 5) + "-" + cep.substring(5));
                } else {
                    edtCepCobranca.setText(cep);
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
                Intent intent = new Intent(getContext(), FinanceiroResumoActivity.class);
                HistoricoFinanceiroHelper.setCliente(ClienteHelper.getCliente());
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
