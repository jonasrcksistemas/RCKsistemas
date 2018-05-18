package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.BO.PedidoBO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.PromocaoRetorno;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProdutoPedidoActivity extends AppCompatActivity {

    @BindView(R.id.rgRealPorc)
    RadioGroup rgRealPorc;
    @BindView(R.id.porcentagem)
    RadioButton rbPorcentagem;
    @BindView(R.id.real)
    RadioButton rbReal;
    @BindView(R.id.edtNomeProduto)
    EditText edtNomeProduto;
    @BindView(R.id.edtTabelaPreco)
    EditText edtTabelaPreco;
    @BindView(R.id.edtQuantidade)
    EditText edtQuantidade;
    @BindView(R.id.edtValorProdutos)
    EditText edtValorProdutos;
    @BindView(R.id.edtDesconto)
    EditText edtDesconto;
    @BindView(R.id.edtDescontoReais)
    EditText edtDescontoReais;
    @BindView(R.id.edtTotal)
    EditText edtTotal;
    @BindView(R.id.tb_produto_pedido)
    Toolbar toolbar;
    @BindView(R.id.btnBuscaProduto)
    Button btnBuscaProduto;
    @BindView(R.id.cdPromocao)
    CardView cdPromocao;
    @BindView(R.id.txtPromocao)
    TextView txtPromocao;
    private WebPedidoItens webPedidoItem;
    private MenuItem salvar_produto;
    private DBHelper db;
    private TabelaPrecoItem tabelaPrecoItem;
    private PedidoBO pedidoBO = new PedidoBO();
    private PromocaoRetorno promocaoRetorno;
    private Float quantidade;
    private Float totalProdutoBruto;
    private Float desconto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_pedido);
        ButterKnife.bind(this);

        PedidoHelper pedidoHelper = new PedidoHelper(this);

        db = new DBHelper(this);
        try {
            webPedidoItem = pedidoHelper.getWebPedidoItem();

            tabelaPrecoItem = db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_CATEGORIA = " + ClienteHelper.getCliente().getIdCategoria()).get(0);

            rbPorcentagem.setText("Desconto %(max " + tabelaPrecoItem.getPerc_desc_final() + "%)");
        } catch (Exception e) {
            e.printStackTrace();
        }

        toolbar.setTitle("Item de venda");

        edtQuantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculaDesconto();
            }
        });

        edtDesconto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (rbPorcentagem.isChecked())
                    calculaDesconto();
            }
        });

        edtDescontoReais.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (rbReal.isChecked())
                    calculaDesconto();
            }
        });

        btnBuscaProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProdutoPedidoActivity.this, ActivityProduto.class);
                intent.putExtra("acao", 2);
                startActivity(intent);
            }
        });

        edtNomeProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProdutoPedidoActivity.this, ActivityProduto.class);
                intent.putExtra("acao", 2);
                startActivity(intent);
            }
        });

        rgRealPorc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.porcentagem:
                        edtDesconto.setEnabled(true);
                        edtDescontoReais.setEnabled(false);
                        break;
                    case R.id.real:
                        edtDesconto.setEnabled(false);
                        edtDescontoReais.setEnabled(true);
                        break;
                }
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getIntExtra("vizualizacao", 0) == 1) {
            edtNomeProduto.setFocusable(false);
            edtTabelaPreco.setFocusable(false);
            edtValorProdutos.setFocusable(false);
            edtQuantidade.setFocusable(false);
            edtDesconto.setFocusable(false);
            edtDescontoReais.setFocusable(false);
            edtTotal.setFocusable(false);
            edtQuantidade.setFocusable(false);
            btnBuscaProduto.setEnabled(false);
            rbReal.setClickable(false);
            rbPorcentagem.setClickable(false);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryCinza));
            this.setTheme(R.style.Theme_MeuTemaPedido);
        }
    }

    @Override
    protected void onResume() {
        try {
            if (PedidoHelper.getProduto() != null && PedidoHelper.getProduto().getId_produto() != PedidoHelper.getWebPedidoItem().getId_produto()) {
                webPedidoItem = new WebPedidoItens(PedidoHelper.getProduto());
                if (Float.parseFloat(edtDesconto.getText().toString()) <= 0)
                    edtDesconto.setText(tabelaPrecoItem.getPerc_desc_final());
            } else {
                webPedidoItem = PedidoHelper.getWebPedidoItem();
                edtQuantidade.setText(webPedidoItem.getQuantidade().toString().replace(".0", ""));
                edtDesconto.setText(webPedidoItem.getValor_desconto_per());
                edtDescontoReais.setText(webPedidoItem.getValor_desconto_real());
                if (webPedidoItem.getTipoDesconto().equals("R"))
                    rbReal.setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                webPedidoItem = new WebPedidoItens(PedidoHelper.getProduto());
                if (Float.parseFloat(edtDesconto.getText().toString()) <= 0)
                    edtDesconto.setText(tabelaPrecoItem.getPerc_desc_final());
            } catch (NullPointerException nullPointer) {
                nullPointer.printStackTrace();
            }
        }

        if (!webPedidoItem.getProduto_tercerizacao().equals("S") && !webPedidoItem.getProduto_materia_prima().equals("S")) {
            edtDesconto.setEnabled(true);
            edtDescontoReais.setEnabled(true);
            rbPorcentagem.setEnabled(true);
            rbReal.setEnabled(true);
            promocaoRetorno = pedidoBO.calculaDesconto(ClienteHelper.getCliente().getId_cadastro(), webPedidoItem.getId_produto(), ProdutoPedidoActivity.this);
            if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0 && promocaoRetorno.getValorDesconto() > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final())) {
                rbPorcentagem.setText("Desconto %(max " + promocaoRetorno.getValorDesconto().toString().replace(".0", "") + "%)");
                cdPromocao.setVisibility(View.VISIBLE);
                txtPromocao.setText("**PRODUTO EM PROMOÇÃO**\n" + promocaoRetorno.getNomePromocao());
            } else {
                cdPromocao.setVisibility(View.GONE);
                rbPorcentagem.setText("Desconto %(max " + tabelaPrecoItem.getPerc_desc_final() + "%)");
            }
        } else {
            rbPorcentagem.setEnabled(false);
            rbReal.setEnabled(false);
            edtDesconto.setEnabled(false);
            edtDesconto.setText("00");
            edtDescontoReais.setEnabled(false);
            edtDescontoReais.setText("0.00");
            cdPromocao.setVisibility(View.VISIBLE);
            txtPromocao.setText("PRODUTO COM PREÇO FIXO");
            rbPorcentagem.setText("Desconto %(max 0%)");
        }


        edtNomeProduto.setText(webPedidoItem.getNome_produto());
        edtTabelaPreco.setText(MascaraUtil.mascaraVirgula(webPedidoItem.getVenda_preco()));
        calculaDesconto();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (getIntent().getIntExtra("vizualizacao", 0) != 1) {
            getMenuInflater().inflate(R.menu.menu_produto_pedido, menu);
            salvar_produto = menu.findItem(R.id.menu_salvar_produto);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_salvar_produto:
                Float descontoPedido;
                if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0 && promocaoRetorno.getValorDesconto() > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final()))
                    descontoPedido = promocaoRetorno.getValorDesconto();
                else
                    descontoPedido = Float.parseFloat(tabelaPrecoItem.getPerc_desc_final());

                if (edtDesconto.getText().toString().trim().isEmpty())
                    edtDesconto.setText("0.00");
                if (edtDescontoReais.getText().toString().trim().isEmpty())
                    edtDescontoReais.setText("0.00");

                if (webPedidoItem != null) {
                    if (!edtQuantidade.getText().toString().trim().isEmpty()) {
                        if (Float.parseFloat(edtQuantidade.getText().toString()) > 0) {
                            if (!edtTabelaPreco.getText().toString().isEmpty()) {
                                if (Float.parseFloat(webPedidoItem.getVenda_preco()) > 0) {
                                    if (Float.parseFloat(edtDesconto.getText().toString()) <= descontoPedido) {

                                        webPedidoItem.setValor_unitario(Float.parseFloat(webPedidoItem.getVenda_preco()));
                                        webPedidoItem.setQuantidade(quantidade.toString());
                                        webPedidoItem.setValor_total(String.valueOf(totalProdutoBruto - Float.parseFloat(edtDescontoReais.getText().toString())));
                                        webPedidoItem.setValor_bruto(String.valueOf(totalProdutoBruto));
                                        webPedidoItem.setValor_desconto_per(edtDesconto.getText().toString());
                                        webPedidoItem.setValor_desconto_real(edtDescontoReais.getText().toString());

                                        if (rbPorcentagem.isChecked())
                                            webPedidoItem.setTipoDesconto("P");
                                        else if (rbReal.isChecked())
                                            webPedidoItem.setTipoDesconto("R");

                                        if (getIntent().getIntExtra("pedido", 0) != 1) {
                                            PedidoHelper pedidoHelper = new PedidoHelper();
                                            pedidoHelper.inserirProduto(webPedidoItem);
                                        } else {
                                            PedidoHelper pedidoHelper = new PedidoHelper();
                                            pedidoHelper.alterarProduto(webPedidoItem, getIntent().getIntExtra("position", 0));
                                        }
                                        finish();
                                    } else {
                                        Toast.makeText(this, "Desconto ultrapassou o limite da categoria!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "O preço não pode ser zero!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "Informe o preço do produto!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "A quantidade não pode ser zero!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Informe a quantidade", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Informe um produto", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        PedidoHelper.setWebPedidoItem(null);
        PedidoHelper.setProduto(null);
        System.gc();
        super.onDestroy();
    }

    private void calculaDesconto() {
        try {
            if (webPedidoItem != null) {
                if (!edtQuantidade.getText().toString().trim().isEmpty()) {
                    quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                    totalProdutoBruto = quantidade * Float.parseFloat(webPedidoItem.getVenda_preco());

                    edtValorProdutos.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto));
                    if (rbPorcentagem.isChecked()) {
                        if (!edtDesconto.getText().toString().trim().isEmpty()) {
                            desconto = (Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100;
                            edtDescontoReais.setText(MascaraUtil.duasCasaDecimal(desconto));
                            edtTotal.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto - desconto));
                        } else {
                            edtDescontoReais.setText("0.00");
                            edtTotal.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto));
                        }
                    } else if (rbReal.isChecked()) {
                        if (!edtDescontoReais.getText().toString().trim().isEmpty()) {
                            desconto = (Float.parseFloat(edtDescontoReais.getText().toString()) * 100) / totalProdutoBruto;
                            edtDesconto.setText(MascaraUtil.duasCasaDecimal(desconto));
                            edtTotal.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto - Float.parseFloat(edtDescontoReais.getText().toString())));
                        } else {
                            edtDesconto.setText("0.00");
                            edtTotal.setText(MascaraUtil.mascaraVirgula(totalProdutoBruto));
                        }
                    }
                    if (promocaoRetorno != null && promocaoRetorno.getValorDesconto() > 0 && promocaoRetorno.getValorDesconto() > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final())) {
                        if (Float.parseFloat(edtDesconto.getText().toString()) > promocaoRetorno.getValorDesconto())
                            edtDesconto.setBackgroundResource(R.drawable.borda_edittext_erro);
                        else
                            edtDesconto.setBackgroundResource(R.drawable.borda_edittext);
                    } else if (Float.parseFloat(edtDesconto.getText().toString()) > Float.parseFloat(tabelaPrecoItem.getPerc_desc_final())) {
                        edtDesconto.setBackgroundResource(R.drawable.borda_edittext_erro);
                    } else {
                        edtDesconto.setBackgroundResource(R.drawable.borda_edittext);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
