package com.udacity.android.popularmovies.data.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.android.popularmovies.model.Review;

import java.lang.reflect.Type;
import java.util.List;

public class ReviewsTypeConverter {

    /**
     * Converts a list of reviews to Json representation.
     *
     * @param reviews
     * @return string of reviews.
     */
    @TypeConverter
    public String toReviewsJson(List<Review> reviews) {
        return new Gson().toJson(reviews);
    }

    /**
     * Converts a String representation of reviews into a list.
     *
     * @param reviews
     * @return list of reviews.
     */
    @TypeConverter
    public List<Review> toReviewsList(String reviews) {
        Type type = new TypeToken<List<Review>>() {}.getType();
        return new Gson().fromJson(reviews, type);
    }
}
