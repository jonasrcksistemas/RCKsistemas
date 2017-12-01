package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Switch;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.bo.SincroniaBO;
import com.example.rcksuporte05.rcksistemas.classes.Sincronia;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 26/10/2017.
 */

public class ActiviyDialogSincronia extends Activity {

    @BindView(R.id.id_opcao_pedidos)
    Switch id_opcao_pedidos;

    @BindView(R.id.id_opcao_cliente)
    Switch id_opcao_cliente;

    @BindView(R.id.id_opcao_produto)
    Switch id_opcao_produto;

    @BindView(R.id.id_opcao_pedidos_pendentes)
    Switch id_opcao_pedidos_pendentes;

    private SincroniaBO sincroniaBO = new SincroniaBO();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sincronia);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnConfirmar)
    public void btnConfirmar() {
        Sincronia sincronia = new Sincronia(id_opcao_cliente.isChecked(), id_opcao_produto.isChecked(), id_opcao_pedidos.isChecked(), id_opcao_pedidos_pendentes.isChecked());
        sincroniaBO.sincronizaApi(sincronia);
        finish();
    }

    @OnClick(R.id.btnCancelar)
    public void btnCancelar() {
        finish();
    }
}
