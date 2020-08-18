package com.udacity.android.popularmovies.ui.detail;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.ui.fragment.MoviesViewPager;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
    public static final String EXTRA_MOVIE_IMAGE = "EXTRA_MOVIE_IMAGE";

    private int mMovieId;
    private static ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mMovieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);
        String movieImage = getIntent().getStringExtra(EXTRA_MOVIE_IMAGE);

        ImageView movie_cover = findViewById(R.id.movie_detail_cover);
        Picasso.get().load(movieImage).into(movie_cover);

        configureViewPager();
    }

    private void configureViewPager() {
        mViewPager = findViewById(R.id.tab_viewpager);
        MoviesViewPager moviesViewPager = MoviesViewPager.getInstance(getSupportFragmentManager());
        moviesViewPager.addFragment(R.string.app_movie_details_fragment, mMovieId, getString(R.string.label_details));
        moviesViewPager.addFragment(R.string.app_movie_reviews_fragment, mMovieId, getString(R.string.reviews));
        mViewPager.setAdapter(moviesViewPager.getAdapter());
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
