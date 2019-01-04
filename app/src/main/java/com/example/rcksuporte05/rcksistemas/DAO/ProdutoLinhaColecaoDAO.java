package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;

import com.example.rcksuporte05.rcksistemas.model.ProdutoLinhaColecao;

public class ProdutoLinhaColecaoDAO {
    private DBHelper db;

    public ProdutoLinhaColecaoDAO(DBHelper db) {
        this.db = db;
    }

    public void atualizaProdutoLinhaColecao(ProdutoLinhaColecao produtoLinhaColecao) {
        ContentValues content = new ContentValues();

        content.put("ATIVO", produtoLinhaColecao.getAtivo());
        content.put("ID_LINHA_COLECAO", produtoLinhaColecao.getIdLinhaColecao());
        content.put("NOME_DESCRICAO_LINHA", produtoLinhaColecao.getNomeDescricaoLinha());
        content.put("USUARIO_ID", produtoLinhaColecao.getUsuarioId());
        content.put("USUARIO_NOME", produtoLinhaColecao.getUsuarioNome());
        content.put("USUARIO_DATA", produtoLinhaColecao.getUsuarioData());

        db.salvarDados("TBL_PRODUTO_LINHA_COLECAO", content);
    }
}
