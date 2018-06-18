package com.example.rcksuporte05.rcksistemas.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityAdicionaContato;
import com.example.rcksuporte05.rcksistemas.adapters.ListaContatoAdapter;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 26/01/2018.
 */

public class CadastroCliente4 extends Fragment implements ListaContatoAdapter.ListaContatoListener {

    @BindView(R.id.recyclerContatos)
    RecyclerView recyclerContatos;

    @BindView(R.id.btnAddContato)
    FloatingActionButton btnAddContato;

    ListaContatoAdapter listaContatoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente4, container, false);
        ButterKnife.bind(this, view);

        recyclerContatos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerContatos.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        preencheListaContatos();

        if (ClienteHelper.getCliente().getId_cadastro_servidor() > 0) {
            btnAddContato.setVisibility(View.GONE);
        }

        ClienteHelper.setCadastroCliente4(this);
        return view;
    }

    private void preencheListaContatos() {
        listaContatoAdapter = new ListaContatoAdapter(ClienteHelper.getCliente().getListaContato(), this);
        recyclerContatos.setAdapter(listaContatoAdapter);
        listaContatoAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btnAddContato)
    public void abrirTela() {
        Intent intent = new Intent(getContext(), ActivityAdicionaContato.class);
        intent.putExtra("visualizacao", 1);
        intent.putExtra("cliente", 1);
        startActivity(intent);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getContext(), ActivityAdicionaContato.class);
        intent.putExtra("contato", position);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        preencheListaContatos();
        super.onResume();
    }
}
