package com.example.rcksuporte05.rcksistemas.Helper;

import com.example.rcksuporte05.rcksistemas.activity.CadastroClienteMain;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente1;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente2;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente3;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente7;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente8;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente9;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RCKSUPORTE05 on 10/10/2017.
 */

public class ClienteHelper {
    private static Cliente cliente;
    private static Cliente vendedor;
    private static CadastroClienteMain cadastroClienteMain;
    private static CadastroCliente1 cadastroCliente1;
    private static CadastroCliente2 cadastroCliente2;
    private static CadastroCliente3 cadastroCliente3;
    private static CadastroCliente7 cadastroCliente7;
    private static CadastroCliente8 cadastroCliente8;
    private static CadastroCliente9 cadastroCliente9;
    private static List<CadastroAnexo> listaCadastroAnexo = new ArrayList<>();
    private static List<CadastroAnexo> listaCadastroAnexoExcluidos = new ArrayList<>();
    private static int posicaoPais = -1;
    private static int posicaoUf = -1;
    private static int posicaoMunicipio = -1;
    private static int posicaoCobrancaPais = -1;
    private static int posicaoCobrancaUf = -1;
    private static int posicaoCobrancaMunicipio = -1;


    public static Cliente getCliente() {
        return cliente;
    }

    public static void setCliente(Cliente cliente) {
        ClienteHelper.cliente = cliente;
    }

    public static Cliente getVendedor() {
        return vendedor;
    }

    public static void setVendedor(Cliente vendedor) {
        ClienteHelper.vendedor = vendedor;
    }

    public static CadastroCliente1 getCadastroCliente1() {
        return cadastroCliente1;
    }

    public static void setCadastroCliente1(CadastroCliente1 cadastroCliente1) {
        ClienteHelper.cadastroCliente1 = cadastroCliente1;
    }

    public static CadastroClienteMain getCadastroClienteMain() {
        return cadastroClienteMain;
    }

    public static void setCadastroClienteMain(CadastroClienteMain cadastroClienteMain) {
        ClienteHelper.cadastroClienteMain = cadastroClienteMain;
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

    public static CadastroCliente7 getCadastroCliente7() {
        return cadastroCliente7;
    }

    public static void setCadastroCliente7(CadastroCliente7 cadastroCliente7) {
        ClienteHelper.cadastroCliente7 = cadastroCliente7;
    }

    public static CadastroCliente8 getCadastroCliente8() {
        return cadastroCliente8;
    }

    public static void setCadastroCliente8(CadastroCliente8 cadastroCliente8) {
        ClienteHelper.cadastroCliente8 = cadastroCliente8;
    }

    public static CadastroCliente9 getCadastroCliente9() {
        return cadastroCliente9;
    }

    public static void setCadastroCliente9(CadastroCliente9 cadastroCliente9) {
        ClienteHelper.cadastroCliente9 = cadastroCliente9;
    }

    public static List<CadastroAnexo> getListaCadastroAnexo() {
        return listaCadastroAnexo;
    }

    public static void setListaCadastroAnexo(List<CadastroAnexo> listaCadastroAnexo) {
        ClienteHelper.listaCadastroAnexo = listaCadastroAnexo;
    }

    public static List<CadastroAnexo> getListaCadastroAnexoExcluidos() {
        return listaCadastroAnexoExcluidos;
    }

    public static void setListaCadastroAnexoExcluidos(List<CadastroAnexo> listaCadastroAnexoExcluidos) {
        ClienteHelper.listaCadastroAnexoExcluidos = listaCadastroAnexoExcluidos;
    }

    public static int getPosicaoPais() {
        return posicaoPais;
    }

    public static void setPosicaoPais(int posicaoPais) {
        ClienteHelper.posicaoPais = posicaoPais;
    }

    public static int getPosicaoUf() {
        return posicaoUf;
    }

    public static void setPosicaoUf(int posicaoUf) {
        ClienteHelper.posicaoUf = posicaoUf;
    }

    public static int getPosicaoMunicipio() {
        return posicaoMunicipio;
    }

    public static void setPosicaoMunicipio(int posicaoMunicipio) {
        ClienteHelper.posicaoMunicipio = posicaoMunicipio;
    }

    public static int getPosicaoCobrancaPais() {
        return posicaoCobrancaPais;
    }

    public static void setPosicaoCobrancaPais(int posicaoCobrancaPais) {
        ClienteHelper.posicaoCobrancaPais = posicaoCobrancaPais;
    }

    public static int getPosicaoCobrancaUf() {
        return posicaoCobrancaUf;
    }

    public static void setPosicaoCobrancaUf(int posicaoCobrancaUf) {
        ClienteHelper.posicaoCobrancaUf = posicaoCobrancaUf;
    }

    public static int getPosicaoCobrancaMunicipio() {
        return posicaoCobrancaMunicipio;
    }

    public static void setPosicaoCobrancaMunicipio(int posicaoCobrancaMunicipio) {
        ClienteHelper.posicaoCobrancaMunicipio = posicaoCobrancaMunicipio;
    }

    public static void moveTela(int position) {
        if (cadastroClienteMain.mViewPager.getCurrentItem() != position)
            cadastroClienteMain.mViewPager.setCurrentItem(position);
    }

    public static void clear() {
        cliente = null;
        cadastroClienteMain = null;
        cadastroCliente1 = null;
        cadastroCliente2 = null;
        cadastroCliente3 = null;
        cadastroCliente7 = null;
        cadastroCliente8 = null;
        cadastroCliente9 = null;
        listaCadastroAnexo = null;
        listaCadastroAnexoExcluidos = null;
        posicaoPais = -1;
        posicaoUf = -1;
        posicaoMunicipio = -1;
        posicaoCobrancaPais = -1;
        posicaoCobrancaUf = -1;
        posicaoCobrancaMunicipio = -1;
    }
}
