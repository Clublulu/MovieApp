package com.udacity.android.popularmovies.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.udacity.android.popularmovies.ui.detail.DetailActivity;
import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        PopularMoviesAdapter.OnClickMovieListener,
        AdapterView.OnItemSelectedListener {

    private RecyclerView mMovies_rv;
    private PopularMoviesAdapter mMoviesAdapter;
    private int mPosition = RecyclerView.NO_POSITION;
    private MainActivityViewModel mViewModel;


    private ProgressBar mLoadingIndicator_pb;
    private TextView mErrorMessage_tv;

    private static Map<String, String> mSortCriteriaMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSortCriteriaMap = createSortCriteriaMap();
        mLoadingIndicator_pb = findViewById(R.id.pb_loading_indicator);
        mErrorMessage_tv = findViewById(R.id.tv_error_message_display);
        setupRecyclerView();

        String prefSortCriteria = getPrefSortCriteria();
        MainActivityViewModelFactory factory = MovieInstanceProviderUtil
                .provideMainActivityViewModelFactory(getApplicationContext(), prefSortCriteria);
        mViewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);
        mViewModel.getMovies().observe(this, movies -> {
            mMoviesAdapter.swapMovies(movies);
            if (mPosition == RecyclerView.NO_POSITION)
                mPosition = 0;
            mMovies_rv.smoothScrollToPosition(mPosition);
            if(movies != null && movies.size() != 0)
                showMovieData();
            else
                showLoading();
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
    
    private void showMovieData() {
        mErrorMessage_tv.setVisibility(View.INVISIBLE);
        mLoadingIndicator_pb.setVisibility(View.INVISIBLE);
        mMovies_rv.setVisibility(View.VISIBLE);
    }
    
    private void showLoading() {
        mMovies_rv.setVisibility(View.INVISIBLE);
        mLoadingIndicator_pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickItem(Movie movie) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL_ACTIVITY_INTENT_EXTRA, movie.movieId);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = (String) parent.getItemAtPosition(position);
        SharedPreferences.Editor sharedPrefEditor = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext()).edit();
        String sortCriteriaValue = mSortCriteriaMap.get(selectedItem);
        sharedPrefEditor.putString(getString(R.string.sort_criteria_key), sortCriteriaValue);
        mViewModel.getLatestMovies(sortCriteriaValue);
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

    private String getPrefSortCriteria() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String sortCriteriaKey = getString(R.string.sort_criteria_key);
        String defaultPopular = getString(R.string.popular);
        return sharedPreferences.getString(sortCriteriaKey, defaultPopular);
    }

    private Map<String, String> createSortCriteriaMap() {
        Map<String, String> map = new HashMap<>();
        map.put(getString(R.string.popular_label), getString(R.string.popular));
        map.put(getString(R.string.top_rated_label), getString(R.string.top_rated));

        return map;
    }

}
