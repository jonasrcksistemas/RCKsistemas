package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectFotoSalvar;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectGeral;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectEndereco;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectContatos;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectMotivos;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectObservacoesComerciais;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectSegmentos;

/**
 * Created by RCK 03 on 25/01/2018.
 *
 */

public class TabsAdapterProspect extends FragmentPagerAdapter{
    private String[] titles = {"Geral", "Endereços", "Contato","Segmentos *", "Motivos não Cadastramento *", "Observações Comerciais",
    "adicionar Foto e Salvar"};

    public TabsAdapterProspect(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new CadastroProspectGeral();
                break;
            case 1:
                frag = new CadastroProspectEndereco();
                break;
            case 2:
                frag = new CadastroProspectContatos();
                break;
            case 3:
                frag = new CadastroProspectSegmentos();
                break;
            case 4:
                frag = new CadastroProspectMotivos();
                break;
            case 5:
                frag = new CadastroProspectObservacoesComerciais();
                break;
            case 6:
                frag = new CadastroProspectFotoSalvar();
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
