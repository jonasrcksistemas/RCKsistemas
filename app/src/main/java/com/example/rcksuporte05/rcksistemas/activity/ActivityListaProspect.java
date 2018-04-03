package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProspectAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityListaProspect extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycleProspect)
    RecyclerView recycleProspect;

    @BindView(R.id.edtTotalProspect)
    EditText edtTotalProspect;

    @BindView(R.id.spFiltaProspect)
    Spinner spFiltraProspect;

    private String[] prospectLista = {"Ambos", "Pendentes", "Salvos"};
    private ListaProspectAdapter listaProspectAdapter;
    private ActionMode actionMode;
    private List<Prospect> listaProspect;
    private DBHelper db;
    private ProgressDialog progress;

    @OnClick(R.id.btnAddProspect)
    public void novoProspect() {
        Prospect prospect = new Prospect();
        prospect.setProspectSalvo("N");
        ProspectHelper.setProspect(prospect);
        Intent intent = new Intent(ActivityListaProspect.this, ActivityCadastroProspect.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prospect);
        ButterKnife.bind(this);
        db = new DBHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner, prospectLista);
        adapter.setDropDownViewResource(R.layout.drop_down_spinner);
        spFiltraProspect.setAdapter(adapter);

        spFiltraProspect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    DBHelper db = new DBHelper(ActivityListaProspect.this);
                    listaProspect = db.listaProspect(spFiltraProspect.getSelectedItemPosition());
                    preencheLista(listaProspect);
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    edtTotalProspect.setText("0: Prospects Listados");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recycleProspect.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recycleProspect.setLayoutManager(new LinearLayoutManager(this));


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                try{
                    if (query.trim().equals("")) {
                        if (listaProspect.size() > 0)
                            preencheLista(listaProspect);
                    } else {
                        preencheLista(buscaProspect(listaProspect, query));
                    }
                }catch (NullPointerException e){
                    Toast.makeText(ActivityListaProspect.this, "Não existem Prospects para consulta", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        searchView.setQueryHint("Nome cadastro/nome fantasia/CPF-CNPJ/codigo prospect");
        return true;
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
        listaProspectAdapter = new ListaProspectAdapter(lista, new ListaProspectAdapter.ProspectAdapterListener() {
            @Override
            public void onProspectRowClicked(int position) {
                if (listaProspectAdapter.getItensSelecionadosCount() > 0) {
                    enableActionMode(position);
                } else {
                    ProspectHelper.setProspect(lista.get(position));
                    Intent intent = new Intent(ActivityListaProspect.this, ActivityCadastroProspect.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onProspectLongClicked(int position) {
                enableActionMode(position);
            }
        });
        recycleProspect.setAdapter(listaProspectAdapter);
        edtTotalProspect.setText(lista.size() + ": Prospects Listados");
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

    @Override
    public void onResume() {
        atualizaTela();
        super.onResume();
    }

    private void enableActionMode(final int position) {
        if (listaProspectAdapter.getItensSelecionadosCount() == 0 || listaProspectAdapter.getItensSelecionados().get(listaProspectAdapter.getItensSelecionadosCount() - 1).getProspectSalvo().equals(listaProspectAdapter.getItem(position).getProspectSalvo())) {
            if (actionMode == null) {
                actionMode = startActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        if (listaProspectAdapter.getItem(position).getProspectSalvo().equals("S"))
                            mode.getMenuInflater().inflate(R.menu.subir_foto, menu);
                        else
                            mode.getMenuInflater().inflate(R.menu.menu_action_mode_produtos, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityListaProspect.this);
                                alert.setTitle("Atenção!");
                                alert.setMessage("Deseja realmente excluir" +
                                        " esses " + listaProspectAdapter.getItensSelecionadosCount() + "itens?");
                                alert.setNegativeButton("NÃO", null);
                                alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DBHelper db = new DBHelper(ActivityListaProspect.this);
                                        for (Prospect prospect : listaProspectAdapter.getItensSelecionados()) {
                                            listaProspectAdapter.remove(prospect);
                                            db.excluiProspect(prospect);
                                        }
                                        listaProspectAdapter.notifyDataSetChanged();
                                        actionMode.finish();
                                        listaProspectAdapter.clearSelections();
                                        actionMode = null;
                                    }
                                });
                                alert.show();
                                break;
                            case R.id.uploadFoto:
                                progress = new ProgressDialog(ActivityListaProspect.this);
                                progress.setMessage("Enviando Prospects");
                                progress.setTitle("Aguarde");
                                progress.show();
                                List<Prospect> listaEnvio = listaProspectAdapter.getItensSelecionados();
                                enviarProspects(listaEnvio);
                                break;
                        }
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        actionMode.finish();
                        listaProspectAdapter.clearSelections();
                        actionMode = null;
                    }
                });
            }
            toggleSelection(position);
        } else {
            Toast.makeText(this, "O item " + listaProspectAdapter.getItem(position).getId_prospect() + " da lista não faz parte do grupo de seleção ativado", Toast.LENGTH_SHORT).show();
        }
    }

    public void toggleSelection(int position) {
        listaProspectAdapter.toggleSelection(position);
        if (listaProspectAdapter.getItensSelecionadosCount() == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(listaProspectAdapter.getItensSelecionadosCount()));
            actionMode.invalidate();
        }
    }

    public void enviarProspects(List<Prospect> prospectsEnvio){
        Rotas apiRetrofit = Api.buildRetrofit();

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        Call<List<Prospect>> call = apiRetrofit.salvarProspect(cabecalho,prospectsEnvio);

        call.enqueue(new Callback<List<Prospect>>() {
            @Override
            public void onResponse(Call<List<Prospect>> call, Response<List<Prospect>> response) {
                List<Prospect> prospectsRetorno = response.body();
                if(prospectsRetorno != null && prospectsRetorno.size() > 0){
                    for (Prospect prospect : prospectsRetorno) {
                        db.atualizarTBL_PROSPECT(prospect);
                    }
                    listaProspectAdapter.clearSelections();
                    actionMode.finish();
                    actionMode = null;
                    progress.dismiss();
                    Toast.makeText(ActivityListaProspect.this, "Enviado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<Prospect>> call, Throwable t) {
                Toast.makeText(ActivityListaProspect.this, "Falhou tente novamente, ou entre em contato com o suporte", Toast.LENGTH_SHORT).show();
                listaProspectAdapter.clearSelections();
                progress.dismiss();
            }
        });

    }


    public void atualizaTela(){
        try {
            DBHelper db = new DBHelper(this);
            listaProspect = db.listaProspect(spFiltraProspect.getSelectedItemPosition());
            preencheLista(listaProspect);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            edtTotalProspect.setText("0: Prospects Listados");
        }
    }
}
