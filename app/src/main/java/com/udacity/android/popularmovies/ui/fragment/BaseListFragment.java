package com.udacity.android.popularmovies.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.udacity.android.popularmovies.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.ui.MovieClickListener;
import com.udacity.android.popularmovies.ui.adapter.BaseListTypeAdapter;
import com.udacity.android.popularmovies.ui.adapter.ListTypeAdapterFactory;


/**
 * Base Master List fragment serves to create Views specifically for lists.
 *
 */
public abstract class BaseListFragment extends Fragment {

    private static int mMovieId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), viewGroup, false);
        BaseListTypeAdapter adapter = ListTypeAdapterFactory.create(getAdapterResId(), getClickListener());
        setupRecyclerView(view, adapter);
        observeData(view, adapter, getMovieId(getArguments()));

        return view;
    }

    void setupRecyclerView(View view, RecyclerView.Adapter adapter) {
        RecyclerView mRecyclerView = view.findViewById(R.id.item_list);
        mRecyclerView.setLayoutManager(getLayoutManager(view));
        mRecyclerView.setAdapter(adapter);
    }

    void hideProgressBar(View view) {
        view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
    }

    private int getMovieId(Bundle bundle) {
        if (bundle != null) {
            mMovieId = bundle.getInt(MovieFragmentFactory.EXTRA_MOVIE_ID);
        }
        return mMovieId;
    }


    abstract RecyclerView.LayoutManager getLayoutManager(View view);

    abstract void observeData(View view, BaseListTypeAdapter adapter, int movieId);

    abstract int getAdapterResId();

    abstract MovieClickListener getClickListener();

    abstract int getLayoutId();
}
