package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ProspectEnviadoViewHolder;
import com.example.rcksuporte05.rcksistemas.model.Prospect;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class ListaProspectEnviadoAdapter extends RecyclerView.Adapter<ProspectEnviadoViewHolder> {
    List<Prospect> prospects;
    ProspectEnviadoListener listener;

    public ListaProspectEnviadoAdapter(List<Prospect> prospects, ProspectEnviadoListener listener) {
        this.prospects = prospects;
        this.listener = listener;
    }

    @Override
    public ProspectEnviadoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prospect_lista_enviado, parent, false);

        return new ProspectEnviadoViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ProspectEnviadoViewHolder holder, int position) {
        holder.nomeProspectEnviado.setText(prospects.get(position).getNome_fantasia());
        holder.txtIdProspectEnviado.setText(prospects.get(position).getId_prospect_servidor());
        try {
            holder.txtDataRetornoEnviado.setText(new SimpleDateFormat("dd/MM/yyyy")
                    .format(new SimpleDateFormat("yyyy-MM-dd")
                            .parse(prospects.get(position).getDataRetorno())));
        } catch (Exception e) {
            holder.txtDataRetornoEnviado.setText("N/D");
            e.printStackTrace();
        }

        if (prospects.get(position).getPessoa_f_j() != null) {
            switch (prospects.get(position).getPessoa_f_j()) {
                case "F":
                    holder.imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_fisica);
                    break;
                case "J":
                    holder.imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_juridica);
                    break;
                default:
                    holder.imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_duvida);
                    break;
            }
        } else
            holder.imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_duvida);

        ApplyClickEvents(holder, position);
    }

    private void ApplyClickEvents(ProspectEnviadoViewHolder holder, final int position) {
        holder.rlItemListaProspectEnviado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    public Prospect getItem(int position) {
        return prospects.get(position);
    }

    @Override
    public int getItemCount() {
        if (prospects.size() > 0)
            return prospects.size();
        else
            return 0;
    }

    public interface ProspectEnviadoListener {
        void onClick(int position);
    }
}
