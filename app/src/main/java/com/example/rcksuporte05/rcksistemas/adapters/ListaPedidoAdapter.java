package com.example.rcksuporte05.rcksistemas.adapters;

import android.app.Activity;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.PedidoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCK 03 on 01/12/2017.
 */

public class ListaPedidoAdapter extends RecyclerView.Adapter<PedidoViewHolder> {
    private List<WebPedido> pedidos;
    private PedidoAdapterListener listener;
    private SparseBooleanArray selectedItems;
    private Activity activity;

    public ListaPedidoAdapter(List<WebPedido> pedidos, PedidoAdapterListener listener, Activity activity) {
        this.pedidos = pedidos;
        this.selectedItems = new SparseBooleanArray();
        this.listener = listener;
        this.activity = activity;
    }

    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pedido_lista, parent, false);

        return new PedidoViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(PedidoViewHolder holder, int position) {
        if (pedidos.get(position).getId_web_pedido_servidor() != null && !pedidos.get(position).getId_web_pedido_servidor().equals("")) {
            holder.txtIdPedido.setText("Nº " + MascaraUtil.numeroZeros(pedidos.get(position).getId_web_pedido_servidor(), 5));
            holder.lyExcluir.setVisibility(View.GONE);
            holder.lyEnvia.setVisibility(View.GONE);
            holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_enviado));
        } else {
            holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_pendente));
            holder.txtIdPedido.setText("Nº " + MascaraUtil.numeroZeros(pedidos.get(position).getId_web_pedido(), 5));
        }

        if (pedidos.get(position).getFinalizado() != null && !pedidos.get(position).getFinalizado().equals("S")) {
            holder.lyEnvia.setVisibility(View.GONE);
            holder.lyCompartilhar.setVisibility(View.GONE);
        }

        holder.txtNomeCliente.setText(pedidos.get(position).getCadastro().getNome_cadastro());
        holder.txtFantasiaCliente.setText(pedidos.get(position).getCadastro().getNome_fantasia());

        if (pedidos.get(position).getValor_total() != null)
            holder.txtPrecoPedido.setText(MascaraUtil.mascaraReal(pedidos.get(position).getValor_total()));
        else
            holder.txtPrecoPedido.setText("");

        try {
            holder.txtDataEmissaoPedido.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(pedidos.get(position).getData_emissao())));

            holder.txtDataEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(pedidos.get(position).getData_prev_entrega())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#5800a387")
                        : Color.TRANSPARENT);

        DBHelper db = new DBHelper(activity);
        try {
            holder.txtOperacao.setText(db.consulta("SELECT NOME_OPERACAO FROM TBL_OPERACAO_ESTOQUE WHERE ID_OPERACAO = " + pedidos.get(position).getId_operacao() + ";", "NOME_OPERACAO"));
        } catch (CursorIndexOutOfBoundsException e) {
            holder.txtOperacao.setText("OPERAÇÃO NÃO DISPONIVEL");
            e.printStackTrace();
        }
        if (!pedidos.get(position).getId_condicao_pagamento().equals("0"))
            holder.txtCondicaoPagamento.setText(db.consulta("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + pedidos.get(position).getId_condicao_pagamento() + ";", "NOME_CONDICAO"));
        else
            holder.txtCondicaoPagamento.setText("");

        if (pedidos.get(position).getFinalizado().equals("N")) {
            holder.abandonado.setVisibility(View.VISIBLE);
            holder.txtPrecoPedido.setVisibility(View.INVISIBLE);
            holder.lyEntrega.setVisibility(View.INVISIBLE);
        } else {
            holder.abandonado.setVisibility(View.INVISIBLE);
            holder.txtPrecoPedido.setVisibility(View.VISIBLE);
            holder.lyEntrega.setVisibility(View.VISIBLE);
        }

        if (pedidos.get(position).getId_operacao().equals("66")) {
            holder.imCampanha.setVisibility(View.VISIBLE);
            holder.cor.setBackground(activity.getDrawable(R.drawable.cor_pedido_bonus));
            holder.txtCondicaoPagamento.setVisibility(View.GONE);
            holder.lyDuplic.setVisibility(View.GONE);
        } else {
            holder.imCampanha.setVisibility(View.GONE);
            holder.txtCondicaoPagamento.setVisibility(View.VISIBLE);
            holder.lyDuplic.setVisibility(View.VISIBLE);
        }

        holder.itemView.setActivated(selectedItems.get(position, false));

        holder.btnExcluir.setOnClickListener(listener.onClickExcluir(position));
        holder.btnEnviar.setOnClickListener(listener.onClickEnviar(position));
        holder.btnDuplic.setOnClickListener(listener.onClickDuplic(position));
        holder.btnCompartilhar.setOnClickListener(listener.onClickCompartilhar(position));
        holder.btnRastreio.setOnClickListener(listener.onClickRastrear(position));

        applyClickEvents(holder, position);
    }

    private void applyClickEvents(PedidoViewHolder holder, final int position) {
        holder.itemListaPedido.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onRowLongClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });

        holder.itemListaPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPedidoRowClicked(position);
            }
        });

    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);

        } else {
            selectedItems.put(pos, true);

        }
        notifyItemChanged(pos);
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public WebPedido getItem(int position) {
        return pedidos.get(position);
    }

    public List<WebPedido> getItensSelecionados() {
        List<WebPedido> pedidosSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            pedidosSelecionados.add(pedidos.get(selectedItems.keyAt(i)));
        }

        return pedidosSelecionados;
    }

    public void remove(WebPedido pedido) {
        pedidos.remove(pedido);
        notifyDataSetChanged();
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }


    public interface PedidoAdapterListener {
        void onPedidoRowClicked(int position);

        void onRowLongClicked(int position);

        View.OnClickListener onClickExcluir(int position);

        View.OnClickListener onClickEnviar(int position);

        View.OnClickListener onClickDuplic(int position);

        View.OnClickListener onClickCompartilhar(int position);

        View.OnClickListener onClickRastrear(int position);
    }
}
