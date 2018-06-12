package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;

public class CadastroFinanceiroResumoDAO {
    private DBHelper db;

    public CadastroFinanceiroResumoDAO(DBHelper db) {
        this.db = db;
    }

    public void atualizarCadastroFinanceiroResumo(CadastroFinanceiroResumo cadastroFinanceiroResumo) {
        ContentValues content = new ContentValues();

        content.put("ID_CADASTRO", cadastroFinanceiroResumo.getIdCadastro());
        content.put("LIMITE_CREDITO", cadastroFinanceiroResumo.getLimiteCredito());
        content.put("FINANCEIRO_VENCIDO", cadastroFinanceiroResumo.getFinanceiroVencido());
        content.put("FINANCEIRO_VENCER", cadastroFinanceiroResumo.getFinanceiroVencer());
        content.put("FINANCEIRO_QUITADO", cadastroFinanceiroResumo.getFinanceiroQuitado());
        content.put("PEDIDOS_LIBERADOS", cadastroFinanceiroResumo.getPedidosLiberados());
        content.put("LIMITE_UTILIZADO", cadastroFinanceiroResumo.getLimiteUtilizado());
        content.put("LIMITE_DISPONIVEL", cadastroFinanceiroResumo.getLimiteDisponivel());
        content.put("USUARIO_ID", cadastroFinanceiroResumo.getUsuarioId());
        content.put("USUARIO_NOME", cadastroFinanceiroResumo.getUsuarioNome());
        content.put("USUARIO_DATA", cadastroFinanceiroResumo.getUsuarioData());
        content.put("DATA_ULTIMA_ATUALIZACAO", db.pegaDataHoraAtual());

        if (cadastroFinanceiroResumo.getIdCadastro() != 0 && db.contagem("SELECT COUNT(ID_CADASTRO) FROM TBL_CADASTRO_FINANCEIRO_RESUMO WHERE ID_CADASTRO = " + cadastroFinanceiroResumo.getIdCadastro() + ";") > 0) {
            db.atualizaDados("TBL_CADASTRO_FINANCEIRO_RESUMO", content, "ID_CADASTRO = " + cadastroFinanceiroResumo.getIdCadastro());
        } else {
            db.salvarDados("TBL_CADASTRO_FINANCEIRO_RESUMO", content);
        }
    }

    public CadastroFinanceiroResumo listaCadastroFinanceiroResumo(int idCadastro) {
        CadastroFinanceiroResumo cadastroFinanceiroResumo = new CadastroFinanceiroResumo();

        Cursor cursor;

        cursor = db.listaDados("SELECT * FROM TBL_CADASTRO_FINANCEIRO_RESUMO WHERE ID_CADASTRO = " + idCadastro);
        cursor.moveToFirst();
        do {
            cadastroFinanceiroResumo.setIdCadastro(cursor.getInt(cursor.getColumnIndex("ID_CADASTRO")));
            cadastroFinanceiroResumo.setLimiteCredito(cursor.getFloat(cursor.getColumnIndex("LIMITE_CREDITO")));
            cadastroFinanceiroResumo.setFinanceiroVencido(cursor.getFloat(cursor.getColumnIndex("FINANCEIRO_VENCIDO")));
            cadastroFinanceiroResumo.setFinanceiroVencer(cursor.getFloat(cursor.getColumnIndex("FINANCEIRO_VENCER")));
            cadastroFinanceiroResumo.setFinanceiroQuitado(cursor.getFloat(cursor.getColumnIndex("FINANCEIRO_QUITADO")));
            cadastroFinanceiroResumo.setPedidosLiberados(cursor.getFloat(cursor.getColumnIndex("PEDIDOS_LIBERADOS")));
            cadastroFinanceiroResumo.setLimiteUtilizado(cursor.getFloat(cursor.getColumnIndex("LIMITE_UTILIZADO")));
            cadastroFinanceiroResumo.setLimiteDisponivel(cursor.getFloat(cursor.getColumnIndex("LIMITE_DISPONIVEL")));
            cadastroFinanceiroResumo.setUsuarioId(cursor.getInt(cursor.getColumnIndex("USUARIO_ID")));
            cadastroFinanceiroResumo.setUsuarioNome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
            cadastroFinanceiroResumo.setUsuarioData(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
            cadastroFinanceiroResumo.setDataUltimaAtualizacao(cursor.getString(cursor.getColumnIndex("DATA_ULTIMA_ATUALIZACAO")));
        } while (cursor.moveToNext());

        return cadastroFinanceiroResumo;
    }
}
