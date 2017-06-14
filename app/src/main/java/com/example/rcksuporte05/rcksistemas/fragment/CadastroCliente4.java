package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;

public class CadastroCliente4 extends Fragment {
    private TextView txtEnviaNFE;
    private RadioGroup radioGroup;
    private RadioButton rdSim;
    private RadioButton rdNao;
    private TextView txtEmail2;
    private TextView txtEmail3;
    private TextView txtEmail4;
    private EditText edtEmail4;
    private TextView txtEmail5;
    private TextView txtEmail1;
    private EditText edtEmail1;
    private EditText edtEmail5;
    private EditText edtEmail3;
    private EditText edtEmail2;
    private String[] cliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente4, container, false);

        txtEnviaNFE = (TextView) view.findViewById(R.id.txtEnviaNFE);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        rdSim = (RadioButton) view.findViewById(R.id.rdSim);
        rdNao = (RadioButton) view.findViewById(R.id.rdNao);
        txtEmail2 = (TextView) view.findViewById(R.id.txtEmail2);
        txtEmail3 = (TextView) view.findViewById(R.id.txtEmail3);
        txtEmail4 = (TextView) view.findViewById(R.id.txtEmail4);
        edtEmail4 = (EditText) view.findViewById(R.id.edtEmail4);
        txtEmail5 = (TextView) view.findViewById(R.id.txtEmail5);
        txtEmail1 = (TextView) view.findViewById(R.id.txtEmail1);
        edtEmail1 = (EditText) view.findViewById(R.id.edtEmail1);
        edtEmail5 = (EditText) view.findViewById(R.id.edtEmail5);
        edtEmail3 = (EditText) view.findViewById(R.id.edtEmail3);
        edtEmail2 = (EditText) view.findViewById(R.id.edtEmail2);

        Bundle bundle = getArguments();

        if (bundle.getInt("cliente") > 0) {
            cliente = bundle.getStringArray("clienteListar");

            rdSim.setClickable(false);
            rdNao.setClickable(false);
            edtEmail4.setFocusable(false);
            edtEmail1.setFocusable(false);
            edtEmail5.setFocusable(false);
            edtEmail3.setFocusable(false);
            edtEmail2.setFocusable(false);

            if (cliente[61].equals("S")) {
                rdSim.setChecked(true);
            } else if (cliente[61].equals("N")) {
                rdNao.setChecked(true);
            }

            edtEmail1.setText(cliente[62]);
            edtEmail2.setText(cliente[63]);
            edtEmail3.setText(cliente[64]);
            edtEmail4.setText(cliente[65]);
            edtEmail5.setText(cliente[66]);
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
