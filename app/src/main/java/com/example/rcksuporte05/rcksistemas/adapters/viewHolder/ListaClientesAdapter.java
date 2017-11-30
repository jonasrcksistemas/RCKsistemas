package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;

import java.util.List;

/**
 * Created by RCK 03 on 29/11/2017.
 */

public class ListaClientesAdapter extends RecyclerView.Adapter<ClientesViewHolder> {
    private List<Cliente> clientes;


    public ListaClientesAdapter(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public ClientesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cliente_lista, parent, false);

        return new ClientesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClientesViewHolder holder, int position) {
          holder.textViewNome.setText(clientes.get(position).getNome_cadastro());
          holder.textViewNomeFantasia.setText(clientes.get(position).getNome_fantasia());

        if (clientes.get(position).getTelefone_principal() != null) {
            String telefone = clientes.get(position).getTelefone_principal().trim().replaceAll("[^0-9]", "");
            if (telefone.length() == 10) {
                holder.textViewTelefone.setText("(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 6) + "-" + telefone.substring(6, 10));
            } else if (telefone.length() == 11) {
                holder.textViewTelefone.setText("(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + "-" + telefone.substring(7, 11));
            } else if (telefone.length() == 9 && !telefone.contains("-")) {
                holder.textViewTelefone.setText(telefone.substring(0, 5) + "-" + telefone.substring(5, 9));
            } else if (telefone.length() == 8) {
                holder.textViewTelefone.setText(telefone.substring(0, 4) + "-" + telefone.substring(4, 8));
            } else {
                holder.textViewTelefone.setText(telefone);
            }
        }

    }


    public Cliente getItem(int position){
        return clientes.get(position);
    }

    @Override
    public int getItemCount() {
        if(clientes != null)
        return clientes.size();

        return 0;
    }
}
