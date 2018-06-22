package com.example.rcksuporte05.rcksistemas.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroFinanceiroResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activityAnaliseDeCredito extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txtNomeCliente)
    TextView txtNomeCliente;

    @BindView(R.id.txtStatus)
    TextView txtStatus;

    @BindView(R.id.txtPendenciaFinanceira)
    TextView txtPendenciaFinanceira;

    @BindView(R.id.txtLimiteCredito)
    TextView txtLimiteCredito;

    @BindView(R.id.txtValorPedido)
    TextView txtValorPedido;

    @BindView(R.id.txtLimiteUtilizado)
    TextView txtLimiteUtilizado;

    @BindView(R.id.txtSaldoRestante)
    TextView txtSaldoRestante;

    private CadastroFinanceiroResumo cadastroFinanceiroResumo;
    private MenuItem sincroniza;
    private ProgressDialog progress;
    private DBHelper db;

    @OnClick(R.id.btnOk)
    public void sair() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise_de_credito);
        ButterKnife.bind(this);

        db = new DBHelper(activityAnaliseDeCredito.this);

        atualizaDados();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void atualizaDados() {
        cadastroFinanceiroResumo = HistoricoFinanceiroHelper.getCadastroFinanceiroResumo();

        txtNomeCliente.setText(HistoricoFinanceiroHelper.getCliente().getNome_cadastro());

        if (HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getLimiteCredito() != null && HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getLimiteCredito() > 0)
            txtLimiteCredito.setText("(+)" + MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCadastroFinanceiroResumo().getLimiteCredito()));
        else
            txtLimiteCredito.setText("(+)" + MascaraUtil.mascaraReal(0.f));

        txtValorPedido.setText("(-)" + MascaraUtil.mascaraReal(getIntent().getFloatExtra("valorPedido", 0)));

        if (cadastroFinanceiroResumo.getFinanceiroVencido() != null && cadastroFinanceiroResumo.getFinanceiroVencido() > 0)
            txtPendenciaFinanceira.setText("(*)" + MascaraUtil.mascaraReal(cadastroFinanceiroResumo.getFinanceiroVencido()));
        else
            txtPendenciaFinanceira.setText("(*)R$ NÃO TEM");

        Float limiteUltilizado = db.soma("SELECT SUM(VALOR_TOTAL) FROM TBL_WEB_PEDIDO " +
                "WHERE PEDIDO_ENVIADO = 'N' AND ID_CONDICAO_PAGAMENTO <> 1 " +
                "AND ID_WEB_PEDIDO <> " + PedidoHelper.getIdPedido() + " " +
                "AND ID_CADASTRO = " + ClienteHelper.getCliente().getId_cadastro() + ";") + cadastroFinanceiroResumo.getLimiteUtilizado();

        if (limiteUltilizado != null && limiteUltilizado > 0)
            txtLimiteUtilizado.setText("(-)" + MascaraUtil.mascaraReal(limiteUltilizado));
        else
            txtLimiteUtilizado.setText("(-)" + MascaraUtil.mascaraReal(0.f));

        Float saldoRestante = cadastroFinanceiroResumo.getLimiteCredito() - limiteUltilizado - getIntent().getFloatExtra("valorPedido", 0) - cadastroFinanceiroResumo.getFinanceiroVencido();

        if (saldoRestante < 0 || cadastroFinanceiroResumo.getFinanceiroVencido() > 0) {
            txtStatus.setText("<NEGADO>");
            txtStatus.setTextColor(Color.RED);
            txtSaldoRestante.setText("(=)" + MascaraUtil.mascaraReal(saldoRestante));
            txtSaldoRestante.setTextColor(Color.RED);
        } else {
            txtStatus.setText("<APROVADO>");
            txtStatus.setTextColor(Color.parseColor("#0277BD"));
            txtSaldoRestante.setText("(=)" + MascaraUtil.mascaraReal(saldoRestante));
            txtSaldoRestante.setTextColor(Color.parseColor("#0277BD"));
        }
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
                atualizaFinanceiro(HistoricoFinanceiroHelper.getCliente().getId_cadastro_servidor());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void atualizaFinanceiro(int idCadastroServidor) {
        progress = new ProgressDialog(activityAnaliseDeCredito.this);
        progress.setMessage("Carregando financeiro!");
        progress.setCancelable(false);
        progress.show();

        Rotas apiRotas = Api.buildRetrofit();
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<CadastroFinanceiroResumo> call = apiRotas.atualizaFinanceiro(idCadastroServidor, cabecalho);

        call.enqueue(new Callback<CadastroFinanceiroResumo>() {
            @Override
            public void onResponse(Call<CadastroFinanceiroResumo> call, Response<CadastroFinanceiroResumo> response) {
                CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
                cadastroFinanceiroResumoDAO.atualizarCadastroFinanceiroResumo(response.body());
                HistoricoFinanceiroHelper.setCadastroFinanceiroResumo(response.body());
                atualizaDados();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<CadastroFinanceiroResumo> call, Throwable t) {
                AlertDialog.Builder alert = new AlertDialog.Builder(activityAnaliseDeCredito.this);
                alert.setTitle("Atenção!");
                alert.setMessage("Não foi possivel atualizar o financeiro deste cliente!\n        Verifique sua conexão com a internet!");
                alert.setNeutralButton("OK", null);
                alert.show();
                progress.dismiss();
            }
        });
    }
}
