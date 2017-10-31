package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterClientes;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido1;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido3;

import java.util.ArrayList;
import java.util.List;

public class ActivityCliente extends AppCompatActivity {
    //    private MenuItem novo_cliente;
    private ListView lstClientes;
    private Toolbar toolbar;
    private List<Cliente> lista;
    private ListaAdapterClientes adaptador;
    private EditText edtTotalClientes;
    private DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        lstClientes = (ListView) findViewById(R.id.lstClientes);
        edtTotalClientes = (EditText) findViewById(R.id.edtTotalClientes);
        toolbar = (Toolbar) findViewById(R.id.tb_cliente);
        toolbar.setTitle("Lista de Clientes");
        if (getIntent().getIntExtra("acao", 0) == 1) {
            toolbar.setTitle("Pesquisa de Clientes");

            try {
                lista = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' ORDER BY ATIVO DESC, NOME_CADASTRO;");
                adaptador = new ListaAdapterClientes(ActivityCliente.this, lista);
                lstClientes.setAdapter(adaptador);
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }

            lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Pedido1 pedido1 = new Pedido1();
                    Pedido3 pedido3 = new Pedido3();
                    pedido1.pegaCliente(adaptador.getItem(position));
                    pedido3.pegaCliente(adaptador.getItem(position));
                    System.gc();
                    finish();
                }
            });
        } else {

            try {
                lista = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' ORDER BY ATIVO DESC, NOME_CADASTRO;");
                adaptador = new ListaAdapterClientes(ActivityCliente.this, lista);
                lstClientes.setAdapter(adaptador);
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }

            lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ActivityCliente.this, ContatoActivity.class);
                    ClienteHelper.setCliente(adaptador.getItem(position));
                    intent.putExtra("id_cliente", Integer.parseInt(adaptador.getItem(position).getId_cadastro()));
                    System.gc();
                    startActivity(intent);
                }
            });
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerForContextMenu(lstClientes);

        System.gc();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(adaptador.getItem(info.position).getNome_cadastro());

        MenuItem historicoFInanceiro = menu.add("Historico Financeiro");
        historicoFInanceiro.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ActivityCliente.this, HistoricoFinanceiroMain.class);
                intent.putExtra("idCliente", Integer.parseInt(adaptador.getItem(info.position).getId_cadastro()));
                ActivityCliente.this.startActivity(intent);

                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
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
                        adaptador = new ListaAdapterClientes(ActivityCliente.this, lista);
                        edtTotalClientes.setText("Clientes listados: " + lista.size() + "   ");
                        edtTotalClientes.setTextColor(Color.BLACK);
                    } else {
                        adaptador = new ListaAdapterClientes(ActivityCliente.this, buscaClientes(lista, query));
                    }
                    lstClientes.setVisibility(View.VISIBLE);
                    lstClientes.setAdapter(adaptador);
                    adaptador.notifyDataSetChanged();
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


    public List<Cliente> buscaClientes(List<Cliente> clientes, String query) {
        final String upperCaseQuery = query.toUpperCase();

        final List<Cliente> lista = new ArrayList<>();
        for (Cliente cliente : clientes) {
            try {
                final String nomeCliente = cliente.getNome_cadastro().toUpperCase();
                final String nomeFantasia = cliente.getNome_fantasia().toUpperCase();

                if (nomeCliente.contains(upperCaseQuery) || nomeFantasia.contains(upperCaseQuery)) {
                    lista.add(cliente);
                }
                if (lista.size() > 0) {
                    edtTotalClientes.setText("Clientes encontrados: " + lista.size() + "   ");
                    edtTotalClientes.setTextColor(Color.BLACK);
                } else {
                    edtTotalClientes.setText("Nenhum cliente encontrado!   ");
                    edtTotalClientes.setTextColor(Color.RED);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return lista;
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
