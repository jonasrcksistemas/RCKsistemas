package com.example.rcksuporte05.rcksistemas.fragment;

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

public class CadastroCliente9 extends Fragment {
    @BindView(R.id.btnSalvar)
    Button btnSalvar;
    @BindView(R.id.edtobsFat)
    EditText edtobsFat;
    @BindView(R.id.edtObsFinancas)
    EditText edtObsFinancas;
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente9, container, false);
        ButterKnife.bind(this, view);

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {

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

                        if (ClienteHelper.getCliente().getFinalizado().equals("N")) {
                            ClienteHelper.getCliente().setAlterado("N");
                        } else {
                            if (ClienteHelper.getCliente().getId_cadastro_servidor() > 0) {
                                ClienteHelper.getCliente().setAlterado("S");
                            }
                        }
                        if (ClienteHelper.getCliente().getFinalizado().equals("S")) {
                            ClienteHelper.getCliente().setAlterado("S");
                        }
                        ClienteHelper.getCliente().setFinalizado("S");
                        db.atualizarTBL_CADASTRO(ClienteHelper.getCliente());

                        if (getActivity().getIntent().getIntExtra("prospect", 0) > 0)
                            db.alterar("DELETE FROM TBL_PROSPECT WHERE ID_PROSPECT = " + getActivity().getIntent().getIntExtra("prospect", 0) + ";");

                        Toast.makeText(getActivity(), "Cliente salvo com sucesso!", Toast.LENGTH_LONG).show();
                        getActivity().finish();
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

        ClienteHelper.setCadastroCliente9(this);
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
        ClienteHelper.getCliente().setF_cliente("N");
        ClienteHelper.getCliente().setF_fornecedor("N");
        ClienteHelper.getCliente().setF_funcionario("N");
        ClienteHelper.getCliente().setF_transportador("N");
        ClienteHelper.getCliente().setF_vendedor("N");
        ClienteHelper.getCliente().setId_entidade("10");
        ClienteHelper.getCliente().setId_empresa(Integer.parseInt(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice()));
        ClienteHelper.getCliente().setUsuario_id(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()));
        ClienteHelper.getCliente().setUsuario_nome(UsuarioHelper.getUsuario().getNome_usuario());
    }
}
