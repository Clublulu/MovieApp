package com.udacity.android.popularmovies.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeIntents;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Trailer;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;
import com.udacity.android.popularmovies.ui.adapter.BaseMovieListTypeAdapter;
import com.udacity.android.popularmovies.ui.adapter.MovieListTypeAdapterFactory;
import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

/**
 * Fragment that displayed either the list of Trailers or Reviews.
 *
 */
public class MovieListsFragment extends Fragment implements MovieOnClickListener {


    private static final String MOVIE_ID_KEY = "movie_id_key";
    private static final String MOVIE_LIST_LAYOUT_ID = "movie_list_id_key";

    private RecyclerView mRecyclerView;
    private TextView mNoMovieListsTextView;

    private int mMovieId;
    private int mLayoutResId;


    public static MovieListsFragment getInstance(int movieId, int layoutResId) {
        MovieListsFragment fragment = new MovieListsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIE_ID_KEY, movieId);
        bundle.putInt(MOVIE_LIST_LAYOUT_ID, layoutResId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
        mNoMovieListsTextView = view.findViewById(R.id.no_movie_list_message);
        if (getArguments() != null) {
            mMovieId = getArguments().getInt(MOVIE_ID_KEY);
            mLayoutResId = getArguments().getInt(MOVIE_LIST_LAYOUT_ID);
        }

        BaseMovieListTypeAdapter adapter = MovieListTypeAdapterFactory.create(mLayoutResId, this);
        setupRecyclerView(view, adapter);

        DetailActivityViewModelFactory factory = MovieInstanceProviderUtil
                .provideDetailActivityViewModelFactory(getActivity().getApplicationContext(), mMovieId);
        DetailActivityViewModel viewModel = new ViewModelProvider(this, factory)
                .get(DetailActivityViewModel.class);
        viewModel.getMovie().observe(this, movie -> {
                // constricted on time. not the best implementation but it works.
                switch (mLayoutResId) {
                    case R.id.trailers:
                        if (!movie.trailers.isEmpty()) {
                            // showMovieListData();
                            adapter.swapData(movie.trailers);
                        } else
                            showNoMovieListData();
                        break;
                    case R.id.reviews:
                        if (!movie.reviews.isEmpty()) {
                            // showMovieListData();
                            adapter.swapData(movie.reviews);
                        } else
                            showNoMovieListData();
                        break;
                }
        });

        return view;
    }

    private void setupRecyclerView(View view, RecyclerView.Adapter adapter) {
        mRecyclerView = view.findViewById(R.id.list_details_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickItem(Object item) {
        Intent youtubeIntent = YouTubeIntents.createPlayVideoIntent(getActivity().getApplicationContext(), ((Trailer) item).url);
        Intent chooser = Intent.createChooser(youtubeIntent, getString(R.string.youtube_intent_dialog_message));

        startActivity(chooser);
    }

    private void showNoMovieListData() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mNoMovieListsTextView.setVisibility(View.VISIBLE);
    }
}
