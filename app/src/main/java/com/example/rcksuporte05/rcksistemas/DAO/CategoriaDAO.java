package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.rcksuporte05.rcksistemas.model.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private DBHelper db;

    public CategoriaDAO(DBHelper db) {
        this.db = db;
    }

    public void atualizaCategoria(Categoria categoria) {
        ContentValues content = new ContentValues();

        content.put("ID_EMPRESA", categoria.getIdEmpresa());
        content.put("ATIVO", categoria.getAtivo());
        content.put("NOME_CATEGORIA", categoria.getNomeCategoria());
        content.put("USUARIO_ID", categoria.getUsuarioId());

        if (categoria.getIdCategoria() != 0 && db.contagem("SELECT COUNT(ID_CATEGORIA) FROM TBL_CADASTRO_CATEGORIA WHERE ID_CATEGORIA = " + categoria.getIdCategoria()) > 0) {
            content.put("ID_CATEGORIA", categoria.getIdCategoria());
            db.atualizaDados("TBL_CADASTRO_CATEGORIA", content, "ID_CATEGORIA = " + categoria.getIdCategoria());
        } else {
            content.put("ID_CATEGORIA", categoria.getIdCategoria());
            db.salvarDados("TBL_CADASTRO_CATEGORIA", content);
        }
    }

    public List<Categoria> listaCategoria() {
        List<Categoria> listaCategoria = new ArrayList<>();
        Cursor cursor;

        cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_CATEGORIA ORDER BY ID_CATEGORIA;");
        cursor.moveToFirst();
        do {
            Categoria categoria = new Categoria();

            categoria.setIdCategoria(cursor.getInt(cursor.getColumnIndex("ID_CATEGORIA")));
            categoria.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
            categoria.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            categoria.setNomeCategoria(cursor.getString(cursor.getColumnIndex("NOME_CATEGORIA")));
            categoria.setUsuarioId(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));

            listaCategoria.add(categoria);
        } while (cursor.moveToNext());
        return listaCategoria;
    }
}
