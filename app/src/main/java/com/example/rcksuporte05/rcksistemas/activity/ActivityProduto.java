package com.example.rcksuporte05.rcksistemas.activity;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProdutoAdpter;
import com.example.rcksuporte05.rcksistemas.adapters.RecyclerTouchListener;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityProduto extends AppCompatActivity {

    @BindView(R.id.listaProdutoRecycler)
    RecyclerView listaProdutoRecyclerView;

    @BindView(R.id.tb_produto)
    Toolbar toolbar;

    @BindView(R.id.edtTotalProdutos)
    TextView edtTotalProdutos;

    @BindView(R.id.edtDataSincronia)
    TextView edtDataSincronia;

    private MenuItem novo_produto;
    private SearchView busca_produto;
    private List<Produto> lista;
    private DBHelper db = new DBHelper(this);
    private ListaProdutoAdpter listaProdutoAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        ButterKnife.bind(this);

        toolbar.setTitle("Lista de Produtos");

        try {
            edtDataSincronia.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse(db.consulta("SELECT DATA_SINCRONIA_PRODUTO FROM TBL_LOGIN", "DATA_SINCRONIA_PRODUTO"))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listaProdutoRecyclerView.setLayoutManager(layoutManager);
        listaProdutoRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        try {
            lista = db.listaProduto("SELECT * FROM TBL_PRODUTO WHERE ATIVO = 'S' ORDER BY NOME_PRODUTO");
            preecheRecyclerProduto(this, lista);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getIntent().getIntExtra("acao", 0) == 1) {

            listaProdutoRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, listaProdutoRecyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(ActivityProduto.this, ProdutoPedidoActivity.class);
                    PedidoHelper.setProduto(listaProdutoAdpter.getItem(position));
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        } else if (getIntent().getIntExtra("acao", 0) == 2) {
            listaProdutoRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, listaProdutoRecyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    PedidoHelper.setProduto(listaProdutoAdpter.getItem(position));
                    finish();
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
                        edtTotalProdutos.setText(lista.size() + " Produtos listados");
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
                lista = db.listaProduto("SELECT * FROM TBL_PRODUTO WHERE NOME_PRODUTO LIKE '%" + query + "%' OR ID_PRODUTO LIKE '%" + query + "%' OR CODIGO_EM_BARRAS LIKE '%" + query + "%' ORDER BY ATIVO DESC, NOME_PRODUTO;");
                if (lista.size() > 1)
                    edtTotalProdutos.setText(lista.size() + " Produtos encontrados");
                else
                    edtTotalProdutos.setText(lista.size() + " Produto encontrado");
                edtTotalProdutos.setTextColor(Color.BLACK);
            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
                edtTotalProdutos.setText("Nenhum produto encontrado");
                edtTotalProdutos.setTextColor(Color.RED);
            }
        }
        System.gc();
        return lista;
    }

    public void preecheRecyclerProduto(Context context, List<Produto> produtos) {
        listaProdutoAdpter = new ListaProdutoAdpter(produtos);
        listaProdutoRecyclerView.setAdapter(listaProdutoAdpter);

        listaProdutoAdpter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        if (lista != null) {
            edtTotalProdutos.setText(lista.size() + " Produtos listados");
            edtTotalProdutos.setTextColor(Color.BLACK);
        } else {
            edtTotalProdutos.setText("Não há produtos a serem exibidos!");
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
