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

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProdutoPedidoActivity extends AppCompatActivity {

    private static Produto objetoProduto;
    private static WebPedidoItens webPedidoItem;
    @BindView(R.id.rgRealPorc)
    RadioGroup rgRealPorc;
    @BindView(R.id.porcentagem)
    RadioButton rbPorcentagem;
    @BindView(R.id.real)
    RadioButton rbReal;
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
    private Float precoVenda;
    private Float precoUnitario;
    private Float quantidade;
    private Float descontoReais;
    private Float precoPago;
    private Float total;
    private Float valorBruto;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_pedido);
        ButterKnife.bind(this);

        PedidoHelper pedidoHelper = new PedidoHelper(this);

        db = new DBHelper(ProdutoPedidoActivity.this);

        try {
            webPedidoItem = pedidoHelper.getWebPedidoItem();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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

        edtTabelaPreco.addTextChangedListener(new TextWatcher() {
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
            btnBuscarProduto.setEnabled(false);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryCinza));
            this.setTheme(R.style.Theme_MeuTemaPedido);
        }
    }

    public void pegaProduto(Produto produto) {
        objetoProduto = produto;
    }

    @Override
    protected void onResume() {
        if (objetoProduto != null) {
            try {
                edtNomeProduto.setText(objetoProduto.getNome_produto());
                edtTabelaPreco.setText(MascaraUtil.mascaraReal(objetoProduto.getVenda_preco()));
                precoVenda = Float.parseFloat(objetoProduto.getVenda_preco());
                try {
                    quantidade = Float.parseFloat(String.valueOf(edtQuantidade.getText()));
                } catch (NumberFormatException e) {
                    quantidade = Float.parseFloat("1");
                }
                descontoReais = (Float.parseFloat(edtDesconto.getText().toString()) / 100 * (precoVenda * quantidade));
                valorBruto = Float.parseFloat(objetoProduto.getVenda_preco()) * quantidade;
                precoPago = ((precoVenda * quantidade) - descontoReais) / quantidade;
                total = ((precoVenda * quantidade) /*- descontoReais*/);

                edtDescontoReais.setText(String.valueOf(descontoReais));
                edtTotal.setText(String.format("R$%.2f", 0));
                edtValorProdutos.setText(MascaraUtil.mascaraReal(valorBruto));

            } catch (Exception e) {
                edtValorProdutos.setText(MascaraUtil.mascaraReal(Float.parseFloat(objetoProduto.getVenda_preco())));
                e.printStackTrace();
            }
        } else if (webPedidoItem != null) {
            try {
                edtNomeProduto.setText(webPedidoItem.getNome_produto());
                edtTabelaPreco.setText(MascaraUtil.mascaraReal(webPedidoItem.getVenda_preco()));
                precoVenda = Float.parseFloat(webPedidoItem.getVenda_preco());
                try {
                    quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                } catch (NumberFormatException e) {
                    quantidade = Float.parseFloat(webPedidoItem.getQuantidade());
                }
                descontoReais = (Float.parseFloat(edtDesconto.getText().toString()) / 100 * (precoVenda * quantidade));
                valorBruto = Float.parseFloat(webPedidoItem.getVenda_preco()) * quantidade;
                precoPago = ((precoVenda * quantidade) - descontoReais) / quantidade;
                total = (precoVenda * quantidade);

                edtQuantidade.setText(quantidade.toString());
                edtDescontoReais.setText(String.valueOf(descontoReais));
                edtTotal.setText(String.format("R$%.2f", 0.f));
                edtValorProdutos.setText(MascaraUtil.mascaraReal(valorBruto));

            } catch (Exception e) {
                edtValorProdutos.setText(MascaraUtil.mascaraReal(Float.parseFloat(objetoProduto.getVenda_preco())));
                e.printStackTrace();
            }
        }

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
                if (getIntent().getIntExtra("pedido", 0) != 1) {
                    if (objetoProduto != null) {
                        if (!edtQuantidade.getText().toString().isEmpty()) {
                            if (Float.parseFloat(edtQuantidade.getText().toString()) > 0) {
                                if (!edtTabelaPreco.getText().toString().trim().isEmpty()) {
                                    if (Float.parseFloat(objetoProduto.getVenda_preco()) > 0) {

                                        WebPedidoItens webPedidoItem = new WebPedidoItens(objetoProduto);
                                        webPedidoItem.setQuantidade(quantidade.toString());
                                        webPedidoItem.setValor_total(total.toString());
                                        webPedidoItem.setValor_unitario(edtTabelaPreco.getText().toString());
                                        webPedidoItem.setValor_bruto(valorBruto.toString());
                                        webPedidoItem.setValor_unitario(objetoProduto.getVenda_preco());
                                        webPedidoItem.setValor_preco_pago(precoPago.toString());
                                        webPedidoItem.setValor_desconto_real(descontoReais.toString());
                                        if (Float.parseFloat(webPedidoItem.getPontos_total()) > 0) {
                                            webPedidoItem.setPontos_coeficiente(String.valueOf(total / Float.parseFloat(webPedidoItem.getPontos_total())));
                                        } else {
                                            webPedidoItem.setPontos_coeficiente("0");
                                        }

                                        PedidoHelper pedidoHelper = new PedidoHelper();
                                        pedidoHelper.inserirProduto(webPedidoItem);
                                        finish();
                                    } else {
                                        Toast.makeText(this, "O preço não pode ser zero!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(this, "Informe o preço do produto!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "A quantidade não pode ser zero!", Toast.LENGTH_SHORT).show();
                                edtQuantidade.setError("A quantidade não pode ser zero!");
                            }
                        } else {
                            Toast.makeText(this, "Informe a quantidade", Toast.LENGTH_SHORT).show();
                            edtQuantidade.setError("Informe a quantidade");
                        }
                    } else {
                        Toast.makeText(this, "Informe um produto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (webPedidoItem != null) {
                        if (!edtQuantidade.getText().toString().isEmpty()) {
                            if (Float.parseFloat(edtQuantidade.getText().toString()) > 0) {
                                if (!edtTabelaPreco.getText().toString().isEmpty()) {
                                    if (Float.parseFloat(webPedidoItem.getValor_unitario()) > 0) {

                                        if (objetoProduto != null) {
                                            webPedidoItem.setProduto(objetoProduto);
                                        }
                                        webPedidoItem.setValor_unitario(edtTabelaPreco.getText().toString());
                                        webPedidoItem.setQuantidade(quantidade.toString());
                                        webPedidoItem.setValor_total(total.toString());
                                        webPedidoItem.setValor_bruto(valorBruto.toString());
                                        webPedidoItem.setValor_preco_pago(precoPago.toString());

                                        PedidoHelper pedidoHelper = new PedidoHelper();
                                        pedidoHelper.alterarProduto(webPedidoItem, getIntent().getIntExtra("position", 0));
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        objetoProduto = null;
        webPedidoItem = null;
        PedidoHelper.setWebPedidoItem(null);
        System.gc();
        super.onDestroy();
    }

    public void calculaDesconto() {
        if (rbPorcentagem.isChecked()) {
            if (objetoProduto != null) {
                try {
                    precoVenda = Float.parseFloat(objetoProduto.getVenda_preco());
                    quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                    try {
                        descontoReais = (Float.parseFloat(edtDesconto.getText().toString()) / 100 * (precoVenda * quantidade));
                    } catch (Exception e) {
                        e.printStackTrace();
                        descontoReais = 0.00f;
                    }
                    valorBruto = Float.parseFloat(objetoProduto.getVenda_preco()) * Float.parseFloat(edtQuantidade.getText().toString());
                    precoPago = ((precoVenda * quantidade) - descontoReais) / quantidade;
                    total = ((precoVenda * quantidade) - descontoReais);

                    edtDescontoReais.setText(String.valueOf(descontoReais));
                    edtTotal.setText(MascaraUtil.mascaraReal(total));
                    edtValorProdutos.setText(MascaraUtil.mascaraReal(valorBruto));
                } catch (NumberFormatException | NullPointerException e) {
                    e.printStackTrace();
                    try {
                        edtTotal.setText("R$00.00");
                        edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(objetoProduto.getVenda_preco())));
                    } catch (NullPointerException e2) {
                        e.printStackTrace();
                        edtValorProdutos.setText("R$00.00");
                    }
                }
            } else if (webPedidoItem != null) {
                try {
                    precoVenda = Float.parseFloat(webPedidoItem.getVenda_preco());
                    quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                    try {
                        descontoReais = (Float.parseFloat(edtDesconto.getText().toString()) / 100 * (precoVenda * quantidade));
                    } catch (Exception e) {
                        e.printStackTrace();
                        descontoReais = 0.00f;
                    }
                    valorBruto = Float.parseFloat(webPedidoItem.getVenda_preco()) * Float.parseFloat(edtQuantidade.getText().toString());
                    precoPago = ((precoVenda * quantidade) - descontoReais) / quantidade;
                    total = ((precoVenda * quantidade) - descontoReais);

                    edtDescontoReais.setText(String.valueOf(descontoReais));
                    edtTotal.setText(MascaraUtil.mascaraReal(total));
                    edtValorProdutos.setText(MascaraUtil.mascaraReal(valorBruto));
                } catch (NumberFormatException | NullPointerException e) {
                    try {
                        edtTotal.setText("R$00.00");
                        edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(webPedidoItem.getVenda_preco())));
                    } catch (NullPointerException e2) {
                        edtValorProdutos.setText("R$00.00");
                    }
                }
            }
        } else if (rbReal.isChecked()) {
            if (objetoProduto != null) {
                try {
                    precoVenda = Float.parseFloat(objetoProduto.getVenda_preco());
                    quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                    try {
                        descontoReais = (Float.parseFloat(edtDescontoReais.getText().toString()) * 100) / (precoVenda * quantidade);
                    } catch (Exception e) {
                        e.printStackTrace();
                        descontoReais = 0.00f;
                    }
                    valorBruto = Float.parseFloat(objetoProduto.getVenda_preco()) * Float.parseFloat(edtQuantidade.getText().toString());
                    total = ((precoVenda * quantidade) - Float.parseFloat(edtDescontoReais.getText().toString()));

                    edtDesconto.setText(String.valueOf(descontoReais));
                    edtTotal.setText(MascaraUtil.mascaraReal(total));
                    edtValorProdutos.setText(MascaraUtil.mascaraReal(valorBruto));
                } catch (NumberFormatException | NullPointerException e) {
                    e.printStackTrace();
                    try {
                        edtTotal.setText("R$00.00");
                        edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(objetoProduto.getVenda_preco())));
                    } catch (NullPointerException e2) {
                        e.printStackTrace();
                        edtValorProdutos.setText("R$00.00");
                    }
                }
            } else if (webPedidoItem != null) {
                try {
                    precoVenda = Float.parseFloat(webPedidoItem.getVenda_preco());
                    quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                    try {
                        descontoReais = (Float.parseFloat(edtDescontoReais.getText().toString()) * 100 / (precoVenda * quantidade));
                    } catch (Exception e) {
                        e.printStackTrace();
                        descontoReais = 0.00f;
                    }
                    valorBruto = Float.parseFloat(webPedidoItem.getVenda_preco()) * Float.parseFloat(edtQuantidade.getText().toString());
                    precoPago = ((precoVenda * quantidade) - descontoReais) / quantidade;
                    total = ((precoVenda * quantidade) - Float.parseFloat(edtDescontoReais.getText().toString()));

                    edtDesconto.setText(String.valueOf(descontoReais));
                    edtTotal.setText(MascaraUtil.mascaraReal(total));
                    edtValorProdutos.setText(MascaraUtil.mascaraReal(valorBruto));
                } catch (NumberFormatException | NullPointerException e) {
                    try {
                        edtTotal.setText("R$00.00");
                        edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(webPedidoItem.getVenda_preco())));
                    } catch (NullPointerException e2) {
                        edtValorProdutos.setText("R$00.00");
                    }
                }
            }
        }
    }
}
