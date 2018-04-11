package com.example.rcksuporte05.rcksistemas.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityProduto;
import com.example.rcksuporte05.rcksistemas.activity.ProdutoPedidoActivity;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterProdutoPedido;
import com.example.rcksuporte05.rcksistemas.bo.PedidoBO;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class Pedido1 extends Fragment implements ListaAdapterProdutoPedido.ProdutoPedidoAdapterListener {

    public static List<WebPedidoItens> listaProdutoRemovido = new ArrayList<>();
    private static ListaAdapterProdutoPedido listaAdapterProdutoPedido;
    private static List<WebPedidoItens> listaProdutoPedido = new ArrayList<>();
    private static RecyclerView lstProdutoPedido;
    private DBHelper db = new DBHelper(PedidoHelper.getActivityPedidoMain());
    private WebPedidoItensDAO webPedidoItensDAO;
    private TabelaPrecoItem tabelaPrecoItem;
    private Button btnInserirProduto;
    private Bundle bundle;

    public static ListaAdapterProdutoPedido getListaAdapterProdutoPedido() {
        return listaAdapterProdutoPedido;
    }

    public static void setListaAdapterProdutoPedido(ListaAdapterProdutoPedido listaAdapterProdutoPedido) {
        Pedido1.listaAdapterProdutoPedido = listaAdapterProdutoPedido;
    }

    public static void removeProdutos(final List<WebPedidoItens> webPedidoItens, Context context) {
        for (WebPedidoItens webPedidoIten : webPedidoItens) {
            listaProdutoPedido.remove(webPedidoIten);
            if (PedidoHelper.getIdPedido() > 0) {
                listaProdutoRemovido = webPedidoItens;
            }
        }
        listaAdapterProdutoPedido.getList(listaProdutoPedido);
        lstProdutoPedido.setAdapter(listaAdapterProdutoPedido);
        listaAdapterProdutoPedido.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pedido1, container, false);

        webPedidoItensDAO = new WebPedidoItensDAO(db);

        final PedidoHelper pedidoHelper = new PedidoHelper(this);
        bundle = getArguments();
        lstProdutoPedido = (RecyclerView) view.findViewById(R.id.lstProdutosPedido);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        lstProdutoPedido.setLayoutManager(layoutManager);
        lstProdutoPedido.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        btnInserirProduto = (Button) view.findViewById(R.id.btnInserirProduto);
        btnInserirProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pedidoHelper.verificaCliente()) {
                    Intent intent = new Intent(getContext(), ActivityProduto.class);
                    intent.putExtra("acao", 1);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Selecione um cliente antes de prosseguir!", Toast.LENGTH_SHORT).show();
                    PedidoHelper.pintaTxtNomeCliente();
                    pedidoHelper.moveTela(0);
                }
            }
        });

        if (PedidoHelper.getIdPedido() > 0) {
            final int pedido = PedidoHelper.getIdPedido();
            try {
                listaProdutoPedido = webPedidoItensDAO.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido);
                preencheLista(listaProdutoPedido);
            } catch (CursorIndexOutOfBoundsException e) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Atenção!");
                alert.setMessage("O pedido encontra-se sem produtos, deseja exclui-lo?");
                alert.setNegativeButton("Não", null);
                alert.setCancelable(false);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.alterar("DELETE FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + pedido);
                        db.alterar("DELETE FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido);
                        PedidoHelper.getActivityPedidoMain().finish();
                    }
                });
                alert.show();
                e.printStackTrace();
            }
        }

        if (bundle.getInt("vizualizacao") == 1) {
            btnInserirProduto.setVisibility(View.INVISIBLE);
        }

        System.gc();
        return (view);
    }

    public void inserirProduto(WebPedidoItens webPedidoItem) {
        webPedidoItem.setId_pedido(String.valueOf(db.contagem("SELECT MAX(ID_WEB_PEDIDO) FROM TBL_WEB_PEDIDO") + 1));
        webPedidoItem.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
        webPedidoItem.setUsuario_lancamento_id(String.valueOf(bundle.getInt("idUsuario")));
        listaProdutoPedido.add(webPedidoItem);
        preencheLista(listaProdutoPedido);
    }

    public void alterarProduto(WebPedidoItens webPedidoItem, int position) {
        webPedidoItem.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
        webPedidoItem.setUsuario_lancamento_id(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
        listaProdutoPedido.set(position, webPedidoItem);
        preencheLista(listaProdutoPedido);
    }

    public List<WebPedidoItens> salvaPedidos() {
        return listaProdutoPedido;
    }

    public void preencheLista(List<WebPedidoItens> webPedidoItens) {
        listaAdapterProdutoPedido = new ListaAdapterProdutoPedido(webPedidoItens, this);
        lstProdutoPedido.setAdapter(listaAdapterProdutoPedido);
        listaAdapterProdutoPedido.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        PedidoHelper pedidoHelper = new PedidoHelper(PedidoHelper.getActivityPedidoMain());
        pedidoHelper.calculaValorPedido(listaProdutoPedido);
        if (listaProdutoPedido.size() > 0) {
            tabelaPrecoItem = db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_CATEGORIA = " + ClienteHelper.getCliente().getIdCategoria()).get(0);
            PedidoBO pedidoBO = new PedidoBO();
            for (WebPedidoItens webPedidoItens : listaProdutoPedido) {
                Float descontoPedido = pedidoBO.calculaDesconto(ClienteHelper.getCliente().getId_cadastro(), webPedidoItens.getId_produto(), getActivity()).getValorDesconto();
                if (descontoPedido <= 0)
                    descontoPedido = Float.parseFloat(tabelaPrecoItem.getPerc_desc_final());
                if (Float.parseFloat(webPedidoItens.getValor_desconto_per()) > descontoPedido)
                    webPedidoItens.setDescontoIndevido(true);
                else
                    webPedidoItens.setDescontoIndevido(false);
            }
            listaAdapterProdutoPedido.notifyDataSetChanged();
        }
        System.gc();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        try {
            listaProdutoRemovido.clear();
            listaAdapterProdutoPedido = null;
            listaProdutoPedido.clear();
            lstProdutoPedido = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onRowClicked(int position) {
        if (listaAdapterProdutoPedido.getSelectedItemCount() > 0) {
            toggleSelection(position);
        } else {
            if (bundle.getInt("vizualizacao") != 1) {
                Intent intent = new Intent(getContext(), ProdutoPedidoActivity.class);
                intent.putExtra("pedido", 1);
                intent.putExtra("position", position);
                PedidoHelper.setWebPedidoItem(listaProdutoPedido.get(position));
                startActivity(intent);
            } else {
                Intent intent = new Intent(getContext(), ProdutoPedidoActivity.class);
                intent.putExtra("pedido", 1);
                intent.putExtra("vizualizacao", 1);
                intent.putExtra("position", position);
                PedidoHelper.setWebPedidoItem(listaProdutoPedido.get(position));
                startActivity(intent);
            }
        }

    }

    @Override
    public void onLongRowClicked(int position) {
        if (bundle.getInt("vizualizacao") != 1) {
            PedidoHelper.getActivityPedidoMain().enableActionMode(position);
        }
    }

    public void toggleSelection(int position) {
        listaAdapterProdutoPedido.toggleSelection(position);
        int count = listaAdapterProdutoPedido.getSelectedItemCount();

        if (count == 0) {
            PedidoHelper.getActivityPedidoMain().actionMode.finish();
        } else {
            PedidoHelper.getActivityPedidoMain().actionMode.setTitle(String.valueOf(count));
            PedidoHelper.getActivityPedidoMain().actionMode.invalidate();
        }
    }
}
