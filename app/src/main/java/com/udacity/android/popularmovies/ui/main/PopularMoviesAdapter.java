package com.udacity.android.popularmovies.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.MoviesListItemBinding;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;

import java.util.List;

/**
 * Adapter class used to create views, bind data to them, and attach the views to the RecyclerView.
 *
 */
public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesAdapterViewHolder> {

    private List<Movie> mMovies;

    private MovieOnClickListener movieListener;

    public PopularMoviesAdapter(MovieOnClickListener movieListener) {
        this.movieListener = movieListener;
    }

    @Override
    public PopularMoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        MoviesListItemBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.movies_list_item, viewGroup, false);

        return new PopularMoviesAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PopularMoviesAdapterViewHolder holder, int position) {
        holder.bind(mMovies);
    }

    @Override
    public int getItemCount() {
        return mMovies != null && mMovies.size() !=  0 ? mMovies.size() : 0;
    }


    class PopularMoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MoviesListItemBinding binding;

        public PopularMoviesAdapterViewHolder(MoviesListItemBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setOnClickListener(this);
            this.binding = binding;
        }

        @Override
        public void onClick(View v) {
            movieListener.onClickItem(mMovies.get(getAdapterPosition()));
        }

        void bind(List<Movie> movies) {
            binding.setMovie(movies.get(getAdapterPosition()));
            binding.executePendingBindings();
        }
    }

    public void swapMovies(List<Movie> newMovies) {
        // notify data set changed only when movies in the adapter are initially empty
        if (mMovies == null) {
            mMovies = newMovies;
            notifyDataSetChanged();
        }
        // otherwise use DiffUtils' calculateDiff method to calculate changes between
        // old & new data, and update the resulting list accordingly
        else {
            DiffUtil.DiffResult result = DiffUtil
                    .calculateDiff(new MoviesListDifferenceCallback(mMovies, newMovies));
            mMovies = newMovies;
            result.dispatchUpdatesTo(this);
        }
    }
}
