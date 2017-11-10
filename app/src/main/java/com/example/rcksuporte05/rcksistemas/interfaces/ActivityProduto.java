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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.rcksuporte05.rcksistemas.Helper.ProdutoHelper;
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
    private EditText edtTotalProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        lstProdutos = (ListView) findViewById(R.id.lstProdutos);
        edtTotalProdutos = (EditText) findViewById(R.id.edtTotalProdutos);
        toolbar = (Toolbar) findViewById(R.id.tb_produto);
        toolbar.setTitle("Lista de Produtos");
        if (getIntent().getIntExtra("acao", 0) == 1) {
            try {
                lista = db.listaProduto("SELECT * FROM TBL_PRODUTO WHERE ATIVO = 'S' ORDER BY NOME_PRODUTO");

                adaptador = new ListaAdapterProdutos(ActivityProduto.this, lista);
                lstProdutos.setAdapter(adaptador);
            } catch (Exception e) {
                e.printStackTrace();
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
                e.printStackTrace();
            }
            lstProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ActivityProduto.this, ActivityDialogProdutoDetalhe.class);
                    ProdutoHelper.setProduto(adaptador.getItem(position));
                    startActivity(intent);

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
                try {
                    if (query.trim().equals("")) {
                        adaptador = new ListaAdapterProdutos(ActivityProduto.this, lista);
                        edtTotalProdutos.setText("Produtos listados :" + lista.size() + "   ");
                        edtTotalProdutos.setTextColor(Color.BLACK);
                    } else {
                        adaptador = new ListaAdapterProdutos(ActivityProduto.this, buscarProdutos(query));
                    }
                    adaptador.notifyDataSetChanged();
                    lstProdutos.setAdapter(adaptador);
                } catch (Exception e) {
                    e.printStackTrace();
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

    public List<Produto> buscarProdutos(String query) {
        List<Produto> lista = new ArrayList<>();

        if (!query.trim().equals("")) {
            try {
                lista = db.listaProduto("SELECT * FROM TBL_PRODUTO WHERE NOME_PRODUTO LIKE '%" + query + "%' ORDER BY ATIVO DESC, NOME_PRODUTO");
                edtTotalProdutos.setText("Produtos encontrados :" + lista.size() + "   ");
                edtTotalProdutos.setTextColor(Color.BLACK);
            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
                edtTotalProdutos.setText("Nenhum produto encontrado   ");
                edtTotalProdutos.setTextColor(Color.RED);
            }
        }
        System.gc();
        return lista;
    }


    @Override
    protected void onResume() {
        if (lista != null) {
            edtTotalProdutos.setText("Produtos listados :" + lista.size() + "   ");
            edtTotalProdutos.setTextColor(Color.BLACK);
        } else {
            edtTotalProdutos.setText("Não há produtos a serem exibidos!   ");
            edtTotalProdutos.setTextColor(Color.RED);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
