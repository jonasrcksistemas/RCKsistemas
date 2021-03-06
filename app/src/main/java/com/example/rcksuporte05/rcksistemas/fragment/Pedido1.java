package com.example.rcksuporte05.rcksistemas.fragment;

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

import com.example.rcksuporte05.rcksistemas.BO.PedidoBO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroFinanceiroResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityProduto;
import com.example.rcksuporte05.rcksistemas.activity.ProdutoPedidoActivity;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterProdutoPedido;
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
    private int idCliente;
    private CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO;

    public static ListaAdapterProdutoPedido getListaAdapterProdutoPedido() {
        return listaAdapterProdutoPedido;
    }

    public static void removeProdutos(final List<WebPedidoItens> webPedidoItens) {
        for (WebPedidoItens webPedidoIten : webPedidoItens) {
            listaProdutoPedido.remove(webPedidoIten);
            if (PedidoHelper.getIdPedido() > 0) {
                listaProdutoRemovido.add(webPedidoIten);
            }
        }
        listaAdapterProdutoPedido.getList(listaProdutoPedido);
        lstProdutoPedido.setAdapter(listaAdapterProdutoPedido);
        PedidoHelper.calculaValorPedido(listaProdutoPedido, PedidoHelper.getActivityPedidoMain());
        listaAdapterProdutoPedido.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pedido1, container, false);

        webPedidoItensDAO = new WebPedidoItensDAO(db);
        listaProdutoPedido = new ArrayList<>();

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
                    PedidoHelper.setListaWebPedidoItens(listaProdutoPedido);
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
        } else if (PedidoHelper.getWebPedido() != null) {
            listaProdutoPedido = PedidoHelper.getListaWebPedidoItens();
            preencheLista(listaProdutoPedido);
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
        if (listaProdutoPedido != null) {
            listaProdutoPedido.add(webPedidoItem);
        } else {
            listaProdutoPedido = new ArrayList<>();
            listaProdutoPedido.add(webPedidoItem);
        }
        preencheLista(listaProdutoPedido);
    }

    public void alterarProduto(WebPedidoItens webPedidoItem, int position) {
        webPedidoItem.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
        webPedidoItem.setUsuario_lancamento_id(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
        listaProdutoPedido.set(position, webPedidoItem);
        preencheLista(listaProdutoPedido);
    }

    public List<WebPedidoItens> salvaPedidos() {
//        listaAdapterProdutoPedido.notifyDataSetChanged();
        PedidoHelper.calculaValorPedido(listaProdutoPedido, PedidoHelper.getActivityPedidoMain());
        PedidoHelper.setListaWebPedidoItens(listaProdutoPedido);
        return listaProdutoPedido;
    }

    public void preencheLista(List<WebPedidoItens> webPedidoItens) {
        listaAdapterProdutoPedido = new ListaAdapterProdutoPedido(webPedidoItens, this);
        lstProdutoPedido.setAdapter(listaAdapterProdutoPedido);
        listaAdapterProdutoPedido.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        if (listaProdutoPedido != null)
            PedidoHelper.calculaValorPedido(listaProdutoPedido, PedidoHelper.getActivityPedidoMain());
        if (ClienteHelper.getCliente() != null) {
            try {
                cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
                HistoricoFinanceiroHelper.setCadastroFinanceiroResumo(cadastroFinanceiroResumoDAO.listaCadastroFinanceiroResumo(ClienteHelper.getCliente().getId_cadastro_servidor()));
                if (HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getFinanceiroVencido() > 0) {
                    PedidoHelper.pintaTxtNomeCliente();
                    Toast.makeText(getActivity(), "Este cliente possui pendências finaneiras!", Toast.LENGTH_LONG).show();
                }
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        if (listaProdutoPedido != null && listaProdutoPedido.size() > 0) {
            if (ClienteHelper.getCliente().getId_cadastro() != idCliente && idCliente > 0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Atenção");
                alert.setMessage("Você alterou o cliente. Deseja alterar o desconto de todos os produtos para a categoria deste cliente?");
                alert.setNegativeButton("NÃO", null);
                alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String descontoCategoria = db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_CATEGORIA = " + ClienteHelper.getCliente().getIdCategoria()).get(0).getPerc_desc_final();
                        for (WebPedidoItens webPedidoItem : listaProdutoPedido) {
                            if (!webPedidoItem.getProduto_tercerizacao().equals("S") && !webPedidoItem.getProduto_materia_prima().equals("S")) {
                                webPedidoItem.setValor_desconto_per(descontoCategoria);
                                webPedidoItem.setDescontoIndevido(false);
                                Float descontoReais = (Float.parseFloat(descontoCategoria) * Float.parseFloat(webPedidoItem.getValor_bruto())) / 100;
                                webPedidoItem.setValor_desconto_real(String.valueOf(descontoReais));
                                webPedidoItem.setValor_total(String.valueOf(Float.parseFloat(webPedidoItem.getValor_bruto()) - descontoReais));
                            }
                        }
                        listaAdapterProdutoPedido.notifyDataSetChanged();
                        PedidoHelper.calculaValorPedido(listaProdutoPedido, PedidoHelper.getActivityPedidoMain());
                    }
                });
                alert.show();
            }
            idCliente = ClienteHelper.getCliente().getId_cadastro();
            tabelaPrecoItem = db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_CATEGORIA = " + ClienteHelper.getCliente().getIdCategoria()).get(0);
            PedidoBO pedidoBO = new PedidoBO();
            CampanhaComercialCabDAO campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);
            if (bundle.getInt("vizualizacao") != 1) {
                for (WebPedidoItens webPedidoItens : listaProdutoPedido) {
                    Float descontoPedido = pedidoBO.calculaDesconto(ClienteHelper.getCliente().getId_cadastro_servidor(), webPedidoItens.getId_produto(), getActivity()).getValorDesconto();
                    if (descontoPedido <= 0 || Float.parseFloat(tabelaPrecoItem.getPerc_desc_final()) > descontoPedido)
                        descontoPedido = Float.parseFloat(tabelaPrecoItem.getPerc_desc_final());
                    if (Float.parseFloat(webPedidoItens.getValor_desconto_per()) > descontoPedido)
                        webPedidoItens.setDescontoIndevido(true);
                    else
                        webPedidoItens.setDescontoIndevido(false);

                    webPedidoItens.setCampanhaIndevida(!campanhaComercialCabDAO.verificaCampanha(ClienteHelper.getCliente(), webPedidoItens));

                }
            }
            listaAdapterProdutoPedido.notifyDataSetChanged();
        }
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
            if (listaProdutoPedido.get(position).getProdutoBase()) {
                if (bundle.getInt("vizualizacao") != 1) {
                    Intent intent = new Intent(getContext(), ProdutoPedidoActivity.class);
                    intent.putExtra("pedido", 1);
                    intent.putExtra("position", position);
                    PedidoHelper.setWebPedidoItem(listaProdutoPedido.get(position));
                    PedidoHelper.setListaWebPedidoItens(listaProdutoPedido);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), ProdutoPedidoActivity.class);
                    intent.putExtra("pedido", 1);
                    intent.putExtra("vizualizacao", 1);
                    intent.putExtra("position", position);
                    PedidoHelper.setWebPedidoItem(listaProdutoPedido.get(position));
                    startActivity(intent);
                }
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Atenção!");
                alert.setMessage("O produto " + listaProdutoPedido.get(position).getNome_produto() + " não esta mais disponivel para venda externa!");
                alert.setNeutralButton("OK", null);
                alert.show();
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
