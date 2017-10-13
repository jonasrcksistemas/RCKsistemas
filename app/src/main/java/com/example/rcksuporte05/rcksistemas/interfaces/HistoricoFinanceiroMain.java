package com.example.rcksuporte05.rcksistemas.interfaces;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterHistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.extras.SlidingTabLayout;

public class HistoricoFinanceiroMain extends AppCompatActivity {

    private SlidingTabLayout stl_tabsHistoricoFinanceiro;
    private ViewPager vp_tabsHistoricoFinanceiro;
    private TabsAdapterHistoricoFinanceiro tabsAdapterHistoricoFinanceiro;
    private HistoricoFinanceiroHelper financeiroHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_financeiro_main);

        DBHelper db = new DBHelper(this);

        financeiroHelper = new HistoricoFinanceiroHelper(this);

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

    @Override
    protected void onDestroy() {
        System.gc();
        financeiroHelper.limparDados();
        super.onDestroy();
    }
}
