package com.udacity.android.popularmovies.ui.adapter;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;

/**
 * Factory to generate a specific Movie, Trailer, or Review Adapter based on the action the user selects in the navigation view.
 *
 */
public class MovieListTypeAdapterFactory {

    public static BaseMovieListTypeAdapter create(int layoutResId, MovieOnClickListener clickListener) {
        switch (layoutResId) {
            case R.id.movies:
                return new MovieIconsAdapter(clickListener);
            case R.id.trailers:
                return new MovieTrailersAdapter(clickListener);
            case R.id.reviews:
                return new MovieReviewsAdapter();
            default:
                return null;
        }
    }

}
