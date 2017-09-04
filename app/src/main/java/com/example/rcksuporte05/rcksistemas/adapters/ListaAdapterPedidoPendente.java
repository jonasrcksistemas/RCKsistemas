package com.example.rcksuporte05.rcksistemas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListaAdapterPedidoPendente extends ArrayAdapter<WebPedido> {

    private Context context;
    private List<WebPedido> lista;
    private Date dataHora;

    public ListaAdapterPedidoPendente(Context context, List<WebPedido> lista) {
        super(context, 0, lista);

        this.context = context;
        this.lista = lista;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        WebPedido itemPosicao = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.pedido_lista, null);

        TextView txtIdPedido = (TextView) convertView.findViewById(R.id.txtIdPedido);
        TextView txtNomeCliente = (TextView) convertView.findViewById(R.id.txtNomeCliente);
        TextView txtPrecoPedido = (TextView) convertView.findViewById(R.id.txtPrecoPedido);
        TextView txtHoraPedido = (TextView) convertView.findViewById(R.id.txtHoraPedido);
        TextView txtDataPedido = (TextView) convertView.findViewById(R.id.txtDataPedido);
        TextView txtPontoCoeficiente = (TextView) convertView.findViewById(R.id.txtPontoCoeficiente);

        View viewCor = convertView.findViewById(R.id.viewCor);

        txtIdPedido.setText(itemPosicao.getId_web_pedido());

        txtNomeCliente.setText(itemPosicao.getCadastro().getNome_cadastro());
        txtPrecoPedido.setText(String.format("R$%.2f", Float.parseFloat(itemPosicao.getValor_total())));

        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dataHora = formatoEntrada.parse(itemPosicao.getUsuario_lancamento_data());
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yy");
            txtHoraPedido.setText("HORA : " + formatoHora.format(dataHora));
            txtDataPedido.setText("DATA : " + formatoData.format(dataHora));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        txtPontoCoeficiente.setText("COEF: " + itemPosicao.getPontos_coeficiente());
        viewCor.setBackgroundColor(Color.parseColor(itemPosicao.getPontos_cor()));

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
        }
        return convertView;
    }
}
