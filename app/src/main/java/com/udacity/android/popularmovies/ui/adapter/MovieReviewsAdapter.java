package com.udacity.android.popularmovies.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.ReviewsListItemBinding;
import com.udacity.android.popularmovies.model.Review;

/**
 * RecyclerView Adapter for Reviews.
 *
 */
public class MovieReviewsAdapter extends BaseMovieListTypeAdapter<Review> {

    @NonNull
    @Override
    public BaseMovieListTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new MovieReviewsViewHolder(
                DataBindingUtil.inflate(
                        inflater,
                        R.layout.reviews_list_item,
                        viewGroup,
                        false));
    }

    class MovieReviewsViewHolder extends BaseMovieListTypeViewHolder {

        private ReviewsListItemBinding mBinding;

        public MovieReviewsViewHolder(ReviewsListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        void bind(Review review) {
            mBinding.setReview(review);
            mBinding.executePendingBindings();
        }
    }
}
