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

public class CadastroCliente5 extends Fragment {
    @BindView(R.id.btnSalvar)
    Button btnSalvar;
    @BindView(R.id.edtobsFat)
    EditText edtobsFat;
    @BindView(R.id.edtObsFinancas)
    EditText edtObsFinancas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente5, container, false);
        ButterKnife.bind(this, view);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DBHelper db = new DBHelper(getActivity());
                    inserirDadosDaFrame();

                    db.inserirTBL_CADASTRO(ClienteHelper.getCliente());
                    Toast.makeText(getActivity(), "Cliente salvo com sucesso!", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {

            edtobsFat.setFocusable(false);
            edtObsFinancas.setFocusable(false);

            edtobsFat.setText(ClienteHelper.getCliente().getObservacoes_faturamento());
            edtObsFinancas.setText(ClienteHelper.getCliente().getObservacoes_financeiro());

            btnSalvar.setVisibility(View.GONE);
        }

        ClienteHelper.setCadastroCliente5(this);
        return view;
    }

    public void inserirDadosDaFrame() {
        if (!edtobsFat.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setObservacoes_faturamento(edtobsFat.getText().toString());
        if (!edtObsFinancas.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setObservacoes_financeiro(edtObsFinancas.getText().toString());

        ClienteHelper.getCliente().setAtivo("S");
        ClienteHelper.getCliente().setId_empresa(Integer.parseInt(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice()));
        ClienteHelper.getCliente().setF_cliente("N");
        ClienteHelper.getCliente().setF_fornecedor("N");
        ClienteHelper.getCliente().setF_funcionario("N");
        ClienteHelper.getCliente().setF_transportador("N");
        ClienteHelper.getCliente().setF_vendedor("N");
        ClienteHelper.getCliente().setId_entidade("11");

    }

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
