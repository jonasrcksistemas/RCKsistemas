package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterProdutoPedido;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 30/11/2017.
 */

public class ProdutoPedidoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    @BindView(R.id.idPosition)
    public TextView idPosition;
    @BindView(R.id.nomeListaProduto)
    public TextView nomeListaProduto;
    @BindView(R.id.precoProduto)
    public TextView precoProduto;
    @BindView(R.id.textViewUnidadeMedida)
    public TextView textViewUnidadeMedida;
    @BindView(R.id.txtDesconto)
    public TextView txtDesconto;
    @BindView(R.id.rlProdutoPedido)
    RelativeLayout rlProdutoPedido;

    ListaAdapterProdutoPedido.ProdutoPedidoAdapterListener listener;

    public ProdutoPedidoViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View view) {
        listener.onRowClicked(getAdapterPosition());
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
    }

    @Override
    public boolean onLongClick(View view) {
        listener.onLongRowClicked(getAdapterPosition());
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
        return true;
    }
}
