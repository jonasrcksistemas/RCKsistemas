package com.example.rcksuporte05.rcksistemas.Helper;

import android.graphics.Bitmap;

/**
 * Created by RCKSUPORTE05 on 26/01/2018.
 */

public class FotoHelper {

    private static Bitmap foto;

    public static Bitmap getFoto() {
        return foto;
    }

    public static void setFoto(Bitmap foto) {
        FotoHelper.foto = foto;
    }
}
