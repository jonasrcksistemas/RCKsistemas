package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiroPendente;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListaAdapterHistoricoFinanceiroPendentes extends ArrayAdapter<HistoricoFinanceiroPendente> {

    private Context context;
    private List<HistoricoFinanceiroPendente> lista;

    public ListaAdapterHistoricoFinanceiroPendentes(Context context, List<HistoricoFinanceiroPendente> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        HistoricoFinanceiroPendente itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.lista_financeiro_pendente, null);

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        TextView txtDocumento = (TextView) convertView.findViewById(R.id.txtDocumento);
        TextView txtParcela = (TextView) convertView.findViewById(R.id.txtParcela);
        TextView txtEmissao = (TextView) convertView.findViewById(R.id.txtEmissao);
        TextView txtVencimento = (TextView) convertView.findViewById(R.id.txtVencimento);
        TextView txtDiasVenc = (TextView) convertView.findViewById(R.id.txtDiasVenc);
        TextView txtValor = (TextView) convertView.findViewById(R.id.txtValor);

        try {
            txtDocumento.setHeight(50);
            txtEmissao.setText(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyyy-MM-dd").parse(itemPosicao.getData_emissao())));
            txtVencimento.setText(new SimpleDateFormat("dd/MM/yy").format(new SimpleDateFormat("yyyy-MM-dd").parse(itemPosicao.getData_vencimento())));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        txtDocumento.setText(itemPosicao.getDocumento());
        txtParcela.setText(itemPosicao.getParcela());
        txtDiasVenc.setText(itemPosicao.getDias_atrazo());
        txtValor.setText(MascaraUtil.mascaraReal(itemPosicao.getValor_total()));

        if (Integer.parseInt(itemPosicao.getDias_atrazo()) > 0) {
            txtValor.setTextColor(Color.RED);
        } else {
            txtValor.setTextColor(Color.parseColor("#4caf50"));
        }

        return convertView;
    }
}
