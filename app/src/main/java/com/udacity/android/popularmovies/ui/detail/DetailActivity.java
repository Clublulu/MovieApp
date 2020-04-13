package com.udacity.android.popularmovies.ui.detail;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.utilities.DateUtil;
import com.udacity.android.popularmovies.utilities.ViewModelInjectorUtil;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_ACTIVITY_INTENT_EXTRA = "DETAIL_ACTIVITY_INTENT_EXTRA";

    private TextView mTitle_tv;
    private TextView mDescription_tv;
    private ImageView mImage_iv;
    private TextView mReleaseDate_tv;
    private TextView mAverageRating_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitle_tv = findViewById(R.id.movie_title);
        mDescription_tv = findViewById(R.id.movie_description);
        mImage_iv = findViewById(R.id.detail_movie_image);
        mReleaseDate_tv = findViewById(R.id.release_date_tv);
        mAverageRating_tv = findViewById(R.id.average_rating_tv);

        int movieId = getIntent().getIntExtra(DETAIL_ACTIVITY_INTENT_EXTRA, -1);

        DetailActivityViewModelFactory factory = ViewModelInjectorUtil
                .provideDetailActivityViewModelFactory(getApplicationContext(), movieId);
        DetailActivityViewModel viewModel = new ViewModelProvider(this, factory)
                .get(DetailActivityViewModel.class);
        viewModel.getMovie().observe(this, movie -> {
            mTitle_tv.setText(movie.title);
            mDescription_tv.setText(movie.description);
            mReleaseDate_tv.setText(DateUtil.formatReleaseDate(movie.releaseDate));
            mAverageRating_tv.setText(movie.averageRating + " " + getString(R.string.average_rating_ten_label));
            Picasso.get().load(movie.image).into(mImage_iv);
        });
    }
}
