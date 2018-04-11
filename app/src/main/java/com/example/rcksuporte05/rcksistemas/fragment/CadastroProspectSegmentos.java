package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.SegmentoAdapter;
import com.example.rcksuporte05.rcksistemas.model.Segmento;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 26/01/2018.
 */

public class CadastroProspectSegmentos extends Fragment implements SegmentoAdapter.SegmentoListener{

    @BindView(R.id.recyclerSegmentos)
    RecyclerView recyclerSegmentos;
    @BindView(R.id.edtOutrosSegmentosProspect)
    public EditText edtOutrosSegmentosProspect;

    View view;
    SegmentoAdapter segmentoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cadastro_prospect_segmentos, container, false);
        ButterKnife.bind(this, view);

        recyclerSegmentos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerSegmentos.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));


        preencheRecycler();
        insereDadosNaTela();

        if (ProspectHelper.getProspect().getProspectSalvo() != null && ProspectHelper.getProspect().getProspectSalvo().equals("S")) {
            recyclerSegmentos.setClickable(false);
            edtOutrosSegmentosProspect.setFocusable(false);

        }


        ProspectHelper.setCadastroProspectSegmentos(this);
        return view;
    }


    public void insereDadosNaTela(){

        if(ProspectHelper.getProspect().getSegmento() != null){
            segmentoAdapter.marcarSelecionado(ProspectHelper.getProspect().getSegmento());
            if(ProspectHelper.getProspect().getSegmento().getDescricaoOutros() != null && !ProspectHelper.getProspect().getSegmento().getDescricaoOutros().equals("")){
                edtOutrosSegmentosProspect.setText(ProspectHelper.getProspect().getSegmento().getDescricaoOutros());
            }
        }

    }

    public void insereDadosDaFrame(){
        Segmento segmento = segmentoAdapter.getItemSelecionado();

        if(segmento != null){
            ProspectHelper.getProspect().setSegmento(segmento);
            if(edtOutrosSegmentosProspect.getText() != null){
                ProspectHelper.getProspect().getSegmento().setDescricaoOutros(edtOutrosSegmentosProspect.getText().toString());
            }
        }

    }


    public void preencheRecycler() {
        if (ProspectHelper.getProspect().getProspectSalvo() != null && ProspectHelper.getProspect().getProspectSalvo().equals("S")) {
            segmentoAdapter = new SegmentoAdapter(ProspectHelper.getSegmentos(), new SegmentoAdapter.SegmentoListener() {
                @Override
                public void onClick(int position) {

                }
            });
        } else {
            segmentoAdapter = new SegmentoAdapter(ProspectHelper.getSegmentos(), this);
        }
        recyclerSegmentos.setAdapter(segmentoAdapter);
        segmentoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
       if(segmentoAdapter.getItem(position).getNomeSetor().toLowerCase().contains("outros")){
          edtOutrosSegmentosProspect.setEnabled(true);
          edtOutrosSegmentosProspect.requestFocus();
       }else {
           edtOutrosSegmentosProspect.setText("");
           edtOutrosSegmentosProspect.setEnabled(false);
       }
       segmentoAdapter.toggleSelection(position);
       segmentoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        ProspectHelper.setCadastroProspectSegmentos(this);
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        insereDadosDaFrame();
        super.onDestroyView();
    }
}
