package com.nanodegree.udacity.popularmoviesapp.ui.delegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.nanodegree.udacity.popularmoviesapp.ui.model.TrailerModel;
import com.nanodegree.udacity.popularmoviesapp.ui.viewholder.TrailerInfoHolder;

import java.util.List;

public class TrailerDelegate extends AdapterDelegate<List<Object>> {
    @Override
    protected boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof TrailerModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return TrailerInfoHolder.create(parent);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final TrailerInfoHolder vh = (TrailerInfoHolder) holder;
        vh.bind((TrailerModel) items.get(position));
    }

    @Override
    protected void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
        ((TrailerInfoHolder) viewHolder).resetViews();
    }
}
