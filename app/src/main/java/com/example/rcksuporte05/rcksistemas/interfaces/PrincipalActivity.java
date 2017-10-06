package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
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
import com.example.rcksuporte05.rcksistemas.classes.Sincronia;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.extras.BancoWeb;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
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
    private Bundle bundleUsuario;
    private int aperta = 0;
    private int id_usuario;
    private int id_vendedor;
    private BancoWeb banco;
    private Toolbar tb_principal;
    private Thread a = new Thread();
    private Thread b = new Thread();
    private ImageView ivInternet;
    private SincroniaBO sincroniaBO = new SincroniaBO();
    private Sincronia sincronia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

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
        tb_principal.setSubtitle("Usuario: " + getIntent().getStringExtra("usuario"));
        tb_principal.setLogo(R.mipmap.ic_launcher);

        DBHelper db = new DBHelper(this);


        setSupportActionBar(tb_principal);


        id_usuario = Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario());
        id_vendedor = Integer.parseInt(UsuarioHelper.getUsuario().getId_quando_vendedor());
    }

    @Override
    public void onClick(View view) {
        if (!a.isAlive()) {
            if (view == btnCliente || view == txtCliente) {
                bundleUsuario = new Bundle();
                bundleUsuario.putInt("usuario", id_usuario);
                Intent intent = new Intent(PrincipalActivity.this, ActivityCliente.class);
                intent.putExtras(bundleUsuario);
                if (!b.isAlive()) {
                    sincronizaUsuario();
                }
                startActivity(intent);
            } else if (view == btnProduto || view == txtProduto) {
                bundleUsuario = new Bundle();
                bundleUsuario.putInt("usuario", id_usuario);
                Intent intent = new Intent(PrincipalActivity.this, ActivityProduto.class);
                intent.putExtras(bundleUsuario);
                if (!b.isAlive()) {
                    sincronizaUsuario();
                }
                startActivity(intent);
            } else if (view == btnPedidos || view == txtPedido) {
                bundleUsuario = new Bundle();
                bundleUsuario.putInt("usuario", id_usuario);
                bundleUsuario.putInt("vendedor", id_vendedor);
                Intent intent = new Intent(PrincipalActivity.this, ActivityPedidoMain.class);
                intent.putExtras(bundleUsuario);
                if (!b.isAlive()) {
                    sincronizaUsuario();
                }
                startActivity(intent);
            } else if (view == btnSincroniza || view == txtSincroniza) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Atenção!");
                alert.setMessage("Tem certeza que deseja iniciar a sincronia agora?");
                alert.setNegativeButton("NÃO", null);
                alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sincronizaApi();
                    }
                });
                alert.show();
            }
        } else {
            Toast.makeText(PrincipalActivity.this, "Por favor aguarde o termino da sincronia", Toast.LENGTH_SHORT).show();
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
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("usuario").equalsIgnoreCase("RCK SISTEMAS")) {
            menu.add(0, AtualizarBanco, 0, "Atualizar Banco");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!a.isAlive()) {
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
                case AtualizarBanco:
                    Thread a = new Thread(new Runnable() {
                        ProgressDialog dialog;

                        @Override
                        public void run() {
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog = new ProgressDialog(PrincipalActivity.this);
                                        dialog.setTitle("Atualizando o Banco de Dados!");
                                        dialog.setMessage("Aguarde...");
                                        dialog.setCancelable(false);
                                        dialog.show();
                                    }
                                });
                                final AlertDialog.Builder alert = new AlertDialog.Builder(PrincipalActivity.this);
                                String atualizaBanco = banco.AtualizaBanco();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ivInternet.setVisibility(View.INVISIBLE);
                                    }
                                });
                                alert.setMessage(atualizaBanco);
                                alert.setNeutralButton("OK", null);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alert.show();
                                        dialog.dismiss();
                                    }
                                });

                            } catch (IOException | XmlPullParserException e) {
                                System.out.println(e.getMessage());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                    Toast.makeText(PrincipalActivity.this, "Servidor indisponivel", Toast.LENGTH_SHORT).show();
                                        ivInternet.setVisibility(View.VISIBLE);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                            }
                                        });
                                        System.out.println(e.getMessage());
                                    }
                                });
                            }
                        }
                    });
                    a.start();
                    break;
                case pedidosPendentes:
                    Intent telaPedidoPendentes = new Intent(PrincipalActivity.this, ListagemPedidoPendente.class);
                    Bundle bundleUsuario = new Bundle();
                    bundleUsuario.putInt("usuario", id_usuario);
                    bundleUsuario.putInt("vendedor", id_vendedor);
                    telaPedidoPendentes.putExtras(bundleUsuario);
                    startActivity(telaPedidoPendentes);
                    break;
                case pedidosEnviados:
                    Intent telaPedidoEnviado = new Intent(PrincipalActivity.this, ListagemPedidoEnviado.class);
                    startActivity(telaPedidoEnviado);
                    break;
            }
        } else {
            Toast.makeText(PrincipalActivity.this, "Por favor aguarde o termino da sincronia", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
/*
    public void sincroniza() {
        final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_sincroniza_main)
                .setContentTitle("Sincronia em andamento")
                .setContentText("Aguarde")
                .setPriority(2);
        final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notificacao.build());

        final ProgressDialog progress = new ProgressDialog(PrincipalActivity.this);
        progress.setMessage("Aguarde");
        progress.setTitle("Sincronia em andamento");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.show();



        banco = new BancoWeb();

        a = new Thread(new Runnable() {
            DBHelper db = new DBHelper(PrincipalActivity.this);

            @Override
            public void run() {

                try {
                    notificacao.setContentText("Usuarios").
                            setProgress(0, 0, true);
                    mNotificationManager.notify(0, notificacao.build());
                    progress.setMessage("Usuarios");

                    Usuario[] usuario = banco.sincronizaUsuario("SELECT * FROM TBL_WEB_USUARIO A INNER JOIN TBL_WEB_USUARIO_SYNC B ON (A.ID_USUARIO = B.ID_USUARIO) WHERE B.ID_WEB_USUARIO = " + id_usuario + " AND B.SYNC = 'N';");
                    progress.setMax(usuario.length);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivInternet.setVisibility(View.INVISIBLE);
                        }
                    });

                    for (int i = 0; usuario.length > i; i++) {
                        notificacao.setProgress(usuario.length, i, false);
                        progress.setProgress(i);
                        if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_USUARIO WHERE ID_USUARIO = " + usuario[i].getId_usuario()) != 0) {
                            db.atualizarTBL_WEB_USUARIO(usuario[i]);
                        } else {
                            db.inserirTBL_WEB_USUARIO(usuario[i]);
                        }
                        mNotificationManager.notify(0, notificacao.build());
                    }
                    banco.Atualiza("UPDATE TBL_WEB_USUARIO_SYNC SET SYNC = 'S' WHERE ID_WEB_USUARIO = " + id_usuario + ";");

                } catch (IOException | XmlPullParserException e) {

                    notificacao.setContentText("Problema de conexão").
                            setContentTitle("Verifique sua conexão!").
                            setProgress(0, 0, false).
                            setSmallIcon(R.mipmap.ic_sem_internet);
                    mNotificationManager.notify(0, notificacao.build());
                    progress.setMessage("Problema de Conexão");
                    progress.setProgress(0);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivInternet.setVisibility(View.VISIBLE);
                        }
                    });
                } finally {
                    try {
                        if (db.consulta("SELECT SENHA FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consulta("SELECT LOGIN FROM TBL_LOGIN", "LOGIN") + "';", "SENHA").equals(db.consulta("SELECT SENHA FROM TBL_LOGIN", "SENHA"))) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("Usuario ok!");
                                }
                            });
                        } else {
                            notificacao.setContentText("Usuario alterado!").
                                    setContentTitle("Por favor, refaça o login!").
                                    setProgress(0, 0, false).
                                    setSmallIcon(R.mipmap.ic_usuario_alterado);
                            mNotificationManager.notify(0, notificacao.build());

                            progress.dismiss();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    a.interrupt();
                                }
                            });

                            DBHelper db = new DBHelper(PrincipalActivity.this);
                            db.alterar("DELETE FROM TBL_LOGIN");
                            Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            System.gc();
                        }
                    } catch (android.database.CursorIndexOutOfBoundsException e) {
                        notificacao.setContentText("Usuario alterado!").
                                setContentTitle("Por favor, refaça o login!").
                                setProgress(0, 0, false).
                                setSmallIcon(R.mipmap.ic_usuario_alterado);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.dismiss();

                        if (db.contagem("SELECT COUNT(*) FROM TBL_LOGIN") != 0) {
                            DBHelper db = new DBHelper(PrincipalActivity.this);
                            db.alterar("DELETE FROM TBL_LOGIN");
                            Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            System.gc();
                        }
                    }
                }

                notificacao.setProgress(0, 0, true).
                        setContentText("Clientes").
                        setContentTitle("Sincronia em andamento");
                mNotificationManager.notify(0, notificacao.build());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setMessage("Clientes");
                        progress.setIndeterminate(true);
                    }
                });

                try {
                    db.alterar("DELETE FROM TBL_CADASTRO;");

                    Cliente[] listaCliente;
                    if (Integer.parseInt(banco.ConsultaSQL("SELECT COUNT(*) FROM TBL_USUARIO_VINCULO_VENDEDOR WHERE ID_USUARIO = " + id_usuario + ";", "COUNT")) > 0) {
                        listaCliente = banco.sincronizaCliente("SELECT A.* FROM TBL_CADASTRO A \n" +
                                "    INNER JOIN TBL_USUARIO_VINCULO_VENDEDOR B ON (B.ID_VENDEDOR = A.ID_VENDEDOR) \n" +
                                "WHERE A.F_CLIENTE = 'S' AND B.ID_USUARIO = '" + id_usuario + "';");
                    } else {
                        listaCliente = banco.sincronizaCliente("SELECT * FROM TBL_CADASTRO WHERE F_VENDEDOR = 'S' OR F_CLIENTE = 'S';");
                    }

                    progress.setMax(listaCliente.length);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivInternet.setVisibility(View.INVISIBLE);
                            progress.setIndeterminate(false);
                        }
                    });

                    for (int i = 0; listaCliente.length > i; i++) {
                        notificacao.setProgress(listaCliente.length, i, false);
                        progress.setProgress(i);
                        db.inserirTBL_CADASTRO(listaCliente[i].getAtivo(),
                                listaCliente[i].getId_empresa(),
                                listaCliente[i].getId_cadastro(),
                                listaCliente[i].getPessoa_f_j(),
                                listaCliente[i].getData_aniversario(),
                                listaCliente[i].getNome_cadastro(),
                                listaCliente[i].getNome_fantasia(),
                                listaCliente[i].getCpf_cnpj(),
                                listaCliente[i].getInscri_estadual(),
                                listaCliente[i].getInscri_municipal(),
                                listaCliente[i].getEndereco(),
                                listaCliente[i].getEndereco_bairro(),
                                listaCliente[i].getEndereco_numero(),
                                listaCliente[i].getEndereco_complemento(),
                                listaCliente[i].getEndereco_uf(),
                                listaCliente[i].getEndereco_id_municipio(),
                                listaCliente[i].getEndereco_cep(),
                                listaCliente[i].getUsuario_id(),
                                listaCliente[i].getUsuario_nome(),
                                listaCliente[i].getUsuario_data(),
                                listaCliente[i].getF_cliente(),
                                listaCliente[i].getF_fornecedor(),
                                listaCliente[i].getF_funcionario(),
                                listaCliente[i].getF_vendedor(),
                                listaCliente[i].getF_transportador(),
                                listaCliente[i].getData_ultima_compra(),
                                listaCliente[i].getId_vendedor(),
                                listaCliente[i].getF_id_cliente(),
                                listaCliente[i].getId_entidade(),
                                listaCliente[i].getF_id_fornecedor(),
                                listaCliente[i].getF_id_vendedor(),
                                listaCliente[i].getF_id_transportador(),
                                listaCliente[i].getTelefone_principal(),
                                listaCliente[i].getEmail_principal(),
                                listaCliente[i].getId_pais(),
                                listaCliente[i].getF_id_funcionario(),
                                listaCliente[i].getAvisar_com_dias(),
                                listaCliente[i].getObservacoes(),
                                listaCliente[i].getPadrao_id_c_custo(),
                                listaCliente[i].getPadrao_id_c_gerenciadora(),
                                listaCliente[i].getPadrao_id_c_analitica(),
                                listaCliente[i].getCob_endereco(),
                                listaCliente[i].getCob_endereco_bairro(),
                                listaCliente[i].getCob_endereco_numero(),
                                listaCliente[i].getCob_endereco_complemento(),
                                listaCliente[i].getCob_endereco_uf(),
                                listaCliente[i].getCob_endereco_id_municipio(),
                                listaCliente[i].getCob_endereco_cep(),
                                listaCliente[i].getCob_endereco_id_pais(),
                                listaCliente[i].getLimite_credito(),
                                listaCliente[i].getLimite_disponivel(),
                                listaCliente[i].getPessoa_contato_financeiro(),
                                listaCliente[i].getEmail_financeiro(),
                                listaCliente[i].getObservacoes_faturamento(),
                                listaCliente[i].getObservacoes_financeiro(),
                                listaCliente[i].getTelefone_dois(),
                                listaCliente[i].getTelefone_tres(),
                                listaCliente[i].getPessoa_contato_principal(),
                                listaCliente[i].getInd_da_ie_destinatario(),
                                listaCliente[i].getComissao_percentual(),
                                listaCliente[i].getId_setor(),
                                listaCliente[i].getNfe_email_enviar(),
                                listaCliente[i].getNfe_email_um(),
                                listaCliente[i].getNfe_email_dois(),
                                listaCliente[i].getNfe_email_tres(),
                                listaCliente[i].getNfe_email_quatro(),
                                listaCliente[i].getNfe_email_cinco(),
                                listaCliente[i].getId_grupo_vendedor(),
                                listaCliente[i].getVendedor_usa_portal(),
                                listaCliente[i].getVendedor_id_user_portal(),
                                listaCliente[i].getF_tarifa(),
                                listaCliente[i].getF_id_tarifa(),
                                listaCliente[i].getF_produtor(),
                                listaCliente[i].getRg_numero(),
                                listaCliente[i].getRg_ssp(),
                                listaCliente[i].getConta_contabil(),
                                listaCliente[i].getMotorista(),
                                listaCliente[i].getF_id_motorista(),
                                listaCliente[i].getHabilitacao_numero(),
                                listaCliente[i].getHabilitacao_categoria(),
                                listaCliente[i].getHabilitacao_vencimento(),
                                listaCliente[i].getMot_id_transportadora(),
                                listaCliente[i].getLocal_cadastro());

                        mNotificationManager.notify(0, notificacao.build());
                    }

                    Cliente[] listaVendedores = banco.sincronizaCliente("SELECT * FROM TBL_CADASTRO WHERE F_VENDEDOR = 'S' ORDER BY F_ID_VENDEDOR;");
                    progress.setMax(listaVendedores.length);
                    for (int i = 0; listaVendedores.length > i; i++) {
                        notificacao.setProgress(listaVendedores.length, i, false);
                        progress.setProgress(i);
                        db.inserirTBL_CADASTRO(listaVendedores[i].getAtivo(),
                                listaVendedores[i].getId_empresa(),
                                listaVendedores[i].getId_cadastro(),
                                listaVendedores[i].getPessoa_f_j(),
                                listaVendedores[i].getData_aniversario(),
                                listaVendedores[i].getNome_cadastro(),
                                listaVendedores[i].getNome_fantasia(),
                                listaVendedores[i].getCpf_cnpj(),
                                listaVendedores[i].getInscri_estadual(),
                                listaVendedores[i].getInscri_municipal(),
                                listaVendedores[i].getEndereco(),
                                listaVendedores[i].getEndereco_bairro(),
                                listaVendedores[i].getEndereco_numero(),
                                listaVendedores[i].getEndereco_complemento(),
                                listaVendedores[i].getEndereco_uf(),
                                listaVendedores[i].getEndereco_id_municipio(),
                                listaVendedores[i].getEndereco_cep(),
                                listaVendedores[i].getUsuario_id(),
                                listaVendedores[i].getUsuario_nome(),
                                listaVendedores[i].getUsuario_data(),
                                listaVendedores[i].getF_cliente(),
                                listaVendedores[i].getF_fornecedor(),
                                listaVendedores[i].getF_funcionario(),
                                listaVendedores[i].getF_vendedor(),
                                listaVendedores[i].getF_transportador(),
                                listaVendedores[i].getData_ultima_compra(),
                                listaVendedores[i].getId_vendedor(),
                                listaVendedores[i].getF_id_cliente(),
                                listaVendedores[i].getId_entidade(),
                                listaVendedores[i].getF_id_fornecedor(),
                                listaVendedores[i].getF_id_vendedor(),
                                listaVendedores[i].getF_id_transportador(),
                                listaVendedores[i].getTelefone_principal(),
                                listaVendedores[i].getEmail_principal(),
                                listaVendedores[i].getId_pais(),
                                listaVendedores[i].getF_id_funcionario(),
                                listaVendedores[i].getAvisar_com_dias(),
                                listaVendedores[i].getObservacoes(),
                                listaVendedores[i].getPadrao_id_c_custo(),
                                listaVendedores[i].getPadrao_id_c_gerenciadora(),
                                listaVendedores[i].getPadrao_id_c_analitica(),
                                listaVendedores[i].getCob_endereco(),
                                listaVendedores[i].getCob_endereco_bairro(),
                                listaVendedores[i].getCob_endereco_numero(),
                                listaVendedores[i].getCob_endereco_complemento(),
                                listaVendedores[i].getCob_endereco_uf(),
                                listaVendedores[i].getCob_endereco_id_municipio(),
                                listaVendedores[i].getCob_endereco_cep(),
                                listaVendedores[i].getCob_endereco_id_pais(),
                                listaVendedores[i].getLimite_credito(),
                                listaVendedores[i].getLimite_disponivel(),
                                listaVendedores[i].getPessoa_contato_financeiro(),
                                listaVendedores[i].getEmail_financeiro(),
                                listaVendedores[i].getObservacoes_faturamento(),
                                listaVendedores[i].getObservacoes_financeiro(),
                                listaVendedores[i].getTelefone_dois(),
                                listaVendedores[i].getTelefone_tres(),
                                listaVendedores[i].getPessoa_contato_principal(),
                                listaVendedores[i].getInd_da_ie_destinatario(),
                                listaVendedores[i].getComissao_percentual(),
                                listaVendedores[i].getId_setor(),
                                listaVendedores[i].getNfe_email_enviar(),
                                listaVendedores[i].getNfe_email_um(),
                                listaVendedores[i].getNfe_email_dois(),
                                listaVendedores[i].getNfe_email_tres(),
                                listaVendedores[i].getNfe_email_quatro(),
                                listaVendedores[i].getNfe_email_cinco(),
                                listaVendedores[i].getId_grupo_vendedor(),
                                listaVendedores[i].getVendedor_usa_portal(),
                                listaVendedores[i].getVendedor_id_user_portal(),
                                listaVendedores[i].getF_tarifa(),
                                listaVendedores[i].getF_id_tarifa(),
                                listaVendedores[i].getF_produtor(),
                                listaVendedores[i].getRg_numero(),
                                listaVendedores[i].getRg_ssp(),
                                listaVendedores[i].getConta_contabil(),
                                listaVendedores[i].getMotorista(),
                                listaVendedores[i].getF_id_motorista(),
                                listaVendedores[i].getHabilitacao_numero(),
                                listaVendedores[i].getHabilitacao_categoria(),
                                listaVendedores[i].getHabilitacao_vencimento(),
                                listaVendedores[i].getMot_id_transportadora(),
                                listaVendedores[i].getLocal_cadastro());

                        mNotificationManager.notify(0, notificacao.build());
                    }

                    banco.Atualiza("UPDATE TBL_CADASTRO_SYNC SET SYNC = 'S' WHERE ID_WEB_USUARIO = " + id_usuario);
                    notificacao.setContentText("Clientes completo")
                            .setProgress(0, 0, false);
                    mNotificationManager.notify(0, notificacao.build());
                    progress.setProgress(0);
                    System.gc();
                } catch (IOException | XmlPullParserException e) {

                    notificacao.setContentText("Problema de conexão").
                            setContentTitle("Verifique sua conexão!").
                            setProgress(0, 0, false).
                            setSmallIcon(R.mipmap.ic_sem_internet);
                    mNotificationManager.notify(0, notificacao.build());

                    progress.setProgress(0);

                } finally {
                    System.gc();
                }

                notificacao.setProgress(0, 0, true).
                        setContentText("Produtos").
                        setContentTitle("Sincronia em andamento");
                mNotificationManager.notify(0, notificacao.build());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setMessage("Produtos");
                        progress.setIndeterminate(true);
                    }
                });
                try {

                    db.alterar("DELETE FROM TBL_PRODUTO;");
                    Produto[] listaProduto = banco.sincronizaProduto("SELECT A.*, B.DESCRICAO FROM TBL_PRODUTO A INNER JOIN TBL_PRODUTO_UNID_MEDIDA B ON A.UNIDADE = B.ABREVIATURA WHERE A.PRODUTO_VENDA = 'S' AND A.MULTI_DISPOSITIVO = 'S' AND A.VENDA_PRECO > 0;");

                    progress.setMax(listaProduto.length);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivInternet.setVisibility(View.INVISIBLE);
                            progress.setIndeterminate(false);
                        }
                    });


                    for (int i = 0; listaProduto.length > i; i++) {
                        notificacao.setProgress(listaProduto.length, i, false);
                        progress.setProgress(i);
                        db.inserirTBL_PRODUTO(listaProduto[i].getAtivo(),
                                listaProduto[i].getId_produto(),
                                listaProduto[i].getNome_produto(),
                                listaProduto[i].getUnidade(),
                                listaProduto[i].getTipo_cadastro(),
                                listaProduto[i].getId_entidade(),
                                listaProduto[i].getNcm(),
                                listaProduto[i].getId_grupo(),
                                listaProduto[i].getId_sub_grupo(),
                                listaProduto[i].getPeso_bruto(),
                                listaProduto[i].getPeso_liquido(),
                                listaProduto[i].getCodigo_em_barras(),
                                listaProduto[i].getMovimenta_estoque(),
                                listaProduto[i].getNome_da_marca(),
                                listaProduto[i].getId_empresa(),
                                listaProduto[i].getId_origem(),
                                listaProduto[i].getCusto_produto(),
                                listaProduto[i].getCusto_per_ipi(),
                                listaProduto[i].getCusto_ipi(),
                                listaProduto[i].getCusto_per_frete(),
                                listaProduto[i].getCusto_frete(),
                                listaProduto[i].getCusto_per_icms(),
                                listaProduto[i].getCusto_icms(),
                                listaProduto[i].getCusto_per_fin(),
                                listaProduto[i].getCusto_fin(),
                                listaProduto[i].getCusto_per_subst(),
                                listaProduto[i].getCusto_subt(),
                                listaProduto[i].getCusto_per_outros(),
                                listaProduto[i].getCusto_outros(),
                                listaProduto[i].getValor_custo(),
                                listaProduto[i].getExcluido(),
                                listaProduto[i].getExcluido_por(),
                                listaProduto[i].getExcluido_por_data(),
                                listaProduto[i].getExcluido_codigo_novo(),
                                listaProduto[i].getAjuste_preco_data(),
                                listaProduto[i].getAjuste_preco_nfe(),
                                listaProduto[i].getAjuste_preco_usuario(),
                                listaProduto[i].getTotal_custo(),
                                listaProduto[i].getTotal_credito(),
                                listaProduto[i].getValor_custo_estoque(),
                                listaProduto[i].getCusto_data_inicial(),
                                listaProduto[i].getCusto_valor_inicial(),
                                listaProduto[i].getProduto_venda(),
                                listaProduto[i].getProduto_insumo(),
                                listaProduto[i].getProduto_consumo(),
                                listaProduto[i].getProduto_producao(),
                                listaProduto[i].getVenda_perc_comissao(),
                                listaProduto[i].getVenda_preco(),
                                listaProduto[i].getVenda_perc_comissao_dois(),
                                listaProduto[i].getDescricao());
                        mNotificationManager.notify(0, notificacao.build());
                    }
                } catch (XmlPullParserException | IOException e) {

                    notificacao.setContentText("Problema de conexão").
                            setContentTitle("Verifique sua conexão!").
                            setProgress(0, 0, false).
                            setSmallIcon(R.mipmap.ic_sem_internet);
                    mNotificationManager.notify(0, notificacao.build());

                    progress.setProgress(0);
                    System.out.println(e.getMessage());
                }

                if (db.contagem("SELECT COUNT(*) FROM TBL_PAISES") == 0) {
                    notificacao.setProgress(0, 0, true).
                            setContentText("Países").
                            setContentTitle("Sincronia em andamento");
                    mNotificationManager.notify(0, notificacao.build());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setMessage("Países");
                            progress.setIndeterminate(true);
                        }
                    });

                    try {
                        Paises[] paises = banco.sincronizaPaises("SELECT * FROM TBL_PAISES");
                        progress.setMax(paises.length);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivInternet.setVisibility(View.INVISIBLE);
                                progress.setIndeterminate(false);
                            }
                        });

                        for (int i = 0; paises.length > i; i++) {
                            notificacao.setProgress(paises.length, i, false);
                            progress.setProgress(i);
                            db.inserirTBL_PAISES(paises[i].getId_pais(),
                                    paises[i].getNome_pais());
                            mNotificationManager.notify(0, notificacao.build());
                        }
                        banco.Atualiza("UPDATE TBL_PAISES_SYNC SET SYNC = 'S' WHERE ID_WEB_USUARIO = " + id_usuario + ";");
                        notificacao.setContentText("Paises completo")
                                .setProgress(0, 0, false);
                        mNotificationManager.notify(0, notificacao.build());
                    } catch (IOException | XmlPullParserException e) {

                        notificacao.setContentText("Problema de conexão").
                                setContentTitle("Verifique sua conexão!").
                                setProgress(0, 0, false).
                                setSmallIcon(R.mipmap.ic_sem_internet);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.setProgress(0);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(PrincipalActivity.this, "Servidor indisponivel", Toast.LENGTH_SHORT).show();
                                ivInternet.setVisibility(View.VISIBLE);

                            }
                        });
                    }
                } else {
                    notificacao.setProgress(0, 0, true).
                            setContentText("Países").
                            setContentTitle("Sincronia em andamento");
                    mNotificationManager.notify(0, notificacao.build());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setMessage("Países");
                            progress.setIndeterminate(true);
                        }
                    });

                    try {

                        Paises[] paises = banco.sincronizaPaises("SELECT A.* FROM TBL_PAISES A INNER JOIN TBL_PAISES_SYNC B ON A.ID_PAIS = B.ID_PAIS WHERE B.SYNC = 'N' AND B.ID_WEB_USUARIO = " + id_usuario + ";");
                        progress.setMax(paises.length);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivInternet.setVisibility(View.INVISIBLE);
                                progress.setIndeterminate(false);
                            }
                        });

                        for (int i = 0; paises.length > i; i++) {
                            notificacao.setProgress(paises.length, i, false);
                            progress.setProgress(i);

                            if (db.contagem("SELECT COUNT(*) FROM TBL_PAISES WHERE ID_PAIS = " + paises[i].getId_pais()) <= 0) {
                                db.inserirTBL_PAISES(paises[i].getId_pais(),
                                        paises[i].getNome_pais());
                            } else {
                                db.atualizarTBL_PAISES(paises[i].getId_pais(),
                                        paises[i].getNome_pais());
                            }
                            mNotificationManager.notify(0, notificacao.build());
                        }

                        banco.Atualiza("UPDATE TBL_PAISES_SYNC SET SYNC = 'N' WHERE ID_WEB_USUARIO = " + id_usuario + ";");

                        notificacao.setContentText("Países completo")
                                .setProgress(0, 0, false);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.setProgress(0);
                    } catch (IOException | XmlPullParserException e) {

                        notificacao.setContentText("Problema de conexão").
                                setContentTitle("Verifique sua conexão!").
                                setProgress(0, 0, false).
                                setSmallIcon(R.mipmap.ic_sem_internet);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.setProgress(0);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(PrincipalActivity.this, "Servidor indisponivel", Toast.LENGTH_SHORT).show();
                                ivInternet.setVisibility(View.VISIBLE);

                            }
                        });
                    }
                }

                if (db.contagem("SELECT COUNT(*) FROM TBL_MUNICIPIOS") <= 0) {
                    notificacao.setProgress(0, 0, true).
                            setContentText("Municipios").
                            setContentTitle("Sincronia em andamento");
                    mNotificationManager.notify(0, notificacao.build());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setMessage("Municipios");
                            progress.setIndeterminate(true);
                        }
                    });

                    try {
                        Municipios[] municipios = banco.sincronizaMunicipios("SELECT * FROM TBL_MUNICIPIOS");
                        progress.setMax(municipios.length);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivInternet.setVisibility(View.INVISIBLE);
                                progress.setIndeterminate(false);
                            }
                        });

                        if (municipios.length > 0) {
                            for (int i = 0; municipios.length > i; i++) {
                                progress.setProgress(i);

                                notificacao.setProgress(municipios.length, i, false);
                                db.inserirTBL_MUNICIPIOS(municipios[i].getId_municipio(), municipios[i].getNome_municipio(), municipios[i].getUf(), municipios[i].getCep());
                                mNotificationManager.notify(0, notificacao.build());
                            }
                        }
                        banco.Atualiza("UPDATE TBL_MUNICIPIOS_SYNC SET SYNC = 'N' WHERE ID_WEB_USUARIO = " + id_usuario + ";");
                        notificacao.setContentText("Municipios completo")
                                .setProgress(0, 0, false);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.setProgress(0);
                    } catch (IOException | XmlPullParserException e) {

                        notificacao.setContentText("Problema de conexão").
                                setContentTitle("Verifique sua conexão!").
                                setProgress(0, 0, false).
                                setSmallIcon(R.mipmap.ic_sem_internet);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.setProgress(0);

                        System.out.println(e.getMessage());
                    }
                }

                notificacao.setProgress(0, 0, true).
                        setContentText("Operações de Estoque").
                        setContentTitle("Sincronia em andamento");
                mNotificationManager.notify(0, notificacao.build());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setMessage("Operações de Estoque");
                        progress.setIndeterminate(true);
                    }
                });

                try {
                    Operacao[] operacao = banco.sincronizaOperacao("SELECT * FROM TBL_OPERACAO_ESTOQUE WHERE E_ENTRADA_S_SAIDA='S' AND ATIVO='S' AND ( TIPO_OPERACAO=1 OR  TIPO_OPERACAO= 5 ) AND EMISSOR='P' AND MOVIMENTA_ESTOQUE='S' AND MULTI_DISPOSITIVO = 'S' ORDER BY ID_OPERACAO;");

                    progress.setMax(operacao.length);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivInternet.setVisibility(View.INVISIBLE);
                            progress.setIndeterminate(false);
                        }
                    });
                    db.alterar("DELETE FROM TBL_OPERACAO_ESTOQUE;");
                    for (int i = 0; operacao.length > i; i++) {
                        notificacao.setProgress(operacao.length, i, false);
                        db.inserirTBL_OPERACAO_ESTOQUE(operacao[i].getAtivo(),
                                operacao[i].getId_operacao(),
                                operacao[i].getNome_operacao());
                        mNotificationManager.notify(0, notificacao.build());
                        progress.setProgress(i);
                    }
                    banco.Atualiza("UPDATE TBL_OPERACAO_ESTOQUE_SYNC SET SYNC = 'S' WHERE ID_WEB_USUARIO = " + id_usuario + ";");
                    notificacao.setContentText("Operações de estoque completo")
                            .setProgress(0, 0, false);
                    mNotificationManager.notify(0, notificacao.build());

                    progress.setProgress(0);
                } catch (IOException | XmlPullParserException e) {

                    notificacao.setContentText("Problema de conexão").
                            setContentTitle("Verifique sua conexão!").
                            setProgress(0, 0, false)
                            .setSmallIcon(R.mipmap.ic_sem_internet);
                    mNotificationManager.notify(0, notificacao.build());

                    progress.setProgress(0);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                                Toast.makeText(PrincipalActivity.this, "Servidor indisponivel", Toast.LENGTH_SHORT).show();
                            ivInternet.setVisibility(View.VISIBLE);

                        }
                    });
                }

                notificacao.setProgress(0, 0, true).
                        setContentText("Tabela de preços").
                        setContentTitle("Sincronia em andamento");
                mNotificationManager.notify(0, notificacao.build());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setMessage("Tabela de preços");
                        progress.setIndeterminate(true);
                    }
                });
                try {
                    TabelaPreco[] tabelaPrecos = banco.sincronizaTabelaPreco("SELECT * FROM TBL_TABELA_PRECO_CAB WHERE ATIVO = 'S' AND MULTI_DISPOSITIVO = 'S';");

                    progress.setMax(tabelaPrecos.length);
                    db.alterar("DELETE FROM TBL_TABELA_PRECO_CAB;");


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivInternet.setVisibility(View.INVISIBLE);
                            progress.setIndeterminate(false);
                        }
                    });
                    for (int i = 0; tabelaPrecos.length > i; i++) {
                        notificacao.setProgress(tabelaPrecos.length, i, false);

                        progress.setProgress(i);

                        db.inserirTBL_TABELA_PRECO_CAB(
                                tabelaPrecos[i].getId_tabela(),
                                tabelaPrecos[i].getId_empresa(),
                                tabelaPrecos[i].getAtivo(),
                                tabelaPrecos[i].getId_tipo_tabela(),
                                tabelaPrecos[i].getNome_tabela(),
                                tabelaPrecos[i].getData_inicio(),
                                tabelaPrecos[i].getData_fim(),
                                tabelaPrecos[i].getDesconto_de_perc(),
                                tabelaPrecos[i].getDesconto_a_perc(),
                                tabelaPrecos[i].getComissao_perc(),
                                tabelaPrecos[i].getVerba_perc(),
                                tabelaPrecos[i].getFaixa_valor_de(),
                                tabelaPrecos[i].getFaixa_valor_a(),
                                tabelaPrecos[i].getUsuario_id(),
                                tabelaPrecos[i].getUsuario_nome(),
                                tabelaPrecos[i].getUsuario_data(),
                                tabelaPrecos[i].getDesconto_verba_max(),
                                tabelaPrecos[i].getId_grupo_vendedores(),
                                tabelaPrecos[i].getUtiliza_verba(),
                                tabelaPrecos[i].getFaixa_valor_bruto_de(),
                                tabelaPrecos[i].getFaixa_valor_bruto_a()
                        );
                        mNotificationManager.notify(0, notificacao.build());
                    }
                    banco.Atualiza("UPDATE TBL_TABELA_PRECO_CAB_SYNC SET SYNC = 'S' WHERE ID_WEB_USUARIO = " + id_usuario + ";");
                    notificacao.setContentText("Tabela de preços completo")
                            .setProgress(0, 0, false);
                    mNotificationManager.notify(0, notificacao.build());
                    progress.setProgress(0);
                } catch (IOException | XmlPullParserException e) {

                    notificacao.setContentText("Problema de conexão").
                            setContentTitle("Verifique sua conexão!").
                            setProgress(0, 0, false).
                            setSmallIcon(R.mipmap.ic_sem_internet);
                    mNotificationManager.notify(0, notificacao.build());
                    progress.setProgress(0);

//                        Toast.makeText(PrincipalActivity.this, "Servidor indisponivel", Toast.LENGTH_SHORT).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivInternet.setVisibility(View.VISIBLE);

                        }
                    });
                }

                if (db.contagem("SELECT COUNT(*) FROM TBL_TABELA_PRECO_ITENS") == 0) {
                    notificacao.setProgress(0, 0, true).
                            setContentText("Tabela de preços").
                            setContentTitle("Sincronia em andamento");
                    mNotificationManager.notify(0, notificacao.build());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setMessage("Tabela de preços");
                            progress.setIndeterminate(true);
                        }
                    });

                    try {

                        TabelaPrecoItem[] tabelaPrecoItem = banco.sincronizaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS;");

                        progress.setMax(tabelaPrecoItem.length);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivInternet.setVisibility(View.INVISIBLE);
                                progress.setIndeterminate(false);
                            }
                        });
                        for (int i = 0; tabelaPrecoItem.length > i; i++) {
                            notificacao.setProgress(tabelaPrecoItem.length, i, false);
                            progress.setProgress(i);
                            db.inserirTBL_TABELA_PRECO_ITENS(
                                    tabelaPrecoItem[i].getId_item(),
                                    tabelaPrecoItem[i].getId_tabela(),
                                    tabelaPrecoItem[i].getPerc_desc_inicial(),
                                    tabelaPrecoItem[i].getPerc_desc_final(),
                                    tabelaPrecoItem[i].getPerc_com_interno(),
                                    tabelaPrecoItem[i].getPerc_com_externo(),
                                    tabelaPrecoItem[i].getPerc_com_exportacao(),
                                    tabelaPrecoItem[i].getPontos_premiacao(),
                                    tabelaPrecoItem[i].getCor_painel(),
                                    tabelaPrecoItem[i].getCor_fonte(),
                                    tabelaPrecoItem[i].getVerba_perc(),
                                    tabelaPrecoItem[i].getUtiliza_verba(),
                                    tabelaPrecoItem[i].getDesconto_verba_max(),
                                    tabelaPrecoItem[i].getId_usuario(),
                                    tabelaPrecoItem[i].getUsuario(),
                                    tabelaPrecoItem[i].getUsuario_data(),
                                    tabelaPrecoItem[i].getCor_web()
                            );
                            mNotificationManager.notify(0, notificacao.build());
                        }

                        banco.Atualiza("UPDATE TBL_TABELA_PRECO_ITENS_SYNC SET SYNC = 'S' WHERE ID_WEB_USUARIO = " + id_usuario + ";");
                        notificacao.setContentText("Tabela de preços completo")
                                .setProgress(0, 0, false);
                        mNotificationManager.notify(0, notificacao.build());
                        progress.setProgress(0);
                    } catch (IOException | XmlPullParserException e) {

                        notificacao.setContentText("Problema de conexão").
                                setContentTitle("Verifique sua conexão!").
                                setProgress(0, 0, false).
                                setSmallIcon(R.mipmap.ic_sem_internet);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.setProgress(0);

//                        Toast.makeText(PrincipalActivity.this, "Servidor indisponivel", Toast.LENGTH_SHORT).show();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivInternet.setVisibility(View.VISIBLE);

                            }
                        });

                    }
                } else {
                    notificacao.setProgress(0, 0, true).
                            setContentText("Tabela de preços").
                            setContentTitle("Sincronia em andamento");
                    mNotificationManager.notify(0, notificacao.build());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setMessage("Tabela de preços");
                            progress.setIndeterminate(true);
                        }
                    });

                    try {
                        TabelaPrecoItem[] tabelaPrecoItem = banco.sincronizaTabelaPrecoItem("SELECT A.* FROM TBL_TABELA_PRECO_ITENS A INNER JOIN TBL_TABELA_PRECO_ITENS_SYNC B ON A.ID_ITEM = B.ID_ITEM WHERE B.SYNC = 'N' AND B.ID_WEB_USUARIO = " + id_usuario + ";");

                        progress.setMax(tabelaPrecoItem.length);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivInternet.setVisibility(View.INVISIBLE);
                                progress.setIndeterminate(false);
                            }
                        });

                        for (int i = 0; tabelaPrecoItem.length > i; i++) {
                            notificacao.setProgress(tabelaPrecoItem.length, i, false);
                            progress.setProgress(i);
                            if (db.contagem("SELECT COUNT(*) FROM TBL_TABELA_PRECO_ITENS WHERE ID_TABELA = " + tabelaPrecoItem[i].getId_tabela()) == 0) {
                                db.inserirTBL_TABELA_PRECO_ITENS(
                                        tabelaPrecoItem[i].getId_item(),
                                        tabelaPrecoItem[i].getId_tabela(),
                                        tabelaPrecoItem[i].getPerc_desc_inicial(),
                                        tabelaPrecoItem[i].getPerc_desc_final(),
                                        tabelaPrecoItem[i].getPerc_com_interno(),
                                        tabelaPrecoItem[i].getPerc_com_externo(),
                                        tabelaPrecoItem[i].getPerc_com_exportacao(),
                                        tabelaPrecoItem[i].getPontos_premiacao(),
                                        tabelaPrecoItem[i].getCor_painel(),
                                        tabelaPrecoItem[i].getCor_fonte(),
                                        tabelaPrecoItem[i].getVerba_perc(),
                                        tabelaPrecoItem[i].getUtiliza_verba(),
                                        tabelaPrecoItem[i].getDesconto_verba_max(),
                                        tabelaPrecoItem[i].getId_usuario(),
                                        tabelaPrecoItem[i].getUsuario(),
                                        tabelaPrecoItem[i].getUsuario_data(),
                                        tabelaPrecoItem[i].getCor_web());
                            } else {
                                db.atualizarTBL_TABELA_PRECO_ITENS(
                                        tabelaPrecoItem[i].getId_item(),
                                        tabelaPrecoItem[i].getId_tabela(),
                                        tabelaPrecoItem[i].getPerc_desc_inicial(),
                                        tabelaPrecoItem[i].getPerc_desc_final(),
                                        tabelaPrecoItem[i].getPerc_com_interno(),
                                        tabelaPrecoItem[i].getPerc_com_externo(),
                                        tabelaPrecoItem[i].getPerc_com_exportacao(),
                                        tabelaPrecoItem[i].getPontos_premiacao(),
                                        tabelaPrecoItem[i].getCor_painel(),
                                        tabelaPrecoItem[i].getCor_fonte(),
                                        tabelaPrecoItem[i].getVerba_perc(),
                                        tabelaPrecoItem[i].getUtiliza_verba(),
                                        tabelaPrecoItem[i].getDesconto_verba_max(),
                                        tabelaPrecoItem[i].getId_usuario(),
                                        tabelaPrecoItem[i].getUsuario(),
                                        tabelaPrecoItem[i].getUsuario_data(),
                                        tabelaPrecoItem[i].getCor_web());
                            }
                            mNotificationManager.notify(0, notificacao.build());
                        }
                        banco.Atualiza("UPDATE TBL_TABELA_PRECO_ITENS_SYNC SET SYNC = 'S' WHERE ID_WEB_USUARIO = " + id_usuario + ";");
                        notificacao.setContentText("Tabela de preços completo")
                                .setProgress(0, 0, false);
                        mNotificationManager.notify(0, notificacao.build());
                        progress.setProgress(0);
                    } catch (IOException | XmlPullParserException e) {

                        notificacao.setContentText("Problema de conexão").
                                setContentTitle("Verifique sua conexão!").
                                setProgress(0, 0, false).
                                setSmallIcon(R.mipmap.ic_sem_internet);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.setProgress(0);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(PrincipalActivity.this, "Servidor indisponivel", Toast.LENGTH_SHORT).show();
                                ivInternet.setVisibility(View.VISIBLE);

                            }
                        });
                    }
                }


                notificacao.setProgress(0, 0, true).
                        setContentText("Condições de pagamento").
                        setContentTitle("Sincronia em andamento");
                mNotificationManager.notify(0, notificacao.build());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setMessage("Condições de Pagamento");
                        progress.setIndeterminate(true);
                    }
                });

                try {
                    db.alterar("DELETE FROM TBL_CONDICOES_PAG_CAB;");
                    CondicoesPagamento[] condicoesPagamento = banco.sincronizaCondicoesPagamento("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE PUBLICAR_NA_WEB = 'S';");

                    progress.setMax(condicoesPagamento.length);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivInternet.setVisibility(View.INVISIBLE);
                        }
                    });
                    for (int i = 0; condicoesPagamento.length > i; i++) {
                        notificacao.setProgress(condicoesPagamento.length, i, false);
                        progress.setProgress(i);
                        db.inserirTBL_CONDICOES_PAG_CAB(
                                condicoesPagamento[i].getAtivo(),
                                condicoesPagamento[i].getId_condicao(),
                                condicoesPagamento[i].getNome_condicao(),
                                condicoesPagamento[i].getNumero_parcelas(),
                                condicoesPagamento[i].getIntervalo_dias(),
                                condicoesPagamento[i].getTipo_condicao(),
                                condicoesPagamento[i].getNfe_tipo_financeiro(),
                                condicoesPagamento[i].getNfe_mostrar_parcelas(),
                                condicoesPagamento[i].getUsuario_id(),
                                condicoesPagamento[i].getUsuario_nome(),
                                condicoesPagamento[i].getUsuario_data(),
                                condicoesPagamento[i].getPublicar_na_web());

                        mNotificationManager.notify(0, notificacao.build());
                    }
                    banco.Atualiza("UPDATE TBL_CONDICOES_PAG_CAB_SYNC SET SYNC = 'S' WHERE ID_WEB_USUARIO = " + id_usuario + ";");
                    notificacao.setContentText("Condições de pagamento")
                            .setProgress(0, 0, false);
                    mNotificationManager.notify(0, notificacao.build());
                    progress.setProgress(0);
                } catch (IOException | XmlPullParserException e) {

                    notificacao.setContentText("Problema de conexão").
                            setContentTitle("Verifique sua conexão!").
                            setProgress(0, 0, false).
                            setSmallIcon(R.mipmap.ic_sem_internet);
                    mNotificationManager.notify(0, notificacao.build());

                    progress.setProgress(0);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                                Toast.makeText(PrincipalActivity.this, "Servidor indisponivel", Toast.LENGTH_SHORT).show();
                            ivInternet.setVisibility(View.VISIBLE);

                        }
                    });
                }

                if (db.contagem("SELECT COUNT(*) FROM TBL_VENDEDOR_BONUS_RESUMO") == 0) {
                    notificacao.setProgress(0, 0, true).
                            setContentText("Bonus resumo").
                            setContentTitle("Sincronia em andamento");
                    mNotificationManager.notify(0, notificacao.build());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setMessage("Bonus resumo");
                            progress.setIndeterminate(true);
                        }
                    });

                    try {
                        VendedorBonusResumo[] vendedorBonusResumo = banco.sincronizaVendedorBonusResumo("SELECT * FROM TBL_VENDEDOR_BONUS_RESUMO;");

                        progress.setMax(vendedorBonusResumo.length);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivInternet.setVisibility(View.INVISIBLE);
                                progress.setIndeterminate(false);
                            }
                        });
                        for (int i = 0; vendedorBonusResumo.length > i; i++) {
                            notificacao.setProgress(vendedorBonusResumo.length, i, false);
                            progress.setProgress(i);
                            db.inserirTBL_VENDEDOR_BONUS_RESUMO(
                                    vendedorBonusResumo[i].getId_vendedor(),
                                    vendedorBonusResumo[i].getId_empresa(),
                                    vendedorBonusResumo[i].getValor_credito(),
                                    vendedorBonusResumo[i].getValor_debito(),
                                    vendedorBonusResumo[i].getValor_bonus_cancelados(),
                                    vendedorBonusResumo[i].getValor_saldo(),
                                    vendedorBonusResumo[i].getData_ultima_atualizacao());
                            mNotificationManager.notify(0, notificacao.build());
                        }

                        banco.Atualiza("UPDATE TBL_VENDEDOR_BONUS_RESUMO_SYNC SET SYNC = 'S' WHERE ID_WEB_USUARIO = " + id_usuario + ";");
                        Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(PrincipalActivity.this, 0, intent, 0);
                        notificacao.setContentText("Completo")
                                .setContentTitle("Sincronia concluída")
                                .setProgress(0, 0, false)
                                .setSmallIcon(R.mipmap.ic_sincronia_sucesso)
                                .setDefaults(NotificationCompat.DEFAULT_ALL)
                                .setPriority(2)
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.dismiss();

                        final AlertDialog.Builder alert = new AlertDialog.Builder(PrincipalActivity.this);
                        alert.setMessage("Completo!");
                        alert.setTitle("Sincronia concluída");
                        alert.setNeutralButton("OK", null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert.show();
                            }
                        });

                    } catch (IOException | XmlPullParserException e) {

                        notificacao.setContentText("Problema de conexão").
                                setContentTitle("Verifique sua conexão!").
                                setProgress(0, 0, false).
                                setSmallIcon(R.mipmap.ic_sem_internet);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.dismiss();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(PrincipalActivity.this, "Servidor indisponivel", Toast.LENGTH_SHORT).show();
                                ivInternet.setVisibility(View.VISIBLE);

                            }
                        });
                    }
                } else {
                    notificacao.setProgress(0, 0, true).
                            setContentText("Bonus resumo").
                            setContentTitle("Sincronia em andamento");
                    mNotificationManager.notify(0, notificacao.build());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setMessage("Bonus resumo");
                            progress.setIndeterminate(true);
                        }
                    });
                    try {

                        VendedorBonusResumo[] vendedorBonusResumo = banco.sincronizaVendedorBonusResumo("SELECT * FROM TBL_VENDEDOR_BONUS_RESUMO A INNER JOIN TBL_VENDEDOR_BONUS_RESUMO_SYNC B ON A.ID_VENDEDOR = B.ID_VENDEDOR WHERE B.SYNC = 'N' AND B.ID_WEB_USUARIO = " + id_usuario + ";");

                        progress.setMax(vendedorBonusResumo.length);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivInternet.setVisibility(View.INVISIBLE);
                                progress.setIndeterminate(false);
                            }
                        });

                        for (int i = 0; vendedorBonusResumo.length > i; i++) {
                            notificacao.setProgress(vendedorBonusResumo.length, i, false);
                            progress.setProgress(i);
                            if (db.contagem("SELECT COUNT(*) FROM TBL_VENDEDOR_BONUS_RESUMO WHERE ID_VENDEDOR = " + vendedorBonusResumo[i].getId_vendedor()) == 0) {
                                db.inserirTBL_VENDEDOR_BONUS_RESUMO(
                                        vendedorBonusResumo[i].getId_vendedor(),
                                        vendedorBonusResumo[i].getId_empresa(),
                                        vendedorBonusResumo[i].getValor_credito(),
                                        vendedorBonusResumo[i].getValor_debito(),
                                        vendedorBonusResumo[i].getValor_bonus_cancelados(),
                                        vendedorBonusResumo[i].getValor_saldo(),
                                        vendedorBonusResumo[i].getData_ultima_atualizacao());
                            } else {
                                db.atualizarTBL_VENDEDOR_BONUS_RESUMO(
                                        vendedorBonusResumo[i].getId_vendedor(),
                                        vendedorBonusResumo[i].getId_empresa(),
                                        vendedorBonusResumo[i].getValor_credito(),
                                        vendedorBonusResumo[i].getValor_debito(),
                                        vendedorBonusResumo[i].getValor_bonus_cancelados(),
                                        vendedorBonusResumo[i].getValor_saldo(),
                                        vendedorBonusResumo[i].getData_ultima_atualizacao());

                            }
                            mNotificationManager.notify(0, notificacao.build());
                        }
                        banco.Atualiza("UPDATE TBL_VENDEDOR_BONUS_RESUMO_SYNC SET SYNC = 'S' WHERE ID_WEB_USUARIO = " + id_usuario + ";");
                        Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(PrincipalActivity.this, 0, intent, 0);
                        notificacao.setContentText("Completo")
                                .setContentTitle("Sincronia concluída")
                                .setProgress(0, 0, false)
                                .setSmallIcon(R.mipmap.ic_sincronia_sucesso)
                                .setDefaults(NotificationCompat.DEFAULT_ALL)
                                .setPriority(2)
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.dismiss();

                        final AlertDialog.Builder alert = new AlertDialog.Builder(PrincipalActivity.this);
                        alert.setMessage("Completo!");
                        alert.setTitle("Sincronia concluída");
                        alert.setNeutralButton("OK", null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert.show();
                            }
                        });

                        System.gc();
                    } catch (IOException | XmlPullParserException e) {

                        notificacao.setContentText("Problema de conexão").
                                setContentTitle("Verifique sua conexão!").
                                setProgress(0, 0, false).
                                setSmallIcon(R.mipmap.ic_sem_internet);
                        mNotificationManager.notify(0, notificacao.build());

                        progress.dismiss();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(PrincipalActivity.this, "Servidor indisponivel", Toast.LENGTH_SHORT).show();
                                ivInternet.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }


            }
        });
        a.start();
    }
    */

    public void sincronizaApi() {

        final Rotas apiRotas = Api.buildRetrofit();

        Call<Sincronia> call = apiRotas.sincroniaApi();

        call.enqueue(new Callback<Sincronia>() {
            @Override
            public void onResponse(Call<Sincronia> call, Response<Sincronia> response) {
                sincronia = response.body();
                sincroniaBO.sincronizaBanco(sincronia, PrincipalActivity.this);
            }

            @Override
            public void onFailure(Call<Sincronia> call, Throwable t) {
                System.out.println();
            }
        });


    }

    public void sincronizaUsuario() {
        banco = new BancoWeb();
        b = new Thread(new Runnable() {
            @Override
            public void run() {
                DBHelper db = new DBHelper(PrincipalActivity.this);
                try {
                    final Bundle bundle = getIntent().getExtras();

                    Usuario[] listaUsuario = banco.sincronizaUsuario("SELECT * FROM TBL_WEB_USUARIO WHERE NOME_USUARIO = '" + bundle.getString("usuario") + "' AND ATIVO = 'S';");
                    try {
                        if (listaUsuario[0].getSenha().equals(db.consulta("SELECT SENHA FROM TBL_WEB_USUARIO WHERE NOME_USUARIO = '" + bundle.getString("usuario") + "'", "SENHA")) && listaUsuario[0].getAparelho_id()
                                .equals(db.consulta("SELECT APARELHO_ID FROM TBL_LOGIN;", "APARELHO_ID"))) {

                            System.out.println("Usuario ok!");

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ivInternet.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), "Usuario Alterado!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "Por favor, refaça o seu login!", Toast.LENGTH_LONG).show();
                                    DBHelper db = new DBHelper(PrincipalActivity.this);
                                    db.alterar("UPDATE TBL_LOGIN SET LOGADO = 'N';");
                                    Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    System.gc();
                                }
                            });
                        }
                    } catch (CursorIndexOutOfBoundsException e) {

                        ivInternet.setVisibility(View.INVISIBLE);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Usuario Alterado!", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Por favor, refaça o seu login!", Toast.LENGTH_LONG).show();
                            }
                        });
                        db.alterar("UPDATE TBL_LOGIN SET LOGADO = 'N';");
                        Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        System.gc();
                    }

                } catch (IOException | XmlPullParserException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivInternet.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
        b.start();
    }

    @Override
    protected void onResume() {
        System.gc();
        if (!b.isAlive()) {
            sincronizaUsuario();
        }
        if (getIntent().getIntExtra("alterado", 0) == 1) {
            sincronizaApi();
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

