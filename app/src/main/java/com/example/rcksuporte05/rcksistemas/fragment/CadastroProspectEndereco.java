package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;

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

    @BindView(R.id.edtPaisProspect)
    EditText edtPaisProspect;


    @BindView(R.id.edtUfProspect)
    EditText edtUfProspect;

    @BindView(R.id.edtMunicipioProspect)
    EditText edtMunicipioProspect;

    @BindView(R.id.edtCep)
    EditText edtCep;

    @BindView(R.id.rgSituacaoPredio)
    RadioGroup rgSituacaoPredio;

    View view;
    RadioButton rdSituacaoPredio;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_cadastro_prospect_ederecos, container, false);
        ButterKnife.bind(this, view);

//        injetaDadosNaTela();
        ProspectHelper.setCadastroProspectEndereco(this);
        return view;
    }

//    public void injetaDadosNaTela(){
//
//        if(ProspectHelper.getProspect().getEndereco() != null){
//            edtEnderecoProspect.setText(ProspectHelper.getProspect().getEndereco());
//
//        }
//        if(ProspectHelper.getProspect().getEndereco_numero() != null){
//            edtNumeroProspect.setText(ProspectHelper.getProspect().getEndereco_numero());
//
//        }
//        if(ProspectHelper.getProspect().getEndereco_bairro() != null){
//            edtBairroProspect.setText(ProspectHelper.getProspect().getEndereco_bairro());
//
//        }
//        if(ProspectHelper.getProspect().get getNome_pais() != null){
//            edtPaisProspect.setText(ProspectHelper.getProspect().getNome_pais());
//
//        }
//        if(ProspectHelper.getProspect().getEndereco_bairro() != null){
//            edtUfProspect.setText(ProspectHelper.getProspect().getEndereco_bairro());
//
//        }
//        if(ProspectHelper.getProspect().getEndereco_municipio() != null){
//            edtMunicipioProspect.setText(ProspectHelper.getProspect().getEndereco_municipio());
//
//        }
//        if(ProspectHelper.getProspect().getEndereco_cep() != null){
//            edtCep.setText(ProspectHelper.getProspect().getEndereco_cep());
//
//        }
//        if(ProspectHelper.getProspect().getSituaçãoPredio() != null){
//
//        }
//    }
//
//
//    public void inserirDadosDaFrame(){
//        if(edtEnderecoProspect.getText() != null && !edtEnderecoProspect.getText().toString().equals("")){
//            ProspectHelper.getProspect().setEndereco(edtEnderecoProspect.getText().toString());
//        }else{
//            System.out.println("Fazer algo para obrigar!");
//        }
//
//        if(edtNumeroProspect.getText() != null && !edtNumeroProspect.getText().toString().equals("")){
//            ProspectHelper.getProspect().setEndereco_numero(edtNumeroProspect.getText().toString());
//        }else{
//            System.out.println("Fazer algo para obrigar!");
//        }
//
//        if(edtBairroProspect.getText() != null && !edtBairroProspect.getText().toString().equals("")){
//            ProspectHelper.getProspect().setEndereco_bairro(edtBairroProspect.getText().toString());
//        }else{
//            System.out.println("Fazer algo para obrigar!");
//        }
//
//        if(edtPaisProspect.getText() != null && !edtPaisProspect.getText().toString().equals("")){
//            ProspectHelper.getProspect().setNome_pais(edtPaisProspect.getText().toString());
//        }else{
//            System.out.println("Fazer algo para obrigar!");
//        }
//
//        if(edtUfProspect.getText() != null && !edtUfProspect.getText().toString().equals("")){
//            ProspectHelper.getProspect().setEndereco_bairro(edtUfProspect.getText().toString());
//        }else{
//            System.out.println("Fazer algo para obrigar!");
//        }
//
//        if(edtMunicipioProspect.getText() != null && !edtMunicipioProspect.getText().toString().equals("")){
//            ProspectHelper.getProspect().setEndereco_municipio(edtMunicipioProspect.getText().toString());
//        }else{
//            System.out.println("Fazer algo para obrigar!");
//        }
//
//        if(edtCep.getText() != null && !edtCep.getText().toString().equals("")){
//            ProspectHelper.getProspect().setEndereco_cep(edtCep.getText().toString());
//        }else{
//            System.out.println("Fazer algo para obrigar!");
//        }
//
//        if(rgSituacaoPredio.getCheckedRadioButtonId() > 0){
//            rdSituacaoPredio = (RadioButton) view.findViewById(rgSituacaoPredio.getCheckedRadioButtonId());
//            ProspectHelper.getProspect().setSituaçãoPredio(rdSituacaoPredio.getText().toString());
//        }
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
