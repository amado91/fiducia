package com.bancomext.fiducia.retrofit;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAutInterceptor implements Interceptor {

    private String credential;

    public BasicAutInterceptor(String user,String password) {
        this.credential = Credentials.basic(user,password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request aut = request.newBuilder().header("Authorization",credential).build();
        return chain.proceed(aut);
    }
}