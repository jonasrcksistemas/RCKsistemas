package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    public LinearLayout itemListaPedido;
    @BindView(R.id.cor)
    public View cor;
    @BindView(R.id.lyExcluir)
    public LinearLayout lyExcluir;
    @BindView(R.id.lyEnvia)
    public LinearLayout lyEnvia;
    @BindView(R.id.lyCompartilhar)
    public LinearLayout lyCompartilhar;
    @BindView(R.id.btnExcluir)
    public Button btnExcluir;
    @BindView(R.id.btnEnviar)
    public Button btnEnviar;
    @BindView(R.id.btnDuplic)
    public Button btnDuplic;
    @BindView(R.id.btnCompartilhar)
    public Button btnCompartilhar;
    @BindView(R.id.btnRastreio)
    public Button btnRastreio;
    @BindView(R.id.txtOperacao)
    public TextView txtOperacao;
    @BindView(R.id.txtCondicaoPagamento)
    public TextView txtCondicaoPagamento;
    @BindView(R.id.abandonado)
    public LinearLayout abandonado;
    @BindView(R.id.lyEntrega)
    public LinearLayout lyEntrega;
    @BindView(R.id.lyDuplic)
    public LinearLayout lyDuplic;
    @BindView(R.id.imCampanha)
    public ImageView imCampanha;

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
