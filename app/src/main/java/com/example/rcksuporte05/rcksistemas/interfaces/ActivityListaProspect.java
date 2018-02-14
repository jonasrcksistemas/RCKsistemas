package com.example.rcksuporte05.rcksistemas.interfaces;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProspectAdapter;
import com.example.rcksuporte05.rcksistemas.classes.Prospect;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityListaProspect extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycleProspect)
    RecyclerView recycleProspect;

    @BindView(R.id.edtTotalProspect)
    EditText edtTotalProspect;
    private ListaProspectAdapter listaProspectAdapter;

    @OnClick(R.id.btnAddProspect)
    public void novoProspect() {
        Prospect prospect = new Prospect();
        ProspectHelper.setProspect(prospect);
        Intent intent = new Intent(ActivityListaProspect.this, ActivityCadastroProspect.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prospect);
        ButterKnife.bind(this);

        recycleProspect.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

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
        return true;
    }

    public void preencheLista(final List<Prospect> lista) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycleProspect.setLayoutManager(layoutManager);
        listaProspectAdapter = new ListaProspectAdapter(lista, new ListaProspectAdapter.ProspectAdapterListener() {
            @Override
            public void onProspectRowClicked(int position) {
                ProspectHelper.setProspect(lista.get(position));
                Intent intent = new Intent(ActivityListaProspect.this, ActivityCadastroProspect.class);
                startActivity(intent);
            }
        });
        recycleProspect.setAdapter(listaProspectAdapter);
        listaProspectAdapter.notifyDataSetChanged();
        edtTotalProspect.setText(lista.size() + ": Prospects Listados");
    }

    @Override
    protected void onResume() {
        try {
            DBHelper db = new DBHelper(this);
            List<Prospect> listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE_SALVO);
            preencheLista(listaProspect);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            edtTotalProspect.setText("0: Prospects Listados");
        }
        super.onResume();
    }
}
