package com.udacity.android.popularmovies.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_reviews")
public class Review implements MovieListable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "author_name")
    public String author;

    @ColumnInfo(name = "description")
    public String description;


    public Review(int id, String author, String description) {
        this.id = id;
        this.author = author;
        this.description = description;
    }

    @Ignore
    public Review(String author, String description) {
        this.author = author;
        this.description = description;
    }



}
