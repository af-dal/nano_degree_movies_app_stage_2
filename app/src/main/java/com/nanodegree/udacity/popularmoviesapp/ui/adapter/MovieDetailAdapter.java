package com.nanodegree.udacity.popularmoviesapp.ui.adapter;

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;
import com.nanodegree.udacity.popularmoviesapp.ui.delegates.MovieDetailInfoDelegate;
import com.nanodegree.udacity.popularmoviesapp.ui.delegates.MovieReviewInfoDelegate;
import com.nanodegree.udacity.popularmoviesapp.ui.delegates.TrailerDelegate;

import java.util.List;

public class MovieDetailAdapter extends ListDelegationAdapter<List<Object>> {

    public MovieDetailAdapter() {
        delegatesManager.addDelegate(new MovieDetailInfoDelegate())
            .addDelegate(new TrailerDelegate())
            .addDelegate(new MovieReviewInfoDelegate());
    }
}