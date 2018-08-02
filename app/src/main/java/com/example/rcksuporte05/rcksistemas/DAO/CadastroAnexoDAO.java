package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;

import java.util.ArrayList;
import java.util.List;

public class CadastroAnexoDAO {
    private DBHelper db;

    public CadastroAnexoDAO(DBHelper db) {
        this.db = db;
    }

    public void atualizarCadastroAnexo(CadastroAnexo cadastroAnexo) {
        ContentValues content = new ContentValues();

        content.put("ID_ANEXO_SERVIDOR", cadastroAnexo.getIdAnexoServidor());
        content.put("ID_ENTIDADE", cadastroAnexo.getIdEntidade());
        content.put("ID_CADASTRO", cadastroAnexo.getIdCadastro());
        content.put("ID_CADASTRO_SERVIDOR", cadastroAnexo.getIdCadastroServidor());
        content.put("NOME_ANEXO", cadastroAnexo.getNomeAnexo());
        content.put("ANEXO", cadastroAnexo.getAnexo());
        content.put("EXCLUIDO", cadastroAnexo.getExcluido());
        content.put("PRINCIPAL", cadastroAnexo.getPrincipal());

        if (cadastroAnexo.getIdAnexo() != 0 && db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_CADASTRO_ANEXOS WHERE ID_ANEXO = " + cadastroAnexo.getIdAnexo() + ";") > 0) {
            db.atualizaDados("TBL_CADASTRO_ANEXOS", content, "ID_ANEXO = " + cadastroAnexo.getIdAnexo() + ";");
        } else {
            db.salvarDados("TBL_CADASTRO_ANEXOS", content);
        }
    }

    public List<CadastroAnexo> enviarCadastroAnexo(int idCadastro) {
        return retornarCadastros(db.listaDados("SELECT * FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + idCadastro + " ORDER BY ID_ANEXO;"));
    }

    public List<CadastroAnexo> listaCadastroAnexoCliente(int idCadastro) {
        return retornarCadastros(db.listaDados("SELECT * FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + idCadastro + " AND ID_ENTIDADE = 1 AND EXCLUIDO = 'N' ORDER BY ID_ANEXO;"));
    }

    public List<CadastroAnexo> listaCadastroAnexoProspect(int idCadastro) {
        return retornarCadastros(db.listaDados("SELECT * FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + idCadastro + " AND ID_ENTIDADE = 10 AND EXCLUIDO = 'N' ORDER BY ID_ANEXO;"));
    }

    private List<CadastroAnexo> retornarCadastros(Cursor cursor) {
        List<CadastroAnexo> listaCadastroAnexo = new ArrayList<>();

        cursor.moveToFirst();
        do {
            CadastroAnexo cadastroAnexo = new CadastroAnexo();

            cadastroAnexo.setIdAnexo(cursor.getInt(cursor.getColumnIndex("ID_ANEXO")));
            cadastroAnexo.setIdAnexoServidor(cursor.getInt(cursor.getColumnIndex("ID_ANEXO_SERVIDOR")));
            cadastroAnexo.setIdEntidade(cursor.getInt(cursor.getColumnIndex("ID_ENTIDADE")));
            cadastroAnexo.setIdCadastro(cursor.getInt(cursor.getColumnIndex("ID_CADASTRO")));
            cadastroAnexo.setIdCadastroServidor(cursor.getInt(cursor.getColumnIndex("ID_CADASTRO_SERVIDOR")));
            cadastroAnexo.setNomeAnexo(cursor.getString(cursor.getColumnIndex("NOME_ANEXO")));
            cadastroAnexo.setAnexo(cursor.getString(cursor.getColumnIndex("ANEXO")));
            cadastroAnexo.setExcluido(cursor.getString(cursor.getColumnIndex("EXCLUIDO")));
            cadastroAnexo.setPrincipal(cursor.getString(cursor.getColumnIndex("PRINCIPAL")));

            listaCadastroAnexo.add(cadastroAnexo);
        } while (cursor.moveToNext());
        return listaCadastroAnexo;
    }
}
