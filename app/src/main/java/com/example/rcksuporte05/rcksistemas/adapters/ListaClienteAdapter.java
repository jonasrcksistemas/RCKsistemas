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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        try {
            if (new SimpleDateFormat("yyyy-MM-dd").parse(clientes.get(position).getData_ultima_compra()).before(new Date(1995))) {
                holder.txtDataUltimaCompra.setText("Ainda não comprou");
            } else {
                holder.txtDataUltimaCompra.setText("Não compra à " + calcularDias(new SimpleDateFormat("yyyy-MM-dd").parse(clientes.get(position).getData_ultima_compra()), new Date()) + " dias");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (clientes.get(position).getId_cadastro_servidor() > 0) {
            if (clientes.get(position).getF_cliente().equals("S")) {
                holder.imStatus.setVisibility(View.GONE);
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

    private String calcularDias(Date data1, Date data2) {
        return String.valueOf((data2.getTime() - data1.getTime()) / 86400000L);
    }

    public interface ClienteAdapterListener {
        void onClickListener(int position);

        void onLongClickListener(int position);
    }
}
