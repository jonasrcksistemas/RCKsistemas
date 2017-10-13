package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterHistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiroPendente;
import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiroQuitado;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.extras.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricoFinanceiroMain extends AppCompatActivity {

    private DBHelper db;
    private List<HistoricoFinanceiroPendente> listaVencidas = new ArrayList<>();
    private List<HistoricoFinanceiroPendente> listaVencer = new ArrayList<>();
    private List<HistoricoFinanceiroQuitado> listaQuitado;
    private SlidingTabLayout stl_tabsHistoricoFinanceiro;
    private ViewPager vp_tabsHistoricoFinanceiro;
    private TabsAdapterHistoricoFinanceiro tabsAdapterHistoricoFinanceiro;
    private HistoricoFinanceiroHelper financeiroHelper;
    private ProgressDialog progress;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_financeiro_main);

        db = new DBHelper(this);

        carregarHistoricoFinanceiro(getIntent().getIntExtra("idCliente", 0));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        tabsAdapterHistoricoFinanceiro.saveState();
    }

    public void carregarHistoricoFinanceiro(int idCliente) {
        progress = new ProgressDialog(HistoricoFinanceiroMain.this);
        progress.setMessage("Carregando historico financeiro!");
        progress.setCancelable(false);
        progress.show();

        Rotas apiRotas = Api.buildRetrofit();

        Call<HistoricoFinanceiro> call = apiRotas.getHistoricoFinanceiro(idCliente);

        call.enqueue(new Callback<HistoricoFinanceiro>() {
            @Override
            public void onResponse(Call<HistoricoFinanceiro> call, Response<HistoricoFinanceiro> response) {

                for (HistoricoFinanceiroPendente hp : response.body().getListaPendente()) {
                    if (Integer.parseInt(hp.getDias_atrazo()) > 0) {
                        listaVencidas.add(hp);
                    } else {
                        listaVencer.add(hp);
                    }
                }

                listaQuitado = response.body().getListaQuitado();

                financeiroHelper = new HistoricoFinanceiroHelper(listaVencidas, listaVencer, listaQuitado);

                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFragsHistoricoFinanceiro);
                vp_tabsHistoricoFinanceiro = (ViewPager) findViewById(R.id.vp_tabsHistoricoFinanceiro);
                tabsAdapterHistoricoFinanceiro = new TabsAdapterHistoricoFinanceiro(getSupportFragmentManager(), HistoricoFinanceiroMain.this, getIntent().getIntExtra("idCliente", 0));
                vp_tabsHistoricoFinanceiro.setAdapter(tabsAdapterHistoricoFinanceiro);

                stl_tabsHistoricoFinanceiro = (SlidingTabLayout) findViewById(R.id.stl_tabsHistoricoFinanceiro);
                stl_tabsHistoricoFinanceiro.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                stl_tabsHistoricoFinanceiro.setSelectedIndicatorColors(Color.WHITE);
                stl_tabsHistoricoFinanceiro.setViewPager(vp_tabsHistoricoFinanceiro);

                toolbar.setTitle(db.consulta("SELECT NOME_CADASTRO FROM TBL_CADASTRO WHERE ID_CADASTRO = " + getIntent().getIntExtra("idCliente", 0) + ";", "NOME_CADASTRO").trim());
                toolbar.setSubtitle("Historico Financeiro");

                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<HistoricoFinanceiro> call, Throwable t) {
                alert = new AlertDialog.Builder(HistoricoFinanceiroMain.this);
                alert.setTitle("Atenção!");
                alert.setMessage("Não foi possivel carregar relatorio!\n        Verifique sua conexão com a internet!");
                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alert.show();
                progress.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        System.gc();
        financeiroHelper.limparDados();
        super.onDestroy();
    }
}
