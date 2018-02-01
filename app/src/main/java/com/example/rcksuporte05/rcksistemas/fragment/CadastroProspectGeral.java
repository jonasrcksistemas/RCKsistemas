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
import butterknife.OnClick;

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

    @BindView(R.id.rgPessoaProspect)
    RadioGroup rgPessoa;

    @BindView(R.id.rgRotaProspect)
    RadioGroup rgRotaProspect;

    View view;
    RadioButton radioButtonRota;
    RadioButton radioButtonPessoa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cadastro_prospect_geral, container, false);
        ButterKnife.bind(this, view);

        injetaDadosNaTela();

        ProspectHelper.setCadastroProspectGeral(this);

        return view;
    }

    @OnClick(R.id.btnTeste)
    public void teste(){
        inserirDadosDaFrame();
    }

    private void injetaDadosNaTela() {
        if(ProspectHelper.getProspect().getPessoa_f_j() != null && ProspectHelper.getProspect().getPessoa_f_j().equals("")){

        }
        if(ProspectHelper.getProspect().getNome_cadastro() != null && ProspectHelper.getProspect().getNome_cadastro().equals("")){
            edtNomeClienteProspect.setText(ProspectHelper.getProspect().getNome_cadastro());
        }

        if(ProspectHelper.getProspect().getNome_fantasia() != null && ProspectHelper.getProspect().getNome_fantasia().equals("")){
            edtNomeFantasiaProspect.setText(ProspectHelper.getProspect().getNome_fantasia());
        }

        if(ProspectHelper.getProspect().getCpf_cnpj() != null && ProspectHelper.getProspect().getCpf_cnpj().equals("")){
            edtCpfCnpjProspect.setText(ProspectHelper.getProspect().getCpf_cnpj());
        }

        if(ProspectHelper.getProspect().getInscri_estadual() != null && ProspectHelper.getProspect().getInscri_estadual().equals("")){
            edtInscEstadualProspect.setText(ProspectHelper.getProspect().getInscri_estadual());
        }

        if(ProspectHelper.getProspect().getInscri_municipal() != null && ProspectHelper.getProspect().getInscri_municipal().equals("")){
            edtInscMunicipalProspect.setText(ProspectHelper.getProspect().getInscri_municipal());
        }


    }


    public void inserirDadosDaFrame(){

        if(rgPessoa.getCheckedRadioButtonId()>0){
            radioButtonPessoa = (RadioButton) view.findViewById(rgPessoa.getCheckedRadioButtonId());
            ProspectHelper.getProspect().setIdRdPessoa(rgPessoa.getCheckedRadioButtonId());
            ProspectHelper.getProspect().setPessoa_f_j(radioButtonPessoa.getText().toString().toLowerCase());
        }

        if(edtNomeClienteProspect.getText() != null && !edtNomeClienteProspect.getText().toString().equals("")){
            ProspectHelper.getProspect().setNome_cadastro(edtNomeClienteProspect.getText().toString());
        }else{
            System.out.println("mensagem por obrigatoriedade");
        }

        if(edtCpfCnpjProspect.getText() != null && !edtCpfCnpjProspect.getText().toString().equals("")){
            ProspectHelper.getProspect().setCpf_cnpj(edtCpfCnpjProspect.getText().toString());
        }else{
            System.out.println("mensagem por obrigatoriedade");
        }

        if(edtNomeFantasiaProspect.getText() != null && !edtNomeFantasiaProspect.getText().toString().equals("")){
            ProspectHelper.getProspect().setNome_fantasia(edtNomeFantasiaProspect.getText().toString());
        }else {
            System.out.println("mensagem por obrigatoriedade");
        }
        if(edtInscEstadualProspect.getText() != null && !edtInscEstadualProspect.getText().toString().equals("")){
            ProspectHelper.getProspect().setInscri_estadual(edtInscEstadualProspect.getText().toString());
        }

        if(edtInscMunicipalProspect.getText() != null && !edtInscMunicipalProspect.getText().toString().equals("")){
            ProspectHelper.getProspect().setInscri_municipal(edtInscMunicipalProspect.getText().toString());
        }

        if(rgRotaProspect.getCheckedRadioButtonId() > 0){
            radioButtonRota = (RadioButton) view.findViewById(rgRotaProspect.getCheckedRadioButtonId());
            ProspectHelper.getProspect().setDiaVisita(radioButtonRota.getText().toString().toLowerCase());
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
