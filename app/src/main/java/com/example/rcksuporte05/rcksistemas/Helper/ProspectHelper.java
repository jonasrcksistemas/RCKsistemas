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
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    public static void moveTela(int position) {
        mViewPager = (ViewPager) activityMain.findViewById(R.id.vp_tabs_prospect);
        if (mViewPager.getCurrentItem() != position) {
            mViewPager.setCurrentItem(position);
        }
    }

    public static boolean salvarProspect() {
        /*Esta variavel é usada para validar o movimento das frags, assim que movimenta, não movimenta outra frag
         */
        boolean verificaMovimento = true;

        //Tela geral
        if (prospect.getPessoa_f_j() == null || prospect.getPessoa_f_j().equals("")) {
            if (verificaMovimento) {
                verificaMovimento = false;
                Toast.makeText(cadastroProspectGeral.getContext(), "Escolher Pessoa Fisica ou Juridica é obrigatorio", Toast.LENGTH_LONG).show();
                moveTela(0);
            }
            cadastroProspectGeral.edtNomeClienteProspect.requestFocus();
        }


        if (prospect.getNome_cadastro() == null || prospect.getNome_cadastro().trim().equals("")) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(0);
            }
            cadastroProspectGeral.edtNomeClienteProspect.setError("Campo Obrigatorio");
            cadastroProspectGeral.edtNomeClienteProspect.requestFocus();
        }


        if (prospect.getNome_fantasia() == null || prospect.getNome_fantasia().equals("")) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(0);
            }
            cadastroProspectGeral.edtNomeFantasiaProspect.setError("Campo Obrigatorio");
            cadastroProspectGeral.edtNomeFantasiaProspect.requestFocus();
        }


        if (prospect.getCpf_cnpj() == null || prospect.getCpf_cnpj().equals("")) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(0);
            }
            cadastroProspectGeral.edtCpfCnpjProspect.setError("Campo Obrigatorio");
            cadastroProspectGeral.edtCpfCnpjProspect.requestFocus();
        }else if(prospect.getPessoa_f_j().equals("F")){
              if(!MascaraUtil.isValidCPF(prospect.getCpf_cnpj())){
                  if (verificaMovimento) {
                      verificaMovimento = false;
                      moveTela(0);
                  }
                  cadastroProspectGeral.edtCpfCnpjProspect.setError("CPF invalido");
                  cadastroProspectGeral.edtCpfCnpjProspect.requestFocus();
              }
        }else if(prospect.getPessoa_f_j().equals("J")){
                if (!MascaraUtil.isValidCNPJ(prospect.getCpf_cnpj())){
                    if (verificaMovimento) {
                        verificaMovimento = false;
                        moveTela(0);
                    }
                    cadastroProspectGeral.edtCpfCnpjProspect.setError("CNPJ invalido");
                    cadastroProspectGeral.edtCpfCnpjProspect.requestFocus();
                }
        }


        if (prospect.getPessoa_f_j() != null && prospect.getPessoa_f_j().equals("J")) {
            if(Integer.parseInt(prospect.getInd_da_ie_destinatario_prospect()) == 0){
                if (prospect.getInscri_estadual() == null || prospect.getInscri_estadual().trim().isEmpty()) {
                    if (verificaMovimento) {
                        verificaMovimento = false;
                        moveTela(0);
                    }
                    cadastroProspectGeral.edtInscEstadualProspect.setError("Campo Obrigatorio");
                    cadastroProspectGeral.edtInscEstadualProspect.requestFocus();
                }
            }
        }

        if (prospect.getDiaVisita() == null || prospect.getDiaVisita().trim().equals("")) {
                if (verificaMovimento) {
                    verificaMovimento = false;
                    Toast.makeText(activityMain, "Escolha um dia da semana para a Visita", Toast.LENGTH_LONG).show();
                    moveTela(0);
                }
        }

        //tela 2 Endereços
        if (prospect.getEndereco() == null || prospect.getEndereco().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(1);
            }
            cadastroProspectEndereco.edtEnderecoProspect.requestFocus();
            cadastroProspectEndereco.edtEnderecoProspect.setError("Campo Obrigatorio");
        }


        if (prospect.getEndereco_numero() == null || prospect.getEndereco_numero().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(1);
            }
            cadastroProspectEndereco.edtNumeroProspect.requestFocus();
            cadastroProspectEndereco.edtNumeroProspect.setError("Campo Obrigatorio");
        }


        if (prospect.getEndereco_bairro() == null || prospect.getEndereco_bairro().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(1);
            }
            cadastroProspectEndereco.edtBairroProspect.requestFocus();
            cadastroProspectEndereco.edtBairroProspect.setError("Campo Obrigatorio");
        }



        if (prospect.getEndereco_cep() == null || prospect.getEndereco_cep().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(1);
            }
            cadastroProspectEndereco.edtCep.requestFocus();
            cadastroProspectEndereco.edtCep.setError("Campo Obrigatorio");
        }

        if (prospect.getSituacaoPredio() == null || prospect.getSituacaoPredio().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                Toast.makeText(activityMain, "Informe a situação do Predio", Toast.LENGTH_LONG).show();
                moveTela(1);
            }
        }


        //tela 3 Contato
        if (prospect.getListaContato().size() < 1) {
            if (verificaMovimento) {
                verificaMovimento = false;
                Toast.makeText(activityMain, "Pelo menos 1 contato é Obrigatorio!", Toast.LENGTH_LONG).show();
                moveTela(2);
            }
        }


        //Tela 4 seguimentos
        if (prospect.getSegmento() == null) {
            if (verificaMovimento) {
                verificaMovimento = false;
                Toast.makeText(activityMain, "Escolha o segmento!", Toast.LENGTH_LONG).show();
                moveTela(3);
            }
        } else if (prospect.getSegmento().getNomeSetor().toLowerCase().contains("outros")) {
            if(verificaMovimento){
                verificaMovimento = false;
                Toast.makeText(activityMain, "Observação obrigatorio quando opção Outros selecionada", Toast.LENGTH_LONG).show();
                moveTela(3);
            }
            cadastroProspectSegmentos.edtOutrosSegmentosProspect.setError("Observação obrigatorio quando opção Outros selecionada");
            cadastroProspectSegmentos.edtOutrosSegmentosProspect.requestFocus();
        }


        //tela 5 motivo não cadastramento
        try {
            if (prospect.getMotivoNaoCadastramento() == null) {
                if (verificaMovimento) {
                    verificaMovimento = false;
                    Toast.makeText(activityMain, "Escolha um Motivo para o Não cadastramento!", Toast.LENGTH_LONG).show();
                    moveTela(4);
                }
            } else if (prospect.getMotivoNaoCadastramento().getMotivo().toLowerCase().contains("outros") && prospect.getMotivoNaoCadastramento().getDescricaoOutros().equals("")) {
                if (verificaMovimento) {
                    verificaMovimento = false;
                    Toast.makeText(activityMain, "Escolha um Motivo para o Não cadastramento!", Toast.LENGTH_LONG).show();
                    moveTela(4);
                }
                cadastroProspectMotivos.edtOutrosMotivosProspect.setError("Observação obrigatorio quando opção Outros selecionada");
                cadastroProspectMotivos.edtOutrosMotivosProspect.requestFocus();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            if (verificaMovimento) {
                verificaMovimento = false;
                Toast.makeText(activityMain, "Escolha um Motivo para o Não cadastramento!", Toast.LENGTH_LONG).show();
                moveTela(4);
            }
        }


        //tela 6 Observações Comerciais
        if (prospect.getReferenciasComerciais().size() < 2) {
            if (verificaMovimento) {
                verificaMovimento = false;
                Toast.makeText(activityMain, "Insira Pelo Menos 2 referencias comercias ", Toast.LENGTH_LONG).show();
                moveTela(5);
            }
        }


        //Tela 7 salvar foto
        if (verificaMovimento) {
            if (prospect.getDataRetorno() == null || prospect.getDataRetorno().trim().isEmpty()) {
                cadastroProspectFotoSalvar.edtDataRetorno.setBackgroundResource(R.drawable.borda_edittext_erro);
                Toast.makeText(activityMain, "Informe a data de Retorno", Toast.LENGTH_LONG).show();
                verificaMovimento = false;
            } else {
                Calendar dataAtual = new GregorianCalendar();
                Calendar dataRetorno = new GregorianCalendar();
                Date date = new Date();
                dataAtual.setTime(date);
                try {
                    dataRetorno.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(prospect.getDataRetorno()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String atual = new SimpleDateFormat("dd/MM/yyyy").format(dataAtual.getTime());
                String retorno = new SimpleDateFormat("dd/MM/yyyy").format(dataRetorno.getTime());
                if (dataAtual.getTime().after(dataRetorno.getTime())) {
                    cadastroProspectFotoSalvar.edtDataRetorno.setBackgroundResource(R.drawable.borda_edittext_erro);
                    Toast.makeText(activityMain, "A data deve ser posterior ao dia de hoje!", Toast.LENGTH_LONG).show();
                    verificaMovimento = false;
                }
            }
        }

        return verificaMovimento;

    }

    public static void clear() {
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
