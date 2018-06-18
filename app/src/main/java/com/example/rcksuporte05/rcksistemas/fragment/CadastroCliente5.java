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
import android.widget.EditText;
import android.widget.LinearLayout;

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
    View view;
    SegmentoAdapter segmentoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_cadastro_cliente5, container, false);
        ButterKnife.bind(this, view);

        recyclerSegmentos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerSegmentos.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        DBHelper db = new DBHelper(getActivity());
        preencheRecycler(db.listaSegmento());
        insereDadosNaTela();

        if (ClienteHelper.getCliente().getId_cadastro_servidor() > 0) {
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
        if (ClienteHelper.getCliente().getId_cadastro_servidor() > 0) {
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
        }
        segmentoAdapter.toggleSelection(position);
        segmentoAdapter.notifyDataSetChanged();
    }
}
