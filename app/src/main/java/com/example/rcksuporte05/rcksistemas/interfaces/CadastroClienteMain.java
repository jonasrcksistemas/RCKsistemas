package com.example.rcksuporte05.rcksistemas.interfaces;

import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.TabsAdapterCliente;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.classes.Municipios;
import com.example.rcksuporte05.rcksistemas.classes.Paises;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.extras.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class CadastroClienteMain extends AppCompatActivity {
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private List<Paises> listaPaises;
    private List<Municipios> listaMunicipios;
    private ArrayList<Cliente> listaVendedor;
    private Bundle bundle;
    private TabsAdapterCliente tabsAdapterCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFrags);
        toolbar.setTitle("Cadastro de Clientes");
        DBHelper db = new DBHelper(this);
        bundle = getIntent().getExtras();

        try {
            listaPaises = db.listaPaises("SELECT * FROM TBL_PAISES;");
            listaMunicipios = db.listaMunicipios("SELECT * FROM TBL_MUNICIPIOS ORDER BY NOME_MUNICIPIO");
            listaVendedor = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE F_VENDEDOR = 'S' ORDER BY NOME_CADASTRO;");
        } catch (CursorIndexOutOfBoundsException e) {
            System.out.println("Erro ao carregar parametros");
        }

        if (Integer.parseInt(bundle.getString("cliente")) > 0) {

            mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
            tabsAdapterCliente = new TabsAdapterCliente(getSupportFragmentManager(), CadastroClienteMain.this, listaPaises, listaMunicipios, listaVendedor, Integer.parseInt(bundle.getString("cliente")), bundle.getStringArray("clienteListar"));
            mViewPager.setAdapter(tabsAdapterCliente);
        } else {
            mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
            tabsAdapterCliente = new TabsAdapterCliente(getSupportFragmentManager(), CadastroClienteMain.this, listaPaises, listaMunicipios, listaVendedor, Integer.parseInt(bundle.getString("cliente")), bundle.getStringArray("clienteListar"));
            mViewPager.setAdapter(tabsAdapterCliente);
        }

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
        System.gc();
        super.onDestroy();
    }
}
