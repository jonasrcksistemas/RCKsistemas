package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Produto;

import java.util.List;

public class ListaAdapterProdutos extends ArrayAdapter<Produto> {

    private Context context;
    private List<Produto> lista;

    public ListaAdapterProdutos(Context context, List<Produto> lista) {
        super(context, 0, lista);

        this.context = context;
        this.lista = lista;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Produto itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.produto_lista, null);

        TextView nomeListaProduto = (TextView) convertView.findViewById(R.id.nomeListaProduto);
        TextView precoProduto = (TextView) convertView.findViewById(R.id.precoProduto);
        TextView textViewUnidadeMedida = (TextView) convertView.findViewById(R.id.textViewUnidadeMedida);

        if (itemPosicao.getAtivo().equalsIgnoreCase("N")) {
            nomeListaProduto.setTextColor(Color.parseColor("#78909c"));
            textViewUnidadeMedida.setTextColor(Color.parseColor("#78909c"));
            precoProduto.setTextColor(Color.parseColor("#78909c"));
        }
        nomeListaProduto.setText(itemPosicao.getNome_produto());

        String preco = null;
        try {
            preco = String.format("%.2f", Float.parseFloat(itemPosicao.getVenda_preco()));
        } catch (Exception e) {
            System.out.println("Produto sem preço");
        }

        if (!itemPosicao.getVenda_preco().equalsIgnoreCase(" ")) {
            precoProduto.setText("R$" + preco);
        } else {
            precoProduto.setText(itemPosicao.getVenda_preco());
        }

        textViewUnidadeMedida.setText(itemPosicao.getDescricao());

        if (position % 2 != 0) {
            convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        System.gc();

        return convertView;
    }
}
