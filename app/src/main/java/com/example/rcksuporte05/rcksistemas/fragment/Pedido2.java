package com.example.rcksuporte05.rcksistemas.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterProdutoPedido;
import com.example.rcksuporte05.rcksistemas.classes.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.interfaces.ProdutoPedidoActivity;

import java.util.ArrayList;
import java.util.List;

public class Pedido2 extends Fragment {

    private static WebPedidoItens webPedidoIten;
    private static ListaAdapterProdutoPedido adapter;
    DBHelper db = new DBHelper(PedidoHelper.getActivityPedidoMain());
    private List<WebPedidoItens> listaProdutoPedido = new ArrayList<>();
    private ListView lstProdutoPedido;
    private Button btnInserirProduto;
    private Button btnFinalizarPedido;
    private TextView txtFinalizarPedido;
    private Bundle bundle;
    private int posicao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pedido2, container, false);

        final PedidoHelper pedidoHelper = new PedidoHelper(this);
        bundle = getArguments();
        lstProdutoPedido = (ListView) view.findViewById(R.id.lstProdutosPedido);

        lstProdutoPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bundle.getInt("vizualizacao") == 1) {
                    Toast.makeText(getContext(), "Pressione e segure sobre o produto para vizualiza-lo!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Pressione e segure sobre o produto para altera-lo!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFinalizarPedido = (Button) view.findViewById(R.id.btnFinalizarPedido);
        btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidoHelper.moveTela(2);
            }
        });

        txtFinalizarPedido = (TextView) view.findViewById(R.id.txtFinalizarPedido);
        txtFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidoHelper.moveTela(2);
            }
        });

        btnInserirProduto = (Button) view.findViewById(R.id.btnInserirProduto);
        btnInserirProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pedidoHelper.verificaCliente()) {
                    Intent intent = new Intent(getContext(), ProdutoPedidoActivity.class);
                    posicao = listaProdutoPedido.size();
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Selecione um cliente antes de prosseguir!", Toast.LENGTH_SHORT).show();
                    pedidoHelper.moveTela(0);
                }
            }
        });

        if (PedidoHelper.getIdPedido() > 0) {
            final int pedido = PedidoHelper.getIdPedido();
            try {
                listaProdutoPedido = db.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido);
                adapter = new ListaAdapterProdutoPedido(getContext(), listaProdutoPedido);
                lstProdutoPedido.setAdapter(adapter);
            } catch (CursorIndexOutOfBoundsException e) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Atenção!");
                alert.setMessage("O pedido encontra-se sem produtos, deseja exclui-lo?");
                alert.setNegativeButton("Não", null);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.alterar("DELETE FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + pedido);
                        db.alterar("DELETE FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido);
                        PedidoHelper.getActivityPedidoMain().finish();
                    }
                });
                alert.show();
                System.out.println(e.getMessage());
            }
        }

        if (bundle.getInt("vizualizacao") == 1) {
            btnInserirProduto.setVisibility(View.INVISIBLE);
        }

        registerForContextMenu(lstProdutoPedido);
        System.gc();
        return (view);
    }

    public void inserirProduto(WebPedidoItens webPedidoItem) {
        webPedidoItem.setId_pedido(String.valueOf(db.contagem("SELECT MAX(ID_WEB_PEDIDO) FROM TBL_WEB_PEDIDO") + 1));
        webPedidoItem.setId_empresa("1");
        webPedidoItem.setUsuario_lancamento_id(String.valueOf(bundle.getInt("idUsuario")));
        webPedidoItem.setId_pedido("1");
        listaProdutoPedido.add(webPedidoItem);
        adapter = new ListaAdapterProdutoPedido(getContext(), listaProdutoPedido);
        lstProdutoPedido.setAdapter(adapter);
    }

    public void alterarProduto(WebPedidoItens webPedidoItem, int position) {
        webPedidoItem.setId_empresa("1");
        webPedidoItem.setUsuario_lancamento_id(String.valueOf(bundle.getInt("idUsuario")));
        listaProdutoPedido.set(position, webPedidoItem);
        adapter = new ListaAdapterProdutoPedido(getContext(), listaProdutoPedido);
        lstProdutoPedido.setAdapter(adapter);
    }

    public List<WebPedidoItens> salvaPedidos() {
        return listaProdutoPedido;
    }

    @Override
    public void onResume() {
        PedidoHelper pedidoHelper = new PedidoHelper(PedidoHelper.getActivityPedidoMain());
        pedidoHelper.calculaValorPedido(listaProdutoPedido);
        lstProdutoPedido.setSelection(posicao);
        System.gc();
        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(listaProdutoPedido.get(info.position).getNome_produto());

        if (bundle.getInt("vizualizacao") != 1) {
            MenuItem alterar = menu.add("Alterar");
            alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent intent = new Intent(getContext(), ProdutoPedidoActivity.class);
                    intent.putExtra("pedido", 1);
                    intent.putExtra("position", info.position);
                    posicao = info.position;
                    PedidoHelper.getProdutoPedidoActivity().pegaProduto(listaProdutoPedido.get(info.position));
                    PedidoHelper.setWebPedidoItem(listaProdutoPedido.get(info.position));
                    startActivity(intent);
                    return false;
                }
            });
            if (listaProdutoPedido.size() > 1) {

                MenuItem deletar = menu.add("Excluir");
                deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Atenção!");
                        alert.setMessage("Tem certeza que deseja remover este produto do pedido?");
                        alert.setNegativeButton("Não", null);
                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    if (!listaProdutoPedido.get(info.position).getId_web_item().trim().isEmpty()) {
                                        db.alterar("DELETE FROM TBL_WEB_PEDIDO_ITENS WHERE ID_WEB_ITEM = " + listaProdutoPedido.get(info.position).getId_web_item());
                                    }
                                } catch (NullPointerException e) {
                                    System.out.println(e.getMessage());
                                }
                                listaProdutoPedido.remove(info.position);
                                adapter.notifyDataSetChanged();
                                PedidoHelper pedidoHelper = new PedidoHelper(PedidoHelper.getActivityPedidoMain());
                                pedidoHelper.calculaValorPedido(listaProdutoPedido);
                            }
                        });
                        alert.show();
                        return false;
                    }
                });
            }
        } else {
            MenuItem alterar = menu.add("Vizualizar");
            alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent intent = new Intent(getContext(), ProdutoPedidoActivity.class);
                    intent.putExtra("pedido", 1);
                    intent.putExtra("vizualizacao", 1);
                    intent.putExtra("position", info.position);
                    posicao = info.position;
                    PedidoHelper.setWebPedidoItem(listaProdutoPedido.get(info.position));
                    startActivity(intent);
                    return false;
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        try {
            webPedidoIten = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.gc();
        super.onDestroy();
    }
}
