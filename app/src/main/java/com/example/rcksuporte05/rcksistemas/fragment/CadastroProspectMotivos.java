package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.MotivoAdapter;
import com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 29/01/2018.
 */

public class CadastroProspectMotivos extends Fragment implements MotivoAdapter.MotivoListener {
    @BindView(R.id.edtOutrosMotivosProspect)
    public EditText edtOutrosMotivosProspect;
    @BindView(R.id.recyclerMotivos)
    RecyclerView recyclerMotivos;
    @BindView(R.id.btnContinuar)
    Button btnContinuar;
    MotivoAdapter motivoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_prospect_motivo, container, false);
        ButterKnife.bind(this, view);
        recyclerMotivos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMotivos.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        preencheRecycler();
        insereDadosNaTela();

        if (ProspectHelper.getProspect().getProspectSalvo() != null && ProspectHelper.getProspect().getProspectSalvo().equals("S")) {
            recyclerMotivos.setClickable(false);
            edtOutrosMotivosProspect.setFocusable(false);
        }

        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insereDadosDaFrame();

                    boolean validado = true;
                    try {
                        if (ProspectHelper.getProspect().getMotivoNaoCadastramento() == null) {
                            validado = false;
                            Toast.makeText(getActivity(), "Escolha um Motivo para o Não cadastramento!", Toast.LENGTH_LONG).show();
                        } else if (ProspectHelper.getProspect().getMotivoNaoCadastramento().getMotivo().toLowerCase().contains("outros") && ProspectHelper.getProspect().getMotivoNaoCadastramento().getDescricaoOutros().equals("")) {
                            validado = false;
                            edtOutrosMotivosProspect.setError("Observação obrigatorio quando opção Outros selecionada");
                            edtOutrosMotivosProspect.requestFocus();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        validado = false;
                        Toast.makeText(getActivity(), "Escolha um Motivo para o Não cadastramento!", Toast.LENGTH_LONG).show();
                    }

                    if (validado) {
                        DBHelper db = new DBHelper(getActivity());
                        db.atualizarTBL_PROSPECT(ProspectHelper.getProspect());
                        ProspectHelper.moveTela(3);
                    }
                }
            });
        }

        ProspectHelper.setCadastroProspectMotivos(this);
        return view;
    }

    public void insereDadosNaTela() {
        if (ProspectHelper.getProspect().getMotivoNaoCadastramento() != null) {
            motivoAdapter.marcarSelecionado(ProspectHelper.getProspect().getMotivoNaoCadastramento());
            if (ProspectHelper.getProspect().getMotivoNaoCadastramento().getDescricaoOutros() != null) {
                edtOutrosMotivosProspect.setText(ProspectHelper.getProspect().getMotivoNaoCadastramento().getDescricaoOutros());
            }
        }
    }


    public void insereDadosDaFrame() {
        if (motivoAdapter.getSelectedItemCount() > 0) {
            MotivoNaoCadastramento motivoSelecionado;
            motivoSelecionado = motivoAdapter.getItemSelecionado();

            if (motivoSelecionado != null) {
                ProspectHelper.getProspect().setMotivoNaoCadastramento(motivoSelecionado);
                if (edtOutrosMotivosProspect.getText() != null) {
                    ProspectHelper.getProspect().getMotivoNaoCadastramento().setDescricaoOutros(edtOutrosMotivosProspect.getText().toString());
                }
            }
        } else
            ProspectHelper.getProspect().setMotivoNaoCadastramento(null);
    }

    private void preencheRecycler() {
        if (ProspectHelper.getProspect().getProspectSalvo() != null && ProspectHelper.getProspect().getProspectSalvo().equals("S")) {
            motivoAdapter = new MotivoAdapter(ProspectHelper.getMotivos(), new MotivoAdapter.MotivoListener() {
                @Override
                public void onClick(int position) {

                }
            }, getActivity());
        } else {
            motivoAdapter = new MotivoAdapter(ProspectHelper.getMotivos(), this, getActivity());
        }
        recyclerMotivos.setAdapter(motivoAdapter);
        motivoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
        if (motivoAdapter.getItem(position).getMotivo().toLowerCase().contains("outros")) {
            edtOutrosMotivosProspect.setEnabled(true);
            edtOutrosMotivosProspect.requestFocus();
        } else {
            edtOutrosMotivosProspect.setText("");
            edtOutrosMotivosProspect.setEnabled(false);
            btnContinuar.callOnClick();
        }
        motivoAdapter.toggleSelection(position);
        motivoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        ProspectHelper.setCadastroProspectMotivos(this);
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        insereDadosDaFrame();
        super.onDestroyView();
    }
}
