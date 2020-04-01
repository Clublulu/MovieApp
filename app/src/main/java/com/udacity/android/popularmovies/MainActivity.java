package com.udacity.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.adapter.PopularMoviesAdapter;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.utilities.PopularMoviesJsonUtility;
import com.udacity.android.popularmovies.utilities.VolleyRequestListener;
import com.udacity.android.popularmovies.utilities.VolleyUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>,
        PopularMoviesAdapter.OnClickMovieListener,
        AdapterView.OnItemSelectedListener {

    private static final int FETCH_MOVIES_LOADER = 1;
    private static final String SELECTED_SPINNER_ITEM_KEY = "SPINNER_ITEM_KEY";

    private RecyclerView mMovies_rv;
    private PopularMoviesAdapter mMoviesAdapter;
    private ProgressBar mLoadingIndicator_pb;
    private TextView mErrorMessage_tv;

    private Map<String, String> sortCriteriaMap = new HashMap<String, String>() {{
        put("Most Popular", "popular");
        put("Top Rated", "top_rated");
    }};

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
        mMovies_rv.setLayoutManager(manager);
        mMovies_rv.setHasFixedSize(true);

        mMoviesAdapter = new PopularMoviesAdapter(this);
        mMovies_rv.setAdapter(mMoviesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem spinnerItem = menu.findItem(R.id.menu_spinner);
        Spinner spinner  = (Spinner) spinnerItem.getActionView();

        ArrayAdapter<CharSequence> spinnerAdapter =
                ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.movie_preferences,
                        R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        return true;
    }
    
    private void showMovieData() {
        mErrorMessage_tv.setVisibility(View.INVISIBLE);
        mMovies_rv.setVisibility(View.VISIBLE);
        
    }
    
    private void showErrorMessage() {
        mMovies_rv.setVisibility(View.INVISIBLE);
        mLoadingIndicator_pb.setVisibility(View.INVISIBLE);
        mErrorMessage_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, final Bundle args) {
        return new Loader<List<Movie>>(getApplicationContext()) {

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
            protected void onForceLoad() {
                super.onForceLoad();
                String sortCriteria = args.getString(SELECTED_SPINNER_ITEM_KEY);
                VolleyUtils.fetchMovieData(getApplicationContext(), sortCriteria,
                        new VolleyRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                List<Movie> movies = PopularMoviesJsonUtility.getMoviesFromJsonString(
                                        getApplicationContext(),
                                        response);
                                deliverResult(movies);
                            }

                            @Override
                            public void onError(String error) {
                                showErrorMessage();
                            }
                        });
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

    @Override
    public void onClickItem(Movie movie) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL_ACTIVITY_INTENT_KEY, movie);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mMoviesAdapter.setMovies(null);
        String selectedItem = (String) parent.getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putString(SELECTED_SPINNER_ITEM_KEY, sortCriteriaMap.get(selectedItem));
        LoaderManager.getInstance(this).restartLoader(FETCH_MOVIES_LOADER, bundle, this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
