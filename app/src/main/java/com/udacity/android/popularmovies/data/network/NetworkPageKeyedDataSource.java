package com.udacity.android.popularmovies.data.network;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.udacity.android.popularmovies.model.Movie;

import java.util.List;

public class NetworkPageKeyedDataSource extends PageKeyedDataSource<Integer, List<Movie>> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, List<Movie>> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, List<Movie>> callback) {

    }

    // empty implementation
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, List<Movie>> callback) { }
}
