package com.example.rcksuporte05.rcksistemas.adapters.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampanhaItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.edtNomeProdutoLinha)
    public EditText edtNomeProdutoLinha;
    @BindView(R.id.edtQuantidadeLinha)
    public EditText edtQuantidadeLinha;
    @BindView(R.id.txtIdProduto)
    public TextView txtIdProduto;
    @BindView(R.id.edtNomeProduto)
    public EditText edtNomeProduto;
    @BindView(R.id.edtQuantidade)
    public EditText edtQuantidade;
    @BindView(R.id.txtTipoCampanha1)
    public TextView txtTipoCampanha1;
    @BindView(R.id.txtTipoCampanha2)
    public TextView txtTipoCampanha2;

    public CampanhaItemViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
