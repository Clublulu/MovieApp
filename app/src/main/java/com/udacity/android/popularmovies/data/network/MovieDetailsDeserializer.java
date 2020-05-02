package com.udacity.android.popularmovies.data.network;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to deserialize raw json movie detail data.
 *
 */
public final class MovieDetailsDeserializer implements JsonDeserializer<List<Movie>> {

    private static Context mContext;

    public MovieDetailsDeserializer(Context context) {
        mContext = context;
    }

    @Override
    public List<Movie> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Movie> movies = new ArrayList<>();
        JsonObject jsonMovie = json.getAsJsonObject();
        JsonArray movieResults =  jsonMovie.get(getStringResource(mContext, R.string.results)).getAsJsonArray();

        for (JsonElement movie : movieResults) {
            jsonMovie = movie.getAsJsonObject();
            String poster_path = jsonMovie.get(getStringResource(mContext, R.string.poster_path)).getAsString();
            int movie_id = jsonMovie.get(getStringResource(mContext, R.string.movie_id)).getAsInt();
            double averageRating = jsonMovie.get(getStringResource(mContext, R.string.average_rating)).getAsDouble();
            String averageRatingString = averageRating + " " + getStringResource(mContext, R.string.average_rating_ten_label);
            String description = jsonMovie.get(getStringResource(mContext, R.string.description)).getAsString();
            String releaseDate = jsonMovie.get(getStringResource(mContext, R.string.release_date)).getAsString();
            String title = jsonMovie.get(getStringResource(mContext, R.string.movie_title)).getAsString();

            movies.add(new Movie(movie_id,
                    MoviesDataSource.buildImageURL(poster_path),
                    title,
                    averageRatingString,
                    description,
                    releaseDate));
        }

        return movies;
    }

    private static String getStringResource(Context context, int resId) {
        return context.getString(resId);
    }
}
