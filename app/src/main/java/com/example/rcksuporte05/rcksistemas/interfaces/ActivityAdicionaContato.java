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

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Contato;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 05/02/2018.
 */

public class ActivityAdicionaContato extends AppCompatActivity{

    @BindView(R.id.edtResponsavelProspect)
    EditText edtResponsavelProspect;

    @BindView(R.id.edtFuncaoResponsavelProspect)
    EditText edtFuncaoResponsavelProspect;

    @BindView(R.id.edtTelefoneProspect)
    EditText edtTelefoneProspect;

    @BindView(R.id.edtEmailProspect)
    EditText edtEmailProspect;

    @BindView(R.id.spTipoTelefone)
    Spinner spTipoTelefone;

    @BindView(R.id.toolbarContatoProspect)
    Toolbar toolbar;

    MenuItem menuItem;
    ArrayAdapter<String> tipoArray;
    List<String> tiposTelefone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_contato);
        ButterKnife.bind(this);
        toolbar.setTitle("Contato");
        tiposTelefone = new ArrayList<>();
        tiposTelefone.add("Celular");
        tiposTelefone.add("Telefone Fixo");
        tipoArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tiposTelefone);
        spTipoTelefone.setAdapter(tipoArray);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public boolean insereDadosdaFrame(){
        Contato contato = new Contato();

        if(edtResponsavelProspect.getText() != null && !edtResponsavelProspect.getText().toString().trim().isEmpty()){
            contato.setPessoa_contato(edtResponsavelProspect.getText().toString());
        }else {
            Toast.makeText(this, "O campo Responsavel é obrigatorio!", Toast.LENGTH_SHORT).show();
            edtResponsavelProspect.requestFocus();
            return false;
        }
        if(edtFuncaoResponsavelProspect.getText() != null && !edtFuncaoResponsavelProspect.getText().toString().trim().isEmpty()){
            contato.setFuncao(edtFuncaoResponsavelProspect.getText().toString());
        }else {
            Toast.makeText(this, "O campo Funcao Responsavel é obrigatorio!", Toast.LENGTH_SHORT).show();
            edtFuncaoResponsavelProspect.requestFocus();
            return false;
        }
        if(spTipoTelefone.getSelectedItemPosition() >= 0){
            contato.setTipo_telefone(tiposTelefone.get(spTipoTelefone.getSelectedItemPosition()));
        }else {
            Toast.makeText(this, "O campo Tipo Telefone é obrigatorio!", Toast.LENGTH_SHORT).show();
            spTipoTelefone.requestFocus();
            return false;
        }
        if(edtTelefoneProspect.getText() != null && !edtTelefoneProspect.getText().toString().trim().isEmpty()){
            contato.setNumero_telefone(edtTelefoneProspect.getText().toString());
        }else {
            Toast.makeText(this, "O campo Telefone é obrigatorio!", Toast.LENGTH_SHORT).show();
            edtTelefoneProspect.requestFocus();
            return false;
        }
        if(edtEmailProspect.getText() != null && !edtEmailProspect.getText().toString().trim().isEmpty()){
            contato.setEmail(edtEmailProspect.getText().toString());
        }

        ProspectHelper.getProspect().getListaContato().add(contato);
        return true;
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
                    if(insereDadosdaFrame()){
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
}
