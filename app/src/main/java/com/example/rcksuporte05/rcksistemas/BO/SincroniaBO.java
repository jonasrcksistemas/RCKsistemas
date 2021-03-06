package com.example.rcksuporte05.rcksistemas.BO;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroCondicoesPagDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CadastroFinanceiroResumoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComClientesDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialCabDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CampanhaComercialItensDAO;
import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.ProdutoLinhaColecaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoClienteDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.PromocaoProdutoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.activity.MainActivity;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.CadastroCondicoesPag;
import com.example.rcksuporte05.rcksistemas.model.CadastroFinanceiroResumo;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComClientes;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialItens;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.MotivoNaoCadastramento;
import com.example.rcksuporte05.rcksistemas.model.Operacao;
import com.example.rcksuporte05.rcksistemas.model.Pais;
import com.example.rcksuporte05.rcksistemas.model.Produto;
import com.example.rcksuporte05.rcksistemas.model.ProdutoLinhaColecao;
import com.example.rcksuporte05.rcksistemas.model.Promocao;
import com.example.rcksuporte05.rcksistemas.model.PromocaoCliente;
import com.example.rcksuporte05.rcksistemas.model.PromocaoProduto;
import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.model.Sincronia;
import com.example.rcksuporte05.rcksistemas.model.TabelaPreco;
import com.example.rcksuporte05.rcksistemas.model.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.model.Usuario;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;

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
    private CategoriaDAO categoriaDAO;
    private PromocaoDAO promocaoDAO;
    private PromocaoClienteDAO promocaoClienteDAO;
    private PromocaoProdutoDAO promocaoProdutoDAO;
    private WebPedidoDAO webPedidoDAO;
    private WebPedidoItensDAO webPedidoItensDAO;
    private CadastroFinanceiroResumoDAO cadastroFinanceiroResumoDAO;
    private CadastroAnexoDAO cadastroAnexoDAO;
    private CampanhaComClientesDAO campanhaComClientesDAO;
    private CampanhaComercialCabDAO campanhaComercialCabDAO;
    private CampanhaComercialItensDAO campanhaComercialItensDAO;
    private ProdutoLinhaColecaoDAO produtoLinhaColecaoDAO;
    private CadastroCondicoesPagDAO cadastroCondicoesPagDAO;

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        SincroniaBO.activity = activity;
    }

    public void sincronizaBanco(final Sincronia sincronia, final NotificationCompat.Builder notificacao, final NotificationManager mNotificationManager, final ProgressDialog progress) {
        //controla o progresso da notificação e do progressDialog
        int contadorNotificacaoEProgresso = 0;
        String relatorio = "";

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
            db.alterar("DELETE FROM TBL_CADASTRO WHERE ID_CADASTRO_SERVIDOR > 0;");
            //insere todos os itens da tabela em questao
            for (Cliente cliente : sincronia.getListaCliente()) {
                db.excluirClienteServidor(cliente);
                notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

                cliente.setFinalizado("S");
                int idCadastro = db.inserirTBL_CADASTRO(cliente);
                if (cliente.getListaCadastroAnexo().size() > 0) {
                    for (CadastroAnexo cadastroAnexo : cliente.getListaCadastroAnexo()) {
                        cadastroAnexo.setIdCadastro(idCadastro);
                        cadastroAnexoDAO.atualizarCadastroAnexo(cadastroAnexo);
                    }
                }

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

        db.alterar("DELETE FROM TBL_PAISES");

        for (Pais pais : sincronia.getListaPais()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

            db.inserirTBL_PAISES(pais);

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

        for (MotivoNaoCadastramento motivo : sincronia.getMotivos()) {
            db.atualizarTBL_CADASTRO_MOTIVO_NAO_CAD(motivo);

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

                final CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
                webPedido.getCadastro().setAlterado("N");
                webPedido.setFinalizado("S");
                db.atualizarTBL_CADASTRO(webPedido.getCadastro());
                db.alterar("DELETE FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + webPedido.getCadastro().getId_cadastro() + ";");
                if (webPedido.getCadastro().getListaCadastroAnexo().size() > 0) {
                    for (CadastroAnexo cadastroAnexo : webPedido.getCadastro().getListaCadastroAnexo()) {
                        if (cadastroAnexo.getExcluido().equals("N"))
                            cadastroAnexoDAO.atualizarCadastroAnexo(cadastroAnexo);
                        else if (cadastroAnexo.getExcluido().equals("S"))
                            db.alterar("DELETE FROM TBL_CADASTRO_ANEXOS WHERE ID_ANEXO = " + cadastroAnexo.getIdAnexo() + ";");
                    }
                } else {
                    db.alterar("DELETE FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + webPedido.getCadastro().getId_cadastro() + ";");
                }

                webPedidoDAO.atualizarTBL_WEB_PEDIDO(webPedido);
                for (WebPedidoItens pedidoIten : webPedido.getWebPedidoItens()) {
                    webPedidoItensDAO.atualizarTBL_WEB_PEDIDO_ITENS(pedidoIten);
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

        if (sincronia.isProspectPendentes()) {
            if (sincronia.getListaProspectPendentes() != null && sincronia.getListaProspectPendentes().size() > 0) {
                for (Prospect prospect : sincronia.getListaProspectPendentes()) {
                    notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

                    db.atualizarTBL_PROSPECT(prospect);


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
        }

        if (sincronia.isProspectEnviados()) {
            if (sincronia.getListaProspectEnviados() != null) {
                for (Prospect prospect : sincronia.getListaProspectEnviados()) {
                    if (db.contagem("SELECT COUNT(*) FROM TBL_PROSPECT WHERE ID_CADASTRO = " + prospect.getId_cadastro()) > 0) {
                        try {
                            db.excluiProspectPorIdServidor(prospect);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    prospect.setProspectSalvo("S");
                    db.atualizarTBL_PROSPECT(prospect);

                    if (prospect.getVisitas() != null && prospect.getVisitas().size() > 0) {
                        for (VisitaProspect visita : prospect.getVisitas()) {
                            visita.setProspect(prospect);
                            db.atualizaTBL_VISITA_PROSPECT(visita);
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
            }
        }

        if (sincronia.getListaProspectPositivados().size() > 0) {
            for (Prospect prospectPositivado : sincronia.getListaProspectPositivados()) {
                db.excluiProspectPorIdServidor(prospectPositivado);

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

        if (sincronia.isVisitasPendentes()) {
            if (sincronia.getVisitas() != null && sincronia.getVisitas().size() > 0) {
                for (VisitaProspect visita : sincronia.getVisitas()) {
                    db.atualizaTBL_VISITA_PROSPECT(visita);

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
        }

        if (sincronia.isPedidosFinalizados()) {
            for (WebPedido webPedido : sincronia.getListaWebPedidosFinalizados()) {
                notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

                webPedido.setPedido_enviado("S");
                webPedido.setFinalizado("S");
                if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO_SERVIDOR = " + webPedido.getId_web_pedido_servidor()) > 0)
                    db.alterar("DELETE FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO_SERVIDOR = " + webPedido.getId_web_pedido_servidor());
                webPedidoDAO.inserirTBL_WEB_PEDIDO(webPedido);
                for (WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
                    String idPedido = db.consulta("SELECT * FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO_SERVIDOR = " + webPedido.getId_web_pedido_servidor(), "ID_WEB_PEDIDO");
                    webPedidoItens.setId_pedido(idPedido);
                    webPedidoItensDAO.atualizarTBL_WEB_PEDIDO_ITENS(webPedidoItens);
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

        db.alterar("DELETE FROM TBL_CADASTRO_CATEGORIA;");

        for (Categoria categoria : sincronia.getListaCategoria()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

            categoriaDAO.atualizaCategoria(categoria);

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

        db.alterar("DELETE FROM TBL_CADASTRO_FINANCEIRO_RESUMO;");
        for (CadastroFinanceiroResumo cadastroFinanceiroResumo : sincronia.getListaCadastroFinanceiroResumo()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

            cadastroFinanceiroResumoDAO.atualizarCadastroFinanceiroResumo(cadastroFinanceiroResumo);

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

        db.alterar("DELETE FROM TBL_PROMOCAO_CAB");

        for (Promocao promocao : sincronia.getListaPromocao()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

            promocaoDAO.atualizaPromocao(promocao);

            db.alterar("DELETE FROM TBL_PROMOCAO_CLIENTE WHERE ID_PROMOCAO = " + promocao.getIdPromocao());
            for (PromocaoCliente promocaoCliente : promocao.getListaPromoCliente()) {
                promocaoClienteDAO.atualizaPromocaoCliente(promocaoCliente);

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

            db.alterar("DELETE FROM TBL_PROMOCAO_PRODUTO WHERE ID_PROMOCAO = " + promocao.getIdPromocao());
            for (PromocaoProduto promocaoProduto : promocao.getListaPromoProduto()) {
                promocaoProdutoDAO.atualizaPromocaoProduto(promocaoProduto);

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

        db.alterar("DELETE FROM TBL_CAMPANHA_COM_CLIENTES;");
        for (CampanhaComClientes campanhaComClientes : sincronia.getListaCampanhaComClientes()) {
            campanhaComClientesDAO.atualizaCampanhaComClientes(campanhaComClientes);

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

        db.alterar("DELETE FROM TBL_CAMPANHA_COMERCIAL_CAB;");
        for (CampanhaComercialCab campanhaComercialCab : sincronia.getListaCampanhaComercialCab()) {
            campanhaComercialCabDAO.atualizaCampanhaComercialCab(campanhaComercialCab);

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

        db.alterar("DELETE FROM TBL_CAMPANHA_COMERCIAL_ITENS;");
        for (CampanhaComercialItens campanhaComercialItens : sincronia.getListaCampanhaComercialItens()) {
            campanhaComercialItensDAO.atualizaCampanhaComercialItens(campanhaComercialItens);

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

        db.alterar("DELETE FROM TBL_PRODUTO_LINHA_COLECAO;");
        for (ProdutoLinhaColecao produtoLinhaColecao : sincronia.getListaProdutoLinhaColecao()) {
            produtoLinhaColecaoDAO.atualizaProdutoLinhaColecao(produtoLinhaColecao);

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

        db.alterar("DELETE FROM TBL_CADASTRO_CONDICOES_PAG;");
        for (CadastroCondicoesPag cadastroCondicoesPag : sincronia.getListaCadastroCondicoesPag()) {
            cadastroCondicoesPagDAO.atualizaCadastroCondicoesPag(cadastroCondicoesPag);

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

        db.alterar("DELETE FROM TBL_WEB_PEDIDO WHERE ID_OPERACAO = 66;");
        for (WebPedido webPedido : sincronia.getListaPedidosBonus()) {
            notificacao.setProgress(maxProgress, contadorNotificacaoEProgresso, false);

            webPedido.setPedido_enviado("S");
            webPedido.setFinalizado("S");
            webPedido.setStatus("L");
            if (db.contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO_SERVIDOR = " + webPedido.getId_web_pedido_servidor()) > 0)
                db.alterar("DELETE FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO_SERVIDOR = " + webPedido.getId_web_pedido_servidor());
            webPedidoDAO.inserirTBL_WEB_PEDIDO(webPedido);
            for (WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
                String idPedido = db.consulta("SELECT * FROM TBL_WEB_PEDIDO WHERE ID_WEB_PEDIDO_SERVIDOR = " + webPedido.getId_web_pedido_servidor(), "ID_WEB_PEDIDO");
                webPedidoItens.setId_pedido(idPedido);
                webPedidoItensDAO.atualizarTBL_WEB_PEDIDO_ITENS(webPedidoItens);
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
                if (sincronia.isProduto()) {
                    db.alterar("UPDATE TBL_LOGIN SET DATA_SINCRONIA = '" + db.pegaDataHoraAtual() + "', DATA_SINCRONIA_PRODUTO = '" + db.pegaDataHoraAtual() + "';");
                } else {
                    db.alterar("UPDATE TBL_LOGIN SET DATA_SINCRONIA = '" + db.pegaDataHoraAtual() + "';");
                }
                alert.show();
            }
        });


    }

    public void sincronizaApi(final Sincronia sincronia) {
        final ImageView ivInternet = (ImageView) activity.findViewById(R.id.ivInternet);

        categoriaDAO = new CategoriaDAO(db);
        promocaoDAO = new PromocaoDAO(db);
        promocaoClienteDAO = new PromocaoClienteDAO(db);
        promocaoProdutoDAO = new PromocaoProdutoDAO(db);
        webPedidoDAO = new WebPedidoDAO(db);
        webPedidoItensDAO = new WebPedidoItensDAO(db);
        cadastroFinanceiroResumoDAO = new CadastroFinanceiroResumoDAO(db);
        cadastroAnexoDAO = new CadastroAnexoDAO(db);
        campanhaComClientesDAO = new CampanhaComClientesDAO(db);
        campanhaComercialCabDAO = new CampanhaComercialCabDAO(db);
        campanhaComercialItensDAO = new CampanhaComercialItensDAO(db);
        produtoLinhaColecaoDAO = new ProdutoLinhaColecaoDAO(db);
        cadastroCondicoesPagDAO = new CadastroCondicoesPagDAO(db);

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
        progress.setTitle("Sincronizando, aguarde");
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
                    final List<WebPedido> listaPedido = webPedidoDAO.listaWebPedido("SELECT * FROM TBL_WEB_PEDIDO WHERE PEDIDO_ENVIADO = 'N' AND USUARIO_LANCAMENTO_ID = " + UsuarioHelper.getUsuario().getId_usuario() + " AND FINALIZADO = 'S' ORDER BY ID_WEB_PEDIDO DESC;");
                    sincronia.setListaWebPedidosPendentes(prepararItensPedidos(listaPedido));
                }

                if (sincronia.isProspectPendentes()) {
                    try {
                        final List<Prospect> listaProspect = db.listaProspect(Prospect.PROSPECT_SALVO);
                        sincronia.setListaProspectPendentes(listaProspect);
                    } catch (CursorIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }

                if (sincronia.isVisitasPendentes()) {
                    sincronia.setVisitas(db.listaProspectsPendentes());
                }

                Call<Sincronia> call = apiRotas.sincroniaApi(Integer.parseInt(UsuarioHelper.getUsuario().getId_usuario()), cabecalho, sincronia);
                try {
                    Response<Sincronia> response = call.execute();
                    if (response.code() == 200) {
                        Sincronia sincronia = response.body();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ivInternet.setVisibility(View.GONE);
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

    private List<WebPedido> prepararItensPedidos(List<WebPedido> listaPedido) {
        for (WebPedido pedido : listaPedido) {
            if (pedido.getCadastro().getId_cadastro_servidor() <= 0) {
                if (db.contagem("SELECT COUNT(ID_ANEXO) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + pedido.getCadastro().getId_cadastro() + " AND EXCLUIDO = 'N';") > 0) {

                    final CadastroAnexoBO cadastroAnexoBO = new CadastroAnexoBO();

                    List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoBO.listaCadastroAnexoComMiniatura(activity, pedido.getCadastro().getId_cadastro());
                    pedido.getCadastro().setListaCadastroAnexo(listaCadastroAnexo);
                }
            }

            List<WebPedidoItens> webPedidoItenses;

            webPedidoItenses = webPedidoItensDAO.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + pedido.getId_web_pedido());
            pedido.setWebPedidoItens(webPedidoItenses);
        }
        return listaPedido;
    }
}
