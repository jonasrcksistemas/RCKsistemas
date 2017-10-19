package com.example.rcksuporte05.rcksistemas.bo;

import android.content.Context;

import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import java.util.List;

/**
 * Created by RCK 03 on 06/10/2017.
 */

public class UsuarioBO {

    public boolean sincronizaNobanco(List<Usuario> usuarioList, Context context){
        DBHelper db = new DBHelper(context);

        try {

            db.alterar("DELETE FROM TBL_WEB_USUARIO");
            for (Usuario usuario : usuarioList) {
                 db.inserirTBL_WEB_USUARIO(usuario);
            }

        }catch (final Exception e){
            e.printStackTrace();
            return false;
        }



        return true;
    }


}
