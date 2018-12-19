package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCpfCnpjCliente extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txtCpfCnpj)
    TextView txtCpfCnpj;

    @BindView(R.id.edtCpfCnpj)
    EditText edtCpfCnpj;

    @BindView(R.id.btnVerificar)
    Button btnVerificar;

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpf_cnpj_cliente);
        ButterKnife.bind(this);

        db = new DBHelper(this);

        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validaCpfCnpj()) {
                    Cliente cliente = verificaCpfCnpj(edtCpfCnpj.getText().toString());
                    if (cliente != null) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCpfCnpjCliente.this);
                        alert.setTitle("Atenção!");
                        String cpfCnpj;
                        switch (edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "").length()) {
                            case 11:
                                cpfCnpj = "CPF";
                                break;
                            case 14:
                                cpfCnpj = "CNPJ";
                                break;
                            default:
                                cpfCnpj = "CNPJ/CPF";
                                break;
                        }

                        alert.setMessage(cpfCnpj + " já cadastrado. \nCodigo: " + cliente.getId_cadastro() +
                                ".\nNome de Cadastro: " + cliente.getNome_cadastro() + "\n" +
                                "Nome Fantasia: " + cliente.getNome_fantasia());
                        alert.setNeutralButton("OK", null);
                        alert.show();
                    } else {
                        if (verificaConexao())
                            consultaCpfCnpjServidor(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""));
                        else {
                            prosseguirCadastro();
                        }
                    }
                }
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void prosseguirCadastro() {
        Intent intent = new Intent(ActivityCpfCnpjCliente.this, CadastroClienteMain.class);
        intent.putExtra("novo", 1);
        startActivity(intent);

        switch (edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "").length()) {
            case 11:
                ClienteHelper.getCliente().setPessoa_f_j("F");
                break;
            case 14:
                ClienteHelper.getCliente().setPessoa_f_j("J");
                break;
            default:
                Toast.makeText(ActivityCpfCnpjCliente.this, "Tamanho do CNPJ/CPF inválido", Toast.LENGTH_SHORT).show();
                break;

        }
        ClienteHelper.getCliente().setCpf_cnpj(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""));
        finish();
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

    public boolean validaCpfCnpj() {
        if (edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "").length() == 11) {
            if (MascaraUtil.isValidCPF(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""))) {
                edtCpfCnpj.setText(MascaraUtil.mascaraCPF(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "")));
                return true;
            } else {
                edtCpfCnpj.setError("CPF Inválido");
                return false;
            }
        } else if (edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "").length() == 14) {
            if (MascaraUtil.isValidCNPJ(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""))) {
                edtCpfCnpj.setText(MascaraUtil.mascaraCNPJ(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "")));
                return true;
            } else {
                edtCpfCnpj.setError("CNPJ Inválido");
                return false;
            }
        } else {
            edtCpfCnpj.setError("Tamanho do CNPJ/CPF inválido");
            return false;
        }
    }

    public Cliente verificaCpfCnpj(String cpfCnpj) {
        try {
            return db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE CPF_CNPJ = '" + cpfCnpj.replaceAll("[^0-9]", "") + "' AND ID_CADASTRO <> " + ClienteHelper.getCliente().getId_cadastro() + " AND FINALIZADO = 'S';").get(0);
        } catch (CursorIndexOutOfBoundsException e) {
            return null;
        }
    }

    public void consultaCpfCnpjServidor(String cpfCnpj) {
        final ProgressDialog progress = new ProgressDialog(ActivityCpfCnpjCliente.this);
        progress.setTitle("Aguarde");
        progress.setMessage("Consultando a base");
        progress.setCancelable(false);
        progress.show();
        Rotas apiRetrofit = Api.buildRetrofit();

        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        Call<Cliente> call = apiRetrofit.verificaCpfCnpj(cpfCnpj, cabecalho);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                System.out.println("Codigo de retorno da API: " + String.valueOf(response.code()));
                switch (response.code()) {
                    case 210:
                        Cliente cliente = response.body();
                        if (cliente != null) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCpfCnpjCliente.this);
                            alert.setTitle("Atenção!");
                            String cpfCnpj = "";
                            if (cliente.getPessoa_f_j().equals("F"))
                                cpfCnpj = "CPF";
                            else if (cliente.getPessoa_f_j().equals("J"))
                                cpfCnpj = "CNPJ";
                            alert.setMessage(cpfCnpj + " já cadastrado. \nCodigo: " + cliente.getId_cadastro() +
                                    ".\nNome de Cadastro: " + cliente.getNome_cadastro() + "\n" +
                                    "Nome Fantasia: " + cliente.getNome_fantasia());
                            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    prosseguirCadastro();
                                }
                            });
                            progress.dismiss();
                            alert.show();
                        }
                        break;
                    case 220:
                        prosseguirCadastro();
                        progress.dismiss();
                        break;
                    case 500:
                        progress.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCpfCnpjCliente.this);
                        alert.setTitle("Atenção!");
                        alert.setMessage("Houve um erro ao consultar o servidor, se persistir, entre em contato com o pessoal responsável.");
                        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                prosseguirCadastro();
                            }
                        });
                        alert.show();
                        break;
                    default:
                        Toast.makeText(ActivityCpfCnpjCliente.this, "Erro não catalogado: " + response.code(), Toast.LENGTH_SHORT).show();
                        prosseguirCadastro();
                        progress.dismiss();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                progress.dismiss();
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCpfCnpjCliente.this);
                alert.setTitle("Atenção");
                alert.setMessage("Sem conexão com o servidor.");
                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prosseguirCadastro();
                    }
                });
                alert.show();
                t.printStackTrace();
            }
        });
    }

    public boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }
}
