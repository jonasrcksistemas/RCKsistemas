package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rcksuporte05.rcksistemas.fragment.HistoricoFinanceiro1;
import com.example.rcksuporte05.rcksistemas.fragment.HistoricoFinanceiro2;
import com.example.rcksuporte05.rcksistemas.fragment.HistoricoFinanceiro3;

public class TabsAdapterHistoricoFinanceiro extends FragmentPagerAdapter {
    private Context context;
    private String[] titles = {"Vencidas", "A Vencer", "Quitados"};
    private int idCliente;

    public TabsAdapterHistoricoFinanceiro(FragmentManager fm, Context context, int idCliente) {
        super(fm);
        this.context = context;
        this.idCliente = idCliente;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new HistoricoFinanceiro1();
                break;
            case 1:
                frag = new HistoricoFinanceiro2();
                break;
            case 2:
                frag = new HistoricoFinanceiro3();
        }

        Bundle bundle = new Bundle();
        bundle.putInt("idCliente", idCliente);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
