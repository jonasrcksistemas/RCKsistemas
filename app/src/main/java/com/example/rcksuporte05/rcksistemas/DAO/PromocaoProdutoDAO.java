package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.PromocaoProduto;

import java.util.ArrayList;
import java.util.List;

public class PromocaoProdutoDAO {
    private DBHelper db;

    public PromocaoProdutoDAO(DBHelper db) {
        this.db = db;
    }

    public void atualizaPromocaoProduto(PromocaoProduto promocaoProduto) {
        ContentValues content = new ContentValues();

        content.put("ID_PROMOCAO", promocaoProduto.getIdPromocao());
        content.put("ID_PRODUTO", promocaoProduto.getIdProduto());
        content.put("ID_EMPRESA", promocaoProduto.getIdEmpresa());
        content.put("ATIVO", promocaoProduto.getAtivo());
        content.put("TIPO_DESCONTO", promocaoProduto.getTipoDesconto());
        content.put("DESCONTO_PERC", promocaoProduto.getDescontoPerc());
        content.put("DESCONTO_VALOR", promocaoProduto.getDescontoValor());
        content.put("PERC_COM_INTERNO", promocaoProduto.getPercComInterno());
        content.put("PERC_COM_EXTERNO", promocaoProduto.getPercComExterno());
        content.put("PERC_COM_EXPORTACAO", promocaoProduto.getPercComExportacao());
        content.put("USUARIO_ID", promocaoProduto.getUsuarioId());
        content.put("USUARIO_NOME", promocaoProduto.getUsuarioNome());
        content.put("USUARIO_DATA", promocaoProduto.getUsuarioData());

        if ((promocaoProduto.getIdPromocao() != 0 && !promocaoProduto.getIdProduto().equals("0")) && this.db.contagem("SELECT COUNT(ID_PROMOCAO) FROM TBL_PROMOCAO_PRODUTO WHERE ID_PROMOCAO = " + promocaoProduto.getIdPromocao() + " AND ID_PRODUTO = " + promocaoProduto.getIdProduto()) > 0) {
            content.put("ID_PROMOCAO", promocaoProduto.getIdPromocao());
            db.atualizaDados("TBL_PROMOCAO_PRODUTO", content, "ID_PROMOCAO = " + promocaoProduto.getIdPromocao());
        } else {
            content.put("ID_PROMOCAO", promocaoProduto.getIdPromocao());
            db.salvarDados("TBL_PROMOCAO_PRODUTO", content);
        }
    }

    public List<PromocaoProduto> listaPromocaoProduto(int idPromocao) {
        List<PromocaoProduto> listaPromocaoProduto = new ArrayList<>();
        Cursor cursor;

        cursor = db.listaDados("SELECT * FROM TBL_PROMOCAO_PRODUTO WHERE ID_PROMOCAO = " + idPromocao + " AND ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice() + " AND ATIVO = 'S';");
        cursor.moveToFirst();
        do {
            PromocaoProduto promocaoProduto = new PromocaoProduto();

            promocaoProduto.setIdPromocao(cursor.getInt(cursor.getColumnIndex("ID_PROMOCAO")));
            promocaoProduto.setIdProduto(cursor.getString(cursor.getColumnIndex("ID_PRODUTO")));
            promocaoProduto.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
            promocaoProduto.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            promocaoProduto.setTipoDesconto(cursor.getString(cursor.getColumnIndex("TIPO_DESCONTO")));
            promocaoProduto.setDescontoPerc(cursor.getFloat(cursor.getColumnIndex("DESCONTO_PERC")));
            promocaoProduto.setDescontoValor(cursor.getFloat(cursor.getColumnIndex("DESCONTO_VALOR")));
            promocaoProduto.setPercComInterno(cursor.getFloat(cursor.getColumnIndex("PERC_COM_INTERNO")));
            promocaoProduto.setPercComExterno(cursor.getFloat(cursor.getColumnIndex("PERC_COM_EXTERNO")));
            promocaoProduto.setPercComExportacao(cursor.getFloat(cursor.getColumnIndex("PERC_COM_EXPORTACAO")));
            promocaoProduto.setUsuarioId(cursor.getInt(cursor.getColumnIndex("USUARIO_ID")));
            promocaoProduto.setUsuarioNome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
            promocaoProduto.setUsuarioData(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));

            listaPromocaoProduto.add(promocaoProduto);
        } while (cursor.moveToNext());
        return listaPromocaoProduto;
    }
}
