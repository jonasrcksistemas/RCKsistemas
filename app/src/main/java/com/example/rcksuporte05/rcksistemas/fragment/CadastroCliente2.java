package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Pais;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CadastroCliente2 extends Fragment {
    @BindView(R.id.edtNumero)
    public EditText edtNumero;
    @BindView(R.id.edtBairro)
    public EditText edtBairro;
    @BindView(R.id.edtCep)
    public EditText edtCep;
    @BindView(R.id.edtEndereco)
    public EditText edtEndereco;
    @BindView(R.id.edtPais)
    Spinner edtPais;
    @BindView(R.id.edtUf)
    Spinner edtUf;
    @BindView(R.id.edtMunicipio)
    Spinner edtMunicipio;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente2, container, false);
        ButterKnife.bind(this, view);

        DBHelper db = new DBHelper(getActivity());

        listaPaises = db.listaPaises();
        paisAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, listaPaises);
        edtPais.setAdapter(paisAdapter);

        ufAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf));
        edtUf.setAdapter(ufAdapter);


        if (ClienteHelper.getCliente().getId_pais() > 0) {
            for (int i = 0; listaPaises.size() > i; i++) {
                if (listaPaises.get(i).getId_pais().equals(String.valueOf(ClienteHelper.getCliente().getId_pais()))) {
                    edtPais.setSelection(i);
                    ClienteHelper.setPosicaoPais(i);
                    break;
                }
            }
        } else {
            for (int i = 0; listaPaises.size() > i; i++) {
                if (paisAdapter.getItem(i).getId_pais().equals("1058")) {
                    edtPais.setSelection(i);
                    ClienteHelper.setPosicaoPais(i);
                    break;
                }
            }
        }

        if (ClienteHelper.getCliente().getEndereco_uf() != null && !ClienteHelper.getCliente().getEndereco_uf().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++) {
                if (ClienteHelper.getCliente().getEndereco_uf().equals(getResources().getStringArray(R.array.uf)[i])) {
                    edtUf.setSelection(i);
                    ClienteHelper.setPosicaoUf(i);
                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                    edtMunicipio.setAdapter(municipioAdapter);
                    break;
                }
            }
        }

        if (ClienteHelper.getCliente().getNome_municipio() != null && !ClienteHelper.getCliente().getNome_municipio().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(listaUf[edtUf.getSelectedItemPosition()]).length > i; i++) {
                if (ClienteHelper.getCliente().getNome_municipio().equals(getResources().getStringArray(listaUf[edtUf.getSelectedItemPosition()])[i])) {
                    edtMunicipio.setSelection(i);
                    ClienteHelper.setPosicaoMunicipio(i);
                    break;
                }
            }
        }

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {

            edtNumero.setFocusable(false);
            edtBairro.setFocusable(false);
            edtCep.setFocusable(false);
            edtEndereco.setFocusable(false);
            edtPais.setEnabled(false);
            edtUf.setEnabled(false);
            edtMunicipio.setEnabled(false);

            if (ClienteHelper.getCliente().getEndereco_cep() != null) {
                String cep = ClienteHelper.getCliente().getEndereco_cep().trim().replaceAll("[^0-9]", "");
                if (cep.length() >= 8) {
                    edtCep.setText(cep.substring(0, 5) + "-" + cep.substring(5));
                } else {
                    edtCep.setText(cep);
                }
            }
        } else {
            if (ClienteHelper.getCliente().getId_pais() <= 0) {
                for (int i = 0; listaPaises.size() > i; i++) {
                    if (paisAdapter.getItem(i).getId_pais().equals("1058")) {
                        edtPais.setSelection(i);
                        break;
                    }
                }
            }

            edtMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ClienteHelper.setPosicaoMunicipio(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edtUf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (ClienteHelper.getPosicaoMunicipio() > -1 && edtUf.getSelectedItemPosition() != ClienteHelper.getPosicaoUf())
                        ClienteHelper.setPosicaoMunicipio(0);

                    if (paisAdapter.getItem(edtPais.getSelectedItemPosition()).getId_pais().equals("1058")) {
                        municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[position]));
                        edtMunicipio.setAdapter(municipioAdapter);
                    }
                    ClienteHelper.setPosicaoUf(position);
                    try {
                        edtMunicipio.setSelection(ClienteHelper.getPosicaoMunicipio());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edtPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (!paisAdapter.getItem(position).getId_pais().equals("1058")) {
                        ClienteHelper.setPosicaoUf(0);
                        ClienteHelper.setPosicaoMunicipio(0);
                        String[] uf = {"EX"};
                        ufAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, uf);
                        edtUf.setAdapter(ufAdapter);
                        municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[8]));
                        edtMunicipio.setAdapter(municipioAdapter);
                    } else {
                        ufAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf));
                        edtUf.setAdapter(ufAdapter);
                    }
                    ClienteHelper.setPosicaoPais(position);
                    try {
                        edtUf.setSelection(ClienteHelper.getPosicaoUf());
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (ClienteHelper.getCliente().getEndereco_uf() != null && !ClienteHelper.getCliente().getEndereco_uf().trim().isEmpty()) {
                            for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++) {
                                if (ClienteHelper.getCliente().getEndereco_uf().equals(getResources().getStringArray(R.array.uf)[i])) {
                                    edtUf.setSelection(i);
                                    ClienteHelper.setPosicaoUf(i);
                                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                                    edtMunicipio.setAdapter(municipioAdapter);
                                    break;
                                }
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if (ClienteHelper.getCliente().getEndereco_cep() != null)
                edtCep.setText(ClienteHelper.getCliente().getEndereco_cep());
        }

        if (ClienteHelper.getCliente().getEndereco() != null)
            edtEndereco.setText(ClienteHelper.getCliente().getEndereco());
        if (ClienteHelper.getCliente().getEndereco_numero() != null)
            edtNumero.setText(ClienteHelper.getCliente().getEndereco_numero());
        if (ClienteHelper.getCliente().getEndereco_bairro() != null)
            edtBairro.setText(ClienteHelper.getCliente().getEndereco_bairro());

        System.gc();

        ClienteHelper.setCadastroCliente2(this);
        return view;
    }

    public void inserirDadosDaFrame() {
        if (!edtNumero.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEndereco_numero(edtNumero.getText().toString());
        else
            ClienteHelper.getCliente().setEndereco_numero(null);

        if (!edtBairro.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEndereco_bairro(edtBairro.getText().toString());
        else
            ClienteHelper.getCliente().setEndereco_bairro(null);

        if (!edtCep.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEndereco_cep(edtCep.getText().toString());
        else
            ClienteHelper.getCliente().setEndereco_cep(null);

        if (!edtEndereco.getText().toString().trim().isEmpty())
            ClienteHelper.getCliente().setEndereco(edtEndereco.getText().toString());
        else
            ClienteHelper.getCliente().setEndereco(null);

        try {
            ClienteHelper.getCliente().setId_pais(Integer.parseInt(listaPaises.get(edtPais.getSelectedItemPosition()).getId_pais()));
            ClienteHelper.getCliente().setEndereco_uf(ufAdapter.getItem(edtUf.getSelectedItemPosition()).toString());
            ClienteHelper.getCliente().setNome_municipio(municipioAdapter.getItem(edtMunicipio.getSelectedItemPosition()).toString());
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
