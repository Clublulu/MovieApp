package com.udacity.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int id;
    private String image;
    private String title;
    private double averageRating;
    private String description;
    private String releaseDate;

    public Movie(int id, String image, String title, double averageRating, String description, String releaseDate) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.averageRating = averageRating;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        image = in.readString();
        title = in.readString();
        averageRating = in.readDouble();
        description = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(image);
        dest.writeString(title);
        dest.writeDouble(averageRating);
        dest.writeString(description);
        dest.writeString(releaseDate);
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
