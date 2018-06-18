package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 29/11/2017.
 */

public class ClientesViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.nomeListaCliente)
    public TextView textViewNome;
    @BindView(R.id.telefoneListaCliente)
    public TextView textViewTelefone;
    @BindView(R.id.textViewNomeFantasia)
    public TextView textViewNomeFantasia;
    @BindView(R.id.idCliente)
    public TextView idCliente;
    @BindView(R.id.imStatus)
    public ImageView imStatus;

    public ClientesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
