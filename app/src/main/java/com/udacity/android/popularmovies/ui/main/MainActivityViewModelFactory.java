package com.udacity.android.popularmovies.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.udacity.android.popularmovies.data.MoviesRepository;

/**
 * View Model Factory helper class that generates the View Model for the Main Activity.
 *
 */
public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MoviesRepository mMoviesRepository;

    public MainActivityViewModelFactory(MoviesRepository repository) {
        mMoviesRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(mMoviesRepository);
    }
}
