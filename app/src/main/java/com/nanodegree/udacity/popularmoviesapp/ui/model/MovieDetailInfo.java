package com.nanodegree.udacity.popularmoviesapp.ui.model;

import android.database.Cursor;

import com.nanodegree.udacity.popularmoviesapp.rest.model.Movie;

public class MovieDetailInfo {

    private final Cursor cursor;

    private final Movie movie;

    public MovieDetailInfo(Cursor cursor, Movie movie) {
        this.cursor = cursor;
        this.movie = movie;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public Movie getMovie() {
        return movie;
    }
}
