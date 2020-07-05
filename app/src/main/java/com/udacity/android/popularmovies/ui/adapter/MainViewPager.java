package com.udacity.android.popularmovies.ui.adapter;

import androidx.fragment.app.FragmentManager;

import com.udacity.android.popularmovies.ui.fragment.MovieListFragment;

/**
 * ViewPager for Main class to help add fragments to ViewPageAdapter.
 *
 */
public class MainViewPager {

    private ViewPagerAdapter mAdapter;

    public MainViewPager(FragmentManager fragmentManager) {
        mAdapter = new ViewPagerAdapter(fragmentManager);
    }

    public void addFragment(String sortCriteria, String tabTitle) {
        mAdapter.addFrag(MovieListFragment.getInstance(sortCriteria), tabTitle);
    }

    public ViewPagerAdapter getAdapter() {
        return mAdapter;
    }

}
