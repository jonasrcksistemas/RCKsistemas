package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProspectViewHolder;
import com.example.rcksuporte05.rcksistemas.classes.Prospect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by RCKSUPORTE05 on 09/02/2018.
 */

public class ListaProspectAdapter extends RecyclerView.Adapter<ProspectViewHolder> {
    public List<Prospect> listaProspect;
    public ProspectAdapterListener listener;

    public ListaProspectAdapter(List<Prospect> listaProspect, ProspectAdapterListener listener) {
        this.listaProspect = listaProspect;
        this.listener = listener;
    }

    @Override
    public ProspectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prospect_lista, parent, false);

        return new ProspectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProspectViewHolder holder, int position) {
        holder.nomeProspect.setText(listaProspect.get(position).getNome_cadastro());
        holder.textViewNomeFantasia.setText(listaProspect.get(position).getNome_fantasia());
        try {
            holder.txtDataRetorno.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(listaProspect.get(position).getDataRetorno())));
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

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
    }

    public interface ProspectAdapterListener {
        void onProspectRowClicked(int position);
    }
}
