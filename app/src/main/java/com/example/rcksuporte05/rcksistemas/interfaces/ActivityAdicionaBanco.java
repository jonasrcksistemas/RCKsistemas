package com.example.rcksuporte05.rcksistemas.interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Banco;
import com.example.rcksuporte05.rcksistemas.classes.ReferenciaBancaria;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 05/02/2018.
 */

public class ActivityAdicionaBanco extends AppCompatActivity {


    @BindView(R.id.spBancoProspect)
    Spinner spBancoProspect;

    @BindView(R.id.edtContaCorrenteProspect)
    EditText edtContaCorrenteProspect;

    @BindView(R.id.edtAgenciaProspect)
    EditText edtAgenciaProspect;

    @BindView(R.id.toolbarBancoProspect)
    Toolbar toolbarBancoProspect;

    MenuItem menuItem;
    DBHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_banco);
        ButterKnife.bind(this);
        db = new DBHelper(this);
        toolbarBancoProspect.setTitle("Banco");
        setSupportActionBar(toolbarBancoProspect);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preencheBancos();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (getIntent().getIntExtra("vizualizacao", 0) != 1) {
            getMenuInflater().inflate(R.menu.menu_salvar, menu);
            menuItem = menu.findItem(R.id.menu_salvar);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_salvar:
                if (insereDadosdaFrame()) {
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean insereDadosdaFrame() {
        ReferenciaBancaria bancos = new ReferenciaBancaria();

//        if (edtBancoProspect.getText() != null) {
//           bancos.set  edtBancoProspect.getText().toString());
//        }
        if (edtContaCorrenteProspect.getText() != null && !edtContaCorrenteProspect.getText().toString().trim().isEmpty()) {
            bancos.setConta_corrente(edtContaCorrenteProspect.getText().toString());
        } else {
            Toast.makeText(this, "O campo Conta Corrente é obrigatorio!", Toast.LENGTH_SHORT).show();
            edtContaCorrenteProspect.requestFocus();
            return false;
        }
        if (edtAgenciaProspect.getText() != null && !edtAgenciaProspect.getText().toString().trim().isEmpty()) {
            bancos.setAgencia(edtAgenciaProspect.getText().toString());
        } else {
            Toast.makeText(this, "O campo Agencia é obrigatorio!", Toast.LENGTH_SHORT).show();
            edtAgenciaProspect.requestFocus();
            return false;
        }

        return true;
    }

    private void preencheBancos() {
        ArrayAdapter<Banco> adapter = new ArrayAdapter<Banco>(ActivityAdicionaBanco.this, android.R.layout.simple_spinner_dropdown_item, db.listaBancos());
        spBancoProspect.setAdapter(adapter);
    }
}
