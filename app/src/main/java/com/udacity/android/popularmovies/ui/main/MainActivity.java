package com.udacity.android.popularmovies.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.ui.TabLayoutClickListener;
import com.udacity.android.popularmovies.ui.adapter.MainViewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(getString(R.string.app_name));
        configureViewPager();
        configureTabLayout();
    }

    private void configureViewPager() {
        mViewPager = findViewById(R.id.tab_viewpager);
        MainViewPager mainViewPager = new MainViewPager(getSupportFragmentManager());
        mainViewPager.addFragment(getString(R.string.popular), getString(R.string.popular_label));
        mainViewPager.addFragment(getString(R.string.top_rated), getString(R.string.top_rated_label));
        mainViewPager.addFragment(getString(R.string.favorites), getString(R.string.favorites_label));
        mViewPager.setAdapter(mainViewPager.getAdapter());
    }

    private void configureTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayoutClickListener(mViewPager));
    }
}
