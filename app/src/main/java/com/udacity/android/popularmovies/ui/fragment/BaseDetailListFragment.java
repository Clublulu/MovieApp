package com.udacity.android.popularmovies.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.model.MovieUmbrella;
import com.udacity.android.popularmovies.ui.MovieClickListener;
import com.udacity.android.popularmovies.ui.adapter.BaseListTypeAdapter;
import com.udacity.android.popularmovies.ui.detail.DetailActivityViewModel;
import com.udacity.android.popularmovies.ui.detail.DetailActivityViewModelFactory;
import com.udacity.android.popularmovies.utilities.ObjectProviderUtil;

import java.util.List;

/**
 * Base Detail List Fragment responsible for providing concrete detail list fragment instances,
 * and providing data from the detail activity view model to them.
 *
 * @param
 */
public abstract class BaseDetailListFragment<T extends MovieUmbrella> extends BaseListFragment {

    static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";

    public static BaseDetailListFragment getInstance(MovieClickListener clickListener, int resId, int movieId) {
        BaseDetailListFragment fragment;
        switch (resId) {
            case R.string.app_movie_trailers_fragment:
                fragment = new TrailerListFragment();
                break;
            case R.string.app_movie_reviews_fragment:
                fragment = new ReviewListFragment();
                break;
            case R.string.app_movie_details_fragment:
                fragment = new MovieDetailFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected fragment: " + resId);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_MOVIE_ID, movieId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    void observeData(View view, BaseListTypeAdapter adapter, int movieId) {
        DetailActivityViewModelFactory factory = ObjectProviderUtil
                .provideDetailActivityViewModelFactory(view.getContext(), movieId);
        DetailActivityViewModel viewModel = new ViewModelProvider(this, factory)
                .get(DetailActivityViewModel.class);
        viewModel.getMovie().observe(this, movie -> {
            if (movie != null) {
                hideProgressBar(view);
                if (!isItemListEmpty(movie)) {
                    adapter.swapData(getItemList(movie));
                } else {
                    TextView noListItemsFound = view.findViewById(R.id.no_list_items_available);
                    noListItemsFound.setText(noItemsFound());
                    noListItemsFound.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    abstract List<T> getItemList(Movie movie);

    @Override
    RecyclerView.LayoutManager getLayoutManager(View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @Override
    int getLayoutId() {
        return R.layout.list_fragment;
    }

    abstract String noItemsFound();

    private boolean isItemListEmpty(Movie movie) {
        return getItemList(movie) == null || getItemList(movie).size() == 0;
    }

}
