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
import com.example.rcksuporte05.rcksistemas.classes.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.List;

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
            MotivoNaoCadastramento motivo = ProspectHelper.getProspect().getMotivoNaoCadastramento();
            motivoAdapter.marcarSelecionado(motivo);
        }

        if(ProspectHelper.getProspect().getObservacoesComerciais() != null){
            edtOutrosMotivosProspect.setText(ProspectHelper.getProspect().getObservacoesComerciais());
        }

    }


    public void insereDadosDaFrame(){
        List<MotivoNaoCadastramento> motivoSelecionado;
        motivoSelecionado = motivoAdapter.getItensSelecionados();
        MotivoNaoCadastramento motivo = new MotivoNaoCadastramento();

        if(motivoSelecionado.size() > 0){
             motivo = motivoSelecionado.get(0);
        }

        if(edtOutrosMotivosProspect.getText() !=null){
            motivo.setDescricaoOutros(edtOutrosMotivosProspect.getText().toString());
        }

        ProspectHelper.getProspect().setMotivoNaoCadastramento(motivo);
    }

    private void preencheRecycler() {
        motivoAdapter = new MotivoAdapter(ProspectHelper.getMotivos(), this);
        recyclerMotivos.setAdapter(motivoAdapter);
        motivoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
        motivoAdapter.toggleSelection(position);
        motivoAdapter.notifyDataSetChanged();
    }


}
