package com.udacity.android.popularmovies.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.ui.MovieClickListener;
import com.udacity.android.popularmovies.ui.adapter.BaseListTypeAdapter;
import com.udacity.android.popularmovies.ui.detail.DetailActivity;
import com.udacity.android.popularmovies.ui.main.MainActivity;
import com.udacity.android.popularmovies.ui.main.MainActivityViewModel;
import com.udacity.android.popularmovies.ui.main.MainActivityViewModelFactory;
import com.udacity.android.popularmovies.utilities.ObjectProviderUtil;

import java.util.List;


/**
 *  Base Main List Fragment provides data from the Main Activity View Model to its concrete implementation classes.
 *
 */
public abstract class BaseMainListFragment extends BaseListFragment implements MovieClickListener {

    @Override
    void observeData(View view, BaseListTypeAdapter adapter, int movieId) {
        MainActivityViewModelFactory factory = ObjectProviderUtil
                .provideMainActivityViewModelFactory(view.getContext());
        MainActivityViewModel viewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);
        viewModel.getLatestMovies(getSortCriteria());

        viewModel.getMovies().observe(this, movies -> {
            if (movies != null && movies.size() != 0) {
                hideProgressBar(view);
                adapter.swapData(movies);
            } else if (hasNoFavorites(movies)) {
                hideProgressBar(view);
                TextView noListItemsFound = view.findViewById(R.id.no_list_items_available);
                noListItemsFound.setText(getNoItemText());
                noListItemsFound.setVisibility(View.VISIBLE);
            }
        });
    }

    abstract String getSortCriteria();

    @Override
    RecyclerView.LayoutManager getLayoutManager(View view) {
        return  new GridLayoutManager(
                view.getContext(),
                2,
                RecyclerView.VERTICAL,
                false);
    }

    @Override
    int getAdapterResId() {
        return R.string.app_adapter_movies;
    }


    @Override
    public void onClickItem(Object movie, ImageView sharedElementItem) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, ((Movie) movie).movieId);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_IMAGE, ((Movie) movie).image);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(
                        getActivity(),
                        sharedElementItem,
                        getString(R.string.movie_poster_shared_element_transition_name));
        startActivity(intent, options.toBundle());
    }

    @Override
    MovieClickListener getClickListener() {
        return this;
    }

    @Override
    int getLayoutId() {
        return R.layout.list_fragment;
    }

    private boolean hasNoFavorites(List<Movie> movies) {
        return getSortCriteria().equals(getString(R.string.favorites)) &&
                (movies == null || movies.size() == 0);
    }

}
