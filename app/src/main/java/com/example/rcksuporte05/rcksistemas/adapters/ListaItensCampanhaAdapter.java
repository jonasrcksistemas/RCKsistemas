package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ItemCampanhaViewHolder;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialItens;

import java.util.List;

public class ListaItensCampanhaAdapter extends RecyclerView.Adapter<ItemCampanhaViewHolder> {

    @NonNull
    @Override
    public ItemCampanhaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCampanhaViewHolder itemCampanhaViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface ItemCampanhaAdapterListener {
        void onClickListener(int position);
    }
}
