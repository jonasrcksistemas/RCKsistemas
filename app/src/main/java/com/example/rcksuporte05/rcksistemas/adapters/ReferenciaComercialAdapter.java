package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ReferenciaComercialViewHolder;
import com.example.rcksuporte05.rcksistemas.classes.ReferenciaComercial;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

/**
 * Created by RCK 03 on 06/02/2018.
 */

public class ReferenciaComercialAdapter extends RecyclerView.Adapter<ReferenciaComercialViewHolder> {

    private List<ReferenciaComercial> referencias;
    private ReferenciaComercialListener listener;

    public ReferenciaComercialAdapter(List<ReferenciaComercial> referencias, ReferenciaComercialListener listener) {
        this.referencias = referencias;
        this.listener = listener;
    }

    @Override
    public ReferenciaComercialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.item_referencia_comercial, parent, false);

        return new ReferenciaComercialViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ReferenciaComercialViewHolder holder, int position) {
        holder.txtNomeFornecedorProspectlist.setText(referencias.get(position).getNome_fornecedor_referencia());
        if(referencias.get(position).getTelefone() != null && !referencias.get(position).getTelefone().replaceAll("[^0-9]", "").trim().isEmpty() && referencias.get(position).getTelefone().replaceAll("[^0-9]", "").length() >= 8 && referencias.get(position).getTelefone().replaceAll("[^0-9]", "").length() <= 11)
        holder.txtTelefoneFornecedorProspectList.setText(MascaraUtil.formataTelefone(referencias.get(position).getTelefone()));
        else
            holder.txtTelefoneFornecedorProspectList.setText("Telefone não informado");

    }

    @Override
    public int getItemCount() {
        return referencias.size();
    }

    public interface ReferenciaComercialListener{
        void onClickReferencia(int position);
    }
}
