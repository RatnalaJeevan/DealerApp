package com.wisedrive.dealerapp1.services1.services;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        client = builder.build();

        retrofit = new Retrofit.Builder()
        .baseUrl("http://164.52.217.96:30025/")
        //test
        //live
        //.baseUrl("https://dealerappapis.wisedrive.in/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//        retrofit = new Retrofit.Builder()
//                .baseUrl("http://164.52.217.96:30025/")
//                //test
//                //live
//                //.baseUrl("https://dealerappapis.wisedrive.in/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();

        return retrofit;
    }
}
