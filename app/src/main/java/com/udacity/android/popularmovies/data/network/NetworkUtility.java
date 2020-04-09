package com.udacity.android.popularmovies.data.network;

import android.content.Context;
import android.net.Uri;

import com.google.gson.reflect.TypeToken;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.utilities.MovieListDeserializer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class NetworkUtility {

    // API key used to query moviedb. Please replace with your own.
    private static final String MOVIE_DB_API_KEY = "1c38f58dda138334ea280fc62955d062";

    public static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";

    private static final String MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    private static final String IMAGE_SIZE = "w185";

    private static NetworkUtility mNetworkUtility;
    private static Context mContext;


    private Retrofit mRetrofit;

    private NetworkUtility(Context context) {
        mContext = context;
        mRetrofit = RetrofitClient
                .getInstance(new TypeToken<List<Movie>>() {}.getType(),
                             new MovieListDeserializer(context));
    }

    public static synchronized NetworkUtility getInstance(Context context) {
        if (mNetworkUtility == null) {
            mNetworkUtility = new NetworkUtility(context);
        }

        return mNetworkUtility;
    }

    public Call<List<Movie>> fetchSortedMovies(String sortCriteria) {
        MoviesNetworkAPI service = mRetrofit.create(MoviesNetworkAPI.class);
        return service.getSortedMovies(sortCriteria, MOVIE_DB_API_KEY);
    }

    public static String buildImageURL(String relativePath) {
        return Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendEncodedPath(relativePath)
                .build()
                .toString();
    }
}

