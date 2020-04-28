package com.udacity.android.popularmovies.ui.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.ActivityDetailBinding;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;
import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

public class DetailActivity extends AppCompatActivity implements MovieOnClickListener {

    public static final String DETAIL_ACTIVITY_INTENT_EXTRA = "DETAIL_ACTIVITY_INTENT_EXTRA";

    private DetailActivityViewModel mViewModel;
    private int mMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.setLifecycleOwner(this);
        mMovieId = getIntent().getIntExtra(DETAIL_ACTIVITY_INTENT_EXTRA, -1);

        MovieTrailersAdapter trailerAdapter = setupRecyclerViewTrailer(binding);

        DetailActivityViewModelFactory factory = MovieInstanceProviderUtil
                .provideDetailActivityViewModelFactory(getApplicationContext(), mMovieId);
        mViewModel = new ViewModelProvider(this, factory)
                .get(DetailActivityViewModel.class);
        mViewModel.getMovie().observe(this, movie -> {
            binding.setMovie(movie);
            trailerAdapter.swapTrailers(movie.trailers);

        });
    }

    public void updateFavorite(View view) {
        boolean isFavorite = ((ToggleButton) view).isChecked();
        mViewModel.updateFavorite(mMovieId, isFavorite);
    }

    @Override
    public void onClickItem(Object trailer) {
        Toast.makeText(getApplicationContext(),"Trailer Click Event works",Toast.LENGTH_SHORT).show();
    }

    private MovieTrailersAdapter setupRecyclerViewTrailer(ActivityDetailBinding binding) {
        RecyclerView trailersRecyclerView = binding.trailersRv;
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MovieTrailersAdapter adapter = new MovieTrailersAdapter(this);
        trailersRecyclerView.setAdapter(adapter);

        return adapter;
    }
}
