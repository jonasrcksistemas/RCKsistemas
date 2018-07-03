package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroCliente7 extends Fragment {

    @BindView(R.id.rdSim)
    RadioButton rdSim;
    @BindView(R.id.rdNao)
    RadioButton rdNao;
    @BindView(R.id.edtEmail4)
    EditText edtEmail4;
    @BindView(R.id.edtEmail1)
    EditText edtEmail1;
    @BindView(R.id.edtEmail5)
    EditText edtEmail5;
    @BindView(R.id.edtEmail3)
    EditText edtEmail3;
    @BindView(R.id.edtEmail2)
    EditText edtEmail2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente7, container, false);
        ButterKnife.bind(this, view);

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {

            rdSim.setClickable(false);
            rdNao.setClickable(false);
            edtEmail4.setFocusable(false);
            edtEmail1.setFocusable(false);
            edtEmail5.setFocusable(false);
            edtEmail3.setFocusable(false);
            edtEmail2.setFocusable(false);
        }

        if (ClienteHelper.getCliente().getNfe_email_enviar() != null && !ClienteHelper.getCliente().getNfe_email_enviar().trim().isEmpty()) {
            if (ClienteHelper.getCliente().getNfe_email_enviar().equals("S")) {
                rdSim.setChecked(true);
            } else if (ClienteHelper.getCliente().getNfe_email_enviar().equals("N")) {
                rdNao.setChecked(true);
            }
        }

        if (ClienteHelper.getCliente().getNfe_email_um() != null && !ClienteHelper.getCliente().getNfe_email_um().trim().isEmpty())
            edtEmail1.setText(ClienteHelper.getCliente().getNfe_email_um());
        if (ClienteHelper.getCliente().getNfe_email_dois() != null && !ClienteHelper.getCliente().getNfe_email_dois().trim().isEmpty())
            edtEmail2.setText(ClienteHelper.getCliente().getNfe_email_dois());
        if (ClienteHelper.getCliente().getNfe_email_tres() != null && !ClienteHelper.getCliente().getNfe_email_tres().trim().isEmpty())
            edtEmail3.setText(ClienteHelper.getCliente().getNfe_email_tres());
        if (ClienteHelper.getCliente().getNfe_email_quatro() != null && !ClienteHelper.getCliente().getNfe_email_quatro().trim().isEmpty())
            edtEmail4.setText(ClienteHelper.getCliente().getNfe_email_quatro());
        if (ClienteHelper.getCliente().getNfe_email_cinco() != null && !ClienteHelper.getCliente().getNfe_email_cinco().trim().isEmpty())
            edtEmail5.setText(ClienteHelper.getCliente().getNfe_email_cinco());

        ClienteHelper.setCadastroCliente7(this);
        return view;
    }

    public void inserirDadosDaFrame() {
        if (rdSim.isChecked()) {
            ClienteHelper.getCliente().setNfe_email_enviar("S");
        } else if (rdNao.isChecked()) {
            ClienteHelper.getCliente().setNfe_email_enviar("N");
        }

        if (!edtEmail4.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setNfe_email_quatro(edtEmail4.getText().toString());
        else
            ClienteHelper.getCliente().setNfe_email_quatro(null);

        if (!edtEmail1.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setNfe_email_um(edtEmail1.getText().toString());
        else
            ClienteHelper.getCliente().setNfe_email_um(null);

        if (!edtEmail5.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setNfe_email_cinco(edtEmail5.getText().toString());
        else
            ClienteHelper.getCliente().setNfe_email_cinco(null);

        if (!edtEmail3.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setNfe_email_tres(edtEmail3.getText().toString());
        else
            ClienteHelper.getCliente().setNfe_email_tres(null);

        if (!edtEmail2.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setNfe_email_dois(edtEmail2.getText().toString());
        else
            ClienteHelper.getCliente().setNfe_email_dois(null);

    }
}
