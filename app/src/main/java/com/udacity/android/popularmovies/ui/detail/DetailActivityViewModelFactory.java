package com.udacity.android.popularmovies.ui.detail;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.udacity.android.popularmovies.data.MoviesRepository;

/**
 * View Model Factory helper class that generates the View Model for the Detail Activity.
 *
 */
public class DetailActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MoviesRepository mRepository;
    private int mMovieId;

    public DetailActivityViewModelFactory(MoviesRepository repository, int movieId) {
        mRepository = repository;
        mMovieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetailActivityViewModel(mRepository, mMovieId);
    }
}
