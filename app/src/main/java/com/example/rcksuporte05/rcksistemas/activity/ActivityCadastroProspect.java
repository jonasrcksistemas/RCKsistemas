package com.example.rcksuporte05.rcksistemas.activity;

import android.content.DialogInterface;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.Helper.VisitaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterProspect;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.util.SlidingTabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 25/01/2018.
 */

public class ActivityCadastroProspect extends AppCompatActivity {
    @BindView(R.id.vp_tabs_prospect)
    public ViewPager mViewPager;
    TabsAdapterProspect tabsAdapterProspect;
    @BindView(R.id.toolbarFragsProspect)
    Toolbar toolbar;

    @BindView(R.id.stl_tabs_Prospect)
    SlidingTabLayout mSlidingTabLayout;
    private DBHelper db;

    private String[] titles = {"Geral", "Endereços", "Descrever Ação"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_prospect);
        ButterKnife.bind(this);
        db = new DBHelper(this);

        buscarSegmentos();
        buscarMotivos();
        buscarPais();

        toolbar.setTitle("Cadastro de Prospect");

        tabsAdapterProspect = new TabsAdapterProspect(getSupportFragmentManager());
        mViewPager.setAdapter(tabsAdapterProspect);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                               @Override
                                               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                                   switch (position) {
                                                       case 0:
                                                           if (ProspectHelper.getCadastroProspectGeral() != null) {
                                                               ProspectHelper.getCadastroProspectGeral().inserirDadosDaFrame();
                                                               if (getIntent().getIntExtra("novo", 0) >= 1) {
                                                                   toolbar.setTitle(titles[position]);
                                                               }
                                                           }
                                                           break;
                                                       case 1:
                                                           if (ProspectHelper.getCadastroProspectEndereco() != null) {
                                                               ProspectHelper.getCadastroProspectEndereco().inserirDadosDaFrame();
                                                               if (getIntent().getIntExtra("novo", 0) >= 1) {
                                                                   toolbar.setTitle(titles[position]);
                                                               }
                                                           }
                                                           break;
                                                       case 2:
                                                           if (ProspectHelper.getCadastroProspectFotoSalvar() != null) {
                                                               ProspectHelper.getCadastroProspectFotoSalvar().insereDadosDaFrame();
                                                               if (getIntent().getIntExtra("novo", 0) >= 1) {
                                                                   toolbar.setTitle(titles[position]);
                                                               }
                                                           }
                                                           break;
                                                   }
                                               }

                                               @Override
                                               public void onPageSelected(int position) {
                                                   switch (position) {
                                                       case 0:
                                                           if (ProspectHelper.getCadastroProspectGeral() != null) {
                                                               ProspectHelper.getCadastroProspectGeral().inserirDadosDaFrame();
                                                           }
                                                           break;
                                                       case 1:
                                                           if (ProspectHelper.getCadastroProspectEndereco() != null) {
                                                               ProspectHelper.getCadastroProspectEndereco().inserirDadosDaFrame();
                                                           }
                                                           break;
                                                       case 2:
                                                           if (ProspectHelper.getCadastroProspectFotoSalvar() != null) {
                                                               ProspectHelper.getCadastroProspectFotoSalvar().insereDadosDaFrame();
                                                           }
                                                   }
                                               }

                                               @Override
                                               public void onPageScrollStateChanged(int state) {

                                               }
                                           }
        );

        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(Color.WHITE);
        mSlidingTabLayout.setViewPager(mViewPager);

        try {
            if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + ProspectHelper.getProspect().getId_prospect() + " AND ID_ENTIDADE = 10;") > 0) {
                CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
                List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoDAO.listaCadastroAnexoProspect(Integer.parseInt(ProspectHelper.getProspect().getId_prospect()));

                for (CadastroAnexo cadastroAnexo : listaCadastroAnexo) {
                    if (cadastroAnexo.getPrincipal().equals("S"))
                        ProspectHelper.getProspect().setFotoPrincipalBase64(cadastroAnexo);
                    else
                        ProspectHelper.getProspect().setFotoSecundariaBase64(cadastroAnexo);
                }
            }
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        try {
            if (ProspectHelper.getProspect().getIdPrimeiraVisita() > 0) {
                VisitaHelper.setVisitaProspect(db.listaVisitaPorId(ProspectHelper.getProspect()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getIntent().getIntExtra("novo", 0) >= 1) {
            try {
                ProspectHelper.setVendedor(db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_ID_VENDEDOR = " + UsuarioHelper.getUsuario().getId_quando_vendedor() + ";").get(0));
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            mSlidingTabLayout.setVisibility(View.GONE);

            mViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem());
                    return true;
                }
            });
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProspectHelper.setActivityMain(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getIntent().getIntExtra("novo", 1) < 1) {
                    if (mViewPager.getCurrentItem() != 0) {
                        mViewPager.setCurrentItem(0);
                    } else {
                        finish();
                    }
                } else {
                    if (mViewPager.getCurrentItem() != 0) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                    } else {
                        if (ProspectHelper.getProspect().getProspectSalvo().equals("N") && ProspectHelper.getProspect().getId_prospect() != null) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(this);
                            alert.setTitle("Atenção");
                            alert.setMessage("Você está prestes a fechar esse cadastro em andamento. Deseja salva-lo PARCIALMENTE para continua-lo mais tarde?" +
                                    "(Clicar em \"NÃO\" irá excluir esse Prospect)");
                            alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                            alert.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    db.alterar("DELETE FROM TBL_PROSPECT WHERE ID_PROSPECT = " + ProspectHelper.getProspect().getId_prospect() + ";");
                                    finish();
                                }
                            });
                            alert.setNeutralButton("Cancelar", null);
                            alert.show();

                        } else {
                            finish();
                        }

                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getIntExtra("novo", 1) < 1) {
            if (mViewPager.getCurrentItem() != 0) {
                mViewPager.setCurrentItem(0);
            } else {
                finish();
            }
        } else {
            if (mViewPager.getCurrentItem() != 0) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
            } else {
                if (ProspectHelper.getProspect().getProspectSalvo().equals("N") && ProspectHelper.getProspect().getId_prospect() != null) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Atenção");
                    alert.setMessage("Você está prestes a fechar esse cadastro em andamento. Deseja salva-lo PARCIALMENTE para continua-lo mais tarde?" +
                            "(Clicar em \"NÃO\" irá excluir esse Prospect)");
                    alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    alert.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.alterar("DELETE FROM TBL_PROSPECT WHERE ID_PROSPECT = " + ProspectHelper.getProspect().getId_prospect() + ";");
                            finish();
                        }
                    });
                    alert.setNeutralButton("Cancelar", null);
                    alert.show();

                } else {
                    finish();
                }

            }
        }
    }

    public void buscarSegmentos() {
        try {
            ProspectHelper.setSegmentos(db.listaSegmento());
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void buscarMotivos() {
        try {
            ProspectHelper.setMotivos(db.listaMotivoNaoCadastramento());
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void buscarPais() {
        try {
            ProspectHelper.setPaises(db.listaPaises());
        } catch (CursorIndexOutOfBoundsException e) {
            Toast.makeText(this, "Você precisa fazer a sincronia pelo menos uma vez!", Toast.LENGTH_LONG).show();
            finish();
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        ProspectHelper.clear();
        super.onDestroy();
    }
}
