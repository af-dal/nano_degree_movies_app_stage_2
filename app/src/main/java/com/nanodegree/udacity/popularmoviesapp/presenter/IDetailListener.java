package com.nanodegree.udacity.popularmoviesapp.presenter;

import android.database.Cursor;

import com.nanodegree.udacity.popularmoviesapp.rest.model.Movie;
import com.nanodegree.udacity.popularmoviesapp.rest.model.review.ReviewResponse;
import com.nanodegree.udacity.popularmoviesapp.rest.model.videos.VideoResponse;

public interface IDetailListener {
    void onDetailLoaded(final Cursor cursor, final Movie movie, VideoResponse videoResponse, ReviewResponse reviewResponse);

    void onError(final String error);
}
