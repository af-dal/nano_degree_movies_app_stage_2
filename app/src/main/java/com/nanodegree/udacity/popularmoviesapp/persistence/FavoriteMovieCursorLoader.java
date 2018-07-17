package com.nanodegree.udacity.popularmoviesapp.persistence;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import com.nanodegree.udacity.popularmoviesapp.persistence.contracts.FavoriteMoviesContract;

public class FavoriteMovieCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private final IFavoriteMovieCursorCallback favoriteMovieCallback;
    private final String movieId;
    private final Context context;

    public FavoriteMovieCursorLoader(@NonNull final Context context, @Nullable final IFavoriteMovieCursorCallback favoriteMovieCallback,
                                     @Nullable final String movieId) {
        this.favoriteMovieCallback = favoriteMovieCallback;
        this.context = context;
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final CursorLoader cursorLoader;
        if (!TextUtils.isEmpty(movieId)) {
            cursorLoader = new CursorLoader(context, FavoriteMoviesContract.FavoriteMovieEntry.buildUriWithId(movieId), null,
                null, null, null);
        } else {
            cursorLoader = new CursorLoader(context, FavoriteMoviesContract.FavoriteMovieEntry.CONTENT_URI, null,
                null, null, null);
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull final Loader<Cursor> loader, Cursor data) {
        data.moveToPosition(-1);
        if (favoriteMovieCallback != null) {
            favoriteMovieCallback.onCursorLoaded(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull final Loader<Cursor> loader) {
    }
}
