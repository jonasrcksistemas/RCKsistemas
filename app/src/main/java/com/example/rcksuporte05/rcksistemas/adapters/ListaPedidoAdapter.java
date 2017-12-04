package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.PedidoViewHolder;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCK 03 on 01/12/2017.
 */

public class ListaPedidoAdapter extends RecyclerView.Adapter<PedidoViewHolder> {
    private List<WebPedido> pedidos = new ArrayList<>();


    public ListaPedidoAdapter(List<WebPedido> pedidos) {
        this.pedidos = pedidos;
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
        holder.txtPrecoPedido.setText(String.format("R$ %.2f", Float.parseFloat(pedidos.get(position).getValor_total())));

        try {

            holder.txtDataEmissaoPedido.setText(new SimpleDateFormat("dd/MM/dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(pedidos.get(position).getData_prev_entrega())));

            holder.txtDataEntrega.setText(new SimpleDateFormat("dd/MM/dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(pedidos.get(position).getData_prev_entrega())));
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.viewCor.setBackgroundColor(Color.parseColor(pedidos.get(position).getPontos_cor()));



    }

    public WebPedido getItem(int position){
        return pedidos.get(position);
    }


    @Override
    public int getItemCount() {
        return pedidos.size();
    }
}
