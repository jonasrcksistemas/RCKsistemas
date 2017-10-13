package com.example.rcksuporte05.rcksistemas.Helper;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;

import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.classes.Municipios;
import com.example.rcksuporte05.rcksistemas.classes.Paises;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import java.util.List;

/**
 * Created by RCKSUPORTE05 on 10/10/2017.
 */

public class ClienteHelper {
    private static Cliente cliente;
    private Context context;
    private DBHelper db;
    private List<Paises> listaPaises;
    private List<Municipios> listaMunicipios;
    private List<Cliente> listaVendedor;

    public ClienteHelper(Context context) {
        this.context = context;
        db = new DBHelper(context);

        try {
            listaPaises = db.listaPaises("SELECT * FROM TBL_PAISES;");
            listaMunicipios = db.listaMunicipios("SELECT * FROM TBL_MUNICIPIOS ORDER BY NOME_MUNICIPIO");
            listaVendedor = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_VENDEDOR = 'S' ORDER BY NOME_CADASTRO;");
        } catch (CursorIndexOutOfBoundsException e) {
            System.out.println("Erro ao carregar parametros");
        }
    }

    public static Cliente getCliente() {
        return cliente;
    }

    public static void setCliente(Cliente cliente) {
        ClienteHelper.cliente = cliente;
    }

    public List<Paises> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Paises> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public List<Municipios> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(List<Municipios> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public List<Cliente> getListaVendedor() {
        return listaVendedor;
    }

    public void setListaVendedor(List<Cliente> listaVendedor) {
        this.listaVendedor = listaVendedor;
    }
}
