package com.example.rcksuporte05.rcksistemas.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.SincroniaBO;
import com.example.rcksuporte05.rcksistemas.BO.UsuarioBO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.model.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int sair = 1;
    private static final int AtualizarBanco = 2;
    private static final int Sobre = 3;
    private static final int Campanha = 4;

    @BindView(R.id.btnCliente)
    Button btnCliente;

    @BindView(R.id.btnProduto)
    Button btnProduto;

    @BindView(R.id.btnPedido)
    Button btnPedidos;

    @BindView(R.id.btnSincroniza)
    Button btnSincroniza;

    @BindView(R.id.btnPedidoFinalizado)
    Button btnPedidoFinalizado;

    @BindView(R.id.btnPedidoPendente)
    Button btnPedidoPendente;

    @BindView(R.id.btnProspectNovo)
    Button btnProspectNovo;

    @BindView(R.id.btnProspectListar)
    Button btnProspectLista;

    @BindView(R.id.ivInternet)
    ImageView ivInternet;

    @BindView(R.id.tb_principal)
    Toolbar tb_principal;

    private int aperta = 0;
    private SincroniaBO sincroniaBO = new SincroniaBO();
    private Sincronia sincronia;
    private List<Usuario> usuarioList = new ArrayList<>();
    private UsuarioBO usuarioBO = new UsuarioBO();
    private DBHelper db;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        db = new DBHelper(this);

        btnPedidos.setOnClickListener(this);
        btnProduto.setOnClickListener(this);
        btnCliente.setOnClickListener(this);
        btnSincroniza.setOnClickListener(this);
        btnPedidoPendente.setOnClickListener(this);
        btnPedidoFinalizado.setOnClickListener(this);
        btnProspectNovo.setOnClickListener(this);
        btnProspectLista.setOnClickListener(this);
        tb_principal.setTitle(getString(R.string.app_name));
        tb_principal.setSubtitle("Usuario: " + UsuarioHelper.getUsuario().getNome_usuario());
        tb_principal.setLogo(R.mipmap.ic_launcher);

        sincronia = new Sincronia(true, true, true, false, false, false, false);

        getUsuarios();

        if (ContextCompat.checkSelfPermission(PrincipalActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(PrincipalActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(PrincipalActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(PrincipalActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(PrincipalActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PrincipalActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
        }
        setSupportActionBar(tb_principal);
    }

    @Override
    public void onClick(View view) {

        if (view == btnCliente) {
            getUsuarios();
            Intent intent = new Intent(PrincipalActivity.this, ActivityCliente.class);
            startActivity(intent);
        } else if (view == btnProduto) {
            getUsuarios();
            Intent intent = new Intent(PrincipalActivity.this, ActivityProduto.class);
            startActivity(intent);
        } else if (view == btnPedidos) {
            getUsuarios();
            Intent intent = new Intent(PrincipalActivity.this, ActivityPedidoMain.class);
            startActivity(intent);
        } else if (view == btnSincroniza) {
            Intent intent = new Intent(PrincipalActivity.this, ActivityDialogSincronia.class);
            SincroniaBO.setActivity(PrincipalActivity.this);
            startActivity(intent);
        } else if (view == btnPedidoFinalizado) {
            getUsuarios();
            Intent telaPedidoEnviado = new Intent(PrincipalActivity.this, ListagemPedidoEnviado.class);
            startActivity(telaPedidoEnviado);
        } else if (view == btnPedidoPendente) {
            getUsuarios();
            Intent telaPedidoPendentes = new Intent(PrincipalActivity.this, ListagemPedidoPendente.class);
            startActivity(telaPedidoPendentes);
        } else if (view == btnProspectNovo) {
            getUsuarios();
            Intent intent = new Intent(PrincipalActivity.this, ActivityListaProspect.class);
            startActivity(intent);
        } else if (view == btnProspectLista) {
            getUsuarios();
            Intent intent = new Intent(PrincipalActivity.this, ActivityListaProspectEnviado.class);
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
        menu.add(0, sair, 0, "Sair");
        menu.add(0, Sobre, 0, "Informações do sistema");
        menu.add(0, Campanha, 0, "Campanhas disponiveis");
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
            case Sobre:
                Intent intent = new Intent(PrincipalActivity.this, infoDialog.class);
                startActivity(intent);
                break;
            case Campanha:
                Intent intentCampanha = new Intent(PrincipalActivity.this, CampanhaActivity.class);
                startActivity(intentCampanha);
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
                ivInternet.setVisibility(View.GONE);
                String dataSincronia = "";
                try {
                    dataSincronia = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(db.consulta("SELECT DATA_SINCRONIA FROM TBL_LOGIN", "DATA_SINCRONIA")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!dataSincronia.equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {
                    Toast.makeText(PrincipalActivity.this, "Sincronia atrasada, por favor faça a sincronia!", Toast.LENGTH_SHORT).show();
                }
                if (!usuarioBO.sincronizaNobanco(usuarioList, PrincipalActivity.this))
                    Toast.makeText(PrincipalActivity.this, "Houve um erro ao salvar os usuarios", Toast.LENGTH_LONG).show();
                else {
                    String aparelhoId = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    try {
                        Usuario usuarioLogin = db.listaUsuario("SELECT * FROM TBL_WEB_USUARIO WHERE ID_USUARIO = " + UsuarioHelper.getUsuario().getId_usuario()).get(0);
                        if (aparelhoId.equals(usuarioLogin.getAparelho_id())) {
                            loginNaApi(usuarioLogin);
                        } else {
                            if (!UsuarioHelper.getUsuario().getLogin().equals("DC")) {
                                Toast.makeText(getApplicationContext(), "Este usuario está logado em outro aparelho!", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Por favor, refaça o seu login!", Toast.LENGTH_LONG).show();
                                db.alterar("UPDATE TBL_LOGIN SET LOGADO = 'N';");
                                Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            System.gc();
                        }
                    } catch (CursorIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Este usuario está logado em outro aparelho!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Por favor, refaça o seu login!", Toast.LENGTH_LONG).show();
                        db.alterar("UPDATE TBL_LOGIN SET LOGADO = 'N';");
                        Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
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

    public void loginNaApi(final Usuario usuario) {
        Rotas apiRotas = Api.buildRetrofit();

        String idAndroit = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<Usuario> call = apiRotas.login(idAndroit, usuario.getId_usuario(), db.consulta("SELECT * FROM TBL_LOGIN", "LOGIN"), db.consulta("SELECT * FROM TBL_LOGIN", "SENHA"));

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuario1 = response.body();
                switch (response.code()) {
                    case 200:
                        if (usuario1.getIdEmpresaMultiDevice() != null && Integer.parseInt(usuario1.getIdEmpresaMultiDevice()) > 0) {
                            System.gc();
                            System.out.println("Usuario ok");
                            UsuarioHelper.setUsuario(usuario1);
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(PrincipalActivity.this);
                            alert.setTitle("Atenção");
                            alert.setMessage("O usuario em questão não tem o codigo da empresa informado em seu cadastro, é necessário a correção no cadastro deste usuário!\n    " +
                                    "Em caso de duvida, favor entrar em contato com a RCK sistemas!");
                            alert.setCancelable(false);
                            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    db.alterar("UPDATE TBL_LOGIN SET LOGADO = 'N'");
                                    finish();
                                }
                            });
                            alert.show();
                        }
                        break;
                    case 500:
                        if (!UsuarioHelper.getUsuario().getLogin().equals("DC")) {
                            Toast.makeText(getApplicationContext(), "Foi encontrada uma divergencia em seu cadastro!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Por favor, refaça o seu login!", Toast.LENGTH_LONG).show();
                            db.alterar("UPDATE TBL_LOGIN SET LOGADO = 'N';");
                            Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        System.gc();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                System.out.println("Não foi possivel validar usuario!");
            }
        });
    }

    @Override
    protected void onResume() {
        if (getIntent().getIntExtra("alterado", 0) == 1) {
            sincroniaBO.sincronizaApi(new Sincronia(true, true, true, false, false, false, false));
            getIntent().putExtra("alterado", 0);
        }
        ClienteHelper.clear();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}

