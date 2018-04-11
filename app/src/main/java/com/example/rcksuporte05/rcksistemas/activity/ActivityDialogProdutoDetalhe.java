package com.example.rcksuporte05.rcksistemas.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.Helper.ProdutoHelper;
import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 09/11/2017.
 */

public class ActivityDialogProdutoDetalhe extends AppCompatActivity {

    @BindView(R.id.toolbarProdutoDetalhe)
    @Nullable
    Toolbar toolbarProduto;

    @BindView(R.id.txtNomeProduto)
    @Nullable
    TextView txtProduto;

    @BindView(R.id.txtUnidadeMedida)
    @Nullable
    TextView txtUnidadeMedida;

    @BindView(R.id.txtValorProduto)
    @Nullable
    TextView txtValorProduto;

    @BindView(R.id.txtCodigo)
    TextView txtCodigo;

    @BindView(R.id.lyValorProduto)
    LinearLayout layoutProduto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_produto_detalhe);
        ButterKnife.bind(this);

        toolbarProduto.setTitle("Produto");
        setSupportActionBar(toolbarProduto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtCodigo.setText("Cod.: "+ProdutoHelper.getProduto().getId_produto());
        txtProduto.setText(ProdutoHelper.getProduto().getNome_produto());
        txtUnidadeMedida.setText(ProdutoHelper.getProduto().getDescricao());


        if (ProdutoHelper.getProduto().getVenda_preco() == null || ProdutoHelper.getProduto().getVenda_preco().trim().equals("")) {
            layoutProduto.setVisibility(View.GONE);
        } else {
            Float valor = Float.parseFloat(ProdutoHelper.getProduto().getVenda_preco());
            txtValorProduto.setText("R$ " + String.format("%.2f", valor).replace(".", ","));
        }


    }



}
