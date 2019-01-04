package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.Helper.VisitaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.VisitaAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class ActivityHistoricoVisitaProspect extends AppCompatActivity implements VisitaAdapter.VisitaListener {
    @BindView(R.id.recycleHistoricoVisita)
    RecyclerView recycleHistoricoVisita;

    @BindView(R.id.toolbarVisita)
    Toolbar toolbarVisita;

    @BindView(R.id.edtTotalVisita)
    TextView edtTotalVisita;

    @BindView(R.id.imFisicaJuridica)
    ImageView imFisicaJuridica;

    @BindView(R.id.txtDescricaoAcao)
    TextView txtNomeProspectVisita;

    private DBHelper db;
    private List<VisitaProspect> visitas;
    private VisitaAdapter visitaAdapter;
    private ActionMode actionMode;
    private ProgressDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_visita);
        ButterKnife.bind(this);

        db = new DBHelper(this);

        Prospect prospect = VisitaHelper.getProspect();
        if (prospect.getPessoa_f_j() != null) {
            switch (prospect.getPessoa_f_j()) {
                case "F":
                    imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_fisica);
                    break;
                case "J":
                    imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_juridica);
                    break;
                default:
                    imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_duvida);
                    break;
            }
        } else
            imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_duvida);

        txtNomeProspectVisita.setText(prospect.getNome_fantasia());

        recycleHistoricoVisita.setLayoutManager(new LinearLayoutManager(this));
        recycleHistoricoVisita.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        setSupportActionBar(toolbarVisita);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @OnClick(R.id.btnAddVisita)
    public void abrirTela() {
        Intent intent = new Intent(this, ActivityVisita.class);
        VisitaHelper.setVisitaProspect(new VisitaProspect());
        startActivity(intent);
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
            visitas = db.listaVisitaPorProspect(VisitaHelper.getProspect());
            preencheLista();
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        super.onResume();
    }

    public void preencheLista() {
        visitaAdapter = new VisitaAdapter(visitas, this);
        recycleHistoricoVisita.setAdapter(visitaAdapter);
        visitaAdapter.notifyDataSetChanged();
        if (visitaAdapter.contaPendentes() > 0) {
            edtTotalVisita.setText("Visitas: " + visitaAdapter.getItemCount() + " Pendentes: " + visitaAdapter.contaPendentes());
        } else
            edtTotalVisita.setText("Visitas: " + visitaAdapter.getItemCount());
    }

    @Override
    public void onClick(int position) {
        if (visitaAdapter.getItensSelecionadosCount() > 0) {
            enableActionMode(position);
        } else {
            if (visitaAdapter.getItem(position).getIdVisitaServidor() != null) {
                Intent intent = new Intent(this, ActivityVisita.class);
                VisitaHelper.setVisitaProspect(visitaAdapter.getItem(position));
                intent.putExtra("acao", 1);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ActivityVisita.class);
                VisitaHelper.setVisitaProspect(visitaAdapter.getItem(position));
                intent.putExtra("acao", 2);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onLongClick(int position) {
        enableActionMode(position);
    }

    private void enableActionMode(final int position) {
        if (visitaAdapter.getItem(position).getIdVisitaServidor() != null) {
            Toast.makeText(ActivityHistoricoVisitaProspect.this, "Esta visita já foi enviada ao servidor!", Toast.LENGTH_LONG).show();
        } else {
            if (actionMode == null) {
                actionMode = startActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.subir_foto, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.uploadFoto:
                                progress = new ProgressDialog(ActivityHistoricoVisitaProspect.this);
                                progress.setMessage("Enviando Visitas");
                                progress.setTitle("Aguarde");
                                progress.show();
                                enviarVisita(visitaAdapter.getItensSelecionados());
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        actionMode.finish();
                        visitaAdapter.clearSelections();
                        actionMode = null;
                    }
                });
            }
            toggleSelection(position);
        }
    }

    public void toggleSelection(int position) {
        visitaAdapter.toggleSelection(position);
        if (visitaAdapter.getItensSelecionadosCount() == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(visitaAdapter.getItensSelecionadosCount()));
            actionMode.invalidate();
        }
    }

    public void enviarVisita(List<VisitaProspect> visitaEnvio) {
        Rotas apiRetrofit = Api.buildRetrofit();

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        for (VisitaProspect visitaProspect : visitaEnvio) {
            try {
                if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + visitaProspect.getIdVisita() + " AND ID_ENTIDADE = 11;") > 0) {
                    CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
                    List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoDAO.listaCadastroAnexoProspect(Integer.parseInt(visitaProspect.getIdVisita()));

                    for (CadastroAnexo cadastroAnexo : listaCadastroAnexo) {
                        if (cadastroAnexo.getPrincipal().equals("S"))
                            visitaProspect.setFotoPrincipalBase64(cadastroAnexo);
                        else
                            visitaProspect.setFotoSecundariaBase64(cadastroAnexo);
                    }
                }
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        Call<List<VisitaProspect>> call = apiRetrofit.salvarVisita(cabecalho, visitaEnvio);

        call.enqueue(new Callback<List<VisitaProspect>>() {
            @Override
            public void onResponse(Call<List<VisitaProspect>> call, Response<List<VisitaProspect>> response) {
                switch (response.code()) {
                    case 200:
                        List<VisitaProspect> listaVisita = response.body();
                        if (listaVisita != null && listaVisita.size() > 0) {
                            for (VisitaProspect visitaProspect : listaVisita) {
                                db.atualizaTBL_VISITA_PROSPECT(visitaProspect);
                            }
                        }
                        visitaAdapter.setLista(db.listaVisitaPorProspect(VisitaHelper.getProspect()));
                        Toast.makeText(ActivityHistoricoVisitaProspect.this, "Visitas enviadas com sucesso!", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(ActivityHistoricoVisitaProspect.this, "Houve um erro ao tentar enviar as visitas selecionadas!", Toast.LENGTH_LONG).show();
                        break;
                }
                progress.dismiss();
                actionMode.finish();
                visitaAdapter.notifyDataSetChanged();
                if (visitaAdapter.contaPendentes() > 0) {
                    edtTotalVisita.setText("Visitas: " + visitaAdapter.getItemCount() + " Pendentes: " + visitaAdapter.contaPendentes());
                } else
                    edtTotalVisita.setText("Visitas: " + visitaAdapter.getItemCount());
            }

            @Override
            public void onFailure(Call<List<VisitaProspect>> call, Throwable t) {
                Toast.makeText(ActivityHistoricoVisitaProspect.this, "Houve um erro ao tentar enviar as visitas selecionadas!", Toast.LENGTH_LONG).show();
                actionMode.finish();
                progress.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        VisitaHelper.limpaVisitaHelper();
        VisitaHelper.setProspect(null);
        System.gc();
        super.onDestroy();
    }
}
