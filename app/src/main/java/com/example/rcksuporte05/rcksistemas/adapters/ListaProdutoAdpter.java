package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProdutoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCK 03 on 30/11/2017.
 */

public class ListaProdutoAdpter extends RecyclerView.Adapter<ProdutoViewHolder> {

    private List<Produto> produtos;
    private ProdutoAdapterListener listener;
    private SparseBooleanArray selectedItems;

    public ListaProdutoAdpter(List<Produto> produtos, ProdutoAdapterListener listener) {
        this.produtos = produtos;
        this.listener = listener;
        this.selectedItems = new SparseBooleanArray();
    }

    @Override
    public ProdutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produto_lista, parent, false);

        return new ProdutoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProdutoViewHolder holder, int position) {

        holder.idProduto.setText(String.valueOf(produtos.get(position).getId_produto()));

        if (produtos.get(position).getCodigo_em_barras() != null && !produtos.get(position).getCodigo_em_barras().equals(""))
            holder.txtCodigoBarra.setText("Codigo EAN: " + produtos.get(position).getCodigo_em_barras());

        holder.nomeListaProduto.setText(produtos.get(position).getNome_produto());

        String preco = null;
        try {
            preco = String.format("%.2f", Float.parseFloat(produtos.get(position).getVenda_preco())).replace(".", ",");
        } catch (Exception e) {
            System.out.println("Produto sem preço");
        }

        if (!produtos.get(position).getVenda_preco().trim().equalsIgnoreCase("")) {
            holder.precoProduto.setText("R$ " + preco);
        } else {
            holder.precoProduto.setText(produtos.get(position).getVenda_preco().replace(".", ","));
        }

        holder.textUN.setText(produtos.get(position).getUnidade());

        if (produtos.get(position).getSaldo_estoque() <= 0) {
            holder.txtCodigoBarra.setTextColor(Color.RED);
            holder.nomeListaProduto.setTextColor(Color.RED);
            holder.precoProduto.setTextColor(Color.RED);
            holder.textUN.setTextColor(Color.RED);
            holder.idProduto.setTextColor(Color.RED);
            holder.txtSaldoEstoque.setTextColor(Color.RED);
        } else {
            holder.txtCodigoBarra.setTextColor(Color.BLACK);
            holder.nomeListaProduto.setTextColor(Color.BLACK);
            holder.precoProduto.setTextColor(Color.BLACK);
            holder.textUN.setTextColor(Color.BLACK);
            holder.idProduto.setTextColor(Color.BLACK);
            holder.txtSaldoEstoque.setTextColor(Color.BLACK);
        }

        if (produtos.get(position).getSaldo_estoque().toString().contains("-"))
            holder.txtSaldoEstoque.setText("Estoque: 00");
        else
            holder.txtSaldoEstoque.setText("Estoque: " + MascaraUtil.mascaraVirgula(produtos.get(position).getSaldo_estoque()).replace(",00", ""));
        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);

        applyCLickEnvents(holder, position);

        System.gc();
    }

    public Produto getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public int getItemCount() {
        if (produtos != null)
            return produtos.size();

        return 0;
    }

    private void applyCLickEnvents(ProdutoViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onClickListener(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    listener.onLongClickListener(position);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyDataSetChanged();
    }

    public int getSelectedItensCount() {
        return selectedItems.size();
    }

    public List<Produto> getItensSelecionados() {
        List<Produto> produtosSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            produtosSelecionados.add(produtos.get(selectedItems.keyAt(i)));
        }
        return produtosSelecionados;
    }

    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public interface ProdutoAdapterListener {
        void onClickListener(int position);

        void onLongClickListener(int position);
    }
}
