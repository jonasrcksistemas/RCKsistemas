package com.example.rcksuporte05.rcksistemas.interfaces;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterPedido;
import com.example.rcksuporte05.rcksistemas.extras.SlidingTabLayout;

public class ActivityPedidoMain extends AppCompatActivity {

    private SlidingTabLayout stl_tabsPedido;
    private ViewPager mViewPager;
    private TabsAdapterPedido tabsAdapterPedido;
    private PedidoHelper pedidoHelper;
    private int vizualizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_main);

        pedidoHelper = new PedidoHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFragsPedido);


        vizualizacao = getIntent().getIntExtra("vizualizacao", 0);

        mViewPager = (ViewPager) findViewById(R.id.vp_tabsPedido);
        tabsAdapterPedido = new TabsAdapterPedido(getSupportFragmentManager(), ActivityPedidoMain.this, UsuarioHelper.getUsuario(), vizualizacao);
        mViewPager.setAdapter(tabsAdapterPedido);

        stl_tabsPedido = (SlidingTabLayout) findViewById(R.id.stl_tabsPedido);
        stl_tabsPedido.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        stl_tabsPedido.setSelectedIndicatorColors(Color.WHITE);
        stl_tabsPedido.setViewPager(mViewPager);

        if (vizualizacao == 1) {
            toolbar.setTitle("Vizualização de Pedido");
            stl_tabsPedido.setBackgroundColor(getResources().getColor(R.color.colorPrimaryAzul));
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryAzul));
            this.setTheme(R.style.Theme_MeuTemaPedido);
        } else if (pedidoHelper.getIdPedido() > 0) {
            toolbar.setTitle("Alteração do pedido");
        } else {
            toolbar.setTitle("Lançamento de Pedido");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    protected void onDestroy() {
        pedidoHelper.limparDados();
        System.gc();
        super.onDestroy();
    }
}
