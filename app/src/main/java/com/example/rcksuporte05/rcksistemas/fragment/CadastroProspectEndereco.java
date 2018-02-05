package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Municipio;
import com.example.rcksuporte05.rcksistemas.classes.Pais;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 26/01/2018.
 */

public class CadastroProspectEndereco extends Fragment {

    @BindView(R.id.edtEnderecoProspect)
    EditText edtEnderecoProspect;

    @BindView(R.id.edtNumeroProspect)
    EditText edtNumeroProspect;

    @BindView(R.id.edtBairroProspect)
    EditText edtBairroProspect;

    @BindView(R.id.edtCep)
    EditText edtCep;

    @BindView(R.id.rgSituacaoPredio)
    RadioGroup rgSituacaoPredio;

    @BindView(R.id.spMunicipioProspect)
    Spinner spMunicipioProspect;

    @BindView(R.id.spPaisProspect)
    Spinner spPaisProspect;

    ArrayAdapter<Municipio> municipioAdapter;
    ArrayAdapter<Pais> paisAdapter;
    View view;
    RadioButton rdSituacaoPredio;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cadastro_prospect_ederecos, container, false);
        ButterKnife.bind(this, view);

        if(ProspectHelper.getMunicipios().size() > 0){
            municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1,ProspectHelper.getMunicipios());
            spMunicipioProspect.setAdapter(municipioAdapter);
        }

        if(ProspectHelper.getPaises().size() > 0){
            paisAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, ProspectHelper.getPaises());
            spPaisProspect.setAdapter(paisAdapter);
        }

        injetaDadosNaTela();
        ProspectHelper.setCadastroProspectEndereco(this);
        return view;
    }

    public void injetaDadosNaTela(){

        if(ProspectHelper.getProspect().getEndereco() != null){
            edtEnderecoProspect.setText(ProspectHelper.getProspect().getEndereco());
        }

        if(ProspectHelper.getProspect().getEndereco_numero() != null){
            edtNumeroProspect.setText(ProspectHelper.getProspect().getEndereco_numero());
        }

        if(ProspectHelper.getProspect().getEndereco_bairro() != null){
            edtBairroProspect.setText(ProspectHelper.getProspect().getEndereco_bairro());
        }

        if(ProspectHelper.getProspect().getEndereco_cep() != null){
            edtCep.setText(ProspectHelper.getProspect().getEndereco_cep());
        }

    }


    public void inserirDadosDaFrame(){
        if(edtEnderecoProspect.getText() != null && !edtEnderecoProspect.getText().toString().equals("")){
            ProspectHelper.getProspect().setEndereco(edtEnderecoProspect.getText().toString());
        }else{
            System.out.println("Fazer algo para obrigar!");
        }

        if(edtNumeroProspect.getText() != null && !edtNumeroProspect.getText().toString().equals("")){
            ProspectHelper.getProspect().setEndereco_numero(edtNumeroProspect.getText().toString());
        }else{
            System.out.println("Fazer algo para obrigar!");
        }

        if(edtBairroProspect.getText() != null && !edtBairroProspect.getText().toString().equals("")){
            ProspectHelper.getProspect().setEndereco_bairro(edtBairroProspect.getText().toString());
        }else{
            System.out.println("Fazer algo para obrigar!");
        }


        if(edtCep.getText() != null && !edtCep.getText().toString().equals("")){
            ProspectHelper.getProspect().setEndereco_cep(edtCep.getText().toString());
        }else{
            System.out.println("Fazer algo para obrigar!");
        }

        if(rgSituacaoPredio.getCheckedRadioButtonId() > 0){
            rdSituacaoPredio = (RadioButton) view.findViewById(rgSituacaoPredio.getCheckedRadioButtonId());
            ProspectHelper.getProspect().setSituacaoPredio(rdSituacaoPredio.getText().toString());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
