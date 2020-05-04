package com.udacity.android.popularmovies.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Trailer;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;
import com.udacity.android.popularmovies.utilities.MoviesURLBuilder;

/**
 * RecyclerView Adapter for Trailers.
 *
 */
public class MovieTrailersAdapter extends BaseMovieListTypeAdapter<Trailer> {

    private MovieOnClickListener mMovieOnClickListener;

    public MovieTrailersAdapter(MovieOnClickListener movieOnClickListener) {
        mMovieOnClickListener = movieOnClickListener;
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
        private ImageView mPlayButton;
        private ImageView mTrailerThumbnail;
        private YouTubePlayerView mYoutubePlayerView;

        public MovieTrailersViewHolder(View itemView) {
            super(itemView);
            mPlayButton = itemView.findViewById(R.id.btnPlay);
            mPlayButton.setOnClickListener(this);
            mTrailerTitle = itemView.findViewById(R.id.trailer_title);
            mYoutubePlayerView = itemView.findViewById(R.id.youtube_view);
            mTrailerThumbnail = itemView.findViewById(R.id.trailer_thumbnail);
        }

        @Override
        public void onClick(View v) {
            hideImages();
        }

        @Override
        void bind(Trailer trailer) {
            configureTitle(trailer);
            String trailerThumbnailImage = MoviesURLBuilder.getInstance().buildYoutubeTrailerThumbnail(trailer.url);
            Picasso.get().load(trailerThumbnailImage).into(mTrailerThumbnail);
            bindYoutubePlayerListener(trailer);
            showImages();
        }

        private void showImages() {
            mYoutubePlayerView.setVisibility(View.GONE);
            mTrailerThumbnail.setVisibility(View.VISIBLE);
            mPlayButton.setVisibility(View.VISIBLE);
        }

        private void hideImages() {
            mTrailerThumbnail.setVisibility(View.GONE);
            mPlayButton.setVisibility(View.GONE);
            mYoutubePlayerView.setVisibility(View.VISIBLE);
        }

        private void bindYoutubePlayerListener(Trailer trailer) {
            mYoutubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(trailer.url, 0);
                }
            });
        }

        private void configureTitle(Trailer trailer) {
            mTrailerTitle.setText(trailer.title);
            mTrailerTitle.setOnClickListener(v -> {
                mMovieOnClickListener.onClickItem(trailer);
            });

        }
    }
}
