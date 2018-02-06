package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterProspect;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.classes.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.extras.SlidingTabLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    ViewPager mViewPager;


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
                                                           break;
                                                   }
                                               }

                                               @Override
                                               public void onPageSelected(int position) {

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
       ProspectHelper.setSegmentos(db.listaSegmento());
    }

    public void buscarMotivos() {
        Rotas apiRotas = Api.buildRetrofit();
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<List<MotivoNaoCadastramento>> call = apiRotas.buscarTodosMotivos(cabecalho);

        call.enqueue(new Callback<List<MotivoNaoCadastramento>>() {
            @Override
            public void onResponse(Call<List<MotivoNaoCadastramento>> call, Response<List<MotivoNaoCadastramento>> response) {
                if (response.code() == 200)
                    ProspectHelper.setMotivos(response.body());

            }

            @Override
            public void onFailure(Call<List<MotivoNaoCadastramento>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void buscarPais(){
        ProspectHelper.setPaises(db.listaPaises());
    }

}
