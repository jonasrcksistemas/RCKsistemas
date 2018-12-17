package com.example.rcksuporte05.rcksistemas.BO;

import android.content.Context;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoDAO;
import com.example.rcksuporte05.rcksistemas.model.Promocao;
import com.example.rcksuporte05.rcksistemas.model.PromocaoCliente;
import com.example.rcksuporte05.rcksistemas.model.PromocaoProduto;
import com.example.rcksuporte05.rcksistemas.model.PromocaoRetorno;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;

import java.util.List;

/**
 * Created by RCK 03 on 07/12/2017.
 */

public class PedidoBO {
    DBHelper db;

    public void excluirPedido(Context context, List<WebPedido> webPedidos) {
        db = new DBHelper(context);

        for (WebPedido pedido : webPedidos) {
            db.alterar("DELETE FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + pedido.getId_web_pedido());
            db.alterar("DELETE FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido());
        }
    }

    public void excluirPedido(Context context, WebPedido pedido) {
        db = new DBHelper(context);

        db.alterar("DELETE FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + pedido.getId_web_pedido());
        db.alterar("DELETE FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido());

    }

    public void excluiItenPedido(Context context, List<WebPedidoItens> webPedidoItens) {
        db = new DBHelper(context);

        for (WebPedidoItens webPedidoIten : webPedidoItens) {
            db.alterar("DELETE FROM TBL_WEB_PEDIDO_ITENS WHERE ID_WEB_ITEM = " + webPedidoIten.getId_web_item());
        }
    }

    public PromocaoRetorno calculaDesconto(int idCliente, String idProduto, Context context) {
        db = new DBHelper(context);

        PromocaoDAO promocaoDAO = new PromocaoDAO(db);

        PromocaoRetorno promocaoRetorno = new PromocaoRetorno(0.f);

        List<Promocao> listaPromocao = promocaoDAO.listaPromocaoTodosClientes();
        if (listaPromocao.size() > 0) {
            for (Promocao promocao : listaPromocao) {
                if (promocao.getAplicacaoProduto() > 0) {
                    for (PromocaoProduto promocaoProduto : promocao.getListaPromoProduto()) {
                        if (promocaoProduto.getIdProduto().equals(idProduto)) {
                            promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                            promocaoRetorno.setValorDesconto(promocaoProduto.getDescontoPerc());
                            return promocaoRetorno;
                        }
                    }
                } else {
                    promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                    promocaoRetorno.setValorDesconto(promocao.getDescontoPerc());
                }
            }
        }
        List<Promocao> listaPromocaoCliente = promocaoDAO.listaPromocaoClienteEspecifico();
        if (listaPromocaoCliente.size() > 0) {
            for (Promocao promocao : listaPromocaoCliente) {
                for (PromocaoCliente promocaoCliente : promocao.getListaPromoCliente()) {
                    if (promocaoCliente.getIdCadastro() == idCliente) {
                        if (promocao.getAplicacaoProduto() > 0) {
                            for (PromocaoProduto promocaoProduto : promocao.getListaPromoProduto()) {
                                if (promocaoProduto.getIdProduto() == idProduto) {
                                    if (promocaoRetorno.getValorDesconto() > promocaoProduto.getDescontoPerc()) {
                                        return promocaoRetorno;
                                    } else {
                                        promocaoRetorno.setNomePromocao(promocao.getNomePromocao());
                                        promocaoRetorno.setValorDesconto(promocaoProduto.getDescontoPerc());
                                        return promocaoRetorno;
                                    }
                                }
                            }
                        } else {
                            if (promocaoRetorno.getValorDesconto() > promocao.getDescontoPerc()) {
                                return promocaoRetorno;
                            } else {
                                promocaoRetorno.setValorDesconto(promocao.getDescontoPerc());
                                return promocaoRetorno;
                            }
                        }
                    }
                }
            }
            return promocaoRetorno;
        }
        return promocaoRetorno;
    }
}