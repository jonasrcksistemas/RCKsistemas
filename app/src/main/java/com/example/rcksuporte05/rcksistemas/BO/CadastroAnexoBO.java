package com.example.rcksuporte05.rcksistemas.BO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;

import java.util.List;

public class CadastroAnexoBO {

    public List<CadastroAnexo> listaCadastroAnexoComMiniatura(Context context, int idCadastro) {
        DBHelper db = new DBHelper(context);
        CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);

        List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoDAO.listaCadastroAnexo(idCadastro);

        for (CadastroAnexo cadastroAnexo : listaCadastroAnexo) {
            byte[] data = Base64.decode(cadastroAnexo.getAnexo(), Base64.NO_WRAP);
            Bitmap miniatura = BitmapFactory.decodeByteArray(data, 0, data.length);
            miniatura = Bitmap.createScaledBitmap(miniatura, 220, 230, false);
            cadastroAnexo.setMiniatura(miniatura);
        }
        return listaCadastroAnexo;
    }
}
