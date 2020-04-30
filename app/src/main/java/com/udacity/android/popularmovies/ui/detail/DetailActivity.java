package com.udacity.android.popularmovies.ui.detail;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.ActivityDetailBinding;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;
import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

public class DetailActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    public static final String DETAIL_ACTIVITY_INTENT_EXTRA = "DETAIL_ACTIVITY_INTENT_EXTRA";

    private DetailActivityViewModel mViewModel;
    private int mMovieId;

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mToolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(mToolbar);
        initActionBarDrawer(savedInstanceState);
        mMovieId = getIntent().getIntExtra(DETAIL_ACTIVITY_INTENT_EXTRA, -1);

        DetailActivityViewModelFactory factory = MovieInstanceProviderUtil
                .provideDetailActivityViewModelFactory(getApplicationContext(), mMovieId);
        new ViewModelProvider(this, factory).get(DetailActivityViewModel.class);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieDetailFragment.getInstance(mMovieId)).commit();
//            mNavigationView.setCheckedItem(R.id.details);
//        }
    }

    public void updateFavorite(View view) {
        boolean isFavorite = ((ToggleButton) view).isChecked();
        mViewModel.updateFavorite(mMovieId, isFavorite);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.details:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieDetailFragment.getInstance(mMovieId)).addToBackStack("details").commit();
                mNavigationView.setCheckedItem(R.id.details);
                break;

            case R.id.trailers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieListsFragment.getInstance(mMovieId)).addToBackStack("trailers").commit();
                mNavigationView.setCheckedItem(R.id.trailers);
                break;

            case R.id.reviews:
                Toast.makeText(getApplicationContext(),"Not yet implemented",Toast.LENGTH_SHORT).show();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieListsFragment.getInstance(mMovieId)).commit();
                mNavigationView.setCheckedItem(R.id.reviews);
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return false;
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
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
