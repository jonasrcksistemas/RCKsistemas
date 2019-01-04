package com.example.rcksuporte05.rcksistemas.activity;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
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
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.CampanhaHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.PromocaoRetorno;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.btnSubtraiQuantidade)
    Button btnSubtraiQuantidade;
    @BindView(R.id.btnSomaQuantidade)
    Button btnSomaQuantidade;
    @BindView(R.id.edtNomeCampanha)
    EditText edtNomeCampanha;
    @BindView(R.id.btnBuscaCampanha)
    Button btnBuscaCampanha;

    private WebPedidoItens webPedidoItem;
    private MenuItem salvar_produto;
    private DBHelper db;
    private TabelaPrecoItem tabelaPrecoItem;
    private PedidoBO pedidoBO = new PedidoBO();
    private PromocaoRetorno promocaoRetorno;
    private Float quantidade;
    private Float totalProdutoBruto;
    private Float desconto;
    private CampanhaComercialCabDAO campanhaComercialCabDAO;
    private boolean verificaCampanha;

    @OnClick(R.id.btnSomaQuantidade)
    public void somaQuantidade() {
        try {
            Float valor = Float.parseFloat(edtQuantidade.getText().toString());
            valor++;
            if (valor <= 0) {
                btnSubtraiQuantidade.setClickable(false);
                edtQuantidade.setText("0");
            } else {
                btnSubtraiQuantidade.setClickable(true);
                edtQuantidade.setText(String.valueOf(valor).replace(".0", ""));
            }
        } catch (Exception e) {
            edtQuantidade.setText("1");
            btnSubtraiQuantidade.setClickable(false);
        }
    }

    @OnClick(R.id.btnSubtraiQuantidade)
    public void subtraiQuantidade() {
        try {
            Float valor = Float.parseFloat(edtQuantidade.getText().toString());
            valor--;
            if (valor <= 0) {
                btnSubtraiQuantidade.setClickable(false);
                edtQuantidade.setText("0");
            } else {
                btnSubtraiQuantidade.setClickable(true);
                edtQuantidade.setText(String.valueOf(valor).replace(".0", ""));
            }
        } catch (Exception e) {
            edtQuantidade.setText("0");
            btnSubtraiQuantidade.setClickable(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_pedido);
        ButterKnife.bind(this);

        PedidoHelper pedidoHelper = new PedidoHelper(this);

        verificaCampanha = true;

        db = new DBHelper(this);
        campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);
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
                try {
                    Float valor = Float.parseFloat(edtQuantidade.getText().toString());
                    if (valor <= 0) {
                        btnSubtraiQuantidade.setClickable(false);
                    } else {
                        btnSubtraiQuantidade.setClickable(true);
                    }
                } catch (Exception e) {
                    btnSubtraiQuantidade.setClickable(false);
                }
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

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getIntExtra("vizualizacao", 0) == 1) {
            edtNomeProduto.setFocusable(false);
            edtNomeCampanha.setFocusable(false);
            edtTabelaPreco.setFocusable(false);
            edtValorProdutos.setFocusable(false);
            edtQuantidade.setFocusable(false);
            edtDesconto.setFocusable(false);
            edtDescontoReais.setFocusable(false);
            edtTotal.setFocusable(false);
            edtQuantidade.setFocusable(false);
            btnBuscaProduto.setEnabled(false);
            btnBuscaCampanha.setEnabled(false);
            rbReal.setClickable(false);
            rbPorcentagem.setClickable(false);
            btnSubtraiQuantidade.setVisibility(View.INVISIBLE);
            btnSomaQuantidade.setVisibility(View.INVISIBLE);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryCinza));
            this.setTheme(R.style.Theme_MeuTemaPedido);
        } else {
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

            btnBuscaCampanha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (PedidoHelper.getProduto() != null) {
                        if (!verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getProduto())) {
                            Toast.makeText(ProdutoPedidoActivity.this, "Nenhuma campanha encontrada", Toast.LENGTH_LONG).show();
                            CampanhaHelper.setCampanhaComercialCab(null);
                            edtNomeCampanha.setText("");
                            webPedidoItem.setIdCampanha(0);
                        }
                    } else if (PedidoHelper.getWebPedidoItem() != null) {
                        if (!verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getWebPedidoItem())) {
                            Toast.makeText(ProdutoPedidoActivity.this, "Nenhuma campanha encontrada", Toast.LENGTH_LONG).show();
                            CampanhaHelper.setCampanhaComercialCab(null);
                            edtNomeCampanha.setText("");
                            webPedidoItem.setIdCampanha(0);
                        }
                    } else {
                        Toast.makeText(ProdutoPedidoActivity.this, "Nenhum produto localizado", Toast.LENGTH_LONG).show();
                    }
                }
            });

            edtNomeCampanha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (PedidoHelper.getProduto() != null) {
                        if (!verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getProduto())) {
                            Toast.makeText(ProdutoPedidoActivity.this, "Nenhuma campanha encontrada", Toast.LENGTH_LONG).show();
                            CampanhaHelper.setCampanhaComercialCab(null);
                            edtNomeCampanha.setText("");
                            webPedidoItem.setIdCampanha(0);
                        }
                    } else if (PedidoHelper.getWebPedidoItem() != null) {
                        if (!verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getWebPedidoItem())) {
                            Toast.makeText(ProdutoPedidoActivity.this, "Nenhuma campanha encontrada", Toast.LENGTH_LONG).show();
                            CampanhaHelper.setCampanhaComercialCab(null);
                            edtNomeCampanha.setText("");
                            webPedidoItem.setIdCampanha(0);
                        }
                    } else {
                        Toast.makeText(ProdutoPedidoActivity.this, "Nenhum produto localizado", Toast.LENGTH_LONG).show();
                    }
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
        }

        if (PedidoHelper.getWebPedidoItem() != null && PedidoHelper.getWebPedidoItem().getIdCampanha() > 0) {
            CampanhaHelper.setCampanhaComercialCab(campanhaComercialCabDAO.listaCampanhaComercialCab(webPedidoItem.getIdCampanha()));
        }
    }

    @Override
    protected void onResume() {
        if (PedidoHelper.getListaProdutos() != null && PedidoHelper.getListaProdutos().size() > 0) {
            Float valorUnitario = 0.f;
            for (Produto produto : PedidoHelper.getListaProdutos()) {
                valorUnitario += Float.parseFloat(produto.getVenda_preco());
            }
            webPedidoItem = new WebPedidoItens();
            webPedidoItem.setNome_produto("VARIOS PRODUTOS SELECIONADOS");
            edtNomeProduto.setText("VARIOS PRODUTOS SELECIONADOS");
            webPedidoItem.setVenda_preco(valorUnitario.toString());
            edtTabelaPreco.setText(valorUnitario.toString());
            rbPorcentagem.setText("Desconto %(max " + tabelaPrecoItem.getPerc_desc_final() + "%)");
            edtDesconto.setText(tabelaPrecoItem.getPerc_desc_final());
            cdPromocao.setVisibility(View.VISIBLE);
            txtPromocao.setText(PedidoHelper.getListaProdutos().size() + " PRODUTOS SELECIONADOS");
        } else {
            try {
                if (PedidoHelper.getProduto() != null && !PedidoHelper.getProduto().getId_produto().equals(PedidoHelper.getWebPedidoItem().getId_produto())) {
                    webPedidoItem = new WebPedidoItens(PedidoHelper.getProduto());
                    PedidoHelper.setProduto(null);
                    PedidoHelper.setWebPedidoItem(webPedidoItem);
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
                    PedidoHelper.setWebPedidoItem(webPedidoItem);
                    if (Float.parseFloat(edtDesconto.getText().toString()) <= 0)
                        edtDesconto.setText(tabelaPrecoItem.getPerc_desc_final());
                } catch (Exception nullPointer) {
                    nullPointer.printStackTrace();
                }
            }

            if (getIntent().getIntExtra("pedido", 0) != 1 && verificaCampanha) {
                verificaCampanha = false;
                if (verificaCampanha(ClienteHelper.getCliente(), PedidoHelper.getWebPedidoItem())) {
                    Toast.makeText(ProdutoPedidoActivity.this, "Campanhas disponiveis para este item encontradas", Toast.LENGTH_LONG).show();
                }
            }

            if (!webPedidoItem.getProduto_tercerizacao().equals("S") && !webPedidoItem.getProduto_materia_prima().equals("S")) {
                edtDesconto.setEnabled(true);
                edtDescontoReais.setEnabled(true);
                rbPorcentagem.setEnabled(true);
                rbReal.setEnabled(true);
                promocaoRetorno = pedidoBO.calculaDesconto(ClienteHelper.getCliente().getId_cadastro_servidor(), webPedidoItem.getId_produto(), ProdutoPedidoActivity.this);
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
                txtPromocao.setText("DESCONTO NÃO AUTORIZADO");
                rbPorcentagem.setText("Desconto %(max 0%)");
            }

            if (CampanhaHelper.getCampanhaComercialCab() != null) {
                edtNomeCampanha.setText(CampanhaHelper.getCampanhaComercialCab().getNomeCampanha());
                webPedidoItem.setIdCampanha(CampanhaHelper.getCampanhaComercialCab().getIdCampanha());
            } else {
                edtNomeCampanha.setText("");
                webPedidoItem.setIdCampanha(0);
            }

            edtNomeProduto.setText(webPedidoItem.getNome_produto());
            edtTabelaPreco.setText(MascaraUtil.mascaraVirgula(webPedidoItem.getVenda_preco()));
            calculaDesconto();
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
                                        if (campanhaComercialCabDAO.verificaCampanha(ClienteHelper.getCliente(), webPedidoItem)) {
                                            if (PedidoHelper.getListaProdutos() != null && PedidoHelper.getListaProdutos().size() > 0) {
                                                final List<Produto> listaProdutosSelecionados = PedidoHelper.getListaProdutos();
                                                for (Produto produto : listaProdutosSelecionados) {
                                                    WebPedidoItens produtoSelecionado = new WebPedidoItens(produto);

                                                    produtoSelecionado.setValor_unitario(Float.valueOf(produto.getVenda_preco()));
                                                    produtoSelecionado.setQuantidade(quantidade.toString());
                                                    produtoSelecionado.setValor_total(String.valueOf((Float.parseFloat(produto.getVenda_preco()) * quantidade) - ((Float.parseFloat(edtDesconto.getText().toString()) / 100) * (Float.parseFloat(produto.getVenda_preco()) * quantidade))));
                                                    produtoSelecionado.setValor_bruto(String.valueOf(Float.valueOf(produtoSelecionado.getVenda_preco()) * Float.parseFloat(quantidade.toString())));
                                                    produtoSelecionado.setValor_desconto_per(edtDesconto.getText().toString());
                                                    produtoSelecionado.setValor_desconto_real(String.valueOf((Float.parseFloat(edtDesconto.getText().toString()) / 100) * (Float.parseFloat(produto.getVenda_preco()) * quantidade)));

                                                    if (rbPorcentagem.isChecked())
                                                        produtoSelecionado.setTipoDesconto("P");
                                                    else if (rbReal.isChecked())
                                                        produtoSelecionado.setTipoDesconto("R");

                                                    if (CampanhaHelper.getCampanhaComercialCab() != null)
                                                        produtoSelecionado.setIdCampanha(CampanhaHelper.getCampanhaComercialCab().getIdCampanha());

                                                    PedidoHelper pedidoHelper = new PedidoHelper();
                                                    pedidoHelper.inserirProduto(produtoSelecionado);

                                                    PedidoHelper.setListaProdutos(null);
                                                }
                                            } else {
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

                                                if (CampanhaHelper.getCampanhaComercialCab() != null)
                                                    webPedidoItem.setIdCampanha(CampanhaHelper.getCampanhaComercialCab().getIdCampanha());

                                                if (getIntent().getIntExtra("pedido", 0) != 1) {
                                                    PedidoHelper pedidoHelper = new PedidoHelper();
                                                    pedidoHelper.inserirProduto(webPedidoItem);
                                                } else {
                                                    PedidoHelper pedidoHelper = new PedidoHelper();
                                                    pedidoHelper.alterarProduto(webPedidoItem, getIntent().getIntExtra("position", 0));
                                                }
                                            }
                                            finish();
                                        } else {
                                            Toast.makeText(this, "A campanha selecionada não se enquadra com este item!", Toast.LENGTH_SHORT).show();
                                            edtNomeCampanha.setBackgroundResource(R.drawable.borda_edittext_erro);
                                        }
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
        CampanhaHelper.clear();
        System.gc();
        super.onDestroy();
    }

    private void calculaDesconto() {
        try {
            if (webPedidoItem != null || (PedidoHelper.getListaProdutos() != null && PedidoHelper.getListaProdutos().size() > 0)) {
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

    private boolean verificaCampanha(Cliente cliente, Produto produto) {
        edtNomeCampanha.setBackgroundResource(R.drawable.borda_edittext);
        try {
            List<CampanhaComercialCab> listaCampanha = campanhaComercialCabDAO.listaCampanhaComercialCab(cliente, produto);
            if (listaCampanha.size() > 0) {
                CampanhaHelper.setListaCampanha(listaCampanha);
                Intent intent = new Intent(ProdutoPedidoActivity.this, CampanhaActivity.class);
                intent.putExtra("pedido", 1);
                startActivity(intent);
                return true;
            }
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return false;
    }
}
