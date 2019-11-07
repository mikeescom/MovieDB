package com.mikeescom.moviedb.model;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.mikeescom.moviedb.service.MovieDataService;

public class MovieDataSourceFactory extends DataSource.Factory {

    private MovieDataSource movieDataSource;
    private MovieDataService movieDataService;
    private MutableLiveData<MovieDataSource> mutableLiveData;

    public MovieDataSourceFactory(MovieDataService movieDataService) {
        this.movieDataService = movieDataService;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        movieDataSource = new MovieDataSource(movieDataService);
        mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
    }
}
