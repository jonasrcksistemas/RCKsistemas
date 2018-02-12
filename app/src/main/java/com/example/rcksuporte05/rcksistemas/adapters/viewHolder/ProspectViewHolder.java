package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCKSUPORTE05 on 09/02/2018.
 */

public class ProspectViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.nomeProspect)
    public TextView nomeProspect;
    @BindView(R.id.txtDataRetorno)
    public TextView txtDataRetorno;
    @BindView(R.id.textViewNomeFantasia)
    public TextView textViewNomeFantasia;
    @BindView(R.id.itemListaProspect)
    public RelativeLayout itemListaProspect;

    public ProspectViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
