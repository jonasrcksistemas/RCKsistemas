package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ReferenciaBancariaViewHolder;
import com.example.rcksuporte05.rcksistemas.classes.ReferenciaBancaria;

import java.util.List;

/**
 * Created by RCK 03 on 05/02/2018.
 */

public class ReferenciaBancariaAdapter extends RecyclerView.Adapter<ReferenciaBancariaViewHolder>{

    private List<ReferenciaBancaria> bancos;



    @Override
    public ReferenciaBancariaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ReferenciaBancariaViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface ReferenciaBancariaListener{
        void onClick(int position);
    }

}
