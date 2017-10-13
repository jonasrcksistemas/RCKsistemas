package com.example.rcksuporte05.rcksistemas.bo;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.classes.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.classes.Municipios;
import com.example.rcksuporte05.rcksistemas.classes.Operacao;
import com.example.rcksuporte05.rcksistemas.classes.Paises;
import com.example.rcksuporte05.rcksistemas.classes.Produto;
import com.example.rcksuporte05.rcksistemas.classes.Sincronia;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPreco;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.classes.VendedorBonusResumo;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.interfaces.MainActivity;


/**
 * Created by RCK 03 on 06/10/2017.
 */

public class SincroniaBO {

    public void sincronizaBanco(Sincronia sincronia, Activity activity, ProgressDialog progress, NotificationCompat.Builder notificacao, NotificationManager mNotificationManager) {
        //controla o progresso da notificação e do progressDialog
        int contadorNotificacaoEProgresso = 0;

        DBHelper db = new DBHelper(activity);


        int maxProgress = sincronia.getListaCliente().size() +
                sincronia.getListaCondicoesPagamento().size() +
//                sincronia.getListaformaPagamento().size() +
                sincronia.getListaMunicipios().size() +
                sincronia.getListaOperacao().size() +
                sincronia.getListaPaises().size() +
                sincronia.getListaProduto().size() +
                sincronia.getListaTabelaPreco().size() +
                sincronia.getListaUsuario().size() +
                sincronia.getListaVendedorBonusResumo().size();

        progress.setIndeterminate(false);
        progress.setMax(maxProgress);

        //TODO VERIFICAR CLIENTES POR VENDEDOR
        //Primeiro delete todos os itens da tabela em questao
        db.alterar("DELETE FROM TBL_CADASTRO");
        //insere todos os itens da tabela em questao
        for (Cliente cliente : sincronia.getListaCliente()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);
            progress.setProgress(contadorNotificacaoEProgresso);

            db.inserirTBL_CADASTRO(cliente);

            //incrementa o progresso da notificao
            //ação e do progressDialog
            contadorNotificacaoEProgresso++;
            mNotificationManager.notify(0, notificacao.build());
        }

        db.alterar("DELETE FROM TBL_PRODUTO");

        for (Produto produto : sincronia.getListaProduto()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);
            progress.setProgress(contadorNotificacaoEProgresso);

            db.inserirTBL_PRODUTO(produto);

            contadorNotificacaoEProgresso++;
            mNotificationManager.notify(0, notificacao.build());
        }

        db.alterar("DELETE FROM TBL_PAISES");

        for (Paises paises : sincronia.getListaPaises()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);
            progress.setProgress(contadorNotificacaoEProgresso);

            db.inserirTBL_PAISES(paises);

            contadorNotificacaoEProgresso++;
            mNotificationManager.notify(0, notificacao.build());
        }

        for (Municipios municipio : sincronia.getListaMunicipios()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);
            progress.setProgress(contadorNotificacaoEProgresso);

            db.inserirTBL_MUNICIPIOS(municipio);

            contadorNotificacaoEProgresso++;
            mNotificationManager.notify(0, notificacao.build());
        }

        db.alterar("DELETE FROM TBL_OPERACAO_ESTOQUE");

        for (Operacao operacao : sincronia.getListaOperacao()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);
            progress.setProgress(contadorNotificacaoEProgresso);

            db.inserirTBL_OPERACAO_ESTOQUE(operacao);

            contadorNotificacaoEProgresso++;
            mNotificationManager.notify(0, notificacao.build());
        }

        db.alterar("DELETE FROM TBL_TABELA_PRECO_CAB");

        for (TabelaPreco tabelaPreco : sincronia.getListaTabelaPreco()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);
            progress.setProgress(contadorNotificacaoEProgresso);

            db.inserirTBL_TABELA_PRECO_CAB(tabelaPreco);

            contadorNotificacaoEProgresso++;
            mNotificationManager.notify(0, notificacao.build());
        }

        db.alterar("DELETE FROM TBL_TABELA_PRECO_ITENS");

        for (TabelaPrecoItem tabelaPrecoItem : sincronia.getListaTabelaPrecoItem()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);
            progress.setProgress(contadorNotificacaoEProgresso);

            db.inserirTBL_TABELA_PRECO_ITENS(tabelaPrecoItem);

            contadorNotificacaoEProgresso++;
            mNotificationManager.notify(0, notificacao.build());
        }

        db.alterar("DELETE FROM TBL_CONDICOES_PAG_CAB");

        for (CondicoesPagamento condicoesPagamento : sincronia.getListaCondicoesPagamento()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);
            progress.setProgress(contadorNotificacaoEProgresso);

            db.inserirTBL_CONDICOES_PAG_CAB(condicoesPagamento);

            contadorNotificacaoEProgresso++;
            mNotificationManager.notify(0, notificacao.build());
        }


        db.alterar("DELETE FROM TBL_VENDEDOR_BONUS_RESUMO");

        for (VendedorBonusResumo vendedorBonusResumo : sincronia.getListaVendedorBonusResumo()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);
            progress.setProgress(contadorNotificacaoEProgresso);

            db.inserirTBL_VENDEDOR_BONUS_RESUMO(vendedorBonusResumo);

            contadorNotificacaoEProgresso++;
            mNotificationManager.notify(0, notificacao.build());
        }

        Intent intent = new Intent(activity, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);
        notificacao.setContentText("Completo")
                .setContentTitle("Sincronia concluída")
                .setProgress(0, 0, false)
                .setSmallIcon(R.mipmap.ic_sincronia_sucesso)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(2)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        mNotificationManager.notify(0, notificacao.build());

        progress.dismiss();

    }
}