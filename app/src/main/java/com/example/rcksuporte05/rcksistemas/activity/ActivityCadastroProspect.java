package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterProspect;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.util.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 25/01/2018.
 */

public class ActivityCadastroProspect extends AppCompatActivity {
    TabsAdapterProspect tabsAdapterProspect;
    ProgressDialog progress;

    private DBHelper db;

    @BindView(R.id.toolbarFragsProspect)
    Toolbar toolbar;

    @BindView(R.id.stl_tabs_Prospect)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.vp_tabs_prospect)
    public ViewPager mViewPager;

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
                                                           if(ProspectHelper.getCadastroProspectGeral() != null) {
                                                               ProspectHelper.getCadastroProspectGeral().inserirDadosDaFrame();
                                                           }
                                                           break;
                                                       case 1:
                                                           if(ProspectHelper.getCadastroProspectEndereco() != null){
                                                               ProspectHelper.getCadastroProspectEndereco().inserirDadosDaFrame();
                                                           }
                                                           break;
                                                       case 2:
                                                           break;
                                                       case 3:
                                                           if(ProspectHelper.getCadastroProspectSegmentos() != null){
                                                               ProspectHelper.getCadastroProspectSegmentos().insereDadosDaFrame();
                                                           }
                                                           break;
                                                       case 4:
                                                           if(ProspectHelper.getCadastroProspectMotivos() != null){
                                                               ProspectHelper.getCadastroProspectMotivos().insereDadosDaFrame();
                                                           }
                                                           break;
                                                       case 5:
                                                           if(ProspectHelper.getCadastroProspectObservacoesComerciais() != null){
                                                               ProspectHelper.getCadastroProspectObservacoesComerciais().insereDadosDaFrame();
                                                           }
                                                           break;
                                                       case 6:
                                                           if (ProspectHelper.getCadastroProspectFotoSalvar() != null) {
                                                               ProspectHelper.getCadastroProspectFotoSalvar().insereDadosDaFrame();
                                                           }
                                                           break;
                                                   }
                                               }

                                               @Override
                                               public void onPageSelected(int position) {
                                                   switch (position) {
                                                       case 0:
                                                           if(ProspectHelper.getCadastroProspectGeral() != null) {
                                                               ProspectHelper.getCadastroProspectGeral().inserirDadosDaFrame();
                                                           }
                                                           break;
                                                       case 1:
                                                           if(ProspectHelper.getCadastroProspectEndereco() != null){
                                                               ProspectHelper.getCadastroProspectEndereco().inserirDadosDaFrame();
                                                           }
                                                           break;
                                                       case 2:
                                                           break;
                                                       case 3:
                                                           if(ProspectHelper.getCadastroProspectSegmentos() != null){
                                                               ProspectHelper.getCadastroProspectSegmentos().insereDadosDaFrame();
                                                           }
                                                           break;
                                                       case 4:
                                                           if(ProspectHelper.getCadastroProspectMotivos() != null){
                                                               ProspectHelper.getCadastroProspectMotivos().insereDadosDaFrame();
                                                           }
                                                           break;
                                                       case 5:
                                                           if(ProspectHelper.getCadastroProspectObservacoesComerciais() != null){
                                                               ProspectHelper.getCadastroProspectObservacoesComerciais().insereDadosDaFrame();
                                                           }
                                                           break;
                                                       case 6:
                                                           if (ProspectHelper.getCadastroProspectFotoSalvar() != null) {
                                                               ProspectHelper.getCadastroProspectFotoSalvar().insereDadosDaFrame();
                                                           }
                                                           break;
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

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProspectHelper.setActivityMain(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mViewPager.getCurrentItem() != 0) {
                    mViewPager.setCurrentItem(0);
                } else {
                    finish();
                }
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(0);
        } else {
            super.onBackPressed();
        }
    }

    public void buscarSegmentos() {
       try {ProspectHelper.setSegmentos(db.listaSegmento());}catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
       }
    }

    public void buscarMotivos() {
      try {ProspectHelper.setMotivos(db.listaMotivoNaoCadastramento());} catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void buscarPais(){
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
        System.gc();
        super.onDestroy();
    }
}
