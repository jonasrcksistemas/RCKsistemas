package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.Promocao;

import java.util.ArrayList;
import java.util.List;

public class PromocaoDAO {
    private DBHelper db;

    public PromocaoDAO(DBHelper db) {
        this.db = db;
    }

    public void atualizaPromocao(Promocao promocao) {
        ContentValues content = new ContentValues();

        content.put("ID_EMPRESA", promocao.getIdEmpresa());
        content.put("NUMERO_CLIENTES", promocao.getNumeroClientes());
        content.put("NUMERO_PRODUTOS", promocao.getNumeroProdutos());
        content.put("ATIVO", promocao.getAtivo());
        content.put("APLICACAO_CLIENTE", promocao.getAplicacaoCliente());
        content.put("APLICACAO_PRODUTO", promocao.getAplicacaoProduto());
        content.put("DESCONTO_PERC", promocao.getDescontoPerc());
        content.put("DATA_INICIO_PROMOCAO", promocao.getDataInicioPromocao());
        content.put("DATA_FIM_PROMOCAO", promocao.getDataFimPromocao());
        content.put("NOME_PROMOCAO", promocao.getNomePromocao());
        content.put("USUARIO_ID", promocao.getUsuarioId());
        content.put("USUARIO_NOME", promocao.getUsuarioNome());
        content.put("USUARIO_DATA", promocao.getUsuarioData());

        if (promocao.getIdPromocao() != 0 && this.db.contagem("SELECT COUNT(ID_PROMOCAO) FROM TBL_PROMOCAO_CAB WHERE ID_PROMOCAO = " + promocao.getIdPromocao()) > 0) {
            content.put("ID_PROMOCAO", promocao.getIdPromocao());
            db.atualizaDados("TBL_PROMOCAO_CAB", content, "ID_PROMOCAO = " + promocao.getIdPromocao());
        } else {
            content.put("ID_PROMOCAO", promocao.getIdPromocao());
            db.salvarDados("TBL_PROMOCAO_CAB", content);
        }
    }

    public List<Promocao> listaPromocaoTodosClientes() {
        PromocaoProdutoDAO promocaoProdutoDAO = new PromocaoProdutoDAO(db);
        Cursor cursor = db.listaDados("SELECT * FROM TBL_PROMOCAO_CAB WHERE APLICACAO_CLIENTE = 0 AND ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice() + " ORDER BY APLICACAO_PRODUTO DESC, DESCONTO_PERC DESC;");

        List<Promocao> listaPromocao = listaPromocao(cursor);

        for (Promocao promocao : listaPromocao) {
            if (promocao.getAplicacaoProduto() > 0)
                promocao.setListaPromoProduto(promocaoProdutoDAO.listaPromocaoProduto(promocao.getIdPromocao()));
        }
        return listaPromocao;
    }

    public List<Promocao> listaPromocaoClienteEspecifico() {
        PromocaoClienteDAO promocaoClienteDAO = new PromocaoClienteDAO(db);
        PromocaoProdutoDAO promocaoProdutoDAO = new PromocaoProdutoDAO(db);
        Cursor cursor = db.listaDados("SELECT * FROM TBL_PROMOCAO_CAB WHERE APLICACAO_CLIENTE = 1 AND ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice() + " ORDER BY APLICACAO_PRODUTO DESC, DESCONTO_PERC DESC;");

        List<Promocao> listaPromocao = listaPromocao(cursor);

        for (Promocao promocao : listaPromocao) {
            if (promocao.getAplicacaoCliente() > 0)
                promocao.setListaPromoCliente(promocaoClienteDAO.listaPromocaoCliente(promocao.getIdPromocao()));
            if (promocao.getAplicacaoProduto() > 0)
                promocao.setListaPromoProduto(promocaoProdutoDAO.listaPromocaoProduto(promocao.getIdPromocao()));
        }

        return listaPromocao;
    }

    public List<Promocao> listaPromocao(Cursor cursor) {
        List<Promocao> listaPromocao = new ArrayList<>();

        cursor.moveToFirst();
        do {
            Promocao promocao = new Promocao();

            promocao.setIdPromocao(cursor.getInt(cursor.getColumnIndex("ID_PROMOCAO")));
            promocao.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
            promocao.setNumeroClientes(cursor.getInt(cursor.getColumnIndex("NUMERO_CLIENTES")));
            promocao.setNumeroProdutos(cursor.getInt(cursor.getColumnIndex("NUMERO_PRODUTOS")));
            promocao.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            promocao.setAplicacaoCliente(cursor.getInt(cursor.getColumnIndex("APLICACAO_CLIENTE")));
            promocao.setAplicacaoProduto(cursor.getInt(cursor.getColumnIndex("APLICACAO_PRODUTO")));
            promocao.setDescontoPerc(cursor.getFloat(cursor.getColumnIndex("DESCONTO_PERC")));
            promocao.setDataInicioPromocao(cursor.getString(cursor.getColumnIndex("DATA_INICIO_PROMOCAO")));
            promocao.setDataFimPromocao(cursor.getString(cursor.getColumnIndex("DATA_FIM_PROMOCAO")));
            promocao.setNomePromocao(cursor.getString(cursor.getColumnIndex("NOME_PROMOCAO")));
            promocao.setUsuarioId(cursor.getInt(cursor.getColumnIndex("USUARIO_ID")));
            promocao.setUsuarioNome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
            promocao.setUsuarioData(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));

            listaPromocao.add(promocao);
        } while (cursor.moveToNext());
        return listaPromocao;
    }
}
