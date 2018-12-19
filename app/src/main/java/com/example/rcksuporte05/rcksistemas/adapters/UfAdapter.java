package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.UFViewHolder;

/**
 * Created by RCK 03 on 01/02/2018.
 */

public class UfAdapter extends RecyclerView.Adapter<UFViewHolder> {

    private UfListener listener;
    private String[] uf;
    private SparseBooleanArray selectedItems;
    private int posAnterior;

    public UfAdapter(String[] uf, UfListener listener) {
        this.listener = listener;
        this.uf = uf;
        this.selectedItems = new SparseBooleanArray();
        posAnterior = -1;
    }

    @Override
    public UFViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_uf, parent, false);

        return new UFViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(UFViewHolder holder, int position) {
        holder.txtUF.setText(uf[position]);

        holder.rlItemUFs.setActivated(selectedItems.get(position, false));

        if (selectedItems.get(position)) {
            holder.txtUF.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.txtUF.setTextColor(Color.parseColor("#86869e"));
        }

        applyClickEvents(holder, position);
    }


    private void applyClickEvents(UFViewHolder holder, final int position) {
        holder.rlItemUFs.setOnClickListener(new View.OnClickListener() {
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

    public void selecionado(String UFSelecionado) {
        for (int i = 0; uf.length > i; i++) {
            if (uf[i].equals(UFSelecionado)) {
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
        return uf.length;
    }

    public String getItem(int position) {
        return uf[position];
    }

    public interface UfListener {
        void onClick(int position);
    }
}
