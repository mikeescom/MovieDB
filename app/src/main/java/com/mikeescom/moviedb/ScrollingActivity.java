package com.mikeescom.moviedb;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.mikeescom.moviedb.databinding.ActivityScrollingBinding;
import com.mikeescom.moviedb.model.Movie;
import com.mikeescom.moviedb.model.MovieResponse;
import com.mikeescom.moviedb.network.Api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollingActivity extends AppCompatActivity {

    private String TAG = ScrollingActivity.class.getSimpleName();

    private static final String API_KEY = "da6df6e2cc2d20a08d9c8b658f12f5a5";
    private ScrollingActivityClickHandlers scrollingActivityClickHandlers;
    private ActivityScrollingBinding activityScrollingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        scrollingActivityClickHandlers = new ScrollingActivityClickHandlers(this);
        activityScrollingBinding = DataBindingUtil.setContentView(this,R.layout.activity_scrolling);
        activityScrollingBinding.setClickHandler(scrollingActivityClickHandlers);
        setSupportActionBar(activityScrollingBinding.toolbar);
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
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.getStackTrace().toString());
            }
        });
    }
}
