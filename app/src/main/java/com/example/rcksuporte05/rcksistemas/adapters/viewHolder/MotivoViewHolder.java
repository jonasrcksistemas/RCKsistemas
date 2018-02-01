package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.MotivoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 01/02/2018.
 */

public class MotivoViewHolder  extends RecyclerView.ViewHolder {
    @BindView(R.id.txtMotivo)
    public TextView txtMotivo;
    @BindView(R.id.rlItemMotivos)
    public RelativeLayout rlItemMotivo;


    public MotivoViewHolder(View itemView, final MotivoAdapter.MotivoListener listener) {
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
