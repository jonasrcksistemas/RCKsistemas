package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.WebPedidoItens;

import java.util.List;

public class ListaAdapterProdutoPedido extends ArrayAdapter<WebPedidoItens> {
    private Context context;
    private List<WebPedidoItens> lista;

    public ListaAdapterProdutoPedido(Context context, List<WebPedidoItens> lista) {
        super(context, 0, lista);

        this.context = context;
        this.lista = lista;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        WebPedidoItens itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.produto_pedido_lista, null);

        TextView nomeListaProduto = (TextView) convertView.findViewById(R.id.nomeListaProduto);
        TextView precoProduto = (TextView) convertView.findViewById(R.id.precoProduto);
        TextView textViewUnidadeMedida = (TextView) convertView.findViewById(R.id.textViewUnidadeMedida);
        TextView idPosition = (TextView) convertView.findViewById(R.id.idPosition);

        nomeListaProduto.setText(itemPosicao.getNome_produto());
        precoProduto.setText(String.format("%.2f", Float.parseFloat(itemPosicao.getQuantidade())) + " x " + String.format("R$%.2f", Float.parseFloat(itemPosicao.getValor_unitario())) + " = " + String.format("R$%.2f", Float.parseFloat(itemPosicao.getValor_total())));
        textViewUnidadeMedida.setText(itemPosicao.getDescricao());
        idPosition.setText(String.valueOf(position + 1));

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        return convertView;
    }
}
