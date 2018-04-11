package com.example.rcksuporte05.rcksistemas.Helper;

import android.location.Location;

import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class VisitaHelper {
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

    public static void limpaVisitaHelper(){
        prospect = null;
        visitaProspect = null;
        mLocation = null;
    }
}
