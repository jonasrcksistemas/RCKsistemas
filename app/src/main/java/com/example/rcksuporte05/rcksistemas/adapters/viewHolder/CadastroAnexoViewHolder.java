package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroAnexoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.itemView)
    public RelativeLayout itemView;

    @BindView(R.id.txtPosition)
    public TextView txtPosition;

    @BindView(R.id.imAnexo)
    public ImageView imAnexo;

    @BindView(R.id.txtNomeAnexo)
    public TextView txtNomeAnexo;

    public CadastroAnexoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
