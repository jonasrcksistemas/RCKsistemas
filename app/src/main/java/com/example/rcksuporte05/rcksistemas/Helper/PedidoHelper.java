package com.example.rcksuporte05.rcksistemas.Helper;

import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.classes.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido1;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido3;
import com.example.rcksuporte05.rcksistemas.interfaces.ActivityPedidoMain;
import com.example.rcksuporte05.rcksistemas.interfaces.ProdutoPedidoActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PedidoHelper {

    private static Float valorVenda;
    private static ActivityPedidoMain activityPedidoMain;
    private static ProdutoPedidoActivity produtoPedidoActivity;
    private static Pedido3 pedido3;
    private static Pedido2 pedido2;
    private static Pedido1 pedido1;
    private static WebPedido webPedido;
    private static WebPedidoItens webPedidoItem;
    private static List<WebPedidoItens> listaWebPedidoItens;
    private static int idPedido;
    private Float valorProdutos = 0.0f;
    private EditText edtTotalVenda;
    private ViewPager mViewPager;
    private DBHelper db;

    public PedidoHelper() {
    }

    public PedidoHelper(ActivityPedidoMain activityPedidoMain) {
        this.activityPedidoMain = activityPedidoMain;
        edtTotalVenda = (EditText) activityPedidoMain.findViewById(R.id.edtTotalVenda);
        db = new DBHelper(activityPedidoMain);
        System.gc();
    }

    public PedidoHelper(ProdutoPedidoActivity produtoPedidoActivity) {
        this.produtoPedidoActivity = produtoPedidoActivity;

        System.gc();
    }

    public PedidoHelper(Pedido1 pedido1) {
        this.pedido1 = pedido1;
        System.gc();
    }

    public PedidoHelper(Pedido2 pedido2) {
        this.pedido2 = pedido2;
        System.gc();
    }

    public PedidoHelper(Pedido3 pedido3) {
        this.pedido3 = pedido3;
        db = new DBHelper(activityPedidoMain);
        System.gc();
    }

    public static ActivityPedidoMain getActivityPedidoMain() {
        System.gc();
        return activityPedidoMain;
    }

    public static ProdutoPedidoActivity getProdutoPedidoActivity() {
        System.gc();
        return produtoPedidoActivity;
    }

    public static int getIdPedido() {
        return idPedido;
    }

    public static void setIdPedido(int idPedido) {
        PedidoHelper.idPedido = idPedido;
    }

    public static WebPedidoItens getWebPedidoItem() {
        return webPedidoItem;
    }

    public static void setWebPedidoItem(WebPedidoItens webPedidoItem) {
        PedidoHelper.webPedidoItem = webPedidoItem;
        System.gc();
    }

    public void calculaValorPedido(List<WebPedidoItens> produtoPedido) {
        Float resultado = Float.valueOf("0");
        for (int i = 0; produtoPedido.size() > i; i++) {
            resultado += Float.valueOf(produtoPedido.get(i).getValor_total());
        }
        valorVenda = resultado;
        edtTotalVenda.setText(String.format("TOTAL    R$%.2f", valorVenda) + "    ");
    }

    public void inserirProduto(WebPedidoItens webPedidoItem) {
        pedido2.inserirProduto(webPedidoItem);
    }

    public void alterarProduto(WebPedidoItens webPedidoIten, int position) {
        pedido2.alterarProduto(webPedidoIten, position);
    }

    public void moveTela(int position) {
        mViewPager = (ViewPager) activityPedidoMain.findViewById(R.id.vp_tabsPedido);
        mViewPager.setCurrentItem(position);
        System.gc();
    }

    public boolean verificaCliente() {
        return pedido1.verificaCliente();
    }

    public boolean salvaPedido() {
        try {
            webPedido = pedido1.salvaPedido();
            webPedido.setId_condicao_pagamento(pedido3.salvaPedido().getId_condicao_pagamento());
            webPedido.setObservacoes(pedido3.salvaPedido().getObservacoes());
            webPedido.setCadastro(pedido3.salvaPedido().getCadastro());
            try {
                webPedido.setData_prev_entrega(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(pedido3.salvaPedido().getData_prev_entrega())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            listaWebPedidoItens = pedido2.salvaPedidos();
            try {
                if (webPedido.getCadastro() != null) {
                    if (listaWebPedidoItens.size() > 0) {
                        if (webPedido.getData_prev_entrega() != null) {

                            Calendar dataAtual = new GregorianCalendar();
                            Calendar dataPedido = new GregorianCalendar();
                            Date date = new Date();
                            dataAtual.setTime(date);
                            dataPedido.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_prev_entrega()));
                            String atual = new SimpleDateFormat("dd/MM/yyyy").format(dataAtual.getTime());
                            String pedido = new SimpleDateFormat("dd/MM/yyyy").format(dataPedido.getTime());
                            if (atual.equals(pedido) || !dataAtual.getTime().after(dataPedido.getTime())) {

                                webPedido.setValor_total(valorVenda.toString());
                                for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                    valorProdutos += Float.parseFloat(listaWebPedidoItens.get(i).getValor_bruto());
                                }

                                webPedido.setValor_produtos(String.valueOf(valorProdutos));

                                if (PedidoHelper.getIdPedido() > 0) {
                                    db.atualizarTBL_WEB_PEDIDO(webPedido);
                                    for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                        if (listaWebPedidoItens.get(i).getId_web_item() == null) {
                                            listaWebPedidoItens.get(i).setId_pedido(webPedido.getId_web_pedido());
                                            db.inserirTBL_WEB_PEDIDO_ITENS(listaWebPedidoItens.get(i));
                                        } else {
                                            db.atualizarTBL_WEB_PEDIDO_ITENS(listaWebPedidoItens.get(i));
                                        }
                                    }
                                } else {
                                    webPedido.setPedido_enviado("N");
                                    webPedido.setUsuario_lancamento_data(db.pegaDataHoraAtual());
                                    webPedido.setData_emissao(db.pegaDataAtual());
                                    db.inserirTBL_WEB_PEDIDO(webPedido);
                                    int idPedido = db.contagem("SELECT MAX(ID_WEB_PEDIDO) FROM TBL_WEB_PEDIDO");
                                    for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                        listaWebPedidoItens.get(i).setId_pedido(String.valueOf(idPedido));
                                        listaWebPedidoItens.get(i).setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                                        db.inserirTBL_WEB_PEDIDO_ITENS(listaWebPedidoItens.get(i));
                                    }
                                }
                                return true;
                            } else {
                                Toast.makeText(activityPedidoMain, "A data de entrega não pode ser menor que a data de emissão da venda", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        } else {
                            Toast.makeText(activityPedidoMain, "ATENÇÃO - Você precisa informar a data de entrega", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    } else {
                        Toast.makeText(activityPedidoMain, "O pedido não pode ser salvo sem produtos!", Toast.LENGTH_SHORT).show();
                        moveTela(1);
                        return false;
                    }
                } else {
                    Toast.makeText(activityPedidoMain, "O pedido não pode ser salvo sem selecionar o cliente!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void limparDados() {
        valorVenda = null;
        listaWebPedidoItens = null;
        webPedido = null;
        webPedidoItem = null;
        activityPedidoMain = null;
        activityPedidoMain = null;
        produtoPedidoActivity = null;
        pedido1 = null;
        pedido2 = null;
        webPedido = null;
        idPedido = 0;
        System.gc();
    }
}
