package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;

import java.util.ArrayList;
import java.util.List;

public class CampanhaComercialCabDAO {
    private DBHelper db;

    public CampanhaComercialCabDAO(DBHelper db) {
        this.db = db;
    }

    public void atualizaCampanhaComercialCab(CampanhaComercialCab campanhaComercialCab) {
        ContentValues content = new ContentValues();

        content.put("ATIVO", campanhaComercialCab.getAtivo());
        content.put("ID_CAMPANHA", campanhaComercialCab.getIdCampanha());
        content.put("ID_TIPO_CAMPANHA", campanhaComercialCab.getIdTipoCampanha());
        content.put("ID_BASE_CAMPANHA", campanhaComercialCab.getIdBaseCampanha());
        content.put("ID_EMPRESA", campanhaComercialCab.getIdEmpresa());
        content.put("DATA_INICIO", campanhaComercialCab.getDataInicio());
        content.put("DATA_FIM", campanhaComercialCab.getDataFim());
        content.put("NOME_CAMPANHA", campanhaComercialCab.getNomeCampanha());
        content.put("DESCRICAO_CAMPANHA", campanhaComercialCab.getDescricaoCampanha());
        content.put("USUARIO_ID", campanhaComercialCab.getUsuarioId());
        content.put("USUARIO_NOME", campanhaComercialCab.getUsuarioNome());
        content.put("USUARIO_DATA", campanhaComercialCab.getUsuarioData());

        db.salvarDados("TBL_CAMPANHA_COMERCIAL_CAB", content);
    }

    public CampanhaComercialCab listaCampanhaComercialCab(int idCapanha) {
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CAMPANHA_COMERCIAL_CAB WHERE ID_CAMPANHA = " + idCapanha + ";");

        return listaCampanhaComercialCab(cursor).get(0);
    }

    public List<CampanhaComercialCab> listaCampanhaComercialCab() {
        Cursor cursor = db.listaDados("SELECT * FROM TBL_CAMPANHA_COMERCIAL_CAB ORDER BY NOME_CAMPANHA;");

        return listaCampanhaComercialCab(cursor);
    }

    public List<CampanhaComercialCab> listaCampanhaComercialCab(Cliente cliente, Produto produto) {
        Cursor cursor = db.listaDados("SELECT CAB.* FROM TBL_CAMPANHA_COMERCIAL_CAB CAB " +
                "INNER JOIN TBL_CAMPANHA_COM_CLIENTES CLIENTE ON CAB.ID_CAMPANHA = CLIENTE.ID_CAMPANHA " +
                "INNER JOIN TBL_CAMPANHA_COMERCIAL_ITENS ITENS ON ITENS.ID_CAMPANHA = CAB.ID_CAMPANHA " +
                "WHERE CLIENTE.ID_CLIENTE = " + cliente.getId_cadastro_servidor() + " AND (ITENS.ID_LINHA_PRODUTO = " + produto.getIdLinhaColecao() + " OR ITENS.ID_PRODUTO_VENDA = '" + produto.getId_produto() + "');");

        return listaCampanhaComercialCab(cursor);
    }

    public boolean verificaCampanha(Cliente cliente, WebPedidoItens webPedidoItem) {
        try {
            if (webPedidoItem.getIdCampanha() > 0) {
                Cursor cursor = db.listaDados("SELECT CAB.* FROM TBL_CAMPANHA_COMERCIAL_CAB CAB " +
                        "INNER JOIN TBL_CAMPANHA_COM_CLIENTES CLIENTE ON CAB.ID_CAMPANHA = CLIENTE.ID_CAMPANHA " +
                        "INNER JOIN TBL_CAMPANHA_COMERCIAL_ITENS ITENS ON ITENS.ID_CAMPANHA = CAB.ID_CAMPANHA " +
                        "WHERE CLIENTE.ID_CLIENTE = " + cliente.getId_cadastro_servidor() + " AND (ITENS.ID_LINHA_PRODUTO = " + webPedidoItem.getIdLinhaColecao() + " OR ITENS.ID_PRODUTO_VENDA = '" + webPedidoItem.getId_produto() + "') AND CAB.ID_CAMPANHA = " + webPedidoItem.getIdCampanha() + ";");

                return listaCampanhaComercialCab(cursor).size() > 0;
            }
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private List<CampanhaComercialCab> listaCampanhaComercialCab(Cursor cursor) {
        List<CampanhaComercialCab> listaCampanhaComercialCab = new ArrayList<>();

        cursor.moveToNext();

        do {
            CampanhaComercialCab campanhaComercialCab = new CampanhaComercialCab();

            campanhaComercialCab.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            campanhaComercialCab.setIdCampanha(cursor.getInt(cursor.getColumnIndex("ID_CAMPANHA")));
            campanhaComercialCab.setIdTipoCampanha(cursor.getInt(cursor.getColumnIndex("ID_TIPO_CAMPANHA")));
            campanhaComercialCab.setIdBaseCampanha(cursor.getInt(cursor.getColumnIndex("ID_BASE_CAMPANHA")));
            campanhaComercialCab.setIdEmpresa(cursor.getInt(cursor.getColumnIndex("ID_EMPRESA")));
            campanhaComercialCab.setDataInicio(cursor.getString(cursor.getColumnIndex("DATA_INICIO")));
            campanhaComercialCab.setDataFim(cursor.getString(cursor.getColumnIndex("DATA_FIM")));
            campanhaComercialCab.setNomeCampanha(cursor.getString(cursor.getColumnIndex("NOME_CAMPANHA")));
            campanhaComercialCab.setDescricaoCampanha(cursor.getString(cursor.getColumnIndex("DESCRICAO_CAMPANHA")));
            campanhaComercialCab.setUsuarioId(cursor.getInt(cursor.getColumnIndex("USUARIO_ID")));
            campanhaComercialCab.setUsuarioNome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
            campanhaComercialCab.setUsuarioData(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));

            if (db.contagem("SELECT COUNT(*) FROM TBL_CAMPANHA_COM_CLIENTES CAMP INNER JOIN TBL_CADASTRO CAD ON CAMP.ID_CLIENTE = CAD.ID_CADASTRO_SERVIDOR WHERE ID_CAMPANHA = " + campanhaComercialCab.getIdCampanha() + " AND CAMP.ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice() + " ORDER BY CAD.NOME_CADASTRO;") > 0) {
                boolean insere = true;
                for (int i = 0; listaCampanhaComercialCab.size() > i; i++) {
                    if (campanhaComercialCab.getIdCampanha() == listaCampanhaComercialCab.get(i).getIdCampanha()) {
                        insere = false;
                    }
                }
                if (insere)
                    listaCampanhaComercialCab.add(campanhaComercialCab);
            }
        } while (cursor.moveToNext());
        return listaCampanhaComercialCab;
    }
}
