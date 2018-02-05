package com.example.rcksuporte05.rcksistemas.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaContatoAdapter;
import com.example.rcksuporte05.rcksistemas.interfaces.ActivityAdicionaContato;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 26/01/2018.
 */

public class CadastroProspectContatos extends Fragment implements ListaContatoAdapter.ListaContatoListener{

    @BindView(R.id.recyclerContatos)
    RecyclerView recyclerContatos;

    ListaContatoAdapter listaContatoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_prospect_contatos, container, false);
        ButterKnife.bind(this, view);

        recyclerContatos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerContatos.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        preencheListaContatos();

        ProspectHelper.setCadastroProspectContatos(this);
        return view;
    }

    private void preencheListaContatos() {
        listaContatoAdapter = new ListaContatoAdapter(ProspectHelper.getProspect().getListaContato(), this);
        recyclerContatos.setAdapter(listaContatoAdapter);
        listaContatoAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btnAddContato)
    public void abrirTela(){
        Intent intent = new Intent(getContext(), ActivityAdicionaContato.class);
        intent.putExtra("visualizacao", 1);
        startActivity(intent);
    }

    @Override
    public void onClick(int position) {

    }


}
