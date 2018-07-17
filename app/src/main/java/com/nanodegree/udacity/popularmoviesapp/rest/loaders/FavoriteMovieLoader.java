package com.nanodegree.udacity.popularmoviesapp.rest.loaders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.nanodegree.udacity.popularmoviesapp.rest.model.Movie;
import com.nanodegree.udacity.popularmoviesapp.rest.repo.MovieRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieLoader implements LoaderManager.LoaderCallbacks<List<Movie>> {
    private final Context context;
    private final List<String> movieIds;
    private final IFavoriteMovieCallback favoriteMovieCallback;
    private final MovieRepository movieRepository = new MovieRepository();

    public FavoriteMovieLoader(@NonNull final Context context, @NonNull final List<String> movieIds,
                               @Nullable final IFavoriteMovieCallback favoriteMovieCallback) {
        this.context = context;
        this.movieIds = movieIds;
        this.favoriteMovieCallback = favoriteMovieCallback;
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<Movie>>(context) {
            @Override
            public List<Movie> loadInBackground() {
                final List<Movie> movieList = new ArrayList<>();
                for (String movieId : movieIds) {
                    final Movie movie = movieRepository.loadMovieBlocking(movieId);
                    if (movie != null) {
                        movieList.add(movie);
                    }
                }
                return movieList;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {
        if (data == null && favoriteMovieCallback != null) favoriteMovieCallback.onFavoriteMoviesError();
        if (favoriteMovieCallback != null) favoriteMovieCallback.onFavoriteMoviesLoaded(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
    }
}
