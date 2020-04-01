package com.udacity.android.popularmovies.utilities;

public interface VolleyRequestListener {
    void onResponse(String response);
    void onError(String error);
}
