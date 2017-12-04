package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 01/12/2017.
 */

public class PedidoViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.txtIdPedido)
    public TextView txtIdPedido;
    @BindView(R.id.txtNomeCliente)
    public TextView txtNomeCliente;
    @BindView(R.id.txtPrecoPedido)
    public TextView txtPrecoPedido;
    @BindView(R.id.txtDataEntrega)
    public TextView txtDataEntrega;
    @BindView(R.id.txtDataEmissaoPedido)
    public TextView txtDataEmissaoPedido;
    @BindView(R.id.viewCor)
    public View viewCor;



    public PedidoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
