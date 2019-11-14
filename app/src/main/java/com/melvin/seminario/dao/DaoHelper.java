package com.melvin.seminario.dao;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class DaoHelper {
    protected Retrofit retrofit;
        //public static final String BASE_URL = "https://stormy-wildwood-43671.herokuapp.com";
//    public static final String BASE_URL = "http://192.168.0.182:8080";
    public static final String BASE_URL = "http://localhost:8080";

    public DaoHelper() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.client(httpClient.build()).build();
    }
}