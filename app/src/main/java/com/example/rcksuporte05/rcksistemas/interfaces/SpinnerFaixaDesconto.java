package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 26/10/2017.
 */

public class SpinnerFaixaDesconto extends Activity {

    @BindView(R.id.spFaixaPadrao)
    Spinner spinner;

    @BindView(R.id.btnConfirmar)
    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_faixa_desconto);
        ButterKnife.bind(this);
        DBHelper db = new DBHelper(this);

        ArrayAdapter<TabelaPrecoItem> adapterFaixaPadrao = new ArrayAdapter(this, R.layout.drop_down_spinner, db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE PONTOS_PREMIACAO > 0;"));
        spinner.setAdapter(adapterFaixaPadrao);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PedidoHelper.setPositionFaixPadrao(spinner.getSelectedItemPosition());
                Intent intent = new Intent(SpinnerFaixaDesconto.this, ActivityProduto.class);
                intent.putExtra("acao", 1);
                startActivity(intent);
                finish();
            }
        });

    }
}
