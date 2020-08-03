package com.udacity.android.popularmovies.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.FragmentMovieDetailBinding;
import com.udacity.android.popularmovies.model.Movie;

public class MovieDetailAdapter  extends BaseListTypeAdapter<Movie>  {


    @NonNull
    @Override
    public BaseMovieListTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        FragmentMovieDetailBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_movie_detail, viewGroup, false);
        return new MovieDetailViewHolder(binding);
    }

    class MovieDetailViewHolder extends BaseMovieListTypeViewHolder {

        public FragmentMovieDetailBinding binding;

        public MovieDetailViewHolder(FragmentMovieDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        void bind(Movie movie) {
            binding.setMovie(movie);
            binding.executePendingBindings();
        }
    }
}
