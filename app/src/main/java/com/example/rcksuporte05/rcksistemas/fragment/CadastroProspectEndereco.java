package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
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

    @BindView(R.id.spUfProspect)
    Spinner spUfProspect;

    ArrayAdapter municipioAdapter;
    ArrayAdapter ufAdapter;
    ArrayAdapter<Pais> paisAdapter;
    View view;
    RadioButton rdSituacaoPredio;
    int[] listaUf = {R.array.AC,
            R.array.AL,
            R.array.AM,
            R.array.AP,
            R.array.BA,
            R.array.CE,
            R.array.DF,
            R.array.ES,
            R.array.EX,
            R.array.GO,
            R.array.MA,
            R.array.MG,
            R.array.MS,
            R.array.MT,
            R.array.PA,
            R.array.PB,
            R.array.PE,
            R.array.PI,
            R.array.PR,
            R.array.RJ,
            R.array.RN,
            R.array.RO,
            R.array.RR,
            R.array.RS,
            R.array.SC,
            R.array.SE,
            R.array.SP,
            R.array.TO};
    private String[] UF = getResources().getStringArray(R.array.uf);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cadastro_prospect_ederecos, container, false);
        ButterKnife.bind(this, view);

        ufAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, UF);
        spUfProspect.setAdapter(ufAdapter);

        spUfProspect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[position]));
                spMunicipioProspect.setAdapter(municipioAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(ProspectHelper.getPaises().size() > 0){
            paisAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, ProspectHelper.getPaises());
            spPaisProspect.setAdapter(paisAdapter);
            spPaisProspect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 2) {
                        spUfProspect.setSelection(8);
                    } else {
                        spUfProspect.setSelection(12);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
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
        } else {
            System.out.println("Fazer algo para obrigar!");
        }

        ProspectHelper.getProspect().setId_pais(paisAdapter.getItem(spPaisProspect.getSelectedItemPosition()).getId_pais());
        ProspectHelper.getProspect().setEndereco_uf(UF[spUfProspect.getSelectedItemPosition()]);
        ProspectHelper.getProspect().setNome_municipio(municipioAdapter.getItem(spMunicipioProspect.getSelectedItemPosition()).toString());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
