package com.udacity.android.popularmovies.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.model.MovieUmbrella;

import java.util.List;

/**
 * Base Adapter for handling Trailers and Reviews.
 *
 * @param <T>
 */
public abstract class BaseListTypeAdapter<T extends MovieUmbrella> extends RecyclerView.Adapter<BaseListTypeAdapter.BaseMovieListTypeViewHolder> {

    private List<T> mMovieList;

    @Override
    public void onBindViewHolder(BaseListTypeAdapter.BaseMovieListTypeViewHolder viewHolder, int position) {
        viewHolder.bind(mMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovieList == null ? 0 : mMovieList.size();
    }

    public void swapData(List<T> newData) {
        mMovieList = newData;
        notifyDataSetChanged();

        // TODO experiment with DiffUtil
//        DiffUtil.DiffResult result = DiffUtil
//                    .calculateDiff(new MoviesListDifferenceCallback(mMovies, newMovies));
//            mMovies = newMovies;
//            result.dispatchUpdatesTo(this);
    }

    List<T> getList() {
        return mMovieList;
    }

    abstract class BaseMovieListTypeViewHolder extends RecyclerView.ViewHolder {

        public BaseMovieListTypeViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void bind(T data);
    }
}
