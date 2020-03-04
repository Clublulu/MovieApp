package com.udacity.android.popularmovies;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.adapter.PopularMoviesAdapter;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.utilities.NetworkUtility;
import com.udacity.android.popularmovies.utilities.PopularMoviesJsonUtility;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final int FETCH_MOVIES_LOADER = 1;

    private RecyclerView mMovies_rv;
    private PopularMoviesAdapter mMoviesAdapter;
    private ProgressBar mLoadingIndicator_pb;
    private TextView mErrorMessage_tv;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovies_rv = findViewById(R.id.movies_rv);
        mLoadingIndicator_pb = findViewById(R.id.pb_loading_indicator);
        mErrorMessage_tv = findViewById(R.id.tv_error_message_display);

        GridLayoutManager manager = new GridLayoutManager(
                                            this,
                                            2,
                                            RecyclerView.VERTICAL,
                                            false);

//        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mMovies_rv.setLayoutManager(manager);
        mMovies_rv.setHasFixedSize(true);

        mMoviesAdapter = new PopularMoviesAdapter();
        mMovies_rv.setAdapter(mMoviesAdapter);

        LoaderManager.getInstance(this).initLoader(FETCH_MOVIES_LOADER, null, this);
    }
    
    private void showMovieData() {
        mErrorMessage_tv.setVisibility(View.INVISIBLE);
        mMovies_rv.setVisibility(View.VISIBLE);
        
    }
    
    private void showErrorMessage() {
        mMovies_rv.setVisibility(View.INVISIBLE);
        mLoadingIndicator_pb.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Movie>>(getApplicationContext()) {

            private List<Movie> movies;

            @Override
            protected void onStartLoading() {
                if(movies != null) {
                    deliverResult(movies);
                } else {
                    forceLoad();
                    mLoadingIndicator_pb.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public List<Movie> loadInBackground() {
                URL popularMoviesURL = NetworkUtility.buildURL();
                try {
                    String jsonMoviesString = NetworkUtility
                                                .getResponseFromHttpRequest(popularMoviesURL);
                    List<Movie> movies = PopularMoviesJsonUtility.getMoviesFromJsonString(
                                                                        getApplicationContext(),
                                                                        jsonMoviesString);
                    return movies;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(List<Movie> data) {
                movies = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        mLoadingIndicator_pb.setVisibility(View.INVISIBLE);
        mMoviesAdapter.setMovies(data);
        if (data == null || data.isEmpty()) {
            showErrorMessage();
        } else {
            showMovieData();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) { }
}
