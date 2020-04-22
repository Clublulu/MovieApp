package com.udacity.android.popularmovies.data.network;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

/**
 * This worker runs on a 24 hour schedule to fetch the latest movie data available.
 *
 */
public class MovieSyncWorker extends Worker {

    private static final String LOG_TAG = MovieSyncWorker.class.getSimpleName();

    public MovieSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(LOG_TAG, "Starting sync job.");
        // creating instances of objects ... do in method or initialize in context? does
        // an instance of MovieSyncWorker always live in the background?
        MoviesDataSource dataSource = MovieInstanceProviderUtil
                .provideMoviesDataSource(getApplicationContext());

        // constant search criteria... how to go about running a worker that fetches me data for both popular
        // and top_rated
        dataSource.retrieveMovies("popular");

        Log.d(LOG_TAG, "Sync job finished.");
        return Result.success();
    }
}
