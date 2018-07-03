package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ClientesViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCK 03 on 30/11/2017.
 */

public class ListaClienteAdapter extends RecyclerView.Adapter<ClientesViewHolder> {
    private List<Cliente> clientes;
    private ClienteAdapterListener listener;
    private SparseBooleanArray selectedItems;

    public ListaClienteAdapter(List<Cliente> clientes, ClienteAdapterListener listener) {
        this.clientes = clientes;
        this.listener = listener;
        this.selectedItems = new SparseBooleanArray();
    }

    @Override
    public ClientesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cliente_lista, parent, false);

        return new ClientesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClientesViewHolder holder, int position) {
        if (clientes.get(position).getId_cadastro_servidor() > 0)
            holder.idCliente.setText(String.valueOf(clientes.get(position).getId_cadastro_servidor()));
        else
            holder.idCliente.setText(String.valueOf(clientes.get(position).getId_cadastro()));
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

        if (clientes.get(position).getId_cadastro_servidor() > 0) {
            if (clientes.get(position).getF_cliente().equals("S")) {
                holder.imStatus.setImageResource(R.mipmap.ic_prospect_salvo);
            } else {
                holder.imStatus.setImageResource(R.mipmap.ic_time);
                holder.txtClienteAguarda.setVisibility(View.VISIBLE);
            }
        } else {
            holder.imStatus.setImageResource(R.mipmap.ic_prospect_pendente);
        }

        if (clientes.get(position).getAlterado().equals("S")) {
            holder.txtClienteAguarda.setTextColor(Color.RED);
            holder.txtClienteAguarda.setText("Cliente com alterações pendentes");
        }

        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);

        applyCLickEnvents(holder, position);
    }


    public Cliente getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public int getItemCount() {
        if (clientes != null)
            return clientes.size();

        return 0;
    }

    private void applyCLickEnvents(ClientesViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClickListener(position);
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

    public List<Cliente> getItensSelecionados() {
        List<Cliente> clientesSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            clientesSelecionados.add(clientes.get(selectedItems.keyAt(i)));
        }
        return clientesSelecionados;
    }

    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public interface ClienteAdapterListener {
        void onClickListener(int position);

        void onLongClickListener(int position);
    }
}
