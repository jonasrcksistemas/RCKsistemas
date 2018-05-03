package com.example.rcksuporte05.rcksistemas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterPedido;
import com.example.rcksuporte05.rcksistemas.fragment.Pedido1;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.util.SlidingTabLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityPedidoMain extends AppCompatActivity {

    public static ActionMode actionMode;
    private static Cliente objetoCliente = null;
    @BindView(R.id.stl_tabsPedido)
    SlidingTabLayout stl_tabsPedido;
    @BindView(R.id.vp_tabsPedido)
    ViewPager mViewPager;
    @BindView(R.id.txtNomeCliente)
    TextView txtNomeCliente;
    @BindView(R.id.BtnFinanceiro)
    Button BtnFinanceiro;
    @BindView(R.id.toolbar2)
    Toolbar toolbar2;
    @BindView((R.id.txtCategoria))
    TextView txtCategoria;
    ActionModeCallback actionModeCallback;
    private PedidoHelper pedidoHelper;
    private TabsAdapterPedido tabsAdapterPedido;
    private int vizualizacao;
    private DBHelper db;
    private Bundle bundle = new Bundle();
    private WebPedido webPedido;
    private Pedido1 pedido1 = new Pedido1();
    private HashMap<Integer, Categoria> listaCategoria;
    private CategoriaDAO categoriaDAO;
    private WebPedidoDAO webPedidoDAO;

    @OnClick(R.id.BtnFinanceiro)
    public void financeiro() {
        if (objetoCliente != null) {
            Intent intent = new Intent(this, FinanceiroResumoActivity.class);
            HistoricoFinanceiroHelper.setCliente(objetoCliente);
            this.startActivity(intent);
        } else {
            txtNomeCliente.setTextColor(Color.YELLOW);
            Toast.makeText(this, "Você precisa selecionar o cliente para consultar seu historico financeiro", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.txtNomeCliente)
    public void nomeCliente() {
        if (vizualizacao != 1) {
            bundle = new Bundle();
            bundle.putInt("acao", 1);
            Intent intent = new Intent(this, ActivityCliente.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_main);
        ButterKnife.bind(this);

        pedidoHelper = new PedidoHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFragsPedido);

        vizualizacao = getIntent().getIntExtra("vizualizacao", 0);

        tabsAdapterPedido = new TabsAdapterPedido(getSupportFragmentManager(), ActivityPedidoMain.this, UsuarioHelper.getUsuario(), vizualizacao);
        mViewPager.setAdapter(tabsAdapterPedido);

        stl_tabsPedido.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        stl_tabsPedido.setSelectedIndicatorColors(Color.WHITE);
        stl_tabsPedido.setViewPager(mViewPager);

        if (vizualizacao == 1) {
            toolbar.setTitle("Vizualização de Pedido");
            toolbar2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryCinza));
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryCinza));
            stl_tabsPedido.setBackgroundColor(getResources().getColor(R.color.colorPrimaryCinza));
            txtNomeCliente.setFocusable(false);

            this.setTheme(R.style.Theme_MeuTemaPedido);
        } else if (pedidoHelper.getIdPedido() > 0) {
            toolbar.setTitle("Alteração do pedido");
        } else {
            toolbar.setTitle("Lançamento de Pedido");
        }
        db = new DBHelper(this);
        categoriaDAO = new CategoriaDAO(db);
        webPedidoDAO = new WebPedidoDAO(db);
        try {
            listaCategoria = categoriaDAO.listaHashCategoria();

        } catch (CursorIndexOutOfBoundsException e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
            alert.setMessage("É necessário executar a sincronização pelo menos uma vez antes de efetuar um pedido");
            alert.setTitle("Atenção!");
            alert.setCancelable(false);
            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PedidoHelper.getActivityPedidoMain().finish();
                }
            });
            alert.show();
            e.printStackTrace();
        }

        if (PedidoHelper.getIdPedido() > 0) {
            webPedido = webPedidoDAO.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + PedidoHelper.getIdPedido()).get(0);
            objetoCliente = webPedido.getCadastro();
            ClienteHelper.setCliente(objetoCliente);
            if (webPedido.getId_web_pedido_servidor() != null)
                toolbar.setSubtitle("Pedido: " + webPedido.getId_web_pedido_servidor());
            else
                toolbar.setSubtitle("Pedido: " + webPedido.getId_web_pedido());


        } else {
            webPedido = new WebPedido();
        }

        actionModeCallback = new ActionModeCallback();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public WebPedido salvaPedido() {
        webPedido.setCadastro(objetoCliente);
        webPedido.setId_empresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
        webPedido.setId_vendedor(UsuarioHelper.getUsuario().getId_quando_vendedor());

        webPedido.setExcluido("N");
        webPedido.setUsuario_lancamento_id(UsuarioHelper.getUsuario().getId_usuario());
        webPedido.setUsuario_lancamento_nome(UsuarioHelper.getUsuario().getNome_usuario());

        webPedido.setStatus("A");

        return webPedido;
    }

    @Override
    public void onBackPressed() {
        if (vizualizacao != 1) {
            if (mViewPager.getCurrentItem() != 0) {
                mViewPager.setCurrentItem(0);
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityPedidoMain.this);
                alert.setMessage("Tem certeza que deseja sair do pedido? As informações não salvas serão perdidas");
                alert.setTitle("Atenção!");
                alert.setNegativeButton("Não", null);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alert.show();
            }
        } else {
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (vizualizacao != 1) {
                    if (mViewPager.getCurrentItem() != 0) {
                        mViewPager.setCurrentItem(0);
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityPedidoMain.this);
                        alert.setMessage("Tem certeza que deseja sair do pedido? As informações não salvas serão perdidas");
                        alert.setTitle("Atenção!");
                        alert.setNegativeButton("Não", null);
                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        alert.show();
                    }
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void pegaCliente(Cliente cliente) {
        objetoCliente = cliente;
        ClienteHelper.setCliente(cliente);
    }

    @Override
    protected void onDestroy() {
        pedidoHelper.limparDados();
        objetoCliente = null;
        ClienteHelper.setCliente(null);
        System.gc();
        super.onDestroy();
    }

    public boolean verificaCliente() {
        if (objetoCliente != null)
            return true;
        else
            return false;
    }

    @Override
    protected void onResume() {
        try {
            txtNomeCliente.setTextColor(Color.WHITE);
            txtNomeCliente.setText(objetoCliente.getNome_cadastro());
            txtCategoria.setText(listaCategoria.get(objetoCliente.getIdCategoria()).getNomeCategoria());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    public void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        pedido1.toggleSelection(position);
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
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
                    pedido1.removeProdutos(pedido1.getListaAdapterProdutoPedido().getItensSelecionados(), ActivityPedidoMain.this);
                    mode.finish();
                    return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            Pedido1.getListaAdapterProdutoPedido().clearSelections();
            actionMode = null;
        }
    }
}