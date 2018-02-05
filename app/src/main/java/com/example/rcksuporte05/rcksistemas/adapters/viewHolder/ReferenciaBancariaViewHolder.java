package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ReferenciaBancariaAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 05/02/2018.
 */

public class ReferenciaBancariaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtBancoProspectList)
    public TextView txtBancoProspectList;

    @BindView(R.id.txtContaCorrenteList)
    public TextView txtContaCorrenteList;

    @BindView(R.id.txtAgenciaProspectList)
    public TextView txtAgenciaProspectList;

    @BindView(R.id.rlItemReferenciaBancaria)
    public RelativeLayout rlItemReferenciaBancaria;

    public ReferenciaBancariaViewHolder(View itemView, final ReferenciaBancariaAdapter.ReferenciaBancariaListener listener) {
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
