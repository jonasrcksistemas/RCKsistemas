package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.MotivoAdapter;
import com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 29/01/2018.
 */

public class CadastroProspectMotivos extends Fragment implements MotivoAdapter.MotivoListener{
    @BindView(R.id.recyclerMotivos)
    RecyclerView recyclerMotivos;

    @BindView(R.id.edtOutrosMotivosProspect)
    public EditText edtOutrosMotivosProspect;

    MotivoAdapter motivoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_prospect_motivo, container, false);
        ButterKnife.bind(this,view);
        recyclerMotivos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMotivos.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        preencheRecycler();
        insereDadosNaTela();

        if (ProspectHelper.getProspect().getProspectSalvo() != null && ProspectHelper.getProspect().getProspectSalvo().equals("S")) {
            recyclerMotivos.setClickable(false);
            edtOutrosMotivosProspect.setFocusable(false);
        }

        ProspectHelper.setCadastroProspectMotivos(this);
        return view;
    }

    public void insereDadosNaTela(){
        if(ProspectHelper.getProspect().getMotivoNaoCadastramento() != null){
            motivoAdapter.marcarSelecionado(ProspectHelper.getProspect().getMotivoNaoCadastramento());
            if(ProspectHelper.getProspect().getMotivoNaoCadastramento().getDescricaoOutros() != null){
                edtOutrosMotivosProspect.setText(ProspectHelper.getProspect().getMotivoNaoCadastramento().getDescricaoOutros());
            }
        }


    }


    public void insereDadosDaFrame(){
        MotivoNaoCadastramento motivoSelecionado;
        motivoSelecionado = motivoAdapter.getItemSelecionado();

        if(motivoSelecionado != null){
             ProspectHelper.getProspect().setMotivoNaoCadastramento(motivoSelecionado);
            if(edtOutrosMotivosProspect.getText() !=null){
                ProspectHelper.getProspect().getMotivoNaoCadastramento().setDescricaoOutros(edtOutrosMotivosProspect.getText().toString());
            }
        }


    }

    private void preencheRecycler() {
        if (ProspectHelper.getProspect().getProspectSalvo() != null && ProspectHelper.getProspect().getProspectSalvo().equals("S")) {
            motivoAdapter = new MotivoAdapter(ProspectHelper.getMotivos(), new MotivoAdapter.MotivoListener() {
                @Override
                public void onClick(int position) {

                }
            });
        }else {
            motivoAdapter = new MotivoAdapter(ProspectHelper.getMotivos(), this);
        }
        recyclerMotivos.setAdapter(motivoAdapter);
        motivoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
        if(motivoAdapter.getItem(position).getMotivo().toLowerCase().contains("outros")){
            edtOutrosMotivosProspect.setEnabled(true);
            edtOutrosMotivosProspect.requestFocus();
        }else{
            edtOutrosMotivosProspect.setText("");
            edtOutrosMotivosProspect.setEnabled(false);
        }
        motivoAdapter.toggleSelection(position);
        motivoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        ProspectHelper.setCadastroProspectMotivos(this);
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        insereDadosDaFrame();
        super.onDestroyView();
    }
}
