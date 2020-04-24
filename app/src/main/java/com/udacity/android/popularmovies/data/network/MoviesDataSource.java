package com.udacity.android.popularmovies.data.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.model.Review;
import com.udacity.android.popularmovies.model.Trailer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Data source that retrieves and caches a local instance of data from the MovieDB API.
 *
 */
public class MoviesDataSource {

    private static final String LOG_TAG = MoviesDataSource.class.getSimpleName();

    // API key used to query moviedb. Please replace with your own.
    private static final String MOVIE_DB_API_KEY = "YOUR_API_KEY_HERE";
    public static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/";
    private static final String YOUTUBE_WATCH_PARAMETER = "watch";
    private static final String IMAGE_SIZE = "w185";

    private static final String PERIODIC_MOVIES_SYNC_TAG = "movies_sync_tag";

    private static MoviesDataSource sInstance;
    private static Retrofit mRetrofit;
    private final MutableLiveData<List<Movie>> mMovies;
    private static Context mContext;

    private MoviesDataSource(Context context) {
        mContext = context;
        mRetrofit = RetrofitClient.getInstance(context);
        mMovies = new MutableLiveData<>();
    }

    public static synchronized MoviesDataSource getInstance(Context context) {
        Log.d(LOG_TAG, "Getting data source instance.");
        if (sInstance == null) {
            sInstance = new MoviesDataSource(context);
            Log.d(LOG_TAG, "Data source instance created.");
        }

        return sInstance;
    }


    public void scheduleRecurringDataFetchTask() {
        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(
                        MovieSyncWorker.class,
                        24,
                        TimeUnit.HOURS).build();
        WorkManager.getInstance(mContext).enqueueUniquePeriodicWork(
                        PERIODIC_MOVIES_SYNC_TAG,
                        ExistingPeriodicWorkPolicy.KEEP,
                        workRequest);
    }

    public void retrieveMovies(String sortCriteria) {
        MoviesNetworkAPI service = mRetrofit.create(MoviesNetworkAPI.class);

        service.getMovies(sortCriteria, MOVIE_DB_API_KEY)
        .flatMap((Function<List<Movie>, ObservableSource<Movie>>) moviesList -> Observable.fromIterable(moviesList))
        .flatMap((Function<Movie, ObservableSource<Movie>>) movie ->
                Observable.zip(Observable.just(movie),
                service.getMovieTrailers(movie.movieId, MOVIE_DB_API_KEY),
                service.getMovieReviews(movie.movieId, MOVIE_DB_API_KEY),
                (Function3<Movie, List<Trailer>, List<Review>, Movie>) (selectedMovie, trailers, reviews) -> {
                    selectedMovie.sortCriteria = sortCriteria;
                    selectedMovie.trailers = trailers;
                    selectedMovie.reviews = reviews;
                            return selectedMovie; }))
        .toList()
        .subscribeOn(Schedulers.io())
        .subscribe(movies -> mMovies.postValue(movies));


    }

    public static String buildImageURL(String relativePath) {
        return Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendEncodedPath(relativePath)
                .build()
                .toString();
    }

    public static String buildYoutubeURL(String trailerKey) {
        return Uri.parse(YOUTUBE_BASE_URL).buildUpon()
                .appendPath(YOUTUBE_WATCH_PARAMETER)
                .appendQueryParameter("v", trailerKey)
                .build()
                .toString();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }
}

