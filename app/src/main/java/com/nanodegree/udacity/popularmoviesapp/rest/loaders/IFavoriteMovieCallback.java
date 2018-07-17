package com.nanodegree.udacity.popularmoviesapp.rest.loaders;

import com.nanodegree.udacity.popularmoviesapp.rest.model.Movie;

import java.util.List;

public interface IFavoriteMovieCallback {

    void onFavoriteMoviesLoaded(final List<Movie> movieList);

    void onFavoriteMoviesError();
}
