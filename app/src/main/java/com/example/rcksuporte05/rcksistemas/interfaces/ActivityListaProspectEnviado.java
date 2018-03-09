package com.example.rcksuporte05.rcksistemas.interfaces;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.VisitaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProspectEnviadoAdapter;
import com.example.rcksuporte05.rcksistemas.classes.Prospect;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.ArrayList;
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

    @BindView(R.id.edtTotalProspectEnviado)
    EditText edtTotalProspectEnviado;

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
            edtTotalProspectEnviado.setText(0+" : Prospects Enviados");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_cliente, menu);

        SearchView searchView;

        final MenuItem item = menu.findItem(R.id.buscaCliente);

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
            public boolean onQueryTextChange(String query) {
                try {
                    if (query.trim().equals("")) {
                        if (listaProspect.size() > 0)
                            preencheLista(listaProspect);
                    } else {
                        preencheLista(buscaProspect(listaProspect, query));
                    }
                }catch (NullPointerException e){
                    Toast.makeText(ActivityListaProspectEnviado.this, "Nem um Prospect vinculado ao vendedor. Sincronizar pode resolver", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        searchView.setQueryHint("Nome cadastro/nome fantasia/CPF-CNPJ/codigo prospect");
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
        }
        super.onResume();
    }

    private void preencheLista(List<Prospect> listaProspect) {
        listaProspectEnviadoAdapter = new ListaProspectEnviadoAdapter(listaProspect,this);
        recyclerProspectsEnviado.setAdapter(listaProspectEnviadoAdapter);
        recyclerProspectsEnviado.setVisibility(View.VISIBLE);
        listaProspectEnviadoAdapter.notifyDataSetChanged();
        if(listaProspectEnviadoAdapter.getItemCount() > 0){
            edtTotalProspectEnviado.setText(listaProspectEnviadoAdapter.getItemCount()+" : Prospects Enviados");
        }else
            edtTotalProspectEnviado.setText("Nem um Prospect Sincronizado");

    }

    @Override
    public void onClick(int position) {
        VisitaHelper.setProspect(listaProspectEnviadoAdapter.getItem(position));
        Intent intent = new Intent(this,ActivityHistoricoVisitaProspect.class);
        startActivity(intent);
    }

    public List<Prospect> buscaProspect(List<Prospect> listaProspect, String query) {
        final String upperCaseQuery = query.toUpperCase();
        final List<Prospect> lista = new ArrayList<>();
        for (Prospect prospect : listaProspect) {
            boolean entrou = false;

            try {
                final String idProspect = prospect.getId_prospect().toUpperCase();
                if (idProspect.equals(upperCaseQuery) && !entrou) {
                    lista.add(prospect);
                    entrou = true;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                final String nomeCadastro = prospect.getNome_cadastro().toUpperCase();
                if (nomeCadastro.contains(upperCaseQuery) && !entrou) {
                    lista.add(prospect);
                    entrou = true;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                final String nomeFantasia = prospect.getNome_fantasia().toUpperCase();
                if (nomeFantasia.contains(upperCaseQuery) && !entrou) {
                    lista.add(prospect);
                    entrou = true;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                final String cpfCnpj = prospect.getCpf_cnpj().toUpperCase();
                if (cpfCnpj.contains(upperCaseQuery) && !entrou) {
                    lista.add(prospect);
                    entrou = true;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
}
