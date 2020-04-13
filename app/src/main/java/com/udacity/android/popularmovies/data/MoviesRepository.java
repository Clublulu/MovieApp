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
            mExecutors.getDiskIO().execute(() -> {
                Log.d(LOG_TAG, "Inserted new movies.");
                mMoviesDao.insertMovies(newMovies);
            });
        });

    }

    public void retrieveMovies(String sortCriteria) {
        mDataSource.retrieveMovies(sortCriteria);
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


    public LiveData<List<Movie>> getMovies() {
        return mMoviesDao.getMovies();
    }

    public LiveData<Movie> getMovie(int movieId) {
        return mMoviesDao.getMovieById(movieId);
    }





}
