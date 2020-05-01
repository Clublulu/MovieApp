package com.udacity.android.popularmovies.ui.detail;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;

public class MoveListTypeAdapterFactory {


    public static BaseMovieListsAdapter create(int layoutResId, MovieOnClickListener clickListener) {
        switch (layoutResId) {
            case R.id.trailers:
                return new MovieTrailersAdapter(clickListener);
            case R.id.reviews:
                return new MovieReviewsAdapter();
        }

        return null;
    }

}
