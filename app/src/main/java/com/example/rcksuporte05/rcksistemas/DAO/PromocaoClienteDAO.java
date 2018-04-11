package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.PromocaoCliente;

import java.util.ArrayList;
import java.util.List;

public class PromocaoClienteDAO {
    private DBHelper db;

    public PromocaoClienteDAO(DBHelper db) {
        this.db = db;
    }

    public void atualizaPromocaoCliente(PromocaoCliente promocaoCliente) {
        ContentValues content = new ContentValues();

        content.put("ID_PROMOCAO", promocaoCliente.getIdPromocao());
        content.put("ID_CADASTRO", promocaoCliente.getIdCadastro());
        content.put("ID_EMPRESA", promocaoCliente.getIdEmpresa());
        content.put("ATIVO", promocaoCliente.getAtivo());
        content.put("USUARIO_ID", promocaoCliente.getUsuarioId());
        content.put("USUARIO_NOME", promocaoCliente.getUsuarioNome());
        content.put("USUARIO_DATA", promocaoCliente.getUsuarioData());

        if ((promocaoCliente.getIdPromocao() != 0 && promocaoCliente.getIdCadastro() != 0) && this.db.contagem("SELECT COUNT(ID_PROMOCAO) FROM TBL_PROMOCAO_CLIENTE WHERE ID_PROMOCAO = " + promocaoCliente.getIdPromocao() + " AND ID_CADASTRO = " + promocaoCliente.getIdCadastro()) > 0) {
            content.put("ID_PROMOCAO", promocaoCliente.getIdPromocao());
            db.atualizaDados("TBL_PROMOCAO_CLIENTE", content, "ID_PROMOCAO = " + promocaoCliente.getIdPromocao());
        } else {
            content.put("ID_PROMOCAO", promocaoCliente.getIdPromocao());
            db.salvarDados("TBL_PROMOCAO_CLIENTE", content);
        }
    }

    public List<PromocaoCliente> listaPromocaoCliente(int idPromocao) {
        List<PromocaoCliente> listaPromocaoCliente = new ArrayList<>();
        Cursor cursor;

        cursor = db.listaDados("SELECT * FROM TBL_PROMOCAO_CLIENTE WHERE ID_PROMOCAO = " + idPromocao + " AND ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice() + " AND ATIVO = 'S';");
        cursor.moveToFirst();
        do {
            PromocaoCliente promocaoCliente = new PromocaoCliente();

            promocaoCliente.setIdPromocao(cursor.getInt(cursor.getColumnIndex("ID_PROMOCAO")));
            promocaoCliente.setIdCadastro(cursor.getInt(cursor.getColumnIndex("ID_CADASTRO")));
            promocaoCliente.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
            promocaoCliente.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            promocaoCliente.setUsuarioId(cursor.getInt(cursor.getColumnIndex("USUARIO_ID")));
            promocaoCliente.setUsuarioNome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
            promocaoCliente.setUsuarioData(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));

            listaPromocaoCliente.add(promocaoCliente);
        } while (cursor.moveToNext());
        return listaPromocaoCliente;
    }
}
