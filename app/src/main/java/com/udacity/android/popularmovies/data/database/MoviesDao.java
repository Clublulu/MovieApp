package com.udacity.android.popularmovies.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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
    @Query("SELECT * FROM movie_table WHERE sort_criteria = :sortCriteria")
    LiveData<List<Movie>> getMovies(String sortCriteria);


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


    /**
     * Returns a count of movies based on the chosen sort criteria
     *
     * @param sortCriteria
     * @return number of movies based on sort criteria
     */
    @Query("SELECT COUNT(movie_id) FROM movie_table WHERE sort_criteria = :sortCriteria")
    int getMovieCount(String sortCriteria);


    /**
     * Deletes all entries from movie_table.
     *
     */
    @Query("DELETE FROM movie_table")
    void deleteAllMovies();


}
