package com.udacity.android.popularmovies.data.network;

import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.model.Review;
import com.udacity.android.popularmovies.model.Trailer;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesNetworkAPI {


    @GET("{sortCriteria}")
    Observable<List<Movie>> getMovies(
            @Path("sortCriteria") String sortCriteria,
            @Query("api_key") String apiKey);


    @GET("{movieId}/videos")
    Observable<List<Trailer>> getMovieTrailers(@Path("movieId") int movieId,
                                               @Query("api_key") String apiKey);

    @GET("{movieId}/reviews")
    Observable<List<Review>> getMovieReviews(@Path("movieId") int movieId,
                                             @Query("api_key") String apiKey);

}
