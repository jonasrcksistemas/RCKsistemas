package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.rcksuporte05.rcksistemas.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 29/11/2017.
 */

public class ClientesViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.nomeListaCliente)
    TextView textViewNome;
    @BindView(R.id.telefoneListaCliente)
    TextView textViewTelefone;
    @BindView(R.id.textViewNomeFantasia)
    TextView textViewNomeFantasia;

    public ClientesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
