package com.udacity.android.popularmovies.ui.adapter;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.ui.MovieClickListener;

/**
 * Factory to generate a specific Movie, Trailer, or Review Adapter based on the action the user selects in the navigation view.
 *
 */
public class ListTypeAdapterFactory {

    private static final String LIST_TYPE_ADAPTER_MSG = "The ListTypeAdapter with resourceId: ";
    private static final String NOT_FOUND = " is not found.";

    public static BaseListTypeAdapter create(int layoutResId, MovieClickListener clickListener) {
        switch (layoutResId) {
            case R.string.app_adapter_movies:
                return new MoviesAdapter(clickListener);
            case R.string.app_adapter_trailers:
                return new TrailersAdapter(clickListener);
            case R.string.app_adapter_reviews:
                return new ReviewsAdapter();
            default:
                throw new RuntimeException(LIST_TYPE_ADAPTER_MSG + layoutResId + NOT_FOUND);
        }
    }

}
