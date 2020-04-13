package com.udacity.android.popularmovies.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class Movie {

    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    public int movieId;

    @ColumnInfo(name = "poster_path")
    public String image;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "vote_average")
    public double averageRating;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "release_date")
    public String releaseDate;


    public Movie(int movieId, String image, String title, double averageRating, String description, String releaseDate) {
        this.movieId = movieId;
        this.image = image;
        this.title = title;
        this.averageRating = averageRating;
        this.description = description;
        this.releaseDate = releaseDate;
    }
}
