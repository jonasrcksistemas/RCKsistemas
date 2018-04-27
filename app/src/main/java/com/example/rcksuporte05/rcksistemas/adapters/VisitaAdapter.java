package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.VisitaViewHolder;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class VisitaAdapter extends RecyclerView.Adapter<VisitaViewHolder> {
    private List<VisitaProspect> visitas;
    private VisitaListener listener;
    private SparseBooleanArray selectedItems;

    public VisitaAdapter(List<VisitaProspect> visitas, VisitaListener listener) {
        this.visitas = visitas;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public VisitaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prospect_visita, parent, false);

        return new VisitaViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(VisitaViewHolder holder, int position) {
        holder.txtDescricaoVisita.setText(visitas.get(position).getDescricaoVisita());
        try {
            holder.txtDataVisita.setText(new SimpleDateFormat("dd/MM/yyyy")
                    .format(new SimpleDateFormat("yyyy-MM-dd")
                            .parse(visitas.get(position).getDataVisita())));
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        if (visitas.get(position).getIdVisitaServidor() != null) {
            holder.idVisitaProspect.setText(visitas.get(position).getIdVisitaServidor());
            holder.visitaSalvo.setImageResource(R.mipmap.ic_prospect_salvo);
        } else {
            holder.idVisitaProspect.setText(visitas.get(position).getIdVisita());
            holder.visitaSalvo.setImageResource(R.mipmap.ic_prospect_pendente);
        }

        holder.itemView
                .setBackgroundColor(selectedItems.get(position) ? Color.parseColor("#dfdfdf")
                        : Color.TRANSPARENT);
    }

    public int contaPendentes() {
        int pendentes = 0;
        for (VisitaProspect visita : visitas) {
            if (visita.getIdVisitaServidor() == null)
                pendentes++;
        }
        return pendentes;
    }

    public VisitaProspect getItem(int position) {
        return visitas.get(position);
    }

    @Override
    public int getItemCount() {
        if (visitas.size() > 0)
            return visitas.size();
        else
            return 0;
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public List<VisitaProspect> getItensSelecionados() {
        List<VisitaProspect> visitasSelecionadas = new ArrayList<>();
        for (int i = 0; selectedItems.size() > i; i++) {
            visitasSelecionadas.add(visitas.get(selectedItems.keyAt(i)));
        }
        return visitasSelecionadas;
    }

    public int getItensSelecionadosCount() {
        return selectedItems.size();
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void setLista(List<VisitaProspect> listaNova) {
        visitas = listaNova;
    }

    public interface VisitaListener {
        void onClick(int position);

        void onLongClick(int position);
    }
}
