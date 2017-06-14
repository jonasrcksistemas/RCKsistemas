package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.classes.Municipios;
import com.example.rcksuporte05.rcksistemas.classes.Paises;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente1;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente2;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente4;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente3;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente5;

import java.util.List;

public class TabsAdapterCliente extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles = {"Geral", "Endereço", "Financeiro/Cobrança", "Email NFe", "Observações"};
    private List<Paises> listaPaises;
    private List<Municipios> listaMunicipios;
    private List<Cliente> listaVendedores;
    private int idCliente;
    private String[] cliente;

    public TabsAdapterCliente(FragmentManager fm, Context c, List<Paises> listaPaises, List<Municipios> listaMunicipios, List<Cliente> listaVendedores, int idCliente, String[] cliente) {
        super(fm);
        mContext = c;
        this.listaPaises = listaPaises;
        this.listaMunicipios = listaMunicipios;
        this.listaVendedores = listaVendedores;
        this.idCliente = idCliente;
        this.cliente = cliente;
    }

    public TabsAdapterCliente(FragmentManager fm, Context c, List<Paises> listaPaises, List<Municipios> listaMunicipios, int idCliente, String[] cliente) {
        super(fm);
        mContext = c;
        this.listaPaises = listaPaises;
        this.listaMunicipios = listaMunicipios;
        this.idCliente = idCliente;
        this.cliente = cliente;
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
        }

        String[] paises = new String[listaPaises.size()];
        String[] idPaises = new String[listaPaises.size()];
        for (int i = 0; listaPaises.size() > i; i++) {
            paises[i] = listaPaises.get(i).getNome_pais();
            idPaises[i] = listaPaises.get(i).getId_pais();
        }
        String[] municipios = new String[listaMunicipios.size()];
        String[] idMunicipios = new String[listaMunicipios.size()];
        for (int i = 0; listaMunicipios.size() > i; i++) {
            municipios[i] = listaMunicipios.get(i).getNome_municipio();
            idMunicipios[i] = listaMunicipios.get(i).getId_municipio();
        }

        String[] vendedores = new String[listaVendedores.size()];
        String[] idVendedores = new String[listaVendedores.size()];
        for (int i = 0; listaVendedores.size() > i; i++) {
            vendedores[i] = listaVendedores.get(i).getNome_cadastro();
            idVendedores[i] = listaVendedores.get(i).getF_id_vendedor();
        }

        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putStringArray("paises", paises);
        b.putStringArray("idPaises", idPaises);
        b.putStringArray("municipios", municipios);
        b.putStringArray("idMunicipios", idMunicipios);
        b.putStringArray("vendedores", vendedores);
        b.putStringArray("idVendedores", idVendedores);
        b.putInt("cliente", idCliente);
        b.putStringArray("clienteListar", cliente);
        frag.setArguments(b);

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
