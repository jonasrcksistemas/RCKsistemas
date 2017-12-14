package com.example.rcksuporte05.rcksistemas.interfaces;

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

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;

public class ContatoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageView;
    private TextView txtRazaoSocial;
    private TextView txtTelefone;
    private TextView txtEmail;
    private TextView txtEndereco;
    private LinearLayout lyDetalhes;
    private LinearLayout lyChamada;
    private LinearLayout lyEmail;
    private LinearLayout lyGps;
    private LinearLayout lyNomeCliente;
    private LinearLayout lyFinanceiro;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        try {
            cliente = ClienteHelper.getCliente();

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            imageView = (ImageView) findViewById(R.id.imFisicaJuridica);
            txtRazaoSocial = (TextView) findViewById(R.id.txtRazaoSocial);
            txtTelefone = (TextView) findViewById(R.id.txtTelefone);
            txtEmail = (TextView) findViewById(R.id.txtEmail);
            txtEndereco = (TextView) findViewById(R.id.txtEndereco);
            lyDetalhes = (LinearLayout) findViewById(R.id.lyDetalhes);
            lyChamada = (LinearLayout) findViewById(R.id.lyChamada);
            lyEmail = (LinearLayout) findViewById(R.id.lyEmail);
            lyGps = (LinearLayout) findViewById(R.id.lyGps);
            lyNomeCliente = (LinearLayout) findViewById(R.id.lyNomeCliente);
            lyFinanceiro = (LinearLayout) findViewById(R.id.lyFinanceiro);

            toolbar.setTitle("Contato");
            txtRazaoSocial.setText(cliente.getNome_cadastro());

            if (cliente.getPessoa_f_j() != null) {
                if (cliente.getPessoa_f_j().equals("F")) {
                    imageView.setImageResource(R.mipmap.ic_pessoa_fisica);
                } else if (cliente.getPessoa_f_j().equals("J")) {
                    imageView.setImageResource(R.mipmap.ic_pessoa_juridica);
                } else {
                    imageView.setImageResource(R.mipmap.ic_pessoa_duvida);
                }
            } else {
                imageView.setImageResource(R.mipmap.ic_pessoa_duvida);
            }

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

            if (cliente.getEndereco() != null && !cliente.getEndereco().trim().equals("")) {
                txtEndereco.setText(cliente.getEndereco().replace(",", "") + ", " + cliente.getEndereco_numero() + " - " + cliente.getEndereco_cep());
            } else if (cliente.getCob_endereco() != null && !cliente.getCob_endereco().trim().equals("")) {
                txtEndereco.setText(cliente.getCob_endereco().replace(",", "") + ", " + cliente.getCob_endereco_numero() + " - " + cliente.getCob_endereco_cep());
            } else {
                txtEndereco.setText("Nenhum endereço informado!");
            }

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
        String telefoneRetorno;
        telefone = telefone.trim().replaceAll("[^0-9]", "");
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
