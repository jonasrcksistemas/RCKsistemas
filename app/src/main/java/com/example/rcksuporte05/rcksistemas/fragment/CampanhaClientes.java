package com.example.rcksuporte05.rcksistemas.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.CampanhaHelper;
import com.example.rcksuporte05.rcksistemas.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampanhaClientes extends Fragment {

    @BindView(R.id.txtDataInicio)
    TextView txtDataInicio;
    @BindView(R.id.txtDataVencimento)
    TextView txtDataVencimento;
    @BindView(R.id.edtDescCampanha)
    EditText edtDescCampanha;

    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_campanha_clientes, container, false);
        ButterKnife.bind(this, view);

        try {
            txtDataInicio.setText("Data inicio \n" + new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(CampanhaHelper.getCampanhaComercialCab().getDataInicio())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            txtDataVencimento.setText("Validade \n" + new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(CampanhaHelper.getCampanhaComercialCab().getDataFim())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        edtDescCampanha.setText(CampanhaHelper.getCampanhaComercialCab().getDescricaoCampanha());

        return view;
    }
}
