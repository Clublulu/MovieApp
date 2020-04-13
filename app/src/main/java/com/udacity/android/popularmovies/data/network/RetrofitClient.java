package com.udacity.android.popularmovies.data.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Helper class to build and retrieve a Retrofit instance.
 *
 */
public class RetrofitClient {

    private static final String LOG_TAG = RetrofitClient.class.getSimpleName();

    private static Retrofit mRetrofit;
    private static final Object LOCK = new Object();

    public static Retrofit getInstance(Type type, Object typeAdapter) {
        Log.d(LOG_TAG, "Getting Retrofit instance.");
        if (mRetrofit == null) {
            synchronized (LOCK) {
                mRetrofit = new Retrofit.Builder()
                        .baseUrl(MoviesDataSource.MOVIE_DB_BASE_URL)
                        .addConverterFactory(createGsonConverter(type, typeAdapter))
                        .build();
                Log.d(LOG_TAG, "Created Retrofit instance.");
            }
        }

        return mRetrofit;
    }

    private static Converter.Factory createGsonConverter(Type type, Object typeAdapter) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type, typeAdapter);
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }

}
