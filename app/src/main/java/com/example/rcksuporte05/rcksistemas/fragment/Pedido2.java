package com.example.rcksuporte05.rcksistemas.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.classes.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPreco;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Pedido2 extends Fragment {

    private static Cliente objetoCliente = null;
    private Spinner spPagamento;
    private Spinner spTabelaPreco;
    private Spinner spFaixaPadrao;
    private ArrayAdapter<TabelaPreco> adapterPreco;
    private ArrayAdapter<TabelaPrecoItem> adapterFaixaPadrao;
    private ArrayAdapter<CondicoesPagamento> adapterPagamento;
    private DBHelper db;
    private EditText edtObservacao;
    private Button btnSalvarPedido;
    private WebPedido webPedido = new WebPedido();
    private Bundle bundle;
    private PedidoHelper pedidoHelper;
    private EditText edtDataEntrega;
    private Button btnDataEntrega;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pedido2, container, false);

        db = new DBHelper(PedidoHelper.getActivityPedidoMain());

        pedidoHelper = new PedidoHelper(this);

        bundle = getArguments();

        edtObservacao = (EditText) view.findViewById(R.id.edtObservacao);
        edtDataEntrega = (EditText) view.findViewById(R.id.edtDataEntrega);
        btnDataEntrega = (Button) view.findViewById(R.id.btnDataEntrega);

        try {
            spPagamento = (Spinner) view.findViewById(R.id.spPagamento);

            adapterPagamento = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, db.listaCondicoesPagamento("SELECT * FROM TBL_CONDICOES_PAG_CAB;"));
            spPagamento.setAdapter(adapterPagamento);

            spTabelaPreco = (Spinner) view.findViewById(R.id.spTabelaPreco);
            adapterPreco = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, db.listaTabelaPreco("SELECT * FROM TBL_TABELA_PRECO_CAB;"));
            spTabelaPreco.setAdapter(adapterPreco);

            spFaixaPadrao = (Spinner) view.findViewById(R.id.spFaixaPadrao);
            adapterFaixaPadrao = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE PONTOS_PREMIACAO > 0;"));
            spFaixaPadrao.setAdapter(adapterFaixaPadrao);
            spFaixaPadrao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0)
                        PedidoHelper.setPositionFaixPadrao(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (CursorIndexOutOfBoundsException e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
            alert.setMessage("É necessário executar a sincronização pelo menos uma vez antes de efetuar um pedido");
            alert.setTitle("Atenção!");
            alert.setCancelable(false);
            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PedidoHelper.getActivityPedidoMain().finish();
                }
            });
            alert.show();
        }

        if (PedidoHelper.getIdPedido() > 0) {
            webPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO = " + PedidoHelper.getIdPedido()).get(0);
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

            //Seleciona a Faixa padrão no Spinner Faixa Padrão
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!webPedido.getId_tabela_preco_faixa().trim().equals(adapterFaixaPadrao.getItem(i).getId_item()));
                spFaixaPadrao.setSelection(i);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Seleciona Tabela de Preco correta dentro do Spinner spTabelaPreco
            try {
                int i = -1;
                do {
                    i++;
                }
                while (!webPedido.getId_tabela().equals(adapterPreco.getItem(i).getId_tabela()));
                spTabelaPreco.setSelection(i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            edtObservacao.setText(webPedido.getObservacoes());
            try {
                edtDataEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_prev_entrega())));
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

                if (pedidoHelper.salvaPedido()) {
                    dialog.dismiss();
                    AlertDialog.Builder alert = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
                    alert.setTitle("Atenção!");
                    alert.setMessage("Pedido salvo com sucesso!");
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
            }
        });

        if (bundle.getInt("vizualizacao") == 1) {
            btnSalvarPedido.setVisibility(View.INVISIBLE);
            edtObservacao.setFocusable(false);
            spPagamento.setEnabled(false);
            spFaixaPadrao.setEnabled(false);
            spTabelaPreco.setEnabled(false);
            edtDataEntrega.setFocusable(false);
        } else {
            btnDataEntrega.setOnClickListener(new View.OnClickListener() {
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
        if (PedidoHelper.getIdPedido() > 0) {
            db = new DBHelper(PedidoHelper.getActivityPedidoMain());
            db.alterar("UPDATE TBL_WEB_PEDIDO SET ID_CADASTRO = " + objetoCliente.getId_cadastro() + " WHERE ID_WEB_PEDIDO = " + PedidoHelper.getIdPedido());
        }
    }

    public WebPedido salvaPedido() {
        webPedido.setId_condicao_pagamento(adapterPagamento.getItem(spPagamento.getSelectedItemPosition()).getId_condicao());
        webPedido.setId_tabela(adapterPreco.getItem(spTabelaPreco.getSelectedItemPosition()).getId_tabela());
        webPedido.setCadastro(objetoCliente);
        webPedido.setObservacoes(edtObservacao.getText().toString());
        webPedido.setData_prev_entrega(edtDataEntrega.getText().toString().trim());
        webPedido.setPedido_enviado("N");

        return webPedido;
    }

    @Override
    public void onResume() {
        try {
            if (PedidoHelper.getPositionFaixPadrao() > 0)
                spFaixaPadrao.setSelection(PedidoHelper.getPositionFaixPadrao());
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        objetoCliente = null;
        System.gc();
        super.onDestroy();
    }
}
