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
import android.net.Uri;
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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.PedidoBO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.PDFPedidoUtil;

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
    private WebPedidoDAO webPedidoDAO;
    private WebPedidoItensDAO webPedidoItensDAO;
    private Usuario usuario;
    private ProgressDialog progress;
    private PedidoBO pedidoBO = new PedidoBO();
    private ListaPedidoAdapter listaPedidoAdapter;
    private MenuItem geraPDF;
    private MenuItem emailPDF;
    private MenuItem duplicaPedido;
    private ActionMode actionMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pedido_pendente);
        ButterKnife.bind(this);

        webPedidoDAO = new WebPedidoDAO(db);
        webPedidoItensDAO = new WebPedidoItensDAO(db);

        Toolbar toolbar = findViewById(R.id.toolbarPedidoPendente);
        toolbar.setTitle("Pedidos Pendentes");
        setSupportActionBar(toolbar);
        usuario = UsuarioHelper.getUsuario();
        edtNumerPedidoPendentes = findViewById(R.id.edtNumeroPedidoPendente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewPedidos.setLayoutManager(layoutManager);

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
                        webPedidoDAO.atualizarTBL_WEB_PEDIDO(pedido);
                        for (WebPedidoItens pedidoIten : pedido.getWebPedidoItens()) {
                            webPedidoItensDAO.atualizarTBL_WEB_PEDIDO_ITENS(pedidoIten);
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

                    webPedidoDAO.atualizarTBL_WEB_PEDIDO(webPedidosEnviados.get(0));
                    for (WebPedidoItens pedidoIten : webPedidosEnviados.get(0).getWebPedidoItens()) {
                        webPedidoItensDAO.atualizarTBL_WEB_PEDIDO_ITENS(pedidoIten);
                    }

                    PendingIntent pendingIntent = PendingIntent.getActivity(ListagemPedidoPendente.this, 0, new Intent(ListagemPedidoPendente.this, ListagemPedidoPendente.class), 0);

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

            webPedidoItenses = webPedidoItensDAO.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido());
            pedido.setWebPedidoItens(webPedidoItenses);
        }

    }

    public WebPedido prepararItemPedido(WebPedido webPedido) {

        List<WebPedidoItens> webPedidoItenses;

        webPedidoItenses = webPedidoItensDAO.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedido.getId_web_pedido());
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

        listaPedidoAdapter = new ListaPedidoAdapter(listaPedido, this, this);
        recyclerViewPedidos.setAdapter(listaPedidoAdapter);

        listaPedidoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        try {
            listaPedido = webPedidoDAO.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC;");

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

    @Override
    public View.OnClickListener onClickExcluir(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deleteAlert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                deleteAlert.setTitle("Atenção!");
                deleteAlert.setMessage("Deseja realmente excluir o pedido " + listaPedidoAdapter.getItem(position).getId_web_pedido() + "?");
                deleteAlert.setNegativeButton("Não", null);
                deleteAlert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            pedidoBO.excluirPedido(ListagemPedidoPendente.this, listaPedidoAdapter.getItem(position));
                            listaPedidoAdapter.remove(listaPedidoAdapter.getItem(position));
                            onResume();
                            Toast.makeText(ListagemPedidoPendente.this, "Pedido exlcuido com sucesso!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(ListagemPedidoPendente.this, "Não foi possivel excluir o pedido!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                deleteAlert.show();
            }
        };
    }

    @Override
    public View.OnClickListener onClickEnviar(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                alert.setTitle("Atenção!");
                alert.setMessage("Deseja enviar o pedido " + listaPedidoAdapter.getItem(position).getId_web_pedido() + " para ser faturado?");
                alert.setNegativeButton("Não", null);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progress = new ProgressDialog(ListagemPedidoPendente.this);
                                progress.setMessage("Enviando pedido " + listaPedidoAdapter.getItem(position).getId_web_pedido() + "...");
                                progress.setTitle("Atenção!");
                                progress.setCancelable(false);
                                progress.show();
                                enviarPedido(listaPedidoAdapter.getItem(position));
                            }
                        }
                );
                alert.show();
            }
        };
    }

    @Override
    public View.OnClickListener onClickDuplic(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder duplicAlert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                duplicAlert.setTitle("Atenção");
                duplicAlert.setMessage("Deseja duplicar o pedido selecionado para poder faturá-lo novamente?");
                duplicAlert.setNegativeButton("Não", null);
                duplicAlert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(db);
                        listaPedidoAdapter.getItem(position).setWebPedidoItens(webPedidoItensDAO.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + listaPedidoAdapter.getItem(position).getId_web_pedido()));
                        listaPedidoAdapter.getItem(position).setId_web_pedido(null);
                        listaPedidoAdapter.getItem(position).setId_web_pedido_servidor(null);
                        listaPedidoAdapter.getItem(position).setPedido_enviado("N");
                        for (WebPedidoItens webPedidoItens : listaPedidoAdapter.getItem(position).getWebPedidoItens()) {
                            webPedidoItens.setId_web_item_servidor(null);
                            webPedidoItens.setId_pedido(null);
                        }
                        PedidoHelper.setWebPedido(listaPedidoAdapter.getItem(position));
                        PedidoHelper.setListaWebPedidoItens(listaPedidoAdapter.getItem(position).getWebPedidoItens());

                        Intent intent = new Intent(ListagemPedidoPendente.this, ActivityPedidoMain.class);
                        startActivity(intent);
                    }
                });
                duplicAlert.show();
            }
        };
    }

    @Override
    public View.OnClickListener onClickCompartilhar(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                alert.setTitle("Atenção");
                alert.setMessage("Deseja compartilhar o pedido " + listaPedidoAdapter.getItem(position).getId_web_pedido() + "?");
                alert.setNegativeButton("Não", null);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PDFPedidoUtil pdfPedidoUtil = new PDFPedidoUtil(listaPedidoAdapter.getItem(position), ListagemPedidoPendente.this);
                        Intent arquivo = new Intent(Intent.ACTION_SEND);
                        arquivo.setType("pdf/*");
                        arquivo.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pdfPedidoUtil.criandoPdf()));

                        Intent intent = Intent.createChooser(arquivo, "Compartilhar pedido");
                        startActivity(intent);
                    }
                });
                alert.show();
            }
        };
    }

    @Override
    public View.OnClickListener onClickRastrear(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ListagemPedidoPendente.this, "Em desenvolvimento!", Toast.LENGTH_LONG).show();
            }
        };
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
            if (geraPDF != null) {
                if (listaPedidoAdapter.getSelectedItemCount() > 1)
                    geraPDF.setVisible(false);
                else
                    geraPDF.setVisible(true);
            }
            if (emailPDF != null) {
                if (listaPedidoAdapter.getSelectedItemCount() > 1)
                    emailPDF.setVisible(false);
                else
                    emailPDF.setVisible(true);
            }
            if (duplicaPedido != null) {
                if (listaPedidoAdapter.getSelectedItemCount() > 1)
                    duplicaPedido.setVisible(false);
                else
                    duplicaPedido.setVisible(true);
            }
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    @Override
    public void onRefresh() {
        listaPedido = webPedidoDAO.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC;");
        preechePedidosRecycler(listaPedido);
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            geraPDF = menu.findItem(R.id.action_pdf_pedido);
            emailPDF = menu.findItem(R.id.action_pdf_pedido_email);
            duplicaPedido = menu.findItem(R.id.action_duplica_pedido);
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
                    AlertDialog.Builder deleteAlert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                    deleteAlert.setTitle("Atenção!");
                    if (pedidosSelecionadosDelete.size() == 1)
                        deleteAlert.setMessage("Deseja realmente excluir o pedido " + pedidosSelecionadosDelete.get(0).getId_web_pedido() + "?");
                    else
                        deleteAlert.setMessage("Deseja realmente excluir os " + pedidosSelecionadosDelete.size() + " pedidos selecionados?");
                    deleteAlert.setNegativeButton("Não", null);
                    deleteAlert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
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
                    deleteAlert.show();
                    mode.finish();

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

                case R.id.action_pdf_pedido:
                    AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                    alert.setTitle("Atenção");
                    alert.setMessage("Deseja gerar um arquivo PDF do pedido " + listaPedidoAdapter.getItensSelecionados().get(0).getId_web_pedido() + " selecionado ?");
                    alert.setNegativeButton("Não", null);
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PDFPedidoUtil pdfPedidoUtil = new PDFPedidoUtil(listaPedidoAdapter.getItensSelecionados().get(0), ListagemPedidoPendente.this);
                            Intent arquivo = new Intent(Intent.ACTION_VIEW);
                            arquivo.setDataAndType(Uri.fromFile(pdfPedidoUtil.criandoPdf()), "application/pdf");
                            arquivo.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                            Intent intent = Intent.createChooser(arquivo, "Abrir arquivo");
                            startActivity(intent);
                        }
                    });
                    alert.show();
                    return true;
                case R.id.action_pdf_pedido_email:
                    AlertDialog.Builder emailAlert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                    emailAlert.setTitle("Atenção");
                    if (listaPedidoAdapter.getSelectedItemCount() > 1)
                        emailAlert.setMessage("Deseja enviar os " + listaPedidoAdapter.getSelectedItemCount() + " pedidos selecionados para os emails de seus cliente?");
                    else
                        emailAlert.setMessage("Deseja enviar um arquivo PDF do pedido " + listaPedidoAdapter.getItensSelecionados().get(0).getId_web_pedido() + " para o email do cliente ?");
                    emailAlert.setNegativeButton("Não", null);
                    emailAlert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent intent = new Intent(Intent.ACTION_SENDTO);

                            PDFPedidoUtil pdfPedidoUtil = new PDFPedidoUtil(listaPedidoAdapter.getItensSelecionados().get(0), ListagemPedidoPendente.this);
                            if (listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_principal() != null && !listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_principal().trim().equals("")) {
                                intent.setData(Uri.parse("mailto: " + listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_principal()));
                            } else if (listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_financeiro() != null && !listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_financeiro().trim().equals("")) {
                                intent.setData(Uri.parse("mailto: " + listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_financeiro()));
                            } else {
                                intent.setData(Uri.parse("mailto: Informe o email do cliente"));
                            }
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Espelho do pedido " + listaPedidoAdapter.getItensSelecionados().get(0).getId_web_pedido());
                            intent.putExtra(Intent.EXTRA_TEXT, "Segue em anexo o espelho do pedido");
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pdfPedidoUtil.criandoPdf()));

                            startActivity(intent);
                        }
                    });
                    emailAlert.show();
                    return true;
                case R.id.action_duplica_pedido:
                    AlertDialog.Builder duplicAlert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                    duplicAlert.setTitle("Atenção");
                    duplicAlert.setMessage("Deseja duplicar o pedido selecionado para poder faturá-lo novamente?");
                    duplicAlert.setNegativeButton("Não", null);
                    duplicAlert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            WebPedido webPedidoDuplicado = listaPedidoAdapter.getItensSelecionados().get(0);
                            WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(db);
                            webPedidoDuplicado.setWebPedidoItens(webPedidoItensDAO.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedidoDuplicado.getId_web_pedido()));
                            webPedidoDuplicado.setId_web_pedido(null);
                            webPedidoDuplicado.setId_web_pedido_servidor(null);
                            webPedidoDuplicado.setPedido_enviado("N");
                            for (WebPedidoItens webPedidoItens : webPedidoDuplicado.getWebPedidoItens()) {
                                webPedidoItens.setId_web_item_servidor(null);
                                webPedidoItens.setId_pedido(null);
                            }
                            PedidoHelper.setWebPedido(webPedidoDuplicado);
                            PedidoHelper.setListaWebPedidoItens(webPedidoDuplicado.getWebPedidoItens());

                            Intent intent = new Intent(ListagemPedidoPendente.this, ActivityPedidoMain.class);
                            startActivity(intent);

                            actionMode.finish();
                        }
                    });
                    duplicAlert.show();
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
