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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProspectAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Prospect;

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

    @BindView(R.id.rgFiltaProspect)
    RadioGroup rgFiltaProspect;

    private ListaProspectAdapter listaProspectAdapter;
    private ActionMode actionMode;
    private List<Prospect> listaProspect;
    private DBHelper db;
    private ProgressDialog progress;
    private SearchView searchView;
    private MenuItem viraCliente;

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

        rgFiltaProspect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    if (actionMode != null)
                        actionMode.finish();

                    switch (checkedId) {
                        case R.id.filtraProspectAmbos:
                            listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE_SALVO);
                            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty()) {
                                preencheLista(buscaProspect(listaProspect, searchView.getQuery().toString()));
                            } else {
                                preencheLista(listaProspect);
                                edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                            }
                            break;
                        case R.id.filtraProspectPendentes:
                            listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE);
                            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty()) {
                                preencheLista(buscaProspect(listaProspect, searchView.getQuery().toString()));
                            } else {
                                preencheLista(listaProspect);
                                edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                            }
                            break;
                        case R.id.filtraProspectSalvos:
                            listaProspect = db.listaProspect(Prospect.PROSPECT_SALVO);
                            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty()) {
                                preencheLista(buscaProspect(listaProspect, searchView.getQuery().toString()));
                            } else {
                                preencheLista(listaProspect);
                                edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                            }
                            break;
                        default:
                            listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE_SALVO);
                            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty()) {
                                preencheLista(buscaProspect(listaProspect, searchView.getQuery().toString()));
                            } else {
                                preencheLista(listaProspect);
                                edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                            }
                            break;
                    }
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    recycleProspect.setVisibility(View.INVISIBLE);
                    edtTotalProspect.setText("0: Prospects Listados");
                }
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
                } catch (NullPointerException e) {
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
        recycleProspect.setVisibility(View.VISIBLE);
        recycleProspect.setAdapter(listaProspectAdapter);
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
        edtTotalProspect.setText(lista.size() + ": Prospects Encontrados");
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
                        if (listaProspectAdapter.getItem(position).getProspectSalvo().equals("S")) {
                            mode.getMenuInflater().inflate(R.menu.subir_vira_cliente, menu);
                            viraCliente = menu.findItem(R.id.viraCliente);
                        } else
                            mode.getMenuInflater().inflate(R.menu.menu_action_mode_produtos, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityListaProspect.this);
                                alert.setTitle("Atenção");
                                if (listaProspectAdapter.getItensSelecionadosCount() > 1)
                                    alert.setMessage("Deseja realmente excluir" +
                                            " esses " + listaProspectAdapter.getItensSelecionadosCount() + "itens?");
                                else
                                    alert.setMessage("Deseja realmente excluir" +
                                            " o prospect " + listaProspectAdapter.getItensSelecionados().get(0).getId_prospect() + " ?");
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
                                alert = new AlertDialog.Builder(ActivityListaProspect.this);
                                alert.setTitle("Atenção");
                                if (listaProspectAdapter.getItensSelecionadosCount() > 1)
                                    alert.setMessage("Tem certeza que deseja enviar esses " + listaProspectAdapter.getItensSelecionadosCount()
                                            + " prospects para o servidor?");
                                else
                                    alert.setMessage("Tem certeza que deseja enviar o prospect " + listaProspectAdapter.getItensSelecionados().get(0).getId_prospect()
                                            + " para o servidor?");
                                alert.setNegativeButton("NÃo", null);
                                alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progress = new ProgressDialog(ActivityListaProspect.this);
                                        progress.setMessage("Enviando Prospects");
                                        progress.setTitle("Aguarde");
                                        progress.show();
                                        List<Prospect> listaEnvio = listaProspectAdapter.getItensSelecionados();
                                        enviarProspects(listaEnvio);
                                    }
                                });
                                alert.show();
                                break;
                            case R.id.viraCliente:
                                if (listaProspectAdapter.getItensSelecionadosCount() == 1) {
                                    alert = new AlertDialog.Builder(ActivityListaProspect.this);
                                    alert.setTitle("Atenção");
                                    alert.setMessage("Deseja transformar " + listaProspectAdapter.getItensSelecionados().get(0).getNome_cadastro() + " em cliente?");
                                    alert.setNegativeButton("NÃO", null);
                                    alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Cliente cliente = new Cliente(listaProspectAdapter.getItensSelecionados().get(0));
                                            Intent intent = new Intent(ActivityListaProspect.this, CadastroClienteMain.class);
                                            intent.putExtra("prospect", Integer.parseInt(listaProspectAdapter.getItensSelecionados().get(0).getId_prospect()));
                                            ClienteHelper.setCliente(cliente);
                                            startActivity(intent);
                                            onDestroyActionMode(mode);
                                        }
                                    });
                                    alert.show();
                                }
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
            if (viraCliente != null) {
                if (listaProspectAdapter.getItensSelecionadosCount() > 1) {
                    viraCliente.setVisible(false);
                } else {
                    viraCliente.setVisible(true);
                }
            }
            actionMode.setTitle(String.valueOf(listaProspectAdapter.getItensSelecionadosCount()));
            actionMode.invalidate();
        }
    }

    public void enviarProspects(List<Prospect> prospectsEnvio) {
        Rotas apiRetrofit = Api.buildRetrofit();

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        Call<List<Prospect>> call = apiRetrofit.salvarProspect(cabecalho, prospectsEnvio);

        call.enqueue(new Callback<List<Prospect>>() {
            @Override
            public void onResponse(Call<List<Prospect>> call, Response<List<Prospect>> response) {
                switch (response.code()) {
                    case 200:
                        List<Prospect> prospectsRetorno = response.body();
                        if (prospectsRetorno != null && prospectsRetorno.size() > 0) {
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
                        break;
                    case 500:
                        progress.dismiss();
                        Toast.makeText(ActivityListaProspect.this, "Erro, não foi possivel enviar o prospect", Toast.LENGTH_SHORT).show();
                        listaProspectAdapter.clearSelections();
                        actionMode.finish();
                        actionMode = null;
                        break;
                    default:
                        progress.dismiss();
                        Toast.makeText(ActivityListaProspect.this, "Erro não catalogado: " + response.code(), Toast.LENGTH_SHORT).show();
                        listaProspectAdapter.clearSelections();
                        actionMode.finish();
                        actionMode = null;
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Prospect>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ActivityListaProspect.this, "Falhou, tente novamente, ou entre em contato com o suporte", Toast.LENGTH_SHORT).show();
                listaProspectAdapter.clearSelections();
                actionMode.finish();
                actionMode = null;
            }
        });

    }


    public void atualizaTela() {
        try {
            DBHelper db = new DBHelper(this);
            switch (rgFiltaProspect.getCheckedRadioButtonId()) {
                case R.id.filtraProspectAmbos:
                    listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE_SALVO);
                    edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                    preencheLista(listaProspect);
                    break;
                case R.id.filtraProspectPendentes:
                    listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE);
                    edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                    preencheLista(listaProspect);
                    break;
                case R.id.filtraProspectSalvos:
                    listaProspect = db.listaProspect(Prospect.PROSPECT_SALVO);
                    edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                    preencheLista(listaProspect);
                    break;
                default:
                    listaProspect = db.listaProspect(Prospect.PROSPECT_PENDENTE_SALVO);
                    edtTotalProspect.setText(listaProspect.size() + ": Prospects Listados");
                    preencheLista(listaProspect);
                    break;
            }

            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty()) {
                preencheLista(buscaProspect(listaProspect, searchView.getQuery().toString()));
            } else {
                preencheLista(listaProspect);
            }

        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            recycleProspect.setVisibility(View.INVISIBLE);
            edtTotalProspect.setText("0: Prospects Listados");
        }
    }
}
