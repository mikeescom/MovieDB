package com.mikeescom.moviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mikeescom.moviedb.model.Movie;
import com.mikeescom.moviedb.model.MovieResponse;
import com.mikeescom.moviedb.model.MoviesRepository;

import java.util.List;

public class ScrollingActivityViewModel extends AndroidViewModel {
    private MoviesRepository movieRepository;

    public ScrollingActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository=new MoviesRepository(application);
    }

    public LiveData<List<Movie>> getMovies(String query){

        return movieRepository.getMutableLiveData(query);
    }
}