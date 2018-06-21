package com.example.rcksuporte05.rcksistemas.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CadastroCliente1 extends Fragment {

    @BindView(R.id.edtData)
    public EditText edtData;
    @BindView(R.id.edtNomeFantasia)
    public EditText edtNomeFantasia;
    @BindView(R.id.edtCpfCnpj)
    public EditText edtCpfCnpj;
    @BindView(R.id.edtTelefonePrincipal)
    public EditText edtTelefonePrincipal;
    @BindView(R.id.edtTelefone1)
    public EditText edtTelefone1;
    @BindView(R.id.edtTelefone2)
    public EditText edtTelefone2;
    @BindView(R.id.edtPessoaContato)
    public EditText edtPessoaContato;
    @BindView(R.id.edtEmailPrincipal)
    public EditText edtEmailPrincipal;
    @BindView(R.id.edtNomeCliente)
    public EditText edtNomeCliente;
    @BindView(R.id.edtInscEstadual)
    public EditText edtInscEstadual;
    @BindView(R.id.txtId)
    TextView txtId;
    @BindView(R.id.rdFisica)
    RadioButton rdFisica;
    @BindView(R.id.rdJuridica)
    RadioButton rdJuridica;
    @BindView(R.id.txtNomeCliente)
    TextView txtNomeCliente;
    @BindView(R.id.txtCpfCnpj)
    TextView txtCpfCnpj;
    @BindView(R.id.spIe)
    Spinner spIe;
    @BindView(R.id.spCategoria)
    Spinner spCategoria;
    @BindView(R.id.txtCategoria)
    TextView txtCategoria;
    @BindView(R.id.txtData)
    TextView txtData;
    @BindView(R.id.edtVendedor)
    EditText edtVendedor;
    @BindView(R.id.edtInscMunicipal)
    EditText edtInscMunicipal;
    @BindView(R.id.btnLigar1)
    Button btnLigar1;
    @BindView(R.id.btnLigar2)
    Button btnLigar2;
    @BindView(R.id.btnLigar3)
    Button btnLigar3;
    private ArrayAdapter arrayIe;
    private ArrayAdapter arrayCategoria;
    private String[] contribuinte = {"Contribuinte", "Isento", "Não Contribuinte"};
    private List<Categoria> listaCategoria;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente1, container, false);
        ButterKnife.bind(this, view);

        DBHelper db = new DBHelper(getActivity());

        CategoriaDAO categoriaDAO = new CategoriaDAO(db);

        listaCategoria = categoriaDAO.listaCategoria();
        arrayCategoria = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, listaCategoria);
        spCategoria.setAdapter(arrayCategoria);

        if (ClienteHelper.getCliente().getIdCategoria() > 0) {
            for (int i = 0; listaCategoria.size() > i; i++) {
                if (listaCategoria.get(i).getIdCategoria() == ClienteHelper.getCliente().getIdCategoria()) {
                    spCategoria.setSelection(i);
                }
            }
        }

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1 || ClienteHelper.getCliente().getId_cadastro_servidor() > 0) {

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

            rdFisica.setClickable(false);
            rdJuridica.setClickable(false);
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

            if (ClienteHelper.getCliente().getIdCategoria() <= 0) {
                spCategoria.setVisibility(View.INVISIBLE);
                txtCategoria.setText("CATEGORIA NÃO INFORMADA");
            } else {
                spCategoria.setEnabled(false);
            }

            txtId.setText("ID: " + ClienteHelper.getCliente().getId_cadastro_servidor());

            if (ClienteHelper.getCliente().getCpf_cnpj() != null && ClienteHelper.getCliente().getPessoa_f_j() != null) {
                switch (ClienteHelper.getCliente().getPessoa_f_j()) {
                    case "J":
                        rdJuridica.setChecked(true);
                        try {
                            edtCpfCnpj.setText(MascaraUtil.mascaraCNPJ(ClienteHelper.getCliente().getCpf_cnpj()));
                        } catch (StringIndexOutOfBoundsException e) {
                            Toast.makeText(getContext(), "Falta de informações no cadastro", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "F":
                        rdFisica.setChecked(true);
                        edtCpfCnpj.setText(MascaraUtil.mascaraCPF(ClienteHelper.getCliente().getCpf_cnpj()));
                        break;
                    default:
                        edtCpfCnpj.setText(ClienteHelper.getCliente().getCpf_cnpj());
                        break;
                }
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
        } else {
            txtId.setText("ID: " + ClienteHelper.getCliente().getId_cadastro());

            if (ClienteHelper.getCliente().getCpf_cnpj() != null && ClienteHelper.getCliente().getPessoa_f_j() != null) {
                switch (ClienteHelper.getCliente().getPessoa_f_j()) {
                    case "J":
                        rdJuridica.setChecked(true);
                        try {
                            edtCpfCnpj.setText(ClienteHelper.getCliente().getCpf_cnpj());
                        } catch (StringIndexOutOfBoundsException e) {
                            Toast.makeText(getContext(), "Falta de informações no cadastro", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "F":
                        rdFisica.setChecked(true);
                        edtCpfCnpj.setText(ClienteHelper.getCliente().getCpf_cnpj());
                        break;
                    default:
                        edtCpfCnpj.setText(ClienteHelper.getCliente().getCpf_cnpj());
                        break;
                }
            }

            btnLigar1.setVisibility(View.INVISIBLE);
            btnLigar2.setVisibility(View.INVISIBLE);
            btnLigar3.setVisibility(View.INVISIBLE);

            if (ClienteHelper.getCliente().getTelefone_principal() != null)
                edtTelefonePrincipal.setText((ClienteHelper.getCliente().getTelefone_principal()));
            if (ClienteHelper.getCliente().getTelefone_dois() != null)
                edtTelefone1.setText((ClienteHelper.getCliente().getTelefone_dois()));
            if (ClienteHelper.getCliente().getTelefone_tres() != null)
                edtTelefone2.setText((ClienteHelper.getCliente().getTelefone_tres()));

            edtData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostraDatePickerDialog(getActivity(), edtData);
                }
            });

            edtVendedor.setText(UsuarioHelper.getUsuario().getNome_usuario());

            edtCpfCnpj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        validaCpfCnpj();
                    else
                        edtCpfCnpj.setText(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""));
                }
            });

            spIe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        edtInscEstadual.setEnabled(false);
                        edtInscEstadual.setText("");
                    } else {
                        edtInscEstadual.setEnabled(true);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        arrayIe = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, contribuinte);
        spIe.setAdapter(arrayIe);

        if (ClienteHelper.getCliente().getInd_da_ie_destinatario() != null) {
            try {
                spIe.setSelection(Integer.parseInt(ClienteHelper.getCliente().getInd_da_ie_destinatario()) - 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (ClienteHelper.getCliente().getNome_vendedor() != null)
            edtVendedor.setText(ClienteHelper.getCliente().getNome_vendedor());
        if (ClienteHelper.getCliente().getNome_cadastro() != null)
            edtNomeCliente.setText(ClienteHelper.getCliente().getNome_cadastro());
        if (ClienteHelper.getCliente().getNome_fantasia() != null)
            edtNomeFantasia.setText(ClienteHelper.getCliente().getNome_fantasia());
        if (ClienteHelper.getCliente().getInscri_estadual() != null)
            edtInscEstadual.setText(ClienteHelper.getCliente().getInscri_estadual());
        if (ClienteHelper.getCliente().getInscri_municipal() != null)
            edtInscMunicipal.setText(ClienteHelper.getCliente().getInscri_municipal());
        if (ClienteHelper.getCliente().getPessoa_contato_principal() != null)
            edtPessoaContato.setText(ClienteHelper.getCliente().getPessoa_contato_principal());
        if (ClienteHelper.getCliente().getEmail_principal() != null)
            edtEmailPrincipal.setText(ClienteHelper.getCliente().getEmail_principal());
        if (ClienteHelper.getCliente().getData_aniversario() != null) {
            try {
                edtData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("YYYY-MM-dd").parse(ClienteHelper.getCliente().getData_aniversario())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (rdJuridica.isChecked()) {
            txtNomeCliente.setText("RAZÃO SOCIAL");
            edtNomeCliente.setHint("Razão Social *");
            txtData.setText("DATA ABERTURA");
            edtData.setHint("Data Abertura *");
            txtCpfCnpj.setText("CNPJ");
            edtCpfCnpj.setHint("CNPJ *");

        } else if (rdFisica.isChecked()) {
            txtNomeCliente.setText("NOME");
            edtNomeCliente.setHint("Nome *");
            txtData.setText("DATA NASCIMENTO");
            edtData.setHint("Data Nascimento *");
            txtCpfCnpj.setText("CPF");
            edtCpfCnpj.setHint("CPF *");
        }

        rdJuridica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtNomeCliente.setText("RAZÃO SOCIAL");
                    edtNomeCliente.setHint("Razão Social *");
                    txtData.setText("DATA ABERTURA");
                    edtData.setHint("Data Abertura *");
                    txtCpfCnpj.setText("CNPJ");
                    edtCpfCnpj.setHint("CNPJ *");
                }
            }
        });

        rdFisica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtNomeCliente.setText("NOME");
                    edtNomeCliente.setHint("Nome *");
                    txtData.setText("DATA NASCIMENTO");
                    edtData.setHint("Data Nascimento *");
                    txtCpfCnpj.setText("CPF");
                    edtCpfCnpj.setHint("CPF *");
                }
            }
        });
        System.gc();

        ClienteHelper.setCadastroCliente1(this);
        return (view);
    }

    public void validaCpfCnpj() {
        if (rdFisica.isChecked()) {
            if (MascaraUtil.isValidCPF(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""))) {
                edtCpfCnpj.setText(MascaraUtil.mascaraCPF(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "")));
            } else {
                edtCpfCnpj.setError("CPF Inválido");
                edtCpfCnpj.requestFocus();
            }
        } else if (rdJuridica.isChecked()) {
            if (MascaraUtil.isValidCNPJ(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""))) {
                edtCpfCnpj.setText(MascaraUtil.mascaraCNPJ(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "")));
            } else {
                edtCpfCnpj.setError("CNPJ Inválido");
                edtCpfCnpj.requestFocus();
            }
        } else {
            Toast.makeText(getActivity(), "Você precisa informar o se é pessoa Física ou Jurídica", Toast.LENGTH_SHORT).show();
            rdFisica.requestFocus();
        }
    }

    public void inserirDadosDaFrame() {
        if (rdFisica.isChecked())
            ClienteHelper.getCliente().setPessoa_f_j("F");
        else if (rdJuridica.isChecked())
            ClienteHelper.getCliente().setPessoa_f_j("J");

        if (!edtNomeCliente.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setNome_cadastro(edtNomeCliente.getText().toString());
        else
            ClienteHelper.getCliente().setNome_cadastro(null)
                    ;
        if (!edtNomeFantasia.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setNome_fantasia(edtNomeFantasia.getText().toString());
        else
            ClienteHelper.getCliente().setNome_fantasia(null);

        if (!edtData.getText().toString().trim().isEmpty()) {
            try {
                ClienteHelper.getCliente().setData_aniversario(new SimpleDateFormat("YYYY-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(edtData.getText().toString())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else
            ClienteHelper.getCliente().setData_aniversario(null);


        if (!edtCpfCnpj.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setCpf_cnpj(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""));
        else
            ClienteHelper.getCliente().setCpf_cnpj(null);

        if (!edtTelefonePrincipal.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setTelefone_principal(edtTelefonePrincipal.getText().toString());
        else
            ClienteHelper.getCliente().setTelefone_principal(null);

        if (!edtTelefone1.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setTelefone_dois(edtTelefone1.getText().toString());
        else
            ClienteHelper.getCliente().setTelefone_dois(null);

        if (!edtTelefone2.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setTelefone_tres(edtTelefone2.getText().toString());
        else
            ClienteHelper.getCliente().setTelefone_tres(null);

        if (!edtPessoaContato.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setPessoa_contato_principal(edtPessoaContato.getText().toString());
        else
            ClienteHelper.getCliente().setPessoa_contato_principal(null);

        if (!edtEmailPrincipal.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEmail_principal(edtEmailPrincipal.getText().toString());
        else
            ClienteHelper.getCliente().setEmail_principal(null);

        if (!edtInscEstadual.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setInscri_estadual(edtInscEstadual.getText().toString());
        else
            ClienteHelper.getCliente().setInscri_estadual(null);

        if (!edtInscMunicipal.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setInscri_municipal(edtInscMunicipal.getText().toString());
        else
            ClienteHelper.getCliente().setInscri_municipal(null);

        ClienteHelper.getCliente().setId_vendedor(Integer.parseInt(UsuarioHelper.getUsuario().getId_quando_vendedor()));

        ClienteHelper.getCliente().setInd_da_ie_destinatario(String.valueOf(spIe.getSelectedItemPosition() + 1));

        ClienteHelper.getCliente().setIdCategoria(listaCategoria.get(spCategoria.getSelectedItemPosition()).getIdCategoria());
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

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
