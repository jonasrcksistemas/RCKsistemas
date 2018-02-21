package com.example.rcksuporte05.rcksistemas.Helper;

import com.example.rcksuporte05.rcksistemas.classes.Prospect;
import com.example.rcksuporte05.rcksistemas.classes.VisitaProspect;

/**
 * Created by RCK 03 on 20/02/2018.
 */

public class VisitaHelper {
    private static Prospect prospect;
    private static VisitaProspect visitaProspect;

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

    public void limpaVisitaHelper(){
        prospect = null;
        visitaProspect = null;
    }
}
