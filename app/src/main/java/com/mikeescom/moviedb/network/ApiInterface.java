package com.mikeescom.moviedb.network;

import com.mikeescom.moviedb.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/3/search/movie")
    Call<MovieResponse> getMovieSearch(@Query("api_key") String apiKey,
                                             @Query("query") String query);
}
