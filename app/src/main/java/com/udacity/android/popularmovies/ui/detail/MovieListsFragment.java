package com.udacity.android.popularmovies.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;
import com.udacity.android.popularmovies.utilities.MovieInstanceProviderUtil;

public class MovieListsFragment extends Fragment implements MovieOnClickListener {

    private static final String LOG_TAG = MovieListsFragment.class.getSimpleName();

    private static final String MOVIE_ID_KEY = "movie_id_key";

    private RecyclerView mRecyclerView;
    private int mMovieId;


    public static MovieListsFragment getInstance(int movieId) {
        MovieListsFragment fragment = new MovieListsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIE_ID_KEY, movieId);
        fragment.setArguments(bundle);

        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
        MovieTrailersAdapter adapter = setupRecyclerViewTrailer(view);

        if (getArguments() != null) {
            mMovieId = getArguments().getInt(MOVIE_ID_KEY);
        }

        DetailActivityViewModelFactory factory = MovieInstanceProviderUtil
                .provideDetailActivityViewModelFactory(getActivity().getApplicationContext(), mMovieId);
        DetailActivityViewModel viewModel = new ViewModelProvider(this, factory)
                .get(DetailActivityViewModel.class);
        viewModel.getMovie().observe(this, movie -> {

            adapter.swapTrailers(movie.trailers);
        });

        return view;
    }

    private MovieTrailersAdapter setupRecyclerViewTrailer(View view) {
        mRecyclerView = view.findViewById(R.id.list_details_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        MovieTrailersAdapter adapter = new MovieTrailersAdapter(this);
        mRecyclerView.setAdapter(adapter);

        return adapter;
    }

    @Override
    public void onClickItem(Object trailer) {
        Toast.makeText(getActivity().getApplicationContext(),"Trailer Click Event works",Toast.LENGTH_SHORT).show();
    }
}
