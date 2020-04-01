package com.udacity.android.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkUtility {

    // API key used to query moviedb. Please replace with your own.
    private static final String MOVIE_DB_API_KEY = "1c38f58dda138334ea280fc62955d062";

    private static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie";

    private static final String MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    private static final String API_KEY_PARAM = "api_key";

    private static final String IMAGE_SIZE = "w185";

    private static NetworkUtility mNetworkUtility;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private NetworkUtility(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();

    }

    static synchronized NetworkUtility getInstance(Context context) {
        if (mNetworkUtility == null) {
            mNetworkUtility = new NetworkUtility(context);
        }

        return mNetworkUtility;
    }

    RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    void addRequestToQueue(Request<String> request) {
        getRequestQueue().add(request);
    }

    static String buildURL(String sortCriteria) {
        return Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendPath(sortCriteria)
                .appendQueryParameter(API_KEY_PARAM, MOVIE_DB_API_KEY)
                .build().toString();

    }

    static String buildImageURL(String relativePath) {
        return Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendEncodedPath(relativePath)
                .build()
                .toString();
    }
}

