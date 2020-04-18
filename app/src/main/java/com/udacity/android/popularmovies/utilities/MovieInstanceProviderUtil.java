package com.udacity.android.popularmovies.utilities;


import android.content.Context;

import com.udacity.android.popularmovies.data.AppExecutors;
import com.udacity.android.popularmovies.data.MoviesRepository;
import com.udacity.android.popularmovies.data.database.MoviesDatabase;
import com.udacity.android.popularmovies.data.network.MoviesDataSource;
import com.udacity.android.popularmovies.ui.detail.DetailActivityViewModelFactory;
import com.udacity.android.popularmovies.ui.main.MainActivityViewModelFactory;

/**
 * Utility class that provides Model View instances for both the main and detail activities,
 * in addition to providing an instance of the Movies Data Source when triggered from running a service.
 *
 */
public class MovieInstanceProviderUtil {

    public static DetailActivityViewModelFactory provideDetailActivityViewModelFactory(Context context, int movieId) {
        MoviesRepository repository = getRepository(context);
        return new DetailActivityViewModelFactory(repository, movieId);
    }

    public static MainActivityViewModelFactory provideMainActivityViewModelFactory(Context context, String sortCriteria) {
        MoviesRepository repository = getRepository(context);
        return new MainActivityViewModelFactory(repository, sortCriteria);
    }

    public static MoviesDataSource provideMoviesDataSource(Context context) {
        getRepository(context);
        return MoviesDataSource.getInstance(context);
    }

    private static MoviesRepository getRepository(Context context) {
        MoviesDatabase database = MoviesDatabase.getInstance(context);
        MoviesDataSource dataSource = MoviesDataSource.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        return MoviesRepository.getInstance(database.moviesDao(), dataSource, executors);
    }

}
