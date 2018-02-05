package com.example.rcksuporte05.rcksistemas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.adapters.viewHolder.ContatoViewHolder;
import com.example.rcksuporte05.rcksistemas.classes.Contato;
import com.example.rcksuporte05.rcksistemas.util.MascaraTelefone;

import java.util.List;

/**
 * Created by RCK 03 on 03/02/2018.
 */

public class ListaContatoAdapter extends RecyclerView.Adapter<ContatoViewHolder>{

    private ListaContatoListener listener;
    private List<Contato> contatos;

    public ListaContatoAdapter(List<Contato> contatos, ListaContatoListener listener) {
        this.listener = listener;
        this.contatos = contatos;
    }

    @Override
    public ContatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contato_itens,parent,false);

        return new ContatoViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ContatoViewHolder holder, int position) {
        holder.txtNomeResponsavelProspectlist.setText(contatos.get(position).getPessoa_contato());
        holder.txtFuncaoResponsalveProspectList.setText(contatos.get(position).getFuncao());
        if (contatos.get(position).getNumero_telefone() != null && !contatos.get(position).getNumero_telefone().replaceAll("[^0-9]", "").trim().isEmpty() && contatos.get(position).getNumero_telefone().replaceAll("[^0-9]", "").length() >= 8 && contatos.get(position).getNumero_telefone().replaceAll("[^0-9]", "").length() <= 11) {
            holder.txtCelular1ProspectList.setText(MascaraTelefone.formataTelefone(contatos.get(position).getNumero_telefone()));
        }else {
            holder.txtCelular1ProspectList.setText("Nenhum telefone válido informado!");
        }

        if(contatos.get(position).getEmail() != null && !contatos.get(position).getEmail().trim().isEmpty()){
            holder.txtEmailProspectLista.setText(contatos.get(position).getEmail());
        }

    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public interface ListaContatoListener{
        void onClick(int position);
    }
}
