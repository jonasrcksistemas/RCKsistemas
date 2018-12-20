package com.example.rcksuporte05.rcksistemas.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.ActivityAddCadastroAnexo;
import com.example.rcksuporte05.rcksistemas.adapters.CadastroAnexoAdapter;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroCliente8 extends Fragment implements CadastroAnexoAdapter.CadastroAnexoAdapterListener {

    @BindView(R.id.recyclerAnexo)
    RecyclerView recyclerAnexo;

    @BindView(R.id.edtTotalAnexos)
    EditText edtTotalAnexos;

    @BindView(R.id.btnAddAnexos)
    FloatingActionButton btnAddAnexos;

    @BindView(R.id.btnContinuar)
    Button btnContinuar;

    private CadastroAnexoAdapter cadastroAnexoAdapter;
    private ActionMode actionMode;

    @OnClick(R.id.btnAddAnexos)
    public void addAnexo() {
        Intent intent = new Intent(getActivity(), ActivityAddCadastroAnexo.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastro_cliente8, container, false);
        ButterKnife.bind(this, view);

        recyclerAnexo.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAnexo.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

        if (getActivity().getIntent().getIntExtra("novo", 0) >= 1) {
            btnContinuar.setVisibility(View.VISIBLE);
            btnContinuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBHelper db = new DBHelper(getActivity());

                    if (ClienteHelper.getListaCadastroAnexo() != null && ClienteHelper.getListaCadastroAnexo().size() > 0) {
                        CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);

                        for (CadastroAnexo cadastroAnexo : ClienteHelper.getListaCadastroAnexo()) {
                            cadastroAnexo.setExcluido("N");
                            cadastroAnexo.setIdEntidade(1);
                            cadastroAnexo.setIdCadastro(ClienteHelper.getCliente().getId_cadastro());
                            cadastroAnexo.setIdCadastroServidor(ClienteHelper.getCliente().getId_cadastro_servidor());
                            cadastroAnexoDAO.atualizarCadastroAnexo(cadastroAnexo);
                        }
                    }

                    if (ClienteHelper.getListaCadastroAnexoExcluidos() != null && ClienteHelper.getListaCadastroAnexoExcluidos().size() > 0) {
                        CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);

                        for (CadastroAnexo cadastroAnexoExcluido : ClienteHelper.getListaCadastroAnexoExcluidos()) {
                            cadastroAnexoExcluido.setExcluido("S");
                            cadastroAnexoDAO.atualizarCadastroAnexo(cadastroAnexoExcluido);
                        }
                    }
                    if (ClienteHelper.getCliente().getFinalizado().equals("S")) {
                        ClienteHelper.getCliente().setAlterado("S");
                    }
                    db.atualizarTBL_CADASTRO(ClienteHelper.getCliente());

                    ClienteHelper.moveTela(4);
                }
            });
        }

        if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {
            btnAddAnexos.setVisibility(View.GONE);
        }

        ClienteHelper.setCadastroCliente8(this);
        return view;
    }

    @Override
    public void onResume() {
        preencheListaAnexo();
        super.onResume();
    }

    private void preencheListaAnexo() {
        cadastroAnexoAdapter = new CadastroAnexoAdapter(ClienteHelper.getListaCadastroAnexo(), this);
        recyclerAnexo.setAdapter(cadastroAnexoAdapter);
        cadastroAnexoAdapter.notifyDataSetChanged();
        if (ClienteHelper.getListaCadastroAnexo() != null && ClienteHelper.getListaCadastroAnexo().size() > 0)
            edtTotalAnexos.setText("Anexos listados: " + ClienteHelper.getListaCadastroAnexo().size());
        else
            edtTotalAnexos.setText("Nenhum anexo registrado");
    }

    @Override
    public void onClickListener(int position) {
        if (cadastroAnexoAdapter.getSelectedItensCount() > 0) {
            enableActionMode(position);
        } else {
            Intent intent = new Intent(getActivity(), ActivityAddCadastroAnexo.class);
            intent.putExtra("Alteracao", position);
            if (getActivity().getIntent().getIntExtra("vizualizacao", 0) >= 1) {
                intent.putExtra("vizualizacao", 1);
            }
            startActivity(intent);
        }
    }

    @Override
    public void onLongClickListener(int position) {
        enableActionMode(position);
    }

    public void enableActionMode(final int position) {
        if (actionMode == null) {
            actionMode = getActivity().startActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.cadastro_anexo_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_delete:
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setTitle("Atenção");
                            if (cadastroAnexoAdapter.getSelectedItensCount() > 1)
                                alert.setMessage("Deseja mesmo excluir os " + cadastroAnexoAdapter.getSelectedItensCount() + " anexos selecionados?");
                            else
                                alert.setMessage("Deseja mesmo excluir o anexo selecionado?");
                            alert.setNegativeButton("NÃO", null);
                            alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (CadastroAnexo cadastroAnexo : cadastroAnexoAdapter.getItensSelecionados()) {
                                        if (ClienteHelper.getListaCadastroAnexoExcluidos() != null) {
                                            ClienteHelper.getListaCadastroAnexoExcluidos().add(cadastroAnexo);
                                        } else {
                                            List<CadastroAnexo> listaCadastroAnexoExcluidos = new ArrayList<>();
                                            listaCadastroAnexoExcluidos.add(cadastroAnexo);
                                            ClienteHelper.setListaCadastroAnexoExcluidos(listaCadastroAnexoExcluidos);
                                        }
                                        ClienteHelper.getListaCadastroAnexo().remove(cadastroAnexo);
                                    }

                                    finishActionMode();
                                    preencheListaAnexo();
                                }
                            });
                            alert.show();
                            break;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    finishActionMode();
                }
            });
        }
        toggleSelection(position);
    }

    public void toggleSelection(int position) {
        cadastroAnexoAdapter.toggleSelection(position);
        if (cadastroAnexoAdapter.getSelectedItensCount() == 0) {
            actionMode.finish();
            actionMode = null;
        } else {
            actionMode.setTitle(String.valueOf(cadastroAnexoAdapter.getSelectedItensCount()));
            actionMode.invalidate();
        }
    }

    public void finishActionMode() {
        if (actionMode != null) {
            actionMode.finish();
            cadastroAnexoAdapter.clearSelection();
            actionMode = null;
        }
    }
}
