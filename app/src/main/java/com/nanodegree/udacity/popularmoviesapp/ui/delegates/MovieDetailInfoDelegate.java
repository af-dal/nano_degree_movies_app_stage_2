package com.nanodegree.udacity.popularmoviesapp.ui.delegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.nanodegree.udacity.popularmoviesapp.ui.model.MovieDetailInfo;
import com.nanodegree.udacity.popularmoviesapp.ui.viewholder.MovieDetailInfoHolder;

import java.util.List;

public class MovieDetailInfoDelegate extends AdapterDelegate<List<Object>> {
    @Override
    protected boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof MovieDetailInfo;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return MovieDetailInfoHolder.create(parent);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final MovieDetailInfoHolder vh = (MovieDetailInfoHolder) holder;
        vh.bind((MovieDetailInfo) items.get(position));
    }

    @Override
    protected void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
        ((MovieDetailInfoHolder) viewHolder).resetViews();
    }
}
