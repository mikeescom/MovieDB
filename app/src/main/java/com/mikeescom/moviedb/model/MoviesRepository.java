package com.mikeescom.moviedb.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.mikeescom.moviedb.BuildConfig;
import com.mikeescom.moviedb.service.MovieDataService;
import com.mikeescom.moviedb.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {
    private ArrayList<Movie> movies=new ArrayList<>();
    private MutableLiveData<List<Movie>> mutableLiveData=new MutableLiveData<>();
    private Application application;

    public MoviesRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Movie>> getMutableLiveData(String query) {

        MovieDataService movieDataService = RetrofitInstance.getService();

        Call<MovieResponse> call = movieDataService.getMovieSearch(BuildConfig.API_KEY, query);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();


                if (movieResponse != null && movieResponse.getResults() != null) {

                    movies = (ArrayList<Movie>) movieResponse.getResults();
                    mutableLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });


        return mutableLiveData;
    }
}
