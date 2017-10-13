package com.example.rcksuporte05.rcksistemas.interfaces;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterCliente;
import com.example.rcksuporte05.rcksistemas.extras.SlidingTabLayout;

public class CadastroClienteMain extends AppCompatActivity {
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private TabsAdapterCliente tabsAdapterCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFrags);
        toolbar.setTitle("Cadastro de Clientes");

        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        tabsAdapterCliente = new TabsAdapterCliente(getSupportFragmentManager());
        mViewPager.setAdapter(tabsAdapterCliente);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(Color.WHITE);
        mSlidingTabLayout.setViewPager(mViewPager);

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
                if (mViewPager.getCurrentItem() != 0) {
                    mViewPager.setCurrentItem(0);
                } else {
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        ClienteHelper.setCliente(null);
        System.gc();
        super.onDestroy();
    }
}
