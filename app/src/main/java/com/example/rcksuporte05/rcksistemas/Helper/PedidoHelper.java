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
    private Float descontoReal = 0.0f;
    private Float mediaDesconto = 0.0f;
    private Float totalPontos = 0.0f;
    private EditText edtTotalVenda;
    private EditText edtNomeProduto;
    private EditText edtTabelaPreco;
    private EditText edtQuantidade;
    private EditText edtValorProdutos;
    private EditText edtDesconto;
    private EditText edtDescontoReais;
    private EditText edtPrecoPago;
    private EditText edtTotal;
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

        edtNomeProduto = (EditText) produtoPedidoActivity.findViewById(R.id.edtNomeProduto);
        edtTabelaPreco = (EditText) produtoPedidoActivity.findViewById(R.id.edtTabelaPreco);
        edtValorProdutos = (EditText) produtoPedidoActivity.findViewById(R.id.edtValorProdutos);
        edtQuantidade = (EditText) produtoPedidoActivity.findViewById(R.id.edtQuantidade);
        edtDesconto = (EditText) produtoPedidoActivity.findViewById(R.id.edtDesconto);
        edtDescontoReais = (EditText) produtoPedidoActivity.findViewById(R.id.edtDescontoReais);
        edtPrecoPago = (EditText) produtoPedidoActivity.findViewById(R.id.edtPrecoPago);
        edtTotal = (EditText) produtoPedidoActivity.findViewById(R.id.edtTotal);

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
        edtTotalVenda.setText(String.format("TOTAL    R$%.2f", valorVenda) + "     ");
    }

    public void inserirProduto(WebPedidoItens webPedidoItem) {
        pedido2.inserirProduto(webPedidoItem);
    }

    public void alterarProduto(WebPedidoItens webPedidoIten, int position) {
        pedido2.alterarProduto(webPedidoIten, position);
    }

    public void moveTela(int position) {
        mViewPager = (ViewPager) activityPedidoMain.findViewById(R.id.vp_tabsHistoricoFinanceiro);
        mViewPager.setCurrentItem(position);
        System.gc();
    }

    public boolean verificaCliente() {
        return pedido1.verificaCliente();
    }

    public boolean salvaPedido() {
        webPedido = pedido1.salvaPedido();
        webPedido.setId_condicao_pagamento(pedido3.salvaPedido().getId_condicao_pagamento());
        webPedido.setObservacoes(pedido3.salvaPedido().getObservacoes());
        webPedido.setCadastro(pedido3.salvaPedido().getCadastro());
        try {
            webPedido.setData_prev_entrega(new SimpleDateFormat("MM-dd-yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(pedido3.salvaPedido().getData_prev_entrega())));
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
                        dataPedido.setTime(new SimpleDateFormat("MM-dd-yyyy").parse(webPedido.getData_prev_entrega()));
                        String atual = new SimpleDateFormat("dd/MM/yyyy").format(dataAtual.getTime());
                        String pedido = new SimpleDateFormat("dd/MM/yyyy").format(dataPedido.getTime());
                        if (atual.equals(pedido) || !dataAtual.getTime().after(dataPedido.getTime())) {

                            webPedido.setValor_total(valorVenda.toString());
                            for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                valorProdutos += Float.parseFloat(listaWebPedidoItens.get(i).getValor_bruto());
                                descontoReal += Float.parseFloat(listaWebPedidoItens.get(i).getValor_desconto_real());
                                mediaDesconto += Float.parseFloat(listaWebPedidoItens.get(i).getTabela_preco_faixa().getPerc_desc_inicial());
                                totalPontos += Float.parseFloat(listaWebPedidoItens.get(i).getPontos_total());
                            }
                            mediaDesconto = (mediaDesconto / listaWebPedidoItens.size());
                            webPedido.setPontos_total(totalPontos.toString());
                            if (totalPontos > 0) {
                                webPedido.setPontos_coeficiente(String.valueOf(Float.parseFloat(webPedido.getValor_total()) / totalPontos));
                            } else {
                                webPedido.setPontos_coeficiente("0");
                            }
                            webPedido.setDesconto_per(String.valueOf(mediaDesconto));
                            webPedido.setValor_produtos(String.valueOf(valorProdutos));
                            webPedido.setValor_desconto(String.valueOf(descontoReal));
                            webPedido.setPontos_cor(db.consulta("SELECT I.COR_WEB, I.ID_ITEM FROM TBL_TABELA_PRECO_ITENS I JOIN TBL_TABELA_PRECO_CAB T ON T.ID_TABELA=I.ID_TABELA WHERE PERC_DESC_INICIAL<= " + mediaDesconto + " AND PERC_DESC_FINAL>= " + mediaDesconto + ";", "COR_WEB"));
                            webPedido.setId_tabela_preco_faixa(db.consulta("SELECT I.COR_WEB, I.ID_ITEM FROM TBL_TABELA_PRECO_ITENS I JOIN TBL_TABELA_PRECO_CAB T ON T.ID_TABELA=I.ID_TABELA WHERE PERC_DESC_INICIAL<= " + mediaDesconto + " AND PERC_DESC_FINAL>= " + mediaDesconto + ";", "ID_ITEM"));
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
                                db.inserirTBL_WEB_PEDIDO(webPedido);
                                int idPedido = db.contagem("SELECT MAX(ID_WEB_PEDIDO) FROM TBL_WEB_PEDIDO");
                                for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                    listaWebPedidoItens.get(i).setId_pedido(String.valueOf(idPedido));
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
