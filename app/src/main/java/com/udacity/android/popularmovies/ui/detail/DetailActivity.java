package com.udacity.android.popularmovies.ui.detail;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

public class DetailActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    public static final String DETAIL_ACTIVITY_INTENT_EXTRA = "DETAIL_ACTIVITY_INTENT_EXTRA";
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    private DetailActivityViewModel mViewModel;
    private int mMovieId;

    private ToggleButton mFavoritesButton;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mToolbar = findViewById(R.id.toolbar_detail);
        mFavoritesButton = findViewById(R.id.button_favorite);
        setSupportActionBar(mToolbar);
        initActionBarDrawer(savedInstanceState);
        mMovieId = getIntent().getIntExtra(DETAIL_ACTIVITY_INTENT_EXTRA, -1);

        DetailActivityViewModelFactory factory = MovieInstanceProviderUtil
                .provideDetailActivityViewModelFactory(getApplicationContext(), mMovieId);
        mViewModel = new ViewModelProvider(this, factory).get(DetailActivityViewModel.class);

        initiateDetailFragment();
        mNavigationView.setCheckedItem(R.id.details);
    }

    public void updateFavorite(View view) {
        boolean isFavorite = ((ToggleButton) view).isChecked();
        mViewModel.updateFavorite(mMovieId, isFavorite);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // sadly, I couldn't figure out how to get the checked state of the toggle button updated via DataBinding,
        // so I pull a direct reference of the button and set the checked state on it.
        mViewModel.getMovie().observe(this, movie -> {
            mFavoritesButton.setChecked(movie.isFavorite);
            getSupportActionBar().setTitle(movie.title);
        });

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.details:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieDetailFragment.getInstance(mMovieId)).commit();
                mNavigationView.setCheckedItem(R.id.details);
                break;

            case R.id.trailers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieListsFragment.getInstance(mMovieId, R.id.trailers)).addToBackStack(null).commit();
                mNavigationView.setCheckedItem(R.id.trailers);
                break;

            case R.id.reviews:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieListsFragment.getInstance(mMovieId, R.id.reviews)).addToBackStack(null).commit();
                mNavigationView.setCheckedItem(R.id.reviews);
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initiateDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, MovieDetailFragment.getInstance(mMovieId))
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit();
    }


    private void initActionBarDrawer(Bundle savedInstanceState) {
        mDrawer = findViewById(R.id.drawer_layout_detail);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();


        mNavigationView = findViewById(R.id.nav_view_detail);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        FragmentManager manager =  getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 1) {
            manager.popBackStackImmediate(BACK_STACK_ROOT_TAG, 0);
            mNavigationView.setCheckedItem(R.id.details);
        } else {
            super.onBackPressed();
            finish();
        }
    }
}
