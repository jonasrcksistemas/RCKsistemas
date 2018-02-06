package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ReferenciaBancariaViewHolder;
import com.example.rcksuporte05.rcksistemas.classes.ReferenciaBancaria;

import java.util.List;

/**
 * Created by RCK 03 on 05/02/2018.
 */

public class ReferenciaBancariaAdapter extends RecyclerView.Adapter<ReferenciaBancariaViewHolder>{

    private List<ReferenciaBancaria> bancos;
    private ReferenciaBancariaListener listener;


    public ReferenciaBancariaAdapter(List<ReferenciaBancaria> bancos, ReferenciaBancariaListener listener) {
        this.bancos = bancos;
        this.listener = listener;
    }

    @Override
    public ReferenciaBancariaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_referencia_bancaria, parent, false);

        return new ReferenciaBancariaViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ReferenciaBancariaViewHolder holder, int position) {
        holder.txtBancoProspectList.setText(bancos.get(position).getNome_banco());
        holder.txtContaCorrenteList.setText(bancos.get(position).getConta_corrente());
        holder.txtAgenciaProspectList.setText(bancos.get(position).getAgencia());
    }

    @Override
    public int getItemCount() {
        return bancos.size();
    }

    public interface ReferenciaBancariaListener{
        void onClickBancos(int position);
    }

}
