package com.example.rcksuporte05.rcksistemas.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by RCK 03 on 11/12/2017.
 */
public class MascaraMonetaria  {


    public static String mascaraReal(Float valor){
        Locale meuLocal = new Locale( "pt", "BR" );
        NumberFormat nf = NumberFormat.getCurrencyInstance(meuLocal);

        String valorString = String.valueOf(valor);

        String valorFinal = nf.format(Float.parseFloat(valorString));


        return valorFinal;
    }


    public static String mascaraReal(String valor){
        Locale meuLocal = new Locale( "pt", "BR" );
        NumberFormat nf = NumberFormat.getCurrencyInstance(meuLocal);

        String valorFinal = nf.format(Float.parseFloat(valor));

        return valorFinal;
    }
}


