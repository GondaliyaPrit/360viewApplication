package com.cameraview.app.Interfaces;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface ApiClient {

     OkHttpClient.Builder httpClient = new OkHttpClient.Builder() .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.remove.bg/v1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Api API = retrofit.create(Api.class);


    Retrofit localretrofit = new Retrofit.Builder()
            .baseUrl("https://webdevelopment33.com/360view/wp-json/wp/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();
    Api LOCALAPI = localretrofit.create(Api.class);

    Retrofit demoretrofit = new Retrofit.Builder()
            .baseUrl("https://www.mercado24.com/wp-json/wp/v1/users/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();
    Api DEMOAPI = demoretrofit.create(Api.class);



}
