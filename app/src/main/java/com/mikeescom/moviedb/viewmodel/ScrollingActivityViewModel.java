package com.mikeescom.moviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.mikeescom.moviedb.model.Movie;
import com.mikeescom.moviedb.model.MovieDataSourceFactory;
import com.mikeescom.moviedb.service.MovieDataService;
import com.mikeescom.moviedb.service.RetrofitInstance;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ScrollingActivityViewModel extends AndroidViewModel {

    private Executor executor;
    private LiveData<PagedList<Movie>> moviesPagedList;

    public ScrollingActivityViewModel(@NonNull Application application) {
        super(application);

        MovieDataService movieDataService = RetrofitInstance.getService();
        MovieDataSourceFactory factory = new MovieDataSourceFactory(movieDataService);

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();

        executor = Executors.newFixedThreadPool(5);

        moviesPagedList = (new LivePagedListBuilder<Long,Movie>(factory, config))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Movie>> getMoviesPagedList() {
        return moviesPagedList;
    }
}