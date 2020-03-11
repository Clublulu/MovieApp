package com.udacity.android.popularmovies;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.utilities.DateUtility;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_ACTIVITY_INTENT_KEY = "detail_activity_intent_key";

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

        Movie movie = getIntent().getParcelableExtra(DETAIL_ACTIVITY_INTENT_KEY);

        mTitle_tv.setText(movie.getTitle());
        mDescription_tv.setText(movie.getDescription());
        mReleaseDate_tv.setText(DateUtility.formatReleaseDate(movie.getReleaseDate()));
        mAverageRating_tv.setText(movie.getAverageRating() + " " + getString(R.string.average_rating_ten_label));
        Picasso.get().load(movie.getImage()).into(mImage_iv);
    }




}
