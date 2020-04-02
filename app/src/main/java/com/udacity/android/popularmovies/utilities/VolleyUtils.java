package com.udacity.android.popularmovies.utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Volley Utility class to help build Volley Requests to send to Volley RequestQueue
 */
public class VolleyUtils {

    public static void fetchMovieData(final Context context, String sortCriteria, final VolleyRequestListener listener) {
        NetworkUtility networkUtility = NetworkUtility.getInstance(context);
        StringRequest request = new StringRequest(Request.Method.GET,
                networkUtility.buildURL(sortCriteria),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                });

        networkUtility.addRequestToQueue(request);
    }

    public static String buildImageUrl(Context context, String poster_path) {
        return NetworkUtility.getInstance(context).buildImageURL(poster_path);
    }
}

