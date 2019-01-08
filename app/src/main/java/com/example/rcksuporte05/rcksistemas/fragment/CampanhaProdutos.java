package com.example.rcksuporte05.rcksistemas.fragment;

import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialItensDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.CampanhaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaCampanhaltensAdapter;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialItens;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampanhaProdutos extends Fragment {

    @BindView(R.id.recycleCampanhaProdutos)
    RecyclerView recycleCampanhaProdutos;

    @BindView(R.id.edtTotalProdutos)
    EditText edtTotalProdutos;

    private View view;
    private DBHelper db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_campanha_produtos, container, false);
        ButterKnife.bind(this, view);

        db = new DBHelper(getActivity());
        CampanhaComercialItensDAO campanhaComercialItensDAO = new CampanhaComercialItensDAO(db);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleCampanhaProdutos.setLayoutManager(layoutManager);

        try {
            List<CampanhaComercialItens> lista = campanhaComercialItensDAO.listaCampanhaComercialItens(CampanhaHelper.getCampanhaComercialCab());
            edtTotalProdutos.setText(lista.size() + " Produtos listados");
            ListaCampanhaltensAdapter adapter = new ListaCampanhaltensAdapter(lista, null);
            recycleCampanhaProdutos.setAdapter(adapter);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            edtTotalProdutos.setText("Nenhum item encontrado");
        }

        return view;
    }
}
