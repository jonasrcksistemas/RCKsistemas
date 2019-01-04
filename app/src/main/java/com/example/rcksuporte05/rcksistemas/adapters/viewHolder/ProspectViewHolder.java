package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProspectAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCKSUPORTE05 on 09/02/2018.
 */

public class ProspectViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    @BindView(R.id.nomeProspect)
    public TextView nomeProspect;

    @BindView(R.id.txtDataRetorno)
    public TextView txtDataRetorno;

    @BindView(R.id.txtRazaoSocial)
    public TextView textViewNomeFantasia;

    @BindView(R.id.prospectSalvo)
    public ImageView prospectSalvo;

    @BindView(R.id.txtIdProspect)
    public TextView txtIdProspect;

    @BindView(R.id.itemListaProspect)
    public RelativeLayout itemListaProspect;

    ListaProspectAdapter.ProspectAdapterListener listener;

    public ProspectViewHolder(View itemView) {
        super(itemView);
        itemView.setOnLongClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public boolean onLongClick(View view) {
        listener.onProspectLongClicked(getAdapterPosition());
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
        return false;
    }
}
