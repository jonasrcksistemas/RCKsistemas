package com.example.rcksuporte05.rcksistemas.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;


public class CadastroCliente1 extends Fragment {

    private ArrayAdapter arrayIe;
    private String[] contribuinte = {"Contribuinte", "Isento", "Não Contribuinte"};
    private TextView txtId;
    private RadioButton rdFisica;
    private RadioButton rdJuridica;
    private TextView txtNomeCliente;
    private EditText edtData;
    private EditText edtNomeFantasia;
    private TextView txtCpfCnpj;
    private EditText edtCpfCnpj;
    private EditText edtTelefonePrincipal;
    private EditText edtTelefone1;
    private EditText edtTelefone2;
    private EditText edtPessoaContato;
    private EditText edtEmailPrincipal;
    private EditText edtNomeCliente;
    private EditText edtInscEstadual;
    private Spinner spIe;
    private TextView txtData;
    private EditText edtVendedor;
    private EditText edtInscMunicipal;
    private Button btnLigar1;
    private Button btnLigar2;
    private Button btnLigar3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente1, container, false);

        txtId = (TextView) view.findViewById(R.id.txtId);
        rdFisica = (RadioButton) view.findViewById(R.id.rdFisica);
        rdJuridica = (RadioButton) view.findViewById(R.id.rdJuridica);
        txtNomeCliente = (TextView) view.findViewById(R.id.txtNomeCliente);
        edtData = (EditText) view.findViewById(R.id.edtData);
        edtNomeFantasia = (EditText) view.findViewById(R.id.edtNomeFantasia);
        txtCpfCnpj = (TextView) view.findViewById(R.id.txtCpfCnpj);
        edtCpfCnpj = (EditText) view.findViewById(R.id.edtCpfCnpj);
        edtTelefonePrincipal = (EditText) view.findViewById(R.id.edtTelefonePrincipal);
        edtTelefone1 = (EditText) view.findViewById(R.id.edtTelefone1);
        edtTelefone2 = (EditText) view.findViewById(R.id.edtTelefone2);
        edtPessoaContato = (EditText) view.findViewById(R.id.edtPessoaContato);
        edtEmailPrincipal = (EditText) view.findViewById(R.id.edtEmailPrincipal);
        edtNomeCliente = (EditText) view.findViewById(R.id.edtNomeCliente);
        edtInscEstadual = (EditText) view.findViewById(R.id.edtInscEstadual);
        edtInscMunicipal = (EditText) view.findViewById(R.id.edtInscMunicipal);
        spIe = (Spinner) view.findViewById(R.id.spIe);
        txtData = (TextView) view.findViewById(R.id.txtData);
        edtVendedor = (EditText) view.findViewById(R.id.edtVendedor);
        btnLigar1 = (Button) view.findViewById(R.id.btnLigar1);
        btnLigar2 = (Button) view.findViewById(R.id.btnLigar2);
        btnLigar3 = (Button) view.findViewById(R.id.btnLigar3);

        btnLigar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerChamada(edtTelefonePrincipal.getText().toString(), ClienteHelper.getCliente().getNome_cadastro());
            }
        });

        btnLigar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerChamada(edtTelefone1.getText().toString(), ClienteHelper.getCliente().getNome_cadastro());
            }
        });

        btnLigar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerChamada(edtTelefone2.getText().toString(), ClienteHelper.getCliente().getNome_cadastro());
            }
        });

        arrayIe = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, contribuinte);
        spIe.setAdapter(arrayIe);

        edtVendedor.setText(ClienteHelper.getCliente().getNome_vendedor());

        if (ClienteHelper.getCliente() != null) {

            rdFisica.setClickable(false);
            rdJuridica.setClickable(false);
            edtData.setFocusable(false);
            edtNomeFantasia.setFocusable(false);
            edtCpfCnpj.setFocusable(false);
            edtTelefonePrincipal.setFocusable(false);
            edtTelefone1.setFocusable(false);
            edtTelefone2.setFocusable(false);
            edtPessoaContato.setFocusable(false);
            edtEmailPrincipal.setFocusable(false);
            edtNomeCliente.setFocusable(false);
            edtInscEstadual.setFocusable(false);
            edtInscMunicipal.setFocusable(false);
            spIe.setEnabled(false);
            edtVendedor.setFocusable(false);

            txtId.setText("ID: " + ClienteHelper.getCliente().getId_cadastro());
            edtNomeCliente.setText(ClienteHelper.getCliente().getNome_cadastro());
            edtNomeFantasia.setText(ClienteHelper.getCliente().getNome_fantasia());

            if (ClienteHelper.getCliente().getData_aniversario() != null) {
                String data = ClienteHelper.getCliente().getData_aniversario().replaceAll("[^0-9]", "");
                if (!data.isEmpty()) {
                    edtData.setText(data.substring(6, 8) + "/" + data.substring(4, 6) + "/" + data.substring(0, 4));
                }
            }

            edtInscEstadual.setText(ClienteHelper.getCliente().getInscri_estadual());
            edtInscMunicipal.setText(ClienteHelper.getCliente().getInscri_municipal());
            String cpfCnpj = ClienteHelper.getCliente().getCpf_cnpj().trim().replaceAll("[^0-9]", "");
            if (ClienteHelper.getCliente().getPessoa_f_j().equals("J")) {
                rdJuridica.setChecked(true);
                try {
                    edtCpfCnpj.setText(cpfCnpj.substring(0, 2) + "." + cpfCnpj.substring(2, 5) + "." + cpfCnpj.substring(5, 8) + "/" + cpfCnpj.substring(8, 12) + "-" + cpfCnpj.substring(12, 14));
                } catch (StringIndexOutOfBoundsException e) {
                    Toast.makeText(getContext(), "Falta de informações no cadastro", Toast.LENGTH_SHORT).show();
                }
            } else if (ClienteHelper.getCliente().getPessoa_f_j().equals("F")) {
                rdFisica.setChecked(true);
                edtCpfCnpj.setText(cpfCnpj.substring(0, 3) + "." + cpfCnpj.substring(3, 6) + "." + cpfCnpj.substring(6, 9) + "-" + cpfCnpj.substring(9, 11));
            } else {
                edtCpfCnpj.setText(cpfCnpj);
            }

            if (ClienteHelper.getCliente().getTelefone_principal() != null) {
                String telefonePrincipal = ClienteHelper.getCliente().getTelefone_principal().trim().replaceAll("[^0-9]", "");
                if (telefonePrincipal.length() == 10) {
                    edtTelefonePrincipal.setText("(" + telefonePrincipal.substring(0, 2) + ") " + telefonePrincipal.substring(2, 6) + "-" + telefonePrincipal.substring(6, 10));
                } else if (telefonePrincipal.length() == 11) {
                    edtTelefonePrincipal.setText("(" + telefonePrincipal.substring(0, 2) + ") " + telefonePrincipal.substring(2, 7) + "-" + telefonePrincipal.substring(7, 11));
                } else if (telefonePrincipal.length() == 9 && !telefonePrincipal.contains("-")) {
                    edtTelefonePrincipal.setText(telefonePrincipal.substring(0, 5) + "-" + telefonePrincipal.substring(5, 9));
                } else if (telefonePrincipal.length() == 8) {
                    edtTelefonePrincipal.setText(telefonePrincipal.substring(0, 4) + "-" + telefonePrincipal.substring(4, 8));
                } else {
                    edtTelefonePrincipal.setText(telefonePrincipal);
                }
            }


            if (ClienteHelper.getCliente().getTelefone_dois() != null) {
                String telefone1 = ClienteHelper.getCliente().getTelefone_dois().trim().replaceAll("[^0-9]", "");
                if (telefone1.length() == 10) {
                    edtTelefone1.setText("(" + telefone1.substring(0, 2) + ") " + telefone1.substring(2, 6) + "-" + telefone1.substring(6, 10));
                } else if (telefone1.length() == 11) {
                    edtTelefone1.setText("(" + telefone1.substring(0, 2) + ") " + telefone1.substring(2, 7) + "-" + telefone1.substring(7, 11));
                } else if (telefone1.length() == 9 && !telefone1.contains("-")) {
                    edtTelefone1.setText(telefone1.substring(0, 5) + "-" + telefone1.substring(5, 9));
                } else if (telefone1.length() == 8) {
                    edtTelefone1.setText(telefone1.substring(0, 4) + "-" + telefone1.substring(4, 8));
                } else {
                    edtTelefone1.setText(telefone1);
                }
            }

            if (ClienteHelper.getCliente().getTelefone_tres() != null) {
                String telefone2 = ClienteHelper.getCliente().getTelefone_tres().trim().replaceAll("[^0-9]", "");
                if (telefone2.length() == 10) {
                    edtTelefone2.setText("(" + telefone2.substring(0, 2) + ") " + telefone2.substring(2, 6) + "-" + telefone2.substring(6, 10));
                } else if (telefone2.length() == 11) {
                    edtTelefone2.setText("(" + telefone2.substring(0, 2) + ") " + telefone2.substring(2, 7) + "-" + telefone2.substring(7, 11));
                } else if (telefone2.length() == 9 && !telefone2.contains("-")) {
                    edtTelefone2.setText(telefone2.substring(0, 5) + "-" + telefone2.substring(5, 9));
                } else if (telefone2.length() == 8) {
                    edtTelefone2.setText(telefone2.substring(0, 4) + "-" + telefone2.substring(4, 8));
                } else {
                    edtTelefone2.setText(telefone2);
                }
            }

            edtPessoaContato.setText(ClienteHelper.getCliente().getPessoa_contato_principal());
            edtEmailPrincipal.setText(ClienteHelper.getCliente().getEmail_principal());
            try {
                spIe.setSelection(Integer.parseInt(ClienteHelper.getCliente().getInd_da_ie_destinatario()) - 1);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        } else {
            btnLigar1.setVisibility(View.INVISIBLE);
            btnLigar2.setVisibility(View.INVISIBLE);
            btnLigar3.setVisibility(View.INVISIBLE);
        }

        if (rdJuridica.isChecked()) {
            txtNomeCliente.setText("RAZÃO SOCIAL");
            edtNomeCliente.setHint("Razão Social");
            txtData.setText("DATA ABERTURA");
            edtData.setHint("Data Abertura");
            txtCpfCnpj.setText("CNPJ");
            edtCpfCnpj.setHint("CNPJ");

        } else if (rdFisica.isChecked()) {
            txtNomeCliente.setText("NOME");
            edtNomeCliente.setHint("Nome");
            txtData.setText("DATA NASCIMENTO");
            edtData.setHint("Data Nascimento");
            txtCpfCnpj.setText("CPF");
            edtCpfCnpj.setHint("CPF");
        }

        rdJuridica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtNomeCliente.setText("RAZÃO SOCIAL");
                    edtNomeCliente.setHint("Razão Social");
                    txtData.setText("DATA ABERTURA");
                    edtData.setHint("Data Abertura");
                    txtCpfCnpj.setText("CNPJ");
                    edtCpfCnpj.setHint("CNPJ");
                }
            }
        });

        rdFisica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtNomeCliente.setText("NOME");
                    edtNomeCliente.setHint("Nome");
                    txtData.setText("DATA NASCIMENTO");
                    edtData.setHint("Data Nascimento");
                    txtCpfCnpj.setText("CPF");
                    edtCpfCnpj.setHint("CPF");
                }
            }
        });
        System.gc();
        return (view);
    }

    public void fazerChamada(final String telefone, final String nome) {
        try {

            if (!telefone.replaceAll("[^0-9]", "").trim().isEmpty()) {
                if (telefone.replaceAll("[^0-9]", "").length() >= 8 && telefone.replaceAll("[^0-9]", "").length() <= 11) {
                    final Intent intent = new Intent(Intent.ACTION_DIAL);

                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
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
                    Toast.makeText(getContext(), "Este numero de telefone não é válido!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Nenhum numero de Telefone informado!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
