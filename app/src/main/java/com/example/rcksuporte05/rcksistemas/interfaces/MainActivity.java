package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.bo.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnEntrar;
    private Button btnFechar;
    private Bundle bundleUsuario;
    private MenuItem sincroniza;
    private Toolbar tb_main;
    private Thread a = new Thread();
    private ProgressDialog progress;
    private List<Usuario> usuarioList = new ArrayList<>();
    private UsuarioBO usuarioBO = new UsuarioBO();
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(MainActivity.this);

        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnFechar = (Button) findViewById(R.id.btnFechar);

        tb_main = (Toolbar) findViewById(R.id.tb_main);
        tb_main.setTitle("Bem vindo ao " + getString(R.string.app_name));
        tb_main.setSubtitle("Login");
        tb_main.setLogo(R.mipmap.ic_launcher);

        setSupportActionBar(tb_main);

        btnEntrar.setOnClickListener(this);
        btnFechar.setOnClickListener(this);

        getUsuarios();
        DBHelper db = new DBHelper(this);
        try {
            if (db.consulta("SELECT SENHA FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consulta("SELECT LOGIN FROM TBL_LOGIN WHERE LOGADO = 'S'", "LOGIN") + "';", "SENHA").equals(db.consulta("SELECT SENHA FROM TBL_LOGIN", "SENHA"))) {
                Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                intent.putExtra("usuario", db.consulta("SELECT NOME_USUARIO FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consulta("SELECT LOGIN FROM TBL_LOGIN", "LOGIN") + "';", "NOME_USUARIO"));
                Usuario usuario = db.listaUsuario("SELECT * FROM TBL_WEB_USUARIO WHERE LOGIN = '" + edtLogin.getText().toString() + "'").get(0);
                UsuarioHelper.setUsuario(usuario);
                startActivity(intent);
                db.close();
                finish();
            }
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN") != 0) {
                Toast.makeText(getApplicationContext(), "Usuario alterado", Toast.LENGTH_LONG).show();
                db.alterar("DELETE FROM TBL_LOGIN");
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnEntrar) {
            if (edtLogin.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(this, "Por favor insira seu usuario", Toast.LENGTH_SHORT).show();
            } else {
                final DBHelper db = new DBHelper(this);
                try {
                    if (edtSenha.getText().toString().equals(db.consulta("SELECT SENHA FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND LOGIN = '" + edtLogin.getText().toString() + "';", "SENHA").trim())) {
                        try {
                            if (!edtLogin.getText().toString().equals(db.consulta("SELECT LOGIN FROM TBL_LOGIN;", "LOGIN").trim())) {
//                                Toast.makeText(this, "Usuario diferente", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder alertUsuario = new AlertDialog.Builder(MainActivity.this);
                                alertUsuario.setMessage("Novo usuario detectado, o banco de dados será limpo e será necessário novo sincronia. \n   Deseja continuar?");
                                alertUsuario.setTitle("Atenção!");
                                alertUsuario.setNegativeButton("NÃO", null);
                                alertUsuario.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.alterar("DELETE FROM TBL_CADASTRO");
                                        logar(1);
                                    }
                                });
                                alertUsuario.show();
                            } else {
                                logar(0);
                            }
                        } catch (CursorIndexOutOfBoundsException e) {
                            if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN") > 0) {
                                AlertDialog.Builder alertUsuario = new AlertDialog.Builder(MainActivity.this);
                                alertUsuario.setMessage("Novo usuario detectado, o banco de dados será limpo e será necessário novo sincronia. \n   Deseja continuar?");
                                alertUsuario.setTitle("Atenção!");
                                alertUsuario.setNegativeButton("NÃO", null);
                                alertUsuario.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.alterar("DELETE FROM TBL_CADASTRO");
                                        logar(1);
                                    }
                                });
                                alertUsuario.show();
                            } else {
                                logar(0);
                            }
                        }


                    } else {
                        Toast.makeText(MainActivity.this, "Senha incorreta", Toast.LENGTH_SHORT).show();
                        edtSenha.setText("");
                        edtSenha.requestFocus();
                    }
                } catch (CursorIndexOutOfBoundsException e) {
                    Toast.makeText(this, "Usuario inexistente", Toast.LENGTH_SHORT).show();
                    edtLogin.setText("");
                    edtSenha.setText("");
                    edtLogin.requestFocus();
                }
            }
        } else if (view == btnFechar) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Deseja realmente sair da aplicação?");
            alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                    startActivity(intent);
                    finish();
                }
            });
            alert.setNegativeButton("NÃO", null);
            alert.show();
        }
    }

    public void logar(final int alterado) {
        try {
            Usuario usuario = db.listaUsuario("SELECT * FROM TBL_WEB_USUARIO WHERE LOGIN = '" + edtLogin.getText().toString() + "'").get(0);
            setAndroidId(usuario, alterado);
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Você precisa estar conectado a internet para poder logar!", Toast.LENGTH_SHORT).show();
                    edtSenha.setText("");
                    edtSenha.requestFocus();
                }
            });
        }
    }


    public void getUsuarios() {
        progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("Carregando Usuarios");
        progress.setTitle("Aguarde");
        progress.show();

        Rotas apiRotas = Api.buildRetrofit();


        Call<List<Usuario>> call = apiRotas.getUsuarios();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                usuarioList = response.body();
                if (!usuarioBO.sincronizaNobanco(usuarioList, MainActivity.this))
                    Toast.makeText(MainActivity.this, "Houve um erro ao salvar os usuarios", Toast.LENGTH_LONG).show();
                progress.dismiss();

            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, "Não foi possivel sincronizar com o servidor, por favor verifique sua conexão", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setAndroidId(final Usuario usuario, final int alterado) {
        final ProgressDialog progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("Aguarde enquanto seus dados são validados");
        progress.setTitle("AGUARDE");
        progress.show();

        Rotas apiRotas = Api.buildRetrofit();

        String idAndroit = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<Usuario> call = apiRotas.setAndroidId(idAndroit, usuario.getId_usuario());

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);

                    if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN") > 0) {
                        db.atualizarTBL_LOGIN("1", edtLogin.getText().toString(), edtSenha.getText().toString(), "S", (Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID)));
                    } else {
                        db.insertTBL_LOGIN("1", edtLogin.getText().toString(), edtSenha.getText().toString(), "S", (Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID)));
                    }
                    bundleUsuario = new Bundle();
                    bundleUsuario.putString("usuario", db.consulta("SELECT NOME_USUARIO FROM TBL_WEB_USUARIO WHERE LOGIN = '" + edtLogin.getText().toString() + "';", "NOME_USUARIO"));
                    intent.putExtras(bundleUsuario);
                    intent.putExtra("alterado", alterado);
                    db.close();
                    System.gc();
                    UsuarioHelper.setUsuario(usuario);
                    progress.dismiss();
                    startActivity(intent);
                    finish();
                } else {

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
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        sincroniza = menu.findItem(R.id.menu_sincroniza);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sincroniza:
                getUsuarios();
                return true;
        }
        return false;
    }
}

