package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
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
    private WebPedidoItens webPedidoItem;
    private Toolbar toolbar;
    private Button btnBuscarProduto;
    private EditText edtNomeProduto;
    private EditText edtTabelaPreco;
    private EditText edtQuantidade;
    private EditText edtValorProdutos;
    private EditText edtDesconto;
    private EditText edtDescontoReais;
    private EditText edtTotal;
    private MenuItem salvar_produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_pedido);
        ButterKnife.bind(this);

        PedidoHelper pedidoHelper = new PedidoHelper(this);

        try {
            webPedidoItem = pedidoHelper.getWebPedidoItem();

        } catch (Exception e) {
            e.printStackTrace();
        }

        toolbar = (Toolbar) findViewById(R.id.tb_produto_pedido);

        toolbar.setTitle("Item de venda");

        edtNomeProduto = (EditText) findViewById(R.id.edtNomeProduto);
        edtTabelaPreco = (EditText) findViewById(R.id.edtTabelaPreco);
        edtValorProdutos = (EditText) findViewById(R.id.edtValorProdutos);
        edtQuantidade = (EditText) findViewById(R.id.edtQuantidade);
        edtDesconto = (EditText) findViewById(R.id.edtDesconto);
        edtDescontoReais = (EditText) findViewById(R.id.edtDescontoReais);
        edtTotal = (EditText) findViewById(R.id.edtTotal);
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

        btnBuscarProduto = (Button) findViewById(R.id.btnBuscaProduto);
        btnBuscarProduto.setOnClickListener(new View.OnClickListener() {
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
                        webPedidoItem.setTipoDesconto("P");
                        break;
                    case R.id.real:
                        edtDesconto.setEnabled(false);
                        edtDescontoReais.setEnabled(true);
                        webPedidoItem.setTipoDesconto("R");
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
            btnBuscarProduto.setEnabled(false);
            rbReal.setClickable(false);
            rbPorcentagem.setClickable(false);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryCinza));
            this.setTheme(R.style.Theme_MeuTemaPedido);
        }
    }

    @Override
    protected void onResume() {
        try {
            try {
                if (PedidoHelper.getProduto() != null && getIntent().getIntExtra("pedido", 0) != 1 || PedidoHelper.getProduto().getId_produto() != PedidoHelper.getWebPedidoItem().getId_produto())
                    webPedidoItem = new WebPedidoItens(PedidoHelper.getProduto());
                else {
                    webPedidoItem = PedidoHelper.getWebPedidoItem();
                    if (webPedidoItem.getTipoDesconto().equals("R"))
                        rbReal.setChecked(true);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                webPedidoItem = PedidoHelper.getWebPedidoItem();
                if (webPedidoItem.getTipoDesconto().equals("R"))
                    rbReal.setChecked(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        edtNomeProduto.setText(webPedidoItem.getNome_produto());
        edtTabelaPreco.setText(MascaraUtil.mascaraReal(webPedidoItem.getVenda_preco()));
        edtDesconto.setText(webPedidoItem.getValor_desconto_per());
        edtDescontoReais.setText(webPedidoItem.getValor_desconto_real());
        edtQuantidade.setText(webPedidoItem.getQuantidade());
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
                if (webPedidoItem != null) {
                    if (!edtQuantidade.getText().toString().trim().isEmpty()) {
                        if (Float.parseFloat(edtQuantidade.getText().toString()) > 0) {
                            if (!edtTabelaPreco.getText().toString().isEmpty()) {
                                if (Float.parseFloat(webPedidoItem.getVenda_preco()) > 0) {

                                    webPedidoItem.setValor_unitario(Float.parseFloat(webPedidoItem.getVenda_preco()));

                                    if (getIntent().getIntExtra("pedido", 0) != 1) {
                                        PedidoHelper pedidoHelper = new PedidoHelper();
                                        pedidoHelper.inserirProduto(webPedidoItem);
                                    } else {
                                        PedidoHelper pedidoHelper = new PedidoHelper();
                                        pedidoHelper.alterarProduto(webPedidoItem, getIntent().getIntExtra("position", 0));
                                    }
                                    finish();
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
                    Float quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                    Float totalProdutoBruto = quantidade * Float.parseFloat(webPedidoItem.getVenda_preco());
                    Float desconto = 0.00f;
                    edtValorProdutos.setText(MascaraUtil.mascaraReal(totalProdutoBruto));
                    if (rbPorcentagem.isChecked()) {
                        if (!edtDesconto.getText().toString().trim().isEmpty()) {
                            desconto = (Float.parseFloat(edtDesconto.getText().toString()) * (totalProdutoBruto)) / 100;
                            edtDescontoReais.setText(MascaraUtil.duasCasaDecimal(desconto));
                            edtTotal.setText(MascaraUtil.mascaraReal(totalProdutoBruto - desconto));
                        } else {
                            edtDescontoReais.setText("0.00");
                            edtTotal.setText(MascaraUtil.mascaraReal(totalProdutoBruto));
                        }
                        webPedidoItem.setTipoDesconto("P");
                    } else if (rbReal.isChecked()) {
                        if (!edtDescontoReais.getText().toString().trim().isEmpty()) {
                            desconto = (Float.parseFloat(edtDescontoReais.getText().toString()) * 100) / totalProdutoBruto;
                            edtDesconto.setText(MascaraUtil.duasCasaDecimal(desconto));
                            edtTotal.setText(MascaraUtil.mascaraReal(totalProdutoBruto - Float.parseFloat(edtDescontoReais.getText().toString())));
                        } else {
                            edtDesconto.setText("0.00");
                            edtTotal.setText(MascaraUtil.mascaraReal(totalProdutoBruto));
                        }
                        webPedidoItem.setTipoDesconto("R");
                    }
                    webPedidoItem.setQuantidade(quantidade.toString());
                    webPedidoItem.setValor_total(String.valueOf(totalProdutoBruto - Float.parseFloat(edtDescontoReais.getText().toString())));
                    webPedidoItem.setValor_bruto(String.valueOf(totalProdutoBruto));
                    webPedidoItem.setValor_desconto_per(edtDesconto.getText().toString());
                    webPedidoItem.setValor_desconto_real(edtDescontoReais.getText().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
