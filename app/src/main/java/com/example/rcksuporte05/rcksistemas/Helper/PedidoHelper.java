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
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
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

    private static CondicoesPagamento condicoesPagamento;
    private static Float valorVenda;
    private static ActivityPedidoMain activityPedidoMain;
    private static ProdutoPedidoActivity produtoPedidoActivity;
    private static Pedido2 pedido2;
    private static Pedido1 pedido1;
    private static WebPedido webPedido;
    private static WebPedidoItens webPedidoItem;
    private static List<WebPedidoItens> listaWebPedidoItens;
    private static List<Produto> listaProdutos;
    private static Produto produto;
    private static int idPedido;
    private static String buscaProduto;
    private Float valorProdutos = 0.0f;
    private Float descontoReal = 0.0f;
    private Float mediaDesconto = 0.0f;
    private TextView edtTotalVenda;
    private TextView edtTotalDescontos;
    private TextView edtTotalProdutos;
    private ViewPager mViewPager;
    private DBHelper db;
    private WebPedidoDAO webPedidoDAO;
    private WebPedidoItensDAO webPedidoItensDAO;

    public PedidoHelper() {
    }

    public PedidoHelper(ActivityPedidoMain activityPedidoMain) {
        this.activityPedidoMain = activityPedidoMain;
        edtTotalVenda = activityPedidoMain.findViewById(R.id.edtTotalVenda);
        edtTotalDescontos = activityPedidoMain.findViewById(R.id.edtTotalDescontos);
        edtTotalProdutos = activityPedidoMain.findViewById(R.id.edtTotalProdutos);
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

    public static List<WebPedidoItens> getListaWebPedidoItens() {
        return listaWebPedidoItens;
    }

    public static void setListaWebPedidoItens(List<WebPedidoItens> listaWebPedidoItens) {
        PedidoHelper.listaWebPedidoItens = listaWebPedidoItens;
    }

    public static List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public static void setListaProdutos(List<Produto> listaProdutos) {
        PedidoHelper.listaProdutos = listaProdutos;
    }

    public static CondicoesPagamento getCondicoesPagamento() {
        return condicoesPagamento;
    }

    public static void setCondicoesPagamento(CondicoesPagamento condicoesPagamento) {
        PedidoHelper.condicoesPagamento = condicoesPagamento;
    }

    public static WebPedido getWebPedido() {
        return webPedido;
    }

    public static void setWebPedido(WebPedido webPedido) {
        PedidoHelper.webPedido = webPedido;
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

    public static String getBuscaProduto() {
        return buscaProduto;
    }

    public static void setBuscaProduto(String buscaProduto) {
        PedidoHelper.buscaProduto = buscaProduto;
    }

    public static void pintaTxtNomeCliente() {
        TextView txtNomeCliente = (TextView) activityPedidoMain.findViewById(R.id.txtNomeCliente);
        txtNomeCliente.setTextColor(Color.RED);
    }

    public static EditText editTextDataEntrega() {
        return activityPedidoMain.findViewById(R.id.edtDataEntrega);
    }

    public static void calculaValorPedido(List<WebPedidoItens> produtoPedido, ActivityPedidoMain activityPedidoMain) {

        Float valorVenda = 0.f;
        Float valorDesconto = 0.f;
        Float valorProduto = 0.f;
        for (int i = 0; produtoPedido.size() > i; i++) {
            try {
                valorVenda += Float.valueOf(produtoPedido.get(i).getValor_total());
                valorDesconto += Float.valueOf(produtoPedido.get(i).getValor_desconto_real());
                valorProduto += Float.valueOf(produtoPedido.get(i).getVenda_preco()) * Float.valueOf(produtoPedido.get(i).getQuantidade());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setValorVenda(valorVenda);
        listaWebPedidoItens = produtoPedido;
        TextView edtTotalVenda = activityPedidoMain.findViewById(R.id.edtTotalVenda);
        TextView edtTotalProdutos = activityPedidoMain.findViewById(R.id.edtTotalProdutos);
        TextView edtTotalDescontos = activityPedidoMain.findViewById(R.id.edtTotalDescontos);
        edtTotalVenda.setText(MascaraUtil.mascaraVirgula(valorVenda));
        edtTotalProdutos.setText(MascaraUtil.mascaraVirgula(valorProduto));
        edtTotalDescontos.setText(MascaraUtil.mascaraVirgula(valorDesconto));
    }

    public static Float getValorVenda() {
        return valorVenda;
    }

    public static void setValorVenda(Float valorVenda) {
        PedidoHelper.valorVenda = valorVenda;
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

    public boolean validaCredito() {
        CadastroFinanceiroResumo cadastroFinanceiroResumo = HistoricoFinanceiroHelper.getCadastroFinanceiroResumo();

        Float limiteUltilizado = db.soma("SELECT SUM(VALOR_TOTAL) FROM TBL_WEB_PEDIDO " +
                "WHERE PEDIDO_ENVIADO = 'N' AND ID_CONDICAO_PAGAMENTO <> 1 " +
                "AND ID_WEB_PEDIDO <> " + PedidoHelper.getIdPedido() + " " +
                "AND ID_CADASTRO = " + ClienteHelper.getCliente().getId_cadastro_servidor() + ";") + cadastroFinanceiroResumo.getLimiteUtilizado();

        Float saldoRestante = cadastroFinanceiroResumo.getLimiteCredito() - limiteUltilizado - getValorVenda() - cadastroFinanceiroResumo.getFinanceiroVencido();

        if (saldoRestante < 0 || cadastroFinanceiroResumo.getFinanceiroVencido() > 0) {
            Toast.makeText(activityPedidoMain, "Esse pedido não passou na análise de crédito!", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public void salvaPedidoParcial() {
        try {
            webPedidoDAO = new WebPedidoDAO(db);
            webPedidoItensDAO = new WebPedidoItensDAO(db);

            webPedido = activityPedidoMain.salvaPedido();
            webPedido.setId_condicao_pagamento(pedido2.salvaPedido().getId_condicao_pagamento());
            webPedido.setId_tabela(pedido2.salvaPedido().getId_tabela());
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

            if (PedidoHelper.getIdPedido() > 0) {
                if (webPedido.getFinalizado().equals("N")) {
                    webPedido.setId_web_pedido(String.valueOf(PedidoHelper.getIdPedido()));
                    webPedidoDAO.atualizarTBL_WEB_PEDIDO(webPedido);
                    for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                        if (listaWebPedidoItens.get(i).getId_web_item() == null) {
                            if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND ID_PRODUTO = " + listaWebPedidoItens.get(i).getId_produto() + ";") <= 0) {
                                listaWebPedidoItens.get(i).setId_pedido(webPedido.getId_web_pedido());
                                webPedidoItensDAO.inserirTBL_WEB_PEDIDO_ITENS(listaWebPedidoItens.get(i));
                            }
                        } else {
                            webPedidoItensDAO.atualizarTBL_WEB_PEDIDO_ITENS(listaWebPedidoItens.get(i));
                        }
                    }
                }
            } else {
                webPedido.setPedido_enviado("N");
                webPedido.setFinalizado("N");
                webPedido.setUsuario_lancamento_data(db.pegaDataHoraAtual());
                webPedido.setData_emissao(db.pegaDataAtual());
                webPedidoDAO.inserirTBL_WEB_PEDIDO(webPedido);
                int idPedido = db.contagem("SELECT MAX(ID_WEB_PEDIDO) FROM TBL_WEB_PEDIDO");
                PedidoHelper.setIdPedido(idPedido);
                getActivityPedidoMain().toolbar.setSubtitle("Pedido: " + idPedido);
                for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                    if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = " + listaWebPedidoItens.get(i).getId_produto() + ";") <= 0) {
                        listaWebPedidoItens.get(i).setId_pedido(String.valueOf(idPedido));
                        listaWebPedidoItens.get(i).setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                        webPedidoItensDAO.inserirTBL_WEB_PEDIDO_ITENS(listaWebPedidoItens.get(i));
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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
                            if (webPedido.getId_condicao_pagamento() != null && !webPedido.getId_condicao_pagamento().equals("0")) {
                                if (atual.equals(pedido) || !dataAtual.getTime().after(dataPedido.getTime())) {
                                    Boolean descontoIndevido = false;
                                    Boolean campanhaIndevida = false;
                                    for (WebPedidoItens webPedidoItens : listaWebPedidoItens) {
                                        if (webPedidoItens.getDescontoIndevido()) {
                                            descontoIndevido = true;
                                            break;
                                        }

                                        if (webPedidoItens.getCampanhaIndevida()) {
                                            campanhaIndevida = true;
                                            break;
                                        }
                                    }
                                    if (!descontoIndevido) {
                                        if (!campanhaIndevida) {
                                            webPedido.setValor_total(valorVenda.toString());
                                            for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                                valorProdutos += Float.parseFloat(listaWebPedidoItens.get(i).getValor_bruto());
                                                descontoReal += Float.parseFloat(listaWebPedidoItens.get(i).getValor_desconto_real());
                                            }
                                            mediaDesconto = (mediaDesconto / listaWebPedidoItens.size());

                                            webPedido.setDesconto_per(String.valueOf(mediaDesconto));
                                            webPedido.setValor_produtos(String.valueOf(valorProdutos));
                                            webPedido.setValor_desconto(String.valueOf(descontoReal));
                                            if (Pedido1.listaProdutoRemovido.size() > 0 && PedidoHelper.getIdPedido() > 0) {
                                                PedidoBO pedidoBO = new PedidoBO();
                                                pedidoBO.excluiItenPedido(PedidoHelper.getActivityPedidoMain(), Pedido1.listaProdutoRemovido);
                                            }
                                            calculaValorPedido(listaWebPedidoItens, activityPedidoMain);
                                            webPedido.setValor_total(String.valueOf(getValorVenda()));

                                            if (PedidoHelper.getIdPedido() > 0) {
                                                webPedido.setId_web_pedido(String.valueOf(PedidoHelper.getIdPedido()));
                                                webPedido.setFinalizado("S");
                                                webPedidoDAO.atualizarTBL_WEB_PEDIDO(webPedido);
                                                for (int i = 0; listaWebPedidoItens.size() > i; i++) {
                                                    if (listaWebPedidoItens.get(i).getId_web_item() == null) {
                                                        if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + PedidoHelper.getIdPedido() + " AND ID_PRODUTO = " + listaWebPedidoItens.get(i).getId_produto() + ";") <= 0) {
                                                            listaWebPedidoItens.get(i).setId_pedido(webPedido.getId_web_pedido());
                                                            webPedidoItensDAO.inserirTBL_WEB_PEDIDO_ITENS(listaWebPedidoItens.get(i));
                                                        }
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
                                                    if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + idPedido + " AND ID_PRODUTO = " + listaWebPedidoItens.get(i).getId_produto() + ";") <= 0) {
                                                        listaWebPedidoItens.get(i).setId_pedido(String.valueOf(idPedido));
                                                        listaWebPedidoItens.get(i).setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                                                        webPedidoItensDAO.inserirTBL_WEB_PEDIDO_ITENS(listaWebPedidoItens.get(i));
                                                    }
                                                }
                                            }
                                            return true;
                                        } else {
                                            Toast.makeText(activityPedidoMain, "Há produtos que não atendem a regra de campanha", Toast.LENGTH_SHORT).show();
                                            moveTela(0);
                                            return false;
                                        }
                                    } else {
                                        Toast.makeText(activityPedidoMain, "Há produtos fora da faixa de desconto permitida", Toast.LENGTH_SHORT).show();
                                        moveTela(0);
                                        return false;
                                    }
                                } else {
                                    Toast.makeText(activityPedidoMain, "A data de entrega não pode ser menor que a data de emissão da venda", Toast.LENGTH_SHORT).show();
                                    editTextDataEntrega().setBackgroundResource(R.drawable.borda_edittext_erro);
                                    moveTela(1);
                                    return false;
                                }
                            } else {
                                Toast.makeText(activityPedidoMain, "Você precisa selecionar uma condição de pagamento", Toast.LENGTH_SHORT).show();
                                moveTela(1);
                                return false;
                            }
                        } else {
                            Toast.makeText(activityPedidoMain, "ATENÇÃO - Você precisa informar a data de entrega", Toast.LENGTH_SHORT).show();
                            editTextDataEntrega().setBackgroundResource(R.drawable.borda_edittext_erro);
                            moveTela(1);
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
                    moveTela(1);
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
        buscaProduto = null;
        listaProdutos = null;
        condicoesPagamento = null;
        System.gc();
    }
}
