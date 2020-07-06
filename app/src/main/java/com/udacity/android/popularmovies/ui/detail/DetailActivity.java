package com.udacity.android.popularmovies.ui.detail;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.ui.TabLayoutClickListener;
import com.udacity.android.popularmovies.ui.fragment.MoviesViewPager;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_ACTIVITY_INTENT_EXTRA = "DETAIL_ACTIVITY_INTENT_EXTRA";
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    private int mMovieId;
    private static ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mMovieId = getIntent().getIntExtra(DETAIL_ACTIVITY_INTENT_EXTRA, -1);

//        mFavoritesButton = findViewById(R.id.button_favorite);


        configureViewPager();
        configureTabLayout();
        configureAppBar();






//
//        DetailActivityViewModelFactory factory = ObjectProviderUtil
//                .provideDetailActivityViewModelFactory(getApplicationContext(), mMovieId);
//        mViewModel = new ViewModelProvider(this, factory).get(DetailActivityViewModel.class);
//
//        initiateDetailFragment();
//        mNavigationView.setCheckedItem(R.id.details);
    }

    private void configureViewPager() {
        mViewPager = findViewById(R.id.tab_viewpager);
        MoviesViewPager moviesViewPager = MoviesViewPager.getInstance(getSupportFragmentManager());
        moviesViewPager.addFragment(mMovieId, getString(R.string.details_label));
        moviesViewPager.addFragment(R.string.app_movie_trailers_fragment, mMovieId, getString(R.string.trailers));
        moviesViewPager.addFragment(R.string.app_movie_reviews_fragment, mMovieId, getString(R.string.reviews));
        mViewPager.setAdapter(moviesViewPager.getAdapter());
    }

    private void configureTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayoutClickListener(mViewPager));
    }



    private void configureAppBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle(getIntent().getExtras().getString(EXTRA_RECIPE_NAME));
//        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        ImageView imageView = findViewById(R.id.app_bar_image);
//        String imageTransitionName = getIntent().getExtras().getString(EXTRA_RECIPE_IMAGE_TRANSITION_NAME);
//        imageView.setTransitionName(imageTransitionName);
//        int imageResId = getIntent().getExtras().getInt(DetailActivity.EXTRA_RECIPE_IMAGE);
//        Picasso.get()
//                .load(imageResId)
//                .noFade()
//                .into(imageView);
    }




    public void updateFavorite(View view) {
        boolean isFavorite = ((ToggleButton) view).isChecked();
//        mViewModel.updateFavorite(mMovieId, isFavorite);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // sadly, I couldn't figure out how to get the checked state of the toggle button updated via DataBinding,
        // so I pull a direct reference of the button and set the checked state on it.
//        mViewModel.getMovie().observe(this, movie -> {
//            mFavoritesButton.setChecked(movie.isFavorite);
//            getSupportActionBar().setTitle(movie.title);
//        });

        return true;
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.details:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieDetailFragment.getInstance(mMovieId)).commit();
//                mNavigationView.setCheckedItem(R.id.details);
//                break;
//
//            case R.id.trailers:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieListsFragment.getInstance(mMovieId, R.id.trailers)).addToBackStack(null).commit();
//                mNavigationView.setCheckedItem(R.id.trailers);
//                break;
//
//            case R.id.reviews:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MovieListsFragment.getInstance(mMovieId, R.id.reviews)).addToBackStack(null).commit();
//                mNavigationView.setCheckedItem(R.id.reviews);
//                break;
//        }
//
//        mDrawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private void initiateDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, MovieDetailFragment.getInstance(mMovieId))
//                .addToBackStack(BACK_STACK_ROOT_TAG)
//                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
