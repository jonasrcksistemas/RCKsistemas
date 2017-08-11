package com.example.rcksuporte05.rcksistemas.interfaces;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

public class ContatoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageView;
    private TextView txtRazaoSocial;
    private TextView txtTelefone;
    private TextView txtEmail;
    private TextView txtEndereco;
    private LinearLayout lyDetalhes;
    private Cliente cliente;
    private Bundle bundle;
    private DBHelper db = new DBHelper(ContatoActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        try {
            cliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE ID_CADASTRO = " + getIntent().getIntExtra("id_cliente", 0)).get(0);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            imageView = (ImageView) findViewById(R.id.imFisicaJuridica);
            txtRazaoSocial = (TextView) findViewById(R.id.txtRazaoSocial);
            txtTelefone = (TextView) findViewById(R.id.txtTelefone);
            txtEmail = (TextView) findViewById(R.id.txtEmail);
            txtEndereco = (TextView) findViewById(R.id.txtEndereco);
            lyDetalhes = (LinearLayout) findViewById(R.id.lyDetalhes);

            toolbar.setTitle("Contato");
            txtRazaoSocial.setText(cliente.getNome_cadastro());

            if (!cliente.getTelefone_principal().trim().equals("")) {
                txtTelefone.setText(formataTelefone(cliente.getTelefone_principal()));
            } else if (!cliente.getTelefone_dois().trim().equals("")) {
                txtTelefone.setText(formataTelefone(cliente.getTelefone_dois()));
            } else if (!cliente.getTelefone_tres().trim().equals("")) {
                txtTelefone.setText(formataTelefone(cliente.getTelefone_tres()));
            } else {
                txtTelefone.setText("Nenhum telefone informado!");
            }

            if (!cliente.getEmail_principal().trim().equals("")) {
                txtEmail.setText(cliente.getEmail_principal());
            } else if (!cliente.getEmail_financeiro().trim().equals("")) {
                txtEmail.setText(cliente.getEmail_financeiro());
            } else {
                txtEmail.setText("Nenhum email informado!");
            }

            if (!cliente.getEndereco().trim().equals("")) {
                txtEndereco.setText(cliente.getEndereco().replace(",", "") + ", " + cliente.getEndereco_numero());
            } else if (!cliente.getCob_endereco().trim().equals("")) {
                txtEndereco.setText(cliente.getCob_endereco().replace(",", "") + ", " + cliente.getCob_endereco_numero());
            } else {
                txtEndereco.setText("Nenhum endereço informado!");
            }

            lyDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ContatoActivity.this, CadastroClienteMain.class);

                    bundle = new Bundle();
                    bundle.putString("cliente", cliente.getId_cadastro());
                    String[] clienteArray = new String[83];
                    clienteArray[0] = cliente.getAtivo();
                    clienteArray[1] = cliente.getId_empresa();
                    clienteArray[2] = cliente.getId_cadastro();
                    clienteArray[3] = cliente.getPessoa_f_j();
                    clienteArray[4] = cliente.getData_aniversario();
                    clienteArray[5] = cliente.getNome_cadastro();
                    clienteArray[6] = cliente.getNome_fantasia();
                    clienteArray[7] = cliente.getCpf_cnpj();
                    clienteArray[8] = cliente.getInscri_estadual();
                    clienteArray[9] = cliente.getInscri_municipal();
                    clienteArray[10] = cliente.getEndereco();
                    clienteArray[11] = cliente.getEndereco_bairro();
                    clienteArray[12] = cliente.getEndereco_numero();
                    clienteArray[13] = cliente.getEndereco_complemento();
                    clienteArray[14] = cliente.getEndereco_uf();
                    clienteArray[15] = cliente.getEndereco_id_municipio();
                    clienteArray[16] = cliente.getEndereco_cep();
                    clienteArray[17] = cliente.getUsuario_id();
                    clienteArray[18] = cliente.getUsuario_nome();
                    clienteArray[19] = cliente.getUsuario_data();
                    clienteArray[20] = cliente.getF_cliente();
                    clienteArray[21] = cliente.getF_fornecedor();
                    clienteArray[22] = cliente.getF_funcionario();
                    clienteArray[23] = cliente.getF_vendedor();
                    clienteArray[24] = cliente.getF_transportador();
                    clienteArray[25] = cliente.getData_ultima_compra();
                    clienteArray[26] = cliente.getId_vendedor();
                    clienteArray[27] = cliente.getF_id_cliente();
                    clienteArray[28] = cliente.getId_entidade();
                    clienteArray[29] = cliente.getF_id_fornecedor();
                    clienteArray[30] = cliente.getF_id_vendedor();
                    clienteArray[31] = cliente.getF_id_transportador();
                    clienteArray[32] = cliente.getTelefone_principal();
                    clienteArray[33] = cliente.getEmail_principal();
                    clienteArray[34] = cliente.getId_pais();
                    clienteArray[35] = cliente.getF_id_funcionario();
                    clienteArray[36] = cliente.getAvisar_com_dias();
                    clienteArray[37] = cliente.getObservacoes();
                    clienteArray[38] = cliente.getPadrao_id_c_custo();
                    clienteArray[39] = cliente.getPadrao_id_c_gerenciadora();
                    clienteArray[40] = cliente.getPadrao_id_c_analitica();
                    clienteArray[41] = cliente.getCob_endereco();
                    clienteArray[42] = cliente.getCob_endereco_bairro();
                    clienteArray[43] = cliente.getCob_endereco_numero();
                    clienteArray[44] = cliente.getCob_endereco_complemento();
                    clienteArray[45] = cliente.getCob_endereco_uf();
                    clienteArray[46] = cliente.getCob_endereco_id_municipio();
                    clienteArray[47] = cliente.getCob_endereco_cep();
                    clienteArray[48] = cliente.getCob_endereco_id_pais();
                    clienteArray[49] = cliente.getLimite_credito();
                    clienteArray[50] = cliente.getLimite_disponivel();
                    clienteArray[51] = cliente.getPessoa_contato_financeiro();
                    clienteArray[52] = cliente.getEmail_financeiro();
                    clienteArray[53] = cliente.getObservacoes_faturamento();
                    clienteArray[54] = cliente.getObservacoes_financeiro();
                    clienteArray[55] = cliente.getTelefone_dois();
                    clienteArray[56] = cliente.getTelefone_tres();
                    clienteArray[57] = cliente.getPessoa_contato_principal();
                    clienteArray[58] = cliente.getInd_da_ie_destinatario();
                    clienteArray[59] = cliente.getComissao_percentual();
                    clienteArray[60] = cliente.getId_setor();
                    clienteArray[61] = cliente.getNfe_email_enviar();
                    clienteArray[62] = cliente.getNfe_email_um();
                    clienteArray[63] = cliente.getNfe_email_dois();
                    clienteArray[64] = cliente.getNfe_email_tres();
                    clienteArray[65] = cliente.getNfe_email_quatro();
                    clienteArray[66] = cliente.getNfe_email_cinco();
                    clienteArray[67] = cliente.getId_grupo_vendedor();
                    clienteArray[68] = cliente.getVendedor_usa_portal();
                    clienteArray[69] = cliente.getVendedor_id_user_portal();
                    clienteArray[70] = cliente.getF_tarifa();
                    clienteArray[71] = cliente.getF_id_tarifa();
                    clienteArray[72] = cliente.getF_produtor();
                    clienteArray[73] = cliente.getRg_numero();
                    clienteArray[74] = cliente.getRg_ssp();
                    clienteArray[75] = cliente.getConta_contabil();
                    clienteArray[76] = cliente.getMotorista();
                    clienteArray[77] = cliente.getF_id_motorista();
                    clienteArray[78] = cliente.getHabilitacao_numero();
                    clienteArray[79] = cliente.getHabilitacao_categoria();
                    clienteArray[80] = cliente.getHabilitacao_vencimento();
                    clienteArray[81] = cliente.getMot_id_transportadora();
                    clienteArray[82] = cliente.getLocal_cadastro();
                    bundle.putStringArray("clienteListar", clienteArray);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }
            });

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(ContatoActivity.this, "Erro ao carregar Cliente", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.gc();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String formataTelefone(String telefone) {
        String telefoneRetorno = telefone.trim().replaceAll("[^0-9]", "");
        if (telefone.length() == 10) {
            telefoneRetorno = "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6, 10);
        } else if (telefone.length() == 11) {
            telefoneRetorno = "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7, 11);
        } else if (telefone.length() == 9 && !telefone.contains("-")) {
            telefoneRetorno = telefone.substring(0, 5) + "-" + telefone.substring(5, 9);
        } else if (telefone.length() == 8) {
            telefoneRetorno = telefone.substring(0, 4) + "-" + telefone.substring(4, 8);
        } else {
            telefoneRetorno = telefone;
        }

        return telefoneRetorno;
    }
}
