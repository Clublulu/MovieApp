package com.udacity.android.popularmovies.ui.fragment;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.ui.MovieClickListener;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailFragment extends BaseDetailListFragment<Movie> {


    @Override
    List<Movie> getItemList(Movie movie) {
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        return movies;
    }


    // empty implementation -- MovieDetailFragment doesn't return items
    @Override
    String noItemsFound() {
        return null;
    }

    @Override
    int getAdapterResId() {
        return R.string.app_adapter_movie_details;
    }

    @Override
    MovieClickListener getClickListener() {
        return null;
    }
}
