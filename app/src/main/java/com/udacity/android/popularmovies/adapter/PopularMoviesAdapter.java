package com.udacity.android.popularmovies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Movie;

import java.util.List;

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesAdapterViewHolder> {

    private List<Movie> movies;

    public PopularMoviesAdapter() {}

    @Override
    public PopularMoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.movies_list_item, viewGroup, false);

        return new PopularMoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopularMoviesAdapterViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.get().load(movie.getImage()).into(holder.movie_iv);
    }

    @Override
    public int getItemCount() {
        return movies != null && movies.size() !=  0 ? movies.size() : 0;
    }

    class PopularMoviesAdapterViewHolder extends RecyclerView.ViewHolder {

        public ImageView movie_iv;

        public PopularMoviesAdapterViewHolder(View itemView) {
            super(itemView);
            movie_iv = itemView.findViewById(R.id.list_item_icon);
        }
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}
