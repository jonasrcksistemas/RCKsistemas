package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampanhaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtIdCampanha)
    public TextView txtIdCampanha;
    @BindView(R.id.txtNomeCampanha)
    public TextView txtNomeCampanha;
    @BindView(R.id.txtDataInicio)
    public TextView txtDataInicio;
    @BindView(R.id.txtDataTermino)
    public TextView txtDataTermino;
    @BindView(R.id.layoutCampanha)
    public RelativeLayout layoutCampanha;
    @BindView(R.id.btnDescCampanha)
    public Button btnDescCampanha;

    public CampanhaViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
