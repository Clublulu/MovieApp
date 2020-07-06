package com.udacity.android.popularmovies.ui.fragment;

import android.content.Intent;

import com.google.android.youtube.player.YouTubeIntents;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.model.Trailer;
import com.udacity.android.popularmovies.ui.MovieClickListener;

import java.util.List;

public class TrailerListFragment extends BaseDetailListFragment<Trailer> implements MovieClickListener {

    @Override
    public void onClickItem(Object item) {
        Intent youtubeIntent = YouTubeIntents.createPlayVideoIntent(getActivity().getApplicationContext(), ((Trailer) item).url);
        Intent chooser = Intent.createChooser(youtubeIntent, getString(R.string.youtube_intent_dialog_message));
        startActivity(chooser);
    }

    @Override
    int getAdapterResId() {
        return R.string.app_adapter_trailers;
    }

    @Override
    MovieClickListener getClickListener() {
        return this;
    }

    @Override
    List<Trailer> getItemList(Movie movie) {
        return movie.trailers;
    }
}
