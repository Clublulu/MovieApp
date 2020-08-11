package com.udacity.android.popularmovies.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.udacity.android.popularmovies.R;

/**
 * Factory class for creating different types of Fragments.
 * Available fragments are:
 *      - MovieListFragment
 *      - ReviewListFragment
 *      - MovieDetailFragment
 *
 */
public final class MovieFragmentFactory {

    public static final String EXTRA_SORT_CRITERIA = "EXTRA_SORT_CRITERIA";
    public static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";

    /**
     * Retrieves either a ReviewListFragment or MovieDetailFragment instance used in the DetailActivity.
     *
     * @param resId
     * @param movieId
     * @return ReviewListFragment or MovieDetailFragment
     */
    public static Fragment getInstance(int resId, int movieId) {
        Fragment fragment;
        switch (resId) {
            case R.string.app_movie_reviews_fragment:
                fragment = new ReviewListFragment();
                break;
            case R.string.app_movie_details_fragment:
                fragment = new MovieDetailFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected fragment: " + resId);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_MOVIE_ID, movieId);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Retrieves a MoveListFragment  based on a given sort criteria.
     *
     * @param sortCriteria the type of sort criteria passed into the bundle
     * @return A MovieListFragment containing a particular sort criteria.
     */
    public static Fragment getInstance(String sortCriteria) {
        Fragment fragment = new MovieListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SORT_CRITERIA, sortCriteria);

        fragment.setArguments(bundle);

        return fragment;
    }
}