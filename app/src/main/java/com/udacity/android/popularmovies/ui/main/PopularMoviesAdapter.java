package com.udacity.android.popularmovies.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Movie;

import java.util.List;

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesAdapterViewHolder> {

    private List<Movie> mMovies;

    private OnClickMovieListener movieListener;

    public PopularMoviesAdapter(OnClickMovieListener movieListener) {
        this.movieListener = movieListener;
    }

    @Override
    public PopularMoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.movies_list_item, viewGroup, false);

        return new PopularMoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopularMoviesAdapterViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        Picasso.get().load(movie.image).into(holder.movie_iv);
    }

    @Override
    public int getItemCount() {
        return mMovies != null && mMovies.size() !=  0 ? mMovies.size() : 0;
    }


    class PopularMoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView movie_iv;

        public PopularMoviesAdapterViewHolder(View itemView) {
            super(itemView);
            movie_iv = itemView.findViewById(R.id.list_item_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            movieListener.onClickItem(mMovies.get(getAdapterPosition()));
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

    public interface OnClickMovieListener {
        void onClickItem(Movie movie);
    }
}
