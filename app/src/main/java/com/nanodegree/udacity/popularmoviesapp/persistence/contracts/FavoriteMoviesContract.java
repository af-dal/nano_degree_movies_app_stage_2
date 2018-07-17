package com.nanodegree.udacity.popularmoviesapp.persistence.contracts;

import android.net.Uri;
import android.provider.BaseColumns;

import com.nanodegree.udacity.popularmoviesapp.BuildConfig;

public final class FavoriteMoviesContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVORITE_MOVIE = "favorite_movie";

    private FavoriteMoviesContract() {
    }

    public static class FavoriteMovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite_movie";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(PATH_FAVORITE_MOVIE)
            .build();

        public static Uri buildUriWithId(final String id) {
            return CONTENT_URI.buildUpon()
                .appendPath(id)
                .build();
        }

    }
}