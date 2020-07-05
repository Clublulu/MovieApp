package com.udacity.android.popularmovies.ui.fragment;


import android.os.Bundle;

/**
 * A Master list Fragment handling phone based configurations.
 */
public class MovieListFragment extends BaseMainListFragment {

    public static final String EXTRA_SORT_CRITERIA = "EXTRA_SORT_CRITERIA";

    public static BaseListFragment getInstance(String sortCriteria) {
        BaseListFragment fragment = new MovieListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SORT_CRITERIA, sortCriteria);

        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    String getSortCriteria() {
        return getArguments().getString(EXTRA_SORT_CRITERIA);
    }
}
