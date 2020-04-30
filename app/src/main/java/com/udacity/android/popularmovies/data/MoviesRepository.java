package com.udacity.android.popularmovies.data;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.udacity.android.popularmovies.data.database.MoviesDao;
import com.udacity.android.popularmovies.data.network.MoviesDataSource;
import com.udacity.android.popularmovies.model.Movie;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


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
//        mDataSource.scheduleRecurringDataFetchTask();
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

    /**
     * Gets the list of movies based on sort criteria.
     *
     * @param sortCriteria
     * @return list of movies
     */
    public LiveData<List<Movie>> getMovies(String sortCriteria) {
        if (isSortCriteriaFavorites(sortCriteria)) {
            return getFavoriteMovies();
        } else {
            return getOtherMovies(sortCriteria);
        }
    }

    /**
     * Get a specific movie via movieId.
     *
     * @param movieId
     * @return a movie
     */
    public LiveData<Movie> getMovieById(int movieId) {
        return mMoviesDao.getMovieById(movieId);
    }

    /**
     * Update the a Movie with the favorite preference.
     *
     * @param movieId
     * @param isFavorite
     */
    public void updateFavorite(int movieId, boolean isFavorite) {
        mExecutors.getDiskExecutor().execute(() -> mMoviesDao.updateFavoriteMovie(movieId, isFavorite));
    }

    private LiveData<List<Movie>> getOtherMovies(String sortCriteria) {
        mExecutors.getNetworkExecutor().execute(() -> {
            if (isDataFetchNeeded(sortCriteria)) {
                mDataSource.retrieveMovies(sortCriteria);
            }
        });
        return mMoviesDao.getMovies(sortCriteria);
    }
    
    
    private LiveData<List<Movie>> getFavoriteMovies() {
        Callable<LiveData<List<Movie>>> callable = () -> mMoviesDao.getFavoriteMovies();
        Future<LiveData<List<Movie>>> future = ((ExecutorService)
                mExecutors.getDiskExecutor()).submit(callable);
        LiveData<List<Movie>> movies = null;
        try {
            movies =  future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return movies;
    }

    private boolean isDataFetchNeeded(String sortCriteria) {
        return mMoviesDao.getMovieCount(sortCriteria) <= 0;
    }
    
    private boolean isSortCriteriaFavorites(String sortCriteria) {
        return "favorites".equals(sortCriteria);
    }
}
