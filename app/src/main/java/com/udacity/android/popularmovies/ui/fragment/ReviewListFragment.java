package com.udacity.android.popularmovies.ui.fragment;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Movie;
import com.udacity.android.popularmovies.model.Review;
import com.udacity.android.popularmovies.ui.MovieClickListener;

import java.util.List;

public class ReviewListFragment extends BaseDetailListFragment<Review> {



    @Override
    int getAdapterResId() {
        return R.string.app_adapter_reviews;
    }

    @Override
    MovieClickListener getClickListener() {
        return null;
    }

    @Override
    String getNoItemText() {
        return getString(R.string.reviews_not_found_message);
    }


    @Override
    List<Review> getItemList(Movie movie) {
        return movie.reviews;
    }


}
