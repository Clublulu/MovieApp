package com.udacity.android.popularmovies.ui.detail;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.ActivityDetailBinding;
import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_ACTIVITY_INTENT_EXTRA = "DETAIL_ACTIVITY_INTENT_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.setLifecycleOwner(this);
        int movieId = getIntent().getIntExtra(DETAIL_ACTIVITY_INTENT_EXTRA, -1);

        DetailActivityViewModelFactory factory = MovieInstanceProviderUtil
                .provideDetailActivityViewModelFactory(getApplicationContext(), movieId);
        DetailActivityViewModel viewModel = new ViewModelProvider(this, factory)
                .get(DetailActivityViewModel.class);
        viewModel.getMovie().observe(this, movie -> {
            binding.setMovie(movie);
        });
    }
}
