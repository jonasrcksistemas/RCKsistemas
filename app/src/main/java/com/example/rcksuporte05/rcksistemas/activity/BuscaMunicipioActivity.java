package com.example.rcksuporte05.rcksistemas.activity;

import android.app.SearchManager;
import android.content.Context;
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
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.MunicipioAdapter;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuscaMunicipioActivity extends AppCompatActivity implements MunicipioAdapter.municipioListener {

    @BindView(R.id.listaMunicipio)
    RecyclerView listaMunicipio;
    @BindView(R.id.toolbarMunicipio)
    Toolbar toolbarMunicipio;
    private MunicipioAdapter adapter;
    private SearchView searchView;
    private String[] municipio;
    private String municipioSelecionado;
    private int[] listaUf = {R.array.AC,
            R.array.AL,
            R.array.AM,
            R.array.AP,
            R.array.BA,
            R.array.CE,
            R.array.DF,
            R.array.ES,
            R.array.EX,
            R.array.GO,
            R.array.MA,
            R.array.MG,
            R.array.MS,
            R.array.MT,
            R.array.PA,
            R.array.PB,
            R.array.PE,
            R.array.PI,
            R.array.PR,
            R.array.RJ,
            R.array.RN,
            R.array.RO,
            R.array.RR,
            R.array.RS,
            R.array.SC,
            R.array.SE,
            R.array.SP,
            R.array.TO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_municipio);
        ButterKnife.bind(this);

        toolbarMunicipio.setTitle("Selecione o Municipio");


        if (getIntent().getIntExtra("cliente", 0) >= 1) {
            municipio = getResources().getStringArray(listaUf[ClienteHelper.getPosicaoUf()]);
            if (ClienteHelper.getPosicaoMunicipio() > -1)
                municipioSelecionado = municipio[ClienteHelper.getPosicaoMunicipio()];
        } else if (getIntent().getIntExtra("prospect", 0) >= 1) {
            municipio = getResources().getStringArray(listaUf[ProspectHelper.getPosicaoUf()]);
            if (ProspectHelper.getPosicaoMunicipio() > -1)
                municipioSelecionado = municipio[ProspectHelper.getPosicaoMunicipio()];
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listaMunicipio.setLayoutManager(layoutManager);
        listaMunicipio.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        preencheLista(municipio);

        setSupportActionBar(toolbarMunicipio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(int position) {
        adapter.toggleSelection(position);
        adapter.notifyDataSetChanged();

        if (getIntent().getIntExtra("cliente", 0) >= 1) {
            if (ClienteHelper.getPosicaoMunicipio() > -1 && position != ClienteHelper.getPosicaoUf())
                ClienteHelper.setPosicaoMunicipio(0);

            for (int i = 0; municipio.length > i; i++) {
                if (municipio[i].equals(adapter.getItem(position))) {
                    ClienteHelper.setPosicaoMunicipio(i);
                    break;
                }
            }
            finish();
        } else if (getIntent().getIntExtra("prospect", 0) >= 1) {
            if (ProspectHelper.getPosicaoMunicipio() > -1 && position != ProspectHelper.getPosicaoUf())
                ProspectHelper.setPosicaoMunicipio(0);

            for (int i = 0; municipio.length > i; i++) {
                if (municipio[i].equals(adapter.getItem(position))) {
                    ProspectHelper.setPosicaoMunicipio(i);
                    break;
                }
            }
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_cliente, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
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
                try {
                    if (query.trim().equals("")) {
                        preencheLista(municipio);
                    } else {
                        preencheLista(buscaMunicipio(query));
                    }
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Municipio");
        return true;
    }

    private void preencheLista(String[] municipio) {
        adapter = new MunicipioAdapter(municipio, this);
        listaMunicipio.setAdapter(adapter);
        adapter.selecionado(municipioSelecionado);
        adapter.notifyDataSetChanged();
    }

    private String[] buscaMunicipio(String query) {
        query = query.toUpperCase();

        List<String> busca = new ArrayList<>();
        for (int i = 0; municipio.length > i; i++) {
            if (municipio[i].toUpperCase().contains(query)) {
                busca.add(municipio[i]);
            }
        }
        final String[] resultado = new String[busca.size()];
        for (int i = 0; busca.size() > i; i++) {
            resultado[i] = busca.get(i);
        }
        return resultado;
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
