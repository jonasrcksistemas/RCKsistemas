package com.example.rcksuporte05.rcksistemas.util.classesGeocoderUtil;

import java.util.List;

/**
 * Created by RCK 03 on 22/02/2018.
 */

public class RespostaGeocoder {
    private List<Result> results;
    private String status;

    public List<Result> getResult() {
        return results;
    }

    public void setResult(List<Result> result) {
        this.results = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
