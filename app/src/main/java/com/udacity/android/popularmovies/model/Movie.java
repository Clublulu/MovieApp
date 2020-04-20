package com.udacity.android.popularmovies.model;


import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.squareup.picasso.Picasso;

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
    public String averageRating;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "release_date")
    public String releaseDate;

    @ColumnInfo(name = "sort_criteria")
    public String sortCriteria;

    @ColumnInfo(name = "is_favorite")
    public boolean isFavorite;

    public Movie(int movieId, String image, String title, String averageRating, String description, String releaseDate, String sortCriteria, boolean isFavorite) {
        this.movieId = movieId;
        this.image = image;
        this.title = title;
        this.averageRating = averageRating;
        this.description = description;
        this.releaseDate = releaseDate;
        this.sortCriteria = sortCriteria;
        this.isFavorite = isFavorite;
    }

    @Ignore
    public Movie(int movieId, String image, String title, String averageRating, String description, String releaseDate) {
        this.movieId = movieId;
        this.image = image;
        this.title = title;
        this.averageRating = averageRating;
        this.description = description;
        this.releaseDate = releaseDate;
    }


    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get().load(imageUrl).into(view);
    }

}
