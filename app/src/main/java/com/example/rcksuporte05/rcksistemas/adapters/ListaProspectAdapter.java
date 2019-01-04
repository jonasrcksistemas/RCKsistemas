package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProspectViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Prospect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCKSUPORTE05 on 09/02/2018.
 */

public class ListaProspectAdapter extends RecyclerView.Adapter<ProspectViewHolder> {
    private List<Prospect> listaProspect;
    private ProspectAdapterListener listener;
    private SparseBooleanArray selectedItems;

    public ListaProspectAdapter(List<Prospect> listaProspect, ProspectAdapterListener listener) {
        this.listaProspect = listaProspect;
        this.listener = listener;
        this.selectedItems = new SparseBooleanArray();
    }

    @Override
    public ProspectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prospect_lista, parent, false);

        return new ProspectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProspectViewHolder holder, int position) {
        holder.txtIdProspect.setText(listaProspect.get(position).getId_prospect());
        holder.nomeProspect.setText(listaProspect.get(position).getNome_cadastro());
        holder.textViewNomeFantasia.setText(listaProspect.get(position).getNome_fantasia());
        try {
            holder.txtDataRetorno.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(listaProspect.get(position).getDataRetorno())));
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        if (listaProspect.get(position).getProspectSalvo().equals("S")) {
            holder.prospectSalvo.setImageResource(R.mipmap.ic_prospect_pendente);
        } else {
            holder.prospectSalvo.setImageResource(R.mipmap.ic_prospect_nao_salvo);
        }

        if (selectedItems.get(position))
            if (listaProspect.get(position).getProspectSalvo().equals("S"))
                holder.itemView.setBackgroundColor(Color.parseColor("#5800a387"));
            else
                holder.itemView.setBackgroundColor(Color.parseColor("#58a30005"));
        else
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);

        ApplyClickEvents(holder, position);
    }

    @Override
    public int getItemCount() {
        if (listaProspect != null)
            return listaProspect.size();
        return 0;
    }

    public void ApplyClickEvents(ProspectViewHolder holder, final int position) {
        holder.itemListaProspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProspectRowClicked(position);
            }
        });
        holder.itemListaProspect.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onProspectLongClicked(position);
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
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
        notifyItemChanged(pos);
    }

    public Prospect getItem(int position) {
        return listaProspect.get(position);
    }

    public List<Prospect> getItensSelecionados() {
        List<Prospect> prospectsSelecionados = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            prospectsSelecionados.add(listaProspect.get(selectedItems.keyAt(i)));
        }
        return prospectsSelecionados;
    }

    public int getItensSelecionadosCount() {
        return selectedItems.size();
    }

    public void remove(Prospect prospect) {
        listaProspect.remove(prospect);
        notifyDataSetChanged();
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public interface ProspectAdapterListener {
        void onProspectRowClicked(int position);

        void onProspectLongClicked(int position);
    }
}
