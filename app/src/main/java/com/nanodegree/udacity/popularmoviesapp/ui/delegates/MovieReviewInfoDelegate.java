package com.nanodegree.udacity.popularmoviesapp.ui.delegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.nanodegree.udacity.popularmoviesapp.rest.model.review.Review;
import com.nanodegree.udacity.popularmoviesapp.ui.viewholder.MovieReviewInfoHolder;

import java.util.List;

public class MovieReviewInfoDelegate extends AdapterDelegate<List<Object>> {
    @Override
    protected boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof Review;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return MovieReviewInfoHolder.create(parent);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final MovieReviewInfoHolder vh = (MovieReviewInfoHolder) holder;

        vh.bind((Review) items.get(position), (!(items.get(position - 1) instanceof Review)));
    }

    @Override
    protected void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
        ((MovieReviewInfoHolder) viewHolder).resetViews();
    }
}
