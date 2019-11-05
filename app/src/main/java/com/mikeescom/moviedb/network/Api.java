package com.mikeescom.moviedb.network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public static ApiInterface getClient() {

        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = adapter.create(ApiInterface.class);
        return api;
    }
}
