package com.udacity.android.popularmovies.ui.fragment;


import android.os.Bundle;

/**
 * A Master list Fragment handling phone based configurations.
 */
public class MovieListFragment extends BaseMainListFragment {


    @Override
    String getSortCriteria() {
        return getArguments().getString(MovieFragmentFactory.EXTRA_SORT_CRITERIA);
    }
}
