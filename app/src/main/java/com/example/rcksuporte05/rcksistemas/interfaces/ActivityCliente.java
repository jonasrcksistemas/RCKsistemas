package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaClienteAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.RecyclerTouchListener;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityCliente extends AppCompatActivity {
    @BindView(R.id.listaRecycler)
    RecyclerView listaDeClientes;
    //    private MenuItem novo_cliente;
    // private ListView lstClientes;
    private Toolbar toolbar;
    private List<Cliente> lista;
    private EditText edtTotalClientes;
    private DBHelper db = new DBHelper(this);
    private ListaClienteAdapter listaClienteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        ButterKnife.bind(this);


        edtTotalClientes = (EditText) findViewById(R.id.edtTotalClientes);
        toolbar = (Toolbar) findViewById(R.id.tb_cliente);
        toolbar.setTitle("Lista de Clientes");
        try {
            lista = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' ORDER BY ATIVO DESC, NOME_CADASTRO;");
            preencheLista(lista);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getIntent().getIntExtra("acao", 0) == 1) {
            toolbar.setTitle("Pesquisa de Clientes");


            listaDeClientes.addOnItemTouchListener(new RecyclerTouchListener(this, listaDeClientes, new RecyclerTouchListener.ClickListener() {

                @Override
                public void onClick(View view, int position) {
                    ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                    Pedido2 pedido2 = new Pedido2();
                    activityPedidoMain.pegaCliente(listaClienteAdapter.getItem(position));
                    pedido2.pegaCliente(listaClienteAdapter.getItem(position));
                    System.gc();
                    finish();
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        } else {

            listaDeClientes.addOnItemTouchListener(new RecyclerTouchListener(this, listaDeClientes, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(ActivityCliente.this, ContatoActivity.class);
                    ClienteHelper.setCliente(listaClienteAdapter.getItem(position));
                    intent.putExtra("id_cliente", Integer.parseInt(listaClienteAdapter.getItem(position).getId_cadastro()));
                    System.gc();
                    startActivity(intent);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        registerForContextMenu(listaDeClientes);

        System.gc();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_cliente, menu);

//        novo_cliente = menu.findItem(R.id.menu_item_novo_cliente);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.buscaCliente);

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
                try {
                    if (query.trim().equals("")) {
                        listaClienteAdapter = new ListaClienteAdapter(lista);
                        edtTotalClientes.setText("Clientes listados: " + lista.size() + "   ");
                        edtTotalClientes.setTextColor(Color.BLACK);
                    } else {
                        listaClienteAdapter = new ListaClienteAdapter(buscaClientes(query));
                    }
                    listaDeClientes.setAdapter(listaClienteAdapter);
                    listaClienteAdapter.notifyDataSetChanged();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Razão Social / Nome Fantasia / CNPJ-CPF / Telefone");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.menu_item_novo_cliente:
                Intent intent = new Intent(ActivityCliente.this, CadastroClienteMain.class);
                bundle = new Bundle();
                bundle.putString("cliente", "0");
                intent.putExtras(bundle);
                startActivity(intent);
                break;*/
            case android.R.id.home:
                System.gc();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public List<Cliente> buscaClientes(final String query) {
        List<Cliente> lista = new ArrayList<>();

        if (!query.trim().equals("")) {
            try {
                lista = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE ATIVO = 'S' AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%') ORDER BY ATIVO DESC, NOME_CADASTRO");
                edtTotalClientes.setText("Clientes encontrados: " + lista.size() + "   ");
                edtTotalClientes.setTextColor(Color.BLACK);
                return lista;
            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
                edtTotalClientes.setText("Nenhum cliente encontrado!   ");
                edtTotalClientes.setTextColor(Color.RED);
            }
        }
        System.gc();
        return lista;
    }

    public void preencheLista(List<Cliente> clientes) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listaDeClientes.setLayoutManager(layoutManager);
        listaClienteAdapter = new ListaClienteAdapter(clientes);
        listaDeClientes.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        listaDeClientes.setAdapter(listaClienteAdapter);
        listaClienteAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        if (lista != null) {
            edtTotalClientes.setText("Clientes listados: " + lista.size() + "   ");
            edtTotalClientes.setTextColor(Color.BLACK);
        } else {
            edtTotalClientes.setText("Não há clientes a serem exibidos!   ");
            edtTotalClientes.setTextColor(Color.RED);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
