package com.udacity.android.popularmovies.utilities;

import android.net.Uri;

public class MoviesURLBuilder {

    private static MoviesURLBuilder sInstance;


    private static final String BASE_YOUTUBE_IMAGE_URL = "https://img.youtube.com/vi/";
    private static final String MAX_RES_DEFAULT = "maxresdefault.jpg";


    private MoviesURLBuilder() {

    }

    public static MoviesURLBuilder getInstance() {
        if (sInstance == null) {
            sInstance = new MoviesURLBuilder();
        }

        return sInstance;
    }

    public String buildYoutubeTrailerThumbnail(String trailerKey) {
        return Uri.parse(BASE_YOUTUBE_IMAGE_URL).buildUpon()
                .appendPath(trailerKey)
                .appendPath(MAX_RES_DEFAULT)
                .build()
                .toString();
    }

}
