package com.example.rcksuporte05.rcksistemas.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.MotivoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento;

import java.util.List;

/**
 * Created by RCK 03 on 01/02/2018.
 */

public class MotivoAdapter extends RecyclerView.Adapter<MotivoViewHolder> {

    private MotivoListener listener;
    private List<MotivoNaoCadastramento> motivos;
    private SparseBooleanArray selectedItems;
    private int posAnterior;
    private MotivoNaoCadastramento motivoSelecionado;
    private Activity activity;

    public MotivoAdapter(List<MotivoNaoCadastramento> motivos, MotivoListener listener, Activity activity) {
        this.listener = listener;
        this.motivos = motivos;
        this.selectedItems = new SparseBooleanArray();
        this.posAnterior = -1;
        this.activity = activity;
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

        if (selectedItems.get(position)) {
            holder.txtMotivo.setTextColor(Color.parseColor("#ffffff"));
        } else
            holder.txtMotivo.setTextColor(activity.getResources().getColor(R.color.colorAccent));

        applyClickEvents(holder, position);
    }


    private void applyClickEvents(MotivoViewHolder holder, final int position) {
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

        if (posAnterior >= 0) {
            selectedItems.delete(posAnterior);
        }

        if (pos == posAnterior) {
            selectedItems.clear();
            posAnterior = -1;
        } else
            posAnterior = pos;
        motivoSelecionado = motivos.get(pos);
    }


    public MotivoNaoCadastramento getItemSelecionado() {
        return motivoSelecionado;
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    @Override
    public int getItemCount() {
        return motivos.size();
    }

    public MotivoNaoCadastramento getItem(int position) {
        return motivos.get(position);
    }

    public void marcarSelecionado(MotivoNaoCadastramento motivo) {
        //está variavel é responsavel para armazenar o index do objeto que correponde com o id do objeto que vem do banco
        int i = 0;
        //está variavel guarda a possição que se encontra o item que veio do banco
        int posicao = -1;

        for (MotivoNaoCadastramento paraTeste : motivos) {
            if (paraTeste.getIdItem().contains(motivo.getIdItem())) {
                posicao = i;
                break;
            }
            i++;
        }

        if (posicao >= 0) {
            toggleSelection(posicao);
        }
    }

    public interface MotivoListener {
        void onClick(int position);
    }
}
