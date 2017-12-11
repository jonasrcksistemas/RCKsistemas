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

public class ProdutoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @BindView(R.id.nomeListaProduto)
    public TextView nomeListaProduto;
    @BindView(R.id.precoProduto)
    public TextView precoProduto;
    @BindView(R.id.txtUnidadeListaProduto)
    public TextView textUN;



    public ProdutoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onClick(View v) {

    }
}
