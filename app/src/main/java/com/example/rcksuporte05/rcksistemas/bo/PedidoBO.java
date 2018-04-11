package com.example.rcksuporte05.rcksistemas.bo;

import android.content.Context;

import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;

import java.util.List;

/**
 * Created by RCK 03 on 07/12/2017.
 */

public class PedidoBO {
    DBHelper db;



    public void excluirPedido(Context context, List<WebPedido> webPedidos){
        db = new DBHelper(context);

        for(WebPedido pedido : webPedidos){
            db.alterar("DELETE FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + pedido.getId_web_pedido());
            db.alterar("DELETE FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " +pedido.getId_web_pedido());
        }

    }

    public void excluiItenPedido(Context context, List<WebPedidoItens> webPedidoItens) {
        db = new DBHelper(context);

        for (WebPedidoItens webPedidoIten : webPedidoItens) {
            db.alterar("DELETE FROM TBL_WEB_PEDIDO_ITENS WHERE ID_WEB_ITEM = " + webPedidoIten.getId_web_item());
        }
    }

}