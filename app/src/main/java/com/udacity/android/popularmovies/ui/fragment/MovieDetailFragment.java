package com.udacity.android.popularmovies.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.udacity.android.popularmovies.databinding.FragmentMovieDetailBinding;
import com.udacity.android.popularmovies.ui.detail.DetailActivityViewModel;
import com.udacity.android.popularmovies.ui.detail.DetailActivityViewModelFactory;
import com.udacity.android.popularmovies.utilities.ObjectProviderUtil;

/**
 * Fragment displaying the detailed information about a Movie.
 *
 */
public class MovieDetailFragment extends Fragment {


    private static final String MOVIE_ID_KEY = "movie_id_key";
    private int mMovieId;

    public static MovieDetailFragment getInstance(int movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIE_ID_KEY, movieId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mMovieId = getArguments().getInt(MOVIE_ID_KEY);
        }

        FragmentMovieDetailBinding binding = FragmentMovieDetailBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        DetailActivityViewModelFactory factory = ObjectProviderUtil
                .provideDetailActivityViewModelFactory(getActivity().getApplicationContext(), mMovieId);
        DetailActivityViewModel viewModel = new ViewModelProvider(this, factory).get(DetailActivityViewModel.class);
        viewModel.getMovie().observe(this, movie -> {
                binding.setMovie(movie);
        });

        return binding.getRoot();
    }

}
