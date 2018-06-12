package com.example.rcksuporte05.rcksistemas.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.activityAnaliseDeCredito;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.Operacao;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Pedido2 extends Fragment {

    private static Cliente objetoCliente = null;
    @BindView(R.id.txtDataEmissao)
    TextView txtDataEmissao;
    @BindView(R.id.spOperacao)
    Spinner spOperacao;
    private ArrayAdapter<Operacao> adapterOperacao;
    private Spinner spPagamento;
    private ArrayAdapter<CondicoesPagamento> adapterPagamento;
    private DBHelper db;
    private WebPedidoDAO webPedidoDAO;
    private EditText edtObservacao;
    private Button btnSalvarPedido;
    private WebPedido webPedido = new WebPedido();
    private Bundle bundle;
    private PedidoHelper pedidoHelper;
    private EditText edtDataEntrega;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pedido2, container, false);
        ButterKnife.bind(this, view);

        db = new DBHelper(PedidoHelper.getActivityPedidoMain());
        webPedidoDAO = new WebPedidoDAO(db);
        txtDataEmissao.setText(db.pegaDataAtual());
        pedidoHelper = new PedidoHelper(this);

        bundle = getArguments();

        edtObservacao = (EditText) view.findViewById(R.id.edtObservacao);
        edtDataEntrega = (EditText) view.findViewById(R.id.edtDataEntrega);

        try {
            spPagamento = (Spinner) view.findViewById(R.id.spPagamento);

            adapterPagamento = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, db.listaCondicoesPagamento("SELECT * FROM TBL_CONDICOES_PAG_CAB;"));
            spPagamento.setAdapter(adapterPagamento);

            adapterOperacao = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, db.listaOperacao("SELECT * FROM TBL_OPERACAO_ESTOQUE;"));
            spOperacao.setAdapter(adapterOperacao);

        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if (PedidoHelper.getIdPedido() > 0) {
            webPedido = webPedidoDAO.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + PedidoHelper.getIdPedido()).get(0);
            objetoCliente = webPedido.getCadastro();

            //Seleciona Condição de pagamento correta dentro do Spinner spPagamento
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!webPedido.getId_condicao_pagamento().equals(adapterPagamento.getItem(i).getId_condicao()));
                spPagamento.setSelection(i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //Seleciona operação correta dentro do Spinner spOperacao
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!webPedido.getId_operacao().equals(adapterOperacao.getItem(i).getId_operacao()));
                spOperacao.setSelection(i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            edtObservacao.setText(webPedido.getObservacoes());
            try {
                txtDataEmissao.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_emissao())));
                edtDataEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_prev_entrega())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            try {

                txtDataEmissao.setText(new SimpleDateFormat("dd/MM/yyyy")
                        .format(new SimpleDateFormat("yyyy-MM-dd")
                                .parse(db.pegaDataAtual())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        btnSalvarPedido = (Button) view.findViewById(R.id.btnSalvarPedido);
        btnSalvarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = new ProgressDialog(PedidoHelper.getActivityPedidoMain());
                dialog.setTitle("Atenção!");
                dialog.setMessage("Salvando o Pedido");
                dialog.setCancelable(false);
                dialog.show();

                if (adapterPagamento.getItem(spPagamento.getSelectedItemPosition()).getId_condicao().equals("1")) {
                    if (pedidoHelper.salvaPedido()) {
                        dialog.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
                        alert.setTitle("Pedido salvo com sucesso!");
                        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PedidoHelper.getActivityPedidoMain().finish();
                            }
                        });
                        alert.setCancelable(false);
                        alert.show();
                    } else {
                        dialog.dismiss();
                    /*AlertDialog.Builder alert = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
                    alert.setTitle("Atenção!");
                    alert.setMessage("O pedido não foi salvo!");
                    alert.setNeutralButton("OK", null);
                    alert.show();*/
                    }
                } else {
                    if (pedidoHelper.validaCredito()) {
                        dialog.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
                        alert.setTitle("Pedido salvo com sucesso!");
                        alert.setMessage("Pedido com crédito Aprovado");
                        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PedidoHelper.getActivityPedidoMain().finish();
                            }
                        });
                        alert.setCancelable(false);
                        alert.show();
                    } else {
                        Intent intent = new Intent(getActivity(), activityAnaliseDeCredito.class);
                        intent.putExtra("valorPedido", PedidoHelper.getValorVenda());
                        HistoricoFinanceiroHelper.setCliente(objetoCliente);
                        dialog.dismiss();
                        startActivity(intent);
                    }
                }
            }
        });

        if (bundle.getInt("vizualizacao") == 1) {
            btnSalvarPedido.setVisibility(View.INVISIBLE);
            edtObservacao.setFocusable(false);
            spPagamento.setEnabled(false);
            edtDataEntrega.setFocusable(false);
            spOperacao.setEnabled(false);
        } else {
            edtDataEntrega.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostraDatePickerDialog(PedidoHelper.getActivityPedidoMain(), edtDataEntrega);
                }
            });
        }
        System.gc();

        return (view);
    }

    public void mostraDatePickerDialog(Context context, final EditText campoTexto) {
        final Calendar calendar;
        //Prepara data anterior caso ja tenha sido selecionada
        if (campoTexto.getTag() != null) {
            calendar = ((Calendar) campoTexto.getTag());
        } else {
            calendar = Calendar.getInstance();
        }

        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                campoTexto.setText(new SimpleDateFormat("dd/MM/yyyy").format(newDate.getTime()));
                campoTexto.setTag(newDate);
                PedidoHelper.editTextDataEntrega().setBackgroundResource(R.drawable.borda_edittext);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void pegaCliente(Cliente cliente) {
        objetoCliente = cliente;
    }

    public WebPedido salvaPedido() {
        webPedido.setId_condicao_pagamento(adapterPagamento.getItem(spPagamento.getSelectedItemPosition()).getId_condicao());
        webPedido.setCadastro(objetoCliente);
        webPedido.setObservacoes(edtObservacao.getText().toString());
        webPedido.setData_prev_entrega(edtDataEntrega.getText().toString().trim());
        webPedido.setPedido_enviado("N");
        webPedido.setId_operacao(adapterOperacao.getItem(spOperacao.getSelectedItemPosition()).getId_operacao());

        return webPedido;
    }

    @Override
    public void onDestroy() {
        objetoCliente = null;
        System.gc();
        super.onDestroy();
    }
}
