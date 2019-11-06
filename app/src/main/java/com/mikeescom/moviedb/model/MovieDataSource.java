package com.mikeescom.moviedb.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.mikeescom.moviedb.BuildConfig;
import com.mikeescom.moviedb.service.MovieDataService;
import com.mikeescom.moviedb.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Movie> {

    private MovieDataService movieDataService;
    private Application application;

    public MovieDataSource(MovieDataService movieDataService, Application application) {
        this.movieDataService = movieDataService;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Movie> callback) {
        movieDataService = RetrofitInstance.getService();
        Call<MovieResponse> call = movieDataService.getPopularMovieshWithPaging(BuildConfig.API_KEY, 1);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieDBResponse=response.body();
                ArrayList<Movie> movies = new ArrayList<>();

                if(movieDBResponse != null && movieDBResponse.getResults() != null) {
                    movies = (ArrayList<Movie>) movieDBResponse.getResults();
                    callback.onResult(movies, null, (long) 2);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Movie> callback) {
        movieDataService = RetrofitInstance.getService();
        Call<MovieResponse> call = movieDataService.getPopularMovieshWithPaging(BuildConfig.API_KEY,  params.key);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                MovieResponse movieDBResponse=response.body();
                ArrayList<Movie> movies = new ArrayList<>();

                if(movieDBResponse != null && movieDBResponse.getResults() != null) {
                    movies = (ArrayList<Movie>) movieDBResponse.getResults();
                    callback.onResult(movies, params.key + 1);

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
