package com.example.rcksuporte05.rcksistemas.Helper;

import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialCab;
import com.example.rcksuporte05.rcksistemas.model.CampanhaComercialItens;

import java.util.List;

public class CampanhaHelper {
    public static CampanhaComercialCab campanhaComercialCab;
    public static List<CampanhaComercialCab> listaCampanha;
    public static CampanhaComercialItens itemCampanhaDetalhe;

    public static CampanhaComercialCab getCampanhaComercialCab() {
        return campanhaComercialCab;
    }

    public static void setCampanhaComercialCab(CampanhaComercialCab campanhaComercialCab) {
        CampanhaHelper.campanhaComercialCab = campanhaComercialCab;
    }

    public static List<CampanhaComercialCab> getListaCampanha() {
        return listaCampanha;
    }

    public static void setListaCampanha(List<CampanhaComercialCab> listaCampanha) {
        CampanhaHelper.listaCampanha = listaCampanha;
    }

    public static CampanhaComercialItens getItemCampanhaDetalhe() {
        return itemCampanhaDetalhe;
    }

    public static void setItemCampanhaDetalhe(CampanhaComercialItens itemCampanhaDetalhe) {
        CampanhaHelper.itemCampanhaDetalhe = itemCampanhaDetalhe;
    }

    public static void clear() {
        campanhaComercialCab = null;
    }
}
