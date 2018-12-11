package com.example.rcksuporte05.rcksistemas.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Contato;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 05/02/2018.
 */

public class ActivityAdicionaContato extends AppCompatActivity {

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

    @BindView(R.id.btnSalvar)
    Button btnSalvar;

    ArrayAdapter<String> tipoArray;
    List<String> tiposTelefone;
    private Contato contato = new Contato();

    @OnClick(R.id.btnSalvar)
    public void salvar() {
        if (insereDadosdaFrame()) {
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_contato);
        ButterKnife.bind(this);
        toolbar.setTitle("Contato");
        tiposTelefone = new ArrayList<>();
        tiposTelefone.add("Celular");
        tiposTelefone.add("Telefone Fixo");
        tipoArray = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tiposTelefone);
        spTipoTelefone.setAdapter(tipoArray);

        if (getIntent().getIntExtra("visualizacao", 0) == 1) {
            if (getIntent().getIntExtra("contato", -1) > -1) {
                injetaDadosNaTela(getIntent().getIntExtra("contato", 0));
            }
            edtResponsavelProspect.setFocusable(false);
            edtFuncaoResponsavelProspect.setFocusable(false);
            edtTelefoneProspect.setFocusable(false);
            edtEmailProspect.setFocusable(false);
            spTipoTelefone.setEnabled(false);
            btnSalvar.setVisibility(View.GONE);

        } else if (getIntent().getIntExtra("contato", -1) > -1) {
            injetaDadosNaTela(getIntent().getIntExtra("contato", 0));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void injetaDadosNaTela(int position) {

        if (getIntent().getIntExtra("cliente", 0) == 1)
            contato = ClienteHelper.getCliente().getListaContato().get(position);
        else
            contato = ProspectHelper.getProspect().getListaContato().get(position);

        edtResponsavelProspect.setText(contato.getPessoa_contato());
        edtFuncaoResponsavelProspect.setText(contato.getFuncao());
        edtTelefoneProspect.setText(contato.getNumero_telefone());
        edtEmailProspect.setText(contato.getEmail());
        switch (contato.getTipo_telefone()) {
            case "Celular":
                spTipoTelefone.setSelection(0);
                break;
            case "Telefone Fixo":
                spTipoTelefone.setSelection(1);
                break;
        }
    }

    public boolean insereDadosdaFrame() {

        if (edtResponsavelProspect.getText() != null && !edtResponsavelProspect.getText().toString().trim().isEmpty()) {
            contato.setPessoa_contato(edtResponsavelProspect.getText().toString());
        } else {
            Toast.makeText(this, "O campo Responsavel é obrigatorio!", Toast.LENGTH_SHORT).show();
            edtResponsavelProspect.requestFocus();
            return false;
        }
        if (edtFuncaoResponsavelProspect.getText() != null && !edtFuncaoResponsavelProspect.getText().toString().trim().isEmpty()) {
            contato.setFuncao(edtFuncaoResponsavelProspect.getText().toString());
        } else {
            Toast.makeText(this, "O campo Funcao Responsavel é obrigatorio!", Toast.LENGTH_SHORT).show();
            edtFuncaoResponsavelProspect.requestFocus();
            return false;
        }
        if (spTipoTelefone.getSelectedItemPosition() >= 0) {
            contato.setTipo_telefone(tiposTelefone.get(spTipoTelefone.getSelectedItemPosition()));
        } else {
            Toast.makeText(this, "O campo Tipo Telefone é obrigatorio!", Toast.LENGTH_SHORT).show();
            spTipoTelefone.requestFocus();
            return false;
        }
        if (edtTelefoneProspect.getText() != null && !edtTelefoneProspect.getText().toString().trim().isEmpty()) {
            contato.setNumero_telefone(edtTelefoneProspect.getText().toString());
        } else {
            Toast.makeText(this, "O campo Telefone é obrigatorio!", Toast.LENGTH_SHORT).show();
            edtTelefoneProspect.requestFocus();
            return false;
        }
        if (edtEmailProspect.getText() != null && !edtEmailProspect.getText().toString().trim().isEmpty()) {
            contato.setEmail(edtEmailProspect.getText().toString());
        }

        contato.setAtivo("S");

        DBHelper db = new DBHelper(this);
        if (contato.getId_contato() != null) {
            if (getIntent().getIntExtra("cliente", 0) == 1) {
                contato.setIdEntidade(1);
                ClienteHelper.getCliente().getListaContato().set(getIntent().getIntExtra("contato", 0), contato);
            } else {
                contato.setIdEntidade(10);
                ProspectHelper.getProspect().getListaContato().set(getIntent().getIntExtra("contato", 0), contato);
            }

            db.atualizarContato(contato, String.valueOf(ClienteHelper.getCliente().getId_cadastro()));
        } else {
            contato.setId_contato(String.valueOf(db.contagem("SELECT MAX(ID_CONTATO) FROM TBL_CADASTRO_CONTATO;") + 1));

            if (getIntent().getIntExtra("cliente", 0) == 1) {
                contato.setIdEntidade(1);
                ClienteHelper.getCliente().getListaContato().add(contato);
            } else {
                contato.setIdEntidade(10);
                ProspectHelper.getProspect().getListaContato().add(contato);
            }

            db.atualizarContato(contato, String.valueOf(ClienteHelper.getCliente().getId_cadastro()));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
