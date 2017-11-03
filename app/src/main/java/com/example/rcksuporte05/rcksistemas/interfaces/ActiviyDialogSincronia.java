package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.bo.SincroniaBO;
import com.example.rcksuporte05.rcksistemas.classes.Sincronia;

/**
 * Created by RCK 03 on 26/10/2017.
 */

public class ActiviyDialogSincronia extends Activity {

    private Switch id_opcao_cliente;
    private Switch id_opcao_produto;
    private Switch id_opcao_pedidos;
    private Button btnConfirmar;
    private Button btnCancelar;
    private SincroniaBO sincroniaBO = new SincroniaBO();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sincronia);

        id_opcao_cliente = (Switch) findViewById(R.id.id_opcao_cliente);
        id_opcao_produto = (Switch) findViewById(R.id.id_opcao_produto);
        id_opcao_pedidos = (Switch) findViewById(R.id.id_opcao_pedidos);
        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sincronia sincronia = new Sincronia(id_opcao_cliente.isChecked(), id_opcao_produto.isChecked(), id_opcao_pedidos.isChecked());
                sincroniaBO.sincronizaApi(sincronia);
                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
