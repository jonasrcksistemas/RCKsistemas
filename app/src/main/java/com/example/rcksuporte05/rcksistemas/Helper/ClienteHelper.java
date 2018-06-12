package com.example.rcksuporte05.rcksistemas.Helper;

import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente1;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente2;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente3;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente4;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente5;
import com.example.rcksuporte05.rcksistemas.model.Cliente;

/**
 * Created by RCKSUPORTE05 on 10/10/2017.
 */

public class ClienteHelper {
    private static Cliente cliente;
    private static CadastroCliente1 cadastroCliente1;
    private static CadastroCliente2 cadastroCliente2;
    private static CadastroCliente3 cadastroCliente3;
    private static CadastroCliente4 cadastroCliente4;
    private static CadastroCliente5 cadastroCliente5;

    public static Cliente getCliente() {
        return cliente;
    }

    public static void setCliente(Cliente cliente) {
        ClienteHelper.cliente = cliente;
    }

    public static CadastroCliente1 getCadastroCliente1() {
        return cadastroCliente1;
    }

    public static void setCadastroCliente1(CadastroCliente1 cadastroCliente1) {
        ClienteHelper.cadastroCliente1 = cadastroCliente1;
    }

    public static CadastroCliente2 getCadastroCliente2() {
        return cadastroCliente2;
    }

    public static void setCadastroCliente2(CadastroCliente2 cadastroCliente2) {
        ClienteHelper.cadastroCliente2 = cadastroCliente2;
    }

    public static CadastroCliente3 getCadastroCliente3() {
        return cadastroCliente3;
    }

    public static void setCadastroCliente3(CadastroCliente3 cadastroCliente3) {
        ClienteHelper.cadastroCliente3 = cadastroCliente3;
    }

    public static CadastroCliente4 getCadastroCliente4() {
        return cadastroCliente4;
    }

    public static void setCadastroCliente4(CadastroCliente4 cadastroCliente4) {
        ClienteHelper.cadastroCliente4 = cadastroCliente4;
    }

    public static CadastroCliente5 getCadastroCliente5() {
        return cadastroCliente5;
    }

    public static void setCadastroCliente5(CadastroCliente5 cadastroCliente5) {
        ClienteHelper.cadastroCliente5 = cadastroCliente5;
    }
}
