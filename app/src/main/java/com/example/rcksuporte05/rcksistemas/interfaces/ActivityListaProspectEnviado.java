package com.example.rcksuporte05.rcksistemas.interfaces;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.Helper.VisitaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProspectEnviadoAdapter;
import com.example.rcksuporte05.rcksistemas.classes.Prospect;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class ActivityListaProspectEnviado extends AppCompatActivity implements ListaProspectEnviadoAdapter.ProspectEnviadoListener{

    @BindView(R.id.recyclerProspectsEnviado)
    RecyclerView recyclerProspectsEnviado;

    @BindView(R.id.toolbarProspectEnviado)
    Toolbar toolbarProspectEnviado;

    ListaProspectEnviadoAdapter listaProspectEnviadoAdapter;
    private List<Prospect> listaProspect;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prospect_enviado);
        ButterKnife.bind(this);

        recyclerProspectsEnviado.setLayoutManager(new LinearLayoutManager(this));
        recyclerProspectsEnviado.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        try {
            DBHelper db = new DBHelper(this);
            listaProspect = db.listaProspect(Prospect.PROSPECT_ENVIADO);
            preencheLista(listaProspect);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            recyclerProspectsEnviado.setVisibility(View.INVISIBLE);
        }

        setSupportActionBar(toolbarProspectEnviado);
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

    @Override
    protected void onResume() {
        try {
            DBHelper db = new DBHelper(this);
            listaProspect = db.listaProspect(Prospect.PROSPECT_ENVIADO);
            preencheLista(listaProspect);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
//            edtTotalProspect.setText("0: Prospects Listados");
        }
        super.onResume();
    }

    private void preencheLista(List<Prospect> listaProspect) {
        listaProspectEnviadoAdapter = new ListaProspectEnviadoAdapter(listaProspect,this);
        recyclerProspectsEnviado.setAdapter(listaProspectEnviadoAdapter);
        recyclerProspectsEnviado.setVisibility(View.VISIBLE);
        listaProspectEnviadoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this,ActivityHistoricoVisitaProspect.class);
        VisitaHelper.setProspect(listaProspectEnviadoAdapter.getItem(position));
        startActivity(intent);
    }
}
