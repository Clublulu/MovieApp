package com.udacity.android.popularmovies.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.TrailersListItemBinding;
import com.udacity.android.popularmovies.model.MovieListable;
import com.udacity.android.popularmovies.model.Trailer;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;

public class MovieTrailersAdapter extends BaseMovieListsAdapter<Trailer> {

    private MovieOnClickListener mClickListener;


    public MovieTrailersAdapter(MovieOnClickListener clickListener) {
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public BaseMovieListsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new MovieTrailersViewHolder(
                DataBindingUtil.inflate(
                        inflater,
                        R.layout.trailers_list_item,
                        viewGroup,
                        false));
    }


    class MovieTrailersViewHolder extends BaseMovieListsAdapter.BaseMovieListsViewHolder implements View.OnClickListener {

        private TrailersListItemBinding mBinding;

        //            mBinding.setTrailer(trailers.get(getAdapterPosition()));
//            mBinding.executePendingBindings();

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
        void bind(MovieListable data) {
            mBinding.setTrailer((Trailer) data);
            mBinding.executePendingBindings();
        }
    }


}
