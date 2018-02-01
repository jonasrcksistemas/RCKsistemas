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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 29/01/2018.
 */

public class CadastroProspectObservacoesComerciais extends Fragment {

    @BindView(R.id.edtObservacaoComercial)
    EditText edtObservacaoComercial;

    @BindView(R.id.edtLimiteCreditoSugerido)
    EditText edtLimiteCreditoSugerido;

    @BindView(R.id.edtLimiteDePrazoSugerido)
    EditText edtLimiteDePrazoSugerido;

    @BindView(R.id.edtBancoProspect)
    EditText edtBancoProspect;

    @BindView(R.id.edtContaCorrenteProspect)
    EditText edtContaCorrenteProspect;

    @BindView(R.id.edtAgenciaProspect)
    EditText edtAgenciaProspect;



        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_cadastro_prospect_observacao_comercial, container, false);
            ButterKnife.bind(this, view);
            injetaDadosNaTela();

            ProspectHelper.setCadastroProspectObservacoesComerciais(this);
            return view;
        }

        public void injetaDadosNaTela(){

            if(ProspectHelper.getProspect().getObservacoesComerciais() != null){
                edtObservacaoComercial.setText(ProspectHelper.getProspect().getObservacoesComerciais());
            }

            if(ProspectHelper.getProspect().getLimiteDeCreditoSugerido() != null){
                edtLimiteCreditoSugerido.setText(ProspectHelper.getProspect().getLimiteDeCreditoSugerido());
            }

            if(ProspectHelper.getProspect().getLimiteDePrazoSugerido() != null){
                edtLimiteDePrazoSugerido.setText(ProspectHelper.getProspect().getLimiteDePrazoSugerido());
            }

            if(ProspectHelper.getProspect().getNomeBanco() != null){
                edtBancoProspect.setText(ProspectHelper.getProspect().getNomeBanco());
            }

            if(ProspectHelper.getProspect().getContaCorrente() != null){
                edtContaCorrenteProspect.setText(ProspectHelper.getProspect().getContaCorrente());
            }

            if(ProspectHelper.getProspect().getAgencia() != null){
                edtAgenciaProspect.setText(ProspectHelper.getProspect().getAgencia());
            }

        }


        public void insereDadosDaFrame(){
            if(edtObservacaoComercial.getText() != null){
                ProspectHelper.getProspect().setObservacoesComerciais(edtObservacaoComercial.getText().toString());
            }
            if(edtLimiteCreditoSugerido.getText() != null){
                ProspectHelper.getProspect().setLimiteDeCreditoSugerido(edtLimiteCreditoSugerido.getText().toString());
            }
            if(edtLimiteDePrazoSugerido.getText() != null){
                ProspectHelper.getProspect().setLimiteDePrazoSugerido(edtLimiteDePrazoSugerido.getText().toString());
            }
            if(edtBancoProspect.getText() != null){
                ProspectHelper.getProspect().setNomeBanco(edtBancoProspect.getText().toString());
            }
            if(edtContaCorrenteProspect.getText() != null){
                ProspectHelper.getProspect().setContaCorrente(edtContaCorrenteProspect.getText().toString());
            }
            if(edtAgenciaProspect.getText() != null){
                ProspectHelper.getProspect().setAgencia(edtAgenciaProspect.getText().toString());
            }


        }


}
