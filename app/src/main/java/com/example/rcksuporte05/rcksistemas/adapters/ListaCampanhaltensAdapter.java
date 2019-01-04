package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.CampanhaItemViewHolder;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

public class ListaCampanhaltensAdapter extends RecyclerView.Adapter<CampanhaItemViewHolder> {
    private List<CampanhaComercialItens> itensCampanha;
    private CampanhaItemAdapterListener listener;

    public ListaCampanhaltensAdapter(List<CampanhaComercialItens> itensCampanha, CampanhaItemAdapterListener listener) {
        this.itensCampanha = itensCampanha;
        this.listener = listener;
    }

    @Override
    public CampanhaItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.campanha_item_lista, parent, false);

        return new CampanhaItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CampanhaItemViewHolder holder, int position) {
        switch (itensCampanha.get(position).getIdTipoCampanha()) {
            case 1:
                holder.txtTipoCampanha1.setText("Pague");
                holder.txtTipoCampanha2.setText("Leve");
                break;
            case 2:
                holder.txtTipoCampanha1.setText("Compre");
                holder.txtTipoCampanha2.setText("Ganhe");
                break;
        }

        holder.edtNomeProdutoLinha.setText(itensCampanha.get(position).getNomeProdutoLinha());
        holder.edtQuantidadeLinha.setText(MascaraUtil.mascaraVirgula(itensCampanha.get(position).getQuantidadeVenda()));

        holder.txtIdProduto.setText("Produto " + itensCampanha.get(position).getIdProdutoBonus());
        holder.edtNomeProduto.setText(itensCampanha.get(position).getNomeProdutoBonus());
        holder.edtQuantidade.setText(MascaraUtil.mascaraVirgula(itensCampanha.get(position).getQuantidadeBonus()));

    }

    @Override
    public int getItemCount() {
        if (itensCampanha != null)
            return itensCampanha.size();
        return 0;
    }

    public void applyClickEvents(CampanhaItemViewHolder holder, CampanhaItemAdapterListener listener) {

    }

    public interface CampanhaItemAdapterListener {
        void onClickListener(int position);
    }
}
