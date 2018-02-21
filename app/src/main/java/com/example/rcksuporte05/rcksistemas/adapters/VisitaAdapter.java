package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.VisitaViewHolder;
import com.example.rcksuporte05.rcksistemas.classes.VisitaProspect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class VisitaAdapter extends RecyclerView.Adapter<VisitaViewHolder> {
    List<VisitaProspect> visitas;
    VisitaListener listener;

    public VisitaAdapter(List<VisitaProspect> visitas, VisitaListener listener) {
        this.visitas = visitas;
        this.listener = listener;
    }

    @Override
    public VisitaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.item_prospect_visita, parent, false);

        return new VisitaViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(VisitaViewHolder holder, int position) {
        holder.txtNomeProspectVisita.setText(visitas.get(position).getProspect().getNome_cadastro());
        try {
            holder.txtDataVisita.setText(new SimpleDateFormat("dd/MM/yyyy")
                                .format(new SimpleDateFormat("yyyy-MM-dd")
                                .parse(visitas.get(position).getDataVisita())));
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        holder.txtMotivoNaoCadVisita.setText(visitas.get(position).getMotivoNaoCadastramento().getMotivo());
    }

    @Override
    public int getItemCount() {
        if (visitas.size() > 0)
            return visitas.size();
        else
            return 0;
    }

    public interface VisitaListener{
        void onClick(int position);
    }
}
