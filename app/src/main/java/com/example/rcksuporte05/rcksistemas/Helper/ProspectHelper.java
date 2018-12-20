package com.example.rcksuporte05.rcksistemas.Helper;

import android.graphics.Bitmap;
import android.location.Location;
import android.support.v4.view.ViewPager;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityCadastroProspect;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectEndereco;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectFotoSalvar;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectGeral;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroProspectMotivos;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.model.Pais;
import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.model.Segmento;

import java.util.List;

/**
 * Created by RCK 03 on 31/01/2018.
 */

public class ProspectHelper {
    public static Bitmap imagem1;
    public static Bitmap imagem2;
    public static String checkin;
    public static Location localizacao;
    private static Prospect prospect;
    private static Cliente vendedor;
    private static List<Segmento> segmentos;
    private static List<MotivoNaoCadastramento> motivos;
    private static List<Pais> paises;
    private static ActivityCadastroProspect activityMain;
    private static CadastroProspectGeral cadastroProspectGeral;
    private static CadastroProspectEndereco cadastroProspectEndereco;
    private static CadastroProspectMotivos cadastroProspectMotivos;
    private static CadastroProspectFotoSalvar cadastroProspectFotoSalvar;
    private static ViewPager mViewPager;
    private static int posicaoPais = -1;
    private static int posicaoUf = -1;
    private static int posicaoMunicipio = -1;

    public static Prospect getProspect() {
        return prospect;
    }

    public static void setProspect(Prospect prospect) {
        ProspectHelper.prospect = prospect;
    }

    public static Cliente getVendedor() {
        return vendedor;
    }

    public static void setVendedor(Cliente vendedor) {
        ProspectHelper.vendedor = vendedor;
    }

    public static CadastroProspectGeral getCadastroProspectGeral() {
        return cadastroProspectGeral;
    }

    public static void setCadastroProspectGeral(CadastroProspectGeral cadastroProspectGeral) {
        ProspectHelper.cadastroProspectGeral = cadastroProspectGeral;
    }

    public static ActivityCadastroProspect getActivityMain() {
        return activityMain;
    }

    public static void setActivityMain(ActivityCadastroProspect activityMain) {
        ProspectHelper.activityMain = activityMain;
    }

    public static CadastroProspectEndereco getCadastroProspectEndereco() {
        return cadastroProspectEndereco;
    }

    public static void setCadastroProspectEndereco(CadastroProspectEndereco cadastroProspectEndereco) {
        ProspectHelper.cadastroProspectEndereco = cadastroProspectEndereco;
    }

    public static CadastroProspectMotivos getCadastroProspectMotivos() {
        return cadastroProspectMotivos;
    }

    public static void setCadastroProspectMotivos(CadastroProspectMotivos cadastroProspectMotivos) {
        ProspectHelper.cadastroProspectMotivos = cadastroProspectMotivos;
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

    public static Bitmap getImagem1() {
        return imagem1;
    }

    public static void setImagem1(Bitmap imagem1) {
        ProspectHelper.imagem1 = imagem1;
    }

    public static Bitmap getImagem2() {
        return imagem2;
    }

    public static void setImagem2(Bitmap imagem2) {
        ProspectHelper.imagem2 = imagem2;
    }

    public static String getCheckin() {
        return checkin;
    }

    public static void setCheckin(String checkin) {
        ProspectHelper.checkin = checkin;
    }

    public static Location getLocalizacao() {
        return localizacao;
    }

    public static void setLocalizacao(Location localizacao) {
        ProspectHelper.localizacao = localizacao;
    }

    public static void moveTela(int position) {
        mViewPager = (ViewPager) activityMain.findViewById(R.id.vp_tabs_prospect);
        if (mViewPager.getCurrentItem() != position) {
            mViewPager.setCurrentItem(position);
        }
    }

    public static void clear() {
        prospect = null;
        segmentos = null;
        motivos = null;
        paises = null;
        cadastroProspectGeral = null;
        cadastroProspectEndereco = null;
        cadastroProspectMotivos = null;
        cadastroProspectFotoSalvar = null;
        posicaoPais = -1;
        posicaoUf = -1;
        posicaoMunicipio = -1;
        imagem1 = null;
        imagem2 = null;
        localizacao = null;
        checkin = null;
    }
}
