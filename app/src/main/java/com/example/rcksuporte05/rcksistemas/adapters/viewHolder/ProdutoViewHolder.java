package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 30/11/2017.
 */

public class ProdutoViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.nomeListaProduto)
    public TextView nomeListaProduto;
    @BindView(R.id.precoProduto)
    public TextView precoProduto;
    @BindView(R.id.textViewUnidadeMedida)
    public TextView textViewUnidadeMedida;

    public ProdutoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
