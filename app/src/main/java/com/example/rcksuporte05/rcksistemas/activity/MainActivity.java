package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnEntrar;
    private Button btnAtualizarUsuarios;
    private ProgressDialog progress;
    private List<Usuario> usuarioList = new ArrayList<>();
    private UsuarioBO usuarioBO = new UsuarioBO();
    private DBHelper db;
    private Button informacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(MainActivity.this);

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnAtualizarUsuarios = (Button) findViewById(R.id.btnAtualizar);
        informacao = (Button) findViewById(R.id.informacao);

        btnEntrar.setOnClickListener(this);
        btnAtualizarUsuarios.setOnClickListener(this);
        informacao.setOnClickListener(this);
        try {

            UsuarioHelper.setUsuario(usuarioBO.buscarUsuarioLogin(this));
            Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
            startActivity(intent);
            finish();
        } catch (/*android.database.CursorIndexOutOfBoundsException*/ Exception e) {
            if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN WHERE LOGADO = 'S'") != 0) {
                Toast.makeText(getApplicationContext(), "Usuario alterado", Toast.LENGTH_LONG).show();
                db.alterar("DELETE FROM TBL_LOGIN");
            }
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnEntrar) {
            if (edtLogin.getText().toString().equalsIgnoreCase("")) {
                edtLogin.setError("Por favor insira seu usuario");
                edtLogin.setText("");
                edtLogin.requestFocus();
            } else {
                if (edtSenha.getText().toString().equalsIgnoreCase("")) {
                    edtSenha.setError("Por favor informe sua senha");
                    edtSenha.setText("");
                    edtSenha.requestFocus();
                } else {
                    logar(0);
                }
            }
        } else if (view == btnAtualizarUsuarios) {
            getUsuarios(true);
        } else if (view == informacao) {
            Intent intent = new Intent(MainActivity.this, infoDialog.class);
            startActivity(intent);
        }
    }

    public void logar(final int alterado) {
        try {
            if (alterado != 1) {
                Usuario usuario = db.listaUsuario("SELECT * FROM TBL_WEB_USUARIO WHERE LOGIN = '" + edtLogin.getText().toString() + "'").get(0);
                loginNaApi(usuario, alterado);
            }
            getUsuarios(false);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            edtLogin.setError("Usuario inexistente!");
            edtLogin.setText("");
            edtLogin.requestFocus();
            edtSenha.setText("");
        }
    }


    public void getUsuarios(final Boolean mostrar) {
        progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("Carregando Usuarios");
        progress.setTitle("Aguarde");
        progress.show();

        Rotas apiRotas = Api.buildRetrofit();

        Call<List<Usuario>> call = apiRotas.getUsuarios();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Configurações inciais atualizadas!");
                alert.setNeutralButton("OK", null);
                usuarioList = response.body();
                if (!usuarioBO.sincronizaNobanco(usuarioList, MainActivity.this))
                    Toast.makeText(MainActivity.this, "Houve um erro ao salvar os usuarios", Toast.LENGTH_LONG).show();
                progress.dismiss();
                if (mostrar)
                    alert.show();
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, "Não foi possivel sincronizar com o servidor, por favor verifique sua conexão", Toast.LENGTH_LONG).show();
                t.printStackTrace();
                edtLogin.requestFocus();
            }
        });
    }

    public void loginNaApi(final Usuario usuario, final int alterado) {
        final ProgressDialog progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("Aguarde enquanto seus dados são validados");
        progress.setTitle("AGUARDE");
        progress.show();

        Rotas apiRotas = Api.buildRetrofit();

        String idAndroit = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<Usuario> call = apiRotas.login(idAndroit, usuario.getId_usuario(), edtLogin.getText().toString(), edtSenha.getText().toString());

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuario1 = response.body();
                switch (response.code()) {
                    case 200:
                        Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                        usuario1.setLogado("S");
                        if (usuario1.getIdEmpresaMultiDevice() != null && Integer.parseInt(usuario1.getIdEmpresaMultiDevice()) > 0) {
                            if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN") > 0) {
                                db.atualizarTBL_LOGIN(usuario1);
                            } else {
                                db.insertTBL_LOGIN(usuario1);
                            }
                            intent.putExtra("alterado", alterado);
                            db.close();
                            System.gc();
                            UsuarioHelper.setUsuario(usuario1);
                            progress.dismiss();
                            startActivity(intent);
                            finish();
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setTitle("Atenção");
                            alert.setMessage("O usuario em questão não tem o codigo da empresa informado em seu cadastro, é necessário a correção no cadastro deste usuário!\n    " +
                                    "Em caso de duvida, favor entrar em contato com a RCK sistemas!");
                            alert.setCancelable(false);
                            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    edtLogin.setText("");
                                    edtSenha.setText("");
                                    edtLogin.requestFocus();
                                }
                            });
                            progress.dismiss();
                            alert.show();
                        }
                        break;
                    case 500:
                        edtSenha.setError("Senha incorreta");
                        edtSenha.setText("");
                        edtSenha.requestFocus();
                        progress.dismiss();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                progress.dismiss();
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Você precisa estar conectado a internet para poder logar!");
                alert.setTitle("Atenção!");
                alert.setNeutralButton("OK", null);
                alert.show();
                edtSenha.setText("");
                edtSenha.requestFocus();
            }
        });
    }

    @Override
    protected void onResume() {
        getUsuarios(true);
        super.onResume();
    }
}

