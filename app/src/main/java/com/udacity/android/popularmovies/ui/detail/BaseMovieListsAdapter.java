package com.udacity.android.popularmovies.ui.detail;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.model.MovieListable;

import java.util.List;

/**
 * Base Adapter for handling Trailers and Reviews.
 *
 * @param <T>
 */
public abstract class BaseMovieListsAdapter<T extends MovieListable> extends RecyclerView.Adapter<BaseMovieListsAdapter.BaseMovieListsViewHolder> {

    private List<T> mMovieList;

    @Override
    public void onBindViewHolder(BaseMovieListsAdapter.BaseMovieListsViewHolder viewHolder, int position) {
        viewHolder.bind(mMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovieList == null ? 0 : mMovieList.size();
    }

    public void swapData(List<T> newData) {
        mMovieList = newData;
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return mMovieList;
    }

    abstract class BaseMovieListsViewHolder extends RecyclerView.ViewHolder {

        public BaseMovieListsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(T data);
    }
}
