package com.udacity.android.popularmovies.ui.fragment;

import androidx.fragment.app.FragmentManager;

import com.udacity.android.popularmovies.ui.adapter.ViewPagerAdapter;

public class MoviesViewPager {

    private ViewPagerAdapter mAdapter;

    private MoviesViewPager(FragmentManager fragmentManager) {
        mAdapter = new ViewPagerAdapter(fragmentManager);
    }

    public static MoviesViewPager getInstance(FragmentManager fragmentManager) {
        return new MoviesViewPager(fragmentManager);
    }

    public void addFragment(String sortCriteria, String tabTitle) {
        mAdapter.addFrag(MovieListFragment.getInstance(sortCriteria), tabTitle);
    }

    public void addFragment(int fragResId, int movieId, String tabTitle) {
        mAdapter.addFrag(BaseDetailListFragment.getInstance(null, fragResId, movieId), tabTitle);
    }


    public ViewPagerAdapter getAdapter() {
        return mAdapter;
    }
}
