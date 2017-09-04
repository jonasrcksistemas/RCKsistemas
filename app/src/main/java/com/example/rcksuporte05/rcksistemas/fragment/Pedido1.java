package com.example.rcksuporte05.rcksistemas.fragment;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.classes.Operacao;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.interfaces.ActivityCliente;
import com.example.rcksuporte05.rcksistemas.interfaces.HistoricoFinanceiroMain;

public class Pedido1 extends Fragment implements View.OnClickListener {

    private static Cliente objetoCliente = null;
    private Spinner spOperacao;
    //    private Spinner spTabelaPreco;
    private ArrayAdapter<Operacao> adapterOperacao;
    //    private ArrayAdapter<TabelaPreco> adapterPreco;
    private Bundle bundle;
    private TextView txtVendedorPedido;
    private Button btnBuscarCliente;
    private Button btnHistoricoFinanceiro;
    private Button btnAdicionarProdutos;
    private TextView txtAdicionarProdutos;
    private TextView txtHistoricoFinanceiro;
    private EditText edtCliente;
    private DBHelper db;
    private WebPedido webPedido = new WebPedido();
    private PedidoHelper pedidoHelper;
    private int idPedido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pedido1, container, false);

        pedidoHelper = new PedidoHelper(this);

        db = new DBHelper(PedidoHelper.getActivityPedidoMain());

        bundle = getArguments();

        edtCliente = (EditText) view.findViewById(R.id.edtCliente);

        btnHistoricoFinanceiro = (Button) view.findViewById(R.id.btnHistoricoFinanceiro);
        btnHistoricoFinanceiro.setOnClickListener(this);

        txtHistoricoFinanceiro = (TextView) view.findViewById(R.id.txtHistoricoFinanceiro);
        txtHistoricoFinanceiro.setOnClickListener(this);

        btnBuscarCliente = (Button) view.findViewById(R.id.btnBuscarCliente);
        btnBuscarCliente.setOnClickListener(this);

        btnAdicionarProdutos = (Button) view.findViewById(R.id.btnAdicionarProdutos);
        btnAdicionarProdutos.setOnClickListener(this);

        txtAdicionarProdutos = (TextView) view.findViewById(R.id.txtAdicionarProdutos);
        txtAdicionarProdutos.setOnClickListener(this);

        txtVendedorPedido = (TextView) view.findViewById(R.id.txtVendedorPedido);
        txtVendedorPedido.setText("Vendedor: " + bundle.getString("usuario"));

        try {
            spOperacao = (Spinner) view.findViewById(R.id.spOperacao);
            adapterOperacao = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, db.listaOperacao("SELECT * FROM TBL_OPERACAO_ESTOQUE;"));
            spOperacao.setAdapter(adapterOperacao);

        } catch (CursorIndexOutOfBoundsException e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
            alert.setMessage("A sincronia é necessária antes de se fazer um pedido, ou não há OPERAÇÕES DE ESTOQUE marcada para multi dispositivo!\n     Qualquer duvida entre em contato com a RCK SISTEMAS.");
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

            txtAdicionarProdutos.setText("Produtos");
        }

        if (bundle.getInt("vizualizacao") == 1) {

            btnBuscarCliente.setEnabled(false);
            spOperacao.setEnabled(false);
        }

        System.gc();

        return (view);
    }

    public void pegaCliente(Cliente cliente) {
        objetoCliente = cliente;
        if (PedidoHelper.getIdPedido() > 0) {
            db = new DBHelper(PedidoHelper.getActivityPedidoMain());
            db.alterar("UPDATE TBL_WEB_PEDIDO SET ID_CADASTRO = " + objetoCliente.getId_cadastro() + " WHERE ID_WEB_PEDIDO = " + PedidoHelper.getIdPedido());
        }
    }

    public boolean verificaCliente() {
        if (objetoCliente != null) {
            return true;
        } else {
            return false;
        }
    }

    public WebPedido salvaPedido() {
        bundle = getArguments();
        webPedido.setCadastro(objetoCliente);
        webPedido.setId_empresa("1");
        webPedido.setId_vendedor(String.valueOf(bundle.getInt("idVendedor")));
        webPedido.setId_operacao(adapterOperacao.getItem(spOperacao.getSelectedItemPosition()).getId_operacao());
        webPedido.setExcluido("N");
        webPedido.setUsuario_lancamento_id(String.valueOf(bundle.getInt("idUsuario")));
        webPedido.setUsuario_lancamento_nome(String.valueOf(bundle.getString("usuario")));
        webPedido.setStatus("A");

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
        } else if (v == btnAdicionarProdutos || v == txtAdicionarProdutos) {
            pedidoHelper.moveTela(1);
        }
    }
}
