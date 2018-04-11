package com.example.rcksuporte05.rcksistemas.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Switch;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.BO.SincroniaBO;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 26/10/2017.
 */

public class ActivityDialogSincronia extends Activity {

    @BindView(R.id.id_opcao_pedidos)
    Switch id_opcao_pedidos;

    @BindView(R.id.id_opcao_cliente)
    Switch id_opcao_cliente;

    @BindView(R.id.id_opcao_produto)
    Switch id_opcao_produto;

    @BindView(R.id.id_opcao_pedidos_pendentes)
    Switch id_opcao_pedidos_pendentes;

    @BindView(R.id.id_opcao_prospect)
    Switch id_opcao_prospect;

    @BindView(R.id.id_opcao_prospect_enviados)
    Switch id_opcao_prospect_enviados;

    @BindView(R.id.id_opcao_visiatas_pendentes)
    Switch id_opcao_visiatas_pendentes;

    private SincroniaBO sincroniaBO = new SincroniaBO();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sincronia);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnConfirmar)
    public void btnConfirmar() {
        Sincronia sincronia = new Sincronia(id_opcao_cliente.isChecked(),
                                             id_opcao_produto.isChecked(),
                                             id_opcao_pedidos.isChecked(),
                                             id_opcao_pedidos_pendentes.isChecked(),
                                             id_opcao_prospect.isChecked(),
                                             id_opcao_prospect_enviados.isChecked(),
                                             id_opcao_visiatas_pendentes.isChecked());
        sincroniaBO.sincronizaApi(sincronia);
        finish();
    }

    @OnClick(R.id.btnCancelar)
    public void btnCancelar() {
        finish();
    }
}
