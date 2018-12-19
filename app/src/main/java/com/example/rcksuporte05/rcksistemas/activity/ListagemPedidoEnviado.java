package com.example.rcksuporte05.rcksistemas.activity;

import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaPedidoAdapter;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.util.PDFPedidoUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListagemPedidoEnviado extends AppCompatActivity implements ListaPedidoAdapter.PedidoAdapterListener {

    @BindView(R.id.listaPedidoEnviados)
    RecyclerView recyclerViewPedidos;
    @BindView(R.id.edtNumeroPedidoEnviado)
    TextView edtNumerPedidoEnviados;
    @BindView(R.id.toolbarPedidoEnviado)
    Toolbar toolbar;
    ListagemPedidoEnviado.ActionModeCallback actionModeCallback;
    private List<WebPedido> listaPedido;
    private DBHelper db = new DBHelper(this);
    private WebPedidoDAO webPedidoDAO;
    private Usuario usuario;
    private ListaPedidoAdapter listaPedidoAdapter;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pedido_enviado);
        ButterKnife.bind(this);

        webPedidoDAO = new WebPedidoDAO(db);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);

        toolbar.setTitle("Pedidos Enviados");
        setSupportActionBar(toolbar);
        usuario = db.listaUsuario("SELECT * FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consulta("SELECT * FROM TBL_LOGIN;", "LOGIN") + "';").get(0);

        try {
            listaPedido = webPedidoDAO.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC;");

            preencheListaRecycler(listaPedido);

        } catch (CursorIndexOutOfBoundsException e) {
            Toast.makeText(this, "Nenhum pedido foi Enviado!", Toast.LENGTH_LONG).show();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewPedidos.setLayoutManager(layoutManager);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionModeCallback = new ListagemPedidoEnviado.ActionModeCallback();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_pedido_enviados, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.busca_pedido_enviados);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            searchView = (SearchView) item.getActionView();
        } else {
            searchView = (SearchView) MenuItemCompat.getActionView(item);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                if (query.trim().equals("")) {
                    preencheListaRecycler(listaPedido);
                    edtNumerPedidoEnviados.setText(listaPedido.size() + ": Pedidos Enviados");
                    edtNumerPedidoEnviados.setTextColor(Color.BLACK);
                } else {
                    List<WebPedido> listaBusca = buscaPedidoEnviado(listaPedido, query);
                    preencheListaRecycler(listaBusca);
                    if (listaBusca.size() > 0) {
                        edtNumerPedidoEnviados.setText(listaBusca.size() + ": Pedidos Encontrados");
                        edtNumerPedidoEnviados.setTextColor(Color.BLACK);
                    } else {
                        edtNumerPedidoEnviados.setText("Nenum pedido encontrado");
                        edtNumerPedidoEnviados.setTextColor(Color.RED);
                    }
                }

                System.gc();
                return false;
            }
        });

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Nome Cliente / Nº pedido");
        return true;
    }

    public List<WebPedido> buscaPedidoEnviado(List<WebPedido> webPedidos, String query) {
        final String upperCaseQuery = query.toUpperCase();

        final List<WebPedido> lista = new ArrayList<>();
        for (WebPedido webPedido : webPedidos) {
            try {
                final String nomeCliente = webPedido.getNome_extenso().toUpperCase();
                final String numeroPedido = webPedido.getId_web_pedido_servidor().toUpperCase();

                if (nomeCliente.contains(upperCaseQuery) || numeroPedido.equals(upperCaseQuery)) {
                    lista.add(webPedido);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    public void preencheListaRecycler(List<WebPedido> listaPedidos) {

        listaPedidoAdapter = new ListaPedidoAdapter(listaPedidos, this, this);

        recyclerViewPedidos.setAdapter(listaPedidoAdapter);

        listaPedidoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        try {
            listaPedido = webPedidoDAO.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR DESC;");
            preencheListaRecycler(listaPedido);
            if (actionMode == null)
                listaPedidoAdapter.clearSelections();
            else
                actionMode.finish();
        } catch (CursorIndexOutOfBoundsException e) {
            Toast.makeText(this, "Nenhum pedido foi Enviado!", Toast.LENGTH_LONG).show();
        }
        edtNumerPedidoEnviados.setText(listaPedido.size() + ": Pedidos Enviados");
        super.onResume();
    }


    @Override
    public void onPedidoRowClicked(int position) {
        if (listaPedidoAdapter.getSelectedItemCount() > 0) {
            enableActionMode(position);
        } else {
            Intent intent = new Intent(ListagemPedidoEnviado.this, ActivityPedidoMain.class);
            PedidoHelper.setIdPedido(Integer.parseInt(listaPedidoAdapter.getItem(position).getId_web_pedido()));
            intent.putExtra("vizualizacao", 1);
            startActivity(intent);
        }
    }

    @Override
    public void onRowLongClicked(int position) {
        enableActionMode(position);
    }

    @Override
    public View.OnClickListener onClickExcluir(int position) {
        return null;
    }

    @Override
    public View.OnClickListener onClickEnviar(int position) {
        return null;
    }

    @Override
    public View.OnClickListener onClickDuplic(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder duplicAlert = new AlertDialog.Builder(ListagemPedidoEnviado.this);
                duplicAlert.setTitle("Atenção");
                duplicAlert.setMessage("Deseja duplicar o pedido selecionado para poder faturá-lo novamente?");
                duplicAlert.setNegativeButton("Não", null);
                duplicAlert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WebPedido webPedidoDuplicado = listaPedidoAdapter.getItem(position);
                        WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(db);
                        webPedidoDuplicado.setWebPedidoItens(webPedidoItensDAO.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedidoDuplicado.getId_web_pedido()));
                        webPedidoDuplicado.setId_web_pedido(null);
                        webPedidoDuplicado.setId_web_pedido_servidor(null);
                        webPedidoDuplicado.setPedido_enviado("N");
                        for (WebPedidoItens webPedidoItens : webPedidoDuplicado.getWebPedidoItens()) {
                            webPedidoItens.setId_web_item_servidor(null);
                            webPedidoItens.setId_pedido(null);
                        }
                        PedidoHelper.setWebPedido(webPedidoDuplicado);
                        PedidoHelper.setListaWebPedidoItens(webPedidoDuplicado.getWebPedidoItens());

                        Intent intent = new Intent(ListagemPedidoEnviado.this, ActivityPedidoMain.class);
                        startActivity(intent);

                        finish();
                    }
                });
                duplicAlert.show();
            }
        };
    }

    @Override
    public View.OnClickListener onClickCompartilhar(final int position) {
        return null;
    }

    @Override
    public View.OnClickListener onClickRastrear(final int position) {
        return null;
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        if (listaPedidoAdapter.getSelectedItemCount() <= 0 || listaPedidoAdapter.getItem(position).getId_web_pedido().equals(listaPedidoAdapter.getItensSelecionados().get(0).getId_web_pedido()))
            toggleSelection(position);
        else
            Toast.makeText(ListagemPedidoEnviado.this, "Somente é permitido a seleção de um pedido por vez", Toast.LENGTH_SHORT).show();
    }

    private void toggleSelection(int position) {
        listaPedidoAdapter.toggleSelection(position);
        int count = listaPedidoAdapter.getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode_pdf, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_pdf_pedido:
                    AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoEnviado.this);
                    alert.setTitle("Atenção");
                    alert.setMessage("Deseja gerar um arquivo PDF do pedido " + listaPedidoAdapter.getItensSelecionados().get(0).getId_web_pedido_servidor() + " selecionado ?");
                    alert.setNegativeButton("Não", null);
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                PDFPedidoUtil pdfPedidoUtil = new PDFPedidoUtil(listaPedidoAdapter.getItensSelecionados().get(0), ListagemPedidoEnviado.this);
                                Intent arquivo = new Intent(Intent.ACTION_VIEW);
                                arquivo.setDataAndType(Uri.fromFile(pdfPedidoUtil.criandoPdf()), "application/pdf");
                                arquivo.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                                Intent intent = Intent.createChooser(arquivo, "Abrir arquivo");
                                startActivity(intent);
                            } catch (Exception e) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoEnviado.this);
                                alert.setTitle("Atenção");
                                alert.setMessage("Ocorreu um erro ao gerar o PDF\n" + e.getMessage());
                                alert.setNeutralButton("OK", null);
                                alert.show();
                                e.printStackTrace();
                            }
                        }
                    });
                    alert.show();
                    return true;
                case R.id.action_pdf_pedido_email:
                    AlertDialog.Builder emailAlert = new AlertDialog.Builder(ListagemPedidoEnviado.this);
                    emailAlert.setTitle("Atenção");
                    emailAlert.setMessage("Deseja enviar um arquivo PDF do pedido " + listaPedidoAdapter.getItensSelecionados().get(0).getId_web_pedido_servidor() + " para o email do cliente ?");
                    emailAlert.setNegativeButton("Não", null);
                    emailAlert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                final Intent intent = new Intent(Intent.ACTION_SENDTO);

                                PDFPedidoUtil pdfPedidoUtil = new PDFPedidoUtil(listaPedidoAdapter.getItensSelecionados().get(0), ListagemPedidoEnviado.this);
                                if (listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_principal() != null && !listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_principal().trim().equals("")) {
                                    intent.setData(Uri.parse("mailto: " + listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_principal()));
                                } else if (listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_financeiro() != null && !listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_financeiro().trim().equals("")) {
                                    intent.setData(Uri.parse("mailto: " + listaPedidoAdapter.getItensSelecionados().get(0).getCadastro().getEmail_financeiro()));
                                } else {
                                    intent.setData(Uri.parse("mailto: Informe o email do cliente"));
                                }
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Espelho do pedido " + listaPedidoAdapter.getItensSelecionados().get(0).getId_web_pedido_servidor());
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pdfPedidoUtil.criandoPdf()));
                                intent.putExtra(Intent.EXTRA_TEXT, "Segue em anexo o espelho do pedido");

                                startActivity(intent);
                            } catch (Exception e) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(ListagemPedidoEnviado.this);
                                alert.setTitle("Atenção");
                                alert.setMessage("Ocorreu um erro ao gerar o PDF\n" + e.getMessage());
                                alert.setNeutralButton("OK", null);
                                alert.show();
                                e.printStackTrace();
                            }
                        }
                    });
                    emailAlert.show();
                    return true;
                case R.id.action_duplica_pedido:
                    AlertDialog.Builder duplicAlert = new AlertDialog.Builder(ListagemPedidoEnviado.this);
                    duplicAlert.setTitle("Atenção");
                    duplicAlert.setMessage("Deseja duplicar o pedido selecionado para poder faturá-lo novamente?");
                    duplicAlert.setNegativeButton("Não", null);
                    duplicAlert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            WebPedido webPedidoDuplicado = listaPedidoAdapter.getItensSelecionados().get(0);
                            WebPedidoItensDAO webPedidoItensDAO = new WebPedidoItensDAO(db);
                            webPedidoDuplicado.setWebPedidoItens(webPedidoItensDAO.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedidoDuplicado.getId_web_pedido()));
                            webPedidoDuplicado.setId_web_pedido(null);
                            webPedidoDuplicado.setId_web_pedido_servidor(null);
                            webPedidoDuplicado.setPedido_enviado("N");
                            for (WebPedidoItens webPedidoItens : webPedidoDuplicado.getWebPedidoItens()) {
                                webPedidoItens.setId_web_item_servidor(null);
                                webPedidoItens.setId_pedido(null);
                            }
                            PedidoHelper.setWebPedido(webPedidoDuplicado);
                            PedidoHelper.setListaWebPedidoItens(webPedidoDuplicado.getWebPedidoItens());

                            Intent intent = new Intent(ListagemPedidoEnviado.this, ActivityPedidoMain.class);
                            startActivity(intent);

                            actionMode.finish();
                            finish();
                        }
                    });
                    duplicAlert.show();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            listaPedidoAdapter.clearSelections();
            actionMode = null;
        }
    }
}
