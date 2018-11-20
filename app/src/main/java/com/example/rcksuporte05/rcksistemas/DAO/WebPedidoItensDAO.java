package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;

import java.util.ArrayList;
import java.util.List;

public class WebPedidoItensDAO {
    private DBHelper db;

    public WebPedidoItensDAO(DBHelper db) {
        this.db = db;
    }

    public void inserirTBL_WEB_PEDIDO_ITENS(WebPedidoItens webPedidoItem) {
        ContentValues content = new ContentValues();

        content.put("ID_WEB_ITEM_SERVIDOR", webPedidoItem.getId_web_item_servidor());
        content.put("ID_PEDIDO", webPedidoItem.getId_pedido());
        content.put("ID_PRODUTO", webPedidoItem.getId_produto());
        content.put("ID_EMPRESA", webPedidoItem.getId_empresa());
        content.put("QUANTIDADE", webPedidoItem.getQuantidade());
        content.put("VALOR_UNITARIO", webPedidoItem.getVenda_preco());
        content.put("VALOR_BRUTO", webPedidoItem.getValor_bruto());
        content.put("VALOR_DESCONTO_REAL", webPedidoItem.getValor_desconto_real());
        content.put("VALOR_DESCONTO_PER_ADD", webPedidoItem.getValor_desconto_per_add());
        content.put("VALOR_DESCONTO_REAL_ADD", webPedidoItem.getValor_desconto_real_add());
        content.put("VALOR_TOTAL", webPedidoItem.getValor_total());
        content.put("DATA_MOVIMENTACAO", db.pegaDataAtual());
        content.put("USUARIO_LANCAMENTO_ID", webPedidoItem.getUsuario_lancamento_id());
        content.put("USUARIO_LANCAMENTO_DATA", db.pegaDataHoraAtual());
        content.put("ID_ITEM_DESCONTO", webPedidoItem.getId_item_desconto());
        content.put("PONTOS_UNITARIO", webPedidoItem.getPontos_unitario());
        content.put("PONTOS_TOTAL", webPedidoItem.getPontos_total());
        content.put("PONTOS_COEFICIENTE", webPedidoItem.getPontos_coeficiente());
        content.put("COMISSAO_PERCENTUAL", webPedidoItem.getComissao_percentual());
        content.put("COMISSAO_VALOR", webPedidoItem.getComissao_valor());
        content.put("VALOR_BONUS_CREDOR", webPedidoItem.getValor_bonus_credor());
        content.put("PERC_BONUS_CREDOR", webPedidoItem.getPerc_bonus_credor());
        content.put("VALOR_DESCONTO_PER_ORIG", webPedidoItem.getValor_desconto_per_orig());
        content.put("VALOR_DESCONTO_REAL_ORIG", webPedidoItem.getValor_desconto_real_orig());
        content.put("VALOR_DESCONTO_PER_ADD_ORIG", webPedidoItem.getValor_desconto_per_add_orig());
        content.put("VALOR_DESCONTO_REAL_ADD_ORIG", webPedidoItem.getValor_desconto_real_add_orig());
        content.put("ID_TABELA_PRECO_FAIXA_ORIG", webPedidoItem.getId_tabela_preco_faixa_orig());
        content.put("VALOR_TOTAL_ORIG", webPedidoItem.getValor_total_orig());
        content.put("PONTOS_UNITARIO_ORIG", webPedidoItem.getPontos_unitario_orig());
        content.put("PONTOS_COEFICIENTE_ORIG", webPedidoItem.getPontos_coeficiente_orig());
        content.put("COMISSAO_PERCENTUAL_ORIG", webPedidoItem.getComissao_percentual_orig());
        content.put("VALOR_BONUS_CREDOR_ORIG", webPedidoItem.getValor_bonus_credor_orig());
        content.put("PERC_BONUS_CREDOR_ORIG", webPedidoItem.getPerc_bonus_credor_orig());
        content.put("COMISSAO_VALOR_ORIG", webPedidoItem.getComissao_valor_orig());
        content.put("PONTOS_TOTAL_ORIG", webPedidoItem.getPontos_total_orig());
        content.put("PONTOS_COR_ORIG", webPedidoItem.getPontos_cor_orig());
        content.put("VALOR_PRECO_PAGO", webPedidoItem.getValor_preco_pago());
        content.put("TIPO_DESCONTO", webPedidoItem.getTipoDesconto());
        content.put("VALOR_DESCONTO_PER", webPedidoItem.getValor_desconto_per());
        content.put("NOME_PRODUTO", webPedidoItem.getNome_produto());
        content.put("PRODUTO_MATERIA_PRIMA", webPedidoItem.getProduto_materia_prima());
        content.put("PRODUTO_TERCERIZACAO", webPedidoItem.getProduto_tercerizacao());

        db.salvarDados("TBL_WEB_PEDIDO_ITENS", content);
        System.gc();
    }

    public void atualizarTBL_WEB_PEDIDO_ITENS(WebPedidoItens webPedidoItem) {
        ContentValues content = new ContentValues();

        content.put("ID_WEB_ITEM", webPedidoItem.getId_web_item());
        content.put("ID_WEB_ITEM_SERVIDOR", webPedidoItem.getId_web_item_servidor());
        content.put("ID_PEDIDO", webPedidoItem.getId_pedido());
        content.put("ID_PRODUTO", webPedidoItem.getId_produto());
        content.put("ID_EMPRESA", webPedidoItem.getId_empresa());
        content.put("QUANTIDADE", webPedidoItem.getQuantidade());
        content.put("VALOR_UNITARIO", webPedidoItem.getValor_unitario());
        content.put("VALOR_BRUTO", webPedidoItem.getValor_bruto());
        content.put("VALOR_DESCONTO_REAL", webPedidoItem.getValor_desconto_real());
        content.put("VALOR_DESCONTO_PER_ADD", webPedidoItem.getValor_desconto_per_add());
        content.put("VALOR_DESCONTO_REAL_ADD", webPedidoItem.getValor_desconto_real_add());
        content.put("VALOR_TOTAL", webPedidoItem.getValor_total());
        content.put("DATA_MOVIMENTACAO", webPedidoItem.getData_movimentacao());
        content.put("USUARIO_LANCAMENTO_ID", webPedidoItem.getUsuario_lancamento_id());
        content.put("USUARIO_LANCAMENTO_DATA", webPedidoItem.getUsuario_lancamento_data());
        content.put("ID_ITEM_DESCONTO", webPedidoItem.getId_item_desconto());
        content.put("PONTOS_UNITARIO", webPedidoItem.getPontos_unitario());
        content.put("PONTOS_TOTAL", webPedidoItem.getPontos_total());
        content.put("PONTOS_COEFICIENTE", webPedidoItem.getPontos_coeficiente());
        content.put("PONTOS_COR", webPedidoItem.getPontos_cor());
        content.put("COMISSAO_PERCENTUAL", webPedidoItem.getComissao_percentual());
        content.put("COMISSAO_VALOR", webPedidoItem.getComissao_valor());
        content.put("VALOR_BONUS_CREDOR", webPedidoItem.getValor_bonus_credor());
        content.put("PERC_BONUS_CREDOR", webPedidoItem.getPerc_bonus_credor());
        content.put("ID_TABELA_PRECO", webPedidoItem.getId_tabela_preco());
        content.put("VALOR_DESCONTO_PER_ORIG", webPedidoItem.getValor_desconto_per_orig());
        content.put("VALOR_DESCONTO_REAL_ORIG", webPedidoItem.getValor_desconto_real_orig());
        content.put("VALOR_DESCONTO_PER_ADD_ORIG", webPedidoItem.getValor_desconto_per_add_orig());
        content.put("VALOR_DESCONTO_REAL_ADD_ORIG", webPedidoItem.getValor_desconto_real_add_orig());
        content.put("ID_TABELA_PRECO_FAIXA_ORIG", webPedidoItem.getId_tabela_preco_faixa_orig());
        content.put("VALOR_TOTAL_ORIG", webPedidoItem.getValor_total_orig());
        content.put("PONTOS_UNITARIO_ORIG", webPedidoItem.getPontos_unitario_orig());
        content.put("PONTOS_COEFICIENTE_ORIG", webPedidoItem.getPontos_coeficiente_orig());
        content.put("COMISSAO_PERCENTUAL_ORIG", webPedidoItem.getComissao_percentual_orig());
        content.put("VALOR_BONUS_CREDOR_ORIG", webPedidoItem.getValor_bonus_credor_orig());
        content.put("PERC_BONUS_CREDOR_ORIG", webPedidoItem.getPerc_bonus_credor_orig());
        content.put("COMISSAO_VALOR_ORIG", webPedidoItem.getComissao_valor_orig());
        content.put("PONTOS_TOTAL_ORIG", webPedidoItem.getPontos_total_orig());
        content.put("PONTOS_COR_ORIG", webPedidoItem.getPontos_cor_orig());
        content.put("VALOR_PRECO_PAGO", webPedidoItem.getValor_preco_pago());
        content.put("TIPO_DESCONTO", webPedidoItem.getTipoDesconto());
        content.put("VALOR_DESCONTO_PER", webPedidoItem.getValor_desconto_per());
        content.put("NOME_PRODUTO", webPedidoItem.getNome_produto());
        content.put("PRODUTO_MATERIA_PRIMA", webPedidoItem.getProduto_materia_prima());
        content.put("PRODUTO_TERCERIZACAO", webPedidoItem.getProduto_tercerizacao());

        if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE ID_WEB_ITEM = " + webPedidoItem.getId_web_item()) <= 0) {
            db.salvarDados("TBL_WEB_PEDIDO_ITENS", content);
        } else {
            db.atualizaDados("TBL_WEB_PEDIDO_ITENS", content, "ID_WEB_ITEM = " + webPedidoItem.getId_web_item());
        }

        System.gc();
    }

    public List<WebPedidoItens> listaWebPedidoItens(String SQL) {
        List<WebPedidoItens> lista = new ArrayList<>();
        Cursor cursor;

        cursor = db.listaDados(SQL);
        cursor.moveToFirst();
        do {
            WebPedidoItens webPedidoItens = new WebPedidoItens();

            webPedidoItens.setId_web_item(cursor.getString(cursor.getColumnIndex("ID_WEB_ITEM")));
            webPedidoItens.setId_web_item_servidor(cursor.getString(cursor.getColumnIndex("ID_WEB_ITEM_SERVIDOR")));
            webPedidoItens.setValor_preco_pago(cursor.getString(cursor.getColumnIndex("VALOR_PRECO_PAGO")));
            webPedidoItens.setId_pedido(cursor.getString(cursor.getColumnIndex("ID_PEDIDO")));
            webPedidoItens.setId_produto(cursor.getString(cursor.getColumnIndex("ID_PRODUTO")));
            webPedidoItens.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
            webPedidoItens.setQuantidade(cursor.getString(cursor.getColumnIndex("QUANTIDADE")));
            webPedidoItens.setValor_unitario(cursor.getFloat(cursor.getColumnIndex("VALOR_UNITARIO")));
            webPedidoItens.setValor_bruto(cursor.getString(cursor.getColumnIndex("VALOR_BRUTO")));
            webPedidoItens.setValor_desconto_per(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER")));
            webPedidoItens.setValor_desconto_real(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL")));
            webPedidoItens.setValor_desconto_per_add(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ADD")));
            webPedidoItens.setValor_desconto_real_add(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ADD")));
            webPedidoItens.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
            webPedidoItens.setData_movimentacao(cursor.getString(cursor.getColumnIndex("DATA_MOVIMENTACAO")));
            webPedidoItens.setUsuario_lancamento_id(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_ID")));
            webPedidoItens.setUsuario_lancamento_data(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_DATA")));
            webPedidoItens.setId_item_desconto(cursor.getString(cursor.getColumnIndex("ID_ITEM_DESCONTO")));
            webPedidoItens.setPontos_unitario(cursor.getString(cursor.getColumnIndex("PONTOS_UNITARIO")));
            webPedidoItens.setPontos_total(cursor.getString(cursor.getColumnIndex("PONTOS_TOTAL")));
            webPedidoItens.setPontos_coeficiente(cursor.getString(cursor.getColumnIndex("PONTOS_COEFICIENTE")));
            webPedidoItens.setPontos_cor(cursor.getString(cursor.getColumnIndex("PONTOS_COR")));
            webPedidoItens.setComissao_percentual(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL")));
            webPedidoItens.setComissao_valor(cursor.getString(cursor.getColumnIndex("COMISSAO_VALOR")));
            webPedidoItens.setValor_bonus_credor(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CREDOR")));
            webPedidoItens.setPerc_bonus_credor(cursor.getString(cursor.getColumnIndex("PERC_BONUS_CREDOR")));
            webPedidoItens.setId_tabela_preco(cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO")));
            webPedidoItens.setValor_desconto_per_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ORIG")));
            webPedidoItens.setValor_desconto_real_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ORIG")));
            webPedidoItens.setValor_desconto_per_add_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ADD_ORIG")));
            webPedidoItens.setValor_desconto_real_add_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ADD_ORIG")));
            webPedidoItens.setId_tabela_preco_faixa_orig(cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO_FAIXA_ORIG")));
            webPedidoItens.setValor_total_orig(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL_ORIG")));
            webPedidoItens.setPontos_unitario_orig(cursor.getString(cursor.getColumnIndex("PONTOS_UNITARIO_ORIG")));
            webPedidoItens.setPontos_coeficiente_orig(cursor.getString(cursor.getColumnIndex("PONTOS_COEFICIENTE_ORIG")));
            webPedidoItens.setComissao_percentual_orig(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL_ORIG")));
            webPedidoItens.setValor_bonus_credor_orig(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CREDOR_ORIG")));
            webPedidoItens.setPerc_bonus_credor_orig(cursor.getString(cursor.getColumnIndex("PERC_BONUS_CREDOR_ORIG")));
            webPedidoItens.setComissao_valor_orig(cursor.getString(cursor.getColumnIndex("COMISSAO_VALOR_ORIG")));
            webPedidoItens.setPontos_total_orig(cursor.getString(cursor.getColumnIndex("PONTOS_TOTAL_ORIG")));
            webPedidoItens.setPontos_cor_orig(cursor.getString(cursor.getColumnIndex("PONTOS_COR_ORIG")));
            webPedidoItens.setTipoDesconto(cursor.getString(cursor.getColumnIndex("TIPO_DESCONTO")));
            webPedidoItens.setProduto_materia_prima(cursor.getString(cursor.getColumnIndex("PRODUTO_MATERIA_PRIMA")));
            webPedidoItens.setProduto_tercerizacao(cursor.getString(cursor.getColumnIndex("PRODUTO_TERCERIZACAO")));
            try {
                webPedidoItens.setProduto(db.listaProduto("SELECT * FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'").get(0));
                webPedidoItens.setProdutoBase(true);
            } catch (CursorIndexOutOfBoundsException e) {
                webPedidoItens.setProdutoBase(false);
                webPedidoItens.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
                e.printStackTrace();
            }

            lista.add(webPedidoItens);
            System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();

        return lista;
    }
}
