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
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityAdicionaBanco;
import com.example.rcksuporte05.rcksistemas.activity.ActivityAdicionaReferenciaComercial;
import com.example.rcksuporte05.rcksistemas.adapters.ReferenciaBancariaAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.ReferenciaComercialAdapter;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 29/01/2018.
 */

public class CadastroCliente6 extends Fragment implements ReferenciaComercialAdapter.ReferenciaComercialListener, ReferenciaBancariaAdapter.ReferenciaBancariaListener {

    @BindView(R.id.recyclerReferenciaComercial)
    RecyclerView recyclerReferenciaComercial;

    @BindView(R.id.recyclerBancos)
    RecyclerView recyclerBancos;

    @BindView(R.id.btnAddReferencia)
    Button btnAddReferencia;

    @BindView(R.id.btnAddBancos)
    Button btnAddBancos;

    ReferenciaComercialAdapter referenciaComercialAdapter;
    ReferenciaBancariaAdapter referenciaBancariaAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente6, container, false);
        ButterKnife.bind(this, view);

        recyclerReferenciaComercial.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerReferenciaComercial.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        recyclerBancos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerBancos.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        injetaDadosNaTela();

        if (ClienteHelper.getCliente().getId_cadastro_servidor() > 0) {
            btnAddReferencia.setVisibility(View.INVISIBLE);
            btnAddBancos.setVisibility(View.INVISIBLE);
        }

        ClienteHelper.setCadastroCliente6(this);
        return view;
    }

    @OnClick(R.id.btnAddReferencia)
    public void addReferenciaComercial() {
        Intent intent = new Intent(getContext(), ActivityAdicionaReferenciaComercial.class);
        intent.putExtra("cliente", 1);
        startActivity(intent);
    }

    @OnClick(R.id.btnAddBancos)
    public void addBancos() {
        Intent intent = new Intent(getContext(), ActivityAdicionaBanco.class);
        intent.putExtra("cliente", 1);
        startActivity(intent);
    }

    public void injetaDadosNaTela() {

        if (ClienteHelper.getCliente().getReferenciasComerciais().size() > 0) {
            referenciaComercialAdapter = new ReferenciaComercialAdapter(ClienteHelper.getCliente().getReferenciasComerciais(), this);
            recyclerReferenciaComercial.setAdapter(referenciaComercialAdapter);
            referenciaComercialAdapter.notifyDataSetChanged();
        }

        if (ClienteHelper.getCliente().getReferenciasBancarias().size() > 0) {
            referenciaBancariaAdapter = new ReferenciaBancariaAdapter(ClienteHelper.getCliente().getReferenciasBancarias(), this);
            recyclerBancos.setAdapter(referenciaBancariaAdapter);
            referenciaBancariaAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        injetaDadosNaTela();
        super.onResume();
    }

    @Override
    public void onClickBancos(int position) {

    }

    @Override
    public void onClickReferencia(int position) {
//        Intent intent = new Intent(getContext(), ActivityAdicionaReferenciaComercial.class);
//        intent.putExtra("edicao", 1);
//        intent.putExtra("position", position);
//        startActivity(intent);
    }
}
