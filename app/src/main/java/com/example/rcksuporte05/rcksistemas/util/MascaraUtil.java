package com.example.rcksuporte05.rcksistemas.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by RCK 03 on 11/12/2017.
 */
public class MascaraUtil {
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static String mascaraReal(Float valor) {
        Locale meuLocal = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(meuLocal);
        String valorString = String.valueOf(valor);

        String valorFinal = nf.format(Float.parseFloat(valorString));

        return valorFinal;
    }


    public static String mascaraReal(String valor) {
        Locale meuLocal = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(meuLocal);

        String valorFinal = nf.format(Float.parseFloat(valor));

        return valorFinal;
    }

    public static String mascaraCPF(String valor) {
        String cpf = "";

        String valorLimpo = valor.trim().replaceAll("[^0-9]", "");
        if (!valorLimpo.equals("") && valorLimpo.length() == 11) {
            cpf = valorLimpo.substring(0, 3) + "." + valorLimpo.substring(3, 6) + "." + valorLimpo.substring(6, 9) + "-" + valorLimpo.substring(9, 11);
        }

        return cpf;
    }

    public static String mascaraCNPJ(String valor) {
        String cnpj = "";

        String valorLimpo = valor.trim().replaceAll("[^0-9]", "");
        if (!valorLimpo.equals("") && valorLimpo.length() == 14) {
            cnpj = valorLimpo.substring(0, 2) + "." + valorLimpo.substring(2, 5) + "." + valorLimpo.substring(5, 8) + "/" + valorLimpo.substring(8, 12) + "-" + valorLimpo.substring(12, 14);
        }

        return cnpj;
    }

    public static String formataTelefone(String telefone) {
        String telefoneRetorno;
        telefone = telefone.trim().replaceAll("[^0-9]", "");
        if (telefone.length() == 10) {
            telefoneRetorno = "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6, 10);
        } else if (telefone.length() == 11) {
            telefoneRetorno = "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7, 11);
        } else if (telefone.length() == 9 && !telefone.contains("-")) {
            telefoneRetorno = telefone.substring(0, 5) + "-" + telefone.substring(5, 9);
        } else if (telefone.length() == 8) {
            telefoneRetorno = telefone.substring(0, 4) + "-" + telefone.substring(4, 8);
        } else {
            telefoneRetorno = telefone;
        }

        return telefoneRetorno;
    }


    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11)) return false;

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    public static boolean isValidCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14)) return false;

        Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    public static String duasCasaDecimal(Float valor) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(valor).replace(",", ".");
    }

    public static String duasCasaDecimal(String valor) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Float.parseFloat(valor));
    }

    public static String mascaraVirgula(Float valor) {
        return mascaraReal(valor).replace("R$", "");
    }

    public static String mascaraVirgula(String valor) {
        return mascaraReal(valor).replace("R$", "");
    }

    public static String mascaraCep(String valor) {
        if (valor.length() == 8)
            return valor.substring(0, 5) + "-" + valor.substring(5, 8);
        else
            return valor;
    }

    public static String numeroZeros(String valor, int quantidade) {
        try {
            if (valor.length() < quantidade) {
                int diferenca = quantidade - valor.length();
                String zeros = "";
                for (int i = 0; diferenca > i; i++) {
                    zeros += "0";
                }
                return zeros + valor;
            } else {
                return valor;
            }
        } catch (Exception e) {
            return valor;
        }
    }
}


