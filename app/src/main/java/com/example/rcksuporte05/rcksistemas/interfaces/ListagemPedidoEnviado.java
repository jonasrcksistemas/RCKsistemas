package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
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
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterPedidoEnviado;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ListagemPedidoEnviado extends AppCompatActivity {

    private ListView lstPedidoEnviado;
    private ListaAdapterPedidoEnviado listaAdapterPedidoEnviado;
    private List<WebPedido> listaPedido = new ArrayList();
    private EditText edtNumerPedidoEnviados;
    private DBHelper db = new DBHelper(this);
    private Usuario usuario;
    private Thread a = new Thread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pedido_enviado);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPedidoEnviado);
        toolbar.setTitle("Pedidos Enviados");
        setSupportActionBar(toolbar);
        usuario = db.listaUsuario("SELECT * FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consulta("SELECT * FROM TBL_LOGIN;", "LOGIN") + "';").get(0);
        lstPedidoEnviado = (ListView) findViewById(R.id.lstPedidoEnviado);
        lstPedidoEnviado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListagemPedidoEnviado.this, "Pressione e segure sobre o pedido para vizualiza-lo!", Toast.LENGTH_SHORT).show();
            }
        });
        edtNumerPedidoEnviados = (EditText) findViewById(R.id.edtNumeroPedidoEnviado);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Pedido: " + listaPedido.get(info.position).getId_web_pedido_servidor());

        MenuItem visualizar = menu.add("Visualizar");
        visualizar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ListagemPedidoEnviado.this, ActivityPedidoMain.class);
                PedidoHelper.setIdPedido(Integer.parseInt(listaPedido.get(info.position).getId_web_pedido()));
                intent.putExtra("vizualizacao", 1);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_pedido_enviados, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.busca_pedido_enviados);

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
                                listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " AND (NOME_EXTENSO LIKE '%" + query + "%' OR ID_WEB_PEDIDO_SERVIDOR LIKE '" + query + "') ORDER BY ID_WEB_PEDIDO DESC;");
                                listaAdapterPedidoEnviado = new ListaAdapterPedidoEnviado(ListagemPedidoEnviado.this, listaPedido);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (listaPedido.size() > 1) {
                                            edtNumerPedidoEnviados.setText(listaPedido.size() + ": Pedidos Encontrados");
                                            edtNumerPedidoEnviados.setTextColor(Color.BLACK);
                                        } else if (listaPedido.size() == 1) {
                                            edtNumerPedidoEnviados.setText(listaPedido.size() + ": Pedido Encontrado");
                                            edtNumerPedidoEnviados.setTextColor(Color.BLACK);
                                        } else if (listaPedido.size() <= 0) {
                                            edtNumerPedidoEnviados.setText("Nenhum pedido encontrado");
                                            edtNumerPedidoEnviados.setTextColor(Color.RED);
                                        }
                                    }
                                });

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            lstPedidoEnviado.setVisibility(View.VISIBLE);
                                            lstPedidoEnviado.setAdapter(listaAdapterPedidoEnviado);
                                            listaAdapterPedidoEnviado.notifyDataSetChanged();
                                        } catch (NullPointerException | IllegalStateException e) {
                                            System.out.println("listaAdapterPedidoEnviado se nenhum dado!");
                                        }
                                    }
                                });
                            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lstPedidoEnviado.setVisibility(View.INVISIBLE);
                                        Toast.makeText(ListagemPedidoEnviado.this, "Sem resutados para '" + query + "'", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC;");
                                    listaAdapterPedidoEnviado = new ListaAdapterPedidoEnviado(ListagemPedidoEnviado.this, listaPedido);
                                    lstPedidoEnviado.setAdapter(listaAdapterPedidoEnviado);
                                    edtNumerPedidoEnviados.setText(listaPedido.size() + ": Pedidos Enviados");
                                    edtNumerPedidoEnviados.setTextColor(Color.BLACK);
                                    lstPedidoEnviado.setVisibility(View.VISIBLE);
                                    listaAdapterPedidoEnviado.notifyDataSetChanged();
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
            listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC;");

            listaAdapterPedidoEnviado = new ListaAdapterPedidoEnviado(ListagemPedidoEnviado.this, listaPedido);
            lstPedidoEnviado.setAdapter(listaAdapterPedidoEnviado);

            registerForContextMenu(lstPedidoEnviado);

        } catch (CursorIndexOutOfBoundsException e) {
            Toast.makeText(this, "Nenuhm pedido foi Enviado!", Toast.LENGTH_LONG).show();
        }
        edtNumerPedidoEnviados.setText(listaPedido.size() + ": Pedidos Enviados");
        super.onResume();
    }
}
