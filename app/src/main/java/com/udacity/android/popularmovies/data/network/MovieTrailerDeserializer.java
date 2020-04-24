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

public class MovieTrailerDeserializer implements JsonDeserializer<List<Trailer>> {

    private Context mContext;

    private static final int MAX_TRAILER_LIMIT = 3;

    public MovieTrailerDeserializer(Context context) {
        mContext = context;
    }

    @Override
    public List<Trailer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Trailer> trailers = new ArrayList<>();
        JsonObject jsonMovie = json.getAsJsonObject();
        JsonArray trailerResults =  jsonMovie.get(getStringResource(mContext, R.string.results)).getAsJsonArray();

        for (int i = 0; i < getActualResultsCount(trailerResults.size()); i++) {
            JsonObject jsonTrailer = trailerResults.get(i).getAsJsonObject();
            String trailerKey = jsonTrailer.get(getStringResource(mContext, R.string.trailer_key)).getAsString();
            String trailerTitle = jsonTrailer.get(getStringResource(mContext, R.string.trailer_name)).getAsString();

            String trailerURL = MoviesDataSource.buildYoutubeURL(trailerKey);
            trailers.add(new Trailer(trailerURL, trailerTitle));
        }

        return trailers;
    }


    private static String getStringResource(Context context, int resId) {
        return context.getString(resId);
    }

    /**
     * limits the amount of trailers up to MAX_TRAILER_LENGTH.
     *
     * @param jsonResultsSize
     * @return actual count to iterate through.
     */
    private int getActualResultsCount(int jsonResultsSize) {
        return jsonResultsSize > MAX_TRAILER_LIMIT ? MAX_TRAILER_LIMIT : jsonResultsSize;
    }
}
