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

import com.example.rcksuporte05.rcksistemas.Helper.ProdutoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProdutoAdpter;
import com.example.rcksuporte05.rcksistemas.adapters.RecyclerTouchListener;
import com.example.rcksuporte05.rcksistemas.classes.Produto;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityProduto extends AppCompatActivity {
    @BindView(R.id.listaProdutoRecycler)
    RecyclerView listaProdutoRecyclerView;
    private MenuItem novo_produto;
    private SearchView busca_produto;
    private Toolbar toolbar;
    private List<Produto> lista;
    private DBHelper db = new DBHelper(this);
    private EditText edtTotalProdutos;
    private ListaProdutoAdpter listaProdutoAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        ButterKnife.bind(this);

        edtTotalProdutos = (EditText) findViewById(R.id.edtTotalProdutos);
        toolbar = (Toolbar) findViewById(R.id.tb_produto);
        toolbar.setTitle("Lista de Produtos");
        if (getIntent().getIntExtra("acao", 0) == 1) {
            try {
                lista = db.listaProduto("SELECT * FROM TBL_PRODUTO WHERE ATIVO = 'S' ORDER BY NOME_PRODUTO");
                preecheRecyclerProduto(this, lista);

            } catch (Exception e) {
                e.printStackTrace();
            }

            listaProdutoRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, listaProdutoRecyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    ProdutoPedidoActivity produtoPedidoActivity = new ProdutoPedidoActivity();
                    Intent intent = new Intent(ActivityProduto.this, ProdutoPedidoActivity.class);
                    produtoPedidoActivity.pegaProduto(listaProdutoAdpter.getItem(position));
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        } else {
            try {
                lista = db.listaProduto("SELECT * FROM TBL_PRODUTO ORDER BY ATIVO DESC, NOME_PRODUTO");
                preecheRecyclerProduto(this, lista);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listaProdutoRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, listaProdutoRecyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(ActivityProduto.this, ActivityDialogProdutoDetalhe.class);
                    ProdutoHelper.setProduto(listaProdutoAdpter.getItem(position));
                    startActivity(intent);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));


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
                        listaProdutoAdpter = new ListaProdutoAdpter(lista);
                        edtTotalProdutos.setText("Produtos listados :" + lista.size() + "   ");
                        edtTotalProdutos.setTextColor(Color.BLACK);
                    } else {
                        listaProdutoAdpter = new ListaProdutoAdpter(buscarProdutos(query));
                    }
                    listaProdutoAdpter.notifyDataSetChanged();
                    listaProdutoRecyclerView.setAdapter(listaProdutoAdpter);
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

    public void preecheRecyclerProduto(Context context, List<Produto> produtos) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        listaProdutoRecyclerView.setLayoutManager(layoutManager);

        listaProdutoAdpter = new ListaProdutoAdpter(produtos);
        listaProdutoRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        listaProdutoRecyclerView.setAdapter(listaProdutoAdpter);

        listaProdutoAdpter.notifyDataSetChanged();


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
