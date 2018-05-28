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
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
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

public class CadastroProspectObservacoesComerciais extends Fragment implements ReferenciaComercialAdapter.ReferenciaComercialListener, ReferenciaBancariaAdapter.ReferenciaBancariaListener {

    @BindView(R.id.edtObservacaoComercial)
    public EditText edtObservacaoComercial;

    @BindView(R.id.edtLimiteDeCreditoSugerido)
    public EditText edtLimiteDeCreditoSugerido;

    @BindView(R.id.edtLimiteDePrazoSugerido)
    public EditText edtLimiteDePrazoSugerido;

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
        View view = inflater.inflate(R.layout.fragment_cadastro_prospect_observacao_comercial, container, false);
        ButterKnife.bind(this, view);

        recyclerReferenciaComercial.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerReferenciaComercial.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        recyclerBancos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerBancos.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        injetaDadosNaTela();

        if (ProspectHelper.getProspect().getProspectSalvo() != null && ProspectHelper.getProspect().getProspectSalvo().equals("S")) {
            edtObservacaoComercial.setFocusable(false);
            edtLimiteDeCreditoSugerido.setFocusable(false);
            edtLimiteDePrazoSugerido.setFocusable(false);
            btnAddReferencia.setVisibility(View.INVISIBLE);
            btnAddBancos.setVisibility(View.INVISIBLE);
        }

        ProspectHelper.setCadastroProspectObservacoesComerciais(this);
        return view;
    }

    @OnClick(R.id.btnAddReferencia)
    public void addReferenciaComercial() {
        insereDadosDaFrame();
        Intent intent = new Intent(getContext(), ActivityAdicionaReferenciaComercial.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnAddBancos)
    public void addBancos() {
        insereDadosDaFrame();
        Intent intent = new Intent(getContext(), ActivityAdicionaBanco.class);
        startActivity(intent);
    }

    public void injetaDadosNaTela() {

        if (ProspectHelper.getProspect().getObservacoesComerciais() != null) {
            edtObservacaoComercial.setText(ProspectHelper.getProspect().getObservacoesComerciais());
        }

        if (ProspectHelper.getProspect().getLimiteDeCreditoSugerido() != null) {
            edtLimiteDeCreditoSugerido.setText(ProspectHelper.getProspect().getLimiteDeCreditoSugerido());
        }

        if (ProspectHelper.getProspect().getLimiteDePrazoSugerido() != null) {
            edtLimiteDePrazoSugerido.setText(ProspectHelper.getProspect().getLimiteDePrazoSugerido());
        }

        if (ProspectHelper.getProspect().getReferenciasComerciais().size() > 0) {
            referenciaComercialAdapter = new ReferenciaComercialAdapter(ProspectHelper.getProspect().getReferenciasComerciais(), this);
            recyclerReferenciaComercial.setAdapter(referenciaComercialAdapter);
            referenciaComercialAdapter.notifyDataSetChanged();
        }

        if (ProspectHelper.getProspect().getReferenciasBancarias().size() > 0) {
            referenciaBancariaAdapter = new ReferenciaBancariaAdapter(ProspectHelper.getProspect().getReferenciasBancarias(), this);
            recyclerBancos.setAdapter(referenciaBancariaAdapter);
            referenciaBancariaAdapter.notifyDataSetChanged();
        }

    }


    public void insereDadosDaFrame() {
        if (edtObservacaoComercial.getText() != null) {
            if (!edtObservacaoComercial.getText().toString().trim().equals("")) {
                ProspectHelper.getProspect().setObservacoesComerciais(edtObservacaoComercial.getText().toString());
            } else {
                ProspectHelper.getProspect().setObservacoesComerciais(null);
            }
        }
        if (edtLimiteDeCreditoSugerido.getText() != null) {
            if (!edtLimiteDeCreditoSugerido.getText().toString().trim().equals("")) {
                ProspectHelper.getProspect().setLimiteDeCreditoSugerido(edtLimiteDeCreditoSugerido.getText().toString());
            } else {
                ProspectHelper.getProspect().setLimiteDeCreditoSugerido(null);
            }
        }
        if (edtLimiteDePrazoSugerido.getText() != null) {
            if (!edtLimiteDePrazoSugerido.getText().toString().trim().equals("")) {
                ProspectHelper.getProspect().setLimiteDePrazoSugerido(edtLimiteDePrazoSugerido.getText().toString());
            } else {
                ProspectHelper.getProspect().setLimiteDePrazoSugerido(null);
            }
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
//        insereDadosDaFrame();
//        Intent intent = new Intent(getContext(), ActivityAdicionaReferenciaComercial.class);
//        intent.putExtra("edicao", 1);
//        intent.putExtra("position", position);
//        startActivity(intent);
    }
}
