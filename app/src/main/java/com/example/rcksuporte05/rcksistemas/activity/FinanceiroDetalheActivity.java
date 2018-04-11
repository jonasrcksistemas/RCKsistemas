package com.example.rcksuporte05.rcksistemas.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;

import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterHistoricoFinanceiroPendentes;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterHistoricoFinanceiroQuitado;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.HistoricoFinanceiro;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FinanceiroDetalheActivity extends AppCompatActivity {

    @BindView(R.id.tbFinanceiro)
    Toolbar toolbar;
    @BindView(R.id.trLinhaColunaQuitado)
    TableRow trLinhaColunaQuitado;
    @BindView(R.id.trLinhaColunaPendente)
    TableRow trLinhaColunaPendente;
    @BindView(R.id.lstHistoricoFinanceiro)
    ListView lstHistoricoFinanceiro;
    @BindView(R.id.edtTotalTitulos)
    EditText edtTotalTitulos;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financeiro_detalhe);
        ButterKnife.bind(this);

        toolbar.setSubtitle(HistoricoFinanceiroHelper.getCliente().getNome_cadastro());

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarHistoricoFinanceiro(HistoricoFinanceiroHelper.getCliente().getId_cadastro());
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        preencheLista();
        super.onResume();
    }

    public void preencheLista() {
        switch (getIntent().getIntExtra("financeiro", 0)) {
            case 1:
                toolbar.setTitle("Historico Financeiro - Vencidas");

                trLinhaColunaQuitado.setVisibility(View.GONE);
                trLinhaColunaPendente.setVisibility(View.VISIBLE);

                ListaAdapterHistoricoFinanceiroPendentes listaAdapterHistoricoFinanceiroVencido = new ListaAdapterHistoricoFinanceiroPendentes(this, HistoricoFinanceiroHelper.getHistoricoFinanceiro().getListaVencida());
                lstHistoricoFinanceiro.setAdapter(listaAdapterHistoricoFinanceiroVencido);

                edtTotalTitulos.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalVencida()));
                break;
            case 2:
                toolbar.setTitle("Historico Financeiro - A vencer");

                trLinhaColunaQuitado.setVisibility(View.GONE);
                trLinhaColunaPendente.setVisibility(View.VISIBLE);

                ListaAdapterHistoricoFinanceiroPendentes listaAdapterHistoricoFinanceiroAvencer = new ListaAdapterHistoricoFinanceiroPendentes(this, HistoricoFinanceiroHelper.getHistoricoFinanceiro().getListaAvencer());
                lstHistoricoFinanceiro.setAdapter(listaAdapterHistoricoFinanceiroAvencer);

                edtTotalTitulos.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalAvencer()));
                break;
            case 3:
                toolbar.setTitle("Historico Financeiro - Quitados");

                trLinhaColunaQuitado.setVisibility(View.VISIBLE);
                trLinhaColunaPendente.setVisibility(View.GONE);

                ListaAdapterHistoricoFinanceiroQuitado listaAdapterHistoricoFinanceiroQuitado = new ListaAdapterHistoricoFinanceiroQuitado(this, HistoricoFinanceiroHelper.getHistoricoFinanceiro().getListaQuitado());
                lstHistoricoFinanceiro.setAdapter(listaAdapterHistoricoFinanceiroQuitado);

                edtTotalTitulos.setText(MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getHistoricoFinanceiro().getTotalQuitado()));
                break;
        }
    }

    public void carregarHistoricoFinanceiro(int idCliente) {
        swipe.setRefreshing(true);

        Rotas apiRotas = Api.buildRetrofit();
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<HistoricoFinanceiro> call = apiRotas.getHistoricoFinanceiro(idCliente, cabecalho);

        call.enqueue(new Callback<HistoricoFinanceiro>() {
            @Override
            public void onResponse(Call<HistoricoFinanceiro> call, Response<HistoricoFinanceiro> response) {

                HistoricoFinanceiroHelper.setHistoricoFinanceiro(response.body());

                preencheLista();

                swipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<HistoricoFinanceiro> call, Throwable t) {
                swipe.setRefreshing(false);
                alert = new AlertDialog.Builder(FinanceiroDetalheActivity.this);
                alert.setTitle("Atenção!");
                alert.setMessage("Não foi possivel carregar relatorio!\n        Verifique sua conexão com a internet!");
                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alert.show();
            }
        });
    }
}
