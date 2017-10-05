package com.example.rcksuporte05.rcksistemas.bo;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;

import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.interfaces.MainActivity;

import java.util.List;

/**
 * Created by RCK 03 on 04/10/2017.
 */

public class UsuarioBO {


    public boolean sincronizaNobanco(List<Usuario> usuarioList, Context context){
        DBHelper db = new DBHelper(context);

       try {

           for (Usuario usuario : usuarioList) {
               if(db.contagem("SELECT COUNT(*) FROM TBL_WEB_USUARIO WHERE ID_USUARIO = " +usuario.getId_usuario()) <= 0){
                   db.inserirTBL_WEB_USUARIO(usuario);
               }else {
                   db.atualizarTBL_WEB_USUARIO(usuario);
               }
           }
       }catch (final Exception e){
           return false;
       }



        return true;
    }



}
