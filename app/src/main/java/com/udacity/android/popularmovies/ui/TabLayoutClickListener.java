package com.udacity.android.popularmovies.ui;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TabLayoutClickListener implements TabLayout.OnTabSelectedListener {

    private ViewPager mViewPager;

    public TabLayoutClickListener(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
