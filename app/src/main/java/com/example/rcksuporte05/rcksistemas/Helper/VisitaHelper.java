package com.example.rcksuporte05.rcksistemas.Helper;

import android.graphics.Bitmap;
import android.location.Location;

import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class VisitaHelper {
    public static Bitmap imagem1;
    public static Bitmap imagem2;
    private static Prospect prospect;
    private static VisitaProspect visitaProspect;
    private static Location mLocation;

    public static Prospect getProspect() {
        return prospect;
    }

    public static void setProspect(Prospect prospect) {
        VisitaHelper.prospect = prospect;
    }

    public static VisitaProspect getVisitaProspect() {
        return visitaProspect;
    }

    public static void setVisitaProspect(VisitaProspect visitaProspect) {
        VisitaHelper.visitaProspect = visitaProspect;
    }

    public static Location getmLocation() {
        return mLocation;
    }

    public static void setmLocation(Location mLocation) {
        VisitaHelper.mLocation = mLocation;
    }

    public static Bitmap getImagem1() {
        return imagem1;
    }

    public static void setImagem1(Bitmap imagem1) {
        VisitaHelper.imagem1 = imagem1;
    }

    public static Bitmap getImagem2() {
        return imagem2;
    }

    public static void setImagem2(Bitmap imagem2) {
        VisitaHelper.imagem2 = imagem2;
    }

    public static void limpaVisitaHelper() {
        visitaProspect = null;
        mLocation = null;
        imagem1 = null;
        imagem2 = null;
    }
}
