package com.example.rcksuporte05.rcksistemas.activity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaClienteAdapter;
import com.example.rcksuporte05.rcksistemas.adapters.RecyclerTouchListener;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido2;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private DBHelper db = new DBHelper(this);
    private ListaClienteAdapter listaClienteAdapter;
    private SearchView searchView;

    @OnClick(R.id.btnInserirCliente)
    public void inserirCliente() {
        Cliente cliente = new Cliente();
        Intent intent = new Intent(ActivityCliente.this, CadastroClienteMain.class);
        ClienteHelper.setCliente(cliente);
        startActivity(intent);
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
            toolbar.setTitle("Pesquisa de Clientes");

            listaDeClientes.addOnItemTouchListener(new RecyclerTouchListener(this, listaDeClientes, new RecyclerTouchListener.ClickListener() {

                @Override
                public void onClick(View view, int position) {
                    if (listaClienteAdapter.getItem(position).getIdCategoria() <= 0) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCliente.this);
                        alert.setTitle("Atenção");
                        alert.setMessage("Este cliente não tem categoria definida!");
                        alert.setNeutralButton("OK", null);
                        alert.show();
                    } else {
                        ActivityPedidoMain activityPedidoMain = new ActivityPedidoMain();
                        Pedido2 pedido2 = new Pedido2();
                        activityPedidoMain.pegaCliente(listaClienteAdapter.getItem(position));
                        pedido2.pegaCliente(listaClienteAdapter.getItem(position));
                        System.gc();
                        finish();
                    }
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        } else {

            listaDeClientes.addOnItemTouchListener(new RecyclerTouchListener(this, listaDeClientes, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    if (listaClienteAdapter.getItem(position).getId_cadastro_servidor() > 0) {
                        Intent intent = new Intent(ActivityCliente.this, ContatoActivity.class);
                        ClienteHelper.setCliente(listaClienteAdapter.getItem(position));
                        System.gc();
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(ActivityCliente.this, CadastroClienteMain.class);
                        ClienteHelper.setCliente(listaClienteAdapter.getItem(position));
                        System.gc();
                        startActivity(intent);
                    }
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

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

//        novo_cliente = menu.findItem(R.id.menu_item_novo_cliente);

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
           /* case R.id.menu_item_novo_cliente:
                Intent intent = new Intent(ActivityCliente.this, CadastroClienteMain.class);
                bundle = new Bundle();
                bundle.putString("cliente", "0");
                intent.putExtras(bundle);
                startActivity(intent);
                break;*/
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
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY ATIVO DESC, NOME_CADASTRO");
                    break;
                case R.id.filtraClientesEfetivados:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' AND ID_CADASTRO_SERVIDOR > 0 AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '?%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY ATIVO DESC, NOME_CADASTRO;");
                    break;
                case R.id.filtraClientesNaoEfetivados:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' AND ID_CADASTRO_SERVIDOR <= 0 AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '?%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY ATIVO DESC, NOME_CADASTRO;");
                    break;
                default:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' AND (NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '?%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' OR ID_CADASTRO_SERVIDOR LIKE '%" + query + "%') ORDER BY ATIVO DESC, NOME_CADASTRO;");
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
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' ORDER BY ATIVO DESC, NOME_CADASTRO;");
                    break;
                case R.id.filtraClientesEfetivados:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' AND ID_CADASTRO_SERVIDOR > 0 ORDER BY ATIVO DESC, NOME_CADASTRO;");
                    break;
                case R.id.filtraClientesNaoEfetivados:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' AND ID_CADASTRO_SERVIDOR <= 0 ORDER BY ATIVO DESC, NOME_CADASTRO;");
                    break;
                default:
                    listaCliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' ORDER BY ATIVO DESC, NOME_CADASTRO;");
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
            Toast.makeText(this, "Nenhum registro encontrado!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public void preencheLista(List<Cliente> clientes) {
        listaClienteAdapter = new ListaClienteAdapter(clientes);
        listaDeClientes.setAdapter(listaClienteAdapter);
        listaClienteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
