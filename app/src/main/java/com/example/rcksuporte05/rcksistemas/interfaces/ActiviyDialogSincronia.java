package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Switch;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RCK 03 on 26/10/2017.
 */

public class ActiviyDialogSincronia extends Activity {

    @BindView(R.id.id_opcao_cliente)
    Switch switchCliente;
    @BindView(R.id.id_opcao_produto)
    Switch switchProduto;
    @BindView(R.id.id_opcao_pedidos)
    Switch switchPedidos;

    @Override
    public  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setContentView(R.layout.dialog_sincronia);



    }


}
