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
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.classes.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.interfaces.ActivityCliente;
import com.example.rcksuporte05.rcksistemas.interfaces.HistoricoFinanceiroMain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Pedido3 extends Fragment implements View.OnClickListener {

    private static Cliente objetoCliente = null;
    private Spinner spPagamento;
    private ArrayAdapter<CondicoesPagamento> adapterPagamento;
    private DBHelper db;
    private EditText edtCliente;
    private EditText edtObservacao;
    private Button btnBuscarCliente;
    private Button btnSalvarPedido;
    private Button btnHistoricoFinanceiro;
    private TextView txtHistoricoFinanceiro;
    private WebPedido webPedido = new WebPedido();
    private Bundle bundle;
    private PedidoHelper pedidoHelper;
    private EditText edtDataEntrega;
    private Button btnDataEntrega;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pedido3, container, false);

        db = new DBHelper(PedidoHelper.getActivityPedidoMain());

        pedidoHelper = new PedidoHelper(this);

        bundle = getArguments();

        edtCliente = (EditText) view.findViewById(R.id.edtCliente);
        edtObservacao = (EditText) view.findViewById(R.id.edtObservacao);
        edtDataEntrega = (EditText) view.findViewById(R.id.edtDataEntrega);
        btnDataEntrega = (Button) view.findViewById(R.id.btnDataEntrega);

        btnHistoricoFinanceiro = (Button) view.findViewById(R.id.btnHistoricoFinanceiro);
        btnHistoricoFinanceiro.setOnClickListener(this);

        txtHistoricoFinanceiro = (TextView) view.findViewById(R.id.txtHistoricoFinanceiro);
        txtHistoricoFinanceiro.setOnClickListener(this);

        btnBuscarCliente = (Button) view.findViewById(R.id.btnBuscarCliente);
        btnBuscarCliente.setOnClickListener(this);
        try {
            spPagamento = (Spinner) view.findViewById(R.id.spPagamento);

            adapterPagamento = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, db.listaCondicoesPagamento("SELECT * FROM TBL_CONDICOES_PAG_CAB;"));
            spPagamento.setAdapter(adapterPagamento);
        } catch (CursorIndexOutOfBoundsException e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
            alert.setMessage("A sincronia é necessária antes de se fazer um pedido, ou não há CONDIÇÕES DE PAGAMENTO marcada para multi dispositivo!\n     Qualquer duvida entre em contato com a RCK SISTEMAS.");
            alert.setTitle("Atenção!");
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
            edtObservacao.setText(webPedido.getObservacoes());
            try {
                edtDataEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("MM-dd-yyyy").parse(webPedido.getData_prev_entrega())));
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
                }
            }
        });

        if (bundle.getInt("vizualizacao") == 1) {
            btnSalvarPedido.setVisibility(View.INVISIBLE);
            btnBuscarCliente.setEnabled(false);
            edtObservacao.setFocusable(false);
            spPagamento.setEnabled(false);
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
        webPedido.setCadastro(objetoCliente);
        webPedido.setObservacoes(edtObservacao.getText().toString());
        webPedido.setData_prev_entrega(edtDataEntrega.getText().toString().trim());

        return webPedido;
    }

    @Override
    public void onResume() {
        try {
            edtCliente.setText(objetoCliente.getNome_cadastro());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        super.onResume();
    }

    @Override
    public void onDestroy() {
        objetoCliente = null;
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == btnHistoricoFinanceiro || v == txtHistoricoFinanceiro) {
            if (objetoCliente != null) {
                Intent intent = new Intent(getContext(), HistoricoFinanceiroMain.class);
                intent.putExtra("idCliente", Integer.parseInt(objetoCliente.getId_cadastro()));
                getContext().startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Você precisa selecionar o cliente para consultar seu historico financeiro", Toast.LENGTH_SHORT).show();
            }
        } else if (v == btnBuscarCliente) {
            bundle = new Bundle();
            bundle.putInt("acao", 1);
            Intent intent = new Intent(getContext(), ActivityCliente.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
