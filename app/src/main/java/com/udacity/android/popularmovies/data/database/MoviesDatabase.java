package com.udacity.android.popularmovies.data.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.udacity.android.popularmovies.data.network.MoviesDataSource;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

/**
 * Database class for the PopularMovies App.
 *
 */
@Database(entities = {Movie.class}, version = 2, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase sInstance;
    private static final String LOG_TAG = MoviesDatabase.class.getSimpleName();

    private static final String DB_NAME = "Popular_Movies";


    public static MoviesDatabase getInstance(Context context) {
        Log.d(LOG_TAG, "Retrieving Database instance.");
        if (sInstance == null) {
            synchronized (MoviesDatabase.class) {
                sInstance = Room.databaseBuilder(
                        context, MoviesDatabase.class, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
                Log.d(LOG_TAG, "Database instance created.");
            }
        }

        return sInstance;
    }



    public abstract MoviesDao moviesDao();
}
