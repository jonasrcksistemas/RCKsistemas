package com.example.rcksuporte05.rcksistemas.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroCliente8 extends Fragment {
    @BindView(R.id.btnSalvar)
    Button btnSalvar;
    @BindView(R.id.edtobsFat)
    EditText edtobsFat;
    @BindView(R.id.edtObsFinancas)
    EditText edtObsFinancas;
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente8, container, false);
        ButterKnife.bind(this, view);

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1 || ClienteHelper.getCliente().getId_cadastro_servidor() > 0) {

            edtobsFat.setFocusable(false);
            edtObsFinancas.setFocusable(false);

            btnSalvar.setVisibility(View.GONE);
        } else {
            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        db = new DBHelper(getActivity());
                        inserirDadosDaFrame();

                        if (ClienteHelper.salvarCliente()) {
                            if (verificaCpfCnpj(ClienteHelper.getCliente().getCpf_cnpj())) {
                                if (ClienteHelper.getCliente().getId_cadastro() <= 0)
                                    db.inserirTBL_CADASTRO(ClienteHelper.getCliente());
                                else
                                    db.atualizarTBL_CADASTRO(ClienteHelper.getCliente());

                                if (getActivity().getIntent().getIntExtra("prospect", 0) > 0)
                                    db.alterar("DELETE FROM TBL_PROSPECT WHERE ID_PROSPECT = " + getActivity().getIntent().getIntExtra("prospect", 0) + ";");

                                Toast.makeText(getActivity(), "Cliente salvo com sucesso!", Toast.LENGTH_LONG).show();
                                getActivity().finish();

                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                alert.setTitle("Atenção");
                                switch (ClienteHelper.getCliente().getPessoa_f_j()) {
                                    case "F":
                                        alert.setMessage("Já existe outro cliente com esse CPF");
                                        break;
                                    case "J":
                                        alert.setMessage("Já existe outro cliente com esse CNPJ");
                                        break;
                                    default:
                                        alert.setMessage("Já existe outro cliente com esse CPF/CNPJ");
                                        break;
                                }
                                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ClienteHelper.moveTela(0);
                                    }
                                });
                                alert.show();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if (ClienteHelper.getCliente().getObservacoes_faturamento() != null && !ClienteHelper.getCliente().getObservacoes_faturamento().trim().isEmpty())
            edtobsFat.setText(ClienteHelper.getCliente().getObservacoes_faturamento());
        if (ClienteHelper.getCliente().getObservacoes_financeiro() != null && !ClienteHelper.getCliente().getObservacoes_financeiro().trim().isEmpty())
            edtObsFinancas.setText(ClienteHelper.getCliente().getObservacoes_financeiro());

        ClienteHelper.setCadastroCliente8(this);
        return view;
    }

    public void inserirDadosDaFrame() {
        if (!edtobsFat.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setObservacoes_faturamento(edtobsFat.getText().toString());
        else
            ClienteHelper.getCliente().setObservacoes_faturamento(null);

        if (!edtObsFinancas.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setObservacoes_financeiro(edtObsFinancas.getText().toString());
        else
            ClienteHelper.getCliente().setObservacoes_financeiro(null);

        ClienteHelper.getCliente().setAtivo("S");
        ClienteHelper.getCliente().setId_empresa(Integer.parseInt(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice()));
        ClienteHelper.getCliente().setF_cliente("N");
        ClienteHelper.getCliente().setF_fornecedor("N");
        ClienteHelper.getCliente().setF_funcionario("N");
        ClienteHelper.getCliente().setF_transportador("N");
        ClienteHelper.getCliente().setF_vendedor("N");
        ClienteHelper.getCliente().setId_entidade("10");
    }

    public boolean verificaCpfCnpj(String cpfCnpj) {
        if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO WHERE CPF_CNPJ = '" + cpfCnpj + "' AND ID_CADASTRO <> " + ClienteHelper.getCliente().getId_cadastro() + ";") > 0)
            return false;
        else
            return true;
    }
}
