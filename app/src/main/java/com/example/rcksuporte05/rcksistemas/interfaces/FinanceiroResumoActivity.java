package com.example.rcksuporte05.rcksistemas.interfaces;

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

import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

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

    private ProgressDialog progress;
    private AlertDialog.Builder alert;
    private MenuItem sincroniza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro_resumo);
        ButterKnife.bind(this);

        toolbar.setSubtitle(HistoricoFinanceiroHelper.getCliente().getNome_cadastro());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lyVencida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinanceiroResumoActivity.this, FinanceiroDetalheActivity.class);
                intent.putExtra("financeiro", 1);
                startActivity(intent);
            }
        });

        lyVencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinanceiroResumoActivity.this, FinanceiroDetalheActivity.class);
                intent.putExtra("financeiro", 2);
                startActivity(intent);
            }
        });

        lyQuitada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinanceiroResumoActivity.this, FinanceiroDetalheActivity.class);
                intent.putExtra("financeiro", 3);
                startActivity(intent);
            }
        });
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
                carregarHistoricoFinanceiro(Integer.parseInt(HistoricoFinanceiroHelper.getCliente().getId_cadastro()));
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
        } catch (NullPointerException e) {
            carregarHistoricoFinanceiro(Integer.parseInt(HistoricoFinanceiroHelper.getCliente().getId_cadastro()));
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        HistoricoFinanceiroHelper.limparDados();
        super.onDestroy();
    }
}
