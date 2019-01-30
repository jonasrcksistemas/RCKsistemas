package com.example.rcksuporte05.rcksistemas.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.CampanhaViewHolder;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListaCampanhaAdapter extends RecyclerView.Adapter<CampanhaViewHolder> {
    private List<CampanhaComercialCab> campanhas;
    private CampanhaAdapterListener listener;

    public ListaCampanhaAdapter(List<CampanhaComercialCab> campanhas, CampanhaAdapterListener listener) {
        this.campanhas = campanhas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CampanhaViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.campanha_lista, parent, false);

        return new CampanhaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CampanhaViewHolder holder, int position) {
        holder.txtIdCampanha.setText(String.valueOf(campanhas.get(position).getIdCampanha()));

        holder.txtNomeCampanha.setText(campanhas.get(position).getNomeCampanha());

        try {
            holder.txtDataInicio.setText("Inicio " + new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(campanhas.get(position).getDataInicio())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            holder.txtDataTermino.setText("Validade  " + new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(campanhas.get(position).getDataFim())));
            if (new SimpleDateFormat("yyyy-MM-dd").parse(campanhas.get(position).getDataFim()).before(new Date())) {
                holder.txtDataTermino.setTextColor(Color.RED);
            } else {
                holder.txtDataTermino.setTextColor(Color.BLACK);
            }

            holder.btnDescCampanha.setOnClickListener(listener.onClickDescCampanha(position));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        applyClickEvents(holder, position);
    }

    public CampanhaComercialCab getItem(int position) {
        return campanhas.get(position);
    }

    @Override
    public int getItemCount() {
        if (campanhas != null)
            return campanhas.size();
        return 0;
    }

    private void applyClickEvents(CampanhaViewHolder holder, final int position) {
        holder.layoutCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickListener(position);
            }
        });
    }

    public interface CampanhaAdapterListener {
        void onClickListener(int position);

        View.OnClickListener onClickDescCampanha(int position);
    }
}
