package com.udacity.android.popularmovies.data.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.android.popularmovies.model.Trailer;

import java.lang.reflect.Type;
import java.util.List;

public class TrailersTypeConverter {

    /**
     * Converts a list of trailers to Json representation.
     *
     * @param trailers
     * @return string of trailers.
     */
    @TypeConverter
    public String toTrailersJson(List<Trailer> trailers) {
        return new Gson().toJson(trailers);
    }

    /**
     * Converts a String representation of trailers into a list.
     *
     * @param trailers
     * @return list of trailers.
     */
    @TypeConverter
    public List<Trailer> toTrailersList(String trailers) {
        Type type = new TypeToken<List<Trailer>>() {}.getType();
        return new Gson().fromJson(trailers, type);
    }
}
