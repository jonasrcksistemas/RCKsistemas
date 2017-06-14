package com.example.rcksuporte05.rcksistemas.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.rcksuporte05.rcksistemas.R;


public class CadastroCliente1 extends Fragment {

    private ArrayAdapter arrayIe;
    private ArrayAdapter arrayVen;
    private String[] contribuinte = {"Contribuinte", "Isento", "Não Contribuinte"};
    private Bundle bundle = new Bundle();
    private TextView txtId;
    private TextView txtPessoa;
    private RadioButton rdFisica;
    private RadioButton rdJuridica;
    private TextView txtNomeCliente;
    private EditText edtData;
    private TextView txtNomeFantasia;
    private EditText edtNomeFantasia;
    private TextView txtCpfCnpj;
    private EditText edtCpfCnpj;
    private TextView txtInscEstadual;
    private EditText edtTelefonePrincipal;
    private TextView txtTelefonePrincipal;
    private EditText edtTelefone1;
    private TextView txtTelefone1;
    private EditText edtTelefone2;
    private TextView txtTelefone2;
    private EditText edtPessoaContato;
    private TextView txtPessoaContato;
    private EditText edtEmailPrincipal;
    private TextView txtEmailPrincipal;
    private EditText edtNomeCliente;
    private EditText edtInscEstadual;
    private Spinner spIe;
    private TextView txtData;
    private Spinner spVendedor;
    private TextView txtInscMunicipal;
    private EditText edtInscMunicipal;
    private String[] cliente;
    private String[] idVendedores;
    private Button btnLigar1;
    private Button btnLigar2;
    private Button btnLigar3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente1, container, false);

        txtId = (TextView) view.findViewById(R.id.txtId);
        txtPessoa = (TextView) view.findViewById(R.id.txtPessoa);
        rdFisica = (RadioButton) view.findViewById(R.id.rdFisica);
        rdJuridica = (RadioButton) view.findViewById(R.id.rdJuridica);
        txtNomeCliente = (TextView) view.findViewById(R.id.txtNomeCliente);
        edtData = (EditText) view.findViewById(R.id.edtData);
        txtNomeFantasia = (TextView) view.findViewById(R.id.txtNomeFantasia);
        edtNomeFantasia = (EditText) view.findViewById(R.id.edtNomeFantasia);
        txtCpfCnpj = (TextView) view.findViewById(R.id.txtCpfCnpj);
        edtCpfCnpj = (EditText) view.findViewById(R.id.edtCpfCnpj);
        txtInscEstadual = (TextView) view.findViewById(R.id.txtInscEstadual);
        edtTelefonePrincipal = (EditText) view.findViewById(R.id.edtTelefonePrincipal);
        txtTelefonePrincipal = (TextView) view.findViewById(R.id.txtTelefonePrincipal);
        edtTelefone1 = (EditText) view.findViewById(R.id.edtTelefone1);
        txtTelefone1 = (TextView) view.findViewById(R.id.txtTelefone1);
        edtTelefone2 = (EditText) view.findViewById(R.id.edtTelefone2);
        txtTelefone2 = (TextView) view.findViewById(R.id.txtTelefone2);
        edtPessoaContato = (EditText) view.findViewById(R.id.edtPessoaContato);
        txtPessoaContato = (TextView) view.findViewById(R.id.txtPessoaContato);
        edtEmailPrincipal = (EditText) view.findViewById(R.id.edtEmailPrincipal);
        txtEmailPrincipal = (TextView) view.findViewById(R.id.txtEmailPrincipal);
        edtNomeCliente = (EditText) view.findViewById(R.id.edtNomeCliente);
        edtInscEstadual = (EditText) view.findViewById(R.id.edtInscEstadual);
        txtInscMunicipal = (TextView) view.findViewById(R.id.txtInscMunicipal);
        edtInscMunicipal = (EditText) view.findViewById(R.id.edtInscMunicipal);
        spIe = (Spinner) view.findViewById(R.id.spIe);
        txtData = (TextView) view.findViewById(R.id.txtData);
        spVendedor = (Spinner) view.findViewById(R.id.spVendedor);
        btnLigar1 = (Button) view.findViewById(R.id.btnLigar1);
        btnLigar2 = (Button) view.findViewById(R.id.btnLigar2);
        btnLigar3 = (Button) view.findViewById(R.id.btnLigar3);

        rdJuridica.setChecked(true);

        btnLigar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!cliente[32].replaceAll("[^0-9]", "").isEmpty() && cliente[32].replaceAll("[^0-9]", "").length() >= 8) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);

                        if (cliente[32].replaceAll("[^0-9]", "").length() == 10) {
                            intent.setData(Uri.parse("tel:" + "0" + cliente[32]));
                        } else if (cliente[32].replaceAll("[^0-9]", "").length() == 11) {
                            intent.setData(Uri.parse("tel:" + "0" + cliente[32]));
                        } else {
                            intent.setData(Uri.parse("tel:" + cliente[32]));
                        }

                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Digite um numero válido", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        btnLigar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!cliente[55].replaceAll("[^0-9]", "").isEmpty() && cliente[55].replaceAll("[^0-9]", "").length() >= 8) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);

                        if (cliente[55].replaceAll("[^0-9]", "").length() == 10) {
                            intent.setData(Uri.parse("tel:" + "0" + cliente[55]));
                        } else if (cliente[55].replaceAll("[^0-9]", "").length() == 11) {
                            intent.setData(Uri.parse("tel:" + "0" + cliente[55]));
                        } else {
                            intent.setData(Uri.parse("tel:" + cliente[55]));
                        }

                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Digite um numero válido", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        btnLigar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!cliente[56].replaceAll("[^0-9]", "").isEmpty() && cliente[56].replaceAll("[^0-9]", "").length() >= 8) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        if (cliente[56].replaceAll("[^0-9]", "").length() == 10) {
                            intent.setData(Uri.parse("tel:" + "0" + cliente[56]));
                        } else if (cliente[56].replaceAll("[^0-9]", "").length() == 11) {
                            intent.setData(Uri.parse("tel:" + "0" + cliente[56]));
                        } else {
                            intent.setData(Uri.parse("tel:" + cliente[56]));
                        }

                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Digite um numero válido", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });


        arrayIe = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, contribuinte);
        spIe.setAdapter(arrayIe);

        bundle = getArguments();
        arrayVen = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, bundle.getStringArray("vendedores"));
        spVendedor.setAdapter(arrayVen);

        if (bundle.getInt("cliente") > 0) {
            cliente = bundle.getStringArray("clienteListar");

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
            spVendedor.setEnabled(false);

            txtId.setText("ID: " + bundle.getInt("cliente"));
            edtNomeCliente.setText(cliente[5]);
            edtNomeFantasia.setText(cliente[6]);

            String data = cliente[4].replaceAll("[^0-9]", "");
            if (!data.isEmpty()) {
                edtData.setText(data.substring(6, 8) + "/" + data.substring(4, 6) + "/" + data.substring(0, 4));
            }

            edtInscEstadual.setText(cliente[8]);
            edtInscMunicipal.setText(cliente[9]);
            String cpfCnpj = cliente[7].trim().replaceAll("[^0-9]", "");
            if (cliente[3].equals("J")) {
                rdJuridica.setChecked(true);
                try {
                    edtCpfCnpj.setText(cpfCnpj.substring(0, 2) + "." + cpfCnpj.substring(2, 5) + "." + cpfCnpj.substring(5, 8) + "/" + cpfCnpj.substring(8, 12) + "-" + cpfCnpj.substring(12, 14));
                } catch (StringIndexOutOfBoundsException e) {
                    Toast.makeText(getContext(), "Falta de informações no cadastro", Toast.LENGTH_SHORT).show();
                }
            } else {
                rdFisica.setChecked(true);
                edtCpfCnpj.setText(cpfCnpj.substring(0, 3) + "." + cpfCnpj.substring(3, 6) + "." + cpfCnpj.substring(6, 9) + "-" + cpfCnpj.substring(9, 11));
            }
            String telefonePrincipal = cliente[32].trim().replaceAll("[^0-9]", "");
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


            String telefone1 = cliente[55].trim().replaceAll("[^0-9]", "");
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

            String telefone2 = cliente[56].trim().replaceAll("[^0-9]", "");
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

            edtPessoaContato.setText(cliente[57]);
            edtEmailPrincipal.setText(cliente[33]);
            try {
                spIe.setSelection(Integer.parseInt(cliente[58]) - 1);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
            idVendedores = bundle.getStringArray("idVendedores");
            for (int i = 0; idVendedores.length > i; i++) {
                if (idVendedores[i].equals(cliente[26])) {
                    spVendedor.setSelection(i);
                }
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

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
