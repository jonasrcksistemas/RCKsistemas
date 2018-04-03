package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido1;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;

public class TabsAdapterPedido extends FragmentPagerAdapter {
    private Context context;
    private String[] titles = {"Produtos", "Finalização"};
    private String usuario;
    private int idUsuario;
    private int idVendedor;
    private int vizualizacao;

    public TabsAdapterPedido(FragmentManager fm, Context c, Usuario usuario, int vizualizacao) {
        super(fm);
        context = c;
        this.idUsuario = Integer.parseInt(usuario.getId_usuario());
        this.usuario = usuario.getNome_usuario();
        this.idVendedor = Integer.parseInt(usuario.getId_quando_vendedor());
        this.vizualizacao = vizualizacao;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new Pedido1();
                break;
            case 1:
                frag = new Pedido2();
                break;
        }

        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putString("usuario", usuario);
        b.putInt("idUsuario", idUsuario);
        b.putInt("idVendedor", idVendedor);
        b.putInt("vizualizacao", vizualizacao);
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
