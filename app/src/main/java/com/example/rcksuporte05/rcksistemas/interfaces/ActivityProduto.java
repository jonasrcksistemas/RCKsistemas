package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterProdutos;
import com.example.rcksuporte05.rcksistemas.classes.Produto;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ActivityProduto extends AppCompatActivity {
    private MenuItem novo_produto;
    private SearchView busca_produto;
    private ListView lstProdutos;
    private Toolbar toolbar;
    private List<Produto> lista;
    private ListaAdapterProdutos adaptador;
    private DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);


        lstProdutos = (ListView) findViewById(R.id.lstProdutos);
        toolbar = (Toolbar) findViewById(R.id.tb_produto);
        toolbar.setTitle("Lista de Produtos");
        if (getIntent().getIntExtra("acao", 0) == 1) {
            try {
                lista = db.listaProduto("SELECT * FROM TBL_PRODUTO WHERE ATIVO = 'S' ORDER BY NOME_PRODUTO");
                adaptador = new ListaAdapterProdutos(ActivityProduto.this, lista);
                lstProdutos.setAdapter(adaptador);
            } catch (Exception e) {
                Toast.makeText(this, "Não há nenhum produto a ser exibido!", Toast.LENGTH_LONG).show();
            }
            lstProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProdutoPedidoActivity produtoPedidoActivity = new ProdutoPedidoActivity();
                    produtoPedidoActivity.pegaProduto(adaptador.getItem(position));
                    finish();
                }
            });
        } else {
            try {
                lista = db.listaProduto("SELECT * FROM TBL_PRODUTO ORDER BY ATIVO DESC, NOME_PRODUTO");
                adaptador = new ListaAdapterProdutos(ActivityProduto.this, lista);
                lstProdutos.setAdapter(adaptador);
            } catch (Exception e) {
                Toast.makeText(this, "Não há nenhum produto a ser exibido!", Toast.LENGTH_SHORT).show();
            }
            lstProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Float preco;
                    String mensagem;
                    try {
                        preco = Float.parseFloat(adaptador.getItem(position).getVenda_preco());
                        mensagem = "Produto: " + adaptador.getItem(position).getId_produto() + "\n        " + adaptador.getItem(position).getNome_produto() + "\nUnidade de Medida: " + adaptador.getItem(position).getDescricao() + "\nPreço de Venda: R$" + String.format("%.2f", preco);
                    } catch (NumberFormatException e) {
                        mensagem = "Produto: " + adaptador.getItem(position).getId_produto() + "\n        " + adaptador.getItem(position).getNome_produto() + "\nUnidade de Medida: " + adaptador.getItem(position).getDescricao();
                        System.out.println("Produto sem preco");
                    }
                    AlertDialog.Builder alert = new AlertDialog.Builder(ActivityProduto.this);
                    alert.setMessage(mensagem);
                    alert.setPositiveButton("OK", null);
                    alert.setTitle("Produto: ");
                    alert.show();
                }
            });
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_produto, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.buscaProduto);

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
                    adaptador = new ListaAdapterProdutos(ActivityProduto.this, lista);
                    adaptador.notifyDataSetChanged();
                    lstProdutos.setAdapter(adaptador);
                } else {
                    adaptador = new ListaAdapterProdutos(ActivityProduto.this, buscarProdutos(lista, query));
                    adaptador.notifyDataSetChanged();
                    lstProdutos.setAdapter(adaptador);
                }

                return false;
            }
        });

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Nome Produto");

        return true;
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

    public List<Produto> buscarProdutos(List<Produto> produtos, String query) {
        final String upperCaseQuery = query.toUpperCase();

        final List<Produto> lista = new ArrayList<>();
        for (Produto produto : produtos) {
            try {
                final String nomeCliente = produto.getNome_produto().toUpperCase();
                if (nomeCliente.contains(upperCaseQuery)) {
                    lista.add(produto);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }


    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
