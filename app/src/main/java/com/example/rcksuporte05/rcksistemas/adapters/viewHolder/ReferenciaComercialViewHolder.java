package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ReferenciaComercialAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 06/02/2018.
 */

public class ReferenciaComercialViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.rlItemReferenciaComercial)
    public RelativeLayout rlItemREferenciaComercial;
    @BindView(R.id.txtNomeFornecedorProspectlist)
    public TextView txtNomeFornecedorProspectlist;
    @BindView(R.id.txtTelefoneFornecedorProspectList)
    public TextView txtTelefoneFornecedorProspectList;


    public ReferenciaComercialViewHolder(View itemView, final ReferenciaComercialAdapter.ReferenciaComercialListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickReferencia(getAdapterPosition());
            }
        });

    }
}
