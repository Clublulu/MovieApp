package com.udacity.android.popularmovies.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.android.popularmovies.R;
import com.udacity.android.popularmovies.databinding.TrailersListItemBinding;
import com.udacity.android.popularmovies.model.Trailer;
import com.udacity.android.popularmovies.ui.MovieOnClickListener;

import java.util.List;

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.MovieTrailersViewHolder> {

    private MovieOnClickListener mClickListener;

    private List<Trailer> mTrailers;

    public MovieTrailersAdapter(MovieOnClickListener clickListener) {
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public MovieTrailersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        TrailersListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.trailers_list_item, viewGroup, false);

        return new MovieTrailersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailersViewHolder holder, int position) {
        holder.bind(mTrailers);
    }

    @Override
    public int getItemCount() {
        return mTrailers == null ? 0 : mTrailers.size();
    }

    public void swapTrailers(List<Trailer> newTrailers) {
        if (mTrailers == null) {
            mTrailers = newTrailers;
            notifyDataSetChanged();
        }
    }

    class MovieTrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TrailersListItemBinding mBinding;

        public MovieTrailersViewHolder(TrailersListItemBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setOnClickListener(this);
            mBinding = binding;
        }

        @Override
        public void onClick(View v) {
            mClickListener.onClickItem(mTrailers.get(getAdapterPosition()));
        }

        void bind(List<Trailer> trailers) {
            mBinding.setTrailer(trailers.get(getAdapterPosition()));
            mBinding.executePendingBindings();
        }
    }
}
