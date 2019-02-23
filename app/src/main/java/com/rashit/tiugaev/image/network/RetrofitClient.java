package com.rashit.tiugaev.image.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final String API_BASE_URL = "https://pixabay.com/api/";

    public Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
