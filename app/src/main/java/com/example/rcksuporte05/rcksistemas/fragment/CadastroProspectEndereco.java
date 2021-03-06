package com.example.rcksuporte05.rcksistemas.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.BuscaMunicipioActivity;
import com.example.rcksuporte05.rcksistemas.activity.BuscaUfActivity;
import com.example.rcksuporte05.rcksistemas.model.Pais;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 26/01/2018.
 */

public class CadastroProspectEndereco extends Fragment {

    @BindView(R.id.edtEnderecoProspect)
    public EditText edtEnderecoProspect;

    @BindView(R.id.edtNumeroProspect)
    public EditText edtNumeroProspect;

    @BindView(R.id.edtBairroProspect)
    public EditText edtBairroProspect;

    @BindView(R.id.edtCep)
    public EditText edtCep;

    @BindView(R.id.spMunicipioProspect)
    public Spinner spMunicipioProspect;

    @BindView(R.id.spPaisProspect)
    public Spinner spPaisProspect;

    @BindView(R.id.spUfProspect)
    public Spinner spUfProspect;

    @BindView(R.id.edtComplementoProspect)
    public EditText edtComplementoProspect;

    @BindView(R.id.btnContinuar)
    Button btnContinuar;

    ArrayAdapter municipioAdapter;
    ArrayAdapter ufAdapter;
    ArrayAdapter<Pais> paisAdapter;
    View view;
    int[] listaUf = {R.array.AC,
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cadastro_prospect_ederecos, container, false);
        ButterKnife.bind(this, view);

        ufAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.uf));
        spUfProspect.setAdapter(ufAdapter);
        spUfProspect.setClickable(false);
        spUfProspect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    Intent intent = new Intent(getActivity(), BuscaUfActivity.class);
                    intent.putExtra("prospect", 1);
                    startActivity(intent);
                }
                return true;
            }
        });

        spMunicipioProspect.setClickable(false);
        spMunicipioProspect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    Intent intent = new Intent(getActivity(), BuscaMunicipioActivity.class);
                    intent.putExtra("prospect", 1);
                    startActivity(intent);
                }
                return true;
            }
        });

        spUfProspect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ProspectHelper.getPosicaoMunicipio() > -1 && spUfProspect.getSelectedItemPosition() != ProspectHelper.getPosicaoUf())
                    ProspectHelper.setPosicaoMunicipio(0);

                if (paisAdapter.getItem(spPaisProspect.getSelectedItemPosition()).getId_pais().equals("1058")) {
                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[position]));
                    spMunicipioProspect.setAdapter(municipioAdapter);
                }
                ProspectHelper.setPosicaoUf(position);
                try {
                    spMunicipioProspect.setSelection(ProspectHelper.getPosicaoMunicipio());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (ProspectHelper.getPaises().size() > 0) {
            paisAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, ProspectHelper.getPaises());
            spPaisProspect.setAdapter(paisAdapter);
            spPaisProspect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (!paisAdapter.getItem(position).getId_pais().equals("1058")) {
                        ProspectHelper.setPosicaoUf(8);
                        spUfProspect.setSelection(8);
                        municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[8]));
                        ProspectHelper.setPosicaoMunicipio(0);
                        spMunicipioProspect.setAdapter(municipioAdapter);
                    }
                    try {
                        spUfProspect.setSelection(ProspectHelper.getPosicaoUf());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ProspectHelper.setPosicaoPais(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        spMunicipioProspect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProspectHelper.setPosicaoMunicipio(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for (int i = 0; ProspectHelper.getPaises().size() > i; i++) {
            if (paisAdapter.getItem(i).getId_pais().equals("1058")) {
                spPaisProspect.setSelection(i);
                ProspectHelper.setPosicaoPais(i);
                break;
            }
        }

        injetaDadosNaTela();

        if (ProspectHelper.getProspect().getProspectSalvo() != null && ProspectHelper.getProspect().getProspectSalvo().equals("S")) {
            edtEnderecoProspect.setFocusable(false);
            edtNumeroProspect.setFocusable(false);
            edtBairroProspect.setFocusable(false);
            edtCep.setFocusable(false);
            spMunicipioProspect.setEnabled(false);
            spPaisProspect.setEnabled(false);
            spUfProspect.setEnabled(false);
            edtComplementoProspect.setFocusable(false);
        }

        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inserirDadosDaFrame();

                    if (ProspectHelper.getProspect().getEndereco_cep().replaceAll("^[0-9]", "").length() >= 8) {
                        edtCep.requestFocus();
                        edtCep.setError("Tamanho maximo é de 8 caracteres");
                    } else {
                        DBHelper db = new DBHelper(getActivity());
                        db.atualizarTBL_PROSPECT(ProspectHelper.getProspect());
                        ProspectHelper.moveTela(2);
                    }

                }
            });
        }

        ProspectHelper.setCadastroProspectEndereco(this);
        return view;
    }

    @Override
    public void onResume() {
        if (ProspectHelper.getPosicaoUf() > -1) {
            spUfProspect.setSelection(ProspectHelper.getPosicaoUf());
            municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[ProspectHelper.getPosicaoUf()]));
            spMunicipioProspect.setAdapter(municipioAdapter);
        }

        if (ProspectHelper.getPosicaoMunicipio() > -1)
            spMunicipioProspect.setSelection(ProspectHelper.getPosicaoMunicipio());

        super.onResume();
    }

    public void injetaDadosNaTela() {

        if (ProspectHelper.getProspect().getEndereco() != null) {
            edtEnderecoProspect.setText(ProspectHelper.getProspect().getEndereco());
        }
        if (ProspectHelper.getProspect().getEndereco_numero() != null) {
            edtNumeroProspect.setText(ProspectHelper.getProspect().getEndereco_numero());

        }
        if (ProspectHelper.getProspect().getEndereco_bairro() != null) {
            edtBairroProspect.setText(ProspectHelper.getProspect().getEndereco_bairro());
        }

        if (ProspectHelper.getProspect().getEndereco_cep() != null) {
            edtCep.setText(ProspectHelper.getProspect().getEndereco_cep());
        }

        if (ProspectHelper.getPosicaoPais() > -1) {
            try {
                spPaisProspect.setSelection(ProspectHelper.getPosicaoPais());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (ProspectHelper.getProspect().getId_pais() != null && !ProspectHelper.getProspect().getId_pais().trim().isEmpty()) {
            for (int i = 0; ProspectHelper.getPaises().size() > i; i++) {
                if (ProspectHelper.getPaises().get(i).getId_pais().equals(ProspectHelper.getProspect().getId_pais())) {
                    spPaisProspect.setSelection(i);
                    ProspectHelper.setPosicaoPais(i);
                    break;
                }
            }
        }

        if (ProspectHelper.getPosicaoUf() > -1) {
            try {
                spUfProspect.setSelection(ProspectHelper.getPosicaoUf());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (ProspectHelper.getProspect().getEndereco_uf() != null && !ProspectHelper.getProspect().getEndereco_uf().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++) {
                if (ProspectHelper.getProspect().getEndereco_uf().equals(getResources().getStringArray(R.array.uf)[i])) {
                    spUfProspect.setSelection(i);
                    ProspectHelper.setPosicaoUf(i);
                    break;
                }
            }
        } else if (ProspectHelper.getVendedor().getEndereco_uf() != null && !ProspectHelper.getVendedor().getEndereco_uf().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(R.array.uf).length > i; i++) {
                if (ProspectHelper.getVendedor().getEndereco_uf().equals(getResources().getStringArray(R.array.uf)[i])) {
                    spUfProspect.setSelection(i);
                    ProspectHelper.setPosicaoUf(i);
                    municipioAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_activated_1, getResources().getStringArray(listaUf[i]));
                    spMunicipioProspect.setAdapter(municipioAdapter);
                    break;
                }
            }
        }


        if (ProspectHelper.getProspect().getNome_municipio() != null && !ProspectHelper.getProspect().getNome_municipio().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(listaUf[spUfProspect.getSelectedItemPosition()]).length > i; i++) {
                if (ProspectHelper.getProspect().getNome_municipio().equals(getResources().getStringArray(listaUf[ProspectHelper.getPosicaoUf()])[i])) {
                    spMunicipioProspect.setSelection(i);
                    ProspectHelper.setPosicaoMunicipio(i);
                    break;
                }
            }
        } else if (ProspectHelper.getVendedor().getNome_municipio() != null && !ProspectHelper.getVendedor().getNome_municipio().trim().isEmpty()) {
            for (int i = 0; getResources().getStringArray(listaUf[spUfProspect.getSelectedItemPosition()]).length > i; i++) {
                if (ProspectHelper.getVendedor().getNome_municipio().equals(getResources().getStringArray(listaUf[spUfProspect.getSelectedItemPosition()])[i])) {
                    spMunicipioProspect.setSelection(i);
                    ProspectHelper.setPosicaoMunicipio(i);
                    break;
                }
            }
        }

        if (ProspectHelper.getProspect().getEndereco_complemento() != null && !ProspectHelper.getProspect().getEndereco_complemento().trim().isEmpty()) {
            edtComplementoProspect.setText(ProspectHelper.getProspect().getEndereco_complemento());
        }
    }


    @SuppressLint("ResourceType")
    public void inserirDadosDaFrame() {
        if (edtEnderecoProspect.getText() != null) {
            ProspectHelper.getProspect().setEndereco(edtEnderecoProspect.getText().toString());
        }

        if (edtNumeroProspect.getText() != null) {
            ProspectHelper.getProspect().setEndereco_numero(edtNumeroProspect.getText().toString());
        }

        if (edtBairroProspect.getText() != null) {
            ProspectHelper.getProspect().setEndereco_bairro(edtBairroProspect.getText().toString());
        }


        if (edtCep.getText() != null) {
            ProspectHelper.getProspect().setEndereco_cep(edtCep.getText().toString());
        }

        if (edtComplementoProspect.getText() != null && !edtComplementoProspect.getText().toString().trim().isEmpty()) {
            ProspectHelper.getProspect().setEndereco_complemento(edtComplementoProspect.getText().toString());
        }

        try {
            ProspectHelper.getProspect().setId_pais(paisAdapter.getItem(spPaisProspect.getSelectedItemPosition()).getId_pais());
            ProspectHelper.getProspect().setEndereco_uf(getResources().getStringArray(R.array.uf)[spUfProspect.getSelectedItemPosition()]);
            ProspectHelper.getProspect().setNome_municipio(getResources().getStringArray(listaUf[spUfProspect.getSelectedItemPosition()])[spMunicipioProspect.getSelectedItemPosition()]);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "Você precisa sincronizar ao menos uma vez", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

        ProspectHelper.setPosicaoPais(spPaisProspect.getSelectedItemPosition());
        ProspectHelper.setPosicaoUf(spUfProspect.getSelectedItemPosition());
        ProspectHelper.setPosicaoMunicipio(spMunicipioProspect.getSelectedItemPosition());
    }
}
