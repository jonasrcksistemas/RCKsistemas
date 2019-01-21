package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;

import com.example.rcksuporte05.rcksistemas.model.CadastroCondicoesPag;

public class CadastroCondicoesPagDAO {
    private DBHelper db;

    public CadastroCondicoesPagDAO(DBHelper db) {
        this.db = db;
    }

    public void atualizaCadastroCondicoesPag(CadastroCondicoesPag cadastroCondicoesPag) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("ID_CONDICAO", cadastroCondicoesPag.getIdCondicao());
        contentValues.put("ID_CADASTRO", cadastroCondicoesPag.getIdCadastro());
        contentValues.put("USUARIO_ID", cadastroCondicoesPag.getUsuarioId());
        contentValues.put("USUARIO_NOME", cadastroCondicoesPag.getUsuarioNome());
        contentValues.put("USUARIO_DATA", cadastroCondicoesPag.getUsuarioData());

        if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO_CONDICOES_PAG WHERE ID_CONDICAO = " + cadastroCondicoesPag.getIdCondicao() + " AND ID_CADASTRO = " + cadastroCondicoesPag.getIdCadastro() + ";") > 0) {
            db.atualizaDados("TBL_CADASTRO_CONDICOES_PAG", contentValues, "WHERE ID_CONDICAO = " + cadastroCondicoesPag.getIdCondicao() + " AND ID_CADASTRO = " + cadastroCondicoesPag.getIdCadastro());
        } else {
            db.salvarDados("TBL_CADASTRO_CONDICOES_PAG", contentValues);
        }
    }
}
