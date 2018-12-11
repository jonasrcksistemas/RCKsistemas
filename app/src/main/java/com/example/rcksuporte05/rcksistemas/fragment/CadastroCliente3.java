package com.example.rcksuporte05.rcksistemas.fragment;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.Helper.HistoricoFinanceiroHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.CadastroClienteMain;
import com.example.rcksuporte05.rcksistemas.activity.FinanceiroResumoActivity;
import com.example.rcksuporte05.rcksistemas.model.Pais;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroCliente3 extends Fragment implements View.OnClickListener {
    @BindView(R.id.edtPaisCobranca)
    Spinner edtPaisCobranca;
    @BindView(R.id.edtUfCobranca)
    Spinner edtUfCobranca;
    @BindView(R.id.edtMunicipioCobranca)
    Spinner edtMunicipioCobranca;
    @BindView(R.id.edtLimiteCredito)
    EditText edtLimiteCredito;
    @BindView(R.id.edtContatoFinanceiro)
    EditText edtContatoFinanceiro;
    @BindView(R.id.edtEmailFinanceiro)
    EditText edtEmailFinanceiro;
    @BindView(R.id.edtEnderecoCobranca)
    EditText edtEnderecoCobranca;
    @BindView(R.id.edtNumero)
    EditText edtNumero;
    @BindView(R.id.edtBairro)
    EditText edtBairro;
    @BindView(R.id.edtComplemento)
    EditText edtComplemento;
    @BindView(R.id.edtCepCobranca)
    EditText edtCepCobranca;
    @BindView(R.id.btnHistoricoFinanceiro)
    Button btnHistoricoFinanceiro;
    @BindView(R.id.txtHistoricoFinanceiro)
    TextView txtHistoricoFinanceiro;
    @BindView(R.id.btnContinuar)
    Button btnContinuar;

    private int[] listaUf = {R.array.AC,
            R.array.AL,
            R.array.AM,
            R.array.AP,
            R.array.BA,
            R.array.CE,
            R.array.DF,
            R.array.ES,
            R.array.EX,
            R.array.GO,
            R.array.MA,
            R.array.MG,
            R.array.MS,
            R.array.MT,
            R.array.PA,
            R.array.PB,
            R.array.PE,
            R.array.PI,
            R.array.PR,
            R.array.RJ,
            R.array.RN,
            R.array.RO,
            R.array.RR,
            R.array.RS,
            R.array.SC,
            R.array.SE,
            R.array.SP,
            R.array.TO};

    private ArrayAdapter municipioAdapter;
    private ArrayAdapter ufAdapter;
    private ArrayAdapter<Pais> paisAdapter;
    private List<Pais> listaPaises;
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente3, container, false);
        ButterKnife.bind(this, view);

        btnHistoricoFinanceiro.setOnClickListener(this);
        txtHistoricoFinanceiro.setOnClickListener(this);

        db = new DBHelper(getActivity());

        listaPaises = db.listaPaises();
        paisAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, listaPaises);
        edtPaisCobranca.setAdapter(paisAdapter);

        ufAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf));
        edtUfCobranca.setAdapter(ufAdapter);

        municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[edtUfCobranca.getSelectedItemPosition()]));
        edtMunicipioCobranca.setAdapter(municipioAdapter);

        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inserirDadosDaFrame();

                    if (ClienteHelper.getCliente().getFinalizado().equals("S")) {
                        ClienteHelper.getCliente().setAlterado("S");
                    }

                    db.atualizarTBL_CADASTRO(ClienteHelper.getCliente());
                    ClienteHelper.moveTela(3);
                }
            });
        }

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {

            edtLimiteCredito.setFocusable(false);
            edtContatoFinanceiro.setFocusable(false);
            edtEmailFinanceiro.setFocusable(false);
            edtEnderecoCobranca.setFocusable(false);
            edtNumero.setFocusable(false);
            edtBairro.setFocusable(false);
            edtComplemento.setFocusable(false);
            edtCepCobranca.setFocusable(false);
            edtMunicipioCobranca.setEnabled(false);
            edtPaisCobranca.setEnabled(false);
            edtUfCobranca.setEnabled(false);

            if (ClienteHelper.getCliente().getLimite_credito() != null) {
                Float limiteCredito = Float.parseFloat(ClienteHelper.getCliente().getLimite_credito());
                edtLimiteCredito.setText("R$" + String.format("%.2f", limiteCredito));
            }

            if (ClienteHelper.getCliente().getCob_endereco_cep() != null) {
                String cep = ClienteHelper.getCliente().getCob_endereco_cep().trim().replaceAll("[^0-9]", "");
                if (cep.length() >= 8) {
                    edtCepCobranca.setText(cep.substring(0, 5) + "-" + cep.substring(5));
                } else {
                    edtCepCobranca.setText(cep);
                }
            }

        } else {
            btnHistoricoFinanceiro.setVisibility(View.GONE);
            txtHistoricoFinanceiro.setVisibility(View.GONE);

            if (ClienteHelper.getCliente().getLimite_credito() != null)
                edtLimiteCredito.setText(ClienteHelper.getCliente().getLimite_credito());

            if (ClienteHelper.getCliente().getCob_endereco_cep() != null)
                edtCepCobranca.setText(ClienteHelper.getCliente().getCob_endereco_cep());

            if (ClienteHelper.getCliente().getCob_endereco_id_pais() <= 0) {
                for (int i = 0; listaPaises.size() > i; i++) {
                    if (paisAdapter.getItem(i).getId_pais().equals("1058")) {
                        edtPaisCobranca.setSelection(i);
                        break;
                    }
                }
            }

            edtMunicipioCobranca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ClienteHelper.setPosicaoCobrancaMunicipio(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edtUfCobranca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (ClienteHelper.getPosicaoCobrancaMunicipio() > -1 && edtUfCobranca.getSelectedItemPosition() != ClienteHelper.getPosicaoCobrancaUf())
                        ClienteHelper.setPosicaoCobrancaMunicipio(0);

                    if (paisAdapter.getItem(edtPaisCobranca.getSelectedItemPosition()).getId_pais().equals("1058")) {
                        municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[position]));
                        edtMunicipioCobranca.setAdapter(municipioAdapter);
                    }
                    ClienteHelper.setPosicaoCobrancaUf(position);
                    try {
                        edtMunicipioCobranca.setSelection(ClienteHelper.getPosicaoCobrancaMunicipio());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edtPaisCobranca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (!paisAdapter.getItem(position).getId_pais().equals("1058")) {
                        ClienteHelper.setPosicaoCobrancaUf(0);
                        ClienteHelper.setPosicaoCobrancaMunicipio(0);
                        String[] uf = {"EX"};
                        ufAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, uf);
                        edtUfCobranca.setAdapter(ufAdapter);
                        municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[8]));
                        edtMunicipioCobranca.setAdapter(municipioAdapter);
                    } else {
                        ufAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf));
                        edtUfCobranca.setAdapter(ufAdapter);
                    }
                    ClienteHelper.setPosicaoCobrancaPais(position);
                    try {
                        edtUfCobranca.setSelection(ClienteHelper.getPosicaoCobrancaUf());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        if (ClienteHelper.getCliente().getCob_endereco_id_pais() > 0) {
            for (int i = 0; listaPaises.size() > i; i++) {
                if (listaPaises.get(i).getId_pais().equals(String.valueOf(ClienteHelper.getCliente().getCob_endereco_id_pais()))) {
                    edtPaisCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaPais(i);
                    break;
                }
            }
        } else {
            for (int i = 0; listaPaises.size() > i; i++) {
                if (paisAdapter.getItem(i).getId_pais().equals("1058")) {
                    edtPaisCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaPais(i);
                    break;
                }
            }
        }

        if (ClienteHelper.getCliente().getCob_endereco_uf() != null && !ClienteHelper.getCliente().getCob_endereco_uf().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++) {
                if (ClienteHelper.getCliente().getCob_endereco_uf().equals(getResources().getStringArray(R.array.uf)[i])) {
                    edtUfCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaUf(i);
                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                    edtMunicipioCobranca.setAdapter(municipioAdapter);
                    break;
                }
            }
        } else if (ClienteHelper.getVendedor().getCob_endereco_uf() != null && !ClienteHelper.getVendedor().getCob_endereco_uf().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++) {
                if (ClienteHelper.getVendedor().getCob_endereco_uf().equals(getResources().getStringArray(R.array.uf)[i])) {
                    edtUfCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaUf(i);
                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                    edtMunicipioCobranca.setAdapter(municipioAdapter);
                    break;
                }
            }
        }

        if (ClienteHelper.getCliente().getNome_cob_municipio() != null && !ClienteHelper.getCliente().getNome_cob_municipio().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(listaUf[edtUfCobranca.getSelectedItemPosition()]).length > i; i++) {
                if (ClienteHelper.getCliente().getNome_cob_municipio().equals(getResources().getStringArray(listaUf[edtUfCobranca.getSelectedItemPosition()])[i])) {
                    edtMunicipioCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaMunicipio(i);
                    break;
                }
            }
        } else if (ClienteHelper.getVendedor().getNome_cob_municipio() != null && !ClienteHelper.getVendedor().getNome_cob_municipio().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(listaUf[edtUfCobranca.getSelectedItemPosition()]).length > i; i++) {
                if (ClienteHelper.getVendedor().getNome_cob_municipio().equals(getResources().getStringArray(listaUf[edtUfCobranca.getSelectedItemPosition()])[i])) {
                    edtMunicipioCobranca.setSelection(i);
                    ClienteHelper.setPosicaoCobrancaMunicipio(i);
                    break;
                }
            }
        }

        if (ClienteHelper.getCliente().getPessoa_contato_financeiro() != null)
            edtContatoFinanceiro.setText(ClienteHelper.getCliente().getPessoa_contato_financeiro());
        if (ClienteHelper.getCliente().getEmail_financeiro() != null)
            edtEmailFinanceiro.setText(ClienteHelper.getCliente().getEmail_financeiro());
        if (ClienteHelper.getCliente().getCob_endereco() != null)
            edtEnderecoCobranca.setText(ClienteHelper.getCliente().getCob_endereco());
        if (ClienteHelper.getCliente().getCob_endereco_numero() != null)
            edtNumero.setText(ClienteHelper.getCliente().getCob_endereco_numero());
        if (ClienteHelper.getCliente().getCob_endereco_bairro() != null)
            edtBairro.setText(ClienteHelper.getCliente().getCob_endereco_bairro());
        if (ClienteHelper.getCliente().getCob_endereco_complemento() != null)
            edtComplemento.setText(ClienteHelper.getCliente().getCob_endereco_complemento());

        ClienteHelper.setCadastroCliente3(this);
        return view;
    }

    public void inserirDadosDaFrame() {
        if (!edtLimiteCredito.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setLimite_credito(edtLimiteCredito.getText().toString().replaceAll("[^0-9]", ""));
        else
            ClienteHelper.getCliente().setLimite_credito(null);

        if (!edtContatoFinanceiro.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setPessoa_contato_financeiro(edtContatoFinanceiro.getText().toString());
        else
            ClienteHelper.getCliente().setPessoa_contato_financeiro(null);

        if (!edtEmailFinanceiro.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEmail_financeiro(edtEmailFinanceiro.getText().toString());
        else
            ClienteHelper.getCliente().setEmail_financeiro(null);

        if (!edtEnderecoCobranca.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setCob_endereco(edtEnderecoCobranca.getText().toString());
        else
            ClienteHelper.getCliente().setCob_endereco(null);

        if (!edtNumero.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setCob_endereco_numero(edtNumero.getText().toString());
        else
            ClienteHelper.getCliente().setCob_endereco_numero(null);

        if (!edtBairro.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setCob_endereco_bairro(edtBairro.getText().toString());
        else
            ClienteHelper.getCliente().setCob_endereco_bairro(null);

        if (!edtComplemento.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setCob_endereco_complemento(edtComplemento.getText().toString());
        else
            ClienteHelper.getCliente().setCob_endereco_complemento(null);

        if (!edtCepCobranca.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setCob_endereco_cep(edtCepCobranca.getText().toString());
        else
            ClienteHelper.getCliente().setCob_endereco_cep(null);

        try {
            ClienteHelper.getCliente().setCob_endereco_id_pais(Integer.parseInt(listaPaises.get(edtPaisCobranca.getSelectedItemPosition()).getId_pais()));
            ClienteHelper.getCliente().setCob_endereco_uf(ufAdapter.getItem(edtUfCobranca.getSelectedItemPosition()).toString());
            ClienteHelper.getCliente().setNome_cob_municipio(municipioAdapter.getItem(edtMunicipioCobranca.getSelectedItemPosition()).toString());
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnHistoricoFinanceiro || v == txtHistoricoFinanceiro) {
            if (ClienteHelper.getCliente() != null && ClienteHelper.getCliente().getId_cadastro_servidor() > 0) {
                try {
                    Intent intent = new Intent(getContext(), FinanceiroResumoActivity.class);
                    HistoricoFinanceiroHelper.setCliente(ClienteHelper.getCliente());
//                    DBHelper db = new DBHelper(getActivity());
//                    CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
//                    HistoricoFinanceiroHelper.setCadastroFinanceiroResumo(cadastroFinanceiroResumoDAO.listaCadastroFinanceiroResumo(ClienteHelper.getCliente().getId_cadastro_servidor()));
                    System.gc();
                    getContext().startActivity(intent);
                    CadastroClienteMain cadastroClienteMain = new CadastroClienteMain();
                    cadastroClienteMain.finish();
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Financeiro não encontrado, por favor faça a sincronia e tente novamente!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Você precisa selecionar um cliente já efetivado para consultar seu historico financeiro", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
