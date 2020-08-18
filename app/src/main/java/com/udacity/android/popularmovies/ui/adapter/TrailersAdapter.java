package com.udacity.android.popularmovies.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Trailer;
import com.udacity.android.popularmovies.ui.MovieClickListener;
import com.udacity.android.popularmovies.utilities.MoviesURLBuilder;

/**
 * RecyclerView Adapter for Trailers.
 *
 */
public class TrailersAdapter extends BaseListTypeAdapter<Trailer> {

    private MovieClickListener mMovieClickListener;

    public TrailersAdapter(MovieClickListener movieClickListener) {
        mMovieClickListener = movieClickListener;
    }

    @NonNull
    @Override
    public BaseMovieListTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.trailers_list_item, viewGroup, false);

        return new MovieTrailersViewHolder(view);
    }

    class MovieTrailersViewHolder extends BaseMovieListTypeViewHolder implements View.OnClickListener {

        private TextView mTrailerTitle;
        private ImageView mTrailerThumbnail;

        public MovieTrailersViewHolder(View itemView) {
            super(itemView);
            mTrailerTitle = itemView.findViewById(R.id.trailer_title);
            mTrailerThumbnail = itemView.findViewById(R.id.trailer_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            mMovieClickListener.onClickItem(getList().get(getAdapterPosition()), null);
        }

        @Override
        void bind(Trailer trailer) {
            mTrailerTitle.setText(trailer.title);
            String trailerThumbnail = MoviesURLBuilder.getInstance().buildYoutubeTrailerThumbnail(trailer.url);
            Picasso.get().load(trailerThumbnail).into(mTrailerThumbnail);
        }
    }
}
