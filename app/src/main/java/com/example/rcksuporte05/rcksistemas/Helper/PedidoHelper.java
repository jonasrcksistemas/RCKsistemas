package com.example.rcksuporte05.rcksistemas.Helper;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.PedidoBO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityPedidoMain;
import com.example.rcksuporte05.rcksistemas.activity.ProdutoPedidoActivity;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido1;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

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
    private static Pedido2 pedido2;
    private static Pedido1 pedido1;
    private static WebPedido webPedido;
    private static WebPedidoItens webPedidoItem;
    private static List<WebPedidoItens> listaWebPedidoItens;
    private static Produto produto;
    private static int idPedido;
    private Float valorProdutos = 0.0f;
    private Float descontoReal = 0.0f;
    private Float mediaDesconto = 0.0f;
    private EditText edtTotalVenda;
    private ViewPager mViewPager;
    private DBHelper db;
    private WebPedidoDAO webPedidoDAO;
    private WebPedidoItensDAO webPedidoItensDAO;

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

    public static Produto getProduto() {
        return produto;
    }

    public static void setProduto(Produto produto) {
        PedidoHelper.produto = produto;
    }

    public static void pintaTxtNomeCliente() {
        TextView txtNomeCliente = (TextView) activityPedidoMain.findViewById(R.id.txtNomeCliente);
        txtNomeCliente.setTextColor(Color.RED
        );
    }

    public static EditText editTextDataEntrega() {
        return (EditText) activityPedidoMain.findViewById(R.id.edtDataEntrega);
    }

    public static void calculaValorPedido(List<WebPedidoItens> produtoPedido, ActivityPedidoMain activityPedidoMain) {

        Float resultado = Float.valueOf("0");
        for (int i = 0; produtoPedido.size() > i; i++) {
            resultado += Float.valueOf(produtoPedido.get(i).getValor_total());
        }
        valorVenda = resultado;
        EditText edtTotalVenda = (EditText) activityPedidoMain.findViewById(R.id.edtTotalVenda);
        edtTotalVenda.setText(MascaraUtil.mascaraReal(resultado));
    }

    public void inserirProduto(WebPedidoItens webPedidoItem) {
        pedido1.inserirProduto(webPedidoItem);
    }

    public void alterarProduto(WebPedidoItens webPedidoIten, int position) {
        pedido1.alterarProduto(webPedidoIten, position);
    }

    public void moveTela(int position) {
        mViewPager = (ViewPager) activityPedidoMain.findViewById(R.id.vp_tabsPedido);
        mViewPager.setCurrentItem(position);
    }

    public boolean verificaCliente() {
        return activityPedidoMain.verificaCliente();
    }

    public boolean salvaPedido() {
        try {
            webPedidoDAO = new WebPedidoDAO(db);
            webPedidoItensDAO = new WebPedidoItensDAO(db);

            webPedido = activityPedidoMain.salvaPedido();
            webPedido.setId_condicao_pagamento(pedido2.salvaPedido().getId_condicao_pagamento());
            webPedido.setId_tabela(pedido2.salvaPedido().getId_tabela());
            webPedido.setCadastro(pedido2.salvaPedido().getCadastro());
            webPedido.setObservacoes(pedido2.salvaPedido().getObservacoes());
            webPedido.setData_prev_entrega(pedido2.salvaPedido().getData_prev_entrega());
            webPedido.setPedido_enviado(pedido2.salvaPedido().getPedido_enviado());
            webPedido.setId_operacao(pedido2.salvaPedido().getId_operacao());
            try {
                webPedido.setData_prev_entrega(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(pedido2.salvaPedido().getData_prev_entrega())));
            } catch (ParseException e) {
                webPedido.setData_prev_entrega(null);
                e.printStackTrace();
            }

            listaWebPedidoItens = pedido1.salvaPedidos();
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
                                Boolean descontoIndevido = false;
                                for (WebPedidoItens webPedidoItens : listaWebPedidoItens) {
                                    if (webPedidoItens.getDescontoIndevido()) {
                                        descontoIndevido = true;
                                        break;
                                    }
                                }
                                if (!descontoIndevido) {
                                    webPedido.setValor_total(valorVenda.toString());
                                    for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                        valorProdutos += Float.parseFloat(listaWebPedidoItens.get(i).getValor_bruto());
                                        descontoReal += Float.parseFloat(listaWebPedidoItens.get(i).getValor_desconto_real());
                                    }
                                    mediaDesconto = (mediaDesconto / listaWebPedidoItens.size());

                                    webPedido.setDesconto_per(String.valueOf(mediaDesconto));
                                    webPedido.setValor_produtos(String.valueOf(valorProdutos));
                                    webPedido.setValor_desconto(String.valueOf(descontoReal));
                                    //TODO Verificar rotina de exclusão dos produtos excluidos
                                    if (Pedido1.listaProdutoRemovido.size() > 0 && PedidoHelper.getIdPedido() > 0) {
                                        PedidoBO pedidoBO = new PedidoBO();
                                        pedidoBO.excluiItenPedido(PedidoHelper.getActivityPedidoMain(), Pedido1.listaProdutoRemovido);
                                    }

                                    if (PedidoHelper.getIdPedido() > 0) {
                                        webPedido.setId_web_pedido(String.valueOf(PedidoHelper.getIdPedido()));
                                        webPedidoDAO.atualizarTBL_WEB_PEDIDO(webPedido);
                                        for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                            if (listaWebPedidoItens.get(i).getId_web_item() == null) {
                                                listaWebPedidoItens.get(i).setId_pedido(webPedido.getId_web_pedido());
                                                webPedidoItensDAO.inserirTBL_WEB_PEDIDO_ITENS(listaWebPedidoItens.get(i));
                                            } else {
                                                webPedidoItensDAO.atualizarTBL_WEB_PEDIDO_ITENS(listaWebPedidoItens.get(i));
                                            }
                                        }
                                    } else {
                                        webPedido.setPedido_enviado("N");
                                        webPedido.setUsuario_lancamento_data(db.pegaDataHoraAtual());
                                        webPedido.setData_emissao(db.pegaDataAtual());
                                        webPedidoDAO.inserirTBL_WEB_PEDIDO(webPedido);
                                        int idPedido = db.contagem("SELECT MAX(ID_WEB_PEDIDO) FROM TBL_WEB_PEDIDO");
                                        for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                            listaWebPedidoItens.get(i).setId_pedido(String.valueOf(idPedido));
                                            listaWebPedidoItens.get(i).setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                                            webPedidoItensDAO.inserirTBL_WEB_PEDIDO_ITENS(listaWebPedidoItens.get(i));
                                        }
                                    }
                                    return true;
                                } else {
                                    Toast.makeText(activityPedidoMain, "Há produtos fora da faixa de desconto permitida", Toast.LENGTH_SHORT).show();
                                    moveTela(0);

                                    return false;
                                }

                            } else {
                                Toast.makeText(activityPedidoMain, "A data de entrega não pode ser menor que a data de emissão da venda", Toast.LENGTH_SHORT).show();
                                editTextDataEntrega().setBackgroundResource(R.drawable.borda_edittext_erro);

                                return false;
                            }
                        } else {
                            Toast.makeText(activityPedidoMain, "ATENÇÃO - Você precisa informar a data de entrega", Toast.LENGTH_SHORT).show();
                            editTextDataEntrega().setBackgroundResource(R.drawable.borda_edittext_erro);

                            return false;
                        }
                    } else {
                        Toast.makeText(activityPedidoMain, "O pedido não pode ser salvo sem produtos!", Toast.LENGTH_SHORT).show();
                        moveTela(0);
                        return false;
                    }
                } else {
                    Toast.makeText(activityPedidoMain, "O pedido não pode ser salvo sem selecionar o cliente!", Toast.LENGTH_SHORT).show();
                    pintaTxtNomeCliente();
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
        pedido1 = null;
        webPedido = null;
        idPedido = 0;
        System.gc();
    }
}
