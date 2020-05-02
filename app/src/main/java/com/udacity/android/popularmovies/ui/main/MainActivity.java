package com.udacity.android.popularmovies.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;
import com.udacity.android.popularmovies.ui.adapter.BaseMovieListTypeAdapter;
import com.udacity.android.popularmovies.ui.adapter.MovieListTypeAdapterFactory;
import com.udacity.android.popularmovies.ui.detail.DetailActivity;
import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

public class MainActivity extends AppCompatActivity implements
        MovieOnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private MainActivityViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private ProgressBar mLoadingIndicator_pb;
    private TextView mErrorMessage_tv;
    private TextView mNoFavorites_tv;
    private int mPosition = RecyclerView.NO_POSITION;
    private String mSortCriteriaValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar_main);
        mLoadingIndicator_pb = findViewById(R.id.pb_loading_indicator);
        mNoFavorites_tv = findViewById(R.id.no_favorites_added);
        mErrorMessage_tv = findViewById(R.id.tv_error_message_display);
        setSupportActionBar(mToolbar);
        initActionBarDrawer(savedInstanceState);

        BaseMovieListTypeAdapter adapter = MovieListTypeAdapterFactory.create(R.id.movies, this);
        buildRecyclerView(adapter);

        MainActivityViewModelFactory factory = MovieInstanceProviderUtil
                .provideMainActivityViewModelFactory(getApplicationContext());
        mViewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);
        mViewModel.getMovies().observe(this, movies -> {
            adapter.swapData(movies);
            if (mPosition == RecyclerView.NO_POSITION)
                mPosition = 0;
            mRecyclerView.smoothScrollToPosition(mPosition);
            if(movies != null && movies.size() != 0)
                showMovieData();
            else
                determineLoadScreen();
        });

        // load initial screen with popular movies
        mSortCriteriaValue = getString(R.string.popular);
        mViewModel.getLatestMovies(getString(R.string.popular));
        mNavigationView.setCheckedItem(R.id.popular);
    }

    @Override
    public void onClickItem(Object movie) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL_ACTIVITY_INTENT_EXTRA, ((Movie) movie).movieId);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.popular:
                mSortCriteriaValue = getString(R.string.popular);
                mNavigationView.setCheckedItem(R.id.popular);
                break;

            case R.id.top_rated:
                mSortCriteriaValue = getString(R.string.top_rated);
                mNavigationView.setCheckedItem(R.id.top_rated);
                break;

            case R.id.favorites:
                mSortCriteriaValue = getString(R.string.favorites);
                mNavigationView.setCheckedItem(R.id.favorites);
                break;
        }
        mViewModel.getLatestMovies(mSortCriteriaValue);
        mDrawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        // Close the drawer if it's still open when the back button is pressed, otherwise default back press
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initActionBarDrawer(Bundle savedInstanceState) {
        mDrawer = findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void buildRecyclerView(RecyclerView.Adapter adapter) {
        mRecyclerView = findViewById(R.id.movies);
        GridLayoutManager manager = new GridLayoutManager(
                this,
                2,
                RecyclerView.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    private void showMovieData() {
        mErrorMessage_tv.setVisibility(View.INVISIBLE);
        mLoadingIndicator_pb.setVisibility(View.INVISIBLE);
        mNoFavorites_tv.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mNoFavorites_tv.setVisibility(View.INVISIBLE);
        mLoadingIndicator_pb.setVisibility(View.VISIBLE);
    }

    private void showNoFavoritesAdded() {
        mLoadingIndicator_pb.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
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
