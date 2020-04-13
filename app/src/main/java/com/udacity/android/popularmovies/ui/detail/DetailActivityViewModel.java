package com.udacity.android.popularmovies.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.udacity.android.popularmovies.data.MoviesRepository;
import com.udacity.android.popularmovies.model.Movie;

/**
 * ViewModel for Detail Activity.
 *
 */
public class DetailActivityViewModel extends ViewModel {

    private final MoviesRepository mRepository;
    private LiveData<Movie> mMovie;

    public DetailActivityViewModel(MoviesRepository repository, int movieId) {
        mRepository = repository;
        mMovie = mRepository.getMovie(movieId);
    }

    public LiveData<Movie> getMovie() {
        return mMovie;
    }

}
