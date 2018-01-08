package com.example.rcksuporte05.rcksistemas.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by RCKSUPORTE05 on 08/01/2018.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain)
            throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("Origin", "mobilerck")
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}