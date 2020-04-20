package com.udacity.android.popularmovies.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.udacity.android.popularmovies.data.MoviesRepository;
import com.udacity.android.popularmovies.model.Movie;

import java.util.List;

/**
 * View Model class for Main Activity.
 *
 */
public class MainActivityViewModel extends ViewModel {

    private static MoviesRepository mRepository;
    private MutableLiveData<String> mSortCriteria;


    public MainActivityViewModel(MoviesRepository repository) {
        mRepository = repository;
        mSortCriteria = new MutableLiveData<>();
    }

    public void getLatestMovies(String sortCriteria) {
        mSortCriteria.setValue(sortCriteria);
    }

    public LiveData<List<Movie>> getMovies() {
        return Transformations.switchMap(
                mSortCriteria,
                sortCriteria -> mRepository.getMovies(sortCriteria));
    }
}
