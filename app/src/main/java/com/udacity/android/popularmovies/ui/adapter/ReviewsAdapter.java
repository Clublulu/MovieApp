package com.udacity.android.popularmovies.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.model.Review;

/**
 * RecyclerView Adapter for Reviews.
 *
 */
public class ReviewsAdapter extends BaseListTypeAdapter<Review> {

    @NonNull
    @Override
    public BaseMovieListTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.reviews_list_item, viewGroup, false);

        return new MovieReviewsViewHolder(view);

    }

    class MovieReviewsViewHolder extends BaseMovieListTypeViewHolder {

        private ExpandableTextView mExpandableTextView;
        private TextView mAuthor;

        public MovieReviewsViewHolder(View view) {
            super(view);
            mExpandableTextView = view.findViewById(R.id.expand_text_view);
            mAuthor = view.findViewById(R.id.review_author);
        }

        @Override
        void bind(Review review) {
            mExpandableTextView.setText(review.description);
            mAuthor.setText(review.author);
        }
    }
}
