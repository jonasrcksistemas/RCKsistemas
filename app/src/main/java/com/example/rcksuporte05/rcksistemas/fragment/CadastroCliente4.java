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

public class CadastroCliente4 extends Fragment {
    private RadioButton rdSim;
    private RadioButton rdNao;
    private EditText edtEmail4;
    private EditText edtEmail1;
    private EditText edtEmail5;
    private EditText edtEmail3;
    private EditText edtEmail2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente4, container, false);

        rdSim = (RadioButton) view.findViewById(R.id.rdSim);
        rdNao = (RadioButton) view.findViewById(R.id.rdNao);
        edtEmail4 = (EditText) view.findViewById(R.id.edtEmail4);
        edtEmail1 = (EditText) view.findViewById(R.id.edtEmail1);
        edtEmail5 = (EditText) view.findViewById(R.id.edtEmail5);
        edtEmail3 = (EditText) view.findViewById(R.id.edtEmail3);
        edtEmail2 = (EditText) view.findViewById(R.id.edtEmail2);


        if (ClienteHelper.getCliente() != null) {

            rdSim.setClickable(false);
            rdNao.setClickable(false);
            edtEmail4.setFocusable(false);
            edtEmail1.setFocusable(false);
            edtEmail5.setFocusable(false);
            edtEmail3.setFocusable(false);
            edtEmail2.setFocusable(false);

            if (ClienteHelper.getCliente().getNfe_email_enviar() != null) {
                if (ClienteHelper.getCliente().getNfe_email_enviar().equals("S")) {
                    rdSim.setChecked(true);
                } else if (ClienteHelper.getCliente().getNfe_email_enviar().equals("N")) {
                    rdNao.setChecked(true);
                }
            }

            edtEmail1.setText(ClienteHelper.getCliente().getNfe_email_um());
            edtEmail2.setText(ClienteHelper.getCliente().getNfe_email_dois());
            edtEmail3.setText(ClienteHelper.getCliente().getNfe_email_tres());
            edtEmail4.setText(ClienteHelper.getCliente().getNfe_email_quatro());
            edtEmail5.setText(ClienteHelper.getCliente().getNfe_email_cinco());
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
