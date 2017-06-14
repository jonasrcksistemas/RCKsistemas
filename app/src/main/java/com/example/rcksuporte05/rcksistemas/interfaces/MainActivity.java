package com.example.rcksuporte05.rcksistemas.interfaces;

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

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.extras.BancoWeb;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnEntrar;
    private Button btnFechar;
    private Bundle bundleUsuario;
    private MenuItem sincroniza;
    private Toolbar tb_main;
    private Thread a = new Thread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        sincronizar();
        DBHelper db = new DBHelper(this);
        try {
            if (db.consulta("SELECT SENHA FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consulta("SELECT LOGIN FROM TBL_LOGIN WHERE LOGADO = 'S'", "LOGIN") + "';", "SENHA").equals(db.consulta("SELECT SENHA FROM TBL_LOGIN", "SENHA"))) {
                Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                intent.putExtra("usuario", db.consulta("SELECT NOME_USUARIO FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consulta("SELECT LOGIN FROM TBL_LOGIN", "LOGIN") + "';", "NOME_USUARIO"));
                startActivity(intent);
                db.close();
                finish();
            }
        } catch (android.database.CursorIndexOutOfBoundsException e) {
            /*if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN") != 0) {
                Toast.makeText(getApplicationContext(), "Usuario alterado", Toast.LENGTH_LONG).show();
                db.alterar("DELETE FROM TBL_LOGIN");
            }*/
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
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DBHelper db = new DBHelper(MainActivity.this);
                    BancoWeb banco = new BancoWeb();
                    Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                    Usuario usuario = db.listaUsuario("SELECT * FROM TBL_WEB_USUARIO WHERE LOGIN = '" + edtLogin.getText().toString() + "'").get(0);
                    banco.Atualiza("UPDATE TBL_WEB_USUARIO SET APARELHO_ID = '" + (Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID)) + "' WHERE ID_USUARIO = " + usuario.getId_usuario() + ";");
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
                    startActivity(intent);
                    finish();
                } catch (IOException | XmlPullParserException e) {
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
        });
        a.start();
    }

    public void sincronizar() throws NullPointerException {
        a = new Thread(new Runnable() {
            @Override
            public void run() {
                BancoWeb banco = new BancoWeb();
                DBHelper db = new DBHelper(MainActivity.this);
                try {
                    try {
                        Usuario[] listaUsuario = banco.sincronizaUsuario("SELECT * FROM TBL_WEB_USUARIO ORDER BY ID_USUARIO");

                        for (int i = 0; listaUsuario.length > i; i++) {
                            if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_USUARIO WHERE ID_USUARIO = " + listaUsuario[i].getId_usuario()) <= 0) {
                                db.inserirTBL_WEB_USUARIO(listaUsuario[i]);
                            } else {
                                db.atualizarTBL_WEB_USUARIO(listaUsuario[i]);
                            }

                        }
                    } catch (final Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Não foi possivel sincronizar com o servidor", Toast.LENGTH_LONG).show();
                                System.out.println("Não foi possivel sincronizar com o servidor: " + e);
                            }
                        });
                    }

                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setTitle("Atenção!");
                            alert.setMessage("Não foi possivel conectar ao servidor!\n      Verifique sua conexão com a internet!");
                            alert.setNeutralButton("OK", null);
                            alert.show();
                        }
                    });
                }
            }
        });
        if (!a.isAlive()) {
            a.start();
        }
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
                sincronizar();
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
    }
}

