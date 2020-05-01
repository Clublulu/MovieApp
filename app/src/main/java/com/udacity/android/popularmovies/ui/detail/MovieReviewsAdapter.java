package com.udacity.android.popularmovies.ui.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.ReviewsListItemBinding;
import com.udacity.android.popularmovies.model.MovieListable;
import com.udacity.android.popularmovies.model.Review;

public class MovieReviewsAdapter extends BaseMovieListsAdapter<Review> {


    @NonNull
    @Override
    public BaseMovieListsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new MovieReviewsViewHolder(
                DataBindingUtil.inflate(
                        inflater,
                        R.layout.reviews_list_item,
                        viewGroup,
                        false));
    }

    class MovieReviewsViewHolder extends BaseMovieListsAdapter.BaseMovieListsViewHolder {

        private ReviewsListItemBinding mBinding;

        public MovieReviewsViewHolder(ReviewsListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        void bind(MovieListable data) {
            mBinding.setReview((Review) data);
            mBinding.executePendingBindings();
        }
    }
}
