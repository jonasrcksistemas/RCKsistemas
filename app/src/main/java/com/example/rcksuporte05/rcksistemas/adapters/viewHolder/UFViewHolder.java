package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.UfAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 01/02/2018.
 */

public class UFViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtUF)
    public TextView txtUF;
    @BindView(R.id.rlItemUFs)
    public RelativeLayout rlItemUFs;


    public UFViewHolder(View itemView, final UfAdapter.UfListener listener) {
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
