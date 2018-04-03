package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.HistoricoFinanceiroQuitado;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListaAdapterHistoricoFinanceiroQuitado extends ArrayAdapter<HistoricoFinanceiroQuitado> {

    private Context context;
    private List<HistoricoFinanceiroQuitado> lista;

    public ListaAdapterHistoricoFinanceiroQuitado(Context context, List<HistoricoFinanceiroQuitado> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        HistoricoFinanceiroQuitado itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.lista_financeiro_quitado, null);

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        TextView txtDocumento = (TextView) convertView.findViewById(R.id.txtDocumento);
        TextView txtParcela = (TextView) convertView.findViewById(R.id.txtParcela);
        TextView txtVencimento = (TextView) convertView.findViewById(R.id.txtVencimento);
        TextView txtValor = (TextView) convertView.findViewById(R.id.txtValor);
        TextView txtPagamento = (TextView) convertView.findViewById(R.id.txtPagamento);
        TextView txtPontualidade = (TextView) convertView.findViewById(R.id.txtPontualidade);

        try {
            txtVencimento.setText(new SimpleDateFormat("dd/mm/yy").format(new SimpleDateFormat("yyyy-mm-dd").parse(itemPosicao.getData_emissao())));
            txtPagamento.setText(new SimpleDateFormat("dd/mm/yy").format(new SimpleDateFormat("yyyy-mm-dd").parse(itemPosicao.getData_baixa())));
            txtPontualidade.setText(itemPosicao.getPontualidade());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        txtDocumento.setText(itemPosicao.getDocumento());
        txtParcela.setText(itemPosicao.getParcela());

        txtValor.setText(MascaraUtil.mascaraReal(itemPosicao.getValor_total()));
        if (itemPosicao.getPontualidade_status().trim().equals("A")) {
            txtValor.setTextColor(Color.RED);
        } else {
            txtValor.setTextColor(Color.parseColor("#4caf50"));
        }

        return convertView;
    }
}
