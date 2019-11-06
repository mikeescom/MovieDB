package com.mikeescom.moviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.mikeescom.moviedb.model.Movie;
import com.mikeescom.moviedb.model.MovieDataSource;
import com.mikeescom.moviedb.model.MovieDataSourceFactory;
import com.mikeescom.moviedb.model.MoviesRepository;
import com.mikeescom.moviedb.service.MovieDataService;
import com.mikeescom.moviedb.service.RetrofitInstance;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ScrollingActivityViewModel extends AndroidViewModel {
    private MoviesRepository movieRepository;

    private Executor executor;
    private LiveData<MovieDataSource> movieDataSourceLiveData;
    private LiveData<PagedList<Movie>> moviesPagedList;

    public ScrollingActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MoviesRepository(application);

        MovieDataService movieDataService = RetrofitInstance.getService();
        MovieDataSourceFactory factory = new MovieDataSourceFactory(movieDataService,application);
       // movieDataSourceLiveData = factory.getMutableLiveData();

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

    public LiveData<List<Movie>> getMovies(String query){
        return movieRepository.getMutableLiveData(query);
    }

    public LiveData<PagedList<Movie>> getMoviesPagedList() {
        return moviesPagedList;
    }
}