package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProspectEnviadoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class ProspectEnviadoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.nomeProspectEnviado)
    public TextView nomeProspectEnviado;
    @BindView(R.id.txtIdProspectEnviado)
    public TextView txtIdProspectEnviado;
    @BindView(R.id.txtDataRetornoEnviado)
    public TextView txtDataRetornoEnviado;
    @BindView(R.id.rlItemListaProspectEnviado)
    public RelativeLayout rlItemListaProspectEnviado;
    @BindView(R.id.imFisicaJuridica)
    public ImageView imFisicaJuridica;


    public ProspectEnviadoViewHolder(View itemView, final ListaProspectEnviadoAdapter.ProspectEnviadoListener listener) {
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
