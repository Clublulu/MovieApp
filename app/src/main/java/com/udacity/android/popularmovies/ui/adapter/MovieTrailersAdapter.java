package com.udacity.android.popularmovies.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.TrailersListItemBinding;
import com.udacity.android.popularmovies.model.MovieUmbrella;
import com.udacity.android.popularmovies.model.Trailer;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;

/**
 * RecyclerView Adapter for Trailers.
 *
 */
public class MovieTrailersAdapter extends BaseMovieListTypeAdapter<Trailer> {

    private MovieOnClickListener mClickListener;

    public MovieTrailersAdapter(MovieOnClickListener clickListener) {
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public BaseMovieListTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new MovieTrailersViewHolder(
                DataBindingUtil.inflate(
                        inflater,
                        R.layout.trailers_list_item,
                        viewGroup,
                        false));
    }

    class MovieTrailersViewHolder extends BaseMovieListTypeViewHolder implements View.OnClickListener {

        private TrailersListItemBinding mBinding;

        public MovieTrailersViewHolder(TrailersListItemBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setOnClickListener(this);
            mBinding = binding;
        }

        @Override
        public void onClick(View v) {
            mClickListener.onClickItem(getList().get(getAdapterPosition()));
        }

        @Override
        void bind(Trailer trailer) {
            mBinding.setTrailer(trailer);
            mBinding.executePendingBindings();
        }
    }
}
