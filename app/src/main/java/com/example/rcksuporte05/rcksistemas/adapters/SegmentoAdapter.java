package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.SegmentoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Segmento;

import java.util.List;

/**
 * Created by RCK 03 on 01/02/2018.
 */

public class SegmentoAdapter extends RecyclerView.Adapter<SegmentoViewHolder> {
    private List<Segmento> segmentos;
    private SegmentoListener listener;
    private SparseBooleanArray selectedItems;
    private int posAnterior;
    private Segmento selecionado;

    public SegmentoAdapter(List<Segmento> segmentos, SegmentoListener listener) {
        this.segmentos = segmentos;
        this.listener = listener;
        this.selectedItems = new SparseBooleanArray();
        this.posAnterior = -1;
    }

    @Override
    public SegmentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.item_segmento, parent, false);

        return new SegmentoViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(SegmentoViewHolder holder, int position) {
        holder.txtNomeSegmento.setText(segmentos.get(position).getNomeSetor());

        holder.rlItemSegmentos.setActivated(selectedItems.get(position, false));

        if(selectedItems.get(position)){
            holder.txtNomeSegmento.setTextColor(Color.parseColor("#ffffff"));
        }

        applyClickEvents(holder, position);
    }

    private void applyClickEvents(SegmentoViewHolder holder, final int position){
        holder.rlItemSegmentos.setOnClickListener(new View.OnClickListener() {
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

        if(posAnterior >= 0){
            selectedItems.delete(posAnterior);
        }
        posAnterior = pos;
        selecionado = segmentos.get(pos);
    }

    public Segmento getItemSelecionado(){
        return selecionado;
    }

    @Override
    public int getItemCount() {
        return segmentos.size();
    }


    public Segmento getItem(int position){
        return segmentos.get(position);
    }

    public void marcarSelecionado(Segmento segmento){
        //está variavel é responsavel para armazenar o index do objeto que correponde com o id do objeto que vem do banco
        int i = 0;
        //está variavel guarda a possição que se encontra o item que veio do banco
        int posicao = -1;

        for(Segmento paraTeste : segmentos){
            if(paraTeste.getIdSetor().contains(segmento.getIdSetor())){
                posicao = i;
                break;
            }
            i++;
        }

        if(posicao >= 0){
            toggleSelection(posicao);
        }
    }

    public interface SegmentoListener{
        void onClick(int position);
    }
}
