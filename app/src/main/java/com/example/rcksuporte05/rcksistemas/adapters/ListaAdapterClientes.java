package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;

import java.util.List;

public class ListaAdapterClientes extends ArrayAdapter<Cliente> {

    private Context context;
    private List<Cliente> lista;

    public ListaAdapterClientes(Context context, List<Cliente> lista) {
        super(context, 0, lista);

        this.context = context;
        this.lista = lista;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final Cliente itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.cliente_lista, null);

        TextView textViewNome = (TextView) convertView.findViewById(R.id.nomeListaCliente);
        TextView textViewTelefone = (TextView) convertView.findViewById(R.id.telefoneListaCliente);
        TextView textViewNomeFantasia = (TextView) convertView.findViewById(R.id.textViewNomeFantasia);

        if (itemPosicao.getAtivo().equalsIgnoreCase("N")) {
            textViewNome.setTextColor(Color.parseColor("#78909c"));
            textViewNomeFantasia.setTextColor(Color.parseColor("#78909c"));
            textViewTelefone.setTextColor(Color.parseColor("#78909c"));
        }
        textViewNome.setText(itemPosicao.getNome_cadastro());

        String telefone = itemPosicao.getTelefone_principal().trim().replaceAll("[^0-9]", "");
        if (telefone.length() == 10) {
            textViewTelefone.setText("(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6, 10));
        } else if (telefone.length() == 11) {
            textViewTelefone.setText("(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7, 11));
        } else if (telefone.length() == 9 && !telefone.contains("-")) {
            textViewTelefone.setText(telefone.substring(0, 5) + "-" + telefone.substring(5, 9));
        } else if (telefone.length() == 8) {
            textViewTelefone.setText(telefone.substring(0, 4) + "-" + telefone.substring(4, 8));
        } else {
            textViewTelefone.setText(telefone);
        }

        textViewNomeFantasia.setText(itemPosicao.getNome_fantasia());

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        System.gc();

        return convertView;
    }
}
