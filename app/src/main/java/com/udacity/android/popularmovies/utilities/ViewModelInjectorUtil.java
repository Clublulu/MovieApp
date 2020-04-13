package com.udacity.android.popularmovies.utilities;


import android.content.Context;

import com.udacity.android.popularmovies.data.AppExecutors;
import com.udacity.android.popularmovies.data.MoviesRepository;
import com.udacity.android.popularmovies.data.database.MoviesDatabase;
import com.udacity.android.popularmovies.data.network.MoviesDataSource;
import com.udacity.android.popularmovies.ui.detail.DetailActivityViewModelFactory;
import com.udacity.android.popularmovies.ui.main.MainActivityViewModelFactory;

/**
 * Utility class that returns a View Model Factory for its corresponding Activity.
 *
 */
public class ViewModelInjectorUtil {

    public static DetailActivityViewModelFactory provideDetailActivityViewModelFactory(Context context, int movieId) {
        MoviesRepository repository = getRepository(context);
        return new DetailActivityViewModelFactory(repository, movieId);
    }

    public static MainActivityViewModelFactory provideMainActivityViewModelFactory(Context context) {
        MoviesRepository repository = getRepository(context);
        return new MainActivityViewModelFactory(repository);
    }

    private static MoviesRepository getRepository(Context context) {
        MoviesDatabase database = MoviesDatabase.getInstance(context);
        MoviesDataSource dataSource = MoviesDataSource.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        return MoviesRepository.getInstance(database.moviesDao(), dataSource, executors);
    }

}
