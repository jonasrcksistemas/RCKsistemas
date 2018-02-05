package com.example.rcksuporte05.rcksistemas.util;

/**
 * Created by RCK 03 on 03/02/2018.
 */

public class MascaraTelefone {

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
}
