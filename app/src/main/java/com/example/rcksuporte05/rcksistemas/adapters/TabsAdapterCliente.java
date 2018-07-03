package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente1;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente2;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente3;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente4;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente5;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente6;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente7;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente8;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente9;

public class TabsAdapterCliente extends FragmentPagerAdapter {
    private String[] titles = {"GERAL", "ENDEREÇO", "FINANCEIRO/COBRANÇA", "CONTATO *", "SEGMENTOS *", "OBSERVAÇÕES COMERCIAIS", "EMAIL NFE", "ANEXOS", "OBSERVAÇÕES"};

    public TabsAdapterCliente(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new CadastroCliente1();
                break;
            case 1:
                frag = new CadastroCliente2();
                break;
            case 2:
                frag = new CadastroCliente3();
                break;
            case 3:
                frag = new CadastroCliente4();
                break;
            case 4:
                frag = new CadastroCliente5();
                break;
            case 5:
                frag = new CadastroCliente6();
                break;
            case 6:
                frag = new CadastroCliente7();
                break;
            case 7:
                frag = new CadastroCliente8();
                break;
            case 8:
                frag = new CadastroCliente9();
                break;
        }

        return frag;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (titles[position]);
    }
}
