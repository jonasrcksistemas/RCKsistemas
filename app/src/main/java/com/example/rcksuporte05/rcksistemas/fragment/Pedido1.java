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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.classes.Operacao;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPreco;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.interfaces.ActivityCliente;
import com.example.rcksuporte05.rcksistemas.interfaces.HistoricoFinanceiroMain;

public class Pedido1 extends Fragment implements View.OnClickListener {

    private static Cliente objetoCliente = null;
    private Spinner spOperacao;
    private Spinner spTabelaPreco;
    private Spinner spFaixaPadrao;
    private ArrayAdapter<Operacao> adapterOperacao;
    private ArrayAdapter<TabelaPreco> adapterPreco;
    private ArrayAdapter<TabelaPrecoItem> adapterFaixaPadrao;
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

            spTabelaPreco = (Spinner) view.findViewById(R.id.spTabelaPreco);
            adapterPreco = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, db.listaTabelaPreco("SELECT * FROM TBL_TABELA_PRECO_CAB;"));
            spTabelaPreco.setAdapter(adapterPreco);

            spFaixaPadrao = (Spinner) view.findViewById(R.id.spFaixaPadrao);
            adapterFaixaPadrao = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, db.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE PONTOS_PREMIACAO > 0;"));
            spFaixaPadrao.setAdapter(adapterFaixaPadrao);
            spFaixaPadrao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    PedidoHelper.setPositionFaixPadrao(spFaixaPadrao.getSelectedItemPosition());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (CursorIndexOutOfBoundsException e) {
//            Toast.makeText(getContext(), "A sincronia é necessária antes de se fazer um pedido", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alert = new AlertDialog.Builder(PedidoHelper.getActivityPedidoMain());
            alert.setMessage("A sincronia é necessária antes de se fazer um pedido, ou não há OPERAÇÕES DE ESTOQUE marcada para multi dispositivo!\n     Qualquer duvida entre em contato com a RCK SISTEMAS.");
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

            //Seleciona a Feixa padrão no Spinner Faixa Padrão
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

            txtAdicionarProdutos.setText("Produtos");
        }

        if (bundle.getInt("vizualizacao") == 1) {
            btnBuscarCliente.setEnabled(false);
            spTabelaPreco.setEnabled(false);
            spOperacao.setEnabled(false);
            spFaixaPadrao.setEnabled(false);
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
        Usuario usuario = UsuarioHelper.getUsuario();

        webPedido.setCadastro(objetoCliente);
        webPedido.setId_empresa(usuario.getIdEmpresaMultiDevice());
        webPedido.setId_vendedor(String.valueOf(bundle.getInt("idVendedor")));
        webPedido.setId_operacao(adapterOperacao.getItem(spOperacao.getSelectedItemPosition()).getId_operacao());
        webPedido.setId_tabela(adapterPreco.getItem(spTabelaPreco.getSelectedItemPosition()).getId_tabela());
        webPedido.setExcluido("N");
        webPedido.setUsuario_lancamento_id(String.valueOf(bundle.getInt("idUsuario")));
        webPedido.setUsuario_lancamento_nome(bundle.getString("usuario"));
        webPedido.setStatus("A");

        return webPedido;
    }

    @Override
    public void onResume() {
        try {
            edtCliente.setText(objetoCliente.getNome_cadastro());
        } catch (NullPointerException e) {
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
            Intent intent = new Intent(getContext(), ActivityCliente.class);
            intent.putExtra("acao", 1);
            startActivity(intent);
        } else if (v == btnAdicionarProdutos || v == txtAdicionarProdutos) {
            pedidoHelper.moveTela(1);
        }
    }
}
