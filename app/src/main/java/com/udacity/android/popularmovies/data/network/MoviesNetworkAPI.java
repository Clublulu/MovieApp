package com.udacity.android.popularmovies.data.network;

import com.udacity.android.popularmovies.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesNetworkAPI {

    @GET("{sortCriteria}")
    Call<List<Movie>> getSortedMovies(
            @Path("sortCriteria") String sortCriteria,
            @Query("api_key") String apiKey);


}
