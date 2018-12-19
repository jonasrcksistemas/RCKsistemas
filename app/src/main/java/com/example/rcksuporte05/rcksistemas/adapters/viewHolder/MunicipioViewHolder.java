package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.MunicipioAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 01/02/2018.
 */

public class MunicipioViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtMunicipio)
    public TextView txtMunicipio;
    @BindView(R.id.rlItemMunicipios)
    public RelativeLayout rlItemMunicipios;


    public MunicipioViewHolder(View itemView, final MunicipioAdapter.municipioListener listener) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(getAdapterPosition());
            }
        });

        ButterKnife.bind(this, itemView);
    }

}
