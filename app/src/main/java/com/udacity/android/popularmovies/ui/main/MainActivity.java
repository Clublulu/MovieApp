package com.udacity.android.popularmovies.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.ui.TabLayoutClickListener;
import com.udacity.android.popularmovies.ui.fragment.MoviesViewPager;

public class MainActivity extends AppCompatActivity {

    private androidx.viewpager.widget.ViewPager mViewPager;

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
        MoviesViewPager mainMoviesViewPager = MoviesViewPager.getInstance(getSupportFragmentManager());
        mainMoviesViewPager.addFragment(getString(R.string.popular), getString(R.string.popular_label));
        mainMoviesViewPager.addFragment(getString(R.string.top_rated), getString(R.string.top_rated_label));
        mainMoviesViewPager.addFragment(getString(R.string.favorites), getString(R.string.favorites_label));
        mViewPager.setAdapter(mainMoviesViewPager.getAdapter());
    }

    private void configureTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayoutClickListener(mViewPager));
    }
}
