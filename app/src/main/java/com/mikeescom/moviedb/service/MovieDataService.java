package com.mikeescom.moviedb.service;

import com.mikeescom.moviedb.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {
    @GET("/3/search/movie")
    Call<MovieResponse> getMovieSearch(@Query("api_key") String apiKey
            , @Query("query") String query);

    @GET("/3/search/movie")
    Call<MovieResponse> getMovieSearchWithPaging(@Query("api_key") String apiKey
            , @Query("query") String query
            , @Query("page") long page);

    @GET("/3/movie/popular")
    Call<MovieResponse> getPopularMovieshWithPaging(@Query("api_key") String apiKey
            , @Query("page") long page);
}
