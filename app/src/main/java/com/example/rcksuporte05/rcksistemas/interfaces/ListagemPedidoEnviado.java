package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.NotificationManager;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.PedidoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.ListaAdapterPedidoEnviado;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ListagemPedidoEnviado extends AppCompatActivity {

    private ListView lstPedidoEnviado;
    private ListaAdapterPedidoEnviado listaAdapterPedidoEnviado;
    private List<WebPedido> listaPedido = new ArrayList();
    private EditText edtNumerPedidoEnviados;
    private DBHelper db = new DBHelper(this);
    private Bundle bundle = new Bundle();
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pedido_enviado);

        bundle = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPedidoEnviado);
        toolbar.setTitle("Pedidos Enviados");
        setSupportActionBar(toolbar);
        usuario = db.listaUsuario("SELECT * FROM TBL_WEB_USUARIO WHERE LOGIN = '" + db.consulta("SELECT * FROM TBL_LOGIN;", "LOGIN") + "';").get(0);
        lstPedidoEnviado = (ListView) findViewById(R.id.lstPedidoEnviado);
        lstPedidoEnviado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListagemPedidoEnviado.this, "Pressione e segure sobre o pedido para vizualiza-lo!", Toast.LENGTH_SHORT).show();
            }
        });
        edtNumerPedidoEnviados = (EditText) findViewById(R.id.edtNumeroPedidoEnviado);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Pedido: " + listaPedido.get(info.position).getId_web_pedido_servidor());

        MenuItem visualizar = menu.add("Visualizar");
        visualizar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ListagemPedidoEnviado.this, ActivityPedidoMain.class);
                PedidoHelper.setIdPedido(Integer.parseInt(listaPedido.get(info.position).getId_web_pedido()));
                bundle = new Bundle();
                bundle.putInt("vizualizacao", 1);
                bundle.putInt("usuario", Integer.parseInt(listaPedido.get(info.position).getUsuario_lancamento_id()));
                bundle.putInt("vendedor", Integer.parseInt(listaPedido.get(info.position).getCadastro().getId_vendedor()));
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        try {
            listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'S' AND USUARIO_LANCAMENTO_ID = " + usuario.getId_usuario() + " ORDER BY ID_WEB_PEDIDO_SERVIDOR;");

            listaAdapterPedidoEnviado = new ListaAdapterPedidoEnviado(ListagemPedidoEnviado.this, listaPedido);
            lstPedidoEnviado.setAdapter(listaAdapterPedidoEnviado);

            registerForContextMenu(lstPedidoEnviado);

        } catch (CursorIndexOutOfBoundsException e) {
            Toast.makeText(this, "Nenuhm pedido foi Enviado!", Toast.LENGTH_LONG).show();
        }
        edtNumerPedidoEnviados.setText(listaPedido.size() + ": Pedidos Enviados");
        super.onResume();
    }
}
