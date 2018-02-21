package com.example.rcksuporte05.rcksistemas.interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class ActivityHistoricoVisitaProspect extends AppCompatActivity {
    @BindView(R.id.recycleHistoricoVisita)
    RecyclerView recycleHistoricoVisita;

    @BindView(R.id.toolbarVisita)
    Toolbar toolbarVisita;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_visita);
        ButterKnife.bind(this);

        recycleHistoricoVisita.setLayoutManager(new LinearLayoutManager(this));
        recycleHistoricoVisita.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));


        setSupportActionBar(toolbarVisita);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

}
