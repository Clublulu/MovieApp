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
 * in addition to providing an instance of the Movies Data Source when triggered from running a service, or a recurring request triggered by WorkManager.
 *
 */
public class ObjectProviderUtil {

    public static DetailActivityViewModelFactory provideDetailActivityViewModelFactory(Context context, int movieId) {
        return new DetailActivityViewModelFactory(getRepository(context), movieId);
    }

    public static MainActivityViewModelFactory provideMainActivityViewModelFactory(Context context) {
        return new MainActivityViewModelFactory(getRepository(context));
    }

    public static MoviesDataSource provideMoviesDataSource(Context context) {
        getRepository(context);
        return MoviesDataSource.getInstance(context);
    }

    private static MoviesRepository getRepository(Context context) {
        return MoviesRepository.getInstance(
                MoviesDatabase.getInstance(context).moviesDao(),
                MoviesDataSource.getInstance(context),
                AppExecutors.getInstance());
    }

}
