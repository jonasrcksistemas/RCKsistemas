package com.example.rcksuporte05.rcksistemas.Helper;

import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.activity.CadastroClienteMain;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente1;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente2;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente3;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente4;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente5;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente6;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente7;
import com.example.rcksuporte05.rcksistemas.fragment.CadastroCliente8;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

/**
 * Created by RCKSUPORTE05 on 10/10/2017.
 */

public class ClienteHelper {
    private static Cliente cliente;
    private static CadastroClienteMain cadastroClienteMain;
    private static CadastroCliente1 cadastroCliente1;
    private static CadastroCliente2 cadastroCliente2;
    private static CadastroCliente3 cadastroCliente3;
    private static CadastroCliente4 cadastroCliente4;
    private static CadastroCliente5 cadastroCliente5;
    private static CadastroCliente6 cadastroCliente6;
    private static CadastroCliente7 cadastroCliente7;
    private static CadastroCliente8 cadastroCliente8;
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

    public static CadastroCliente6 getCadastroCliente6() {
        return cadastroCliente6;
    }

    public static void setCadastroCliente6(CadastroCliente6 cadastroCliente6) {
        ClienteHelper.cadastroCliente6 = cadastroCliente6;
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

    private static void moveTela(int position) {
        if (cadastroClienteMain.mViewPager.getCurrentItem() != position)
            cadastroClienteMain.mViewPager.setCurrentItem(position);
    }

    public static boolean salvarCliente() {
        boolean verificaMovimento = true;

        if (cliente.getPessoa_f_j() == null || cliente.getPessoa_f_j().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                Toast.makeText(cadastroCliente1.getContext(), "Escolher Pessoa Fisica ou Juridica é obrigatorio", Toast.LENGTH_LONG).show();
                moveTela(0);
            }
        }

        if (cliente.getNome_cadastro() == null || cliente.getNome_cadastro().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(0);
            }
            cadastroCliente1.edtNomeCliente.setError("Campo Obrigatorio");
            cadastroCliente1.edtNomeCliente.requestFocus();
        }

        if (cliente.getNome_fantasia() == null || cliente.getNome_fantasia().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(0);
            }
            cadastroCliente1.edtNomeFantasia.setError("Campo Obrigatorio");
            cadastroCliente1.edtNomeFantasia.requestFocus();
        }

        if (cliente.getCpf_cnpj() == null || cliente.getCpf_cnpj().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(0);
            }
            cadastroCliente1.edtCpfCnpj.setError("Campo Obrigatorio");
            cadastroCliente1.edtCpfCnpj.requestFocus();
        } else if (cliente.getCpf_cnpj().equals("F")) {
            if (!MascaraUtil.isValidCPF(cliente.getCpf_cnpj())) {
                if (verificaMovimento) {
                    verificaMovimento = false;
                    moveTela(0);
                }
                cadastroCliente1.edtCpfCnpj.setError("CPF Inválido");
                cadastroCliente1.edtCpfCnpj.requestFocus();
            }
        } else if (cliente.getCpf_cnpj().equals("J")) {
            if (!MascaraUtil.isValidCNPJ(cliente.getCpf_cnpj())) {
                if (verificaMovimento) {
                    verificaMovimento = false;
                    moveTela(0);
                }
                cadastroCliente1.edtCpfCnpj.setError("CNPJ Inválido");
                cadastroCliente1.edtCpfCnpj.requestFocus();
            }
        }

        if (cliente.getPessoa_f_j() != null && cliente.getPessoa_f_j().equals("J")) {
            if (Integer.parseInt(cliente.getInd_da_ie_destinatario()) == 1) {
                if (cliente.getInscri_estadual() == null || cliente.getInscri_estadual().trim().isEmpty()) {
                    if (verificaMovimento) {
                        verificaMovimento = false;
                        moveTela(0);
                    }
                    cadastroCliente1.edtInscEstadual.setError("Campo Obrigatorio");
                    cadastroCliente1.edtInscEstadual.requestFocus();
                }
            }
        }
        //Tela 2

        if (cliente.getEndereco() == null || cliente.getEndereco().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(1);
            }
            cadastroCliente2.edtEndereco.requestFocus();
            cadastroCliente2.edtEndereco.setError("Campo Obrigatorio");
        }


        if (cliente.getEndereco_numero() == null || cliente.getEndereco_numero().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(1);
            }
            cadastroCliente2.edtNumero.requestFocus();
            cadastroCliente2.edtNumero.setError("Campo Obrigatorio");
        }


        if (cliente.getEndereco_bairro() == null || cliente.getEndereco_bairro().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(1);
            }
            cadastroCliente2.edtBairro.requestFocus();
            cadastroCliente2.edtBairro.setError("Campo Obrigatorio");
        }


        if (cliente.getEndereco_cep() == null || cliente.getEndereco_cep().trim().isEmpty()) {
            if (verificaMovimento) {
                verificaMovimento = false;
                moveTela(1);
            }
            cadastroCliente2.edtCep.requestFocus();
            cadastroCliente2.edtCep.setError("Campo Obrigatorio");
        }

        //Tela 4

        if (cliente.getListaContato().size() < 1) {
            if (verificaMovimento) {
                verificaMovimento = false;
                Toast.makeText(cadastroClienteMain, "Pelo menos 1 contato é Obrigatorio!", Toast.LENGTH_LONG).show();
                moveTela(3);
            }
        }

        //Tela 5

        if (cliente.getSegmento() == null) {
            if (verificaMovimento) {
                verificaMovimento = false;
                Toast.makeText(cadastroClienteMain, "Escolha o segmento!", Toast.LENGTH_LONG).show();
                moveTela(4);
            }
        } else if (cliente.getSegmento().getNomeSetor().toLowerCase().contains("outros")) {
            if (cliente.getSegmento().getDescricaoOutros() == null || cliente.getSegmento().getDescricaoOutros().equals("")) {
                if (verificaMovimento) {
                    verificaMovimento = false;
                    Toast.makeText(cadastroClienteMain, "Observação obrigatorio quando opção Outros selecionada", Toast.LENGTH_LONG).show();
                    moveTela(4);
                }
                cadastroCliente5.edtOutrosSegmentosCliente.setError("Observação obrigatorio quando opção Outros selecionada");
                cadastroCliente5.edtOutrosSegmentosCliente.requestFocus();
            }
        }

        //Tela 6

        if (cliente.getReferenciasComerciais().size() < 2) {
            if (verificaMovimento) {
                verificaMovimento = false;
                Toast.makeText(cadastroClienteMain, "Insira Pelo Menos 2 referencias comercias ", Toast.LENGTH_LONG).show();
                moveTela(5);
            }
        }

        return verificaMovimento;
    }

    public static void clear() {
        cliente = null;
        cadastroClienteMain = null;
        cadastroCliente1 = null;
        cadastroCliente2 = null;
        cadastroCliente3 = null;
        cadastroCliente4 = null;
        cadastroCliente5 = null;
        posicaoPais = -1;
        posicaoUf = -1;
        posicaoMunicipio = -1;
        posicaoCobrancaPais = -1;
        posicaoCobrancaUf = -1;
        posicaoCobrancaMunicipio = -1;
    }
}
