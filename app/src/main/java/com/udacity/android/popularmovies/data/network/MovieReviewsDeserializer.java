package com.udacity.android.popularmovies.data.network;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Review;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to deserialize raw json movie review data.
 *
 *
 */
public class MovieReviewsDeserializer implements JsonDeserializer<List<Review>> {

    private Context mContext;

    public MovieReviewsDeserializer(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public List<Review> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Review> reviews = new ArrayList<>();
        JsonObject jsonMovie = json.getAsJsonObject();
        JsonArray reviewResults =  jsonMovie.get(getStringResource(mContext, R.string.results)).getAsJsonArray();

        for (int i = 0; i < reviewResults.size(); i++) {
            JsonObject jsonReview = reviewResults.get(i).getAsJsonObject();
            String author = jsonReview.get(getStringResource(mContext, R.string.review_author)).getAsString();
            String content = jsonReview.get(getStringResource(mContext, R.string.review_content)).getAsString();
            reviews.add(new Review(author, content));
        }

        return reviews;
    }


    private static String getStringResource(Context context, int resId) {
        return context.getString(resId);
    }
}
