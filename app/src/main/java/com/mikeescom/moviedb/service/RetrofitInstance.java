package com.mikeescom.moviedb.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static String BASE_URL="https://api.themoviedb.org/3/";

    public static ApiInterface getService(){


        if(retrofit==null){

            retrofit=new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit.create(ApiInterface.class);

    }
}
