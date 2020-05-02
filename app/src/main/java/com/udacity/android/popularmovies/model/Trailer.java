package com.udacity.android.popularmovies.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_trailer")
public class Trailer implements MovieUmbrella {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "title")
    public String title;

    public Trailer(int id, String url, String title) {
        this.id = id;
        this.url = url;
        this.title = title;
    }

    @Ignore
    public Trailer(String url, String title) {
        this.url = url;
        this.title = title;
    }

}
