package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.CampanhaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaCampanhaAdapter;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampanhaActivity extends AppCompatActivity implements ListaCampanhaAdapter.CampanhaAdapterListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycleCampanha)
    RecyclerView recycleCampanha;

    @BindView(R.id.edtTotalCampanhas)
    EditText edtTotalCampanhas;

    private ListaCampanhaAdapter adapter;
    private DBHelper db;
    private CampanhaComercialCabDAO campanhaComercialCabDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanha);
        ButterKnife.bind(this);

        db = new DBHelper(this);
        campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycleCampanha.setLayoutManager(layoutManager);

        if (getIntent().getIntExtra("pedido", 0) >= 1) {
            toolbar.setTitle("Campanhas disponiveis para este item");
            preencheLista(CampanhaHelper.getListaCampanha());
        } else {
            toolbar.setTitle("Campanhas");
            preencheLista(campanhaComercialCabDAO.listaCampanhaComercialCab());
        }

        recycleCampanha.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void preencheLista(List<CampanhaComercialCab> lista) {
        adapter = new ListaCampanhaAdapter(lista, this);
        recycleCampanha.setAdapter(adapter);
        edtTotalCampanhas.setText(lista.size() + " Campanhas");
    }

    @Override
    public void onClickListener(int position) {
        if (getIntent().getIntExtra("pedido", 0) >= 1) {
            CampanhaHelper.setCampanhaComercialCab(adapter.getItem(position));
            finish();
        } else {
            Intent intent = new Intent(CampanhaActivity.this, ItemCampanhaActivity.class);
            CampanhaHelper.setCampanhaComercialCab(adapter.getItem(position));
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (getIntent().getIntExtra("pedido", 0) >= 1) {
            getMenuInflater().inflate(R.menu.menu_campanha, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.limpaCampanha:
                CampanhaHelper.setCampanhaComercialCab(null);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
