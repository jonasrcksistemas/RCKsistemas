package com.example.rcksuporte05.rcksistemas.interfaces;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.Helper.VisitaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.VisitaAdapter;
import com.example.rcksuporte05.rcksistemas.classes.VisitaProspect;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class ActivityHistoricoVisitaProspect extends AppCompatActivity implements VisitaAdapter.VisitaListener{
    @BindView(R.id.recycleHistoricoVisita)
    RecyclerView recycleHistoricoVisita;

    @BindView(R.id.toolbarVisita)
    Toolbar toolbarVisita;

    @BindView(R.id.edtTotalVisita)
    TextView edtTotalVisita;


    DBHelper db;
    List<VisitaProspect> visitas;
    VisitaAdapter visitaAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_visita);
        ButterKnife.bind(this);

        db = new DBHelper(this);

        recycleHistoricoVisita.setLayoutManager(new LinearLayoutManager(this));
        recycleHistoricoVisita.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        setSupportActionBar(toolbarVisita);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @OnClick(R.id.btnAddVisita)
    public void abrirTela(){
        Intent intent = new Intent(this, ActivityVisita.class);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {

        try {
            visitas = db.listaVisitaPorProspect(VisitaHelper.getProspect());
            preencheLista();
        }catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }

        super.onResume();
    }

    public void preencheLista(){
        visitaAdapter = new VisitaAdapter(visitas,this);
        recycleHistoricoVisita.setAdapter(visitaAdapter);
        visitaAdapter.notifyDataSetChanged();
        if(visitaAdapter.contaPendentes() > 0){
            edtTotalVisita.setText("Visitas: "+visitaAdapter.getItemCount()+" Pendentes: "+visitaAdapter.contaPendentes());
        }else
            edtTotalVisita.setText("Visitas: "+visitaAdapter.getItemCount());
    }

    @Override
    protected void onDestroy() {
        VisitaHelper.limpaVisitaHelper();
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onClick(int position) {

    }
}
