package com.udacity.android.popularmovies.ui.main;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;
import com.udacity.android.popularmovies.ui.detail.DetailActivity;
import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        MovieOnClickListener,
        AdapterView.OnItemSelectedListener {

    private RecyclerView mMovies_rv;
    private PopularMoviesAdapter mMoviesAdapter;
    private int mPosition = RecyclerView.NO_POSITION;
    private MainActivityViewModel mViewModel;

    private ProgressBar mLoadingIndicator_pb;
    private TextView mErrorMessage_tv;
    private TextView mNoFavorites_tv;

    private static Map<String, String> mSortCriteriaMap;
    private String mSortCriteriaValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator_pb = findViewById(R.id.pb_loading_indicator);
        mNoFavorites_tv = findViewById(R.id.no_favorites_added);
        mErrorMessage_tv = findViewById(R.id.tv_error_message_display);

        createSortCriteriaMap();
        setupRecyclerView();

        MainActivityViewModelFactory factory = MovieInstanceProviderUtil
                .provideMainActivityViewModelFactory(getApplicationContext());
        mViewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);
        mViewModel.getMovies().observe(this, movies -> {
            mMoviesAdapter.swapMovies(movies);
            if (mPosition == RecyclerView.NO_POSITION)
                mPosition = 0;
            mMovies_rv.smoothScrollToPosition(mPosition);
            if(movies != null && movies.size() != 0)
                showMovieData();
            else
                determineLoadScreen();
        });
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

    @Override
    public void onClickItem(Object movie) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL_ACTIVITY_INTENT_EXTRA, ((Movie) movie).movieId);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = (String) parent.getItemAtPosition(position);
        mSortCriteriaValue = mSortCriteriaMap.get(selectedItem);
        mViewModel.getLatestMovies(mSortCriteriaValue);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    private void setupRecyclerView() {
        mMovies_rv = findViewById(R.id.movies_rv);
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

    private void createSortCriteriaMap() {
        mSortCriteriaMap  = new HashMap<>();
        mSortCriteriaMap.put(getString(R.string.popular_label), getString(R.string.popular));
        mSortCriteriaMap.put(getString(R.string.top_rated_label), getString(R.string.top_rated));
        mSortCriteriaMap.put(getString(R.string.favorites_label), getString(R.string.favorites));
    }

    private void showMovieData() {
        mErrorMessage_tv.setVisibility(View.INVISIBLE);
        mLoadingIndicator_pb.setVisibility(View.INVISIBLE);
        mNoFavorites_tv.setVisibility(View.INVISIBLE);
        mMovies_rv.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mMovies_rv.setVisibility(View.INVISIBLE);
        mNoFavorites_tv.setVisibility(View.INVISIBLE);
        mLoadingIndicator_pb.setVisibility(View.VISIBLE);
    }

    private void showNoFavoritesAdded() {
        mLoadingIndicator_pb.setVisibility(View.INVISIBLE);
        mMovies_rv.setVisibility(View.INVISIBLE);
        mNoFavorites_tv.setVisibility(View.VISIBLE);
    }

    // determines whether to show a progress bar while waiting for Popular / Top Rated data to load
    // OR, for Favorites, a message that no favorites have been added yet.
    private void determineLoadScreen() {
        if (mSortCriteriaValue.equals(getString(R.string.favorites))) {
            showNoFavoritesAdded();
        } else {
            showLoading();
        }
    }
}
