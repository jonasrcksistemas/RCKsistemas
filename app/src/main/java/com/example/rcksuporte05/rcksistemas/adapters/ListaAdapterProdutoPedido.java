package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProdutoPedidoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;

import java.util.ArrayList;
import java.util.List;

public class ListaAdapterProdutoPedido extends RecyclerView.Adapter<ProdutoPedidoViewHolder> {
    private static int currentSelectedIndex = -1;
    private List<WebPedidoItens> lista;
    private ProdutoPedidoAdapterListener listener;
    private SparseBooleanArray selectedItems;

    public ListaAdapterProdutoPedido(List<WebPedidoItens> lista, ProdutoPedidoAdapterListener listener) {
        this.listener = listener;
        this.lista = lista;
        this.selectedItems = new SparseBooleanArray();
    }

    @Override
    public ProdutoPedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produto_pedido_lista, parent, false);

        return new ProdutoPedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProdutoPedidoViewHolder holder, int position) {
        holder.nomeListaProduto.setText(lista.get(position).getNome_produto());
        Float valorProduto;
        if (lista.get(position).getValor_desconto_per() != null && !lista.get(position).getValor_desconto_per().trim().isEmpty() && Float.parseFloat(lista.get(position).getValor_desconto_per()) > 0)
            valorProduto = Float.parseFloat(lista.get(position).getValor_total()) / Float.parseFloat(lista.get(0).getQuantidade());
        else
            valorProduto = lista.get(position).getValor_unitario();

        holder.precoProduto.setText(String.format("%.2f", Float.parseFloat(lista.get(position).getQuantidade())) + " x " + String.format("R$%.2f", valorProduto) + " = " + String.format("R$%.2f", Float.parseFloat(lista.get(position).getValor_total())));

        holder.textViewUnidadeMedida.setText(lista.get(position).getDescricao());
        holder.idPosition.setText(String.valueOf(position + 1));

        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);

        applyClickEvents(holder, position);
    }

    @Override
    public int getItemCount() {
        if (lista != null)
            return lista.size();

        return 0;
    }

    private void applyClickEvents(final ProdutoPedidoViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRowClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongRowClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return false;
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

    public WebPedidoItens getItem(int position) {
        return lista.get(position);
    }

    public List<WebPedidoItens> getItensSelecionados() {
        List<WebPedidoItens> webPedidoItensSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            webPedidoItensSelecionados.add(lista.get(selectedItems.keyAt(i)));
        }

        return webPedidoItensSelecionados;
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void getList(List<WebPedidoItens> webPedidoItens) {
        lista = webPedidoItens;
    }

    public interface ProdutoPedidoAdapterListener {

        void onRowClicked(int position);

        void onLongRowClicked(int position);
    }
}
