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
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.CampanhaHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaClienteAdapter;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.util.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CampanhaClientes extends Fragment implements ListaClienteAdapter.ClienteAdapterListener {

    @BindView(R.id.recycleCampanhaClientes)
    RecyclerView recycleCampanhaClientes;

    @BindView(R.id.edtTotalClientes)
    EditText edtTotalClientes;

    private View view;
    private DBHelper db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_campanha_clientes, container, false);
        ButterKnife.bind(this, view);

        db = new DBHelper(getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleCampanhaClientes.setLayoutManager(layoutManager);
        recycleCampanhaClientes.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

        try {
            List<Cliente> listaCliente = db.listaCliente("SELECT CAD.* FROM TBL_CAMPANHA_COM_CLIENTES CAMP INNER JOIN TBL_CADASTRO CAD ON CAMP.ID_CLIENTE = CAD.ID_CADASTRO_SERVIDOR WHERE ID_CAMPANHA = " + CampanhaHelper.getCampanhaComercialCab().getIdCampanha() + " AND CAMP.ID_EMPRESA = " + UsuarioHelper.getUsuario().getIdEmpresaMultiDevice() + " ORDER BY CAD.NOME_CADASTRO;");
            edtTotalClientes.setText(listaCliente.size() + " Clientes listados");
            ListaClienteAdapter listaClienteAdapter = new ListaClienteAdapter(listaCliente, this);
            recycleCampanhaClientes.setAdapter(listaClienteAdapter);
        } catch (CursorIndexOutOfBoundsException e) {
            edtTotalClientes.setText("0 Clientes listados");
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onClickListener(int position) {

    }

    @Override
    public void onLongClickListener(int position) {

    }
}
