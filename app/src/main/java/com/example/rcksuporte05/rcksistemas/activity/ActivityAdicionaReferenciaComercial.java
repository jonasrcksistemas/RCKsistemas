package com.example.rcksuporte05.rcksistemas.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.ReferenciaComercial;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 06/02/2018.
 */

public class ActivityAdicionaReferenciaComercial extends AppCompatActivity {

    @BindView(R.id.edtFonecedorProspect)
    EditText edtFonecedorProspect;

    @BindView(R.id.edtTelFonecedorProspect)
    EditText edtTelFonecedorProspect;

    @BindView(R.id.toolbarReferenciaComercial)
    Toolbar toolbar;


    MenuItem menuItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_referencia_comercial);
        ButterKnife.bind(this);

        toolbar.setTitle("Adiciona Refêrencia Comercial");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getIntExtra("edicao", 0) == 1) {

        }

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean insereDadosdaFrame() {
        ReferenciaComercial referenciaComercial = new ReferenciaComercial();

        if (edtFonecedorProspect.getText() != null && !edtFonecedorProspect.getText().toString().trim().isEmpty()) {
            referenciaComercial.setNome_fornecedor_referencia(edtFonecedorProspect.getText().toString());
        } else {
            Toast.makeText(ActivityAdicionaReferenciaComercial.this, "O campo Fornecedor é obrigatorio!", Toast.LENGTH_LONG).show();
            edtFonecedorProspect.requestFocus();
            return false;
        }
        if (edtTelFonecedorProspect.getText() != null && !edtTelFonecedorProspect.getText().toString().replaceAll("[^0-9]", "").trim().isEmpty() && edtTelFonecedorProspect.getText().toString().replaceAll("[^0-9]", "").length() >= 8 && edtTelFonecedorProspect.getText().toString().replaceAll("[^0-9]", "").length() <= 11) {
            referenciaComercial.setTelefone(edtTelFonecedorProspect.getText().toString());
        } else {
            Toast.makeText(ActivityAdicionaReferenciaComercial.this, "Campo Telefone invalido ou vazio!", Toast.LENGTH_LONG).show();
            edtTelFonecedorProspect.requestFocus();
            return false;
        }

        DBHelper db = new DBHelper(this);
        referenciaComercial.setId_referencia_comercial(String.valueOf(db.contagem("SELECT COUNT(*) FROM TBL_REFERENCIA_COMERCIAL;") + 1));

        db.atualizarReferenciaComercial(referenciaComercial, "0");

        if (getIntent().getIntExtra("cliente", 0) == 1)
            ClienteHelper.getCliente().getReferenciasComerciais().add(referenciaComercial);
        else
            ProspectHelper.getProspect().getReferenciasComerciais().add(referenciaComercial);
        return true;
    }
//
//    private void injetaDadosNaTela(){
//        if(ProspectHelper.getProspect() != null && ProspectHelper.getProspect().getReferenciasComerciais().size() > 0){
//            int posicao = getIntent().getIntExtra("position", 0);
//
//            if(ProspectHelper.getProspect().getReferenciasComerciais().get(posicao) != null &&){
//
//            }
//
//        }
//    }


}
