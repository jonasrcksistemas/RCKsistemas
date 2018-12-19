package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.MunicipioViewHolder;

/**
 * Created by RCK 03 on 01/02/2018.
 */

public class MunicipioAdapter extends RecyclerView.Adapter<MunicipioViewHolder> {

    private municipioListener listener;
    private String[] municipio;
    private SparseBooleanArray selectedItems;
    private int posAnterior;

    public MunicipioAdapter(String[] municipio, municipioListener listener) {
        this.listener = listener;
        this.municipio = municipio;
        this.selectedItems = new SparseBooleanArray();
        posAnterior = -1;
    }

    @Override
    public MunicipioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_municipio, parent, false);

        return new MunicipioViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(MunicipioViewHolder holder, int position) {
        holder.txtMunicipio.setText(municipio[position]);

        holder.rlItemMunicipios.setActivated(selectedItems.get(position, false));

        if (selectedItems.get(position)) {
            holder.txtMunicipio.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.txtMunicipio.setTextColor(Color.parseColor("#86869e"));
        }

        applyClickEvents(holder, position);
    }


    private void applyClickEvents(MunicipioViewHolder holder, final int position) {
        holder.rlItemMunicipios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position);
            }
        });
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }

        if (posAnterior >= 0) {
            selectedItems.delete(posAnterior);
        }

        if (pos == posAnterior) {
            selectedItems.clear();
            posAnterior = -1;
        } else
            posAnterior = pos;
    }

    public void selecionado(String municipioSelecionado) {
        for (int i = 0; municipio.length > i; i++) {
            if (municipio[i].equals(municipioSelecionado)) {
                toggleSelection(i);
                break;
            }
        }
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    @Override
    public int getItemCount() {
        return municipio.length;
    }

    public String getItem(int position) {
        return municipio[position];
    }

    public interface municipioListener {
        void onClick(int position);
    }
}
