package com.udacity.android.popularmovies.utilities;

import android.content.Context;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class PopularMoviesJsonUtility {


    public static List<Movie> getMoviesFromJsonString(Context context, String jsonMovieString) {
        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject jsonMovie = new JSONObject(jsonMovieString);
            JSONArray movieResults = jsonMovie.getJSONArray(getStringResource(context, R.string.results));
            for (int i = 0; i < movieResults.length(); i++) {
                JSONObject movie = movieResults.getJSONObject(i);
                String poster_path = movie.getString(getStringResource(context, R.string.poster_path));
                int movie_id = movie.getInt(getStringResource(context, R.string.movie_id));

                double averageRating = movie.getDouble(getStringResource(context, R.string.average_rating));
                String description = movie.getString(getStringResource(context, R.string.description));
                String releaseDate = movie.getString(getStringResource(context, R.string.release_date));
                String title = movie.getString(getStringResource(context, R.string.movie_title));

                movies.add(new Movie(movie_id,
                                         NetworkUtility.buildImageURL(poster_path),
                                         title,
                                         averageRating,
                                         description,
                                         releaseDate));
            }
        } catch (JSONException e) {
            throw new RuntimeException("Unable to parson JSON data");
        }

        return movies;
    }


    private static String getStringResource(Context context, int resId) {
        return context.getString(resId);
    }


}
