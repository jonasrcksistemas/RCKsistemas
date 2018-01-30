package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 25/01/2018.
 */


public class CadastroProspectGeral extends Fragment {


    @BindView(R.id.edtNomeClienteProspect)
    EditText edtNomeClienteProspect;

    @BindView(R.id.edtNomeFantasiaProspect)
    EditText edtNomeFantasiaProspect;

    @BindView(R.id.edtCpfCnpjProspect)
    EditText edtCpfCnpjProspect;

    @BindView(R.id.edtInscEstadualProspect)
    EditText edtInscEstadualProspect;

    @BindView(R.id.edtInscMunicipalProspect)
    EditText edtInscMunicipalProspect;

    @BindView(R.id.rdPessoaProspect)
    RadioGroup rgPessoa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_prospect_geral, container, false);
        ButterKnife.bind(this, view);




        return view;
    }
}
