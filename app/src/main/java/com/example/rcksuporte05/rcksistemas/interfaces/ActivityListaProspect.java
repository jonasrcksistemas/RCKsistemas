package com.example.rcksuporte05.rcksistemas.interfaces;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @BindView(R.id.spFiltaProspect)
    Spinner spFiltraProspect;

    private String[] prospectLista = {"Ambos", "Pendentes", "Salvos"};
    private ListaProspectAdapter listaProspectAdapter;
    private ActionMode actionMode;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner, prospectLista);
        adapter.setDropDownViewResource(R.layout.drop_down_spinner);
        spFiltraProspect.setAdapter(adapter);

        spFiltraProspect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    DBHelper db = new DBHelper(ActivityListaProspect.this);
                    List<Prospect> listaProspect = db.listaProspect(spFiltraProspect.getSelectedItemPosition());
                    preencheLista(listaProspect);
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    recycleProspect.setVisibility(View.INVISIBLE);
                    edtTotalProspect.setText("0: Prospects Listados");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        recycleProspect.setVisibility(View.VISIBLE);
        edtTotalProspect.setText(lista.size() + ": Prospects Listados");
    }

    @Override
    protected void onResume() {
        try {
            DBHelper db = new DBHelper(this);
            List<Prospect> listaProspect = db.listaProspect(spFiltraProspect.getSelectedItemPosition());
            preencheLista(listaProspect);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            edtTotalProspect.setText("0: Prospects Listados");
        }
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
                                Toast.makeText(ActivityListaProspect.this, "Enviando prospects ao servidor", Toast.LENGTH_SHORT).show();
                                actionMode.finish();
                                listaProspectAdapter.clearSelections();
                                actionMode = null;
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
}
