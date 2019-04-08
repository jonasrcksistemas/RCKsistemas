package com.example.rcksuporte05.rcksistemas.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.CadastroAnexoBO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaClienteAdapter;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
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

public class ActivityCliente extends AppCompatActivity {
    @BindView(R.id.listaRecycler)
    RecyclerView listaDeClientes;

    @BindView(R.id.edtTotalClientes)
    EditText edtTotalClientes;

    @BindView(R.id.tb_cliente)
    Toolbar toolbar;

    @BindView(R.id.rgFiltraCliente)
    RadioGroup rgFiltraCliente;

    @BindView(R.id.filtraTodosClientes)
    RadioButton filtraTodosClientes;

    @BindView(R.id.filtraClientesEfetivados)
    RadioButton filtraClientesEfetivados;

    @BindView(R.id.filtraClientesNaoEfetivados)
    RadioButton filtraClientesNaoEfetivados;

    @BindView(R.id.btnInserirCliente)
    Button btnInserirCliente;

    private DBHelper db = new DBHelper(this);
    private ListaClienteAdapter listaClienteAdapter;
    private SearchView searchView;
    private ActionMode actionMode;

    @OnClick(R.id.btnInserirCliente)
    public void inserirCliente() {
        Cliente cliente = new Cliente();
        ClienteHelper.setCliente(cliente);

        if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO WHERE FINALIZADO = 'N'") >= 1) {
            android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this);
            alert.setTitle("Atenção!");
            alert.setMessage("Foi detectado um cadastro de cliente em andamento, deseja continua-lo?");
            alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.alterar("DELETE FROM TBL_CADASTRO WHERE FINALIZADO = 'N'");
                    Intent intent = new Intent(ActivityCliente.this, ActivityCpfCnpjCliente.class);
                    intent.putExtra("novo", 1);
                    startActivity(intent);
                }
            });
            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        ClienteHelper.setCliente(db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO = 'N'").get(0));

                        if (ClienteHelper.getCliente().getId_cadastro() > 0) {
                            if (db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + ClienteHelper.getCliente().getId_cadastro() + " AND EXCLUIDO = 'N';") > 0) {
                                final ProgressDialog progress = new ProgressDialog(ActivityCliente.this);
                                progress.setTitle("Aguarde");
                                progress.setMessage("Carregando anexos do cliente");
                                progress.setCancelable(false);
                                progress.show();
                                final CadastroAnexoBO cadastroAnexoBO = new CadastroAnexoBO();
                                Thread a = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoBO.listaCadastroAnexoComMiniatura(ActivityCliente.this, ClienteHelper.getCliente().getId_cadastro());
                                        ClienteHelper.setListaCadastroAnexo(listaCadastroAnexo);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progress.dismiss();
                                            }
                                        });
                                    }
                                });
                                a.start();
                            }
                        } else {
                            if (ClienteHelper.getCliente().getListaCadastroAnexo().size() > 0) {
                                ClienteHelper.setListaCadastroAnexo(ClienteHelper.getCliente().getListaCadastroAnexo());
                            }
                        }

                        Intent intent = new Intent(ActivityCliente.this, CadastroClienteMain.class);
                        intent.putExtra("novo", 1);
                        startActivity(intent);
                    } catch (CursorIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            });
            alert.show();
        } else {
            Intent intent = new Intent(ActivityCliente.this, ActivityCpfCnpjCliente.class);
            intent.putExtra("novo", 1);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        ButterKnife.bind(this);

        toolbar.setTitle("Lista de Clientes");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listaDeClientes.setLayoutManager(layoutManager);
        listaDeClientes.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        if (getIntent().getIntExtra("acao", 0) == 1) {
            btnInserirCliente.setVisibility(View.GONE);
        }

        rgFiltraCliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (searchView.getQuery() != null && searchView.getQuery().toString().trim().isEmpty())
                    preencheLista(listaClientes());
                else
                    preencheLista(buscaClientes(searchView.getQuery().toString()));
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        System.gc();
    }

    @Override
    protected void onResume() {
        try {
            if (searchView != null && !searchView.getQuery().toString().trim().isEmpty())
                preencheLista(buscaClientes(searchView.getQuery().toString()));
            else {
                preencheLista(listaClientes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
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
                        preencheLista(listaClientes());
                    } else {
                        preencheLista(buscaClientes(query));
                    }
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Razão Social / Nome Fantasia / CNPJ-CPF / Telefone");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.gc();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Cliente> buscaClientes(final String query) {
        try {
            List<Cliente> listaCliente;
            switch (rgFiltraCliente.getCheckedRadioButtonId()) {
                case R.id.filtraTodosClientes:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND ATIVO = 'S' AND F_VENDEDOR = 'N' AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY ATIVO DESC, NOME_CADASTRO");
                    break;
                case R.id.filtraClientesEfetivados:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' AND F_VENDEDOR = 'N' AND ID_CADASTRO_SERVIDOR > 0 AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '?%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY ATIVO DESC, NOME_CADASTRO;");
                    break;
                case R.id.filtraClientesNaoEfetivados:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE <> 'S' AND ATIVO = 'S' AND F_VENDEDOR = 'N' AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '?%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY ATIVO DESC, NOME_CADASTRO;");
                    break;
                default:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND ATIVO = 'S' AND F_VENDEDOR = 'N' AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY ATIVO DESC, NOME_CADASTRO");
                    break;
            }
            listaDeClientes.setVisibility(View.VISIBLE);
            edtTotalClientes.setText("Clientes listados: " + listaCliente.size() + "   ");
            edtTotalClientes.setTextColor(Color.BLACK);
            return listaCliente;
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            listaDeClientes.setVisibility(View.INVISIBLE);
            edtTotalClientes.setText("Não há clientes a serem exibidos!   ");
            edtTotalClientes.setTextColor(Color.RED);
        }
        return null;
    }

    public List<Cliente> listaClientes() {
        try {
            List<Cliente> listaCliente;
            switch (rgFiltraCliente.getCheckedRadioButtonId()) {
                case R.id.filtraTodosClientes:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND ATIVO = 'S' AND F_VENDEDOR = 'N' ORDER BY ATIVO DESC, NOME_CADASTRO;");
                    break;
                case R.id.filtraClientesEfetivados:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' AND F_VENDEDOR = 'N' AND ID_CADASTRO_SERVIDOR > 0 ORDER BY ATIVO DESC, NOME_CADASTRO;");
                    break;
                case R.id.filtraClientesNaoEfetivados:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND F_CLIENTE <> 'S' AND ATIVO = 'S' AND F_VENDEDOR = 'N' ORDER BY ATIVO DESC, NOME_CADASTRO;");
                    break;
                default:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE FINALIZADO <> 'N' AND AND ATIVO = 'S' AND F_VENDEDOR = 'N' ORDER BY ATIVO DESC, NOME_CADASTRO;");
                    break;
            }
            listaDeClientes.setVisibility(View.VISIBLE);
            edtTotalClientes.setText("Clientes listados: " + listaCliente.size() + "   ");
            edtTotalClientes.setTextColor(Color.BLACK);
            return listaCliente;
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            listaDeClientes.setVisibility(View.INVISIBLE);
            edtTotalClientes.setText("Não há clientes a serem exibidos!   ");
            edtTotalClientes.setTextColor(Color.RED);
            Toast.makeText(this, "Nenhum registro encontrado", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public void preencheLista(List<Cliente> clientes) {
        if (getIntent().getIntExtra("acao", 0) == 1) {

            listaClienteAdapter = new ListaClienteAdapter(clientes, new ListaClienteAdapter.ClienteAdapterListener() {
                @Override
                public void onClickListener(int position) {
                    if (listaClienteAdapter.getItem(position).getIdCategoria() <= 0) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCliente.this);
                        alert.setTitle("Atenção");
                        alert.setMessage("Este cliente não tem categoria definida");
                        alert.setNeutralButton("OK", null);
                        alert.show();
                    } else {
                        try {
                            ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                            Pedido2 pedido2 = new Pedido2();
                            activityPedidoMain.pegaCliente(listaClienteAdapter.getItem(position));
                            System.out.println(listaClienteAdapter.getItem(position));
                            pedido2.pegaCliente(listaClienteAdapter.getItem(position));
                            /*CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
                            HistoricoFinanceiroHelper.setCadastroFinanceiroResumo(cadastroFinanceiroResumoDAO.listaCadastroFinanceiroResumo(ClienteHelper.getCliente().getId_cadastro_servidor()));*/
                            System.gc();
                            finish();
                        } catch (CursorIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            Toast.makeText(ActivityCliente.this, "Financeiro não encontrado, por favor faça a sincronia e tente novamente!", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onLongClickListener(int position) {

                }
            });

        } else {
            listaClienteAdapter = new ListaClienteAdapter(clientes, new ListaClienteAdapter.ClienteAdapterListener() {
                @Override
                public void onClickListener(int position) {
                    if (listaClienteAdapter.getSelectedItensCount() > 0) {
                        enableActionMode(position);
                    } else {
                        Intent intent;
                        if (listaClienteAdapter.getItem(position).getF_cliente().equals("S")) {
                            intent = new Intent(ActivityCliente.this, ContatoActivity.class);
                            ClienteHelper.setCliente(listaClienteAdapter.getItem(position));
                            if (listaClienteAdapter.getItem(position).getId_cadastro_servidor() > 0)
                                intent.putExtra("vizualizacao", 1);
                        } else {
                            intent = new Intent(ActivityCliente.this, CadastroClienteMain.class);
                            intent.putExtra("novo", 1);
                            ClienteHelper.setCliente(listaClienteAdapter.getItem(position));
                        }
                        System.gc();
                        startActivity(intent);
                    }
                }

                @Override
                public void onLongClickListener(int position) {
                    enableActionMode(position);
                }
            });
        }
        listaDeClientes.setAdapter(listaClienteAdapter);
        listaClienteAdapter.notifyDataSetChanged();
    }

    public void enableActionMode(final int position) {
        if (listaClienteAdapter.getItem(position).getId_cadastro_servidor() <= 0 || (listaClienteAdapter.getItem(position).getId_cadastro_servidor() > 0 && listaClienteAdapter.getItem(position).getAlterado().equals("S"))) {
            if (listaClienteAdapter.getSelectedItensCount() == 0 || (listaClienteAdapter.getItensSelecionados().get(listaClienteAdapter.getSelectedItensCount() - 1).getAlterado().equals(listaClienteAdapter.getItem(position).getAlterado()))) {
                if (actionMode == null) {
                    actionMode = startActionMode(new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            mode.getMenuInflater().inflate(R.menu.menu_cadastro_cliente, menu);
                            if (listaClienteAdapter.getItem(position).getId_cadastro_servidor() > 0)
                                menu.findItem(R.id.excluir_cliente).setVisible(false);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            return false;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.subir_cliente:
                                    AlertDialog.Builder alertEnviar = new AlertDialog.Builder(ActivityCliente.this);
                                    alertEnviar.setTitle("Atenção");
                                    if (listaClienteAdapter.getSelectedItensCount() > 1)
                                        alertEnviar.setMessage("Deseja enviar os " + listaClienteAdapter.getSelectedItensCount() + " cadastros de clientes selecionandos ao servidor?");
                                    else
                                        alertEnviar.setMessage("Deseja enviar o cliente " + listaClienteAdapter.getItensSelecionados().get(0).getNome_cadastro() + " ao servidor?");
                                    alertEnviar.setNegativeButton("NÃO", null);
                                    alertEnviar.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            enviarClientes(listaClienteAdapter.getItensSelecionados());
                                        }
                                    });
                                    alertEnviar.show();
                                    break;
                                case R.id.excluir_cliente:
                                    AlertDialog.Builder alertExcluir = new AlertDialog.Builder(ActivityCliente.this);
                                    alertExcluir.setTitle("Atenção");
                                    if (listaClienteAdapter.getSelectedItensCount() > 1)
                                        alertExcluir.setMessage("Deseja excluir esses " + listaClienteAdapter.getSelectedItensCount() + " cadastros?");
                                    else
                                        alertExcluir.setMessage("Deseja excluir o cadastro de " + listaClienteAdapter.getItensSelecionados().get(0).getNome_cadastro() + " ?");
                                    alertExcluir.setNegativeButton("NÃO", null);
                                    alertExcluir.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            for (Cliente cliente : listaClienteAdapter.getItensSelecionados()) {
                                                db.excluirClienteLocal(cliente);
                                            }
                                            actionMode.finish();
                                            listaClienteAdapter.clearSelection();
                                            actionMode = null;
                                            onResume();
                                        }
                                    });
                                    alertExcluir.show();
                                    break;
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            actionMode.finish();
                            listaClienteAdapter.clearSelection();
                            actionMode = null;
                        }
                    });
                }
                toggleSelection(position);
            } else {
                Toast.makeText(ActivityCliente.this, "O cadastro " + listaClienteAdapter.getItem(position).getNome_cadastro() + " não faz parte do grupo de seleção ativado", Toast.LENGTH_SHORT).show();
            }
        } else if (listaClienteAdapter.getItem(position).getF_cliente().equals("S")) {
            Toast.makeText(ActivityCliente.this, "Esse cliente já está efetivado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ActivityCliente.this, "Cliente foi enviado e esta aguardando análise para efetivação", Toast.LENGTH_SHORT).show();
        }
    }

    public void toggleSelection(int position) {
        listaClienteAdapter.toggleSelection(position);
        if (listaClienteAdapter.getSelectedItensCount() == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(listaClienteAdapter.getSelectedItensCount()));
            actionMode.invalidate();
        }
    }

    public void enviarClientes(List<Cliente> listaClientes) {
        final ProgressDialog progress = new ProgressDialog(ActivityCliente.this);
        progress.setTitle("Aguarde");
        progress.setMessage("Enviando os cadastros dos clientes");
        progress.setCancelable(false);
        progress.show();
        Rotas apiRetrofit = Api.buildRetrofit();

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        final CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
        try {
            for (Cliente cliente : listaClientes) {
                try {
                    cliente.setListaCadastroAnexo(cadastroAnexoDAO.enviarCadastroAnexo(cliente.getId_cadastro()));
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        Call<List<Cliente>> call = apiRetrofit.salvarClientes(cabecalho, listaClientes);
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                switch (response.code()) {
                    case 200:
                        List<Cliente> clientesRetorno = response.body();
                        if (clientesRetorno != null && clientesRetorno.size() > 0) {
                            for (Cliente cliente : clientesRetorno) {
                                cliente.setAlterado("N");
                                db.atualizarTBL_CADASTRO(cliente);
                                db.alterar("DELETE FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + cliente.getId_cadastro() + ";");
                                if (cliente.getListaCadastroAnexo().size() > 0) {
                                    for (CadastroAnexo cadastroAnexo : cliente.getListaCadastroAnexo()) {
                                        if (cadastroAnexo.getExcluido().equals("N"))
                                            cadastroAnexoDAO.atualizarCadastroAnexo(cadastroAnexo);
                                        else if (cadastroAnexo.getExcluido().equals("S"))
                                            db.alterar("DELETE FROM TBL_CADASTRO_ANEXOS WHERE ID_ANEXO = " + cadastroAnexo.getIdAnexo() + ";");
                                    }
                                } else {
                                    db.alterar("DELETE FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + cliente.getId_cadastro() + ";");
                                }
                            }
                            listaClienteAdapter.clearSelection();
                            actionMode.finish();
                            actionMode = null;
                            progress.dismiss();
                            Toast.makeText(ActivityCliente.this, "Enviado com sucesso", Toast.LENGTH_SHORT).show();
                            onResume();
                        }
                        break;
                    case 500:
                        listaClienteAdapter.clearSelection();
                        actionMode.finish();
                        actionMode = null;
                        progress.dismiss();
                        Toast.makeText(ActivityCliente.this, "Erro ao enviar os cadastros", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        progress.dismiss();
                        Toast.makeText(ActivityCliente.this, "Codigo de retorno não implementado: " + response.code(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ActivityCliente.this, "Falha ao enviar cadastros", Toast.LENGTH_SHORT).show();
                listaClienteAdapter.clearSelection();
                actionMode.finish();
                actionMode = null;
            }
        });
    }
}
