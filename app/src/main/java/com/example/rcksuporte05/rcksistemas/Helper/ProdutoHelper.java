package com.example.rcksuporte05.rcksistemas.Helper;

import com.example.rcksuporte05.rcksistemas.classes.Produto;

/**
 * Created by RCK 03 on 10/11/2017.
 */

public class ProdutoHelper {
     private static Produto produto;

    public static Produto getProduto() {
        return produto;
    }

    public static void setProduto(Produto produto) {
        ProdutoHelper.produto = produto;
    }

}
