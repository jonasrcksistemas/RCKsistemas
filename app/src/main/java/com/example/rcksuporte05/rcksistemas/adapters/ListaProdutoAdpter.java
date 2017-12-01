package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProdutoViewHolder;
import com.example.rcksuporte05.rcksistemas.classes.Produto;

import java.util.List;

/**
 * Created by RCK 03 on 30/11/2017.
 */

public class ListaProdutoAdpter  extends RecyclerView.Adapter<ProdutoViewHolder> {

    private List<Produto> produtos;

    public ListaProdutoAdpter(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public ProdutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produto_lista, parent, false);

        return new ProdutoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProdutoViewHolder holder, int position) {

        if (produtos.get(position).getAtivo().equalsIgnoreCase("N")) {
            holder.nomeListaProduto.setTextColor(Color.parseColor("#78909c"));
            holder.textViewUnidadeMedida.setTextColor(Color.parseColor("#78909c"));
            holder.precoProduto.setTextColor(Color.parseColor("#78909c"));
        }

        holder.nomeListaProduto.setText(produtos.get(position).getNome_produto());

        String preco = null;
        try {
            preco = String.format("%.2f", Float.parseFloat(produtos.get(position).getVenda_preco())).replace(".", ",");
        } catch (Exception e) {
            System.out.println("Produto sem preço");
        }

        if (!produtos.get(position).getVenda_preco().trim().equalsIgnoreCase("")) {
            holder.precoProduto.setText("R$" + preco);
        } else {
            holder.precoProduto.setText(produtos.get(position).getVenda_preco().replace(".", ","));
        }

        holder.textViewUnidadeMedida.setText(produtos.get(position).getDescricao());

        System.gc();
    }

    public Produto getItem(int position){
        return produtos.get(position);
    }

    @Override
    public int getItemCount() {
        if (produtos != null)
        return produtos.size();

        return 0;
    }
}
