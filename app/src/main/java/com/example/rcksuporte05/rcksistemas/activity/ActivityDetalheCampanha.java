package com.example.rcksuporte05.rcksistemas.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.Helper.CampanhaHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityDetalheCampanha extends Activity {

    @BindView(R.id.txtNomeCampanha)
    TextView txtNomeCampanha;
    @BindView(R.id.txtNomeCliente)
    TextView txtNomeCliente;
    @BindView(R.id.edtNomeProdutoLinha)
    EditText edtNomeProdutoLinha;
    @BindView(R.id.btnInfoCampanha)
    Button btnInfoCampanha;
    @BindView(R.id.txtTipoCampanha1)
    TextView txtTipoCampanha1;
    @BindView(R.id.edtQuantidadeLinha)
    EditText edtQuantidadeLinha;
    @BindView(R.id.txtTipoCampanha2)
    TextView txtTipoCampanha2;
    @BindView(R.id.txtIdProduto)
    TextView txtIdProduto;
    @BindView(R.id.edtNomeProduto)
    EditText edtNomeProduto;
    @BindView(R.id.edtQuantidade)
    EditText edtQuantidade;
    private CampanhaComercialItens campanhaComercialItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_campanha);
        ButterKnife.bind(this);

        campanhaComercialItens = CampanhaHelper.getItemCampanhaDetalhe();

        if (CampanhaHelper.getCampanhaComercialCab().getNomeCampanha() != null)
            txtNomeCampanha.setText(CampanhaHelper.getCampanhaComercialCab().getNomeCampanha());

        if (ClienteHelper.getCliente().getNome_cadastro() != null)
            txtNomeCliente.setText(ClienteHelper.getCliente().getNome_cadastro());

        switch (campanhaComercialItens.getIdTipoCampanha()) {
            case 1:
                txtTipoCampanha1.setText("Pague");
                txtTipoCampanha2.setText("Leve");
                break;
            case 2:
                txtTipoCampanha1.setText("Compre");
                txtTipoCampanha2.setText("Ganhe");
                break;
        }

        edtNomeProdutoLinha.setText(campanhaComercialItens.getNomeProdutoLinha());
        edtQuantidadeLinha.setText(MascaraUtil.mascaraVirgula(campanhaComercialItens.getQuantidadeVenda()));

        edtNomeProduto.setText(campanhaComercialItens.getNomeProdutoBonus());
        try {
            edtQuantidade.setText(String.valueOf(campanhaComercialItens.getQuantidadeBonus()).replace(".0", ""));
        } catch (Exception e) {
            e.printStackTrace();
            edtQuantidade.setText(String.valueOf(campanhaComercialItens.getQuantidadeBonus()));
        }

        if (campanhaComercialItens.getIdLinhaProduto() > 0) {
            btnInfoCampanha.setVisibility(View.VISIBLE);
            btnInfoCampanha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ActivityDetalheCampanha.this, ActivityItemLinhaProduto.class);
                    intent.putExtra("linha", campanhaComercialItens.getIdLinhaProduto());
                    intent.putExtra("nomelinha", campanhaComercialItens.getNomeProdutoLinha());
                    startActivity(intent);
                }
            });
        }
    }
}

