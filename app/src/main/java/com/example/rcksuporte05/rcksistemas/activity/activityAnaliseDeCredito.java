package com.example.rcksuporte05.rcksistemas.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick(R.id.btnOk)
    public void sair() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise_de_credito);
        ButterKnife.bind(this);

        cadastroFinanceiroResumo = HistoricoFinanceiroHelper.getCadastroFinanceiroResumo();

        txtNomeCliente.setText(HistoricoFinanceiroHelper.getCliente().getNome_cadastro());

        if (HistoricoFinanceiroHelper.getCliente().getLimite_credito() != null && !HistoricoFinanceiroHelper.getCliente().getLimite_credito().trim().isEmpty())
            txtLimiteCredito.setText("(+)" + MascaraUtil.mascaraReal(HistoricoFinanceiroHelper.getCliente().getLimite_credito()));

        txtValorPedido.setText("(-)" + MascaraUtil.mascaraReal(getIntent().getFloatExtra("valorPedido", 0)));

        if (cadastroFinanceiroResumo.getFinanceiroVencido() != null && cadastroFinanceiroResumo.getFinanceiroVencido() > 0)
            txtPendenciaFinanceira.setText("(*)" + MascaraUtil.mascaraReal(cadastroFinanceiroResumo.getFinanceiroVencido()));

        if (cadastroFinanceiroResumo.getLimiteUtilizado() != null && cadastroFinanceiroResumo.getLimiteUtilizado() > 0)
            txtLimiteUtilizado.setText("(-)" + MascaraUtil.mascaraReal(cadastroFinanceiroResumo.getLimiteUtilizado()));

        Float saldoRestante = cadastroFinanceiroResumo.getLimiteCredito() - cadastroFinanceiroResumo.getLimiteUtilizado() - getIntent().getFloatExtra("valorPedido", 0);

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
}
