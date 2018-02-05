package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaContatoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 03/02/2018.
 */

public class ContatoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtNomeResponsavelProspectlist)
    public TextView txtNomeResponsavelProspectlist;

    @BindView(R.id.txtFuncaoResponsalveProspectList)
    public TextView txtFuncaoResponsalveProspectList;

    @BindView(R.id.txtCelular1ProspectList)
    public TextView txtCelular1ProspectList;

    @BindView(R.id.txtEmailProspectLista)
    public TextView txtEmailProspectLista;


    public ContatoViewHolder(View itemView, final ListaContatoAdapter.ListaContatoListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(getAdapterPosition());
            }
        });
    }
}
