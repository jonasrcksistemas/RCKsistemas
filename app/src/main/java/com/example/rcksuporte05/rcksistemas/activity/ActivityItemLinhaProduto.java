package com.example.rcksuporte05.rcksistemas.activity;

import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProdutoAdpter;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityItemLinhaProduto extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleLinhaProdutos)
    RecyclerView recycleLinhaProdutos;
    @BindView(R.id.edtTotalProdutos)
    EditText edtTotalProdutos;
    private ListaProdutoAdpter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_linha_produto);
        ButterKnife.bind(this);

        try {
            toolbar.setTitle(getIntent().getStringExtra("nomelinha"));
            DBHelper db = new DBHelper(this);
            adapter = new ListaProdutoAdpter(db.listaProduto("SELECT PROD.* FROM TBL_PRODUTO PROD INNER JOIN TBL_PRODUTO_LINHA_COLECAO LINHA ON PROD.ID_LINHA_COLECAO = LINHA.ID_LINHA_COLECAO\n" +
                    "WHERE PROD.ID_LINHA_COLECAO = " + getIntent().getIntExtra("linha", 0) + ";"), null);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recycleLinhaProdutos.setLayoutManager(layoutManager);
            recycleLinhaProdutos.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
            recycleLinhaProdutos.setAdapter(adapter);
            edtTotalProdutos.setText(adapter.getItemCount() + " produtos nesta linha");
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(this, "Nenhum produto dessa linha esta disponivel para venda externa", Toast.LENGTH_LONG).show();
            finish();
        }

        setSupportActionBar(toolbar);
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
}
