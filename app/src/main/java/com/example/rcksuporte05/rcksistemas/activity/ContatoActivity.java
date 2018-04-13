package com.example.rcksuporte05.rcksistemas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Cliente;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContatoActivity extends AppCompatActivity {

    @BindView(R.id.txtCategoria)
    TextView txtCategoria;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imFisicaJuridica)
    ImageView imFisicaJuridica;
    @BindView(R.id.txtRazaoSocial)
    TextView txtRazaoSocial;
    @BindView(R.id.txtTelefone)
    TextView txtTelefone;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtEndereco)
    TextView txtEndereco;
    @BindView(R.id.lyDetalhes)
    LinearLayout lyDetalhes;
    @BindView(R.id.lyChamada)
    LinearLayout lyChamada;
    @BindView(R.id.lyEmail)
    LinearLayout lyEmail;
    @BindView(R.id.lyGps)
    LinearLayout lyGps;
    @BindView(R.id.lyNomeCliente)
    LinearLayout lyNomeCliente;
    @BindView(R.id.lyFinanceiro)
    LinearLayout lyFinanceiro;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        ButterKnife.bind(this);

        DBHelper db = new DBHelper(this);
        CategoriaDAO categoriaDAO = new CategoriaDAO(db);

        try {
            cliente = ClienteHelper.getCliente();

            try {
                txtCategoria.setText(categoriaDAO.listaHashCategoria().get(cliente.getIdCategoria()).getNomeCategoria());
            } catch (NullPointerException e) {
                txtCategoria.setText("Este cliente não tem categoria definida!");
            }

            toolbar.setTitle("Contato");
            txtRazaoSocial.setText(cliente.getNome_cadastro());

            if (cliente.getPessoa_f_j() != null) {
                switch (cliente.getPessoa_f_j()) {
                    case "F":
                        imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_fisica);
                        break;
                    case "J":
                        imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_juridica);
                        break;
                    default:
                        imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_duvida);
                        break;
                }
            } else
                imFisicaJuridica.setImageResource(R.mipmap.ic_pessoa_duvida);

            if (cliente.getTelefone_principal() != null && !cliente.getTelefone_principal().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_principal().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_principal().replaceAll("[^0-9]", "").length() <= 11) {
                txtTelefone.setText(formataTelefone(cliente.getTelefone_principal()));
            } else if (cliente.getTelefone_dois() != null && !cliente.getTelefone_dois().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_dois().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_dois().replaceAll("[^0-9]", "").length() <= 11) {
                txtTelefone.setText(formataTelefone(cliente.getTelefone_dois()));
            } else if (cliente.getTelefone_tres() != null && !cliente.getTelefone_tres().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_tres().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_tres().replaceAll("[^0-9]", "").length() <= 11) {
                txtTelefone.setText(formataTelefone(cliente.getTelefone_tres()));
            } else {
                txtTelefone.setText("Nenhum telefone válido informado!");
            }

            if (cliente.getEmail_principal() != null && !cliente.getEmail_principal().trim().equals("")) {
                txtEmail.setText(cliente.getEmail_principal());
            } else if (cliente.getEmail_financeiro() != null && !cliente.getEmail_financeiro().trim().equals("")) {
                txtEmail.setText(cliente.getEmail_financeiro());
            } else {
                txtEmail.setText("Nenhum email informado!");
            }

            String endereco;
            if (cliente.getEndereco() != null && !cliente.getEndereco().trim().equals("")) {
                endereco = cliente.getEndereco().replace(",", "") + ", " + cliente.getEndereco_numero() + " - " + cliente.getEndereco_cep();
            } else if (cliente.getCob_endereco() != null && !cliente.getCob_endereco().trim().equals("")) {
                endereco = cliente.getCob_endereco().replace(",", "") + ", " + cliente.getCob_endereco_numero() + " - " + cliente.getCob_endereco_cep();
            } else {
                endereco = "Nenhum endereço informado!";
            }
            txtEndereco.setText(endereco.replace(", null", ""));

            lyDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detalhes();
                }
            });

            lyChamada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fazerChamada(txtTelefone.getText().toString(), cliente.getNome_cadastro());
                }
            });

            lyEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtEmail.getText().toString().equals("Nenhum email informado!")) {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(ContatoActivity.this);
                        alert.setMessage("Nenhum email informado!");
                        alert.setTitle("Atenção");
                        alert.setNeutralButton("OK", null);
                        alert.show();
                    } else {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(ContatoActivity.this);
                        alert.setMessage("Deseja enviar um email a " + cliente.getNome_cadastro() + "?");
                        alert.setTitle("Atenção");
                        alert.setNegativeButton("Não", null);
                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Intent intent = new Intent(Intent.ACTION_SENDTO);
                                intent.setData(Uri.parse("mailto: " + txtEmail.getText().toString()));

                                startActivity(intent);
                            }
                        });
                        alert.show();
                    }
                }
            });

            lyGps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtEndereco.getText().toString().equals("Nenhum endereço informado!")) {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(ContatoActivity.this);
                        alert.setMessage("Nenhum endereço informado!");
                        alert.setTitle("Atenção");
                        alert.setNeutralButton("OK", null);
                        alert.show();
                    } else {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(ContatoActivity.this);
                        alert.setMessage("Deseja navegar para a localização para " + cliente.getNome_cadastro() + " no GPS?");
                        alert.setTitle("Atenção");
                        alert.setNegativeButton("Não", null);
                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + txtEndereco.getText().toString());
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);

                            }
                        });
                        alert.show();
                    }
                }
            });

            lyNomeCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detalhes();
                }
            });

            lyFinanceiro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ContatoActivity.this, FinanceiroResumoActivity.class);
                    HistoricoFinanceiroHelper.setCliente(ClienteHelper.getCliente());
                    System.gc();
                    startActivity(intent);
                    CadastroClienteMain cadastroClienteMain = new CadastroClienteMain();
                    cadastroClienteMain.finish();
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
        telefone = telefone.trim().replaceAll("[^0-9]", "");
        switch (telefone.length()) {
            case 10:
                return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6, 10);
            case 11:
                return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7, 11);
            case 9:
                return telefone.substring(0, 5) + "-" + telefone.substring(5, 9);
            case 8:
                return telefone.substring(0, 4) + "-" + telefone.substring(4, 8);
            default:
                return telefone;
        }
    }

    public void fazerChamada(final String telefone, final String nome) {
        try {
            if (!telefone.replaceAll("[^0-9]", "").trim().isEmpty()) {
                if (telefone.replaceAll("[^0-9]", "").length() >= 8 && telefone.replaceAll("[^0-9]", "").length() <= 11) {
                    final Intent intent = new Intent(Intent.ACTION_DIAL);

                    AlertDialog.Builder alert = new AlertDialog.Builder(ContatoActivity.this);
                    alert.setMessage("Deseja ligar para " + nome + " usando o número " + telefone + " ?");
                    alert.setTitle("ATENÇÃO");
                    alert.setNegativeButton("Não", null);
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (telefone.replaceAll("[^0-9]", "").length() == 10) {
                                intent.setData(Uri.parse("tel:" + "0" + telefone));
                            } else if (telefone.replaceAll("[^0-9]", "").length() == 11) {
                                intent.setData(Uri.parse("tel:" + "0" + telefone));
                            } else {
                                intent.setData(Uri.parse("tel:" + telefone));
                            }
                            startActivity(intent);
                        }
                    });
                    alert.show();

                } else {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(ContatoActivity.this);
                    alert.setMessage("Este numero de telefone não é válido!");
                    alert.setTitle("Atenção");
                    alert.setNeutralButton("OK", null);
                    alert.show();
                }
            } else {
                final AlertDialog.Builder alert = new AlertDialog.Builder(ContatoActivity.this);
                alert.setMessage("Nenhum numero de Telefone informado!");
                alert.setTitle("Atenção");
                alert.setNeutralButton("OK", null);
                alert.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void detalhes() {
        Intent intent = new Intent(ContatoActivity.this, CadastroClienteMain.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        ClienteHelper.setCliente(null);
        super.onDestroy();
    }
}
