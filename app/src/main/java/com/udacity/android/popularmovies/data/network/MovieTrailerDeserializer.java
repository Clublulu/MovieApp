package com.udacity.android.popularmovies.data.network;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Trailer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to deserialize raw json movie trailer data.
 *
 */
public class MovieTrailerDeserializer implements JsonDeserializer<List<Trailer>> {

    private Context mContext;



    public MovieTrailerDeserializer(Context context) {
        mContext = context;
    }

    @Override
    public List<Trailer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Trailer> trailers = new ArrayList<>();
        JsonObject jsonMovie = json.getAsJsonObject();
        JsonArray trailerResults =  jsonMovie.get(getStringResource(mContext, R.string.results)).getAsJsonArray();


        for (int i = 0; i < trailerResults.size(); i++) {
            JsonObject jsonTrailer = trailerResults.get(i).getAsJsonObject();
            String trailerKey = jsonTrailer.get(getStringResource(mContext, R.string.trailer_key)).getAsString();
            String trailerTitle = jsonTrailer.get(getStringResource(mContext, R.string.trailer_name)).getAsString();


            trailers.add(new Trailer(trailerKey, trailerTitle));
        }

        return trailers;
    }


    private static String getStringResource(Context context, int resId) {
        return context.getString(resId);
    }

}
