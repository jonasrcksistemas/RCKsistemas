package com.example.rcksuporte05.rcksistemas.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.bo.PedidoBO;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListagemPedidoPendente extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ListaPedidoAdapter.PedidoAdapterListener {

    ActionModeCallback actionModeCallback;
    @BindView(R.id.listaPedidosPendentes)
    RecyclerView recyclerViewPedidos;
    private List<WebPedido> listaPedido = new ArrayList();
    private EditText edtNumerPedidoPendentes;
    private DBHelper db = new DBHelper(this);
    private Usuario usuario;
    private ProgressDialog progress;
    private ActionMode actionMode;
    private PedidoBO pedidoBO = new PedidoBO();
    private ListaPedidoAdapter listaPedidoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pedido_pendente);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPedidoPendente);
        toolbar.setTitle("Pedidos Pendentes");
        setSupportActionBar(toolbar);
        usuario = UsuarioHelper.getUsuario();
        edtNumerPedidoPendentes = (EditText) findViewById(R.id.edtNumeroPedidoPendente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewPedidos.setLayoutManager(layoutManager);
        recyclerViewPedidos.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        actionModeCallback = new ActionModeCallback();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_pedido_pendente:
                if (listaPedido.size() > 0) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Atenção!");
                    alert.setMessage("Deseja sincronizar os pedidos?");
                    alert.setNegativeButton("NÃO", null);
                    alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progress = new ProgressDialog(ListagemPedidoPendente.this);
                            progress.setMessage("Enviando pedidos...");
                            progress.setTitle("Atenção!");
                            progress.setCancelable(false);
                            progress.show();

                            enviarPedidos(listaPedido);
                        }
                    });
                    alert.show();

                    break;
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Atenção!");
                    alert.setMessage("Não há pedidos pendentes para enviar!");
                    alert.setNeutralButton("OK", null);
                    alert.show();
                }
        }
        return super.onOptionsItemSelected(item);
    }


    public void enviarPedidos(final List<WebPedido> listaParaEnvio) {

        final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(ListagemPedidoPendente.this)
                .setSmallIcon(R.mipmap.ic_enviar_pedidos)
                .setContentTitle("Enviando pedidos")
                .setContentText("Estabelecendo Conexão")
                .setProgress(0, 0, true)
                .setPriority(2);
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificacao.build());

        prepararItensPedidos();

        final Rotas apiRotas = Api.buildRetrofit();
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<List<WebPedido>> call = apiRotas.enviarPedidos(listaParaEnvio, cabecalho);

        call.enqueue(new Callback<List<WebPedido>>() {
            @Override
            public void onResponse(Call<List<WebPedido>> call, Response<List<WebPedido>> response) {
                if (response.code() == 200) {
                    List<WebPedido> webPedidosEnviados = response.body();

                    for (WebPedido pedido : webPedidosEnviados) {
                        db.atualizarTBL_WEB_PEDIDO(pedido);
                        for (WebPedidoItens pedidoIten : pedido.getWebPedidoItens()) {
                            db.atualizarTBL_WEB_PEDIDO_ITENS(pedidoIten);
                        }
                    }

                    PendingIntent pendingIntent = PendingIntent.getActivity(ListagemPedidoPendente.this, 0, new Intent(ListagemPedidoPendente.this, ListagemPedidoEnviado.class), 0);

                    notificacao.setContentTitle("Pedidos enviados com sucesso!")
                            .setContentText(listaParaEnvio.size() + " pedidos enviados.")
                            .setSmallIcon(R.mipmap.ic_sincronia_sucesso)
                            .setPriority(2)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .addAction(0, "PEDIDOS ENVIADOS", pendingIntent)
                            .setProgress(0, 0, false)
                            .setAutoCancel(true);
                    notificationManager.notify(0, notificacao.build());
                    onResume();
                    progress.dismiss();
                } else {
                    notificacao.setContentText("Não foi possivel enviar os pedidos")
                            .setContentTitle("Problema de conexão")
                            .setProgress(0, 0, false)
                            .setSmallIcon(R.mipmap.ic_sem_internet)
                            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                            .setPriority(2);
                    notificationManager.notify(0, notificacao.build());
                    onResume();
                    progress.dismiss();
                    Toast.makeText(ListagemPedidoPendente.this, "Não foi possivel enviar os pedidos", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<WebPedido>> call, Throwable t) {
                onResume();
                progress.dismiss();
                notificacao.setContentText("Não foi possivel enviar os pedidos")
                        .setContentTitle("Problema de conexão")
                        .setProgress(0, 0, false)
                        .setSmallIcon(R.mipmap.ic_sem_internet)
                        .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                        .setPriority(2);
                notificationManager.notify(0, notificacao.build());
                Toast.makeText(ListagemPedidoPendente.this, "Não foi possivel enviar os pedidos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void enviarPedido(final WebPedido webPedido) {

        final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(ListagemPedidoPendente.this)
                .setSmallIcon(R.mipmap.ic_enviar_pedidos)
                .setContentTitle("Enviando pedido " + webPedido.getId_web_pedido())
                .setProgress(0, 0, true)
                .setPriority(2);
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificacao.build());

        final List<WebPedido> pedido = new ArrayList<>();
        pedido.add(prepararItemPedido(webPedido));

        final Rotas apiRotas = Api.buildRetrofit();
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        Call<List<WebPedido>> call = apiRotas.enviarPedidos(pedido, cabecalho);

        call.enqueue(new Callback<List<WebPedido>>() {
            @Override
            public void onResponse(Call<List<WebPedido>> call, Response<List<WebPedido>> response) {
                if (response.code() == 200) {
                    final List<WebPedido> webPedidosEnviados = response.body();

                    db.atualizarTBL_WEB_PEDIDO(webPedidosEnviados.get(0));
                    for (WebPedidoItens pedidoIten : webPedidosEnviados.get(0).getWebPedidoItens()) {
                        db.atualizarTBL_WEB_PEDIDO_ITENS(pedidoIten);
                    }

                    PendingIntent pendingIntent = PendingIntent.getActivity(ListagemPedidoPendente.this, 0, new Intent(ListagemPedidoPendente.this, ListagemPedidoEnviado.class), 0);

                    notificacao.setContentTitle("Pedido " + webPedidosEnviados.get(0).getId_web_pedido_servidor() + " enviado com sucesso!")
                            .setSmallIcon(R.mipmap.ic_sincronia_sucesso)
                            .setPriority(2)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .addAction(0, "PEDIDOS ENVIADOS", pendingIntent)
                            .setProgress(0, 0, false)
                            .setAutoCancel(true);
                    notificationManager.notify(0, notificacao.build());
                    onResume();
                    progress.dismiss();
                } else {
                    notificacao.setContentText("Não foi possivel enviar os pedidos")
                            .setContentTitle("Problema de conexão")
                            .setProgress(0, 0, false)
                            .setSmallIcon(R.mipmap.ic_sem_internet)
                            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                            .setPriority(2);
                    notificationManager.notify(0, notificacao.build());
                    Toast.makeText(ListagemPedidoPendente.this, "Não foi possivel enviar os pedidos", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<WebPedido>> call, Throwable t) {
                notificacao.setContentText("Não foi possivel enviar os pedidos")
                        .setContentTitle("Problema de conexão")
                        .setProgress(0, 0, false)
                        .setSmallIcon(R.mipmap.ic_sem_internet)
                        .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                        .setPriority(2);
                notificationManager.notify(0, notificacao.build());
                onResume();
                progress.dismiss();
                Toast.makeText(ListagemPedidoPendente.this, "Não foi possivel enviar os pedidos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void prepararItensPedidos() {
        for (WebPedido pedido : listaPedido) {
            List<WebPedidoItens> webPedidoItenses;

            webPedidoItenses = db.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido());
            pedido.setWebPedidoItens(webPedidoItenses);
        }

    }

    public WebPedido prepararItemPedido(WebPedido webPedido) {

        List<WebPedidoItens> webPedidoItenses;

        webPedidoItenses = db.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedido.getId_web_pedido());
        webPedido.setWebPedidoItens(webPedidoItenses);

        return webPedido;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_pedido_pendente, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        final MenuItem item = menu.findItem(R.id.busca_pedido_pendente);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchView = (SearchView) item.getActionView();
        } else {
            searchView = (SearchView) MenuItemCompat.getActionView(item);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                if (query.trim().equals("")) {
                    preechePedidosRecycler(listaPedido);
                    edtNumerPedidoPendentes.setText(listaPedido.size() + ": Pedidos Pendentes");
                    edtNumerPedidoPendentes.setTextColor(Color.BLACK);
                } else {
                    List<WebPedido> listaBusca = buscaPedidoPendente(listaPedido, query);
                    preechePedidosRecycler(listaBusca);
                    if (listaBusca.size() > 0) {
                        edtNumerPedidoPendentes.setText(listaBusca.size() + ": Pedidos Encontrados");
                        edtNumerPedidoPendentes.setTextColor(Color.BLACK);
                    } else {
                        edtNumerPedidoPendentes.setText("Nenum pedido encontrado");
                        edtNumerPedidoPendentes.setTextColor(Color.RED);
                    }
                }

                System.gc();
                return false;
            }
        });

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Nome Cliente / Nº pedido");
        return true;
    }

    public List<WebPedido> buscaPedidoPendente(List<WebPedido> webPedidos, String query) {
        final String upperCaseQuery = query.toUpperCase();

        final List<WebPedido> lista = new ArrayList<>();
        for (WebPedido webPedido : webPedidos) {
            try {
                final String nomeCliente = webPedido.getNome_extenso().toUpperCase();
                final String numeroPedido = webPedido.getId_web_pedido().toUpperCase();

                if (nomeCliente.contains(upperCaseQuery) || numeroPedido.equals(upperCaseQuery)) {
                    lista.add(webPedido);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    public void preechePedidosRecycler(List<WebPedido> listaPedido) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewPedidos.setLayoutManager(layoutManager);

        listaPedidoAdapter = new ListaPedidoAdapter(listaPedido, this);
        recyclerViewPedidos.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerViewPedidos.setAdapter(listaPedidoAdapter);

        listaPedidoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        try {
            listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC;");

            preechePedidosRecycler(listaPedido);

            if (actionMode == null) {
                listaPedidoAdapter.clearSelections();
            } else
                actionMode.finish();


        } catch (CursorIndexOutOfBoundsException e) {
            listaPedido.clear();
            Toast.makeText(this, "Nenuhm pedido pendente!", Toast.LENGTH_LONG).show();
        }
        edtNumerPedidoPendentes.setText(listaPedido.size() + ": Pedidos Pendentes");
        super.onResume();
    }

    @Override
    public void onPedidoRowClicked(int position) {
        if (listaPedidoAdapter.getSelectedItemCount() > 0) {
            enableActionMode(position);
        } else {
            Intent intent = new Intent(ListagemPedidoPendente.this, ActivityPedidoMain.class);
            PedidoHelper.setIdPedido(Integer.parseInt(listaPedidoAdapter.getItem(position).getId_web_pedido()));
            startActivity(intent);
        }
    }

    @Override
    public void onRowLongClicked(int position) {
        enableActionMode(position);
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        listaPedidoAdapter.toggleSelection(position);
        int count = listaPedidoAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    @Override
    public void onRefresh() {
        listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC;");

        preechePedidosRecycler(listaPedido);

    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    final List<WebPedido> pedidosSelecionadosDelete = listaPedidoAdapter.getItensSelecionados();
                    if (pedidosSelecionadosDelete.size() > 0) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                        alert.setTitle("Atenção!");
                        alert.setMessage("Deseja realmente excluir o pedido " + pedidosSelecionadosDelete.get(0).getId_web_pedido() + "?");
                        alert.setNegativeButton("Não", null);
                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    pedidoBO.excluirPedido(ListagemPedidoPendente.this, pedidosSelecionadosDelete);
                                    listaPedidoAdapter.remove(pedidosSelecionadosDelete.get(0));
                                    onResume();
                                    Toast.makeText(ListagemPedidoPendente.this, "Pedido exlcuido com sucesso!", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(ListagemPedidoPendente.this, "Não foi possivel excluir o pedido!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        alert.show();
                        mode.finish();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                        alert.setTitle("Atenção!");
                        alert.setMessage("Deseja realmente excluir os pedidos?");
                        alert.setNegativeButton("Não", null);
                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    pedidoBO.excluirPedido(ListagemPedidoPendente.this, pedidosSelecionadosDelete);
                                    for (WebPedido pedido : pedidosSelecionadosDelete) {
                                        listaPedidoAdapter.remove(pedido);
                                    }
                                    onResume();
                                    Toast.makeText(ListagemPedidoPendente.this, "Pedidos exlcuidos com sucesso!", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                    Toast.makeText(ListagemPedidoPendente.this, "Não foi possivel excluir os pedidos!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        alert.show();

                    }
                    return true;
                case R.id.action_mode_menu_pedido_pendente:
                    final List<WebPedido> pedidosSelecionadosEnviar = listaPedidoAdapter.getItensSelecionados();
                    if (pedidosSelecionadosEnviar.size() == 1) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                        alert.setTitle("Atenção!");
                        alert.setMessage("Deseja enviar o pedido " + pedidosSelecionadosEnviar.get(0).getId_web_pedido() + " para ser faturado?");
                        alert.setNegativeButton("Não", null);
                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progress = new ProgressDialog(ListagemPedidoPendente.this);
                                        progress.setMessage("Enviando pedido " + pedidosSelecionadosEnviar.get(0).getId_web_pedido() + "...");
                                        progress.setTitle("Atenção!");
                                        progress.setCancelable(false);
                                        progress.show();
                                        enviarPedido(pedidosSelecionadosEnviar.get(0));
                                    }
                                }
                        );
                        alert.show();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                        alert.setTitle("Atenção!");
                        alert.setMessage("Deseja sincronizar os pedidos?");
                        alert.setNegativeButton("NÃO", null);
                        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progress = new ProgressDialog(ListagemPedidoPendente.this);
                                progress.setMessage("Enviando pedidos...");
                                progress.setTitle("Atenção!");
                                progress.setCancelable(false);
                                progress.show();

                                enviarPedidos(pedidosSelecionadosEnviar);
                            }
                        });
                        alert.show();
                    }
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            listaPedidoAdapter.clearSelections();
            actionMode = null;
            onRefresh();
        }


    }

}
