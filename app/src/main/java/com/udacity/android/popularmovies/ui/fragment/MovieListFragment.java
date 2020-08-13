package com.udacity.android.popularmovies.ui.fragment;


import android.os.Bundle;

import com.udacity.android.popularmovies.R;

/**
 * A Master list Fragment handling phone based configurations.
 */
public class MovieListFragment extends BaseMainListFragment {


    @Override
    String getSortCriteria() {
        return getArguments().getString(MovieFragmentFactory.EXTRA_SORT_CRITERIA);
    }

    @Override
    String getNoItemText() {
        return getString(R.string.favorites_not_found_message);
    }
}
