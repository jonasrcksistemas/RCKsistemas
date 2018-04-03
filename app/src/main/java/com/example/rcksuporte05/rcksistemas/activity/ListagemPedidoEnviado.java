package com.example.rcksuporte05.rcksistemas.activity;

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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoAdapter;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListagemPedidoEnviado extends AppCompatActivity implements ListaPedidoAdapter.PedidoAdapterListener {

    @BindView(R.id.listaPedidoEnviados)
    RecyclerView recyclerViewPedidos;
    @BindView(R.id.edtNumeroPedidoEnviado)
    TextView edtNumerPedidoEnviados;

    private List<WebPedido> listaPedido;
    private DBHelper db = new DBHelper(this);
    private Usuario usuario;
    private ListaPedidoAdapter listaPedidoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pedido_enviado);
        ButterKnife.bind(this);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPedidoEnviado);
        toolbar.setTitle("Pedidos Enviados");
        setSupportActionBar(toolbar);
        usuario = db.listaUsuario("SELECT * FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consulta("SELECT * FROM TBL_LOGIN;", "LOGIN") + "';").get(0);

        try {
            listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC;");

            preencheListaRecycler(listaPedido);

        } catch (CursorIndexOutOfBoundsException e) {
            Toast.makeText(this, "Nenhum pedido foi Enviado!", Toast.LENGTH_LONG).show();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewPedidos.setLayoutManager(layoutManager);
        recyclerViewPedidos.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));


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
                if (query.trim().equals("")) {
                    preencheListaRecycler(listaPedido);
                    edtNumerPedidoEnviados.setText(listaPedido.size() + ": Pedidos Enviados");
                    edtNumerPedidoEnviados.setTextColor(Color.BLACK);
                } else {
                    List<WebPedido> listaBusca = buscaPedidoEnviado(listaPedido, query);
                    preencheListaRecycler(listaBusca);
                    if (listaBusca.size() > 0) {
                        edtNumerPedidoEnviados.setText(listaBusca.size() + ": Pedidos Encontrados");
                        edtNumerPedidoEnviados.setTextColor(Color.BLACK);
                    } else {
                        edtNumerPedidoEnviados.setText("Nenum pedido encontrado");
                        edtNumerPedidoEnviados.setTextColor(Color.RED);
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

    public List<WebPedido> buscaPedidoEnviado(List<WebPedido> webPedidos, String query) {
        final String upperCaseQuery = query.toUpperCase();

        final List<WebPedido> lista = new ArrayList<>();
        for (WebPedido webPedido : webPedidos) {
            try {
                final String nomeCliente = webPedido.getNome_extenso().toUpperCase();
                final String numeroPedido = webPedido.getId_web_pedido_servidor().toUpperCase();

                if (nomeCliente.contains(upperCaseQuery) || numeroPedido.equals(upperCaseQuery)) {
                    lista.add(webPedido);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    public void preencheListaRecycler(List<WebPedido> listaPedidos) {

        listaPedidoAdapter = new ListaPedidoAdapter(listaPedidos, this);

        recyclerViewPedidos.setAdapter(listaPedidoAdapter);

        listaPedidoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        try {
            listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC;");

            preencheListaRecycler(listaPedido);

        } catch (CursorIndexOutOfBoundsException e) {
            Toast.makeText(this, "Nenhum pedido foi Enviado!", Toast.LENGTH_LONG).show();
        }
        edtNumerPedidoEnviados.setText(listaPedido.size() + ": Pedidos Enviados");
        super.onResume();
    }



    @Override
    public void onPedidoRowClicked(int position) {
        Intent intent = new Intent(ListagemPedidoEnviado.this, ActivityPedidoMain.class);
        PedidoHelper.setIdPedido(Integer.parseInt(listaPedidoAdapter.getItem(position).getId_web_pedido()));
        intent.putExtra("vizualizacao", 1);
        startActivity(intent);

    }

    @Override
    public void onRowLongClicked(int position) {

    }


}
