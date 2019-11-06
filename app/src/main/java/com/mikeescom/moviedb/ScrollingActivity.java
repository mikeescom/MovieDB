package com.mikeescom.moviedb;

import android.content.Context;
import android.os.Bundle;
import com.mikeescom.moviedb.databinding.ActivityScrollingBinding;
import com.mikeescom.moviedb.model.Movie;
import com.mikeescom.moviedb.model.MovieResponse;
import com.mikeescom.moviedb.network.Api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollingActivity extends AppCompatActivity {

    private String TAG = ScrollingActivity.class.getSimpleName();

    private static final String API_KEY = BuildConfig.API_KEY;
    private ScrollingActivityClickHandlers scrollingActivityClickHandlers;
    private ActivityScrollingBinding activityScrollingBinding;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private ArrayList<Movie> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        scrollingActivityClickHandlers = new ScrollingActivityClickHandlers(this);
        activityScrollingBinding = DataBindingUtil.setContentView(this,R.layout.activity_scrolling);
        activityScrollingBinding.setClickHandler(scrollingActivityClickHandlers);
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

    private void loadRecyclerView(List<Movie> moviesList) {
        recyclerView = activityScrollingBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        moviesAdapter = new MoviesAdapter();
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.setMovies(moviesList);
        moviesAdapter.setListener(new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {

            }
        });
        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Movie bookToDelete = moviesList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteBook(bookToDelete);
            }
        }).attachToRecyclerView(recyclerView);*/
    }

    public class ScrollingActivityClickHandlers {
        Context context;

        public ScrollingActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onSubmitButtonClicked(View view){
            search(activityScrollingBinding.search.getText().toString());
        }
    }

    private void search(String query) {

        Call<MovieResponse> call = Api.getClient().getMovieSearch(API_KEY, query);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movies = response.body();
                loadRecyclerView(movies.getResults());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.getStackTrace().toString());
            }
        });
    }
}
