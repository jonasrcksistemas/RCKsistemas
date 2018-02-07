package com.example.rcksuporte05.rcksistemas.Helper;

import com.example.rcksuporte05.rcksistemas.classes.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.classes.Municipio;
import com.example.rcksuporte05.rcksistemas.classes.Pais;
import com.example.rcksuporte05.rcksistemas.classes.Prospect;
import com.example.rcksuporte05.rcksistemas.classes.Segmento;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectContatos;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectEndereco;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectFotoSalvar;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectGeral;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectMotivos;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectObservacoesComerciais;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectSegmentos;

import java.util.List;

/**
 * Created by RCK 03 on 31/01/2018.
 */

public class ProspectHelper {
    private static Prospect prospect;
    private static List<Segmento> segmentos;
    private static List<MotivoNaoCadastramento> motivos;
    private static List<Pais> paises;
    public static List<Municipio> municipios;
    private static CadastroProspectGeral cadastroProspectGeral;
    private static CadastroProspectEndereco cadastroProspectEndereco;
    private static CadastroProspectContatos cadastroProspectContatos;
    private static CadastroProspectSegmentos cadastroProspectSegmentos;
    private static CadastroProspectMotivos cadastroProspectMotivos;
    private static CadastroProspectObservacoesComerciais cadastroProspectObservacoesComerciais;
    private static CadastroProspectFotoSalvar cadastroProspectFotoSalvar;
    private static int posicaoPais = -1;
    private static int posicaoUf = -1;
    private static int posicaoMunicipio = -1;

    public static Prospect getProspect() {
        return prospect;
    }

    public static void setProspect(Prospect prospect) {
        ProspectHelper.prospect = prospect;
    }

    public static CadastroProspectGeral getCadastroProspectGeral() {
        return cadastroProspectGeral;
    }

    public static void setCadastroProspectGeral(CadastroProspectGeral cadastroProspectGeral) {
        ProspectHelper.cadastroProspectGeral = cadastroProspectGeral;
    }

    public static CadastroProspectEndereco getCadastroProspectEndereco() {
        return cadastroProspectEndereco;
    }

    public static void setCadastroProspectEndereco(CadastroProspectEndereco cadastroProspectEndereco) {
        ProspectHelper.cadastroProspectEndereco = cadastroProspectEndereco;
    }

    public static CadastroProspectContatos getCadastroProspectContatos() {
        return cadastroProspectContatos;
    }

    public static void setCadastroProspectContatos(CadastroProspectContatos cadastroProspectContatos) {
        ProspectHelper.cadastroProspectContatos = cadastroProspectContatos;
    }

    public static CadastroProspectSegmentos getCadastroProspectSegmentos() {
        return cadastroProspectSegmentos;
    }

    public static void setCadastroProspectSegmentos(CadastroProspectSegmentos cadastroProspectSegmentos) {
        ProspectHelper.cadastroProspectSegmentos = cadastroProspectSegmentos;
    }

    public static CadastroProspectMotivos getCadastroProspectMotivos() {
        return cadastroProspectMotivos;
    }

    public static void setCadastroProspectMotivos(CadastroProspectMotivos cadastroProspectMotivos) {
        ProspectHelper.cadastroProspectMotivos = cadastroProspectMotivos;
    }

    public static CadastroProspectObservacoesComerciais getCadastroProspectObservacoesComerciais() {
        return cadastroProspectObservacoesComerciais;
    }

    public static void setCadastroProspectObservacoesComerciais(CadastroProspectObservacoesComerciais cadastroProspectObservacoesComerciais) {
        ProspectHelper.cadastroProspectObservacoesComerciais = cadastroProspectObservacoesComerciais;
    }

    public static CadastroProspectFotoSalvar getCadastroProspectFotoSalvar() {
        return cadastroProspectFotoSalvar;
    }

    public static void setCadastroProspectFotoSalvar(CadastroProspectFotoSalvar cadastroProspectFotoSalvar) {
        ProspectHelper.cadastroProspectFotoSalvar = cadastroProspectFotoSalvar;
    }

    public static List<Segmento> getSegmentos() {
        return segmentos;
    }

    public static void setSegmentos(List<Segmento> segmentos) {
        ProspectHelper.segmentos = segmentos;
    }

    public static List<MotivoNaoCadastramento> getMotivos() {
        return motivos;
    }

    public static void setMotivos(List<MotivoNaoCadastramento> motivos) {
        ProspectHelper.motivos = motivos;
    }

    public static List<Pais> getPaises() {
        return paises;
    }

    public static void setPaises(List<Pais> paises) {
        ProspectHelper.paises = paises;
    }

    public static List<Municipio> getMunicipios() {
        return municipios;
    }

    public static void setMunicipios(List<Municipio> municipios) {
        ProspectHelper.municipios = municipios;
    }

    public static int getPosicaoPais() {
        return posicaoPais;
    }

    public static void setPosicaoPais(int posicaoPais) {
        ProspectHelper.posicaoPais = posicaoPais;
    }

    public static int getPosicaoUf() {
        return posicaoUf;
    }

    public static void setPosicaoUf(int posicaoUf) {
        ProspectHelper.posicaoUf = posicaoUf;
    }

    public static int getPosicaoMunicipio() {
        return posicaoMunicipio;
    }

    public static void setPosicaoMunicipio(int posicaoMunicipio) {
        ProspectHelper.posicaoMunicipio = posicaoMunicipio;
    }

    public static void clear() {
        prospect = null;
        segmentos = null;
        motivos = null;
        paises = null;
        municipios = null;
        cadastroProspectGeral = null;
        cadastroProspectEndereco = null;
        cadastroProspectContatos = null;
        cadastroProspectSegmentos = null;
        cadastroProspectMotivos = null;
        cadastroProspectObservacoesComerciais = null;
        cadastroProspectFotoSalvar = null;
        posicaoPais = -1;
        posicaoUf = -1;
        posicaoMunicipio = -1;
        System.gc();
    }
}
