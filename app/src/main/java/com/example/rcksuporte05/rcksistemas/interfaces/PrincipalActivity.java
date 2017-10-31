package com.example.rcksuporte05.rcksistemas.interfaces;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.bo.SincroniaBO;
import com.example.rcksuporte05.rcksistemas.bo.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.classes.Sincronia;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int pedidosPendentes = 0;
    private static final int pedidosEnviados = 1;
    private static final int sair = 2;
    private static final int AtualizarBanco = 3;
    private Button btnCliente;
    private Button btnProduto;
    private Button btnPedidos;
    private Button btnSincroniza;
    private TextView txtPedido;
    private TextView txtCliente;
    private TextView txtProduto;
    private TextView txtSincroniza;
    private int aperta = 0;
    private Toolbar tb_principal;
    private ImageView ivInternet;
    private SincroniaBO sincroniaBO = new SincroniaBO();
    private Sincronia sincronia;
    private List<Usuario> usuarioList = new ArrayList<>();
    private UsuarioBO usuarioBO = new UsuarioBO();
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        db = new DBHelper(this);

        btnCliente = (Button) findViewById(R.id.btnCliente);
        btnProduto = (Button) findViewById(R.id.btnProduto);
        btnPedidos = (Button) findViewById(R.id.btnPedido);
        btnSincroniza = (Button) findViewById(R.id.btnSincroniza);
        txtPedido = (TextView) findViewById(R.id.txtPedido);
        txtProduto = (TextView) findViewById(R.id.txtProduto);
        txtCliente = (TextView) findViewById(R.id.txtCliente);
        txtSincroniza = (TextView) findViewById(R.id.txtSincroniza);
        ivInternet = (ImageView) findViewById(R.id.ivInternet);
        txtPedido.setOnClickListener(this);
        txtCliente.setOnClickListener(this);
        txtProduto.setOnClickListener(this);
        btnPedidos.setOnClickListener(this);
        btnProduto.setOnClickListener(this);
        btnCliente.setOnClickListener(this);
        btnSincroniza.setOnClickListener(this);
        tb_principal = (Toolbar) findViewById(R.id.tb_principal);
        tb_principal.setTitle(getString(R.string.app_name));
        tb_principal.setSubtitle("Usuario: " + UsuarioHelper.getUsuario().getNome_usuario());
        tb_principal.setLogo(R.mipmap.ic_launcher);

        sincronia = new Sincronia(true, true, true);

        setSupportActionBar(tb_principal);

    }

    @Override
    public void onClick(View view) {

        if (view == btnCliente || view == txtCliente) {
            getUsuarios();
            Intent intent = new Intent(PrincipalActivity.this, ActivityCliente.class);
            startActivity(intent);
        } else if (view == btnProduto || view == txtProduto) {
            getUsuarios();

            Intent intent = new Intent(PrincipalActivity.this, ActivityProduto.class);
            startActivity(intent);
        } else if (view == btnPedidos || view == txtPedido) {
            getUsuarios();
            Intent intent = new Intent(PrincipalActivity.this, ActivityPedidoMain.class);

            startActivity(intent);
        } else if (view == btnSincroniza || view == txtSincroniza) {
            getUsuarios();
            Intent intent = new Intent(PrincipalActivity.this, ActiviyDialogSincronia.class);
            SincroniaBO.setActivity(PrincipalActivity.this);
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {
        aperta++;
        if (aperta >= 2) {
            finish();
        } else {
            Toast.makeText(this, "Toque novamente para sair!", Toast.LENGTH_SHORT).show();
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                aperta = 0;
                System.gc();
            }
        }, 2000, 5000);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, pedidosPendentes, 0, "Pedidos pendentes");
        menu.add(0, pedidosEnviados, 0, "Pedidos enviados");
        menu.add(0, sair, 0, "Sair");

        if (UsuarioHelper.getUsuario().getNome_usuario().equalsIgnoreCase("RCK SISTEMAS")) {
            menu.add(0, AtualizarBanco, 0, "Atualizar Banco");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case sair:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Tem certteza que deseja sair?\n(O login só é possível com acesso a internet)");
                alert.setTitle("Atenção!");
                alert.setNegativeButton("Não", null);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper db = new DBHelper(PrincipalActivity.this);
                        db.alterar("UPDATE TBL_LOGIN SET LOGADO = 'N'");
                        Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                        startActivity(intent);
                        System.gc();
                        finish();
                    }
                });
                alert.show();
                break;
            case pedidosPendentes:
                Intent telaPedidoPendentes = new Intent(PrincipalActivity.this, ListagemPedidoPendente.class);
                startActivity(telaPedidoPendentes);
                break;
            case pedidosEnviados:
                Intent telaPedidoEnviado = new Intent(PrincipalActivity.this, ListagemPedidoEnviado.class);
                startActivity(telaPedidoEnviado);
                break;
        }

        return false;
    }


    public void getUsuarios() {
        Rotas apiRotas = Api.buildRetrofit();

        Call<List<Usuario>> call = apiRotas.getUsuarios();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                usuarioList = response.body();
                ivInternet.setVisibility(View.INVISIBLE);
                if (!usuarioBO.sincronizaNobanco(usuarioList, PrincipalActivity.this))
                    Toast.makeText(PrincipalActivity.this, "Houve um erro ao salvar os usuarios", Toast.LENGTH_LONG).show();
                else {
                    String aparelhoId = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    String idLogin = db.consulta("SELECT * FROM TBL_WEB_USUARIO WHERE ID_USUARIO = " + UsuarioHelper.getUsuario().getId_usuario(), "APARELHO_ID");
                    if (aparelhoId.equals(idLogin)) {
                        if (UsuarioHelper.getUsuario().getSenha().equals(db.consulta("SELECT SENHA FROM TBL_WEB_USUARIO WHERE ATIVO = 'S' AND LOGIN = '" + UsuarioHelper.getUsuario().getLogin() + "';", "SENHA").trim())) {
                            System.out.println("Usuario ok!");
                        } else {
                            Toast.makeText(getApplicationContext(), "Por favor, refaça o seu login!", Toast.LENGTH_LONG).show();
                            db.alterar("UPDATE TBL_LOGIN SET LOGADO = 'N';");
                            Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            System.gc();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Este usuario está logado em outro aparelho!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Por favor, refaça o seu login!", Toast.LENGTH_LONG).show();
                        db.alterar("UPDATE TBL_LOGIN SET LOGADO = 'N';");
                        Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        System.gc();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PrincipalActivity.this, "Não foi possivel sincronizar com o servidor, por favor verifique sua conexão", Toast.LENGTH_LONG).show();
                ivInternet.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        System.gc();
        getUsuarios();
        if (getIntent().getIntExtra("alterado", 0) == 1) {
            sincroniaBO.sincronizaApi(new Sincronia(true, true, true));
            getIntent().putExtra("alterado", 0);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}

