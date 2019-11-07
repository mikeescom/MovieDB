package com.mikeescom.moviedb;

import android.content.Context;
import android.os.Bundle;
import com.mikeescom.moviedb.databinding.ActivityScrollingBinding;
import com.mikeescom.moviedb.model.Movie;
import com.mikeescom.moviedb.viewmodel.ScrollingActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    private String TAG = ScrollingActivity.class.getSimpleName();

    private ActivityScrollingBinding activityScrollingBinding;
    private ScrollingActivityViewModel scrollingActivityViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private PagedList<Movie> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        activityScrollingBinding = DataBindingUtil.setContentView(this,R.layout.activity_scrolling);
        scrollingActivityViewModel = ViewModelProviders.of(this).get(ScrollingActivityViewModel.class);
        getPopularMovies();
        swipeRefreshLayout = activityScrollingBinding.swipeLayout;
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovies();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getPopularMovies() {
        scrollingActivityViewModel.getMoviesPagedList().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> moviesFromLiveData) {
                moviesList = moviesFromLiveData;
                loadRecyclerView(moviesList);
            }
        });
    }

    private void loadRecyclerView(final List<Movie> moviesList) {
        recyclerView = activityScrollingBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        moviesAdapter = new MoviesAdapter();
        moviesAdapter.setMovies(moviesList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.setListener(new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                Toast.makeText(ScrollingActivity.this, movie.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
        moviesAdapter.notifyDataSetChanged();
    }
}
