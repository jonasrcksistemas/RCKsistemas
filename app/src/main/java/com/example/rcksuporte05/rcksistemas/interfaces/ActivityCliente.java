package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterClientes;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido1;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido3;

import java.util.List;

public class ActivityCliente extends AppCompatActivity {
    //    private MenuItem novo_cliente;
    private ListView lstClientes;
    private Toolbar toolbar;
    private List<Cliente> listaAux;
    private List<Cliente> lista;
    private ListaAdapterClientes adaptadorPrincipal;
    private ListaAdapterClientes adaptador;
    private DBHelper db = new DBHelper(this);
    private Thread b = new Thread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        lstClientes = (ListView) findViewById(R.id.lstClientes);
        toolbar = (Toolbar) findViewById(R.id.tb_cliente);
        toolbar.setTitle("Lista de Clientes");
        if (getIntent().getIntExtra("acao", 0) == 1) {
            toolbar.setTitle("Pesquisa de Clientes");

            try {
                lista = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' ORDER BY ATIVO DESC, NOME_CADASTRO;");
                listaAux = lista;
                adaptadorPrincipal = new ListaAdapterClientes(ActivityCliente.this, lista);
                lstClientes.setAdapter(adaptadorPrincipal);
                System.gc();
            } catch (Exception e) {
                Toast.makeText(this, "Não há nenhum cliente a ser exibido!", Toast.LENGTH_LONG).show();
            }

            lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Pedido1 pedido1 = new Pedido1();
                    Pedido3 pedido3 = new Pedido3();
                    pedido1.pegaCliente(listaAux.get(position));
                    pedido3.pegaCliente(listaAux.get(position));
                    System.gc();
                    finish();
                }
            });
        } else {

            try {
                lista = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' ORDER BY ATIVO DESC, NOME_CADASTRO;");
                listaAux = lista;
                adaptadorPrincipal = new ListaAdapterClientes(ActivityCliente.this, lista);
                lstClientes.setAdapter(adaptadorPrincipal);
                System.gc();
            } catch (Exception e) {
                Toast.makeText(this, "Não há nenhum cliente a ser exibido!", Toast.LENGTH_LONG).show();
            }

            lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ActivityCliente.this, ContatoActivity.class);
                    ClienteHelper.setCliente(listaAux.get(position));
                    intent.putExtra("id_cliente", Integer.parseInt(listaAux.get(position).getId_cadastro()));
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
        menu.setHeaderTitle(listaAux.get(info.position).getNome_cadastro());

        MenuItem historicoFInanceiro = menu.add("Historico Financeiro");
        historicoFInanceiro.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ActivityCliente.this, HistoricoFinanceiroMain.class);
                intent.putExtra("idCliente", Integer.parseInt(listaAux.get(info.position).getId_cadastro()));
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
                final String consulta = query.replace("'", "");
                b = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!consulta.equals("") && consulta.length() >= 3) {
                            try {
                                listaAux = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE NOME_CADASTRO LIKE '%" + consulta + "%' OR NOME_FANTASIA LIKE '%" + consulta + "%' OR CPF_CNPJ LIKE '" + consulta + "%' OR TELEFONE_PRINCIPAL LIKE '%" + consulta + "%' ORDER BY ATIVO DESC, NOME_CADASTRO");
                                adaptador = new ListaAdapterClientes(ActivityCliente.this, listaAux);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            lstClientes.setVisibility(View.VISIBLE);
                                            lstClientes.setAdapter(adaptador);
                                            adaptador.notifyDataSetChanged();
                                        } catch (NullPointerException | IllegalStateException e) {
                                            System.out.println("adaptador se nenhum dado!");
                                        }
                                    }
                                });
                            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lstClientes.setVisibility(View.INVISIBLE);
                                        Toast.makeText(ActivityCliente.this, "Sem resutados para '" + consulta + "'", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listaAux = lista;
                                    lstClientes.setVisibility(View.VISIBLE);
                                    lstClientes.setAdapter(adaptadorPrincipal);
                                    adaptadorPrincipal.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });
                if (!b.isAlive()) {
                    b.start();
                }
                System.gc();
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

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
