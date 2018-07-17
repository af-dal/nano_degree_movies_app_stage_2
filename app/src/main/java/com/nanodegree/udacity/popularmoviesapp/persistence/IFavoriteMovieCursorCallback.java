package com.nanodegree.udacity.popularmoviesapp.persistence;

import android.database.Cursor;

public interface IFavoriteMovieCursorCallback {
    void onCursorLoaded(final Cursor cursor);
}
