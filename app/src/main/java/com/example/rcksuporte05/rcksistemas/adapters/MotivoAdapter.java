package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.MotivoViewHolder;
import com.example.rcksuporte05.rcksistemas.classes.MotivoNaoCadastramento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCK 03 on 01/02/2018.
 */

public class MotivoAdapter extends RecyclerView.Adapter<MotivoViewHolder> {

    private MotivoListener listener;
    private List<MotivoNaoCadastramento> motivos;
    private SparseBooleanArray selectedItems;
    private int posAnterior;

    public MotivoAdapter(List<MotivoNaoCadastramento> motivos, MotivoListener listener) {
        this.listener = listener;
        this.motivos = motivos;
        this.selectedItems = new SparseBooleanArray();
        this.posAnterior = -1;
    }

    @Override
    public MotivoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_motivo, parent, false);

        return new MotivoViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(MotivoViewHolder holder, int position) {
        holder.txtMotivo.setText(motivos.get(position).getMotivo());

        holder.rlItemMotivo.setActivated(selectedItems.get(position, false));

        applyClickEvents(holder, position);
    }


    private void applyClickEvents(MotivoViewHolder holder, final int position){
        holder.rlItemMotivo.setOnClickListener(new View.OnClickListener() {
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
    }


    public List<MotivoNaoCadastramento> getItensSelecionados(){
        List<MotivoNaoCadastramento> motivosSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            motivosSelecionados.add(motivos.get(selectedItems.keyAt(i)));
        }

        return motivosSelecionados;
    }

    @Override
    public int getItemCount() {
        return motivos.size();
    }

    public void marcarSelecionado(MotivoNaoCadastramento motivo){
        //está variavel é responsavel para armazenar o index do objeto que correponde com o id do objeto que vem do banco
        int i = 0;
        //está variavel guarda a possição que se encontra o item que veio do banco
        int posicao = -1;

        for(MotivoNaoCadastramento paraTeste : motivos){
            if(paraTeste.getIdItem().contains(motivo.getIdItem())){
                posicao = i;
                break;
            }
            i++;
        }

        if(posicao >= 0){
            toggleSelection(posicao);
        }
    }

    public interface MotivoListener{
        void onClick(int position);
    }
}
