package com.example.rcksuporte05.rcksistemas.bo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import java.util.List;

/**
 * Created by RCK 03 on 06/10/2017.
 */

public class UsuarioBO {
    DBHelper db;

    public boolean sincronizaNobanco(List<Usuario> usuarioList, Context context) {
        db = new DBHelper(context);

        try {

            db.alterar("DELETE FROM TBL_WEB_USUARIO");
            for (Usuario usuario : usuarioList) {
                db.inserirTBL_WEB_USUARIO(usuario);
            }

        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }


    /*
      foi criado para poder executar um inner join para montar um usuario com token no login
      automatico.
    */
    public Usuario buscarUsuarioLogin(Context context) {
        db = new DBHelper(context);

        SQLiteDatabase con = db.getReadableDatabase();
        Cursor cursor;
        cursor = con.rawQuery("SELECT U.*, L.TOKEN FROM TBL_WEB_USUARIO U INNER JOIN TBL_LOGIN L ON U.LOGIN = L.LOGIN", null);
        cursor.moveToFirst();

        Usuario usuario = new Usuario();

        usuario.setId_usuario(cursor.getString(cursor.getColumnIndex("U.ID_USUARIO")));
        usuario.setAtivo(cursor.getString(cursor.getColumnIndex("U.ATIVO")));
        usuario.setNome_usuario(cursor.getString(cursor.getColumnIndex("U.NOME_USUARIO")));
        usuario.setLogin(cursor.getString(cursor.getColumnIndex("U.LOGIN")));
        usuario.setSenha(cursor.getString(cursor.getColumnIndex("U.SENHA")));
        usuario.setSenha_confirma(cursor.getString(cursor.getColumnIndex("U.SENHA_CONFIRMA")));
        usuario.setData_cadastro(cursor.getString(cursor.getColumnIndex("U.DATA_CADASTRO")));
        usuario.setUsuario_cadatro(cursor.getString(cursor.getColumnIndex("U.USUARIO_CADATRO")));
        usuario.setData_alterado(cursor.getString(cursor.getColumnIndex("U.DATA_ALTERADO")));
        usuario.setUsuario_alterou(cursor.getString(cursor.getColumnIndex("U.USUARIO_ALTEROU")));
        usuario.setAparece_cad_usuario(cursor.getString(cursor.getColumnIndex("U.APARECE_CAD_USUARIO")));
        usuario.setCliente_lista_todos(cursor.getString(cursor.getColumnIndex("U.CLIENTE_LISTA_TODOS")));
        usuario.setCliente_lista_setor(cursor.getString(cursor.getColumnIndex("U.CLIENTE_LISTA_SETOR")));
        usuario.setCliente_lista_representante(cursor.getString(cursor.getColumnIndex("U.CLIENTE_LISTA_REPRESENTANTE")));
        usuario.setPedido_lista_todos(cursor.getString(cursor.getColumnIndex("U.PEDIDO_LISTA_TODOS")));
        usuario.setPedido_lista_setor(cursor.getString(cursor.getColumnIndex("U.PEDIDO_LISTA_SETOR")));
        usuario.setPedido_lista_representante(cursor.getString(cursor.getColumnIndex("U.PEDIDO_LISTA_REPRESENTANTE")));
        usuario.setMensagem_lista_financeiro(cursor.getString(cursor.getColumnIndex("U.MENSAGEM_LISTA_FINANCEIRO")));
        usuario.setMensagem_lista_todos(cursor.getString(cursor.getColumnIndex("U.MENSAGEM_LISTA_TODOS")));
        usuario.setMensagem_lista_setor(cursor.getString(cursor.getColumnIndex("U.MENSAGEM_LISTA_SETOR")));
        usuario.setMensagem_lista_representante(cursor.getString(cursor.getColumnIndex("U.MENSAGEM_LISTA_REPRESENTANTE")));
        usuario.setOrcamento_lista_todos(cursor.getString(cursor.getColumnIndex("U.ORCAMENTO_LISTA_TODOS")));
        usuario.setOrcamento_lista_setor(cursor.getString(cursor.getColumnIndex("U.ORCAMENTO_LISTA_SETOR")));
        usuario.setOrcamento_lista_representante(cursor.getString(cursor.getColumnIndex("U.ORCAMENTO_LISTA_REPRESENTANTE")));
        usuario.setUsuario_lista_todos(cursor.getString(cursor.getColumnIndex("U.USUARIO_LISTA_TODOS")));
        usuario.setUsuario_lista_setor(cursor.getString(cursor.getColumnIndex("U.USUARIO_LISTA_SETOR")));
        usuario.setUsuario_lista_representante(cursor.getString(cursor.getColumnIndex("U.USUARIO_LISTA_REPRESENTANTE")));
        usuario.setExcluido(cursor.getString(cursor.getColumnIndex("U.EXCLUIDO")));
        usuario.setId_setor(cursor.getString(cursor.getColumnIndex("U.ID_SETOR")));
        usuario.setId_quando_vendedor(cursor.getString(cursor.getColumnIndex("U.ID_QUANDO_VENDEDOR")));
        usuario.setAparelho_id(cursor.getString(cursor.getColumnIndex("U.APARELHO_ID")));
        usuario.setToken(cursor.getString(cursor.getColumnIndex("L.TOKEN")));
        usuario.setIdEmpresaMultiDevice(cursor.getString(cursor.getColumnIndex("U.ID_EMPRESA_MULTI_DEVICE")));

        return usuario;
    }

}
