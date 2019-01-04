package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.rcksuporte05.rcksistemas.model.CampanhaComClientes;

import java.util.ArrayList;
import java.util.List;

public class CampanhaComClientesDAO {
    private DBHelper db;

    public CampanhaComClientesDAO(DBHelper db) {
        this.db = db;
    }

    public void atualizaCampanhaComClientes(CampanhaComClientes campanhaComClientes) {
        ContentValues content = new ContentValues();

        content.put("ID_EMPRESA", campanhaComClientes.getIdEmpresa());
        content.put("ID_CAMPANHA", campanhaComClientes.getIdCampanha());
        content.put("ID_CLIENTE", campanhaComClientes.getIdCliente());
        content.put("USUARIO_ID", campanhaComClientes.getUsuarioId());
        content.put("USUARIO_NOME", campanhaComClientes.getUsuarioNome());
        content.put("USUARIO_DATA", campanhaComClientes.getUsuarioData());

        db.salvarDados("TBL_CAMPANHA_COM_CLIENTES", content);
    }

    public List<CampanhaComClientes> listaCampanhaComClientes() {
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CAMPANHA_COM_CLIENTES ORDER BY ID_CAMPANHA DESC;");

        return listaCampanhaComClientes(cursor);
    }

    private List<CampanhaComClientes> listaCampanhaComClientes(Cursor cursor) {
        List<CampanhaComClientes> listaCampanhaComClientes = new ArrayList<>();

        cursor.moveToFirst();
        do {
            CampanhaComClientes campanhaComClientes = new CampanhaComClientes();

            campanhaComClientes.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
            campanhaComClientes.setIdCampanha(cursor.getInt(cursor.getColumnIndex("ID_CAMPANHA")));
            campanhaComClientes.setIdCliente(cursor.getInt(cursor.getColumnIndex("ID_CLIENTE")));
            campanhaComClientes.setUsuarioId(cursor.getInt(cursor.getColumnIndex("USUARIO_ID")));
            campanhaComClientes.setUsuarioNome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
            campanhaComClientes.setUsuarioData(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));

            listaCampanhaComClientes.add(campanhaComClientes);
        } while (cursor.moveToNext());
        return listaCampanhaComClientes;
    }
}
