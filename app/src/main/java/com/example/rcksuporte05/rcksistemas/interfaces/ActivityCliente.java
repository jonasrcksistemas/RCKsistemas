package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterClientes;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido1;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido3;

import java.util.List;

public class ActivityCliente extends AppCompatActivity {
    //    private MenuItem novo_cliente;
    private ListView lstClientes;
    private Toolbar toolbar;
    private List<Cliente> listaAux;
    private List<Cliente> lista;
    private ListaAdapterClientes adaptadorPrincipal;
    private ListaAdapterClientes adaptador;
    private DBHelper db = new DBHelper(this);
    private Thread b = new Thread();
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        lstClientes = (ListView) findViewById(R.id.lstClientes);
        toolbar = (Toolbar) findViewById(R.id.tb_cliente);
        toolbar.setTitle("Lista de Clientes");
        bundle = getIntent().getExtras();
        if (bundle.getInt("acao") == 1) {
            toolbar.setTitle("Pesquisa de Clientes");

            try {
                lista = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' AND ATIVO = 'S' ORDER BY ATIVO DESC, NOME_CADASTRO;");
                listaAux = lista;
                adaptadorPrincipal = new ListaAdapterClientes(ActivityCliente.this, lista);
                lstClientes.setAdapter(adaptadorPrincipal);
                System.gc();
            } catch (Exception e) {
                Toast.makeText(this, "Não há nenhum cliente a ser exibido!", Toast.LENGTH_LONG).show();
            }

            lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Pedido1 pedido1 = new Pedido1();
                    Pedido3 pedido3 = new Pedido3();
                    pedido1.pegaCliente(listaAux.get(position));
                    pedido3.pegaCliente(listaAux.get(position));
                    System.gc();
                    finish();
                }
            });
        } else {

            try {
                lista = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_CLIENTE = 'S' AND F_VENDEDOR = 'N' ORDER BY ATIVO DESC, NOME_CADASTRO;");
                listaAux = lista;
                adaptadorPrincipal = new ListaAdapterClientes(ActivityCliente.this, lista);
                lstClientes.setAdapter(adaptadorPrincipal);
                System.gc();
            } catch (Exception e) {
                Toast.makeText(this, "Não há nenhum cliente a ser exibido!", Toast.LENGTH_LONG).show();
            }

            lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(ActivityCliente.this, CadastroClienteMain.class);
                    bundle = new Bundle();
                    bundle.putString("cliente", listaAux.get(position).getId_cadastro());
                    String[] cliente = new String[83];
                    cliente[0] = listaAux.get(position).getAtivo();
                    cliente[1] = listaAux.get(position).getId_empresa();
                    cliente[2] = listaAux.get(position).getId_cadastro();
                    cliente[3] = listaAux.get(position).getPessoa_f_j();
                    cliente[4] = listaAux.get(position).getData_aniversario();
                    cliente[5] = listaAux.get(position).getNome_cadastro();
                    cliente[6] = listaAux.get(position).getNome_fantasia();
                    cliente[7] = listaAux.get(position).getCpf_cnpj();
                    cliente[8] = listaAux.get(position).getInscri_estadual();
                    cliente[9] = listaAux.get(position).getInscri_municipal();
                    cliente[10] = listaAux.get(position).getEndereco();
                    cliente[11] = listaAux.get(position).getEndereco_bairro();
                    cliente[12] = listaAux.get(position).getEndereco_numero();
                    cliente[13] = listaAux.get(position).getEndereco_complemento();
                    cliente[14] = listaAux.get(position).getEndereco_uf();
                    cliente[15] = listaAux.get(position).getEndereco_id_municipio();
                    cliente[16] = listaAux.get(position).getEndereco_cep();
                    cliente[17] = listaAux.get(position).getUsuario_id();
                    cliente[18] = listaAux.get(position).getUsuario_nome();
                    cliente[19] = listaAux.get(position).getUsuario_data();
                    cliente[20] = listaAux.get(position).getF_cliente();
                    cliente[21] = listaAux.get(position).getF_fornecedor();
                    cliente[22] = listaAux.get(position).getF_funcionario();
                    cliente[23] = listaAux.get(position).getF_vendedor();
                    cliente[24] = listaAux.get(position).getF_transportador();
                    cliente[25] = listaAux.get(position).getData_ultima_compra();
                    cliente[26] = listaAux.get(position).getId_vendedor();
                    cliente[27] = listaAux.get(position).getF_id_cliente();
                    cliente[28] = listaAux.get(position).getId_entidade();
                    cliente[29] = listaAux.get(position).getF_id_fornecedor();
                    cliente[30] = listaAux.get(position).getF_id_vendedor();
                    cliente[31] = listaAux.get(position).getF_id_transportador();
                    cliente[32] = listaAux.get(position).getTelefone_principal();
                    cliente[33] = listaAux.get(position).getEmail_principal();
                    cliente[34] = listaAux.get(position).getId_pais();
                    cliente[35] = listaAux.get(position).getF_id_funcionario();
                    cliente[36] = listaAux.get(position).getAvisar_com_dias();
                    cliente[37] = listaAux.get(position).getObservacoes();
                    cliente[38] = listaAux.get(position).getPadrao_id_c_custo();
                    cliente[39] = listaAux.get(position).getPadrao_id_c_gerenciadora();
                    cliente[40] = listaAux.get(position).getPadrao_id_c_analitica();
                    cliente[41] = listaAux.get(position).getCob_endereco();
                    cliente[42] = listaAux.get(position).getCob_endereco_bairro();
                    cliente[43] = listaAux.get(position).getCob_endereco_numero();
                    cliente[44] = listaAux.get(position).getCob_endereco_complemento();
                    cliente[45] = listaAux.get(position).getCob_endereco_uf();
                    cliente[46] = listaAux.get(position).getCob_endereco_id_municipio();
                    cliente[47] = listaAux.get(position).getCob_endereco_cep();
                    cliente[48] = listaAux.get(position).getCob_endereco_id_pais();
                    cliente[49] = listaAux.get(position).getLimite_credito();
                    cliente[50] = listaAux.get(position).getLimite_disponivel();
                    cliente[51] = listaAux.get(position).getPessoa_contato_financeiro();
                    cliente[52] = listaAux.get(position).getEmail_financeiro();
                    cliente[53] = listaAux.get(position).getObservacoes_faturamento();
                    cliente[54] = listaAux.get(position).getObservacoes_financeiro();
                    cliente[55] = listaAux.get(position).getTelefone_dois();
                    cliente[56] = listaAux.get(position).getTelefone_tres();
                    cliente[57] = listaAux.get(position).getPessoa_contato_principal();
                    cliente[58] = listaAux.get(position).getInd_da_ie_destinatario();
                    cliente[59] = listaAux.get(position).getComissao_percentual();
                    cliente[60] = listaAux.get(position).getId_setor();
                    cliente[61] = listaAux.get(position).getNfe_email_enviar();
                    cliente[62] = listaAux.get(position).getNfe_email_um();
                    cliente[63] = listaAux.get(position).getNfe_email_dois();
                    cliente[64] = listaAux.get(position).getNfe_email_tres();
                    cliente[65] = listaAux.get(position).getNfe_email_quatro();
                    cliente[66] = listaAux.get(position).getNfe_email_cinco();
                    cliente[67] = listaAux.get(position).getId_grupo_vendedor();
                    cliente[68] = listaAux.get(position).getVendedor_usa_portal();
                    cliente[69] = listaAux.get(position).getVendedor_id_user_portal();
                    cliente[70] = listaAux.get(position).getF_tarifa();
                    cliente[71] = listaAux.get(position).getF_id_tarifa();
                    cliente[72] = listaAux.get(position).getF_produtor();
                    cliente[73] = listaAux.get(position).getRg_numero();
                    cliente[74] = listaAux.get(position).getRg_ssp();
                    cliente[75] = listaAux.get(position).getConta_contabil();
                    cliente[76] = listaAux.get(position).getMotorista();
                    cliente[77] = listaAux.get(position).getF_id_motorista();
                    cliente[78] = listaAux.get(position).getHabilitacao_numero();
                    cliente[79] = listaAux.get(position).getHabilitacao_categoria();
                    cliente[80] = listaAux.get(position).getHabilitacao_vencimento();
                    cliente[81] = listaAux.get(position).getMot_id_transportadora();
                    cliente[82] = listaAux.get(position).getLocal_cadastro();
                    bundle.putStringArray("clienteListar", cliente);
                    intent.putExtras(bundle);
                    System.gc();
                    startActivity(intent);
                }
            });
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerForContextMenu(lstClientes);

        System.gc();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(listaAux.get(info.position).getNome_cadastro());

        MenuItem historicoFInanceiro = menu.add("Historico Financeiro");
        historicoFInanceiro.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ActivityCliente.this, HistoricoFinanceiroMain.class);
                intent.putExtra("idCliente", Integer.parseInt(listaAux.get(info.position).getId_cadastro()));
                ActivityCliente.this.startActivity(intent);

                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_cliente, menu);

//        novo_cliente = menu.findItem(R.id.menu_item_novo_cliente);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
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
                b = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!query.equals("") && query.length() >= 3) {
                            try {
                                listaAux = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE NOME_CADASTRO LIKE '%" + query + "%' OR NOME_FANTASIA LIKE '%" + query + "%' OR CPF_CNPJ LIKE '" + query + "%' OR TELEFONE_PRINCIPAL LIKE '%" + query + "%' ORDER BY ATIVO DESC, NOME_CADASTRO");
                                adaptador = new ListaAdapterClientes(ActivityCliente.this, listaAux);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            lstClientes.setVisibility(View.VISIBLE);
                                            lstClientes.setAdapter(adaptador);
                                            adaptador.notifyDataSetChanged();
                                        } catch (NullPointerException | IllegalStateException e) {
                                            System.out.println("adaptador se nenhum dado!");
                                        }
                                    }
                                });
                            } catch (CursorIndexOutOfBoundsException | NullPointerException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lstClientes.setVisibility(View.INVISIBLE);
                                        Toast.makeText(ActivityCliente.this, "Sem resutados para '" + query + "'", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listaAux = lista;
                                    lstClientes.setVisibility(View.VISIBLE);
                                    lstClientes.setAdapter(adaptadorPrincipal);
                                    adaptadorPrincipal.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });
                if (!b.isAlive()) {
                    b.start();
                }
                System.gc();
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

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
