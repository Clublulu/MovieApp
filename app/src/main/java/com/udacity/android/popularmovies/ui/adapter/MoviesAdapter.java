package com.udacity.android.popularmovies.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.MoviesListItemBinding;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.ui.MovieClickListener;

/**
 * RecyclerView Adapter for displaying Movies on main screen.
 *
 */
public class MoviesAdapter extends BaseListTypeAdapter<Movie> {

    private MovieClickListener movieListener;

    public MoviesAdapter(MovieClickListener movieListener) {
        this.movieListener = movieListener;
    }

    @Override
    public PopularMoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        MoviesListItemBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.movies_list_item, viewGroup, false);

        return new PopularMoviesAdapterViewHolder(binding);
    }

    class PopularMoviesAdapterViewHolder extends BaseMovieListTypeViewHolder implements View.OnClickListener {

        public MoviesListItemBinding binding;

        public PopularMoviesAdapterViewHolder(MoviesListItemBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setOnClickListener(this);
            this.binding = binding;
        }

        @Override
        public void onClick(View v) {
            movieListener.onClickItem(getList().get(getAdapterPosition()));
        }

        @Override
        void bind(Movie movie) {
            binding.setMovie(movie);
            binding.executePendingBindings();
        }
    }
}
