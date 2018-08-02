package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.HistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinanceiroResumoActivity extends AppCompatActivity {

    @BindView(R.id.idToolbarFinanceiro)
    Toolbar toolbar;
    @BindView(R.id.txtVencida)
    TextView txtVencida;
    @BindView(R.id.txtAvencer)
    TextView txtAvencer;
    @BindView(R.id.txtQuitada)
    TextView txtQuitada;
    @BindView(R.id.lyVencida)
    LinearLayout lyVencida;
    @BindView(R.id.lyVencer)
    LinearLayout lyVencer;
    @BindView(R.id.lyQuitada)
    LinearLayout lyQuitada;
    @BindView(R.id.txtLimiteCredito)
    TextView txtLimiteCredito;
    @BindView(R.id.lySincronia)
    LinearLayout lySincronia;
    @BindView(R.id.txtDataHoraSincroniza)
    TextView txtDataHoraSincroniza;
    private ProgressDialog progress;
    private AlertDialog.Builder alert;
    private MenuItem sincroniza;
    private CadastroFinanceiroResumo cadastroFinanceiroResumo;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro_resumo);
        ButterKnife.bind(this);

        db = new DBHelper(this);

        try {
            toolbar.setSubtitle(HistoricoFinanceiroHelper.getCliente().getNome_cadastro());

            cadastroFinanceiroResumo = HistoricoFinanceiroHelper.getCadastroFinanceiroResumo();
        } catch (NullPointerException e) {
            e.printStackTrace();
            AlertDialog.Builder alert = new AlertDialog.Builder(FinanceiroResumoActivity.this);
            alert.setMessage("Deseja carregar historico financeiro detalhado desse cliente?");
            alert.setNegativeButton("NÂO", null);
            alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    carregarHistoricoFinanceiro(HistoricoFinanceiroHelper.getCliente().getId_cadastro_servidor());
                }
            });
            alert.show();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lyVencida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HistoricoFinanceiroHelper.getHistoricoFinanceiro() != null) {
                    Intent intent = new Intent(FinanceiroResumoActivity.this, FinanceiroDetalheActivity.class);
                    intent.putExtra("financeiro", 1);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(FinanceiroResumoActivity.this);
                    alert.setMessage("Deseja carregar historico financeiro detalhado desse cliente?");
                    alert.setNegativeButton("NÂO", null);
                    alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carregarHistoricoFinanceiro(HistoricoFinanceiroHelper.getCliente().getId_cadastro_servidor());
                        }
                    });
                    alert.show();
                }
            }
        });

        lyVencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HistoricoFinanceiroHelper.getHistoricoFinanceiro() != null) {
                    Intent intent = new Intent(FinanceiroResumoActivity.this, FinanceiroDetalheActivity.class);
                    intent.putExtra("financeiro", 2);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(FinanceiroResumoActivity.this);
                    alert.setMessage("Deseja carregar historico financeiro detalhado desse cliente?");
                    alert.setNegativeButton("NÂO", null);
                    alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carregarHistoricoFinanceiro(HistoricoFinanceiroHelper.getCliente().getId_cadastro_servidor());
                        }
                    });
                    alert.show();
                }
            }
        });

        lyQuitada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HistoricoFinanceiroHelper.getHistoricoFinanceiro() != null) {
                    Intent intent = new Intent(FinanceiroResumoActivity.this, FinanceiroDetalheActivity.class);
                    intent.putExtra("financeiro", 3);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(FinanceiroResumoActivity.this);
                    alert.setMessage("Deseja carregar historico financeiro detalhado desse cliente?");
                    alert.setNegativeButton("NÂO", null);
                    alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            carregarHistoricoFinanceiro(HistoricoFinanceiroHelper.getCliente().getId_cadastro_servidor());
                        }
                    });
                    alert.show();
                }
            }
        });

        try {
            txtVencida.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getFinanceiroVencido()));

            txtAvencer.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getFinanceiroVencer()));

            txtQuitada.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getFinanceiroQuitado()));


            if (HistoricoFinanceiroHelper.getCliente().getLimite_credito() != null && !HistoricoFinanceiroHelper.getCliente().getLimite_credito().trim().isEmpty())
                txtLimiteCredito.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCliente().getLimite_credito()));
            else
                txtLimiteCredito.setText("< Não cadastrado >");

            try {
                txtDataHoraSincroniza.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse(cadastroFinanceiroResumo.getDataUltimaAtualizacao())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        carregarHistoricoFinanceiro(HistoricoFinanceiroHelper.getCliente().getId_cadastro_servidor());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);
        sincroniza = menu.findItem(R.id.menu_sincroniza);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_sincroniza:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Deseja carregar historico financeiro detalhado desse cliente?");
                alert.setNegativeButton("NÂO", null);
                alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        carregarHistoricoFinanceiro(HistoricoFinanceiroHelper.getCliente().getId_cadastro_servidor());
                    }
                });
                alert.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void carregarHistoricoFinanceiro(int idCliente) {
        progress = new ProgressDialog(FinanceiroResumoActivity.this);
        progress.setMessage("Carregando historico financeiro!");
        progress.setCancelable(false);
        progress.show();

        Rotas apiRotas = Api.buildRetrofit();
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<HistoricoFinanceiro> call = apiRotas.getHistoricoFinanceiro(idCliente, cabecalho);

        call.enqueue(new Callback<HistoricoFinanceiro>() {
            @Override
            public void onResponse(Call<HistoricoFinanceiro> call, Response<HistoricoFinanceiro> response) {

                HistoricoFinanceiroHelper.setHistoricoFinanceiro(response.body());

                txtVencida.setText(MascaraUtil.mascaraReal(response.body().getTotalVencida()));

                txtAvencer.setText(MascaraUtil.mascaraReal(response.body().getTotalAvencer()));

                txtQuitada.setText(MascaraUtil.mascaraReal(response.body().getTotalQuitado()));

                if (HistoricoFinanceiroHelper.getCliente().getLimite_credito() != null && !HistoricoFinanceiroHelper.getCliente().getLimite_credito().trim().isEmpty())
                    txtLimiteCredito.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCliente().getLimite_credito()));
                else
                    txtLimiteCredito.setText("< Não cadastrado >");

                /*CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
                cadastroFinanceiroResumoDAO.atualizarCadastroFinanceiroResumo(response.body().getCadastroFinanceiroResumo());*/
                HistoricoFinanceiroHelper.setCadastroFinanceiroResumo(response.body().getCadastroFinanceiroResumo());

                lySincronia.setVisibility(View.GONE);

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<HistoricoFinanceiro> call, Throwable t) {
                alert = new AlertDialog.Builder(FinanceiroResumoActivity.this);
                alert.setTitle("Atenção!");
                alert.setMessage("Não foi possivel carregar relatorio!\n        Verifique sua conexão com a internet!");
                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alert.show();
                progress.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        try {
            txtVencida.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalVencida()));

            txtAvencer.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalAvencer()));

            txtQuitada.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalQuitado()));

            if (HistoricoFinanceiroHelper.getCliente().getLimite_credito() != null && !HistoricoFinanceiroHelper.getCliente().getLimite_credito().trim().isEmpty())
                txtLimiteCredito.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCliente().getLimite_credito()));
            else
                txtLimiteCredito.setText("< Não cadastrado >");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        HistoricoFinanceiroHelper.limparDados();
        super.onDestroy();
    }
}
