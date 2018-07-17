package com.nanodegree.udacity.popularmoviesapp.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nanodegree.udacity.popularmoviesapp.persistence.contracts.FavoriteMoviesContract;

public class FavoriteMovieProvider extends ContentProvider {
    public static final int CODE_FAVORITE_MOVIE = 100;
    public static final int CODE_FAVORITE_MOVIE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieAppDbHelper openHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavoriteMoviesContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, FavoriteMoviesContract.PATH_FAVORITE_MOVIE, CODE_FAVORITE_MOVIE);
        matcher.addURI(authority, FavoriteMoviesContract.PATH_FAVORITE_MOVIE + "/#", CODE_FAVORITE_MOVIE_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        openHelper = new MovieAppDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull final Uri uri, @Nullable final String[] projection, @Nullable final String selection,
                        @Nullable final String[] selectionArgs, @Nullable final String sortOrder) {
        final Context context = getContext();
        final Cursor cursor;

        if (context == null) return null;

        switch (sUriMatcher.match(uri)) {
            case CODE_FAVORITE_MOVIE:
                cursor = openHelper.getReadableDatabase().query(
                    FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
                break;
            case CODE_FAVORITE_MOVIE_WITH_ID:
                final String movieId = uri.getLastPathSegment();
                final String[] selectionArguments = new String[] {movieId};

                cursor = openHelper.getReadableDatabase().query(
                    FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME,
                    projection,
                    FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_NAME_ID + " = ? ",
                    selectionArguments,
                    null,
                    null,
                    sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull final Uri uri, @Nullable final ContentValues values) {
        final Context context = getContext();
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        if (context == null) return uri;

        switch (sUriMatcher.match(uri)) {
            case CODE_FAVORITE_MOVIE:
                db.beginTransaction();
                try {
                    db.insert(FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME, null, values);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                break;
            default:
                return uri;
        }
        context.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final Context context = getContext();
        if (context == null) return 0;

        int numRowsDeleted;

        switch (sUriMatcher.match(uri)) {
            case CODE_FAVORITE_MOVIE_WITH_ID:
                final String movieId = uri.getLastPathSegment();
                final String[] selectionArguments = new String[] {movieId};
                numRowsDeleted = openHelper.getWritableDatabase().delete(
                    FavoriteMoviesContract.FavoriteMovieEntry.TABLE_NAME,
                    FavoriteMoviesContract.FavoriteMovieEntry.COLUMN_NAME_ID + " = ? ",
                    selectionArguments);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
