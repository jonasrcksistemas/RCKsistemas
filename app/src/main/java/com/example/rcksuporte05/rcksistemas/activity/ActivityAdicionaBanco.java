package com.example.rcksuporte05.rcksistemas.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Banco;
import com.example.rcksuporte05.rcksistemas.model.ReferenciaBancaria;

import java.util.List;

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
    ArrayAdapter<Banco> adapter;

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

        if (spBancoProspect.getSelectedItemPosition() >= 0) {
            bancos.setNome_banco(adapter.getItem(spBancoProspect.getSelectedItemPosition()).getNome_banco());
            bancos.setCodigo_febraban(adapter.getItem(spBancoProspect.getSelectedItemPosition()).getCodigo_febraban());
        }
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

        DBHelper db = new DBHelper(this);
        bancos.setId_referencia_bancaria(String.valueOf(db.contagem("SELECT COUNT(*) FROM TBL_REFERENCIA_BANCARIA;") + 1));

        db.atualizarRefernciaBancaria(bancos, "0");

        if (getIntent().getIntExtra("cliente", 0) == 1)
            ClienteHelper.getCliente().getReferenciasBancarias().add(bancos);
        else
            ProspectHelper.getProspect().getReferenciasBancarias().add(bancos);
        return true;
    }

    private void preencheBancos() {
        List<Banco> listaBanco = db.listaBancos();
        if (listaBanco.size() > 0) {
            adapter = new ArrayAdapter<Banco>(ActivityAdicionaBanco.this, android.R.layout.simple_spinner_dropdown_item, listaBanco);
            spBancoProspect.setAdapter(adapter);
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Atenção");
            alert.setMessage("Não há bancos salvos na base, entre em contato com a empresa para esclarecer a situação");
            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
    }
}
