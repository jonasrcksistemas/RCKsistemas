package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
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
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.SegmentoAdapter;
import com.example.rcksuporte05.rcksistemas.model.Segmento;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 26/01/2018.
 */

public class CadastroCliente5 extends Fragment implements SegmentoAdapter.SegmentoListener {

    @BindView(R.id.edtOutrosSegmentosCliente)
    public EditText edtOutrosSegmentosCliente;
    @BindView(R.id.recyclerSegmentos)
    RecyclerView recyclerSegmentos;
    @BindView(R.id.btnContinuar)
    Button btnContinuar;
    View view;
    SegmentoAdapter segmentoAdapter;
    private DBHelper db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_cadastro_cliente5, container, false);
        ButterKnife.bind(this, view);

        recyclerSegmentos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerSegmentos.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        db = new DBHelper(getActivity());
        preencheRecycler(db.listaSegmento());
        insereDadosNaTela();

        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inserirDadosDaFrame();

                    boolean validado = true;
                    if (ClienteHelper.getCliente().getSegmento() == null) {
                        Toast.makeText(getContext(), "Escolha o segmento!", Toast.LENGTH_LONG).show();
                        validado = false;
                    } else if (ClienteHelper.getCliente().getSegmento().getNomeSetor().toLowerCase().contains("outros")) {
                        if (ClienteHelper.getCliente().getSegmento().getDescricaoOutros() == null || ClienteHelper.getCliente().getSegmento().getDescricaoOutros().equals("")) {
                            edtOutrosSegmentosCliente.setError("Observação obrigatorio quando opção Outros selecionada");
                            edtOutrosSegmentosCliente.requestFocus();
                            validado = false;
                        }
                    }

                    if (validado) {
                        if (ClienteHelper.getCliente().getFinalizado().equals("S")) {
                            ClienteHelper.getCliente().setAlterado("S");
                        }
                        db.atualizarTBL_CADASTRO(ClienteHelper.getCliente());
                        ClienteHelper.moveTela(5);
                    }
                }
            });
        }

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {
            recyclerSegmentos.setClickable(false);
            edtOutrosSegmentosCliente.setFocusable(false);
        }

        ClienteHelper.setCadastroCliente5(this);
        return view;
    }


    public void insereDadosNaTela() {

        if (ClienteHelper.getCliente().getSegmento() != null) {
            segmentoAdapter.marcarSelecionado(ClienteHelper.getCliente().getSegmento());
            if (ClienteHelper.getCliente().getSegmento().getDescricaoOutros() != null && !ClienteHelper.getCliente().getSegmento().getDescricaoOutros().equals("")) {
                edtOutrosSegmentosCliente.setText(ClienteHelper.getCliente().getSegmento().getDescricaoOutros());
            }
        }

    }

    public void inserirDadosDaFrame() {
        if (segmentoAdapter.getSelectedItemCount() > 0) {
            Segmento segmento = segmentoAdapter.getItemSelecionado();

            if (segmento != null) {
                ClienteHelper.getCliente().setSegmento(segmento);
                if (edtOutrosSegmentosCliente.getText() != null) {
                    ClienteHelper.getCliente().getSegmento().setDescricaoOutros(edtOutrosSegmentosCliente.getText().toString());
                }
            }
        } else
            ClienteHelper.getCliente().setSegmento(null);
    }


    public void preencheRecycler(List<Segmento> listaSegmento) {
        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {
            segmentoAdapter = new SegmentoAdapter(listaSegmento, new SegmentoAdapter.SegmentoListener() {
                @Override
                public void onClick(int position) {

                }
            });
        } else {
            segmentoAdapter = new SegmentoAdapter(listaSegmento, this);
        }
        recyclerSegmentos.setAdapter(segmentoAdapter);
        segmentoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(int position) {
        if (segmentoAdapter.getItem(position).getNomeSetor().toLowerCase().contains("outros")) {
            edtOutrosSegmentosCliente.setEnabled(true);
            edtOutrosSegmentosCliente.requestFocus();
        } else {
            edtOutrosSegmentosCliente.setText("");
            edtOutrosSegmentosCliente.setEnabled(false);
            btnContinuar.callOnClick();
        }
        segmentoAdapter.toggleSelection(position);
        segmentoAdapter.notifyDataSetChanged();
    }
}
