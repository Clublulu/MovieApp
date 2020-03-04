package com.udacity.android.popularmovies.utilities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Utility class handling network communication between app and MovieDB API
 *
 */
public final class NetworkUtility {

    // API key used to query moviedb. Please replace with your own.
    private static final String MOVIE_DB_API_KEY = "1c38f58dda138334ea280fc62955d062";


    private static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/discover/movie";

    private static final String MOVIE_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    private static final String POPULARITY = "popularity.desc";


    private static final String API_KEY_PARAM = "api_key";
    private static final String SORT_BY_PARAM = "sort_by";

    private static final String IMAGE_SIZE = "w185";

    public static URL buildURL() {
        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, MOVIE_DB_API_KEY)
                .appendQueryParameter(SORT_BY_PARAM, POPULARITY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid URL");
        }

        return url;
    }

    public static String buildImageURL(String relativePath) {
        return Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendPath(relativePath)
                .build()
                .toString();
    }

    public static String getResponseFromHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
