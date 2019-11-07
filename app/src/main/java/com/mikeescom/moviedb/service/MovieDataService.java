package com.mikeescom.moviedb.service;

import com.mikeescom.moviedb.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {
    @GET("/3/movie/popular")
    Call<MovieResponse> getPopularMovieshWithPaging(@Query("api_key") String apiKey
            , @Query("page") long page);
}
