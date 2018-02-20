package com.example.rcksuporte05.rcksistemas.interfaces;

import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaProspectEnviadoAdapter;
import com.example.rcksuporte05.rcksistemas.classes.Prospect;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class ActivityListaProspectEnviado extends AppCompatActivity implements ListaProspectEnviadoAdapter.ProspectEnviadoListener{

    @BindView(R.id.recyclerProspectsEnviado)
    RecyclerView recyclerProspectsEnviado;

    ListaProspectEnviadoAdapter listaProspectEnviadoAdapter;
    private List<Prospect> listaProspect;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_prospect_enviado);
        ButterKnife.bind(this);

        recyclerProspectsEnviado.setLayoutManager(new LinearLayoutManager(this));
        recyclerProspectsEnviado.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        try {
            DBHelper db = new DBHelper(this);
            listaProspect = db.listaProspect(Prospect.PROSPECT_ENVIADO);
            preencheLista(listaProspect);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            recyclerProspectsEnviado.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    protected void onResume() {
        try {
            DBHelper db = new DBHelper(this);
            listaProspect = db.listaProspect(Prospect.PROSPECT_ENVIADO);
            preencheLista(listaProspect);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
//            edtTotalProspect.setText("0: Prospects Listados");
        }
        super.onResume();
    }

    private void preencheLista(List<Prospect> listaProspect) {
        listaProspectEnviadoAdapter = new ListaProspectEnviadoAdapter(listaProspect,this);
        recyclerProspectsEnviado.setAdapter(listaProspectEnviadoAdapter);
        recyclerProspectsEnviado.setVisibility(View.VISIBLE);
        listaProspectEnviadoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {

    }
}
