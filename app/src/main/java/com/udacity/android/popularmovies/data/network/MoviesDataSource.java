package com.udacity.android.popularmovies.data.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.gson.reflect.TypeToken;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.utilities.MovieListDeserializer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Data source that retrieves and caches a local instance of data from the MovieDB API.
 *
 */
public class MoviesDataSource {

    private static final String LOG_TAG = MoviesDataSource.class.getSimpleName();

    // API key used to query moviedb. Please replace with your own.
    private static final String MOVIE_DB_API_KEY = "1c38f58dda138334ea280fc62955d062";
    public static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";

    private static final String PERIODIC_MOVIES_SYNC_TAG = "movies_sync_tag";

    private static MoviesDataSource sInstance;
    private static Retrofit mRetrofit;
    private final MutableLiveData<List<Movie>> mMovies;
    private static Context mContext;

    private MoviesDataSource(Context context) {
        mContext = context;
        mRetrofit = RetrofitClient
                .getInstance(new TypeToken<List<Movie>>() {}.getType(),
                             new MovieListDeserializer(context));
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
        Call<List<Movie>> call = service.getSortedMovies(sortCriteria, MOVIE_DB_API_KEY);

        try {
            Response<List<Movie>> response = call.execute();
            if (response.isSuccessful()) {
                for (Movie movie : response.body()) {
                    movie.sortCriteria = sortCriteria;
                }
                mMovies.postValue(response.body());
            }
        } catch (IOException e) {
            // TODO handle error
            // best way to handle this error?
            e.printStackTrace();
        }
    }

    public static String buildImageURL(String relativePath) {
        return Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendEncodedPath(relativePath)
                .build()
                .toString();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }
}

