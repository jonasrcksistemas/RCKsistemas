package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.CadastroAnexoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;

import java.util.ArrayList;
import java.util.List;

public class CadastroAnexoAdapter extends RecyclerView.Adapter<CadastroAnexoViewHolder> {
    private List<CadastroAnexo> cadastroAnexo;
    private CadastroAnexoAdapterListener listener;
    private SparseBooleanArray selectedItems;

    public CadastroAnexoAdapter(List<CadastroAnexo> cadastroAnexo, CadastroAnexoAdapterListener listener) {
        this.cadastroAnexo = cadastroAnexo;
        this.listener = listener;
        this.selectedItems = new SparseBooleanArray();
    }

    @Override
    public CadastroAnexoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_cadastro_anexo, parent, false);

        return new CadastroAnexoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CadastroAnexoViewHolder holder, int position) {
        holder.txtPosition.setText(String.valueOf(position + 1));

        holder.txtNomeAnexo.setText(cadastroAnexo.get(position).getNomeAnexo());

        holder.imAnexo.setImageBitmap(cadastroAnexo.get(position).getMiniatura());

        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);

        applyClickEvents(holder, position);
    }

    public CadastroAnexo getItem(int position) {
        return cadastroAnexo.get(position);
    }

    @Override
    public int getItemCount() {
        if (cadastroAnexo != null)
            return cadastroAnexo.size();
        return 0;
    }

    private void applyClickEvents(CadastroAnexoViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClickListener(position);
                return true;
            }
        });
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyDataSetChanged();
    }

    public int getSelectedItensCount() {
        return selectedItems.size();
    }

    public List<CadastroAnexo> getItensSelecionados() {
        List<CadastroAnexo> cadastroAnexoSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            cadastroAnexoSelecionados.add(cadastroAnexo.get(selectedItems.keyAt(i)));
        }
        return cadastroAnexoSelecionados;
    }

    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public interface CadastroAnexoAdapterListener {
        void onClickListener(int position);

        void onLongClickListener(int position);
    }
}
