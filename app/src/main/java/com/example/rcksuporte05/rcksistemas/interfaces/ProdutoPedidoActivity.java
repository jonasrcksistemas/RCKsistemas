package com.example.rcksuporte05.rcksistemas.interfaces;

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
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Produto;
import com.example.rcksuporte05.rcksistemas.classes.WebPedidoItens;

public class ProdutoPedidoActivity extends AppCompatActivity {

    private static Produto objetoProduto;
    private static WebPedidoItens webPedidoItem;
    private Toolbar toolbar;
    private Button btnBuscarProduto;
    private EditText edtNomeProduto;
    private EditText edtTabelaPreco;
    private EditText edtQuantidade;
    //    private EditText edtValorProdutos;
//    private EditText edtDesconto;
//    private EditText edtDescontoReais;
//    private EditText edtPrecoPago;
    private EditText edtTotal;
    //    private Spinner spFaixaPadrao;
//    private ArrayAdapter<TabelaPrecoItem> adapterFaixaPadrao;
    private MenuItem salvar_produto;
    private Float precoVenda;
    private Float quantidade;
    //    private Float descontoReais;
//    private Float precoPago;
    private Float total;
    private Float valorBruto;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_pedido);

        PedidoHelper pedidoHelper = new PedidoHelper(this);

        bundle = getIntent().getExtras();

        try {
            webPedidoItem = pedidoHelper.getWebPedidoItem();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        toolbar = (Toolbar) findViewById(R.id.tb_produto_pedido);

        toolbar.setTitle("Item de venda");

        edtNomeProduto = (EditText) findViewById(R.id.edtNomeProduto);
        edtTabelaPreco = (EditText) findViewById(R.id.edtTabelaPreco);
//        edtValorProdutos = (EditText) findViewById(R.id.edtValorProdutos);
        edtQuantidade = (EditText) findViewById(R.id.edtQuantidade);
        /*edtDesconto = (EditText) findViewById(R.id.edtDesconto);
        edtDescontoReais = (EditText) findViewById(R.id.edtDescontoReais);
        edtPrecoPago = (EditText) findViewById(R.id.edtPrecoPago);*/
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
                if (objetoProduto != null) {
                    try {
                        objetoProduto.setVenda_preco(edtTabelaPreco.getText().toString());
                        precoVenda = Float.parseFloat(objetoProduto.getVenda_preco());
                        quantidade = Float.parseFloat(edtQuantidade.getText().toString());
//                        descontoReais = (Float.parseFloat(adapterFaixaPadrao.getItem(spFaixaPadrao.getSelectedItemPosition()).getPerc_desc_inicial()) / 100 * (precoVenda * quantidade));
                        valorBruto = Float.parseFloat(objetoProduto.getVenda_preco()) * Float.parseFloat(edtQuantidade.getText().toString());
//                        precoPago = (((precoVenda * quantidade) - descontoReais) / quantidade);
                        total = ((precoVenda * quantidade) /*- descontoReais*/);

//                        edtPrecoPago.setText(String.format("R$%.4f", precoPago));
//                        edtDescontoReais.setText(String.format("%.2f", descontoReais));
                        edtTotal.setText(String.format("R$%.2f", total));
//                        edtValorProdutos.setText(String.format("R$%.2f", valorBruto));
                    } catch (NumberFormatException | NullPointerException e) {
                        /*try {
                            edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(objetoProduto.getVenda_preco())));
                        } catch (NullPointerException e2) {
                            edtValorProdutos.setText("R$00,00");
                        }*/
                    }
                } else if (webPedidoItem != null) {
                    try {
                        webPedidoItem.setVenda_preco(edtTabelaPreco.getText().toString());
                        precoVenda = Float.parseFloat(webPedidoItem.getVenda_preco());
                        quantidade = Float.parseFloat(edtQuantidade.getText().toString());
//                        descontoReais = (Float.parseFloat(adapterFaixaPadrao.getItem(spFaixaPadrao.getSelectedItemPosition()).getPerc_desc_inicial()) / 100 * (precoVenda * quantidade));
                        valorBruto = Float.parseFloat(webPedidoItem.getVenda_preco()) * Float.parseFloat(edtQuantidade.getText().toString());
//                        precoPago = (((precoVenda * quantidade) - descontoReais) / quantidade);
                        total = ((precoVenda * quantidade) /*- descontoReais*/);

//                        edtPrecoPago.setText(String.format("R$%.4f", precoPago));
//                        edtDescontoReais.setText(String.format("%.2f", descontoReais));
                        edtTotal.setText(String.format("R$%.2f", total));
//                        edtValorProdutos.setText(String.format("R$%.2f", valorBruto));
                    } catch (NumberFormatException | NullPointerException e) {
                        try {
//                            edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(webPedidoItem.getVenda_preco())));
                        } catch (NullPointerException e2) {
//                            edtValorProdutos.setText("R$00,00");
                        }
                    }
                }
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
                if (objetoProduto != null) {
                    try {
                        objetoProduto.setVenda_preco(edtTabelaPreco.getText().toString());
                        precoVenda = Float.parseFloat(objetoProduto.getVenda_preco());
                        quantidade = Float.parseFloat(edtQuantidade.getText().toString());
//                        descontoReais = (Float.parseFloat(adapterFaixaPadrao.getItem(spFaixaPadrao.getSelectedItemPosition()).getPerc_desc_inicial()) / 100 * (precoVenda * quantidade));
                        valorBruto = Float.parseFloat(objetoProduto.getVenda_preco()) * Float.parseFloat(edtQuantidade.getText().toString());
//                        precoPago = (((precoVenda * quantidade) - descontoReais) / quantidade);
                        total = ((precoVenda * quantidade) /*- descontoReais*/);

//                        edtPrecoPago.setText(String.format("R$%.4f", precoPago));
//                        edtDescontoReais.setText(String.format("%.2f", descontoReais));
                        edtTotal.setText(String.format("R$%.2f", total));
//                        edtValorProdutos.setText(String.format("R$%.2f", valorBruto));
                    } catch (NumberFormatException | NullPointerException e) {
                        /*try {
                            edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(objetoProduto.getVenda_preco())));
                        } catch (NullPointerException e2) {
                            edtValorProdutos.setText("R$00,00");
                        }*/
                    }
                } else if (webPedidoItem != null) {
                    try {
                        webPedidoItem.setVenda_preco(edtTabelaPreco.getText().toString());
                        precoVenda = Float.parseFloat(webPedidoItem.getVenda_preco());
                        quantidade = Float.parseFloat(edtQuantidade.getText().toString());
//                        descontoReais = (Float.parseFloat(adapterFaixaPadrao.getItem(spFaixaPadrao.getSelectedItemPosition()).getPerc_desc_inicial()) / 100 * (precoVenda * quantidade));
                        valorBruto = Float.parseFloat(webPedidoItem.getVenda_preco()) * Float.parseFloat(edtQuantidade.getText().toString());
//                        precoPago = (((precoVenda * quantidade) - descontoReais) / quantidade);
                        total = ((precoVenda * quantidade) /*- descontoReais*/);

//                        edtPrecoPago.setText(String.format("R$%.4f", precoPago));
//                        edtDescontoReais.setText(String.format("%.2f", descontoReais));
                        edtTotal.setText(String.format("R$%.2f", total));
//                        edtValorProdutos.setText(String.format("R$%.2f", valorBruto));
                    } catch (NumberFormatException | NullPointerException e) {
                        try {
//                            edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(webPedidoItem.getVenda_preco())));
                        } catch (NullPointerException e2) {
//                            edtValorProdutos.setText("R$00,00");
                        }
                    }
                }
            }
        });

        btnBuscarProduto = (Button) findViewById(R.id.btnBuscaProduto);
        btnBuscarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(ProdutoPedidoActivity.this, ActivityProduto.class);
                bundle.putInt("acao", 1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        /*try {
            spFaixaPadrao = (Spinner) findViewById(R.id.spFaixaPadrao);
            adapterFaixaPadrao = new ArrayAdapter(ProdutoPedidoActivity.this, android.R.layout.simple_list_item_activated_1, db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE PONTOS_PREMIACAO > 0;"));
            spFaixaPadrao.setAdapter(adapterFaixaPadrao);
        } catch (CursorIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        spFaixaPadrao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (objetoProduto != null) {
                    edtDesconto.setText(String.format("%.2f", Float.parseFloat(adapterFaixaPadrao.getItem(position).getPerc_desc_inicial())) + "%");
                    try {
                        precoVenda = Float.parseFloat(objetoProduto.getVenda_preco());
                        quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                        descontoReais = (Float.parseFloat(adapterFaixaPadrao.getItem(spFaixaPadrao.getSelectedItemPosition()).getPerc_desc_inicial()) / 100 * (precoVenda * quantidade));
                        valorBruto = Float.parseFloat(objetoProduto.getVenda_preco()) * Float.parseFloat(edtQuantidade.getText().toString());
                        precoPago = ((precoVenda * quantidade) - descontoReais) / quantidade;
                        total = ((precoVenda * quantidade) - descontoReais);

                        edtPrecoPago.setText(String.format("R$%.4f", precoPago));
                        edtDescontoReais.setText(String.format("%.2f", descontoReais));
                        edtTotal.setText(String.format("R$%.2f", total));
                        edtValorProdutos.setText(String.format("R$%.2f", valorBruto));
                    } catch (NumberFormatException | NullPointerException e) {
                        try {
                            edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(objetoProduto.getVenda_preco())));
                        } catch (NullPointerException e2) {
                            edtValorProdutos.setText("R$00,00");
                        }
                    }
                } else if (webPedidoItem != null) {
                    edtDesconto.setText(String.format("%.2f", Float.parseFloat(adapterFaixaPadrao.getItem(position).getPerc_desc_inicial())) + "%");
                    try {
                        precoVenda = Float.parseFloat(webPedidoItem.getVenda_preco());
                        quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                        descontoReais = (Float.parseFloat(adapterFaixaPadrao.getItem(spFaixaPadrao.getSelectedItemPosition()).getPerc_desc_inicial()) / 100 * (precoVenda * quantidade));
                        valorBruto = Float.parseFloat(webPedidoItem.getVenda_preco()) * Float.parseFloat(edtQuantidade.getText().toString());
                        precoPago = ((precoVenda * quantidade) - descontoReais) / quantidade;
                        total = ((precoVenda * quantidade) - descontoReais);

                        edtPrecoPago.setText(String.format("R$%.4f", precoPago));
                        edtDescontoReais.setText(String.format("%.2f", descontoReais));
                        edtTotal.setText(String.format("R$%.2f", total));
                        edtValorProdutos.setText(String.format("R$%.2f", valorBruto));
                    } catch (NumberFormatException | NullPointerException e) {
                        try {
                            edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(webPedidoItem.getVenda_preco())));
                        } catch (NullPointerException e2) {
                            edtValorProdutos.setText("R$00,00");
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*if (getIntent().getIntExtra("pedido", 0) == 1) {
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!Objects.equals(webPedidoItem.getTabela_preco_faixa().getPerc_desc_inicial(), adapterFaixaPadrao.getItem(i).getPerc_desc_inicial()));
                spFaixaPadrao.setSelection(i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }*/

        if (getIntent().getIntExtra("vizualizacao", 0) == 1) {
            edtNomeProduto.setFocusable(false);
            edtTabelaPreco.setFocusable(false);
//            edtValorProdutos.setFocusable(false);
            edtQuantidade.setFocusable(false);
//            edtDesconto.setFocusable(false);
//            edtDescontoReais.setFocusable(false);
//            edtPrecoPago.setFocusable(false);
            edtTotal.setFocusable(false);
            edtQuantidade.setFocusable(false);
//            spFaixaPadrao.setEnabled(false);
            btnBuscarProduto.setEnabled(false);
        }
    }

    public void pegaProduto(Produto produto) {
        objetoProduto = produto;
    }

    public void pegaProduto(WebPedidoItens webPedidoItem) {
        ProdutoPedidoActivity.webPedidoItem = webPedidoItem;
    }

    @Override
    protected void onResume() {
        if (objetoProduto != null) {
            try {
                edtNomeProduto.setText(objetoProduto.getNome_produto());
                edtTabelaPreco.setText(objetoProduto.getVenda_preco());
                precoVenda = Float.parseFloat(objetoProduto.getVenda_preco());
                try {
                    quantidade = Float.parseFloat(String.valueOf(edtQuantidade.getText()));
                } catch (NumberFormatException e) {
                    quantidade = Float.parseFloat("1");
                }
//                descontoReais = (Float.parseFloat(adapterFaixaPadrao.getItem(spFaixaPadrao.getSelectedItemPosition()).getPerc_desc_inicial()) / 100 * (precoVenda * quantidade));
                valorBruto = Float.parseFloat(objetoProduto.getVenda_preco()) * quantidade;
//                precoPago = ((precoVenda * quantidade) - descontoReais) / quantidade;
                total = ((precoVenda * quantidade) /*- descontoReais*/);

//                edtPrecoPago.setText(String.format("R$%.4f", precoPago));
//                edtDescontoReais.setText(String.format("%.2f", descontoReais));
                edtTotal.setText(String.format("R$%.2f", total));
//                edtValorProdutos.setText(String.format("R$%.2f", valorBruto));

            } catch (Exception e) {
//                edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(objetoProduto.getVenda_preco())));
                System.out.println(e.toString());
            }
        } else if (webPedidoItem != null) {
            try {
                edtNomeProduto.setText(webPedidoItem.getNome_produto());
                edtTabelaPreco.setText(webPedidoItem.getValor_unitario());
                precoVenda = Float.parseFloat(webPedidoItem.getVenda_preco());
                try {
                    quantidade = Float.parseFloat(edtQuantidade.getText().toString());
                } catch (NumberFormatException e) {
                    quantidade = Float.parseFloat(webPedidoItem.getQuantidade());
                }
//                descontoReais = (Float.parseFloat(adapterFaixaPadrao.getItem(spFaixaPadrao.getSelectedItemPosition()).getPerc_desc_inicial()) / 100 * (precoVenda * quantidade));
                valorBruto = Float.parseFloat(webPedidoItem.getVenda_preco()) * quantidade;
//                precoPago = ((precoVenda * quantidade) - descontoReais) / quantidade;
                total = ((precoVenda * quantidade) /*- descontoReais*/);

                edtQuantidade.setText(quantidade.toString());
//                edtPrecoPago.setText(String.format("R$%.4f", precoPago));
//                edtDescontoReais.setText(String.format("%.2f", descontoReais));
                edtTotal.setText(String.format("R$%.2f", total));
//                edtValorProdutos.setText(String.format("R$%.2f", valorBruto));

            } catch (Exception e) {
//                edtValorProdutos.setText(String.format("R$%.2f", Float.parseFloat(objetoProduto.getVenda_preco())));
                System.out.println(e.toString());
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
                                    if (Float.parseFloat(edtTabelaPreco.getText().toString()) > 0) {

                                        WebPedidoItens webPedidoItem = new WebPedidoItens(objetoProduto/*, adapterFaixaPadrao.getItem(spFaixaPadrao.getSelectedItemPosition())*/);
                                        webPedidoItem.setQuantidade(quantidade.toString());
                                        webPedidoItem.setValor_total(total.toString());
                                        webPedidoItem.setValor_unitario(edtTabelaPreco.getText().toString());
                                        webPedidoItem.setValor_bruto(valorBruto.toString());
                                        webPedidoItem.setValor_unitario(edtTabelaPreco.getText().toString());
                                /*webPedidoItem.setValor_preco_pago(precoPago.toString());
                                webPedidoItem.setValor_desconto_real(descontoReais.toString());
                                webPedidoItem.setPontos_total(String.valueOf(total * Float.parseFloat(adapterFaixaPadrao.getItem(spFaixaPadrao.getSelectedItemPosition()).getPontos_premiacao())));
                                if (Float.parseFloat(webPedidoItem.getPontos_total()) > 0) {
                                    webPedidoItem.setPontos_coeficiente(String.valueOf(total / Float.parseFloat(webPedidoItem.getPontos_total())));
                                } else {
                                    webPedidoItem.setPontos_coeficiente("0");
                                }*/

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
                            }
                        } else {
                            Toast.makeText(this, "Informe a quantidade", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Informe um produto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (webPedidoItem != null) {
                        if (!edtQuantidade.getText().toString().isEmpty()) {
                            if (Float.parseFloat(edtQuantidade.getText().toString()) > 0) {
                                if (!edtTabelaPreco.getText().toString().isEmpty()) {
                                    if (Float.parseFloat(edtTabelaPreco.getText().toString()) > 0) {

                                        if (objetoProduto != null) {
                                            webPedidoItem.setProduto(objetoProduto);
                                        }
                                        webPedidoItem.setValor_unitario(edtTabelaPreco.getText().toString());
//                                webPedidoItem.setTabela_preco_faixa(adapterFaixaPadrao.getItem(spFaixaPadrao.getSelectedItemPosition()));
                                        webPedidoItem.setQuantidade(quantidade.toString());
                                        webPedidoItem.setValor_total(total.toString());
                                        webPedidoItem.setValor_bruto(valorBruto.toString());
//                                webPedidoItem.setValor_preco_pago(precoPago.toString());

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
}
