package com.example.rcksuporte05.rcksistemas.Helper;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiroPendente;
import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiroQuitado;
import com.example.rcksuporte05.rcksistemas.extras.BancoWeb;
import com.example.rcksuporte05.rcksistemas.fragment.HistoricoFinanceiro1;
import com.example.rcksuporte05.rcksistemas.fragment.HistoricoFinanceiro2;
import com.example.rcksuporte05.rcksistemas.interfaces.HistoricoFinanceiroMain;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoFinanceiroHelper {
    private static HistoricoFinanceiroMain historicoFinanceiroMain;
    private static HistoricoFinanceiro1 historicoFinanceiro1;
    private static HistoricoFinanceiro2 historicoFinanceiro2;
    private static List<HistoricoFinanceiroPendente> listaVencidas = new ArrayList<>();
    private static List<HistoricoFinanceiroPendente> listaVencer = new ArrayList<>();
    private static List<HistoricoFinanceiroQuitado> listaQuitado;
    private BancoWeb bancoWeb = new BancoWeb();
    private ProgressDialog dialog;
    private AlertDialog.Builder alert;
    private Thread a;

    public HistoricoFinanceiroHelper() {
    }

    public HistoricoFinanceiroHelper(HistoricoFinanceiroMain historicoFinanceiroMain) {
        HistoricoFinanceiroHelper.historicoFinanceiroMain = historicoFinanceiroMain;
        System.gc();
    }

    public HistoricoFinanceiroHelper(HistoricoFinanceiro1 historicoFinanceiro1) {
        HistoricoFinanceiroHelper.historicoFinanceiro1 = historicoFinanceiro1;
        System.gc();
    }

    public HistoricoFinanceiroHelper(HistoricoFinanceiro2 historicoFinanceiro2) {
        HistoricoFinanceiroHelper.historicoFinanceiro2 = historicoFinanceiro2;
        System.gc();
    }

    public static List<HistoricoFinanceiroPendente> getListaVencer() {
        return listaVencer;
    }

    public void carregaRelatorio(final int idCliente) {
        a = new Thread(new Runnable() {
            @Override
            public void run() {

                historicoFinanceiroMain.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog = new ProgressDialog(historicoFinanceiroMain);
                        dialog.setMessage("Carregando historico financeiro!");
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                a.interrupt();
                                historicoFinanceiroMain.finish();
                                Toast.makeText(historicoFinanceiroMain, "Ação cancelada pelo usuário", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.show();
                    }
                });
                try {
                    final List<HistoricoFinanceiroPendente> listaPendentes = bancoWeb.sincronizaHistoricoFinanceiroPendente(idCliente);
                    for (int i = 0; listaPendentes.size() > i; i++) {
                        if (Integer.parseInt(listaPendentes.get(i).getDias_atrazo()) > 0) {
                            listaVencidas.add(listaPendentes.get(i));
                        } else {
                            listaVencer.add(listaPendentes.get(i));
                        }
                    }
                    listaQuitado = bancoWeb.sincronizaHistoricoFinanceiroQuitado(idCliente);

                    historicoFinanceiroMain.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            historicoFinanceiro1.carregarLista(listaVencidas);
                            historicoFinanceiro2.carregarLista(listaVencer);
                            dialog.dismiss();
                            if (listaQuitado.size() <= 0 && listaPendentes.size() <= 0) {
                                Toast.makeText(historicoFinanceiroMain, "Cliente sem historico financeiro.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (IOException | XmlPullParserException e) {
                    System.out.println(e.getMessage());
                    try {
                        historicoFinanceiroMain.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert = new AlertDialog.Builder(historicoFinanceiroMain);
                                alert.setTitle("Atenção!");
                                alert.setMessage("Não foi possivel carregar relatorio!\n        Verifique sua conexão com a internet!");
                                alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        historicoFinanceiroMain.finish();
                                    }
                                });
                                dialog.dismiss();
                                alert.show();
                            }
                        });
                    } catch (NullPointerException exception) {
                        System.out.println(exception.getMessage());
                    }
                }
            }
        });

        a.start();
    }

    public List<HistoricoFinanceiroPendente> getListaVencidas() {
        return listaVencidas;
    }

    public List<HistoricoFinanceiroQuitado> getListaQuitado() {
        return listaQuitado;
    }

    public void limparDados() {
        try {
            historicoFinanceiroMain = null;
            historicoFinanceiro1 = null;
            listaVencidas.clear();
            listaVencer.clear();
            listaQuitado.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
