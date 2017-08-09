package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
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
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.classes.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.extras.BancoWeb;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListagemPedidoPendente extends AppCompatActivity {

    private ListView lstPedidoPendente;
    private ListaAdapterPedidoPendente listaAdapterPedidoPendente;
    private List<WebPedido> listaPedido = new ArrayList();
    private List<WebPedidoItens> listaPedidoItens = new ArrayList();
    private EditText edtNumerPedidoPendentes;
    private MenuItem subirPedido;
    private BancoWeb bancoWeb = new BancoWeb();
    private DBHelper db = new DBHelper(this);
    private Bundle bundle;
    private Usuario usuario;
    private Thread a = new Thread();

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
                        public void onClick(final DialogInterface dialog, int which) {
                            final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(ListagemPedidoPendente.this)
                                    .setSmallIcon(R.mipmap.ic_enviar_pedidos)
                                    .setContentTitle("Enviando pedidos")
                                    .setContentText("Estabelecendo Conexão")
                                    .setProgress(0, 0, true)
                                    .setPriority(2);
                            final NotificationManager notificationManager =
                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(0, notificacao.build());

                            Thread a = new Thread(new Runnable() {
                                @Override
                                public void run() {


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ListagemPedidoPendente.this, "Enviando pedidos...", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    try {
                                        String idWebPedido;
                                        String idWebPedidoItem;
                                        for (int i = 0; listaPedido.size() > i; i++) {
                                            notificacao.setContentText("Enviando pedido " + listaPedido.get(i).getId_web_pedido())
                                                    .setProgress(listaPedido.size(), i, false)
                                                    .setContentText("Enviando...")
                                                    .setPriority(2);

                                            idWebPedido = bancoWeb.sincronizaWebPedido(listaPedido.get(i));
                                            db.alterar("UPDATE TBL_WEB_PEDIDO SET ID_WEB_PEDIDO_SERVIDOR = " + idWebPedido + " WHERE ID_WEB_PEDIDO = " + listaPedido.get(i).getId_web_pedido());

                                            listaPedidoItens = db.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + listaPedido.get(i).getId_web_pedido());
                                            for (int c = 0; listaPedidoItens.size() > c; c++) {
                                                idWebPedidoItem = bancoWeb.sincronizaWebPedidoItens(listaPedidoItens.get(c));
                                                db.alterar("UPDATE TBL_WEB_PEDIDO_ITENS SET ID_WEB_ITEM_SERVIDOR = " + idWebPedidoItem + ", ITEM_ENVIADO = 'S' WHERE ID_WEB_ITEM = " + listaPedidoItens.get(c).getId_web_item());
                                            }
                                            bancoWeb.Atualiza("UPDATE TBL_WEB_PEDIDO SET STATUS = 'P' WHERE ID_WEB_PEDIDO = " + idWebPedido + ";");
                                            db.alterar("UPDATE TBL_WEB_PEDIDO SET PEDIDO_ENVIADO = 'S', STATUS = 'P' WHERE ID_WEB_PEDIDO = " + listaPedido.get(i).getId_web_pedido());
                                            listaPedidoItens.clear();

                                            notificationManager.notify(0, notificacao.build());
                                        }

                                        PendingIntent pendingIntent = PendingIntent.getActivity(ListagemPedidoPendente.this, 0, new Intent(ListagemPedidoPendente.this, ListagemPedidoEnviado.class), 0);

                                        notificacao.setContentTitle("Pedidos enviados com sucesso!")
                                                .setContentText(listaPedido.size() + " pedidos enviados.")
                                                .setSmallIcon(R.mipmap.ic_sincronia_sucesso)
                                                .setPriority(2)
                                                .setDefaults(NotificationCompat.DEFAULT_ALL)
                                                .addAction(0, "PEDIDOS ENVIDADOS", pendingIntent)
                                                .setProgress(0, 0, false)
                                                .setAutoCancel(true);
                                        notificationManager.notify(0, notificacao.build());

                                    } catch (XmlPullParserException | IOException e) {
                                        notificacao.setContentText("Não foi possivel enviar os pedidos")
                                                .setContentTitle("Problema de conexão")
                                                .setProgress(0, 0, false)
                                                .setSmallIcon(R.mipmap.ic_sem_internet)
                                                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                                                .setPriority(2);
                                        notificationManager.notify(0, notificacao.build());

                                    } finally {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N'");

                                                    listaAdapterPedidoPendente = new ListaAdapterPedidoPendente(ListagemPedidoPendente.this, listaPedido);
                                                    lstPedidoPendente.setAdapter(listaAdapterPedidoPendente);

                                                } catch (CursorIndexOutOfBoundsException e) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            lstPedidoPendente.setVisibility(View.INVISIBLE);
                                                            Toast.makeText(ListagemPedidoPendente.this, "Pedidos sincronizados com sucesso!", Toast.LENGTH_LONG).show();
                                                            edtNumerPedidoPendentes.setText("0: Pedidos Pendentes");
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                            a.start();
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
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
                        final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(ListagemPedidoPendente.this)
                                .setSmallIcon(R.mipmap.ic_enviar_pedidos)
                                .setContentTitle("Enviando pedido " + listaPedido.get(info.position).getId_web_pedido())
                                .setContentText("Estabelecendo Conexão")
                                .setProgress(0, 0, true)
                                .setPriority(2);
                        final NotificationManager notificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0, notificacao.build());

                        Thread a = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ListagemPedidoPendente.this, "Enviando pedido...", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                notificacao.setContentText("Enviando...")
                                        .setPriority(2);
                                notificationManager.notify(0, notificacao.build());
                                try {
                                    String idWebPedido = bancoWeb.sincronizaWebPedido(listaPedido.get(info.position));
                                    db.alterar("UPDATE TBL_WEB_PEDIDO SET ID_WEB_PEDIDO_SERVIDOR = " + idWebPedido + " WHERE ID_WEB_PEDIDO = " + listaPedido.get(info.position).getId_web_pedido());
                                    listaPedidoItens = db.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + listaPedido.get(info.position).getId_web_pedido());
                                    for (int i = 0; listaPedidoItens.size() > i; i++) {
                                        notificacao.setProgress(listaPedidoItens.size(), i, false)
                                                .setPriority(2);
                                        String idWebPedidoItem = bancoWeb.sincronizaWebPedidoItens(listaPedidoItens.get(i));
                                        db.alterar("UPDATE TBL_WEB_PEDIDO_ITENS SET ID_WEB_ITEM_SERVIDOR = " + idWebPedidoItem + ", ITEM_ENVIADO = 'S' WHERE ID_WEB_ITEM = " + listaPedidoItens.get(i).getId_web_item());
                                        notificationManager.notify(0, notificacao.build());
                                    }
                                    bancoWeb.Atualiza("UPDATE TBL_WEB_PEDIDO SET STATUS = 'P' WHERE ID_WEB_PEDIDO = " + idWebPedido + ";");
                                    db.alterar("UPDATE TBL_WEB_PEDIDO SET PEDIDO_ENVIADO = 'S', STATUS = 'P' WHERE ID_WEB_PEDIDO = " + listaPedido.get(info.position).getId_web_pedido());
                                    listaPedidoItens.clear();

                                    PendingIntent pendingIntent = PendingIntent.getActivity(ListagemPedidoPendente.this, 0, new Intent(ListagemPedidoPendente.this, ListagemPedidoEnviado.class), 0);
                                    notificacao.setContentTitle("Envio bem sucedido.")
                                            .setContentText("Pedido " + idWebPedido + " enviado com sucesso!")
                                            .setSmallIcon(R.mipmap.ic_sincronia_sucesso)
                                            .setPriority(2)
                                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                                            .addAction(0, "PEDIDOS ENVIDADOS", pendingIntent)
                                            .setProgress(0, 0, false)
                                            .setAutoCancel(true);
                                    notificationManager.notify(0, notificacao.build());

                                } catch (XmlPullParserException | IOException e) {

                                    notificacao.setContentText("Não foi possivel enviar o pedido")
                                            .setContentTitle("Problema de conexão")
                                            .setProgress(0, 0, false)
                                            .setSmallIcon(R.mipmap.ic_sem_internet)
                                            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                                            .setPriority(2);
                                    notificationManager.notify(0, notificacao.build());

                                } finally {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N'");

                                                listaAdapterPedidoPendente = new ListaAdapterPedidoPendente(ListagemPedidoPendente.this, listaPedido);
                                                lstPedidoPendente.setAdapter(listaAdapterPedidoPendente);
                                                edtNumerPedidoPendentes.setText("0: Pedidos Pendentes");
                                            } catch (CursorIndexOutOfBoundsException e) {
                                                Toast.makeText(ListagemPedidoPendente.this, "Pedido " + listaPedido.get(info.position).getId_web_pedido() + " foi enviado com sucesso!", Toast.LENGTH_LONG).show();
                                                listaAdapterPedidoPendente.clear();
                                                edtNumerPedidoPendentes.setText("0: Pedidos Pendentes");
                                            }

                                        }
                                    });
                                }
                            }
                        });
                        a.start();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_pedido_pendente, menu);

        subirPedido = menu.findItem(R.id.menu_pedido_pendente);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.busca_pedido_pendente);

        //TODO Nullpointer no Listener da Busca

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
                        if (!query.equals("") && query.length() >= 3) {
                            try {
                                listaPedido = db.listaWebPedido("SELECT * FROM TBL_CADASTRO WHERE NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' ORDER BY ATIVO DESC, NOME_CADASTRO");
                                listaAdapterPedidoPendente = new ListaAdapterPedidoPendente(ListagemPedidoPendente.this, listaPedido);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            lstPedidoPendente.setVisibility(View.VISIBLE);
                                            lstPedidoPendente.setAdapter(listaAdapterPedidoPendente);
                                            listaAdapterPedidoPendente.notifyDataSetChanged();
                                        } catch (NullPointerException | IllegalStateException e) {
                                            System.out.println("adaptador se nenhum dado!");
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
                                    lstPedidoPendente.setVisibility(View.VISIBLE);
                                    lstPedidoPendente.setAdapter(listaAdapterPedidoPendente);
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
        searchView.setQueryHint("Nº pedido/ Nome cliente");
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
