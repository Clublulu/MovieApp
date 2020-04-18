package com.udacity.android.popularmovies.data;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.udacity.android.popularmovies.data.database.MoviesDao;
import com.udacity.android.popularmovies.data.network.MoviesDataSource;
import com.udacity.android.popularmovies.model.Movie;

import java.util.List;


/**
 * Repository class that acts as the central API for data operations.
 *
 */
public class MoviesRepository {

    private static final String LOG_TAG = MoviesRepository.class.getSimpleName();

    private static MoviesRepository sInstance;
    private static final Object LOCK = new Object();

    private static MoviesDao mMoviesDao;
    private static MoviesDataSource mDataSource;
    private static AppExecutors mExecutors;

    private MoviesRepository(MoviesDao moviesDao, MoviesDataSource dataSource, AppExecutors executors) {
        mMoviesDao = moviesDao;
        mDataSource = dataSource;
        mExecutors = executors;

        LiveData<List<Movie>> movies = mDataSource.getMovies();
        movies.observeForever(newMovies -> {
            mExecutors.getDiskExecutor().execute(() -> {
                Log.d(LOG_TAG, "Inserting new movies.");
                mMoviesDao.insertMovies(newMovies);
                Log.d(LOG_TAG, "Movies inserted.");
            });
        });

        // schedule a unique recurring task that fetches data every 24 hr.
        mDataSource.scheduleRecurringDataFetchTask();
    }

    public static MoviesRepository getInstance(MoviesDao moviesDao,
                                               MoviesDataSource dataSource, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting repository instance");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MoviesRepository(moviesDao, dataSource, executors);
                Log.d(LOG_TAG, "Repository instance created");
            }
        }

        return sInstance;
    }

    public LiveData<List<Movie>> getLatestMovies(String sortCriteria) {
        mExecutors.getNetworkExecutor().execute(() -> {
            if (isDataFetchNeeded(sortCriteria)) {
                mDataSource.retrieveMovies(sortCriteria);
            }
        });
        return mMoviesDao.getMovies(sortCriteria);
    }

    public LiveData<Movie> getMovieById(int movieId) {
        return mMoviesDao.getMovieById(movieId);
    }


    private boolean isDataFetchNeeded(String sortCriteria) {
        return mMoviesDao.getMovieCount(sortCriteria) <= 0;
    }
}
