package com.udacity.android.popularmovies.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.udacity.android.popularmovies.model.Movie;

import java.util.List;


/**
 * Dao which provides an api for all data operations throughout Popular Movies.
 *
 */
@Dao
public interface MoviesDao {

    /**
     * Gets a list of movies.
     *
     * @return LiveData<List<Movie>>
     */
    @Query("SELECT * FROM movie_table")
    LiveData<List<Movie>> getMovies();


    /**
     * Gets a single movie by its' movie id.
     *
     * @param movieId
     * @return LiveData<Movie>
     */
    @Query("SELECT * FROM movie_table WHERE movie_id = :movieId")
    LiveData<Movie> getMovieById(int movieId);


    /**
     * Inserts a bulk of movies.
     * @param movies
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);



}
