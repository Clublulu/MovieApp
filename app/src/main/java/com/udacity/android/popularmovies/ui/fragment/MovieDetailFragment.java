package com.udacity.android.popularmovies.ui.fragment;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.udacity.android.popularmovies.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.udacity.android.popularmovies.databinding.FragmentMovieDetailBinding;
import com.udacity.android.popularmovies.ui.detail.DetailActivityViewModel;
import com.udacity.android.popularmovies.ui.detail.DetailActivityViewModelFactory;
import com.udacity.android.popularmovies.utilities.ObjectProviderUtil;

public class MovieDetailFragment extends Fragment {

    private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
    private int mMovieId;
    private ImageView mButtonFavorite;
    private AnimatedVectorDrawable mEmptyHeart;
    private AnimatedVectorDrawable mFillHeart;
    private DetailActivityViewModel mViewModel;

    public static Fragment getInstance(int movieId) {
        Fragment fragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_MOVIE_ID, movieId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMovieDetailBinding binding = FragmentMovieDetailBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        
        mMovieId = getArguments().getInt(EXTRA_MOVIE_ID);
        mEmptyHeart = (AnimatedVectorDrawable) getActivity().getDrawable(R.drawable.avd_heart_empty);
        mFillHeart = (AnimatedVectorDrawable) getActivity().getDrawable(R.drawable.avd_heart_fill);
        mButtonFavorite = binding.getRoot().findViewById(R.id.button_favorite);

        DetailActivityViewModelFactory factory = ObjectProviderUtil
                .provideDetailActivityViewModelFactory(getContext(), mMovieId);
        mViewModel = new ViewModelProvider(this, factory)
                .get(DetailActivityViewModel.class);
        mViewModel.getMovie().observe(this,
                movie -> {
                    configureFavoriteButton(movie.isFavorite);
                    binding.setMovie(movie);
                });


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.getMovie().observe(this, movie -> {
            AnimatedVectorDrawable drawable = movie.isFavorite ? mFillHeart : mEmptyHeart;
            mButtonFavorite.setImageDrawable(drawable);
            drawable.start();
        });

    }

    private void configureFavoriteButton(boolean isFavorite) {
        mButtonFavorite.setOnClickListener(v -> {
            AnimatedVectorDrawable drawable = isFavorite ? mEmptyHeart : mFillHeart;
            mButtonFavorite.setImageDrawable(drawable);
            drawable.start();
            mViewModel.updateFavorite(mMovieId, !isFavorite);
        });
    }
    
}
