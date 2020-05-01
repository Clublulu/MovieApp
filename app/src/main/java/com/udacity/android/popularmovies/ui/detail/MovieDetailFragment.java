package com.udacity.android.popularmovies.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.FragmentMovieDetailBinding;
import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

/**
 * Fragment displaying the detailed information about a Movie.
 *
 */
public class MovieDetailFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

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
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        if (getArguments() != null) {
            mMovieId = getArguments().getInt(MOVIE_ID_KEY);
        }

        FragmentMovieDetailBinding binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_movie_detail);
        binding.setLifecycleOwner(this);
        DetailActivityViewModelFactory factory = MovieInstanceProviderUtil.provideDetailActivityViewModelFactory(
                                                                                getActivity().getApplicationContext(), mMovieId);
        DetailActivityViewModel viewModel = new ViewModelProvider(this, factory).get(DetailActivityViewModel.class);
        viewModel.getMovie().observe(this, movie -> {
            binding.setMovie(movie);
        });

        return view;
    }


}
