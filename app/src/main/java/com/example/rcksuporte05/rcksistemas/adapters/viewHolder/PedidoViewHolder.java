package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 01/12/2017.
 */

public class PedidoViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    @BindView(R.id.edtNomeProduto)
    public TextView txtIdPedido;
    @BindView(R.id.txtNomeCliente)
    public TextView txtNomeCliente;
    @BindView(R.id.txtPrecoPedido)
    public TextView txtPrecoPedido;
    @BindView(R.id.txtDataEntrega)
    public TextView txtDataEntrega;
    @BindView(R.id.txtDataEmissaoPedido)
    public TextView txtDataEmissaoPedido;
    @BindView(R.id.item_lista_pedido)
    public RelativeLayout itemListaPedido;


    ListaPedidoAdapter.PedidoAdapterListener listener;

    public PedidoViewHolder(View itemView) {
        super(itemView);
        itemView.setOnLongClickListener(this);
        ButterKnife.bind(this, itemView);

    }


    @Override
    public boolean onLongClick(View view) {
        listener.onRowLongClicked(getAdapterPosition());
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
        return true;
    }
}
