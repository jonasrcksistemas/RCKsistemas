package com.example.rcksuporte05.rcksistemas.Helper;

import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.MotivoNaoCadastramento;
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
import com.example.rcksuporte05.rcksistemas.interfaces.ActivityCadastroProspect;

import java.util.List;

/**
 * Created by RCK 03 on 31/01/2018.
 */

public class ProspectHelper {
    private static Prospect prospect;
    private static List<Segmento> segmentos;
    private static List<MotivoNaoCadastramento> motivos;
    private static List<Pais> paises;
    private static ActivityCadastroProspect activityMain;
    private static CadastroProspectGeral cadastroProspectGeral;
    private static CadastroProspectEndereco cadastroProspectEndereco;
    private static CadastroProspectContatos cadastroProspectContatos;
    private static CadastroProspectSegmentos cadastroProspectSegmentos;
    private static CadastroProspectMotivos cadastroProspectMotivos;
    private static CadastroProspectObservacoesComerciais cadastroProspectObservacoesComerciais;
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

    public static CadastroProspectGeral getCadastroProspectGeral() {
        return cadastroProspectGeral;
    }

    public static ActivityCadastroProspect getActivityMain() {
        return activityMain;
    }

    public static void setActivityMain(ActivityCadastroProspect activityMain) {
        ProspectHelper.activityMain = activityMain;
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

    public static void moveTela(int position){
        mViewPager = (ViewPager) activityMain.findViewById(R.id.vp_tabs_prospect);
        if(mViewPager.getCurrentItem() != position){
            mViewPager.setCurrentItem(position);
        }
    }

    public static void salvarProspect(){
        //Tela geral
        if(prospect.getPessoa_f_j() == null || prospect.getPessoa_f_j().equals("")){
            moveTela(0);
            cadastroProspectGeral.edtNomeClienteProspect.requestFocus();
            Toast.makeText(cadastroProspectGeral.getContext(),"Escolher Pessoa Fisica ou Juridica é obrigatorio", Toast.LENGTH_LONG).show();
        }

        if(prospect.getNome_cadastro() == null || prospect.getNome_cadastro().trim().equals("")){
            moveTela(0);
            cadastroProspectGeral.edtNomeClienteProspect.setError("Campo Obrigatorio");
            cadastroProspectGeral.edtNomeClienteProspect.requestFocus();
        }

        if(prospect.getNome_fantasia() == null || prospect.getNome_fantasia().equals("")){
            moveTela(0);
            cadastroProspectGeral.edtNomeFantasiaProspect.setError("Campo Obrigatorio");
            cadastroProspectGeral.edtNomeFantasiaProspect.requestFocus();
        }
        if(prospect.getCpf_cnpj() == null || prospect.getCpf_cnpj().equals("")){
           moveTela(0);
           cadastroProspectGeral.edtCpfCnpjProspect.setError("Campo Obrigatorio");
           cadastroProspectGeral.edtCpfCnpjProspect.requestFocus();
        }
        if(prospect.getPessoa_f_j() !=null && prospect.getPessoa_f_j().equals("J")){
            if(prospect.getInscri_estadual() == null || !prospect.getInscri_estadual().equals("")){
               moveTela(0);
               cadastroProspectGeral.edtInscEstadualProspect.setError("Campo Obrigatorio");
               cadastroProspectGeral.edtInscEstadualProspect.requestFocus();

            }
        }



        //tela 2 Endereços

        //tela 3 Contato
        if (prospect.getListaContato().size() < 1){
            moveTela(2);
            try{
                Toast.makeText(cadastroProspectMotivos.getContext(),"Pelo menos 1 contato é Obrigatorio!", Toast.LENGTH_LONG).show();
            }catch (NullPointerException e){
                Toast.makeText(activityMain,"Pelo menos 1 contato é Obrigatorio!", Toast.LENGTH_LONG).show();
            }

        }

        //Tela 4 seguimentos
        if(prospect.getSegmento() == null){
            moveTela(3);
            Toast.makeText(activityMain,"Escolha o segmento!", Toast.LENGTH_LONG).show();
        }else if(prospect.getSegmento().getNomeSetor().toLowerCase().contains("outros")){
//            cadastroProspectSegmentos.edtOutrosSegmentosProspect.setError("Campo Obrigado quando Outros Selecionado");
//            cadastroProspectSegmentos.edtOutrosSegmentosProspect.requestFocus();
        }


        //tela 5 motivo não cadastramento
        if(prospect.getMotivoNaoCadastramento() == null){
            moveTela(4);
            Toast.makeText(activityMain,"Escolha um Motivo para o Não cadastramento!", Toast.LENGTH_LONG).show();
        }

        //tela 6 Observações Comerciais
        if(prospect.getReferenciaComercial().size() >= 2){
           moveTela(5);
           Toast.makeText(activityMain, "Insira Pelo Menos 2 referencias comercias ",  Toast.LENGTH_LONG).show();
        }


    }

    public static void clear(){
        prospect = null;
        segmentos = null;
        motivos = null;
        paises = null;
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
