package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.VisitaAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class VisitaViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rlItemVisita)
    public RelativeLayout rlItemVisita;

    @BindView(R.id.txtNomeProspectVisita)
    public TextView txtNomeProspectVisita;

    @BindView(R.id.txtDataVisita)
    public TextView txtDataVisita;

    @BindView(R.id.visitaSalvo)
    public ImageView visitaSalvo;

    public VisitaViewHolder(View itemView, final VisitaAdapter.VisitaListener listener) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        rlItemVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(getAdapterPosition());
            }
        });

    }
}
