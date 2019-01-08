package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.rcksuporte05.rcksistemas.BO.CadastroAnexoBO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterCliente;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.util.SlidingTabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroClienteMain extends AppCompatActivity {
    @BindView(R.id.vp_tabs)
    public ViewPager mViewPager;
    @BindView(R.id.toolbarFrags)
    Toolbar toolbar;
    @BindView(R.id.stl_tabs)
    SlidingTabLayout mSlidingTabLayout;
    private TabsAdapterCliente tabsAdapterCliente;

    private String[] titles = {"Geral", "Endereço", "Email NF-E", "Fotos", "Observações"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente_main);
        ButterKnife.bind(this);
        toolbar.setTitle("Cadastro de Clientes");

        tabsAdapterCliente = new TabsAdapterCliente(getSupportFragmentManager());
        mViewPager.setAdapter(tabsAdapterCliente);

        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(Color.WHITE);
        mSlidingTabLayout.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (ClienteHelper.getCadastroCliente8() != null)
                    ClienteHelper.getCadastroCliente8().finishActionMode();
                switch (position) {
                    case 0:
                        if (ClienteHelper.getCadastroCliente1() != null) {
                            ClienteHelper.getCadastroCliente1().inserirDadosDaFrame();
                            if (getIntent().getIntExtra("novo", 0) >= 1) {
                                toolbar.setTitle(titles[position]);
                            }
                        }
                        break;
                    case 1:
                        if (ClienteHelper.getCadastroCliente2() != null) {
                            ClienteHelper.getCadastroCliente2().inserirDadosDaFrame();
                            if (getIntent().getIntExtra("novo", 0) >= 1) {
                                toolbar.setTitle(titles[position]);
                            }
                        }
                        break;
                    case 2:
                        if (getIntent().getIntExtra("novo", 0) >= 1) {
                            toolbar.setTitle(titles[position]);
                        }
                        break;
                    case 3:
                        if (ClienteHelper.getCadastroCliente7() != null) {
                            ClienteHelper.getCadastroCliente7().inserirDadosDaFrame();
                            if (getIntent().getIntExtra("novo", 0) >= 1) {
                                toolbar.setTitle(titles[position]);
                            }
                        }
                        break;
                    case 4:
                        if (getIntent().getIntExtra("novo", 0) >= 1) {
                            toolbar.setTitle(titles[position]);
                        }
                        break;
                    case 5:
                        if (ClienteHelper.getCadastroCliente9() != null) {
                            ClienteHelper.getCadastroCliente9().inserirDadosDaFrame();
                            if (getIntent().getIntExtra("novo", 0) >= 1) {
                                toolbar.setTitle(titles[position]);
                            }
                        }
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (ClienteHelper.getCadastroCliente8() != null)
                    ClienteHelper.getCadastroCliente8().finishActionMode();
                switch (position) {
                    case 0:
                        if (ClienteHelper.getCadastroCliente1() != null) {
                            ClienteHelper.getCadastroCliente1().inserirDadosDaFrame();
                        }
                        break;
                    case 1:
                        if (ClienteHelper.getCadastroCliente2() != null) {
                            ClienteHelper.getCadastroCliente2().inserirDadosDaFrame();
                        }
                        break;
                    case 3:
                        if (ClienteHelper.getCadastroCliente7() != null) {
                            ClienteHelper.getCadastroCliente7().inserirDadosDaFrame();
                        }
                        break;
                    case 5:
                        if (ClienteHelper.getCadastroCliente9() != null) {
                            ClienteHelper.getCadastroCliente9().inserirDadosDaFrame();
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        DBHelper db = new DBHelper(this);
        try {
            if (ClienteHelper.getCliente().getId_cadastro() > 0) {
                if (db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + ClienteHelper.getCliente().getId_cadastro() + " AND EXCLUIDO = 'N';") > 0) {
                    final ProgressDialog progress = new ProgressDialog(this);
                    progress.setTitle("Aguarde");
                    progress.setMessage("Carregando anexos do cliente");
                    progress.setCancelable(false);
                    progress.show();
                    final CadastroAnexoBO cadastroAnexoBO = new CadastroAnexoBO();
                    Thread a = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoBO.listaCadastroAnexoComMiniatura(CadastroClienteMain.this, ClienteHelper.getCliente().getId_cadastro());
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

        } catch (NullPointerException e) {
            e.printStackTrace();
            finish();
        }

        ClienteHelper.setCadastroClienteMain(this);

        if (getIntent().getIntExtra("novo", 0) >= 1) {
            mSlidingTabLayout.setVisibility(View.GONE);
            mViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getIntent().getIntExtra("novo", 0) < 1) {
                    if (mViewPager.getCurrentItem() != 0) {
                        mViewPager.setCurrentItem(0);
                    } else {
                        finish();
                    }
                } else {
                    if (mViewPager.getCurrentItem() != 0) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                    } else {
                        finish();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (!ClienteHelper.getCliente().getF_cliente().equals("S")) {
            ClienteHelper.clear();
        }
        super.onDestroy();
    }
}
