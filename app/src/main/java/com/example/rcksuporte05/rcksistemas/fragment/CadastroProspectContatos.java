package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Prospect;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 26/01/2018.
 */

public class CadastroProspectContatos extends Fragment {
    Prospect prospect;

    @BindView(R.id.edtResponsavelProspect)
    EditText edtResponsavelProspect;

    @BindView(R.id.edtFuncaoResponsavelProspect)
    EditText edtFuncaoResponsavelProspect;

    @BindView(R.id.edtCelular1Prospect)
    EditText edtCelular1Prospect;

    @BindView(R.id.edtCelular2Prospect)
    EditText edtCelular2Prospect;

    @BindView(R.id.edtTelefone1Prospect)
    EditText edtTelefone1Prospect;

    @BindView(R.id.edtEmailProspect)
    EditText edtEmailProspect;

    @BindView(R.id.edtEmail2Prospect)
    EditText edtEmail2Prospect;

    @BindView(R.id.edtFonecedor1)
    EditText edtFonecedor1;

    @BindView(R.id.edtTelFonecedor1)
    EditText edtTelFonecedor1;

    @BindView(R.id.edtFonecedor2)
    EditText edtFonecedor2;

    @BindView(R.id.edtTelFonecedor2)
    EditText edtTelFonecedor2;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_prospect_contatos, container, false);
        ButterKnife.bind(this, view);

        injetaDadosNaTela();
        ProspectHelper.setCadastroProspectContatos(this);
        return view;
    }


    @OnClick(R.id.btnTesteContato)
    public void teste(){
        prospect = ProspectHelper.getProspect();
        System.out.println("dsdsadas");
    }

    public void injetaDadosNaTela(){
        if(ProspectHelper.getProspect().getNomeResponsavel() != null){
            edtResponsavelProspect.setText(ProspectHelper.getProspect().getNomeResponsavel());
        }
        if(ProspectHelper.getProspect().getFuncaoResponsavel() != null){
            edtFuncaoResponsavelProspect.setText(ProspectHelper.getProspect().getFuncaoResponsavel());
        }
        if(ProspectHelper.getProspect().getCelular1() != null){
            edtCelular1Prospect.setText(ProspectHelper.getProspect().getCelular1());
        }
        if(ProspectHelper.getProspect().getCelular2() != null){
            edtCelular2Prospect.setText(ProspectHelper.getProspect().getCelular2());
        }
        if(ProspectHelper.getProspect().getTelefoneFixo() != null){
            edtTelefone1Prospect.setText(ProspectHelper.getProspect().getTelefoneFixo());
        }
        if(ProspectHelper.getProspect().getEmail1() != null){
            edtEmailProspect.setText(ProspectHelper.getProspect().getEmail1());
        }
        if(ProspectHelper.getProspect().getEmail2() != null){
            edtEmail2Prospect.setText(ProspectHelper.getProspect().getEmail2());
        }
        if(ProspectHelper.getProspect().getFornecedor1() != null){
            edtFonecedor1.setText(ProspectHelper.getProspect().getFornecedor1());
        }
        if(ProspectHelper.getProspect().getTelefoneFornecedor1() != null){
            edtTelFonecedor1.setText(ProspectHelper.getProspect().getTelefoneFornecedor1());
        }
        if(ProspectHelper.getProspect().getTelefoneFornecedor1() != null){
            edtFonecedor2.setText(ProspectHelper.getProspect().getTelefoneFornecedor1());
        }
        if(ProspectHelper.getProspect().getTelefoneFornecedor2() != null){
            edtTelFonecedor2.setText(ProspectHelper.getProspect().getTelefoneFornecedor2());
        }

    }


    public void insereDadosdaFrame(){
        if(edtResponsavelProspect.getText() != null){
            ProspectHelper.getProspect().setNomeResponsavel(edtFuncaoResponsavelProspect.getText().toString());
        }
        if(edtFuncaoResponsavelProspect.getText() != null){
            ProspectHelper.getProspect().setFuncaoResponsavel(edtFuncaoResponsavelProspect.getText().toString());
        }
        if(edtCelular1Prospect.getText() != null){
            ProspectHelper.getProspect().setCelular1(edtCelular1Prospect.getText().toString());
        }
        if(edtCelular2Prospect.getText() != null){
            ProspectHelper.getProspect().setCelular2(edtCelular2Prospect.getText().toString());
        }
        if(edtTelefone1Prospect.getText() != null){
            ProspectHelper.getProspect().setTelefoneFixo(edtTelefone1Prospect.getText().toString());
        }
        if(edtEmailProspect.getText() != null){
            ProspectHelper.getProspect().setEmail1(edtEmailProspect.getText().toString());
        }
        if(edtEmail2Prospect.getText() != null){
            ProspectHelper.getProspect().setEmail2(edtEmail2Prospect.getText().toString());
        }
        if(edtFonecedor1.getText() != null){
            ProspectHelper.getProspect().setFornecedor1(edtFonecedor1.getText().toString());
        }
        if(edtTelFonecedor1.getText() != null){
            ProspectHelper.getProspect().setTelefoneFornecedor1((edtTelFonecedor1.getText().toString()));
        }
        if(edtFonecedor2.getText() != null){
            ProspectHelper.getProspect().setTelefoneFornecedor1(edtFonecedor2.getText().toString());
        }
        if(edtTelFonecedor2.getText() != null){
            ProspectHelper.getProspect().setTelefoneFornecedor2(edtTelFonecedor2.getText().toString());
        }
    }


}
