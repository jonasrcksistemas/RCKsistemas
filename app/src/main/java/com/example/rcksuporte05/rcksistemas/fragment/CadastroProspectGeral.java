package com.example.rcksuporte05.rcksistemas.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 25/01/2018.
 */


public class CadastroProspectGeral extends Fragment {

    @BindView(R.id.edtNomeClienteProspect)
    public EditText edtNomeClienteProspect;

    @BindView(R.id.edtNomeFantasiaProspect)
    public EditText edtNomeFantasiaProspect;

    @BindView(R.id.edtCpfCnpjProspect)
    public EditText edtCpfCnpjProspect;

    @BindView(R.id.edtInscEstadualProspect)
    public EditText edtInscEstadualProspect;

    @BindView(R.id.spIeProspect)
    Spinner spIeProspect;

    @BindView(R.id.spCategoriaProspect)
    Spinner spCategoriaProspect;

    @BindView(R.id.txtCpfCnpjProspect)
    TextView txtCpfCnpjProspect;

    @BindView(R.id.btnContinuar)
    Button btnContinuar;

    @BindView(R.id.rdPessoaProspect)
    RadioGroup rdPessoaProspect;

    @BindView(R.id.rdFisica)
    RadioButton rdFisica;

    @BindView(R.id.rdJuridica)
    RadioButton rdJuridica;

    @BindView(R.id.edtTelefoneProspect)
    EditText edtTelefoneProspect;

    View view;
    private ArrayAdapter arrayIe;
    private ArrayAdapter<Categoria> arrayCategoriaProspect;
    private List<Categoria> listaCategoria;
    private String[] contribuinte = {"Contribuinte", "Isento", "Não Contribuinte"};
    private DBHelper db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cadastro_prospect_geral, container, false);
        ButterKnife.bind(this, view);

        if (ProspectHelper.getProspect().getCpf_cnpj() != null && !ProspectHelper.getProspect().getCpf_cnpj().equals("")) {
            edtCpfCnpjProspect.setFocusable(false);
            rdFisica.setClickable(false);
            rdJuridica.setClickable(false);
        }

        db = new DBHelper(getContext());

        CategoriaDAO categoriaDAO = new CategoriaDAO(db);

        listaCategoria = categoriaDAO.listaCategoria();
        arrayCategoriaProspect = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, listaCategoria);
        spCategoriaProspect.setAdapter(arrayCategoriaProspect);

        arrayIe = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_activated_1, contribuinte);
        spIeProspect.setAdapter(arrayIe);

        rdPessoaProspect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rdFisica.isChecked()) {
                    txtCpfCnpjProspect.setText("CPF");
                    edtCpfCnpjProspect.setHint("CPF *");
                } else if (rdJuridica.isChecked()) {
                    txtCpfCnpjProspect.setText("CNPJ");
                    edtCpfCnpjProspect.setHint("CNPJ *");
                }
            }
        });

        spIeProspect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrayIe.getItem(position).equals("Não Contribuinte")) {
                    edtInscEstadualProspect.setEnabled(false);
                } else
                    edtInscEstadualProspect.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtCpfCnpjProspect.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    verificaCnpjCpf();
                } else {
                    edtCpfCnpjProspect.setText(edtCpfCnpjProspect.getText().toString().trim().replaceAll("[^0-9]", ""));
                }
            }
        });

        if (ProspectHelper.getProspect().getProspectSalvo() != null && ProspectHelper.getProspect().getProspectSalvo().equals("S")) {
            edtNomeClienteProspect.setFocusable(false);
            edtNomeFantasiaProspect.setFocusable(false);
            edtCpfCnpjProspect.setFocusable(false);
            edtInscEstadualProspect.setFocusable(false);
            rdPessoaProspect.setEnabled(false);
            spIeProspect.setEnabled(false);
            spCategoriaProspect.setEnabled(false);
        }

        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inserirDadosDaFrame();

                    boolean validado = true;
                    if (ProspectHelper.getProspect().getPessoa_f_j() == null || ProspectHelper.getProspect().getPessoa_f_j().equals("")) {
                        Toast.makeText(getContext(), "Escolher Pessoa Fisica ou Juridica é obrigatorio", Toast.LENGTH_LONG).show();
                        edtCpfCnpjProspect.requestFocus();
                        validado = false;
                    }

                    if (ProspectHelper.getProspect().getNome_cadastro() == null || ProspectHelper.getProspect().getNome_cadastro().trim().equals("")) {
                        validado = false;
                        edtNomeClienteProspect.setError("Campo Obrigatorio");
                        edtNomeClienteProspect.requestFocus();
                    }


                    if (ProspectHelper.getProspect().getNome_fantasia() == null || ProspectHelper.getProspect().getNome_fantasia().equals("")) {

                        validado = false;
                        edtNomeFantasiaProspect.setError("Campo Obrigatorio");
                        edtNomeFantasiaProspect.requestFocus();
                    }

                    if (ProspectHelper.getProspect().getCpf_cnpj() == null || ProspectHelper.getProspect().getCpf_cnpj().equals("")) {

                        validado = false;
                        edtCpfCnpjProspect.setError("Campo Obrigatorio");
                        edtCpfCnpjProspect.requestFocus();
                    } else if (ProspectHelper.getProspect().getPessoa_f_j() != null && ProspectHelper.getProspect().getPessoa_f_j().equals("F")) {
                        if (!MascaraUtil.isValidCPF(ProspectHelper.getProspect().getCpf_cnpj())) {
                            validado = false;
                            edtCpfCnpjProspect.setError("CPF invalido");
                            edtCpfCnpjProspect.requestFocus();
                        }
                    } else if (ProspectHelper.getProspect().getPessoa_f_j() != null && ProspectHelper.getProspect().getPessoa_f_j().equals("J")) {
                        if (!MascaraUtil.isValidCNPJ(ProspectHelper.getProspect().getCpf_cnpj())) {
                            validado = false;
                            edtCpfCnpjProspect.setError("CNPJ invalido");
                            edtCpfCnpjProspect.requestFocus();
                        }
                    }

                    if (validado) {
                        ProspectHelper.getProspect().setProspectSalvo("N");
                        ProspectHelper.getProspect().setUsuario_id(UsuarioHelper.getUsuario().getId_usuario());
                        ProspectHelper.setProspect(db.atualizarTBL_PROSPECT(ProspectHelper.getProspect()));
                        ProspectHelper.moveTela(1);
                    }
                }
            });
        }

        injetaDadosNaTela();

        ProspectHelper.setCadastroProspectGeral(this);

        return view;
    }

    private void injetaDadosNaTela() {
        if (ProspectHelper.getProspect().getPessoa_f_j() != null && !ProspectHelper.getProspect().getPessoa_f_j().equals("")) {
            if (ProspectHelper.getProspect().getPessoa_f_j().equals("F")) {
                rdFisica.setChecked(true);
            } else if (ProspectHelper.getProspect().getPessoa_f_j().equals("J")) {
                rdJuridica.setChecked(true);
            }
        }

        if (ProspectHelper.getProspect().getNome_cadastro() != null && !ProspectHelper.getProspect().getNome_cadastro().equals("")) {
            edtNomeClienteProspect.setText(ProspectHelper.getProspect().getNome_cadastro());
        }

        if (ProspectHelper.getProspect().getNome_fantasia() != null && !ProspectHelper.getProspect().getNome_fantasia().equals("")) {
            edtNomeFantasiaProspect.setText(ProspectHelper.getProspect().getNome_fantasia());
        }

        if (ProspectHelper.getProspect().getTelefone() != null && !ProspectHelper.getProspect().getTelefone().equals("")) {
            edtTelefoneProspect.setText(ProspectHelper.getProspect().getTelefone());
        }

        if (ProspectHelper.getProspect().getCpf_cnpj() != null && !ProspectHelper.getProspect().getCpf_cnpj().equals("")) {
            switch (ProspectHelper.getProspect().getPessoa_f_j()) {
                case "F":
                    edtCpfCnpjProspect.setText(MascaraUtil.mascaraCPF(ProspectHelper.getProspect().getCpf_cnpj()));
                    break;
                case "J":
                    edtCpfCnpjProspect.setText(MascaraUtil.mascaraCNPJ(ProspectHelper.getProspect().getCpf_cnpj()));
                    break;
                default:
                    edtCpfCnpjProspect.setText(ProspectHelper.getProspect().getCpf_cnpj());
                    break;
            }
        }

        if (ProspectHelper.getProspect().getInscri_estadual() != null && !ProspectHelper.getProspect().getInscri_estadual().equals("")) {
            edtInscEstadualProspect.setText(ProspectHelper.getProspect().getInscri_estadual());
        }

        if (ProspectHelper.getProspect().getInd_da_ie_destinatario_prospect() != null && !ProspectHelper.getProspect().getInd_da_ie_destinatario_prospect().equals("")) {
            spIeProspect.setSelection(Integer.parseInt(ProspectHelper.getProspect().getInd_da_ie_destinatario_prospect()) - 1);
        }

        if (ProspectHelper.getProspect().getIdCategoria() > 0) {
            spCategoriaProspect.setSelection(arrayCategoriaProspect.getPosition(listaCategoria.get(ProspectHelper.getProspect().getIdCategoria() - 1)));
        }
    }

    @SuppressLint("ResourceType")
    public void inserirDadosDaFrame() {

        if (rdFisica.isChecked()) {
            ProspectHelper.getProspect().setPessoa_f_j("F");
        } else if (rdJuridica.isChecked()) {
            ProspectHelper.getProspect().setPessoa_f_j("J");
        }

        if (edtNomeClienteProspect.getText() != null && !edtNomeClienteProspect.getText().toString().trim().equals("")) {
            ProspectHelper.getProspect().setNome_cadastro(edtNomeClienteProspect.getText().toString());
        } else {
            System.out.println("mensagem por obrigatoriedade");
        }

        if (edtCpfCnpjProspect.getText() != null && !edtCpfCnpjProspect.getText().toString().equals("")) {
            ProspectHelper.getProspect().setCpf_cnpj(edtCpfCnpjProspect.getText().toString().trim().replaceAll("[^0-9]", ""));
        } else {
            System.out.println("mensagem por obrigatoriedade");
        }

        if (edtNomeFantasiaProspect.getText() != null && !edtNomeFantasiaProspect.getText().toString().equals("")) {
            ProspectHelper.getProspect().setNome_fantasia(edtNomeFantasiaProspect.getText().toString());
        } else {
            System.out.println("mensagem por obrigatoriedade");
        }

        if (edtTelefoneProspect.getText() != null && !edtTelefoneProspect.getText().toString().equals("")) {
            ProspectHelper.getProspect().setTelefone(edtTelefoneProspect.getText().toString());
        } else {
            System.out.println("mensagem por obrigatoriedade");
        }

        if (edtInscEstadualProspect.getText() != null && !edtInscEstadualProspect.getText().toString().equals("")) {
            ProspectHelper.getProspect().setInscri_estadual(edtInscEstadualProspect.getText().toString());
        }

        ProspectHelper.getProspect().setInd_da_ie_destinatario_prospect(String.valueOf(spIeProspect.getSelectedItemPosition() + 1));

        ProspectHelper.getProspect().setIdCategoria(listaCategoria.get(spCategoriaProspect.getSelectedItemPosition()).getIdCategoria());

    }

    public void verificaCnpjCpf() {
        if (rdFisica.isChecked()) {
            if (MascaraUtil.isValidCPF(edtCpfCnpjProspect.getText().toString().trim().replaceAll("[^0-9]", ""))) {
                if (db.verificaCnpjCpf(edtCpfCnpjProspect.getText().toString())) {
                    edtCpfCnpjProspect.setText(MascaraUtil.mascaraCPF(edtCpfCnpjProspect.getText().toString()));
                } else {
                    chamaDialog();
                }
            } else {
                edtCpfCnpjProspect.setError("CPF invalido");
            }
        } else if (rdJuridica.isChecked()) {
            if (MascaraUtil.isValidCNPJ(edtCpfCnpjProspect.getText().toString().trim().replaceAll("[^0-9]", ""))) {
                if (db.verificaCnpjCpf(edtCpfCnpjProspect.getText().toString())) {
                    edtCpfCnpjProspect.setText(MascaraUtil.mascaraCNPJ(edtCpfCnpjProspect.getText().toString()));
                } else {
                    chamaDialog();
                }
            } else {
                edtCpfCnpjProspect.setError("CNPJ invalido");
            }
        }
    }

    private void chamaDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Atenção ele já foi prospectado!");
        alert.setMessage(" Tem certeza que digitou o Cpf/Cnpj correto? \n Se deseja conferir ou redigitar basta clicar em não");
        alert.setCancelable(false);
        alert.setNegativeButton("Não", null);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        alert.show();
    }
}
