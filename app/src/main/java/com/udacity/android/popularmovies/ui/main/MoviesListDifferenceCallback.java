package com.udacity.android.popularmovies.ui.main;

import androidx.recyclerview.widget.DiffUtil;

import com.udacity.android.popularmovies.model.Movie;

import java.util.List;

/**
 * Helper class that is a DiffUtil.Callback,
 * checks whether contents of old & new data are the same, and swaps it accordingly.
 *
 */
public class MoviesListDifferenceCallback extends DiffUtil.Callback {

    private List<Movie> mOldMovies;
    private List<Movie> mNewMovies;

    public MoviesListDifferenceCallback(List<Movie> oldMovies, List<Movie> newMovies) {
        mOldMovies = oldMovies;
        mNewMovies = newMovies;
    }

    @Override
    public int getOldListSize() {
        return mOldMovies.size();
    }

    @Override
    public int getNewListSize() {
        return mNewMovies.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldMovies.get(oldItemPosition).movieId ==
                mNewMovies.get(newItemPosition).movieId;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Movie oldMovie = mOldMovies.get(oldItemPosition);
        Movie newMovie = mNewMovies.get(newItemPosition);

        return oldMovie.movieId == newMovie.movieId &&
                oldMovie.title.equals(newMovie.title);
    }
}
