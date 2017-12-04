package com.example.rcksuporte05.rcksistemas.bo;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.classes.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.classes.Operacao;
import com.example.rcksuporte05.rcksistemas.classes.Produto;
import com.example.rcksuporte05.rcksistemas.classes.Sincronia;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPreco;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.classes.VendedorBonusResumo;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.classes.WebPedidoItens;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.interfaces.MainActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by RCK 03 on 06/10/2017.
 */

public class SincroniaBO {

    private static Activity activity;
    private DBHelper db = new DBHelper(activity);

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        SincroniaBO.activity = activity;
    }

    public void sincronizaBanco(Sincronia sincronia, final NotificationCompat.Builder notificacao, final NotificationManager mNotificationManager, final ProgressDialog progress) {
        //controla o progresso da notificação e do progressDialog
        int contadorNotificacaoEProgresso = 0;

        final int maxProgress = sincronia.getMaxProgress();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.setIndeterminate(false);
                progress.setMax(maxProgress);
            }
        });

        //Primeiro delete todos os itens da tabela em questao
        if (sincronia.isCliente()) {
            db.alterar("DELETE FROM TBL_CADASTRO");
            //insere todos os itens da tabela em questao
            for (Cliente cliente : sincronia.getListaCliente()) {
                notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

                db.inserirTBL_CADASTRO(cliente);

                //incrementa o progresso da notificao
                //ação e do progressDialog
                contadorNotificacaoEProgresso++;
                final int finalContadorNotificacaoEProgresso = contadorNotificacaoEProgresso;
                //SubThread necessária para manipulação de componentes da tela
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setProgress(finalContadorNotificacaoEProgresso);
                    }
                });
                mNotificationManager.notify(0, notificacao.build());
            }
        }

        db.alterar("DELETE FROM TBL_WEB_USUARIO");

        for (Usuario usuario : sincronia.getListaUsuario()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

            db.inserirTBL_WEB_USUARIO(usuario);

            contadorNotificacaoEProgresso++;
            final int finalContadorNotificacaoEProgresso = contadorNotificacaoEProgresso;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setProgress(finalContadorNotificacaoEProgresso);
                }
            });
            mNotificationManager.notify(0, notificacao.build());
        }

        if (sincronia.isProduto()) {
            db.alterar("DELETE FROM TBL_PRODUTO");

            for (Produto produto : sincronia.getListaProduto()) {
                notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

                db.inserirTBL_PRODUTO(produto);

                contadorNotificacaoEProgresso++;
                final int finalContadorNotificacaoEProgresso = contadorNotificacaoEProgresso;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setProgress(finalContadorNotificacaoEProgresso);
                    }
                });
                mNotificationManager.notify(0, notificacao.build());
            }
        }

        db.alterar("DELETE FROM TBL_OPERACAO_ESTOQUE");

        for (Operacao operacao : sincronia.getListaOperacao()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

            db.inserirTBL_OPERACAO_ESTOQUE(operacao);

            contadorNotificacaoEProgresso++;
            final int finalContadorNotificacaoEProgresso = contadorNotificacaoEProgresso;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setProgress(finalContadorNotificacaoEProgresso);
                }
            });
            mNotificationManager.notify(0, notificacao.build());
        }

        db.alterar("DELETE FROM TBL_TABELA_PRECO_CAB");

        for (TabelaPreco tabelaPreco : sincronia.getListaTabelaPreco()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

            db.inserirTBL_TABELA_PRECO_CAB(tabelaPreco);

            contadorNotificacaoEProgresso++;
            final int finalContadorNotificacaoEProgresso = contadorNotificacaoEProgresso;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setProgress(finalContadorNotificacaoEProgresso);
                }
            });
            mNotificationManager.notify(0, notificacao.build());
        }

        db.alterar("DELETE FROM TBL_TABELA_PRECO_ITENS");

        for (TabelaPrecoItem tabelaPrecoItem : sincronia.getListaTabelaPrecoItem()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

            db.inserirTBL_TABELA_PRECO_ITENS(tabelaPrecoItem);

            contadorNotificacaoEProgresso++;
            final int finalContadorNotificacaoEProgresso = contadorNotificacaoEProgresso;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setProgress(finalContadorNotificacaoEProgresso);
                }
            });
            mNotificationManager.notify(0, notificacao.build());
        }

        db.alterar("DELETE FROM TBL_CONDICOES_PAG_CAB");

        for (CondicoesPagamento condicoesPagamento : sincronia.getListaCondicoesPagamento()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

            db.inserirTBL_CONDICOES_PAG_CAB(condicoesPagamento);

            contadorNotificacaoEProgresso++;
            final int finalContadorNotificacaoEProgresso = contadorNotificacaoEProgresso;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setProgress(finalContadorNotificacaoEProgresso);
                }
            });
            mNotificationManager.notify(0, notificacao.build());
        }


        db.alterar("DELETE FROM TBL_VENDEDOR_BONUS_RESUMO");

        for (VendedorBonusResumo vendedorBonusResumo : sincronia.getListaVendedorBonusResumo()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

            db.inserirTBL_VENDEDOR_BONUS_RESUMO(vendedorBonusResumo);

            contadorNotificacaoEProgresso++;
            final int finalContadorNotificacaoEProgresso = contadorNotificacaoEProgresso;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setProgress(finalContadorNotificacaoEProgresso);
                }
            });
            mNotificationManager.notify(0, notificacao.build());
        }

        if (sincronia.isPedidosPendentes()) {
            for (WebPedido webPedido : sincronia.getListaWebPedidosPendentes()) {
                notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

                db.atualizarTBL_WEB_PEDIDO(webPedido);
                for (WebPedidoItens pedidoIten : webPedido.getWebPedidoItens()) {
                    db.atualizarTBL_WEB_PEDIDO_ITENS(pedidoIten);
                }
            }

            contadorNotificacaoEProgresso++;
            final int finalContadorNotificacaoEProgresso = contadorNotificacaoEProgresso;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setProgress(finalContadorNotificacaoEProgresso);
                }
            });
            mNotificationManager.notify(0, notificacao.build());
        }

        if (sincronia.isPedidosFinalizados()) {
            for (WebPedido webPedido : sincronia.getListaWebPedidosFinalizados()) {
                notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

                webPedido.setPedido_enviado("S");
                if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO_SERVIDOR = " + webPedido.getId_web_pedido_servidor()) > 0) {
                    db.alterar("DELETE FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO_SERVIDOR = " + webPedido.getId_web_pedido_servidor());
                }
                db.inserirTBL_WEB_PEDIDO(webPedido);
                for (WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
                    String idPedido = db.consulta("SELECT * FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO_SERVIDOR = " + webPedido.getId_web_pedido_servidor(), "ID_WEB_PEDIDO");
                    webPedidoItens.setId_pedido(idPedido);
                    db.atualizarTBL_WEB_PEDIDO_ITENS(webPedidoItens);
                }

                contadorNotificacaoEProgresso++;
                final int finalContadorNotificacaoEProgresso = contadorNotificacaoEProgresso;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setProgress(finalContadorNotificacaoEProgresso);
                    }
                });
                mNotificationManager.notify(0, notificacao.build());
            }
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

        System.gc();

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setMessage("Sincronia concluida com sucesso");
        alert.setTitle("Atenção");
        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mNotificationManager.cancel(0);
                System.gc();
            }
        });
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.dismiss();
                alert.show();
            }
        });

    }

    public void sincronizaApi(final Sincronia sincronia) {
        final ImageView ivInternet = (ImageView) activity.findViewById(R.id.ivInternet);

        final NotificationCompat.Builder notificacao = new NotificationCompat.Builder(activity)
                .setSmallIcon(R.mipmap.ic_sincroniza_main)
                .setContentTitle("Sincronia em andamento")
                .setContentText("Aguarde")
                .setPriority(2)
                .setProgress(0, 0, true);
        final NotificationManager mNotificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notificacao.build());

        final ProgressDialog progress = new ProgressDialog(activity);
        progress.setMessage("Sincronia em execução");
        progress.setTitle("Aguarde");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        final Rotas apiRotas = Api.buildRetrofit();

        final Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> cabecalho = new HashMap<>();
                cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

                if (sincronia.isPedidosPendentes()) {
                    final List<WebPedido> listaPedido = db.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " ORDER BY ID_WEB_PEDIDO DESC;");
                    sincronia.setListaWebPedidosPendentes(prepararItensPedidos(listaPedido));
                }
                Call<Sincronia> call = apiRotas.sincroniaApi(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho, sincronia);

                try {
                    Response<Sincronia> response = call.execute();
                    if (response.code() == 200) {
                        Sincronia sincronia = response.body();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivInternet.setVisibility(View.INVISIBLE);
                            }
                        });
                        sincronizaBanco(sincronia, notificacao, mNotificationManager, progress);
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivInternet.setVisibility(View.VISIBLE);
                                System.out.println("Houve um problema na requisição, entre em contato no suporte para esclarecer a situação");
                                progress.dismiss();
                            }
                        });
                        notificacao.setContentText("Erro de comunicação")
                                .setContentTitle("Houve um problema na requisição, entre em contato no suporte para esclarecer a situação")
                                .setProgress(0, 0, false)
                                .setSmallIcon(R.mipmap.ic_sem_internet)
                                .setDefaults(NotificationCompat.DEFAULT_ALL)
                                .setPriority(2)
                                .setAutoCancel(true);
                        mNotificationManager.notify(0, notificacao.build());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivInternet.setVisibility(View.VISIBLE);
                            progress.dismiss();
                        }
                    });
                    notificacao.setContentText("Erro de comunicação")
                            .setContentTitle("Verifique sua conexão e tente novamente")
                            .setProgress(0, 0, false)
                            .setSmallIcon(R.mipmap.ic_sem_internet)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(2)
                            .setAutoCancel(true);
                    mNotificationManager.notify(0, notificacao.build());
                }
                activity = null;
            }
        });
        a.start();
    }

    public List<WebPedido> prepararItensPedidos(List<WebPedido> listaPedido) {
        for (WebPedido pedido : listaPedido) {
            List<WebPedidoItens> webPedidoItenses;

            webPedidoItenses = db.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido());
            pedido.setWebPedidoItens(webPedidoItenses);
        }
        return listaPedido;
    }
}
