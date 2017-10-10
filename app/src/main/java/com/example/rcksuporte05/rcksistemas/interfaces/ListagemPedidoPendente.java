package com.example.rcksuporte05.rcksistemas.interfaces;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterPedidoPendente;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.classes.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListagemPedidoPendente extends AppCompatActivity {

    private ListView lstPedidoPendente;
    private ListaAdapterPedidoPendente listaAdapterPedidoPendente;
    private List<WebPedido> listaPedido = new ArrayList();
    private EditText edtNumerPedidoPendentes;
    private DBHelper db = new DBHelper(this);
    private Bundle bundle;
    private Usuario usuario;
    private Thread a = new Thread();
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pedido_pendente);

        bundle = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPedidoPendente);
        toolbar.setTitle("Pedidos Pendentes");
        setSupportActionBar(toolbar);
        usuario = db.listaUsuario("SELECT * FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consulta("SELECT * FROM TBL_LOGIN;", "LOGIN") + "';").get(0);
        lstPedidoPendente = (ListView) findViewById(R.id.lstPedidoPendente);
        lstPedidoPendente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListagemPedidoPendente.this, "Pressione e segure sobre o pedido para altera-lo!", Toast.LENGTH_SHORT).show();
            }
        });
        edtNumerPedidoPendentes = (EditText) findViewById(R.id.edtNumeroPedidoPendente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

                            enviarPedidos();
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

    @Override
    public void onCreateContextMenu(final ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Pedido: " + listaPedido.get(info.position).getId_web_pedido());

        MenuItem enviar = menu.add("Enviar pedido");
        enviar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                alert.setTitle("Atenção!");
                alert.setMessage("Deseja enviar o pedido " + listaPedido.get(info.position).getId_web_pedido() + " para ser faturado?");
                alert.setNegativeButton("Não", null);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progress = new ProgressDialog(ListagemPedidoPendente.this);
                        progress.setMessage("Enviando pedido " + listaPedido.get(info.position).getId_web_pedido() + "...");
                        progress.setTitle("Atenção!");
                        progress.setCancelable(false);
                        progress.show();

                        enviarPedido(listaPedido.get(info.position));
                    }
                });
                alert.show();
                return false;
            }

        });


        MenuItem alterar = menu.add("Alterar");
        alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ListagemPedidoPendente.this, ActivityPedidoMain.class);
                PedidoHelper.setIdPedido(Integer.parseInt(listaPedido.get(info.position).getId_web_pedido()));
                bundle = getIntent().getExtras();
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }
        });

        MenuItem excluir = menu.add("Excluir");
        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoPendente.this);
                alert.setTitle("Atenção!");
                alert.setMessage("Deseja realmente excluir o pedido " + listaPedido.get(info.position).getId_web_pedido() + "?");
                alert.setNegativeButton("Não", null);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            db.alterar("DELETE FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + listaPedido.get(info.position).getId_web_pedido());
                            db.alterar("DELETE FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + listaPedido.get(info.position).getId_web_pedido());
                            listaPedido.remove(info.position);
                            listaAdapterPedidoPendente.notifyDataSetChanged();
                            edtNumerPedidoPendentes.setText(listaPedido.size() + ": Pedidos Pendentes");
                            Toast.makeText(ListagemPedidoPendente.this, "Pedido exlcuido com sucesso!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            Toast.makeText(ListagemPedidoPendente.this, "Não foi possivel excluir o pedido!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                alert.show();
                return false;
            }
        });
    }


    public void enviarPedidos() {

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

        Call<List<WebPedido>> call = apiRotas.enviarPedidos(listaPedido);


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
                            .setContentText(listaPedido.size() + " pedidos enviados.")
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
                    onResume();
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
                .setContentText("Estabelecendo Conexão")
                .setProgress(0, 0, true)
                .setPriority(2);
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificacao.build());

        final List<WebPedido> pedido = new ArrayList<>();
        pedido.add(prepararItemPedido(webPedido));

        final Rotas apiRotas = Api.buildRetrofit();

        Call<List<WebPedido>> call = apiRotas.enviarPedidos(pedido);
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
                            .setContentText(listaPedido.size() + " pedidos enviados.")
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
        MenuItem item = menu.findItem(R.id.busca_pedido_pendente);

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
                a = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!query.equals("")) {
                            try {
                                listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " AND (NOME_EXTENSO LIKE '%" + query + "%' OR ID_WEB_PEDIDO LIKE '" + query + "') ORDER BY ID_WEB_PEDIDO DESC;");
                                listaAdapterPedidoPendente = new ListaAdapterPedidoPendente(ListagemPedidoPendente.this, listaPedido);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (listaPedido.size() > 1) {
                                            edtNumerPedidoPendentes.setText(listaPedido.size() + ": Pedidos Encontrados");
                                            edtNumerPedidoPendentes.setTextColor(Color.BLACK);
                                        } else if (listaPedido.size() == 1) {
                                            edtNumerPedidoPendentes.setText(listaPedido.size() + ": Pedido Encontrado");
                                            edtNumerPedidoPendentes.setTextColor(Color.BLACK);
                                        } else if (listaPedido.size() <= 0) {
                                            edtNumerPedidoPendentes.setText("Nenhum pedido encontrado");
                                            edtNumerPedidoPendentes.setTextColor(Color.RED);
                                        }
                                    }
                                });

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            lstPedidoPendente.setVisibility(View.VISIBLE);
                                            lstPedidoPendente.setAdapter(listaAdapterPedidoPendente);
                                            listaAdapterPedidoPendente.notifyDataSetChanged();
                                        } catch (NullPointerException | IllegalStateException e) {
                                            System.out.println("listaAdapterPedidoPendente se nenhum dado!");
                                        }
                                    }
                                });
                            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lstPedidoPendente.setVisibility(View.INVISIBLE);
                                        Toast.makeText(ListagemPedidoPendente.this, "Sem resutados para '" + query + "'", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC;");
                                    listaAdapterPedidoPendente = new ListaAdapterPedidoPendente(ListagemPedidoPendente.this, listaPedido);
                                    lstPedidoPendente.setAdapter(listaAdapterPedidoPendente);
                                    edtNumerPedidoPendentes.setText(listaPedido.size() + ": Pedidos Pendentes");
                                    edtNumerPedidoPendentes.setTextColor(Color.BLACK);
                                    lstPedidoPendente.setVisibility(View.VISIBLE);
                                    listaAdapterPedidoPendente.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });
                if (!a.isAlive()) {
                    a.start();
                }
                System.gc();
                return false;
            }
        });

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Nome Cliente / Nº pedido");
        return true;
    }

    @Override
    protected void onResume() {
        try {
            listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC;");

            listaAdapterPedidoPendente = new ListaAdapterPedidoPendente(ListagemPedidoPendente.this, listaPedido);
            lstPedidoPendente.setAdapter(listaAdapterPedidoPendente);

            registerForContextMenu(lstPedidoPendente);

        } catch (CursorIndexOutOfBoundsException e) {
            listaPedido.clear();
            Toast.makeText(this, "Nenuhm pedido pendente!", Toast.LENGTH_LONG).show();
        }
        edtNumerPedidoPendentes.setText(listaPedido.size() + ": Pedidos Pendentes");
        super.onResume();
    }
}
