package com.example.rcksuporte05.rcksistemas.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import com.example.rcksuporte05.rcksistemas.model.WebPedido;

import java.util.ArrayList;
import java.util.List;

public class WebPedidoDAO {
    private DBHelper db;

    public WebPedidoDAO(DBHelper db) {
        this.db = db;
    }

    public void inserirTBL_WEB_PEDIDO(WebPedido webPedido) {
        ContentValues content = new ContentValues();

        content.put("ID_EMPRESA", webPedido.getId_empresa());
        content.put("ID_CADASTRO", webPedido.getCadastro().getId_cadastro_servidor());
        content.put("ID_VENDEDOR", webPedido.getId_vendedor());
        content.put("ID_CONDICAO_PAGAMENTO", webPedido.getId_condicao_pagamento());
        content.put("ID_OPERACAO", webPedido.getId_operacao());
        content.put("ID_TABELA", webPedido.getId_tabela());
        content.put("NOME_EXTENSO", webPedido.getCadastro().getNome_cadastro());
        content.put("DATA_EMISSAO", db.pegaDataAtual());
        content.put("VALOR_PRODUTOS", webPedido.getValor_produtos());
        content.put("VALOR_DESCONTO", webPedido.getValor_desconto());
        content.put("VALOR_DESCONTO_ADD", webPedido.getValor_desconto_add());
        content.put("DESCONTO_PER", webPedido.getDesconto_per());
        content.put("DESCONTO_PER_ADD", webPedido.getDesconto_per_add());
        content.put("VALOR_TOTAL", webPedido.getValor_total());
        content.put("EXCLUIDO", webPedido.getExcluido());
        content.put("EXCLUIDO_USUARIO_ID", webPedido.getExcluido_usuario_id());
        content.put("EXCLUIDO_USUARIO_NOME", webPedido.getExcluido_usuario_nome());
        content.put("EXCLUIDO_USUARIO_DATA", webPedido.getExcluido_usuario_data());
        content.put("JUSTIFICATIVA_EXCLUSAO", webPedido.getJustificativa_exclusao());
        content.put("USUARIO_LANCAMENTO_ID", webPedido.getUsuario_lancamento_id());
        content.put("USUARIO_LANCAMENTO_NOME", webPedido.getUsuario_lancamento_nome());
        content.put("USUARIO_LANCAMENTO_DATA", webPedido.getUsuario_lancamento_data());
        content.put("OBSERVACOES", webPedido.getObservacoes());
        content.put("STATUS", webPedido.getStatus());
        content.put("ID_PEDIDO_VENDA", webPedido.getId_pedido_venda());
        content.put("ID_NOTA_FISCAL", webPedido.getId_nota_fiscal());
        content.put("ID_TABELA_PRECO_FAIXA", webPedido.getId_tabela_preco_faixa());
        content.put("PONTOS_TOTAL", webPedido.getPontos_total());
        content.put("PONTOS_COEFICIENTE", webPedido.getPontos_coeficiente());
        content.put("PONTOS_COR", webPedido.getPontos_cor());
        content.put("COMISSAO_PERCENTUAL", webPedido.getComissao_percentual());
        content.put("COMISSAO_VALOR", webPedido.getComissao_valor());
        content.put("ID_FAIXA_FINAL", webPedido.getId_faixa_final());
        content.put("VALOR_BONUS_CREDOR", webPedido.getValor_bonus_credor());
        content.put("PERC_BONUS_CREDOR", webPedido.getPerc_bonus_credor());
        content.put("DATA_PREV_ENTREGA", webPedido.getData_prev_entrega());
        content.put("ID_WEB_PEDIDO_SERVIDOR", webPedido.getId_web_pedido_servidor());
        content.put("PEDIDO_ENVIADO", webPedido.getPedido_enviado());

        db.salvarDados("TBL_WEB_PEDIDO", content);
        System.gc();
    }

    public void atualizarTBL_WEB_PEDIDO(WebPedido webPedido) {
        ContentValues content = new ContentValues();

        content.put("ID_WEB_PEDIDO", webPedido.getId_web_pedido());
        content.put("ID_EMPRESA", webPedido.getId_empresa());
        content.put("ID_CADASTRO", webPedido.getCadastro().getId_cadastro_servidor());
        content.put("ID_VENDEDOR", webPedido.getId_vendedor());
        content.put("ID_CONDICAO_PAGAMENTO", webPedido.getId_condicao_pagamento());
        content.put("ID_OPERACAO", webPedido.getId_operacao());
        content.put("ID_TABELA", webPedido.getId_tabela());
        content.put("NOME_EXTENSO", webPedido.getNome_extenso());
        content.put("DATA_EMISSAO", webPedido.getData_emissao());
        content.put("VALOR_PRODUTOS", webPedido.getValor_produtos());
        content.put("VALOR_DESCONTO", webPedido.getValor_desconto());
        content.put("VALOR_DESCONTO_ADD", webPedido.getValor_desconto_add());
        content.put("DESCONTO_PER", webPedido.getDesconto_per());
        content.put("DESCONTO_PER_ADD", webPedido.getDesconto_per_add());
        content.put("VALOR_TOTAL", webPedido.getValor_total());
        content.put("EXCLUIDO", webPedido.getExcluido());
        content.put("EXCLUIDO_USUARIO_ID", webPedido.getExcluido_usuario_id());
        content.put("EXCLUIDO_USUARIO_NOME", webPedido.getExcluido_usuario_nome());
        content.put("EXCLUIDO_USUARIO_DATA", webPedido.getExcluido_usuario_data());
        content.put("JUSTIFICATIVA_EXCLUSAO", webPedido.getJustificativa_exclusao());
        content.put("USUARIO_LANCAMENTO_ID", webPedido.getUsuario_lancamento_id());
        content.put("USUARIO_LANCAMENTO_NOME", webPedido.getUsuario_lancamento_nome());
        content.put("USUARIO_LANCAMENTO_DATA", webPedido.getUsuario_lancamento_data());
        content.put("OBSERVACOES", webPedido.getObservacoes());
        content.put("STATUS", webPedido.getStatus());
        content.put("ID_PEDIDO_VENDA", webPedido.getId_pedido_venda());
        content.put("ID_NOTA_FISCAL", webPedido.getId_nota_fiscal());
        content.put("ID_TABELA_PRECO_FAIXA", webPedido.getId_tabela_preco_faixa());
        content.put("PONTOS_TOTAL", webPedido.getPontos_total());
        content.put("PONTOS_COEFICIENTE", webPedido.getPontos_coeficiente());
        content.put("PONTOS_COR", webPedido.getPontos_cor());
        content.put("COMISSAO_PERCENTUAL", webPedido.getComissao_percentual());
        content.put("COMISSAO_VALOR", webPedido.getComissao_valor());
        content.put("ID_FAIXA_FINAL", webPedido.getId_faixa_final());
        content.put("VALOR_BONUS_CREDOR", webPedido.getValor_bonus_credor());
        content.put("PERC_BONUS_CREDOR", webPedido.getPerc_bonus_credor());
        content.put("DATA_PREV_ENTREGA", webPedido.getData_prev_entrega());
        content.put("PEDIDO_ENVIADO", webPedido.getPedido_enviado());
        content.put("ID_WEB_PEDIDO_SERVIDOR", webPedido.getId_web_pedido_servidor());

        db.atualizaDados("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = " + webPedido.getId_web_pedido());
        System.gc();
    }

    public List<WebPedido> listaWebPedido(String SQL) {
        List<WebPedido> lista = new ArrayList<>();
        Cursor cursor;

        cursor = db.listaDados(SQL);
        cursor.moveToFirst();
        do {
            WebPedido webPedido = new WebPedido();
            try {

                webPedido.setId_web_pedido(cursor.getString(cursor.getColumnIndex("ID_WEB_PEDIDO")));
                webPedido.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
                webPedido.setCadastro(db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE ID_CADASTRO_SERVIDOR = " + cursor.getString(cursor.getColumnIndex("ID_CADASTRO"))).get(0));
                webPedido.setId_vendedor(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
                webPedido.setId_condicao_pagamento(cursor.getString(cursor.getColumnIndex("ID_CONDICAO_PAGAMENTO")));
                webPedido.setId_operacao(cursor.getString(cursor.getColumnIndex("ID_OPERACAO")));
                webPedido.setId_tabela(cursor.getString(cursor.getColumnIndex("ID_TABELA")));
                webPedido.setNome_extenso(cursor.getString(cursor.getColumnIndex("NOME_EXTENSO")));
                webPedido.setData_emissao(cursor.getString(cursor.getColumnIndex("DATA_EMISSAO")));
                webPedido.setValor_produtos(cursor.getString(cursor.getColumnIndex("VALOR_PRODUTOS")));
                webPedido.setValor_desconto(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO")));
                webPedido.setValor_desconto_add(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_ADD")));
                webPedido.setDesconto_per(cursor.getString(cursor.getColumnIndex("DESCONTO_PER")));
                webPedido.setDesconto_per_add(cursor.getString(cursor.getColumnIndex("DESCONTO_PER_ADD")));
                webPedido.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                webPedido.setExcluido(cursor.getString(cursor.getColumnIndex("EXCLUIDO")));
                webPedido.setExcluido_usuario_id(cursor.getString(cursor.getColumnIndex("EXCLUIDO_USUARIO_ID")));
                webPedido.setExcluido_usuario_nome(cursor.getString(cursor.getColumnIndex("EXCLUIDO_USUARIO_NOME")));
                webPedido.setExcluido_usuario_data(cursor.getString(cursor.getColumnIndex("EXCLUIDO_USUARIO_DATA")));
                webPedido.setJustificativa_exclusao(cursor.getString(cursor.getColumnIndex("JUSTIFICATIVA_EXCLUSAO")));
                webPedido.setUsuario_lancamento_id(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_ID")));
                webPedido.setUsuario_lancamento_nome(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_NOME")));
                webPedido.setUsuario_lancamento_data(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_DATA")));
                webPedido.setObservacoes(cursor.getString(cursor.getColumnIndex("OBSERVACOES")));
                webPedido.setStatus(cursor.getString(cursor.getColumnIndex("STATUS")));
                webPedido.setId_pedido_venda(cursor.getString(cursor.getColumnIndex("ID_PEDIDO_VENDA")));
                webPedido.setId_nota_fiscal(cursor.getString(cursor.getColumnIndex("ID_NOTA_FISCAL")));
                webPedido.setId_tabela_preco_faixa(cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO_FAIXA")));
                webPedido.setPontos_total(cursor.getString(cursor.getColumnIndex("PONTOS_TOTAL")));
                webPedido.setPontos_coeficiente(cursor.getString(cursor.getColumnIndex("PONTOS_COEFICIENTE")));
                webPedido.setPontos_cor(cursor.getString(cursor.getColumnIndex("PONTOS_COR")));
                webPedido.setComissao_percentual(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL")));
                webPedido.setComissao_valor(cursor.getString(cursor.getColumnIndex("COMISSAO_VALOR")));
                webPedido.setId_faixa_final(cursor.getString(cursor.getColumnIndex("ID_FAIXA_FINAL")));
                webPedido.setValor_bonus_credor(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CREDOR")));
                webPedido.setPerc_bonus_credor(cursor.getString(cursor.getColumnIndex("PERC_BONUS_CREDOR")));
                webPedido.setId_web_pedido_servidor(cursor.getString(cursor.getColumnIndex("ID_WEB_PEDIDO_SERVIDOR")));
                webPedido.setData_prev_entrega(cursor.getString(cursor.getColumnIndex("DATA_PREV_ENTREGA")));
                webPedido.setPedido_enviado(cursor.getString(cursor.getColumnIndex("PEDIDO_ENVIADO")));

                lista.add(webPedido);
            } catch (CursorIndexOutOfBoundsException e) {
                System.out.println("Cliente não encontrado na base de dados!");
                e.printStackTrace();
            }

            System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        return lista;
    }
}
