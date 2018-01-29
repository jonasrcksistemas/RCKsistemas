package com.example.rcksuporte05.rcksistemas.classes;

/**
 * Created by RCKSUPORTE05 on 26/01/2018.
 */

public class Foto {
    private String base64;

    public Foto(String base64) {
        this.base64 = base64;
    }

    public Foto() {
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
