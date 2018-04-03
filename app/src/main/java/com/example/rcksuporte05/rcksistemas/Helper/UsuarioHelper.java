package com.example.rcksuporte05.rcksistemas.Helper;

import com.example.rcksuporte05.rcksistemas.model.Usuario;

/**
 * Created by RCK 03 on 06/10/2017.
 */

public class UsuarioHelper {
    private static Usuario usuario;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        UsuarioHelper.usuario = usuario;
    }
}
