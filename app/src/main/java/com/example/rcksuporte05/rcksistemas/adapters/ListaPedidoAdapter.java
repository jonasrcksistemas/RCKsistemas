package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.PedidoViewHolder;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCK 03 on 01/12/2017.
 */

public class ListaPedidoAdapter extends RecyclerView.Adapter<PedidoViewHolder> {
    private List<WebPedido> pedidos = new ArrayList<>();
    private PedidoAdapterListener listener;
    private SparseBooleanArray selectedItems;


    private static int currentSelectedIndex = -1;

    public ListaPedidoAdapter(List<WebPedido> pedidos, PedidoAdapterListener listener) {
        this.pedidos = pedidos;
        this.selectedItems = new SparseBooleanArray();
        this.listener = listener;

    }


    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.pedido_lista, parent, false);

        return new PedidoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PedidoViewHolder holder, int position) {
        if (pedidos.get(position).getId_web_pedido_servidor() != null && !pedidos.get(position).getId_web_pedido_servidor().equals(""))
            holder.txtIdPedido.setText(pedidos.get(position).getId_web_pedido_servidor());
        else
            holder.txtIdPedido.setText(pedidos.get(position).getId_web_pedido());

        holder.txtNomeCliente.setText(pedidos.get(position).getCadastro().getNome_cadastro());
        holder.txtPrecoPedido.setText(MascaraUtil.mascaraReal(pedidos.get(position).getValor_total()));

        try {

            holder.txtDataEmissaoPedido.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(pedidos.get(position).getData_emissao())));

            holder.txtDataEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(pedidos.get(position).getData_prev_entrega())));
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.viewCor.setBackgroundColor(Color.parseColor(pedidos.get(position).getPontos_cor()));

        holder.itemView.setActivated(selectedItems.get(position, false));

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
        currentSelectedIndex = pos;
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

    public WebPedido getItem(int position){
        return pedidos.get(position);
    }

    public List<WebPedido> getItensSelecionados(){
        List<WebPedido> pedidosSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            pedidosSelecionados.add(pedidos.get(selectedItems.keyAt(i)));
        }

        return pedidosSelecionados;
    }

    public void remove(WebPedido pedido){
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
    }
}
